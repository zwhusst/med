<html>
<head>
  <title>天天动 - ${searchData.webQuery!""}</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <base href="${baseHref}" />
  <link href="image/favicon.ico" rel="shortcut icon">
  <link type="text/css" rel="stylesheet" href="style/search.css"/>  
  <link type="text/css" rel="stylesheet" href="style/layout.css"/>
  <link rel="stylesheet" href="themes/base/jquery.ui.all.css" type="text/css"/>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="js/search.js"></script>
  <script type="text/javascript" src="js/jquery.tools.min.js"></script>
  <script type="text/javascript" src="js/tooltip-1.1.2.js"></script>  
  <script type="text/javascript" src="js/menu.js"></script>
  <script type="text/javascript" src="js/med.js"></script>
  <script src="js/si-clear-children.js" type="text/javascript"></script>
  <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
  <script src="js/ui/jquery.ui.core.js" type="text/javascript"></script>
  <script src="js/ui/jquery.ui.draggable.js" type="text/javascript"></script>
  <script src="js/ui/jquery.ui.resizable.js" type="text/javascript"></script>
  <script src="js/ui/jquery.ui.dialog.js" type="text/javascript"></script>
  <script src="js/ui/jquery.ui.widget.js" type="text/javascript"></script>
  <script src="js/external/jquery.bgiframe-2.1.1.js" type="text/javascript"></script>
  <script src="js/google/jsapi.js" type="text/javascript"></script><!--http:// www.google.com/uds/jsapi-->
  <script type="text/javascript" src="js/google/uds.js?file=search&amp;v=1.0"></script><!--http:// www.google.com/uds/?file=search&amp;v=1.0-->
  <link rel="stylesheet" type="text/css" href="style/google/default.css"><!--http:// www.google.com/uds/api/search/1.0/dc3de76e47ee565996b49228b275fafb/default.css-->
<style type="text/css">
form.gsc-search-box {
  display: none;
}

#searchcontrol .gsc-control {
  WIDTH: 400px
}
div.gsc-control {
  width:100%;
}
gsc-input {
  display: none;
}
.gsc-result-info-container {
  display: none;
}
.gsc-control div {
  font-size:11pt;
  position:static;
}
div.gsc-control {
  width: 100%;
}
</style>
<script language="JavaScript">
<!--
google.load('search', '1.0');
function reqestGoogleHtml() {
  var webResultTag=document.getElementById("WebResultTag");
    
  // Create a search control
  var searchControl = new google.search.SearchControl();
  searchControl.setResultSetSize(GSearch.LARGE_RESULTSET);

  // Add in a full set of searchers
  var websearch, websearch1,search;
  websearch = new google.search.WebSearch();
  websearch.setUserDefinedLabel("网页");
  <#if searchData.outerSite??>
  websearch.setSiteRestriction("${searchData.outerSite}");
  </#if>
  searchControl.addSearcher(websearch);

  websearch1 = new google.search.WebSearch();
  websearch1.setQueryAddition("病例 ");
  websearch1.setUserDefinedLabel("病例");
  searchControl.addSearcher(websearch1);
  
  search = new google.search.ImageSearch();
  search.setUserDefinedLabel("图片");
  searchControl.addSearcher(search);

  search = new google.search.NewsSearch();
  search.setUserDefinedLabel("新闻");
  searchControl.addSearcher(search);

  search = new google.search.BlogSearch();
  search.setUserDefinedLabel("博客");
  searchControl.addSearcher(search);

  var drawOptions = new google.search.DrawOptions();
  drawOptions.setDrawMode(google.search.SearchControl.DRAW_MODE_TABBED);
  // tell the searcher to draw itself and tell it where to attach
  searchControl.draw(document.getElementById("TopicWebResult"), drawOptions);
  // execute an inital search
  searchControl.execute("${searchData.webQuery}");    
}
google.setOnLoadCallback(reqestGoogleHtml, true);
// -->
</script>
</head>
<body>
<script language="JavaScript">
$(document).ready(function() {
  try{
    initSearch();
  }catch(err) {}

  try{
    SI.ClearChildren.initialize();
  }catch(err) {}
});  
</script>  
<center>
  <div style="text-align: left;" id="wapper">
    <div id="masthead">
    <p class="top_def">
      <a href="#">就医导航</a>
      <a href="#">设为首页</a>
    </p>
    <div class="line"></div>
    <div class="head_bg"></div>
    <div class="head_img"></div>
    <div class="s_frame">
      <a href="/medweb/"><img alt="天天动" src="image/logo.png" class="logo"></a>
      <input type="text" value="${searchData.userQuery!""}" onkeyup="paneSearchKeyupHandler('${baseHref}',event)" name="querystr" class="search_in">
      <span onclick="javascript:paneSearch('${baseHref}')" title="搜索" class="search_bt"></span>
      <img alt="" src="image/googlelogo.png" class="google">  
      <div id="panecheckbox" class="panecheckbox" style='left: 320px; position: absolute;top: 75px;'>
        <#if searchData.paneKeywords??>
        <#list searchData.paneKeywords as paneKeyword>
        <input type="checkbox" id="${paneKeyword.keyword.id?c}" name="panekeyword" checked="checked">${paneKeyword.keyword.name}
        </#list>
        </#if>
      </div>
    </div>
    </div>

    <div class="c clear_children" id="container">

