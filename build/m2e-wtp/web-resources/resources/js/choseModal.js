function choseModal(tableName,urlStr,input,fuzzy){
		var modalName = tableName+"Modal";
		/* $("#"+modalName).on("click","input[name=search]",function(){ */
		$('div[name='+modalName+']').on("click","input[name=search]",function(){
			var dataStr ="";
			for(var i=0;i<fuzzy.length;i++){    //遍历查询条件，拼出dataStr
				var fuz = $("div[name="+modalName+"] input[name="+fuzzy[i]+"]");				
				dataStr += fuzzy[i]+"="+fuz.val()+"&";
			}
			var html="";
			$.ajax({
				url:urlStr,
				data:dataStr,
				type:'post',
				success:function(data){
					var json = eval(data);
					html = getHtml(tableName,json);
					$("tbody[name="+tableName+"]").html(html);
				}
				
			})
		})
		
		$("#"+modalName).on("click","button[name=go]",function(){
			var radio = $("input[type='radio']:checked");
			for(var i=0;i<input.length;i++){         //输入框需要的字段,给主页面输入框赋值
				$("#"+input[i]).val(radio.attr("r_"+input[i]));
			}
		})
		
/*		$("#"+modalName).on("click","tr",function(){
			$(this.parentNode).find("input[type=radio]").removeAttr("checked");
			$(this).find("input[type='radio']").attr("checked",true);
		})*/
		
	}