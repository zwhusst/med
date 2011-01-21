<html><head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link type="text/css" rel="stylesheet" href="style/main.css" />
<script src="js/med.js" type="text/javascript"></script>

<script type="text/javascript"> 
function correctPNG() { 
    var arVersion = navigator.appVersion.split("MSIE") 
    var version = parseFloat(arVersion[1]) 
    if ((version >= 5.5) && (document.body.filters)) 
    { 
       for(var j=0; j<document.images.length; j++) 
       { 
          var img = document.images[j] 
          var imgName = img.src.toUpperCase() 
          if (imgName.substring(imgName.length-3, imgName.length) == "PNG") 
          { 
             var imgID = (img.id) ? "id='" + img.id + "' " : "" 
             var imgClass = (img.className) ? "class='" + img.className + "' " : "" 
             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' " 
             var imgStyle = "display:inline-block;" + img.style.cssText 
             if (img.align == "left") imgStyle = "float:left;" + imgStyle 
             if (img.align == "right") imgStyle = "float:right;" + imgStyle 
             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle 
             var strNewHTML = "<span " + imgID + imgClass + imgTitle 
             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";" 
             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" 
             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>" 
             img.outerHTML = strNewHTML 
             j = j-1 
          } 
       } 
    }     
} 
window.attachEvent("onload", correctPNG); 
</script>

<link rel="shortcut icon" href="image/favicon.ico" />
<title>天天动</title>
</head>
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
   <div id="footer">

	<p>&copy;2011 <a href="about.html">天天动</a> | 
	<a href="duty.html">用前必读</a> | 
	<a href="partner.html">合作伙伴</a>| 
	<a href="contact.html">联系我们</a>|
	<a href="http://www.miibeian.gov.cn" target="_blank">沪ICP备09022690号</a></p>

</div>
</body>

</html>