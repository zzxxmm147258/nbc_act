<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>  
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="head_content.jsp" %>
</head>

<body style="background: #fff;">
    <!-- head -->
    <div class="nav-top" style="background: #fafbfd;">
        <div style="width: 90%; margin:0 auto;">
            <a class="navbar-brand mt20">
                <img src="${prc}/resources/html/front/images/logo.png">
            </a>
        </div>
    </div>

    <!--  login  CON -->
    <div class="login-box">
        <div class="tree">
            <img src="${prc}/resources/html/front/images/bgleft.png" alt="">
        </div>
        <div class="input-box">
            <p class="font18" style="margin-bottom:25px">会员登录</p>
            <input id="username" type="text" class="input01"  placeholder="请输入登录账号">
            <input id="passwd" type="password" class="input01 suo" placeholder="请输入账号密码">
            <button id="login" class="loginBtn" onclick="login()">立即登录</button>
        </div>

    </div>
    <!--底栏  footer -->
    <div class="footer" style="background: #fff; color:#878686;margin-top:0 ">
        <div class="con" style="padding-right:15px;">
            <p class="col-lg-6  fl" style="font-size:12px; line-height:20px; margin-top:30px">
                北京三芳科技有限公司&nbsp;&nbsp;
                <br>Copyright © 2012-2015 sunfuedu.com</p>
            <p class="col-lg-6  fr phone01">
                客服热线：400 5798 987</p>
        </div>
    </div>
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
<script type="text/javascript">
    	function login(){
    		username = $.trim($("#username").val())
    		passwd = $.trim($("#passwd").val())
    		if(username !="" && passwd!=""){
    			$.ajax({
    			    type:'post',
    			    url:'${prc}/common/user/login' ,
    			    data:{"user_name":username,"password":passwd} ,
    			    success: function(data){
    			    	if(data.success){
        					location.href = '${prc}/common/project/home'
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
</script>   	
</html>
