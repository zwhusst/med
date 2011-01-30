﻿(function(){
  var site = 'http://www.ttdong.com.cn/medweb';
  var box_label = 'Health Search';
  var box_width = 80;
  var html = [
    '<form method="post" target="_blank" action="'+site+'/s" onsubmit="javascirpt:this.service.value=window.location; this.action=\''+site+'/rs/ss/\'+encodeURI(this.qalias.value)+'+'\'?qs=\''+'+encodeURI(this.qalias.value)'+';return true;">',
    '<div style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; color: #000000">'+box_label+'</div>',
    '<input type="hidden" name="service" value="external" />',
    '<input style="vertical-align: middle; width: '+box_width+'px" type="text" name="qalias" maxlength="127" value="" />',
    '&nbsp;',
    '<input style="vertical-align: middle; width: 30px" type="submit" value="Go" />',
    '<br>',
    '<a href="'+site+'" style=" border: 0; text-decoration: none"><img style="border: 0; margin-top: 3px; vertical-align: middle" src="'+site+'/image/medweb_box.png" alt="Powered by TTDong" /></a>',
    '</form>'
  ];
  document.write(html.join(''));
})();