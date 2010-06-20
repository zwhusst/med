<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="menu" varStatus="menuStatus"  items="${categoryMenu.menus}">
<div class="left-list">
<h3>${menu.keyword.name}</h3>
<p>就诊科室:点击查名院专家</br><c:forEach varStatus="status" var="category" items="${menu.categories}">
<c:if test="${menuStatus.index == 0}">
</c:if>
<span style="white-space:nowrap" id="cId${category.id}">
<a href="#" onclick="loadTopData('${category.id}','tab-1','tab-2')">${category.name}</a></span></c:forEach>
</p>
</div>
</c:forEach>
<input type="hidden" name="curCategoryId" id="curCategoryId" value="${categoryMenu.cid}"/>