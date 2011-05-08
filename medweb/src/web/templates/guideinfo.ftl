<html><head>
<title>天天动 - 就医指南</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <base href="${baseHref}" />  
  <link type="text/css" rel="stylesheet" href="style/search.css">
  <link type="text/css" rel="stylesheet" href="style/layout.css">
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="js/search.js"></script>
  <script type="text/javascript" src="js/jquery.tools.min.js"></script>
  <script type="text/javascript" src="js/tooltip-1.1.2.js"></script>  
  <script type="text/javascript" src="js/menu.js"></script>
  <script type="text/javascript" src="js/med.js"></script>

<script type="text/javascript"> 
function correctPNG() { 
    var arVersion = navigator.appVersion.split("MSIE") 
    var version = parseFloat(arVersion[1]) 
    if ((version &gt;= 5.5) &amp;&amp; (document.body.filters)) 
    { 
       for(var j=0; j&lt;document.images.length; j++) 
       { 
          var img = document.images[j] 
          var imgName = img.src.toUpperCase() 
          if (imgName.substring(imgName.length-3, imgName.length) == "PNG") 
          { 
             var imgID = (img.id) ? "id='" + img.id + "' " : "" 
             var imgClass = (img.className) ? "class='" + img.className + "' " : "" 
             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' " 
             var imgStyle = "display:inline-block;" + img.style.cssText 
             if (img.align == "left") imgStyle = "float:left;" + imgStyle 
             if (img.align == "right") imgStyle = "float:right;" + imgStyle 
             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle 
             var strNewHTML = "&lt;span " + imgID + imgClass + imgTitle 
             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";" 
             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" 
             + "(src=\'" + img.src + "\', sizingMethod='scale');\"&gt;&lt;/span&gt;" 
             img.outerHTML = strNewHTML 
             j = j-1 
          } 
       } 
    }     
} 
window.attachEvent("onload", correctPNG); 
</script>

   <link href="image/favicon.ico" rel="shortcut icon">
<script src="js/si-clear-children.js" type="text/javascript"></script> 
</head><body>
<script language="JavaScript">
<!--
  var hospitalArea;
  var doctorArea = "0";
  function showHospital() {  
    document.getElementById('guideType').value = "hospital";
    // visible the top hospital content
    document.getElementById("tab-1").style.display="block";
    document.getElementById("tab-2").style.display="none";
    $("#areaOptions").removeAttr("disabled");
    if (hospitalArea != null) {
      $("#areaOptions").attr('value',hospitalArea);
    }
    $("#t1").removeClass('tnormal').addClass('tactive');
    $("#t2").removeClass('tactive').addClass('tnormal');
  }
  
  function showDoctor() {
    document.getElementById('guideType').value = "doctor";
    // visible the top doctor content
    document.getElementById("tab-1").style.display="none";
    document.getElementById("tab-2").style.display="block";
    $("#areaOptions").attr('disabled','disabled');    
    hospitalArea = $("#areaOptions").attr('value');
    $("#areaOptions").attr('value',doctorArea);
    $("#t1").removeClass('tactive').addClass('tnormal');
    $("#t2").removeClass('tnormal').addClass('tactive');      
  }
  function loadGuideInfo(allIds,curKeywordId, curCategoryId) {
    var url="rs/guide";
    // area
    var areaId=document.getElementById('areaOptions').value;
    url=url+"/"+areaId;
    
    // guidetype
    var guideType=document.getElementById('guideType').value;
    if (guideType==null){
      guideType="hospital";
    }
    url=url+"/"+guideType;
    url=url+"/"+allIds+"/"+curKeywordId+"/"+curCategoryId;
    window.location.href="${baseHref}"+url;
  }
//-->
</script> 
<center>
<div style="text-align: left;" id="wapper">
<div id="masthead">  <p class="top_def"><a href="#">就医导航</a>     <a href="#">设为首页</a>  </p>  <div class="line"></div>  <div class="head_bg"></div>
<div class="head_img"></div>  <div class="s_frame">
  <a href="/medweb/"><img alt="天天动" src="image/logo.png" class="logo"></a>
  <input type="text" value="" onkeyup="paneSearchKeyupHandler('${baseHref}',event)" name="querystr" class="search_in">
  <a onclick="javascript:paneSearch('${baseHref}')" title="搜索" href="#" class="search_bt"></a>  
  <img alt="" src="image/googlelogo.png" class="google">  
  <div id="panecheckbox" class="panecheckbox"></div>  </div> </div>
<input type="hidden" value="${guideinfo.guideType!"hospital"}" id="guideType" name="guideType">
<div class="c clear_children" id="container">
  <div class="sc" id="left_col">
     <div id="left-title"><h5>就医指南</h5></div>
      <span style="font-size: 14px;">临床科室 &gt;&gt; 名医名院直通车</span>
      <div id="catemenu">
