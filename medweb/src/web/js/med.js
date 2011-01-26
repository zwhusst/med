function sHomeInput(event){
  if(event.keyCode==13){
   homeSearch();
  }
}

function homeSearch() {
  var val = document.getElementById("search-text").value;
  window.location.href="rs/ss/"+val+"?qs="+encodeURI(val);
}

function paneSearchKeyupHandler(basehref,event){
  if(event.keyCode==13){
    paneSearch(basehref);
  }
}

function popDialog(basehref,dialogId) {
  var jqDialogId = "#"+dialogId;
  var kId = $(jqDialogId).attr('kid');
  if ($(jqDialogId).attr('loaded') == "false") {  
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, dialogId, popDialogCallback, jqDialogId);
  var url = url4KeywordDetails(basehref,kId);
  req.open("GET", url , true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);  
  } else {
  $(jqDialogId).dialog({ width: 500,height: 430 });
  }
}

function popDialogCallback(dialogId) {
	$(dialogId).dialog({ width: 500,height: 430 });	 
	$(dialogId).bind('dialogclose', function(event, ui) {
	  if ($(this).dialog('isOpen')) {
  		$(this).dialog('destroy');
  	   }
	});
  $(dialogId).attr('loaded','true');
}

function paneSearch(basehref){
  var url = assembleSearchURL(basehref);
  window.location.href=url;
}

function initMenu() {
  if ($('#catemenu').attr('loaded') == "false") { 
    $('#catemenu').attr('loaded','true');
    
	$('#catemenu ul').hide(
	);
	$('#catemenu li a').click(
		function() {
			$(this).next().slideToggle('normal');
		}
	);
	$('.ul-img').toggle(
		function(){
			$(this).css({ "background": "url('image/ulimg02.png')", "background-repeat": "no-repeat" });
		},
		function(){
			$(this).css({ "background": "url('image/ulimg01.png')", "background-repeat": "no-repeat" });
		}
	);
   }

}
function moveFun(para){
  if (para.length <2) {
  return;
  }
  
  for (var i = 0; i < para.length; i = i+ 2) {
  var sId = para[i];
  var tId = para[i+ 1];
  var srcEle = document.getElementById(sId);
  var targetEle = document.getElementById(tId);  
  if ((srcEle != null) && (targetEle != null)) {
    targetEle.innerHTML = srcEle.innerHTML;
  	  srcEle.innerHTML="";
  }
  }
}

function url4KeywordDetails(basehref,detailsKeyId){
  // topic filter
  var tFilter=getDocumentNameValue("tFilter",",");
  // category filter
  var cFilter=null;
  // query keywords
  var queryKeywords=getCheckedKeywordsURL();
  // query string
  var queryString=getDocumentNameValue("querystr"," ");

  var url;
  if ((queryKeywords!=null)&&(queryString!=null)&&(queryString!="")) {
    url="rs/details"
  } else if((queryKeywords==null)&&(queryString!=null)&&(queryString!="")) {
    url="rs/details4s";
  } else if((queryKeywords!=null)&&(queryString==null || queryString=="")) {
    url="rs/details4k";
  }
  
  if ((tFilter!=null)&&(tFilter!="-1")) {
    // current no category filter, just use -1
    url=url+"/"+tFilter+"/-1";
  }
  if((queryString!=null)&&(queryString!="")){
    url=url+"/"+encodeURI(queryString);
  }
   if (queryKeywords!=null) {
    url=url+"/"+queryKeywords;
  }
  url=url+"/"+detailsKeyId;
  if((queryString!=null)&&(queryString!="")){
    url=url+"?qs="+encodeURI(queryString);
  }
  return basehref+url;
}

function assembleSearchURL(basehref){
  // topic filter
  var tFilter=getDocumentNameValue("tFilter",",");
  // category filter
  var cFilter=null;
  // query keywords
  var queryKeywords=getCheckedKeywordsURL();
  // query string
  var queryString=getDocumentNameValue("querystr"," ");

  var url;
  if ((queryKeywords!=null)&&(queryString!=null)&&(queryString!="")) {
    url="rs/s"
  } else if((queryKeywords==null)&&(queryString!=null)&&(queryString!="")) {
    url="rs/ss";
  } else if((queryKeywords!=null)&&(queryString==null || queryString=="")) {
    url="rs/sk";
  }

  if ((tFilter!=null)&&(tFilter!="-1")) {
    // current no category filter, just use -1
    url=url+"/"+tFilter+"/-1";
  }
  if (queryKeywords!=null) {
    url=url+"/"+queryKeywords;
  }
  if((queryString!=null)&&(queryString!="")){
    url=url+"/"+encodeURI(queryString)+"?qs="+encodeURI(queryString);
  }
  return basehref+url;
}

function getDocumentNameValue(eleId, sep) {
  var result=null;
  var data=document.getElementsByName(eleId);
  if (data!=null) {
    for(i=0;i<data.length;i++){
       if (result==null) {
         result=data[i].value;
       }else{
         result=result+sep+data[i].value;
       }
    }
  }
  return result;
}

function getCheckedKeywordsURL(){
  var url=null;
  var keywords = document.getElementsByName("panekeyword");
  if (keywords !=null) {
    for (var i = 0; i < keywords.length; i++) {
      if (keywords[i].checked){
        if (url==null) {
          url=keywords[i].id;
        }else{
          url=url+","+keywords[i].id;
        }
      }    
    }
  }  
  return url;
}

function renderHtmlData(req, elementId, callback,callbackPara) {
   return function () {   
   // If the request's status is "complete"
   if (req.readyState == 4) {     
     // Check that we received a successful response from the server
     if (req.status == 200) {
     // Pass the XML payload of the response to the handler function.
     document.getElementById(elementId).innerHTML=req.responseText;
     if (callback != null) {
     	if (callbackPara != null) {
    		callback(callbackPara);
    	} else {
    		callback();
    	}
     }     
//     if (elementId =="TopicResult") {
//       adjustlayout();
//	     adjustScrollBar();
//     } 
     } else {
     // An HTTP problem has occurred
     document.getElementById(elementId).innerHTML="HTTP error "+req.status+": "+req.statusText;
     }
   } else {
    document.getElementById(elementId).innerHTML="<center><img src='image/ajax_load.gif'></center>";
   }
  }
}

function newXMLHttpRequest() {
  var xmlreq = false;

  // Create XMLHttpRequest object in non-Microsoft browsers
  if (window.XMLHttpRequest) {
  xmlreq = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
  try {
    // Try to create XMLHttpRequest in later versions
    // of Internet Explorer
    xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e1) {
    // Failed to create required ActiveXObject
    try {
    // Try version supported by older versions
    // of Internet Explorer
    xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
   } catch (e2) {
    // Unable to create an XMLHttpRequest by any means
    xmlreq = false;
    }
  }
  }

  return xmlreq;
}