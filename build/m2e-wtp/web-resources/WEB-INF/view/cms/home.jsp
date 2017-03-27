<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>    
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link rel="shortcut icon" href="${prc}/resources/html/cms/favicon.ico" />
    <link href="${prc}/resources/html/cms/css/bootstrap.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/animate.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/style.css" rel="stylesheet">
</head>

<body>
    <div id="wrapper">
        <!-- 左侧切换选择按钮 -->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="profile-element">
                            <a href="#">
                                <strong class="font-bold block m-t-xs" style="font-size:16px; color:#fff;">${session_admin.userName}账号</strong>
                                <span class="text-muted text-xs block" style="font-size:14px; color:#fff;">超级管理员</span>
                            </a>
                        </div>
                    </li>

                    <li class="titleHeight  active_list   group1" data-link="${prc}/main/project/list">
                        <i class="fa fa-th-large  font_20" style="margin-right:10px"></i>
                        <span class="nav-label">项目管理</span>
                    </li>

                    <li class="titleHeight  group1" data-link="${prc}/main/type/list">
                        <i class="fa fa-sitemap font_20" style="margin-right:10px"></i>
                        <span class="nav-label">产品体系管理</span>
                    </li>

                    <li class="titleHeight  group2" data-link="${prc}/main/order/list">
                        <i class="fa fa-bar-chart-o  font_20" style="margin-right:10px"></i>
                        <span class="nav-label">配置单查看</span>
                    </li>


                    <li class="titleHeight   group3" data-link="${prc}/main/admin/list">
                        <i class="fa fa-user  font_20" style="margin-right:10px"></i>
                        <span class="nav-label">管理员设置</span>
                    </li>

                    <li class="titleHeight   group3" data-link="${prc}/main/user/list">
                        <i class="fa fa-users font_20" style="margin-right:10px"></i>
                        <span class="nav-label">会员管理</span>
                    </li>
                    
                    <%-- <li class="titleHeight   group1" data-link="${prc}/admin/dictdef/list">
                        <i class="fa fa-users font_20" style="margin-right:10px"></i>
                        <span class="nav-label">字典数据</span>
                    </li> --%>

                </ul>
            </div>
        </nav>

        <div id="page-wrapper" class="gray-bg dashbard-1">
            <!-- head   开始-->
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i></a>
                    <ul class="nav navbar-top-links">
                    	
                        <li class="col-lg-2  textCenter" group="group1">
                            <a  class="color3 active_font">
                                <i class="fa fa-home"></i>资源管理
                            </a>
                        </li>

                        <li class="col-lg-2  textCenter" group="group2">
                            <a class="color3">
                                <i class="fa fa-bar-chart-o"></i>配置单查看
                            </a>
                        </li>
                        
                        <li class="col-lg-2  textCenter" group="group3">
                            <a class="color3">
                                <i class="fa fa-edit"></i>用户管理
                            </a>
                        </li>

                        <li class="col-lg-2 fr textRight">
                            <a href="javascript:logout();" class="color9">
                                <i class="fa fa-sign-out"></i> 退出
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <!-- head  end -->
            <!--  CON  -->
            <div class="row wrapper border-bottom white-bg page-heading" style="padding: 23px  40px;">
                <!-- iframe -->
                <iframe src="#" id="index_container" frameBorder=0 scrolling=no width="100%" onload="reinitIframeEND()" /></iframe>
            </div>
        </div>
    </div>
    <!-- Mainly scripts -->
    <script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
    <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
    <script src="${prc}/resources/html/cms/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${prc}/resources/html/cms/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${prc}/resources/html/cms/js/hplus.js"></script>
    <script src="${prc}/resources/html/cms/js/plugins/pace/pace.min.js"></script>
    
    
    <script>
        $(function() {
            $(document).on("click","ul.navbar-top-links li:not('.textRight')",function(){
                var $this = $(this);
                $this.find("a").addClass("active_font");
                $this.siblings().each(function(i,item){
                    $(item).find("a").removeClass("active_font");
                });
                var group = $this.attr("group");
                show_menu(group);
            });
            $("ul.navbar-top-links li:not('.textRight')").eq(0).trigger("click");
            // 左侧竖边栏单击加背景色/iframe切换
            $("li.titleHeight").on('click', function(event) {
                event.preventDefault();
                var $this = $(this),
                dataLink = $this.attr("data-link");
                $("#index_container").attr("src", dataLink);
            });
            // 左侧竖边栏单击切换效果
            $("li.titleHeight").on('click', function(event) {
                $(this).addClass('active_list').siblings().removeClass('active_list')
            });
            show_menu('group1');
        });
        
        function show_menu(group){
        	$("#side-menu li:not('.nav-header')").hide().removeClass("disBlock");
            $("#side-menu li."+group).show().addClass("disBlock");
            $("#side-menu li."+group).first().addClass('active_list');
            $("#side-menu li."+group).first().trigger("click");
        }
        
        function logout(){
        	if(confirm("您是否确定需要退出后台系统?")){
        		location.href = '${prc}/main/admin/logout'	
        	}
        }
        
        function reinitIframe(){  
        	var iframe = document.getElementById("index_container");  
        	try{  
        	    var bHeight = iframe.contentWindow.document.body.scrollHeight;  
        	    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;  
        	    var height = Math.max(bHeight, dHeight);  
        	    iframe.height = height;  
        	}catch (ex){}  
       	}  
        	  
       	var timer1 = window.setInterval("reinitIframe()", 500); //定时开始  
       	function reinitIframeEND(){  
        	var iframe = document.getElementById("index_container");  
        	try{  
        	    var bHeight = iframe.contentWindow.document.body.scrollHeight;  
        	    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;  
        	    var height = Math.max(bHeight, dHeight);  
        	    iframe.height = height;  
        	}catch (ex){}  
        	// 停止定时  
        	//window.clearInterval(timer1);  
       	  
       	}  
        
    </script>
</body>

</html>
