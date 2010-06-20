<%@ page session="false" import="com.souyibao.shared.MedEntityManager, com.souyibao.shared.entity.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib uri="http://com.medsearch.org/taglibs/search-1.0" prefix="medsearch" %>
<% String id = request.getParameter("id");Hospital hos = null;if (id != null) {hos = MedEntityManager.getInstance().getHospitalById(id);}%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title><%= hos.getName()  %></title>
<LINK href="style/medsearch.css" type="text/css" rel="stylesheet" />
<link href="style/themes/lighting.css" type="text/css" rel="stylesheet" />
<link href="style/themes/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
	
</body>
</html>