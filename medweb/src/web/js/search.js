function initSearch() {

	
	
	
	$("#search-list01 a[rel]").overlay();

		
	$("#search-list01 a[title]").tooltip('.tooltip01');
	$("#search-list02 a[title]").tooltip('.tooltip02');
	$("#search-list03 a[title]").tooltip('.tooltip03');
	$("#search-list04 a[title]").tooltip('.tooltip04');
	$("#search-list05 a[title]").tooltip('.tooltip05');
	
	$("#searchframe02 a[title]").tooltip('.tooltip01'); 
	$("#searchframe03 a[title]").tooltip('.tooltip01'); 
	$("#searchframe04 a[title]").tooltip('.tooltip01'); 
	$("#searchframe05 a[title]").tooltip('.tooltip01');
	$("#searchframe06 a[title]").tooltip('.tooltip01');
	   
	$('#searchframe02').hide(
	);
	$('#searchframe03').hide(
	);

	$('#searchframe04').hide(
	);

	$('#searchframe05').hide(
	);
	$('#searchframe06').hide(
	);

	$('.list01').click(
		function() {
			$('#searchframe02').fadeIn('normal');
			$('#searchframe01').hide();
			
		}
	);
	$('.comeback01').click(
		function() {
			$('#searchframe01').fadeIn('normal');
			$('#searchframe02').hide();
			
		}
	);
	$('.list02').click(
		function() {
			$('#searchframe03').fadeIn('normal');
			$('#searchframe01').hide();
			
		}
	);
	$('.comeback02').click(
		function() {
			$('#searchframe01').fadeIn('normal');
			$('#searchframe03').hide();
			
		}
	);
	$('.list03').click(
		function() {
			$('#searchframe04').fadeIn('normal');
			$('#searchframe01').hide();
			
		}
	);
	$('.comeback03').click(
		function() {
			$('#searchframe01').fadeIn('normal');
			$('#searchframe04').hide();
			
		}
	);
	$('.list04').click(
		function() {
			$('#searchframe05').fadeIn('normal');
			$('#searchframe01').hide();
			
		}
	);
	$('.comeback04').click(
		function() {
			$('#searchframe01').fadeIn('normal');
			$('#searchframe05').hide();
			
		}
	);
	$('.list05').click(
		function() {
			$('#searchframe06').fadeIn('normal');
			$('#searchframe01').hide();
			
		}
	);
	$('.comeback05').click(
		function() {
			$('#searchframe01').fadeIn('normal');
			$('#searchframe06').hide();
			
		}
	);
	$('.head_img').bgiframe();	
}
$(document).ready(function() {initSearch();});