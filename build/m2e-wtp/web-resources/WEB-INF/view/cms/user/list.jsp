<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link href="${prc}/resources/html/cms/css/bootstrap.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/style.css" rel="stylesheet">
</head>

<body style="background:#fff">
    <div class="gray-bg dashbard-1">
        <div class="row wrapper white-bg page-heading">
            <div class="col-lg-9 fl">
                <h2>用户管理</h2>
                <ol class="breadcrumb" style="font-size:14px">
                    <li>
                        <a href="#">用户管理</a>
                    </li>
                    <li>
                        <strong>会员设置</strong>
                    </li>
                </ol>
            </div>

            <!-- 添加管理员按钮 -->
            <div class="col-lg-3 fr" style="margin-top:33px;">
                <a href="#" style="margin-right:12px" data-toggle="modal" data-target="#addUser01">
                      <button class="btn btn-md btn-primary  current_a fr" style="margin-right:50px">添加会员</button>
                </a>
            </div>
        </div>

        <!-- table表格 -->
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row col-lg-12">
                <div class="ibox-content" style="overflow:hidden; border:none; padding: 0;">
                    <table class="table table-striped table-bordered table-hover tableCenter " id="editable">
                        <thead>
                            <tr>
                                <th>账号</th>
                                <th>真实姓名</th>
                                <th>密码</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${data}" var="user" varStatus="vs">
	                            <tr id="${user.id }" class="gradeA">
	                                <td>${user.userName}</td>
	                                <td>
	                                    <div class="inputName">
	                                        <input id="trueName${vs.count}" type="text" name="trueName" class="form-control  inputEdit   fl" value="${user.trueName}" >
	                                        <label for="trueName${vs.count}" class="fl"><i class="fa  fa-edit  font_26 color2"></i></label>
	                                        <!-- 注意此处input的id名需要变化，保证每个input的id都不一样，lable的for="input的id名"，input和lable是一 一对应的，因此每一个input的id都要不同，其对应的 label的for值==input的id值-->
	                                    </div>
	                                </td>
	                                <td>
	                                    <div class="inputName">
	                                        <input id="passwd${vs.count}" type="text" name="password" class="form-control  inputEdit   fl" value="${user.password}">
	                                        <label for="passwd${vs.count}" class="fl"><i class="fa  fa-edit  font_26 color2"></i></label>
	                                    </div>
	                                </td>
	                                <td>
	                                    <div class="checkbox   marginClear">
	                                        <a href="javascript:show_remove('${user.id }');" data-toggle="modal">删除</a>
	                                    </div>
	                                </td>
	                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- 分页器 -->
                    <div class="btn-group  fr">
                        <ul id="pagination">
          
                        </ul>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- 删除  提示弹窗内容如下 -->
    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    	<input type="hidden" id="del_id"/>
        <div class="modal-dialog" style="width:400px">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="min-height: 110px; border:0">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h5 class=" font_18">删除后不可恢复，确定删除？</h5>
                </div>
                <div class="modal-footer" style=" border:0; padding-bottom: 40px;">
                    <button type="button" class="btn btn-white" data-dismiss="modal" style="width:100px">关闭</button>
                    <button onclick="remove()" type="button" class="btn btn-primary"  style="width:100px; margin:0 50px 0 50px;">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 添加管理员弹窗 -->
    <div class="modal inmodal" id="addUser01" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width:400px">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="min-height: 110px; border:0">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <form class="m-t-xl" role="form">
                        <div class="form-group  clearfix">
                            <p class="fl" style="width:80px; text-align:right; margin-top:6px; margin-right:15px;">账号：</p>
                            <input id="userName" type="text" class="form-control  fl" placeholder="请输入账号" style="width:220px">
                        </div>
                        <div class="form-group  clearfix">
                            <p class="fl" style="width:80px; text-align:right; margin-top:6px; margin-right:15px;">姓名：</p>
                            <input id="trueName" type="text" class="form-control  fl" placeholder="请输入姓名" style="width:220px">
                        </div>
                        <div class="form-group  clearfix">
                            <p class="fl" style="width:80px; text-align:right; margin-top:6px; margin-right:15px;">密码：</p>
                            <input id="password" type="password" class="form-control  fl" placeholder="请输入密码" style="width:220px">
                        </div>
                        <div class="modal-footer" style=" border:0; padding-bottom: 40px;">
                            <button type="button" class="btn btn-white" data-dismiss="modal" style="width:100px">关闭</button>
                            <button onclick="saveAdmin()" type="button" class="btn btn-primary" style="width:100px; margin:0 50px 0 50px;">确定</button>
                        </div>
                    </form>    
                </div>
            </div>
        </div>
     </div> 
        <script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
        <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
        <script src="${prc}/resources/bootstrap/js/bootstrap-paginator.js"></script>
        <script>
            $(function() {
                $('a.btn-primary').click(function(event) {
                    $(this).addClass('current_a').siblings().removeClass('current_a');
                });
                $('.btn-white').click(function(event) {
                    $(this).addClass('active').siblings().removeClass('active');
                });
            });
            
            var options = {
           		currentPage: ${page.page},    
           	    totalPages: ${page.limit},    
           	    size:"normal",    
           	    bootstrapMajorVersion: 3,    
           	    alignment:"right",    
           	    numberOfPages:10,
           	    totalPages:${page.totalPages},
           	    pageUrl:function(type,page, current){
           	    	return "${prc}/main/user/list?page="+page
           	    }
           	}
           	
           	$('#pagination').bootstrapPaginator(options);
            
            
            function show_remove(del_id){
            	$('#del_id').val(del_id);
            	$('#myModal').modal();
            }
            
            function remove(){
            	del_id = $('#del_id').val();
            	location.href='${prc}/main/user/del/'+del_id
            }
            
            
          //删除按钮事件
        	$("a[name=remove]").click(function(){
        		/* $('#myModal').modal('show'); */
        	  if(confirm("是否确定要删除？")){
        		var tr = this.parentNode.parentNode.parentNode;
        		var id = tr.getAttribute("id");
        		$.ajax({
        			url:'<c:url value="/main/user/del.ajax" />',
        			data:{id:id},
        			type:'post',
        			success:function(data){
        				if(data.success){
        				tr.remove();
        				}
        			},
        			error:function(){
        				alert("提交失败");
        			}
        		})
        		}
        	});
          
        	//当前文本框失去焦点时
            $('input').blur(function(){
        		 var tr = this.parentNode.parentNode.parentNode;
         		 var id = tr.getAttribute("id");
         		 var trueName=$("#"+id+" input[name='trueName']" ).val();
         		var password=$("#"+id+" input[name='password']" ).val();
         		 if(trueName||password){
         		 $.ajax({
        			url:'<c:url value="/main/user/update.ajax" />',
        			data:{id:id,trueName:trueName,password:password},
        			type:'post',
        			success:function(data){
        				if(data.success){
        					if(trueName){
        					   $("#"+id+" input[name='trueName']" ).val(trueName);
        					   //$("#"+id+" input[name='trueName']" ).attr("placeholder",trueName);
        					}
        					if(password){
        						$("#"+id+" input[name='password']" ).val(password);
          					    //$("#"+id+" input[name='password']" ).attr("placeholder",trueName);
          					}
        				}
        			},
        		 })
         		}    
        	 });
        	
         // 添加系统会员
            function saveAdmin(){
            	userName = $.trim($("#userName").val());
            	trueName = $.trim($("#trueName").val());
            	password = $("#password").val();
            	var b = /^[0-9a-zA-Z]*$/g;
            	if(userName=="" || !b.test(userName)){
            		alert('账号：只能是数字和字母组成。');
            		return ;
            	}
            	if(trueName==""){
            		alert('信息不完整，不能添加！');
            		return ;
            	}
            	if(password==""){
            		alert('信息不完整，不能添加！');
            		return ;
            	}
				$.getJSON('${prc}/main/user/check/user_name/'+userName,function(data){
            		if(data.success){ // 帐号可以使用
            			$.post('${prc}/main/user/save',
            					{"userName":userName,"trueName":trueName,"password":password},
            					function(data){
            						if(data.success){
            							alert(data.message);
            							location.href="${prc}/main/user/list";
            						}else{
            							alert(data.message);
            						}
            					});
            		}else{ // 不能使用
            			alert(data.message);
            		}
            	});
            	
            }
            
        </script>
</body>

</html>
