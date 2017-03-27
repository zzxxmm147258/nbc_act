
//请求路径
var Path = {
    base :get_root(),
    selectBymId : '/common/order/selectBymId.ajax',
    save :'/common/order/save.ajax'
};

function get_root(){
	var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}

var Data = {};
$(document).ready(function () {
	if(!Data){
		Data = new Object();
	}
	
	getSum();
	escorTotalFun();
	total();
	
	$('#itemAmount01').blur(function(){
		
		var prv = parseInt($(this).prev().attr('number')),
		next = parseInt($(this).next().attr('number')),
		now = parseInt($(this).val());
		if(prv <= now && now <= next){
		}else{
			alert('范围：'+ prv +'-'+ next +'人');
			$(this).val(prv);
		}
		getSum();
	});
	$('#itemAmount02').blur(function(){
		var prv = parseInt($(this).prev().attr('number')),
		next = parseInt($(this).next().attr('number')),
		now = parseInt($(this).val());
		if(prv <= now && now <= next){
		}else{
			alert('范围：'+ prv +'-'+ next +'人');
			$(this).val(prv);
		}
		escorTotalFun();
		 
	});

	$('.i-checks').each(function(){
	    	$(this).change(function(){
	    		 getSum();
	    		 total();
	    	});
	    });
	
	$(".item01 a").click(function() {
		setPoNumber(this);
		getSum();
		total();
	});
	$(".item02 a").click(function() {
		setPoNumber(this);
		escorTotalFun();
		total();
	});
	
	$('#submit').click(function(){submit()});
	
	var submit = function(){
		var userName = $('#userName').val(),
		phone = $('#phone').val();
		
		if($.trim(userName).length === 0 || $.trim(phone).length === 0){
			alert("请填写联系方式！");
			return false;
		}
		var re = /^0\d{2,3}-?\d{7,8}$/,
        reg_phone = /^1\d{10}$/;
	    if(re.test(phone) || reg_phone.test(phone)){
	    }else{
	    	alert("联系方式格式错误！");
			return false;
	    }
		Data.id = $('#orderId').val();
		Data.mId = $('#mId').val();
		Data.userName = userName;
		Data.phone = phone;
		console.log(Data);
		var param = JSON.stringify(Data)
		$('#submit').attr('disabled',true);
		$.ajax({
            url: Path.base + Path.save,
            type:'GET',
            dataType: 'json',
            data: 'datas=' + param,
            success: function(data){
                if(data.success){
                    alert('保存成功');
                    location.href=Path.base+'/common/order/list';
                }else{
                    alert(data.message);
                }
            },
            error:function(error){
                console.log(error);
                alert("保存失败,稍候重试");
            }
        });
	}
});

var totalPrice = 0;
var escorTotal = 0;
var getSum = function(){
	var o = $('input[name="price"]:checked'),
	 num = parseInt($('#itemAmount01').val()),
	 sum = 0,
	 configId = '';
	 for(var i=0; i < o.length; i++){
		 var type = $(o[i]).attr('priceType'),
		 v = parseInt($(o[i]).val());
		 
		 sum = type == '10' ? sum + num * v : sum + v; 
		 configId += i === 0 ?  $(o[i]).attr('id') : ','+ $(o[i]).attr('id');
	 }
	 $("#totalPrice").html(sum+"元");
	 var f=(sum/num).toFixed(2);
	 $("#numberPrice").html(f+"元/人");
	 $("#totalPriceIput").val(sum);
	 $("#numberPriceIput").val(f);
	 
	 Data.num = num;
	 Data.configId = configId;
	 Data.totalPrice = sum;
	 Data.numberPrice = f;
};

var escorTotalFun = function(){
	var v = parseInt($('#escorPrice').attr('escorPrice')),
	u = parseInt($('#itemAmount02').val()),
	t = v * u;
	$('#escorTotal').html(t+'元');
	$("#escorTotalIput").val(t);
	
	Data.escortTotalPrice = t;
	Data.escortNumber  = u;
	Data.escortPrice = v;
};

var total = function(){
	var totalPrice = parseInt($("#totalPriceIput").val());
	var escorTotal =parseInt($("#escorTotalIput").val());
	$('#total').html(escorTotal + totalPrice+'元');
	
	Data.total = escorTotal + totalPrice;
	
}


var setPoNumber = function(o){
	var count = $(o).attr("data"),
    number = $(o).attr('number');
	var $parent = $(o).parent(),
	$input = $parent.find('input[type="text"]');
	
	var value = $input.val();
	
	if(count == '+'){
		if(!$parent.find(".plus").hasClass("no-plus")){
			value++;
			if (value == number ) {
				$parent.find(".plus").addClass("no-plus");           
	        }
			$parent.find(".minus").removeClass("no-minus");
		}
	}else{
		if($parent.find(".minus").hasClass("no-minus")){
		}else{
			value--;
			if (value == number ) {
				$parent.find(".minus").addClass("no-minus");           
	        }
			$parent.find(".plus").removeClass("no-plus");
		}
	}
	
	$input.val(value);
}



