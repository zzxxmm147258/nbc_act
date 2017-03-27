<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>    
    
 <!-- 最顶部  我的配置单栏 -->
    <div class="head-box">
        <div class="textRight" style="max-width:1170px; margin:0 auto">
            <a href="${prc}/common/order/list">
                <img src="${prc}/resources/html/front/images/shopping.png" alt="我的配置单">
                <span style="margin-left:3px">我的配置单</span>
            </a>
            <span style="margin-left:30px">欢迎：${session_user.trueName}</span>
            <a href="${prc}/common/user/logout">
                <span style="margin-left:30px">退出</span>
            </a>
        </div>
    </div>

    <!-- logo和搜索栏 -->
    <div class="nav-top">
        <div class="nav-center">
            <a class="navbar-brand textLeft  fl ${not empty home?'':'mt20'}" style="width: 50%;">
                <img src="${prc}/resources/html/front/images/logo.png">
            </a>
            <form action="${prc}/common/project/search" method="get" id="nameform">
	            <div class="input-group fr mt30 col-lg-6  col-md-6">
	                <input type="text" class="form-control" name="name" placeholder="输入关键词搜索" style="color:#999">
	                <span class="input-group-btn">
	                <button type="submit" class="btn btn-primary" value="case" 
	                name="search" form="nameform" style="margin-left:-5px;border-radius:0 5px 5px 0">案例</button>
	                <button type="submit" class="btn btn-primary" value="project" name="search"
	                 form="nameform" id="btn-radius" >项目</button>
	              </span>
	            </div>
	        </form>    
        </div>
    </div>
    
    <!--  导航栏 -->
    <nav class="navbar navbar-default navbar-sunfu" role="navigation">
        <div class="container-sunfu" style="max-width:1170px; margin:0 auto">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav  navbar-nav" style="width:100%">
                    <li class="${not empty home?'active':''}"><a href="${prc}/common/project/home">首页</a></li>
                    <c:forEach items="${type}" var="p" varStatus="i" >
                       <li class="${p.id eq typeId?'active':''}"><a href="${prc}/common/project/list/${p.id}">${p.name}</a></li>
                    </c:forEach>
                    <li class="${not empty caseList?'active':''}" ><a href="${prc}/common/case/list">精彩案例 </a></li>
                </ul>
            </div>
        </div>
    </nav>
