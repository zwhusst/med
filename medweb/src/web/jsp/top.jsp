<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>天天动 - 就医指南</title>
<%@ include file="search-header.html" %>
<script type="text/javascript" src="js/si-clear-children.js"></script> 
</head>
<body onload="initTopPage()">
<SCRIPT LANGUAGE="JavaScript">
<!--
  function initTopPage() {
    var ks = document.getElementById("allKeys").value;
    loadCMenu("catemenu",ks, '${kid}', '${cid}');
  }
  function loadHospital() {  
  	var ccIdVal = document.getElementById('curCategoryId');
  	if ((ccIdVal != null) && (ccIdVal.value !="")) {
  		loadTopHospital(ccIdVal.value,'tab-1');
  	}
  	document.getElementById('curContentType').value = "hospital";
  }
  function loadDoctor() {
    document.getElementById('curContentType').value = "doctor";
  	var ccIdVal = document.getElementById('curCategoryId');
  	if ((ccIdVal != null) && (ccIdVal.value !="")) {
  		loadTopDoctor(ccIdVal.value,'tab-2');
  	}  	
  }
//-->
</SCRIPT> 
<center>
<div id="wapper" style="text-align:left">
<%@ include file="search-box.html" %>
<input type="hidden" name="allKeys" id="allKeys" value="${keywords}"/>
<input type="hidden" name="curContentType" id="curContentType" value="hospital"/>
<div id="container" class="c clear_children">
  <div id="left_col" class="sc" >
     <div id="left-title"><h5>就医指南</h5></div>
      <span style="font-size:14px">临床科室 >> 名医名院直通车</span>
      <div id="catemenu"></div>    
  </div>
  <div id="page_content" class="pc cc_tallest">
    <div class="pg_title" >
    <ul id="flowtabs">
      <li>
      <a id="t1" class="t1"  href="javascript:loadHospital()">名院<span></span></a>
      </li>
      <li>
      <a id="t2" class="t2"  href="javascript:loadDoctor()">名医<span></span></a>
      </li>
    </ul>
    <p class="pg-p01">
      <span class="sel01">寻诊地区
      <select name="Select1" id="areasInTop" onchange="topAreaChange('tab-1')">
        <c:forEach var="a" items="${areas}">
        <option value="${a.id}" <c:if test="${a.id == 0}">selected</c:if>>${a.name}</option>
        </c:forEach>
      </select>
      </span>
    </p>    
    </div>
    <div id="flowpanes">
      <div id="tab-1" >
      <!-- hospital data here -->  
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div> 
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
      </div>
    
      <div id="tab-2">
      <!-- doctor data here -->
      </div>
    </div>
  </div>
  <%@ include file="footer.html" %>
  <center>
</body>
</html>