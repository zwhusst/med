<%@ page session="false" import="com.souyibao.shared.entity.Hospital,java.util.List,com.souyibao.web.*,com.souyibao.web.taglib.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% List<Hospital> hosList = (List<Hospital>) request.getAttribute("hosList");%>
<table id="hosList" width="100%">
	<thead><tr><td>名称</td><td>医院类型</td><td>医院等级</td><td>地址</td></tr></thead>
	<tbody>
		<%=MedTagUtil.getHosListHtml(hosList)%>
	</tbody>
</table>