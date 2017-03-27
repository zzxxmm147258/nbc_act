/*需要页面先引用bootstrap*/
var monthStr = getStr(12);
var dayStr = getStr(30);
var hourStr = getStr(24);
var minuteStr = getStr(60);
var secondStr = getStr(60);


function getStr(num){
	var str = '';
	for(var i=0;i<num;i++){
		str += '<option value='+i+'>'+i;
	}
	return str;
}

	



var durModalStr = '<div class="modal fade" id="durTimeModal" tabindex="-1"'+
					'role="dialog" aria-labelledby="durTimeModalLabel" aria-hidden="true">'+
						'<div class="modal-dialog" style="width:900px">'+
   							'<div class="modal-content">'+
      							'<div class="modal-header">'+
         							'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
         							'<h4 class="modal-title" id="durTimeModalLabel">持续时间</h4>'+
      							'</div>'+
      							'<div class="modal-body">'+
      								'<div class="row">'+
      									'<div class="col-md-2">'+
      										'<div class="input-group">'+
      											'<input type="text" id="durYear" class="form-control">'+
      											'<span class="input-group-addon">年</span>'+
      										'</div>'+
      									'</div>'+
							      		'<div class="col-md-2">'+
							      			'<div class="input-group">'+
							      				'<select id="durMonth" class="form-control">'+
							      					monthStr+
							      				'</select>'+
							      				'<span class="input-group-addon">月</span>'+
							      			'</div>'+
							      		'</div>'+
							      		'<div class="col-md-2">'+
							      			'<div class="input-group">'+
							      				'<select id="durDay" class="form-control">'+
							      					dayStr+
							      				'</select>'+
							      				'<span class="input-group-addon">日</span>'+
							      			'</div>'+
							      		'</div>'+
							      		'<div class="col-md-2">'+
							      			'<div class="input-group">'+
							      				'<select id="durHour" class="form-control">'+
							      					hourStr+
							      				'</select>'+
							      				'<span class="input-group-addon">时</span>'+
							      			'</div>'+
							      		'</div>'+
							      		'<div class="col-md-2">'+
							      			'<div class="input-group">'+
							      				'<select id="durMinute" class="form-control">'+
							      					minuteStr+
							      				'</select>'+
							      				'<span class="input-group-addon">分</span>'+
							      			'</div>'+
							      		'</div>'+
							      		'<div class="col-md-2">'+
							      			'<div class="input-group">'+
							      				'<select id="durSecond" class="form-control">'+
							      					secondStr+
							      				'</select>'+
							      				'<span class="input-group-addon">秒</span>'+
							      			'</div>'+
							      		'</div>'+
							      	'</div>'+
							      '</div>'+
							      '<div class="modal-footer">'+
							         '<button type="button" class="btn btn-default" '+
							            'data-dismiss="modal">关闭'+
							         '</button>'+
							         '<button type="button" id="getSecond" class="btn btn-primary" data-dismiss="modal" >提交</button>'+
							      '</div>'+
							  	'</div>'+
							'</div>'+
						'</div>';
$('body').append(durModalStr);
var nowInput;
$(".durTime").click(function(){
	nowInput = $(this);
	
	$('#durTimeModal').modal('show');
	

})
	//durTimeModal模态框显示动作
 	$('#durTimeModal').on('show.bs.modal', function () {
		$("#durYear").val(0);
		$("#durMonth").val(0);
		$("#durDay").val(0);
		$("#durHour").val(0);
		$("#durMinute").val(0);
		$("#durSecond").val(0);
		}) 
	//durTimeModal模态框提交按钮动作
	$("#getSecond").click(function(){
		var year = $("#durYear").val();
		var month = $("#durMonth").val();
		var day = $("#durDay").val();
		var hour = $("#durHour").val();
		var minute = $("#durMinute").val();
		var second = $("#durSecond").val();
		var timeStr = '';
		if(year>0){
			timeStr += year+"年"
		}
		if(month>0){
			timeStr += month+"个月"
		}
		if(day>0){
			timeStr += day+"天"
		}
		if(hour>0){
			timeStr += hour+"小时"
		}
		if(minute>0){
			timeStr += minute+"分钟"
		}
		if(second>0){
			timeStr += second+"秒"
		}
		var allSecond = year*365*24*60*60+month*30*24*60*60+day*24*60*60+hour*60*60+minute*60+second;
		nowInput.val(timeStr);
		nowInput.siblings('input.durTime').val(allSecond);
		//alert(nowInput.siblings('input.durTime').val());
	})