<div class="sc" id="left_col">
  <div id="left-title"><h5>就医指南</h5></div>
  <span style="font-size: 14px;">临床科室 &gt;&gt; 名医名院直通车</span>
  <div id="GuideInfoSection">
    <div class="left-list">
      <#if searchData.diagnoseGuides??>
      <#assign diagnoseIds=searchData.keywordIds4Diagnose!"">
      <#list searchData.diagnoseGuides as guide>
      <h3>${guide.keyword.name}</h3>
      <#if !searchData.outerSite??>
      <p>就诊科室:点击查名院专家<br>
      <#else>
      <p>就诊科室:<br>
      </#if>
      <#list guide.categories as category>
      <#if !searchData.outerSite??>
        <span style="white-space: nowrap;"><a href="${baseHref}rs/guide/hospital/${diagnoseIds}/${guide.keyword.id?c}/${category.id}" target="_blank">${category.name}</a></span>
      <#else>
        <span style="white-space: nowrap;">${category.name}</span>
      </#if>
      </#list>
      </p>
      </#list>
      </#if>
    </div>
  </div>
</div>

<div class="pc cc_tallest" id="page_content">
  <div class="pg_title"><p><span class="res-title">健康搜索导航:</span>以下是与&nbsp;<span class="results">${searchData.webQuery!""}&nbsp;</span>&nbsp;密切关联的医药信息，点击重组你的搜索条件。</p></div>
  <div style="display: block;" id="TopicResult">
    <div id="searchframe">
    <#if (searchData.topicSearchData)?? && !searchData.emptyTopicResult>
      <div class="searchlist">
      <#list searchData.topicSearchData as topicData>
        <table><tbody><tr><td><h4>${topicData.topic.name}</h4></td></tr></tbody></table>
        <#list topicData.data as data>
          <a <#if !data.emptyAlias>title="又名:${data.alias}"</#if> href="javascript:popDialog('${baseHref}','dialog${data.id}')">${data.name}</a>
        </#list>

        <#if searchData.singleTopicResult>
        <!--back to multi topic page-->
          <span><a href="${baseHref}${topicData.ctxUrl}"><<返回</a></span>
        <#else>
        <!--go to one specific topic page-->
          <#if topicData.execeedMaxResult>
          <span><a href="${baseHref}${topicData.ctxUrl}">更多...</a></span>
          </#if>
        </#if>  
        <div class="tooltip05"></div>

        <#list topicData.data as data>
        <div style="display: none;" loaded="false" title="${data.name}" kid="${data.id}" id="dialog${data.id}"></div>
        </#list>

    </#list>
      </div>
    <#else>
      <br/>
      <span class="res-title">抱歉! 搜索导航暂无与之密切关联的健康信息。</span>
    </#if>
  </div>

  <input type="hidden" name="tFilter" value="${searchData.converageTopic}"/>
  <#if searchData.outerSite??>
  <input type="hidden" name="outersite" id="outersite" value="${searchData.outerSite}"/>
  </#if>
<!-- 就医指南信息 -->
</div>

<div id="search-tabs">
  <div><p class="tabs-title"><span class="res-title">搜索结果:</span>符合<span class="results">&nbsp;${searchData.webQuery}&nbsp;&nbsp;</span>的相关网页</p></div>

  <table id="WebResultParentTag" class="vsepleft">
    <tbody><tr><td id="TopicWebResult"></td></tr>
    <tr><td>
      <div id="footer">
        <hr style="width:100%;color: #DDD;background-color: #DDD;height: 1px;border: 1;">  
        <p>&copy;2013 宜豪健康科技<a href="about.html">天天动</a> | 
        <a href="duty.html">用前必读</a> | 
        <a href="partner.html">合作伙伴</a>| 
        <a href="contact.html">联系我们</a>|
        <a target="_blank" href="http://www.miibeian.gov.cn">沪ICP备09022690号</a></p>
      </div>  
    </td></tr>  
    </tbody>
  </table>
 </div>
</div>

<div class="search_right" style="position:absolute">
  <dt class="title">快乐健康每一天!</dt>
  <dt class="pic"><img src="image/sepic.jpg"></dt>
  <dt class="advword">您的健康，我的使命</dt>
  <dt class="searchli"><a href="http://seeeye.firsthospital.cn/" target="_blank">第一人民医院眼科</a></dt>
  <dt class="searchli"><a href="http://www.kidheart.com.cn/" target="_blank">儿科医院心血管中心</a></dt>
  <dt class="searchli"><a href="http://www.obgyn.renji.com/" target="_blank">仁济医院妇产科</a></dt>
  <dt class="title" style="font-size: 13px;font-weight: normal;padding-top: 0px;text-align: center;">---全国知名权威专科---</dt>
</div>
<div style="position:absolute;right: 0px;top: 320px;text-align: left;background-color: #0D96FB;width: 165px;padding: 3px 3px 30px 3px;">
  <div style="line-height: 40px;text-align: center;color: white;"><a style="color: white;" onclick='javascript:showModalDialog("consultant.html", "consulting_dialog", {"width":710,"height": 380,"dialogClass":"consulting_dialog", "resizable": false, "title":"寻求帮助"})'>寻求更多帮助</a></div>
  <div style="font-size: 12px;color: white;padding: 5px;">您有任何困难和建议，<br>尽请告诉我们，<br>愿我们专业的个性化健康咨询服务能更好的服务您！</div>
  <div style="text-align: center;"><img src='image/ttd_wechat.jpg' style="width: 140px;padding-top:10px"/></div>
  <div style="text-align: center;color: white;font-size: 13px;padding-top: 5px;">手机扫描加关注<br>第一时间获得健康帮助<br><br>公众帐号：健康咨询-天天动<br>微信号：ttdong_com_cn</div>
</div>

</div>
</div>
</center>
<div id="consulting_dialog"></div>
</body></html>