<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table width="100%">
	<c:forEach var="doc" items="${doclist}"><tr>
		<td><c:out value="${doc.name}" /></td>
		<td><c:out value="${doc.area}" /></td>
		<td><c:out value="${doc.grade}" /></td>
		<td><c:out value="${doc.hospitalName}" /></td>
	</tr></c:forEach>
</table>
