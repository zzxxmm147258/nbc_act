/**
 * <p>标题：插件</p>
 * <p>功能：插件</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月2日 上午9:38:00</p>
 * <p>类全名：hibo_validate.js</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
$.extend({
	/**
	 $.ImgUpParams.then({
	    btn : '#button',//触发事件按钮
	    isHideBtn : false,//图片超过张数是否隐藏按钮
	    imgClass : 'imgClass',//显示图片框的class
	    delImgUrl:'',
	    loadImgUrl:'',
	    allBefore : function(){//处理开始前
	    },
	    imgNum : 3,//最多上传张数
	    imgNumFn : function(data){//自定义处理张数限制
	    	data.imgNum;
	    	data.count;
	    	alert('最多选3张');
	    },
	    oneBefore : function(data){//每张图片处理开始前
	    	data.id;
	    	data.file;
	    	oneDelete(data.id);//删除当前图片方法
	    },
	    showDiv : '.showImg',//装载显示图片的div
	    success : function(data){//单张处理成功函数
	    	data.id;
	    	data.data;
	    },
	    complete : function(data){//全部处理完成
	    	data.map
	    }
	});
	*/
    ImgUpParams : function(){
    	var pMap = new $.HMap();
    	var len = 0;
        this.then = function(s){
            $(s.btn).click(function(){
            	if(s.allBefore){
                	if(s.allBefore()!=undefined){
                		return;
                	}
                }
                var input = $('<input type="file" style="display:none;" accept="image/**;capture=camera">').appendTo(document.body);
                $(input).change(function() {
                    var files = this.files;
                    len = len + files.length;
                    if(s.imgNum){
                        if((len)>s.imgNum){
                            if(s.imgNumFn){
                                return s.imgNumFn({imgNum:s.imgNum,count:len});
                            }else{
                            	return alert('最多选'+s.imgNum+'张');
                            }
                        }
                        if((len)>=s.imgNum&&s.isHideBtn){
                            $(s.btn).hide();
                        }
                    }
                    var imgClass = s.imgClass?'class='+s.imgClass:'';
                    for (var i = 0; i < files.length; i++) {
                    	var id = $.Random.getRanStr(16);
                    	if(pMap.containsKey(id)){
                    		len--;
                    		continue;
                    	}
                    	if(s.oneBefore){
                        	s.oneBefore({id:id,file:files[i]});
                        }
                        if(s.showDiv){
	                        var showB = $('<div id="'+id+'b" '+imgClass+' style="position:relative;"><img id="'+id+'img" src="'+$.Url(s.loadImgUrl?s.loadImgUrl:'/resources/image/sysimg/load.gif')+'" style="width:100%;height:100%;"><i id="'+id+'" style="z-index: 100;background: url('+$.Url(s.delImgUrl?s.delImgUrl:'/resources/image/sysimg/jiao.png')+') no-repeat;background-size:100% 100%;right: 0;top:0;position: absolute;display: block;font-style: normal;"></i></div>');
	                        $(showB).appendTo(s.showDiv);
	                        var bWidth = $('#'+id+'b').width()/4+'px';
	                        $('#'+id).css({"width":bWidth,'height':bWidth}).click(function(){
	                            $('#'+this.id+'b').remove();
	                            pMap.remove(this.id);
	                            len--;
	                            if((pMap.size()==len)&&s.complete){
	                            	s.complete({map:pMap});
	                            }
	                            $(s.btn).show();
	                        });
                        }
                        $.ImgUtils.zip({
                    		file : files[i],
                    		id:id,
                    		width : s.width,
                    		height : s.height,
                    		success : function(data){
                    			var id = data.id;
	                            pMap.put(id,data.base64);
	                            $('#'+id+'img').attr("src",data.base64);
	                            input.remove();
	                            if(s.success){
	                                s.success({id:id,data:data});
	                            }
	                            if((pMap.size()==len)&&s.complete){
	                            	s.complete({map:pMap});
	                            }
                    		}
                        });
                    };
                });
                $(input).click();
            });
		    oneDelete = function(id){
		    	if(!id){
		    		throw new error("id can't empty");
		    	}
		    	pMap.remove(id);
		    	len--;
		        $(s.btn).show();
		    }
        }
        return this;
    }(),
    /*$.SendSms({
	wait:30,//时间
	timeBox:$('.times'),//秒数倒数
	code:$('.code'),//验证码输入框
	message:'message',//信息框可以是函数、class、id
	phone:$('.userPhone'),//电话号
	sbtn:$('.send'),//点击发送的按钮
	tempNo:1008,//模板号
	url:$.RootPath+'/common/sms/ajaxSendSMS'//发送地址
	});*/
	SendSms:function(s){
		if(s.message&&typeof(s.message)=='function'){
		}else if(typeof(s.message)=='string'){
			s.message = function(text){
				$(s.message).html(text);
			}
		}else{
			var color = s.color?s.color:'white';
			var msgDiv='';
			if($.Browser.versions.mobile){
				msgDiv+='<div id="smsError" style="width:6rem;height:.88rem;position:fixed;left:10%;top:10%;z-index:10;display:none;">';
				msgDiv+='<div id="sms_error_mask" style="background:#000;opacity: .6;filter: alpha(opacity=60%);width:100%;height:100%;position:absolute;left:0;top:0;"></div>';
				msgDiv+='<p id="smsMessage" style="margin:0;width:100%;height:100%;position:absolute;left:0;top:0;font:.32rem/.88rem \'微软雅黑\';color:'+color+';text-align:center;"></p>';
				msgDiv+='</div>';
			}else{
				msgDiv+='<div id="smsError" style="width:100%;height:50px;position:fixed;top:30%;z-index:999;display:none;">';
				msgDiv+='<div id="sms_error_mask" style="background:#000;opacity: .6;filter: alpha(opacity=60%);height:100%;position:absolute;left:0;top:0;"></div>';
				msgDiv+='<p id="smsMessage" style="margin:0;position:absolute;left:0;top:11px;font:18px \'微软雅黑\';color:'+color+';text-align:center;vertical-align:middle;"></p>';
				msgDiv+='</div>';
			}
			$(msgDiv).appendTo(document.body)
			s.message = function(text){
				$('#smsError').fadeIn(0,function(){
					var zWidth = $('#smsError').show().width();
					var width = $('#smsMessage').text(text).width();
					$('#sms_error_mask').width(width+40);
					var l = (zWidth-width)/2;
					$('#smsMessage').css('left',l);
					$('#sms_error_mask').css('left',(l-20));
					$('#smsError').fadeOut(3000);
			    });
			}
		}
		s.canSend = true;
		time = function(timeBox,vals,code) {//time是时间$('.time') 、val是获取里面的$('.code')、btn值的是输入手机号的那个input
		            if (s.wait == 0) {
		            	if(code.length>0){
		            		//code.attr('disabled','disabled');
		            	}
		            	if(vals.length>0){
			                vals.val("发送验证码")
			                vals.text('重新发送');
		                }
		            	if(timeBox.length>0){
		            		timeBox.hide();
		            	}
		            	s.canSend = true;
		            } else { 
		            	if(code.length>0){
		            		code.removeAttr('disabled');
		            	}
		            	if(timeBox.length>0){
		            		timeBox.text(s.wait + 's');
		            	}
		            	s.wait--;
		                setTimeout(function() {
		                	time(timeBox,vals,code);
		                },1000)
		            }
		};
		
		//点击验证码发送请求
		$(s.sbtn).click(function(){
			s.wait = s.wait?s.wait:60;
			if(!s.canSend){
				s.message("验证码已发送,"+waitTime+"s后可再次发送!");
				return;
			}
			var userPhone = $(s.phone).val();
			if($.DataCheck.testPhone(userPhone)){
				s.canSend = false;
				var url=s.url?s.url:$.RootPath+'/common/bas/sms/ajaxSendSMS';
				var type = s.type?type:'post';
				var tempNo = s.tempNo?s.tempNo:1008;
				$.ajax({
					url : url,
					type : type,
					data : {tempNo:tempNo,phone:userPhone},
					dataType : 'json',
					success : function(data) {
						if(data&&data.success){
							$(s.timeBox).show();
							time($(s.timeBox),$(s.sbtn),$(s.code));
						}else if(data){
							s.canSend = true;
							$(s.timeBox).hide();
							s.message(data.message);
						}else{
							s.message("短信发送失败!");
						}
					},
					error:function(e){
						s.canSend = true;
					}
				});
			}else{
				$(s.times).hide();
				s.message("请输入正确手机号!");
			}
		});
	},
	Mask : function(obj){
		var s = '<style type="text/css">'+
		'.spinners{'+
		'	display: none;'+
		'	width: 100%;'+
		'	height: 100%;'+
		'	background: #000;'+
		'	opacity: 0.5;'+
		'	top: 0;'+
		'	z-index: 999;'+
		'   position: fixed;'+
		'}'+
		'.spinner {'+
		'  top: 50%;'+
		'  left: 50%;'+
		'  margin-left: -.5rem;'+
		'  margin-top: -.5rem;'+
		'  width: 1rem;'+
		'  height: 1rem;'+
		'  position: absolute;'+
		'}'+
		'.container1 > div, .container2 > div, .container3 > div {'+
		'  width: .2rem;'+
		'  height: .2rem;'+
		'  background-color: #ffffff;'+
		'  border-radius: 100%;'+
		'  position: absolute;'+
		'  -webkit-animation: bouncedelay 1.2s infinite ease-in-out;'+
		'}'+
		'.spinner .spinner-container {'+
		'  position: absolute;'+
		'  width: 100%;'+
		'  height: 100%;'+
		'}'+
		'.container2 {'+
		'  -webkit-transform: rotateZ(45deg);'+
		'  transform: rotateZ(45deg);'+
		'}'+
		'.container3 {'+
		'  -webkit-transform: rotateZ(90deg);'+
		'  transform: rotateZ(90deg);'+
		'}'+
		'.circle1 { top: 0; left: 0; }'+
		'.circle2 { top: 0; right: 0; }'+
		'.circle3 { right: 0; bottom: 0; }'+
		'.circle4 { left: 0; bottom: 0; }'+
		'.container2 .circle1 {'+
		'  -webkit-animation-delay: -1.1s;'+
		'}'+
		'.container3 .circle1 {'+
		'  -webkit-animation-delay: -1.0s;'+
		'}'+
		'.container1 .circle2 {'+
		'  -webkit-animation-delay: -0.9s;'+
		'}'+
		'.container2 .circle2 {'+
		'  -webkit-animation-delay: -0.8s;'+
		'}'+
		'.container3 .circle2 {'+
		'  -webkit-animation-delay: -0.7s;'+
		'}'+
		'.container1 .circle3 {'+
		'  -webkit-animation-delay: -0.6s;'+
		'}'+
		'.container2 .circle3 {'+
		'  -webkit-animation-delay: -0.5s;'+
		'}'+
		'.container3 .circle3 {'+
		'  -webkit-animation-delay: -0.4s;'+
		'}'+
		'.container1 .circle4 {'+
		'  -webkit-animation-delay: -0.3s;'+
		'}'+
		'.container2 .circle4 {'+
		'  -webkit-animation-delay: -0.2s;'+
		'} '+
		'.container3 .circle4 {'+
		'  -webkit-animation-delay: -0.1s;'+
		'}'+
		'@-webkit-keyframes bouncedelay {'+
		'  0%, 80%, 100% { -webkit-transform: scale(0.0) }'+
		'  40% { -webkit-transform: scale(1.0) }'+
		'}'+
		'</style>'+
		'<div class="spinners">' +
			'<div class="spinner">' +
			'  <div class="spinner-container container1">' +
			'	<div class="circle1"></div>' +
			'	<div class="circle2"></div>' +
			'	<div class="circle3"></div>' +
			'	<div class="circle4"></div>' +
			'</div>' +
			'<div class="spinner-container container2">' +
			'	<div class="circle1"></div>' +
			'	<div class="circle2"></div>' +
			'	<div class="circle3"></div>' +
			'	<div class="circle4"></div>' +
			'</div>'+
			'<div class="spinner-container container3">' +
			'	<div class="circle1"></div>' +
			'	<div class="circle2"></div>' +
			'	<div class="circle3"></div>' +
			'	<div class="circle4"></div>' +
			'</div>' +
	    '</div>	';
		show = function(){
			this.isShow = true;
			this.show();
		};
		hide = function(){
			this.isShow = false;
			this.hide();
		}
		if(!obj){
			obj = document.body;
		}
		var d = $(s).appendTo($(obj));
		d['isShow'] = false;
		return d;
	},
});