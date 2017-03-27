function listInput(tbodyName,attr,editSubmitUrl,addSubmitUrl,removeUrl,controlTd,inputTd,addSubmitTd,editSubmitTd,ralation){
		var addTrName = tbodyName + "Add";
		var addTr = '<tr name='+addTrName+'><td name="addTd" colspan='+attr.length+1+' class="text-center"><a><span class="glyphicon glyphicon-plus"/></a></td></tr>';
		
		if(controlTd == null){
			controlTd = '<td class="text-center">'
				+'<a name="edit"><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/>'
				+'<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
				+'</td>'
		}
		if(addSubmitTd == null){
			//添加的提交按钮
			addSubmitTd = '<td style="padding:1px"><input name="addSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
		}
		if(editSubmitTd==null){
			//编辑的提交按钮
			editSubmitTd = '<td style="padding:1px"><input name="editSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
		}
		
		//最后一行的添加按钮
		$("tbody[name="+tbodyName+"]").on("click","tr[name="+addTrName+"] td[name=addTd]",function(){
			var html;
			if(!inputTd){
				for(var i=0;i<attr.length;i++){
					html += '<td style="padding:1px"><input id='+attr[i]+'></td>';
				}
			}else{
				html = inputTd;
			}
			html += addSubmitTd;
			var oo = $('tr[name='+addTrName+']');
			change(oo[0]);
			backHtml = oo.html();
			backObj = oo;
			oo.html(html);
		})
		
		//编辑图标按钮
		$("tbody[name="+tbodyName+"]").on("click","a[name=edit]",function(){
			var tr = this.parentNode.parentNode;
			var attrValue = new Array();
			var trValue = tr.getAttribute(tbodyName);
			var html;
			for(var i=0;i<attr.length;i++){
				attrValue[i] = $('tr['+tbodyName+'='+trValue+'] td[name='+attr[i]+']').html();
				if(!inputTd){
					html += '<td style="padding:1px"><input id='+attr[i]+' value='+attrValue[i]+'></td>'
				}else{
					html = inputTd;
				}
			}
			html += editSubmitTd;
			var oo = $('tr['+tbodyName+'='+trValue+']');
			change(oo[0]);
			backHtml = oo.html();
			backObj = oo;
			oo.html(html);
			if(inputTd){
				for(var i=0;i<attr.length;i++){
					$("#"+attr[i]).val(attrValue[i]);
				}
			}
		})
		
	//编辑的提交按钮事件
	$("tbody[name="+tbodyName+"]").on('click','input[name=editSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var attrValue = new Array();
		var trValue = tr.getAttribute(tbodyName);
		var dataString = 'old_'+tbodyName+'='+trValue;
		if(null != ralation && 'child' == ralation[0]){
			dataString += '&'+ralation[1]+'='+tr.parentNode.getAttribute(ralation[1]);
		}
		var html;
		for(var i=0;i<attr.length;i++){
			attrValue[i] = $('#'+attr[i]).val();
			dataString += '&'+attr[i]+'='+attrValue[i];
			html += '<td name='+attr[i]+'>'+attrValue[i]+'</td>';
		}
		if(tbodyName==attr[0] && (null == attrValue[0] || '' == attrValue[0])){
			alert(attr[0]+"不能为空！");
		}else{
		$.ajax({
			url:editSubmitUrl,
			data:dataString,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data > 0){
					html += controlTd;
					$('tr['+tbodyName+'='+trValue+']').html(html);
					if(tbodyName==attr[0]){
						tr.setAttribute(tbodyName,attrValue[0]);
					}
					backObj = null;
				}else{
					alert("修改失败！");
				}
			},
			error:function(){
				alert("提交失败！");
			}
		})
		}
	})
		
	//添加时的提交按钮
	$("tbody[name="+tbodyName+"]").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var attrValue = new Array();
		var dataString = '';
		var html='';
		if(null != ralation && 'child' == ralation[0]){
			dataString = ralation[1]+'='+tr.parentNode.getAttribute(ralation[1])+'&';
		}
		for(var i=0;i<attr.length;i++){
			attrValue[i] = $('#'+attr[i]).val();
			if(i==0){
				dataString +=attr[i]+'='+attrValue[i];
			}else{
				dataString += '&'+attr[i]+'='+attrValue[i];
			}
			 html += '<td name='+attr[i]+'>'+attrValue[i]+'</td>'
		}
		
		if(tbodyName==attr[0] && (null == attrValue[0] || '' == attrValue[0])){
			alert(attr[0]+"不能为空！");
		}else{
		$.ajax({
			url:addSubmitUrl,
			data:dataString,
			type:'post',
			success:function(data){
				 tr.remove();
				 html ='<tr '+tbodyName+'='+ data +'>' + html + controlTd + '</tr>' + addTr; 
				 $("tbody[name="+tbodyName+"]").append(html);
				/*window.location.reload();*/
			},
			error:function(){
				alert("提交失败！");
			}
		})
		}
	})
		
	//删除按钮事件
	$("tbody[name="+tbodyName+"]").on("click","a[name=remove]",function(){
	if(confirm("是否确定要删除？")){
		var tr = this.parentNode.parentNode;
		var trValue = tr.getAttribute(tbodyName);
		var dataString = tbodyName+"="+trValue;
		if(null != ralation && 'child' == ralation[0]){
			dataString += '&'+ralation[1]+'='+tr.parentNode.getAttribute(ralation[1]);
		}
		$.ajax({
			url:removeUrl,
			data:dataString,
			type:'post',
			success:function(data){
				if(data > 0){
				tr.remove();
				if(ralation&&"parent"==ralation[0]){
					$(ralation[1]).html('');   // 删除页面中的子表数据
				}
				}
			},
			error:function(){
				alert("提交失败");
			}
		})
	}
	});
		
		//点击别的行时关闭input
		var backObj = null;
		var backHtml = null;
		var b = true;
		$(document).on("click","tr",function(){
			if(null!=backObj){
				if(this!=backObj[0]){
					b=true;
				}else{
					b=false;
				}
			}
			change(this);
		});
	/* 	$(document).on("click","body",function(){
			change(this);
		}); */
		function change(obj){
			if(null!=backObj){
				if(b&&obj!=backObj[0]){
					$(backObj[0]).html(backHtml);
					backObj=null;
					backHtml = null;
				}else{
					b=true;
				}
			}
		}
		
	}