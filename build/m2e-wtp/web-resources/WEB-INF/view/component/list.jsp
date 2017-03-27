<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/view/common/head.jsp"%>

<body>
	<div class="container" style="height:650px">
		<div class="col-md-6">
			<div class="scrollWH" scrollH="100">
			<table class="table table-hover table-bordered">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						<td>dictid</td>
						<td>cname</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody name="dictid">
					<c:forEach items="${dictdefs }" var="dictdef">
						<tr dictid="${dictdef.dictid}">
							<td name="dictid">${dictdef.dictid}</td>
							<td name="cname">${dictdef.cname }</td>
							<td class="text-center">
							<a name="edit">编辑</a>&nbsp&nbsp
							<a name="remove">删除</a>
							</td>
						</tr>
					</c:forEach>
					<tr name="dictidAdd">
						<td	name="addTd" colspan=3 class="text-center">
						<a><span class="glyphicon glyphicon-plus" /></a></td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="col-md-6">
		<div class="scrollWH" scrollH="100">
		<table class="table table-hover table-bordered">
			<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						<td>code</td>
						<td>cname</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody name="code">

				</tbody>
		</table>
		</div>
		</div>
	</div>
	
	
</body>
<script type="text/javascript" src='<c:url value="/resources/js/listInput.js"/>'></script>
<script type="text/javascript">

	//操作td	
	var defControlTd = '<td class="text-center">'
	+'<a name="edit">编辑</a>&nbsp&nbsp'
	+'<a name="remove">删除</a>'
	+'</td>';
	//添加的提交按钮
	var addSubmitTd = '<td style="padding:1px"><input name="addSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
	//编辑的提交按钮
	var editSubmitTd = '<td style="padding:1px"><input name="editSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
	//参与增加和编辑事件的字段
	var defAttr = new Array("dictid","cname");
	
	//编辑，添加，删除的后台地址
	var defEditSubmitUrl = '<c:url value="/admin/dictdef/update" />';
	var defAddSubmitUrl = '<c:url value="/admin/dictdef/add" />';
	var defRemoveUrl = '<c:url value="/admin/dictdef/del" />';
	
	
		
		
	var infoAttr= new Array("code","cname");	
	var infoEditSubmitUrl = '<c:url value="/admin/dictinfo/update" />';
	var infoAddSubmitUrl = '<c:url value="/admin/dictinfo/add" />';
	var infoRemoveUrl = '<c:url value="/admin/dictinfo/del" />';
	var infoControlTd = '<td class="text-center">'
		+'<a name="edit">编辑</a>&nbsp&nbsp'
		+'<a name="remove">删除</a>'
		+'</td>';
	var defRalation = new Array("parent","code");
	var infoRalation = new Array("child","dictid"); //代表：存在关系，是子表，关联字段是"dictid"
	listInput("dictid",defAttr,defEditSubmitUrl,defAddSubmitUrl,defRemoveUrl,defControlTd,null,null,null,defRalation);
	listInput("code",infoAttr,infoEditSubmitUrl,infoAddSubmitUrl,infoRemoveUrl,infoControlTd,null,null,null,infoRalation);
	
	
	
	
	var addInfo = '<tr name="codeAdd"><td name="addTd" colspan=3 class="text-center"><a><span class="glyphicon glyphicon-plus"/></a></td></tr>';
	//点击主表，出现相应子表
	$('tbody[name=dictid]').on('click','tr',function(){
		var dictid = this.getAttribute('dictid');
		if(null != dictid){
		$.ajax({
			url:'<c:url value="/admin/dictdef/getChild"/>',
			data:{dictid:dictid},
			type:'post',
			success:function(data){
				$('tbody[name=code]').attr("dictid",dictid);
				create(data,dictid);
			},
			error:function(){
				alert("提交失败");
			}
		})
		}
	})
	
	//遍历子表并显示
	function create(data,dictid){
		var json = eval(data);
		var html;
		for(var i=0;i<json.length;i++){
			html += '<tr code='+json[i].code+'><td name="code">'+json[i].code+'</td><td name="cname">'+json[i].cname+'</td>';
			html += infoControlTd +'</tr>';
		}
		html += addInfo;
		$('tbody[name=code]').html(html);
	}

	
	
/* 	//子表的删除按钮事件
	$('tbody[name=code]').on('click','a[name=remove2]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var code = tr.getAttribute('code');
			var dictid = tr.getAttribute('dictid');
	 		$.ajax({
				url:'<c:url value="/admin/dictinfo/del" />',
				data:{code:code,dictid:dictid},
				type:'post',
				success:function(data){
					tr.remove();
				},
				error:function(){
					alert("提交失败");
				}
			}) 
		};	
	})	 */
/* 	//主表添加时的提交按钮
	$("#dictid").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var dictid = $('input[name="dictid"]').val();
		var cname = $('input[name="cname"]').val();
		if(dictid == null || dictid == ''){
			alert("dictid不能为空！");
		}else{
			$.ajax({
				url:'<c:url value="/admin/dictdef/add" />',
				data:{dictid:dictid,cname:cname},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data > 0){
						tr.remove();
						var html = '<tr dictid='+dictid+' onclick="getChild(this)"><td name="dictid">'+dictid+'</td><td name="cname">'+cname+'</td>';
						html += controlDef + '</tr>' + addDef;
						$('#dictid').append(html);
					}else{
						alert("添加失败！");
					}
				},
				error:function(){
					alert("提交失败！");
				}
			})
		}
	}) */
	/* //子表添加时的提交按钮
	$("#code").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var dictid = tr.parentNode.getAttribute('dictid');   //获取dictid
		var code = $('input[name="code"]').val();
		var cname = $('input[name="cname"]').val();
		if(code == null || code == ''){
			alert("code不能为空！");
		}else{
		$.ajax({
			url:'<c:url value="/admin/dictinfo/add" />',
			data:{dictid:dictid,code:code,cname:cname},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data > 0){
				tr.remove();
				var html = '<tr code='+code+'><td name="code">'+code+'</td><td name="cname">'+cname+'</td>';
				html += controlInfo + '</tr>' + addInfo;
				$('#code').append(html);
				}else{
					alert("添加失败！");
				}
			},
			error:function(){
				alert("提交失败！");
			}
		})
		}
	}) */
	
/* 	//主表的删除按钮事件
 	function removeDef(obj){
	if(confirm("是否确定要删除？")){
		var tr = obj.parentNode.parentNode.parentNode;
		var dictid = tr.getAttribute('dictid');
		$.ajax({
			url:'<c:url value="/admin/dictdef/del" />',
			data:{dictid:dictid},
			type:'post',
			success:function(data){
				if(data > 0){
				tr.remove();
				$('#code').html('');   // 删除页面中的子表数据
				}
			},
			error:function(){
				alert("提交失败");
			}
		})
	}
	}; */

</script>
</html>