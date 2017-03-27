<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<% 
	String base_path = request.getScheme().concat("://").concat(request.getServerName()).concat(":")
			.concat(String.valueOf(request.getServerPort())).concat(request.getContextPath());
	request.setAttribute("base_path", base_path);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../head_content.jsp" %>
</head>

<body style="background:#fff;">
    <!-- logo和搜索栏 -->
    <div class="nav-top">
        <div style="max-width:1170px; margin:0 auto">
            <a class="navbar-brand textLeft  fl mt20">
                <img src="${base_path}/resources/html/front/images/logo.png">
            </a>
        </div>
    </div>

    <!-- title -->
    <p class="title01">${bean.name}</p>
    <p class="font14 textCenter">
        <a href="#" class="mr20">${bean.school }</a>
        <a href="#" class="mr20">${bean.date_time }</a>
    </p>

	<div id="detailWrap" class="container" style="font-size:16px;">
	    ${bean.detail }
	</div>
</body>
</html>
