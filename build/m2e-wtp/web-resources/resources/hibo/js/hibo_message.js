(function(win,doc){
	/**
		$Message.confirm('这是一个confirm',function(){确认函数},function(){取消函数});
		$Message.alert('这是一个alset',function(){确认函数});
	*/
	$Message = {//选择提示框处理初始化
		style : doc.write('<style type="text/css">.message {width: 100%;height: 100%;position: absolute;top:0;left:0;z-index: 1000000000;font-size:16px;}.message .message-mark {width: 100%;height: 100%;position: absolute;top:0;left:0;z-index: 200;background-color: black;opacity:0.5;}.message .message-box {width: 260px;position: absolute;z-index: 201;top: 50%;left:50%;margin-left:-130px;margin-top:-120px;border-radius:5px;background-color: #F1F2F3;}.message .message-box .text {width: 80%;text-align: center;margin: 30px 10%;}.message .message-box .btn1 {float: left;width: 129px;text-align: center;height: 40px;line-height:40px;}.message .message-box .btn2 {float: left;width: 258px;text-align: center;height: 40px;line-height:40px;}.message .message-box .sure {border-top:1px #E4E8EA solid;color: #444444;border-right:1px #E4E8EA solid;}.message .message-box .calnce{border-top:1px #E4E8EA solid;color: #727272;border-left:1px #E4E8EA solid;}.message .message-box-btn-on {color:#0094ea;}.message .message-box .btn1 span {cursor: pointer;}.message .message-box .btn2 span {cursor: pointer;}</style>'),
		utils : function(text,sureFn,calnceFn,sureText,calnceText){
			var message = $('<div class="message"><div class="message-mark"></div></div>').appendTo(document.body);
			message[0].addEventListener('touchmove',function(e){e.preventDefault();},true);
			var box = $('<div class="message-box"><div class="text">'+text+'</div></div>').appendTo(message);
			var sure = $('<div class="'+(calnceFn?'btn1':'btn2')+' sure"><span>'+sureText+'</span></div>').appendTo(box);
			sure.children('span').click(function(){
				if(sureFn){
					sureFn(this);
				}
				$(this).closest('.message').remove();
			}).mouseover(function(){
				$(this).addClass('message-box-btn-on');
			}).mouseout(function(){
				$(this).removeClass('message-box-btn-on');
			});//初始化确定按钮事件
			var calnce = null;
			if(calnceFn){
				calnce = $('<div class="btn1 calnce"><span>'+calnceText+'</span></div>').appendTo(box);
				calnce.children('span').click(function(){
					calnceFn(this);
					$(this).closest('.message').remove();
				}).mouseover(function(){
					$(this).addClass('message-box-btn-on');
				}).mouseout(function(){
					$(this).removeClass('message-box-btn-on');
				});//初始化取消按钮事件
			}
			return {message:message,btnSure:sure,btnCalnce:calnce};
		},
		/**
		 * text:提示内容
		 * sureFn：确定回调函数
		 * calnceFn 取消回调函数
		*/
		confirm : function(text,sureFn,calnceFn,sureText,calnceText){
			if('string' == typeof (sureFn)){
				if('string' == typeof (calnceFn)){
					calnceText = calnceFn;
					calnceFn = new Function();
				}
				sureText = sureFn;
				sureFn = new Function();
			}else{
				if('string' == typeof (calnceFn)){
					calnceText = sureText;
					sureText = calnceFn;
					calnceFn = new Function();
				}
			}
			return this.utils(text,sureFn?sureFn:new Function(),calnceFn?calnceFn:new Function(),sureText?sureText:'确定',calnceText?calnceText:"取消");
		},
		alert : function(text,sureFn,sureText){
			if('string' == typeof (sureFn)){
				sureText = sureFn;
				sureFn = new Function();
			}
			return this.utils(text,sureFn?sureFn:new Function(),null,sureText?sureText:'确定',"取消");
		}
	}
})(window,document)
