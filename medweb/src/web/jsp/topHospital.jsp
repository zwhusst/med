<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="emptyData" value="true"/>
<c:forEach var="h" items="${thosList}">
<c:set var="emptyData" value="false"/>
<div class="pg-contlist-1" >
<h1>
  <a class="h1-a" href="${h.website}" target="blank">${h.name}</a>
  <span class="lev"><span class="lev02">${h.grade}</span>/${h.area.name}</span>
</h1>
<p>特色专科:<span class="special">
<c:forEach var="c" items="${h.categories}">
<c:out value="${c.name}"/>&nbsp;&nbsp;
</c:forEach>
</span></p>
<p class="profile">${h.description}</p>
<p>
<span class="addres">联系地址:&nbsp;${h.address}</span><span class="webaddres">联系电话:&nbsp;${h.telephone}</span>
</p>
<p>
<span style="display:none">
相关搜索:
<a href="#">评价</a>
<a href="#">门诊时间</a>
<a href="#">地图</a>
<a href="#">预约挂号</a>
</span>
<span class="more" style="display:none"><a class="down" href="#">详细介绍&gt;&gt;</a><a class="up" href="#">收回缩略&lt;&lt;</a></span>
</p>
</div>
<br/>
</c:forEach>
<c:if test="${emptyData == 'true'}">
<br/>
抱歉! 暂时没有相关信息。
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
<div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
</c:if>