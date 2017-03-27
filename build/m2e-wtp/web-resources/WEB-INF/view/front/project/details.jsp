<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <jsp:include page="../head_content.jsp"/>
    <link rel="stylesheet" type="text/css" href="${prc}/resources/html/front/css/custom.css" />
	
	<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${prc}/resources/html/front/js/icheck.min.js"></script>
    <script type="text/javascript" src="${prc}/resources/html/front/js/detail.js"></script>
</head>


<body style="background:#fff;">

    <!-- 导航以上顶部  -->
    <jsp:include page="../header.jsp" />

    <!-- 案例内容  -->
    <div class="container clear">
        <!-- TOP   开始-->
        <div class="detail-box">
            <!-- 左侧图片 -->
            <div class="detailLeft col-lg-3 fl">
                <img src="${prc}/image/${bean.imgUrl}/cut002" alt="">
            </div>
            <!-- 右侧文字描述 -->
            <div class="detailRight col-lg-9 fr">
                <span class="font24">${bean.name }</span>
                <p class="col-lg-12">
                    <button class="btn fl btn-primary mr10">${bean.classId }</button>
                    <button class="btn fl btn-primary mr10">${bean.gradeId }</button>
                </p>
                <p class="clear">${bean.des }</p>
            </div>
        </div>
        <!-- TOP  结束 -->
        <!-- tab切换  开始 -->
        <div class="tab01">
            <ul id="titleList">
                <li class="${empty tab_type or tab_type eq 'content' ?'current':''}" name="content" data-link="${prc}/common/project/info?tab_type=content&id=${bean.id}"><a>项目介绍</a></li>
                <li class="${'case' eq tab_type?'current':''}" name="case" data-link="${prc}/common/project/info?tab_type=case&id=${bean.id}"><a>案例展示</a></li>
                <li class="${'config' eq tab_type?'current':''}" name="config" data-link="${prc}/common/project/info?tab_type=config&id=${bean.id}&orderId=${orderId}"><a>项目配置</a></li>
            </ul>
        </div>
        <!-- tab切换  结束-->

        <!-- 切换子页面  开始 -->
      <div class="tabBox">  
        <c:choose> 
            <c:when test="${empty bean.details}">  
                <div style="text-align: center;padding: 40px 0;">
                     <img src="${prc}/resources/html/front/images/null.png" alt="">
                </div>
            </c:when>
            <c:otherwise > 
                   ${bean.details}
            </c:otherwise>
         </c:choose>
       </div> 
                 
        
        
        <!-- 切换子页面  结束 -->
    </div>
     <%-- 底栏  header --%>
    <%@include file="../footer.jsp" %>
</body>

<script type="text/javascript">
    $(function() {
        // tab切换
        $('#titleList li').click(function(event) {
            $(this).addClass('current').siblings().removeClass('current');
        });
        // iframe切换
        $("#titleList> li").on('click', function(event) {
            event.preventDefault();
            var $this = $(this),
            dataLink = $this.attr("data-link");
            type = $this.attr("name");
            $.get(dataLink,function(data){
            	$(".tabBox").html(data);
            	if(type == 'case'){
            		case_more();
            	}
            	if(type== 'config'){
            		$.getScript('${prc}/resources/html/front/js/detail.js');
            	}
            });
        });
        
        <c:if test="${!empty orderId}">
	    	modify_order();
	    </c:if>
    });
    
    function modify_order(){
    	$("#titleList li:last").trigger("click") 
    }
    
    function case_more(){
		count = $('#case_list').children('div').length;
		$.get('${prc}/common/case/loading/${bean.id}',
				{"start":count,"limit":3}
				,function(data){
			data = $.trim(data)
			if(data!=''){
				$("#case_list").append(data)	
			}else if(count==0){
				$("#case_more_btn").remove();
				$("#case_list").html('<div style="text-align: center;padding: 40px 0;"><img src="${prc}/resources/html/front/images/null.png" alt=""></div>')
			}else{
				$("#case_more_btn").html('全部加载完毕')
			}		
			
			
		});
	}
    
    
  
     
</script>
</html>

