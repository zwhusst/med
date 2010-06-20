<%@ page session="false" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><%@ include file="index-header.html" %>
<body>
<div id="wapper">
  <!--<div id="title-bg" ><img src="image/image04.png" alt=""/></div>--->
  <div id="header">
      <ul>
        <li>
			<!-- <a href="#">&nbsp;&nbsp;&nbsp;就医导航</a>--->
        </li>
      </ul>
    <h5>生命在于运动-健康生活天天动</h5>
    <p>
    <!--<a href="#">会员登录</a>--->
    <a href="#">设为首页</a>
    </p>
  </div>

  <center>
  <img id="main-img" src="image/logo.png" alt="天天动" />
  </center>
<div id="main">
            
        <table style="position:absolute;top:11.3em;margin-left:300px;"><tr><td><input id="search-text" name="q"  type="text" onkeyup="sHomeInput(event)"/></td>
        <td style="background: url('image/inputbt.png');background-repeat:no-repeat;width: 102px; height: 37px;"  onclick="javascript:homeSearch()"></td>
        <td><img id="google" src="image/googlelogo.png" alt="" /></td></tr>
	</table>
        <p id="search-des">输入您的健康查询词，天天动将智能分析您的需求并提供紧密关联的症状，疾病，医院和专家等医疗健康信息，使您快速获取满意的搜索结果。</p>
	
  </div>

<!--
	<div id="main">
			<img id="main-img" src="image/logo.png" alt="天天动" />
			
			<input id="search-text" name="Text1" type="text" />
			<a id="search-bt" href="#" title="搜索">健康搜索</a>
			<img id="google" src="image/googlelogo.png" alt="" />
			<p id="search-des">输入您的健康查询词，天天动将智能分析您的需求并提供紧密关联的症状，疾病，医院和专家等医疗健康信息，使您快速获取满意的搜索结果。</p>
	</div>

-->

   <%@ include file="footer.html" %>
</div>
</body>
</html>