(function(){
  var site = 'http://www.ttdong.com.cn';
  var box_width = 80;
  var html = [
    '<form style="text-align: left;" method="post" target="_blank" action="'+site+'/s" onsubmit="javascirpt:this.outersite.value=window.location; this.action=\''+site+'/rs/ss/\'+encodeURI(this.qalias.value)+'+'\'?qs=\''+'+encodeURI(this.qalias.value)'+';return true;">',	 '<p style="margin: 0;"><a href="'+site+'" style=" border: 0; text-decoration: none"><img style="height:25px;border: 0; margin-top: 3px; vertical-align: middle" src="'+site+'/image/logo.png" alt="Powered by TTDong" /></a></p>',
    '<p style="margin: 0;">',	 '<input type="hidden" name="outersite" value="external" />',
    '<input style="vertical-align: middle; width: '+box_width+'px" type="text" name="qalias" maxlength="127" value="" />',
    '&nbsp;',
    '<input style="vertical-align: middle; width: 40px" type="submit" value="搜索" />',
    '</p>',
    '</form>'
  ];
  document.write(html.join(''));
})();