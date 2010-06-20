<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p><img src="image/triangle.gif"><a href="s?hl=search<c:out value='${ep.fullUrl}' />">查询
<c:forEach var="data" items="${ep.fullUrlText}" varStatus="status">
<c:if test="${status.index > 0}">&nbsp;和&nbsp;</c:if>${data}
</c:forEach>
</a></p>
<p><img src="image/triangle.gif"><a href="s?hl=search&dinput=0<c:out value='${ep.focusUrl}' />">仅仅查询&nbsp;<c:out value='${ep.keyword.name}' /></a></p>
<hr>
<div><B><c:out value='${ep.keyword.name}' /><c:out value='${ep.alias}' /></B></div>
<div height="100%"><c:out value='${ep.explanation}' escapeXml="false"/></div>    
