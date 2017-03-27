$(function(){
	var tw = 0;
	$('.THU_width th').each(function() {
		var row = $(this).attr('rowspan');
		if (row) {
			var x = $(this).attr('width');
			tw = tw + parseInt(x);
			$(this).css('width', x + "px");
		}
	});
	
	$('.THD_width th').each(function(){
		var x = $(this).attr('width');
		tw = tw + parseInt(x);
		$(this).css('width',x+"px");
	});
	$(".dataTables_scrollHeadInner").css("width",tw);
});
	
	
	