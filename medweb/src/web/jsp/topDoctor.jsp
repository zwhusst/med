<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="emptyData" value="true"/>
<c:forEach var="d" items="${tDoclist}">
<c:set var="emptyData" value="false"/>
<div class="pg-contlist-2">
<h1>
<a href="${d.url}" target="_blank" class="h1-a">${d.name} 医生</a>
<span class="lev">
<span class="lev02">${d.grade}</span>/${d.area}
</span>
</h1>
<p>特长:<span class="special">${d.traits}</span></p>
<p class="profile">${d.profile}</p>
<p>
<span style="display:none">
相关搜索:
<a href="#">评价</a>
<a href="#">门诊时间</a>
<a href="#">地图</a>
<a href="#">预约挂号</a>
<a href="#">咨询</a>
</span>
<span class="more" style="display:none"><a href="#" class="down">详细介绍>></a><a href="#" class="up">收回缩略<<</a></span>
</p>
</div>
</c:forEach>
<c:if test="${emptyData == 'true'}">
<br/>
抱歉! 暂时没有相关信息。
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
</c:if>