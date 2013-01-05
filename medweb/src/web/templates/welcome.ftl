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
    <h5>生命在于运动-健康生活天天动</h5>
    <p>

    <!--<a href="#">会员登录</a>--->
    <a href="#">设为首页</a>
    </p>
  </div>
  <center>
  <img id="main-img" src="image/logo.png" alt="天天动" />
  
<div id="main">
<table style="position: relative;top:11em;margin-left: 80px;">
  <tr>
    <td><input id="search-text" name="q" type="text" onkeyup="sHomeInput(event)"/></td>
    <td style="background: url('image/inputbt.png');background-repeat:no-repeat;width: 102px; height: 37px;"  onclick="javascript:homeSearch()"></td>
    <td><img id="google" src="image/googlelogo.png" alt="" /></td>
  </tr>
  <tr><td colspan="2" style="FONT: 12px 宋体, Arial, Helvetica, sans-serif;COLOR: #999999;line-height: 19px;">
  <p >输入您的健康查询词，天天动将智能分析您的需求并提供紧密关联的症状、<br>疾病、医院和专家等医疗健康信息，使您快速获取满意的搜索结果。</p>
  </td></tr>
</table>
<table id="site-table" style="position:relative;top:11em; border-collapse:collapse;" border="0">
  <caption style="text-align:left;" class="fav-hd">实用健康网址</caption>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://seeeye.ttdong.com.cn" target="_blank">上海市第一人民医院眼科</a></td>
    <td class="left-cell"><a class="site-name" href="http://www.kidheart.com.cn" target="_blank">复旦大学附属儿科医院新血管中心</a></td>
    <td class="right-cell"><a class="site-name" href="http://www.obgyn.renji.com" target="_blank">上海交通大学医学院附属仁济医院妇产科</a></td>
  </tr>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://www.pumch.cn" target="_blank">北京协和医院</a></td>
    <td class="left-cell"><a class="site-name" href="http://www.cd120.com" target="_blank">四川大学华西医院</a></td>
    <td class="right-cell"><a class="site-name" href="http://www.301hospital.com.cn" target="_blank">中国人民解放军总医院</a></td>
  </tr>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://www.rjh.com.cn/pages/index.shtml" target="_blank">上海交通大学医学院附属瑞金医院</a></td>
    <td class="left-cell"><a class="site-name" href="http://xjwww.fmmu.edu.cn" target="_blank">第四军医大学西京医院</a></td>
    <td class="right-cell"><a class="site-name" href="http://www.huashan.org.cn" target="_blank">复旦大学附属华山医院</a></td>
  </tr>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://www.bddyyy.com.cn" target="_blank">北京大学第一医院</a></td>
    <td class="left-cell"><a class="site-name" href="http://www.zs-hospital.sh.cn" target="_blank">复旦大学附属中山医院</a></td>
    <td class="right-cell"><a class="site-name" href="http://www.gzsums.net" target="_blank">中山大学附属第一医院</a></td>
  </tr>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://www.pkuph.cn/mass" target="_blank">北京大学人民医院</a></td>
    <td class="left-cell"><a class="site-name" href="http://www.tjh.com.cn" target="_blank">华中科技大学同济医学院附属同济医院</a></td>
    <td class="right-cell"><a class="site-name" href="http://www.fuwaihospital.org/Hospitals/Main" target="_blank">中国医学科学院阜外心血管病医院</a></td>
  </tr>
  <tr>
    <td class="left-cell"><a class="site-name" href="http://www.chhospital.com.cn" target="_blank">第二军医大学长海医院</a></td>
    <td class="full"><a class="site-name" href="http://www.9hospital.com" target="_blank">上海交通大学医学院附属第九人民医院</a></td>
    <td class='right-corner'><a class="site-name" href="http://www.shca.org.cn" target="_blank">复旦大学附属肿瘤医院</a></td>
  </tr>
  <tr>
    <td class="full"><a class="site-name" href="http://www.shsmu.edu.cn" target="_blank">上海交通大学医学院附属第六人民医院</a></td>
  </tr>
</table>
</center>
</div>
<br><br>
   <div id="footer">
	<p>&copy;2013 <a href="about.html">天天动</a> | 
	<a href="duty.html">用前必读</a> | 
	<a href="partner.html">合作伙伴</a>| 
	<a href="contact.html">联系我们</a>|
	<a href="http://www.miibeian.gov.cn" target="_blank">沪ICP备09022690号</a></p>
</div>
</body>

</html>