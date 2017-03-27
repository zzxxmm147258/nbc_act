<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>    

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link rel="shortcut icon" href="favicon.ico" />
    <link href="${prc}/resources/html/cms/css/bootstrap.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/style.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <h1 class="logo-name">
                  <img src="${prc}/resources/html/cms/img/logo.png" alt="">
                </h1>
        <h3 style="font-size:28px; margin-bottom:30px">后台管理中心</h3>
        <div class="form-group">
            <input id="username" type="text" class="form-control" placeholder="用户名">
        </div>
        <div class="form-group">
            <input id="passwd" type="password" class="form-control" placeholder="密码">
        </div>
        <button id="login" type="submit" class="btn btn-primary block full-width m-b" onclick="login()">登 录</button>
    </div>
    <script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
    	function login(){
    		username = $.trim($("#username").val())
    		passwd = $.trim($("#passwd").val())
    		if(username !="" && passwd!=""){
    			$.ajax({
    			    type:'post',
    			    url:'${prc}/main/admin/login' ,
    			    data:{"username":username,"password":passwd} ,
    			    success: function(data){
    			    	if(data.success){
        					location.href = '${prc}/main/admin/home'
        				}else{
        					alert(data.message)
        				}
    			    },
    			    dataType:'json'
    			});
    			
    		}else{
    			alert('用户名,密码不能为空。')
    		}
    	}
    	
    	$('#passwd').bind('keyup', function(event) {
    		if (event.keyCode == "13") {
    			//回车执行查询
    			$('#login').click();
    		}
    	});
    	
    	$(function(){
    		if(window.top!=window.self){//存在父级页面
    			parent.location.href = '${prc}/main/admin/login'
    		 }
    	})
    	
    </script>
</body>

</html>

