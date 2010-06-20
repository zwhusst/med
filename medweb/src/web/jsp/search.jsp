<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>天天动 - ${sp}</title>
<%@ include file="search-header.html" %>
<script type="text/javascript" src="js/si-clear-children.js"></script>
<link type="text/css" href="themes/base/jquery.ui.all.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.google.com/uds/jsapi"></script>
<script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="js/ui/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="js/ui/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="js/ui/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="js/external/jquery.bgiframe-2.1.1.js"></script>
<STYLE type=text/css>
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
.gsc-control div {
  font-size:11pt;
  position:static;
}
div.gsc-control {
	width: 100%;
}
</STYLE>
<SCRIPT LANGUAGE="JavaScript">
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
      searchControl.execute("<c:out value='${exdata.googleSearchData}' />");      
}
google.setOnLoadCallback(reqestGoogleHtml, true);

function init() {
  loadResult("TopicResult");
}
$.ui.dialog.defaults.bgiframe = true;
//-->
</SCRIPT>  
</head>
<body onload="init()">  
<center>
  <div id="wapper" style="text-align:left">
  <%@ include file="search-box.html" %>
  <!-- ex data from request start -->
  <c:forEach var="data" items="${exdata.queryStrings}">
  	<input type="hidden" name="ex_q" value="${data}"/>
  </c:forEach>  
  <c:forEach var="data" items="${exdata.DInputParas}">
  	<input type="hidden" name="ex_dinput" value="${data}"/>
  </c:forEach>
  <c:forEach var="data" items="${exdata.CFilters}">
  	<input type="hidden" name="ex_cFilter" value="${data}"/>
  </c:forEach>
  <c:forEach var="data" items="${exdata.TFilters}">
  	<input type="hidden" name="ex_tFilter" value="${data}"/>
  </c:forEach>  
  <c:forEach var="data" items="${exdata.keywordParas}">
  	<input type="hidden" name="ex_k" value="${data}"/>
  </c:forEach>    
  <!-- ex data from request end -->
  <div id="container" class="c clear_children">
    <div id="left_col" class="sc" >
      <div id="left-title"><h5>就医指南</h5></div>
      <span style="font-size:14px">临床科室 >> 名医名院直通车</span>
      <div id="GuideInfoSection"></div>
    </div>
    <div id="page_content" class="pc cc_tallest">
      <div class="pg_title" >
        <p><span class="res-title">健康搜索导航:</span>以下是与&nbsp;<span class="results"><c:out value='${exdata.displaySearchData}' escapeXml="false" /></span>&nbsp;密切关联的医药信息，点击重组你的搜索条件。 </p>
      </div>
      <div id="TopicResult" style="display: block;"></div>
      <div id="search-tabs">
        <div><p class="tabs-title"><span class="res-title">搜索结果:</span>符合<span class="results">&nbsp;<c:out value='${exdata.displaySearchData}' escapeXml="false"/>&nbsp;</span>的相关网页</p></div>
		<table class="vsepleft" id="WebResultParentTag">
			<TR><td id="TopicWebResult"></td></TR>
		</table>
      </div>
    </div>
	<!-- keywords output to search pane -->
	<div id="dummyPaneKeyword" style="display:none;">
	<c:forEach var="keyword" items="${exdata.keywords}">
	<input type="checkbox" checked="checked" name="panekeyword" id="${keyword.id}"/>${keyword.name}
	</c:forEach>
	</div>    
  </div>
    <%@ include file="footer.html" %>
  </div>
  </center>  
  </body>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
    moveFun(['dummyPaneKeyword','panecheckbox']);
  //-->
  </SCRIPT>  
</html>