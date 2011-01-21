function sHomeInput(event){
  if(event.keyCode==13){
   homeSearch();
  }
}

function homeSearch() {
  var val = document.getElementById("search-text").value;
  window.location.href="rs/ss/"+val;
}

function listTd(tdata, kid, cid) {
	var url = "s?hl=topd&"+"k="+kid +"&cid="+cid+"&ks=" + tdata;
	window.open(url);
}

function paneSearchKeyupHandler(event){
  if(event.keyCode==13){
    paneSearch();
  }
}

function popDialog(dialogId) {
  var jqDialogId = "#"+dialogId;
  var kId = $(jqDialogId).attr('kid');
  if ($(jqDialogId).attr('loaded') == "false") {  
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, dialogId, popDialogCallback, jqDialogId);
  var url = url4KeywordDetails(kId);
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

function paneSearch(){
  var url = assembleSearchURL();
  window.location.href=url;
}

function loadTopDataCB() {
  var ccIdVal = document.getElementById('curCategoryId');
  if (ccIdVal != null) {
    loadTopData(ccIdVal.value, "tab-1","tab-2");
   }
}
function loadTopData(cId, hosEleId, docEleId) {
  var ccIdVal = document.getElementById('curCategoryId');
  if (ccIdVal != null) {
    var cEleId = "cId"+ccIdVal.value;
    var obj = document.getElementById(cEleId);
    if (obj!=null) {
      obj.style.fontWeight='normal';
    }
  	ccIdVal.value = cId;
  }
  
  var contentType = document.getElementById('curContentType').value;
  if (contentType == "hospital") {
  	loadTopHospital(cId,hosEleId);  
  } else {
  	loadTopDoctor(cId, docEleId)
  }
  
  var cEleId2 = "cId"+cId;
  document.getElementById(cEleId2).style.fontWeight='bold';

}

function topAreaChange(hosEleId) {
  var ccIdVal = document.getElementById('curCategoryId');
  if (ccIdVal != null) {
  	loadTopHospital(ccIdVal.value,hosEleId);
  }
}
function loadTopHospital(cId, hosEleId){
  // load hospital data
  var aId;
  var areaVal = document.getElementById('areasInTop');
  if (areaVal == null) {
    aId = '0'
  } else {
    aId = areaVal.value;
  }
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, hosEleId);
  req.open("GET", "s?hl=thos&cId="+cId+"&aId="+aId, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);

  // visible the top hospital content
  document.getElementById("tab-1").style.display="block";
  document.getElementById("tab-2").style.display="none";
  $("#areasInTop").removeAttr("disabled");
  $("#t1").removeClass('tnormal').addClass('tactive');
  $("#t2").removeClass('tactive').addClass('tnormal');
}

function loadTopDoctor(cId, docEleId){
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, docEleId);
  req.open("GET", "s?hl=tdoc&cId="+cId, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);
  
  // visible the top doctor content
  document.getElementById("tab-1").style.display="none";
  document.getElementById("tab-2").style.display="block";
  $("#areasInTop").attr('disabled','disabled');
  $("#t1").removeClass('tactive').addClass('tnormal');
  $("#t2").removeClass('tnormal').addClass('tactive');  
}

function loadCMenu(eleId,ks, kid, cid){
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, eleId, loadTopDataCB);  
  req.open("GET", "s?hl=cmu&k="+kid+"&cid="+cid+"&ks="+ks, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);
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

function loadResult(eleId) {   
  var url = asseExSessionURL(true,true);
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, eleId, moveFun, ['dummyGuideInfo','GuideInfoSection']);
  req.open("GET", "s?hl=tr"+url, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);
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

function gT(topicId,eleId) {
  var url = assembleSessionURL(false,true);

  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, eleId);
  req.open("GET", "s?hl=tr&tFilter="+topicId+url, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");  
  req.send(null);
}

function gA(eleId) {
  var url = assembleSessionURL(false,true);

  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, eleId);
  req.open("GET", "s?hl=tr"+url, true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");  
  req.send(null);
}

