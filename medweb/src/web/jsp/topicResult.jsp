<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="searchframe">
<div class="searchlist" ><c:forEach var="topic" items="${searchResult.avaliableTopics}">
<!--workaround to filter some topic--><c:if test="${topic.id  != 2}">
<c:set var="vTopic" value="${topic}"/>
<% com.souyibao.shared.entity.Topic t = (com.souyibao.shared.entity.Topic)pageContext.getAttribute("vTopic");
   com.souyibao.search.SearchResult sResult = (com.souyibao.search.SearchResult)request.getAttribute("searchResult");
   pageContext.setAttribute("tResult", sResult.getDataByTopic(t)); %>
<table><tr><td><h4><c:out value="${topic.name}" /></h4></td>
<c:if test="${tResult.enableCategoryFilter}">
<td>
<select onchange="cc('TopicResult')" id="categoryFilter${topic.id}">
<c:forEach var="option" items="${tResult.selectOptions}">
<option value="${option.id}" <c:if test="${option.checked}"> selected </c:if> >${option.value}</option>
</c:forEach>
</select>
</td>
</c:if>
</tr></table>
<p id="search-list05">
   <c:forEach var="singleResult" items="${tResult.result}">
   <a href="javascript:popDialog('dialog<c:out value='${singleResult.id}' />')" 
    <c:if test="${!singleResult.emptyAlias}"> 
    title="又名:<c:out value="${singleResult.alias}"/>" 
    </c:if>><c:out value="${singleResult.name}" /></a>
   </c:forEach>
   <c:if test="${tResult.execeedMaxResult}"><span><a id="Topic_${topic.id}" href="javascript:gT('${topic.id}','TopicResult')">更多...</a></span></c:if>
</p>
<!-- keyword pop dialog -->
<c:forEach var="singleResult" items="${tResult.result}"><div id="dialog<c:out value='${singleResult.id}' />" kid="<c:out value='${singleResult.id}' />" title="<c:out value='${singleResult.name}' />" loaded="false" style="display:none"></div></c:forEach>
<div class="tooltip05"></div>
</c:if><!--end--></c:forEach>
</div>
<c:if test="${empty searchResult.avaliableTopics}">
<br/>
<span style="font-size:14px">抱歉! 搜索导航暂无与之密切关联的健康信息。</span>
</c:if>
</div>

<!-- curdata from request start -->
<c:forEach var="data" items="${curData.CFilters}">
  <input type="hidden" name="cFilter" value="${data}"/>
</c:forEach>  
<c:forEach var="data" items="${curData.TFilters}">
  <input type="hidden" name="tFilter" value="${data}"/>
</c:forEach>
<c:forEach var="data" items="${curData.keywords}">
  <input type="hidden" name="k" value="${data}"/>
</c:forEach>
<c:forEach var="data" items="${curData.handlers}">
  <input type="hidden" name="hl" value="${data}"/>
</c:forEach>
<!-- curdata from request end -->

<!-- 就医指南信息 -->
<div id="dummyGuideInfo" style="display:none;">
<c:forEach var="keyword" items="${searchResult.queryKeywords}">
<!--only sympton and disease -->
<c:if test="${(keyword.topic.id == 1) || (keyword.topic.id == 4) }">
<div class="left-list">
<h3><c:out value="${keyword.name}" /></h3>
<p>就诊科室:点击查名院专家</br><c:forEach var="category" items="${keyword.categories}">
<span style="white-space:nowrap"><a href="javascript:listTd('${searchResult.topData}', '${keyword.id}', '${category.id}' )"><c:out value="${category.name}" /></a></span></c:forEach>
</p>
</div></c:if>
</c:forEach>
</div>