<#if guideinfo.diagnoseGuides??>
<#assign diagnoseIds="${guideinfo.diagnoseKeywordIds}">
<#list guideinfo.diagnoseGuides as guide>
<div class="left-list">
<h3>${guide.keyword.name}</h3>
<p>就诊科室:点击查名院专家<br>
<#list guide.categories as category>
<span id="cId9" style="white-space: nowrap; <#if (guide.keyword.id?c==guideinfo.queryKeywordId) && ((category.id?c==guideinfo.queryCategoryId)) >font-weight: bold;</#if>">
<a href="javascript:loadGuideInfo('${diagnoseIds!""}', '${guide.keyword.id?c}', '${category.id?c}')">${category.name}</a>
</span>
</#list>
</p>
</div>
</#list>
</#if>
</div></div>
  <div class="pc cc_tallest" id="page_content">
    <div class="pg_title">
    <ul id="flowtabs">
      <li>
      <a href="javascript:showHospital()" class="t1 tactive" id="t1">名院<span></span></a>
      </li>
      <li>
      <a href="javascript:showDoctor()" class="t2 tnormal" id="t2">名医<span></span></a>
      </li>
    </ul>
    <p class="pg-p01">
      <span class="sel01">寻诊地区
      <select onchange="loadGuideInfo('${guideinfo.diagnoseKeywordIds!}', '${guideinfo.queryKeywordId}', '${guideinfo.queryCategoryId}')" id="areaOptions" name="Select1">
    <#if guideinfo.areas??>
      <#list guideinfo.areas as area>
      <option <#if area.id?c==guideinfo.areaId!"0">selected</#if> value="${area.id?c}">${area.name}</option>              
      </#list>
    </#if>
      </select>
      </span>
    </p>    
    </div>
    <div id="flowpanes">
      <div id="tab-1" style="display: block;">
<#if guideinfo.hospitals??>
<#list guideinfo.hospitals as hospital>
<div class="pg-contlist-1" >
<h1>
  <a class="h1-a" href="${hospital.website}" target="blank">${hospital.name}</a>
  <span class="lev"><span class="lev02">${hospital.grade}</span>/${hospital.area.name}</span>
</h1>
<p>特色专科:<span class="special">
<#list hospital.categories as hospitalCategory>
${hospitalCategory.name}&nbsp;&nbsp;
</#list>
</span></p>
<p class="profile">${hospital.description}</p>
<p>
<span class="addres">联系地址:&nbsp;${hospital.address}</span><span class="webaddres">联系电话:&nbsp;${hospital.telephone}</span>
</p>
<p>
<span style="display:none">
相关搜索:
<a href="#">评价</a>
<a href="#">门诊时间</a>
<a href="#">地图</a>
<a href="#">预约挂号</a>
</span>
<span class="more" style="display:none"><a class="down" href="#">详细介绍&gt;&gt;</a><a class="up" href="#">收回缩略&lt;&lt;</a></span>
</p>
</div>
<br/>
</#list>
</#if>
</div>
    
<div id="tab-2" style="display: none;">
<#if guideinfo.doctors??>
<#list guideinfo.doctors as doctor>
<div class="pg-contlist-2">
<h1>

<div style="height:0px;font-size:0px;"></div>

<a href="${doctor.url}" target="_blank" class="h1-a">${doctor.name}&nbsp;医生</a>
<span class="lev">
<span class="lev02">${doctor.grade}</span>/${doctor.area}
</span>
</h1>
<p>特长:<span class="special">${doctor.traits}</span></p>
<p class="profile">${doctor.profile}</p>
<p>
<span style="display:none">
相关搜索:
<a href="#">评价</a>
<a href="#">门诊时间</a>
<a href="#">地图</a>
<a href="#">预约挂号</a>
<a href="#">咨询</a>

</span>
<span class="more" style="display:none"><a href="#" class="down">详细介绍&gt;&gt;</a><a href="#" class="up">收回缩略&lt;&lt;</a></span>
</p>
</div>
</#list>
</#if>
</div>
    </div>
  </div>
<script language="JavaScript">
<!--
  <#assign guideType=guideinfo.guideType!"hospital">
  <#if guideinfo.guideType=="hospital">
    showHospital()
  <#else>
    showDoctor()
  </#if>
//-->
</script>
  <div id="footer">
  <p>&copy;2011 <a href="about.html">天天动</a> | 
  <a href="duty.html">用前必读</a> | 
  <a href="partner.html">合作伙伴</a>| 
  <a href="contact.html">联系我们</a>|
  <a target="_blank" href="http://www.miibeian.gov.cn">沪ICP备09022690号</a></p>

  <center>
</center></div></div></div></center></body></html>