function cc(eleId) {
  var paras, catVaalue;
  catVaalue = document.getElementById('categoryFilter3');
  if (catVaalue != null) {
    if (paras!=null) {
       paras=paras+"&cFilter=" + catVaalue.value;
    }else{
      paras="cFilter=" + catVaalue.value;
    }
  }
  catVaalue = document.getElementById('categoryFilter1');
  if (catVaalue != null) {
    if (paras!=null) {
      paras=paras+"&cFilter=" + catVaalue.value;
    }else{
      paras="cFilter=" + catVaalue.value;
    }
  }
  catVaalue = document.getElementById('categoryFilter5');
  if (catVaalue != null) {
    if (paras!=null) {
      paras=paras+"&cFilter=" + catVaalue.value;
    }else{
      paras="cFilter=" + catVaalue.value;
    }
  }   
//  catVaalue = document.getElementById('categoryFilter4');
//  if (catVaalue != null) {
//    if (paras!=null) {
//      paras=paras+"&cFilter=" + catVaalue.value;
//    }else{
//      paras="cFilter=" + catVaalue.value;
//    }
//  }
//  var handler="topicResult";
//  var curTopicId = document.getElementById("curTopic");
//  if (curTopicId != null) {
//    var topicIdValue = curTopicId.value;    
//    if (topicIdValue != "0") {
//      handler="topicFilterResult";
//    } 
//  }
  
  var url = assembleSessionURL(true,false);
  
  var req = newXMLHttpRequest();
  req.onreadystatechange = renderHtmlData(req, eleId);
  req.open("GET", "s?hl=tr"+url+"&"+paras , true);
  req.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
  req.send(null);
}

function url4KeywordDetails(detailsKeyId){
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
    url=url+"/"+queryString;
  }
   if (queryKeywords!=null) {
    url=url+"/"+queryKeywords;
  }
  url=url+"/"+detailsKeyId;
  return encodeURI(url);
}

function assembleSearchURL(){
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
    url=url+"/"+queryString;
  }
  return encodeURI(url);
}

function getDocumentNameValue(eleId, sep) {
  var result=null;
  var data=document.getElementsByName(eleId);
  if (data!=null) {
    for(length=0;length<data.length;length++){
       if (result==null) {
         result=data[length].value;
       }else{
         result=result+sep+data[length].value;
       }
    }
  }
  return result;
}

function assembleSessionURL(includingTFilter,includingCFilter){
  var length,url;
  url="";
  
  var data=document.getElementsByName("q");
  if (data!=null) {
    for(length=0;length<data.length;length++){
      url = url+"&q="+data[length].value;
    }
  }
  
  if (includingTFilter) {
    data=document.getElementsByName("tFilter");
    if (data!=null) {
      for(length=0;length<data.length;length++){
        url = url+"&tFilter="+data[length].value;
      }
    }
  }
  
  if(includingCFilter){
    data=document.getElementsByName("cFilter");
    if (data!=null) {
      for(length=0;length<data.length;length++){
        url = url+"&cFilter="+data[length].value;
      }
    }
  }
  
//  data=document.getElementsByName("k");
//  if (data!=null) {
//    for(length=0;length<data.length;length++){
//      url = url+"&k="+data[length].value;
//    }
//  }
  var keywordsURL = getCheckedKeywordsURL();
  if (keywordsURL != null) {
    url=url+"&"+keywordsURL;
  }
  
  return encodeURI(url);
}

function asseExSessionURL(includingTFilter,includingCFilter){
  var length,url, data;
  url="";
  
  var deprecateUserInput=document.getElementsByName("ex_dinput");
  if ((deprecateUserInput != null) && (deprecateUserInput.length > 0) 
    && (deprecateUserInput[0].value == "0")){
  } else {
	  data=document.getElementsByName("q");
	  if (data!=null) {
	    for(length=0;length<data.length;length++){
	      url = url+"&q="+data[length].value;
	    }
	  }
  }
  
  if (includingTFilter) {
    data=document.getElementsByName("ex_tFilter");
    if (data!=null) {
      for(length=0;length<data.length;length++){
        url = url+"&tFilter="+data[length].value;
      }
    }
  }
  
  if(includingCFilter){
    data=document.getElementsByName("ex_cFilter");
    if (data!=null) {
      for(length=0;length<data.length;length++){
        url = url+"&cFilter="+data[length].value;
      }
    }
  }
  
  var keywordsURL = getCheckedKeywordsURL();
  if (keywordsURL != null) {
    url=url+"&"+keywordsURL;
  }
  
//  data=document.getElementsByName("ex_k");
//  if (data!=null) {
//    for(length=0;length<data.length;length++){
//      url = url+"&k="+data[length].value;
//    }
//  }
  
  return encodeURI(url);
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