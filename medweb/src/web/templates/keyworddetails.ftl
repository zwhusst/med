<p><img src="/image/triangle.gif">&nbsp;&nbsp;<a href="/${detailsData.searchAllURL}">查询&nbsp;
${detailsData.keyword.name}<#if detailsData.userQuery??>&nbsp;和&nbsp;${detailsData.userQuery}</#if>
<#if detailsData.querykeywords??>
<#list detailsData.querykeywords as querykeyword>&nbsp;和&nbsp;${querykeyword.name}</#list>
</#if>
</a></p>
<p><img src="/image/triangle.gif">&nbsp;&nbsp;<a href="/${detailsData.simpleSearchURL}">仅仅查询&nbsp;${detailsData.keyword.name}</a></p>
<hr>
<div><B>${detailsData.keyword.name} ${detailsData.keywordAlias}</B></div>
<div height="100%">${detailsData.explanation}</div>   