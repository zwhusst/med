﻿(function(){
  var site = 'http://www.ttdong.com.cn/medweb';
  var box_label = 'Search for Health Information';
  var box_width = 165;
  var html = [
    '<form method="get" action="'+site+'/s" target="_blank">',
    '<div style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; color: #000000">'+box_label+'</div>',
    '<input type="hidden" name="service" value="external" />',
    '<input type="hidden" name="hl" value="search" />',
    '<input style="vertical-align: middle; width: '+box_width+'px" type="text" name="q" maxlength="127" value="" />',
    '&nbsp;',
    '<input style="vertical-align: middle; width: 30px" type="submit" value="Go" />',
    '&nbsp;',
    '<a href="'+site+'" style=" border: 0; text-decoration: none"><img style="border: 0; vertical-align: middle" src="'+site+'/image/medweb_box.png" alt="Powered by TTDong" /></a>',
    '</form>'
  ];
  document.write(html.join(''));
})();