<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<% 
	String base_path = request.getScheme().concat("://").concat(request.getServerName()).concat(":")
			.concat(String.valueOf(request.getServerPort())).concat(request.getContextPath());
	request.setAttribute("base_path", base_path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%@include file="../head_content.jsp" %>
    <link rel="stylesheet" href="${prc}/resources/html/front/css/style.css" type="text/css" />
</head>

<body style="background:#fff;">
   
   <!-- 导航以上顶部  -->
    <jsp:include page="../header.jsp" />
    
    <!-- title -->
    <p class="title01">${bean.name }</p>
    <p class="font14 textCenter">
        <a href="#" class="mr20">${bean.school }</a>
        <a href="#" class="mr20">${bean.date_time }</a>
    </p>

	<div class="bdsharebuttonbox" data-tag="share_1" style="width:90%;margin: 0 auto;text-align:right;max-width:966px">
		<a class="bds_more" data-cmd="more" style="float:right;"></a>
		<a class="bds_qzone" data-cmd="qzone" href="#" style="float:right;"></a>
		<a class="bds_tsina" data-cmd="tsina" style="float:right;"></a>
		<a class="bds_weixin" data-cmd="weixin" style="float:right;"></a>
		<a class="bds_tqq" data-cmd="tqq" style="float:right;"></a>
		<a class="bds_mshare" data-cmd="mshare" style="float:right;"></a>
		<!-- <a class="bds_count" data-cmd="count" style="float:right;"></a> -->
	</div>
	<c:if test="${fn:length(img)>=3}">
	    <!--  精彩图片icon 开始-->
	    <div class="iconStyle">
	        <div class="titleBg">
	            <img src="${prc}/resources/html/front/images/left.png" alt="" class="fl">
	            <span class="fl  greenIcon">精彩图片<i class="font14">(${count})</i></span>
	            <img src="${prc}/resources/html/front/images/right.png" alt="" class="fl">
	            
	        </div>
	    </div>
	    <!-- 精彩图片  结束 -->
	    <!-- 轮播  开始-->
	    <section class="jq22-container">
	        <div class="container01">
	            <div id="full" class="carousel slide" data-ride="carousel">
	                <div class="carousel-inner">
	                  <c:forEach items="${img}" var="p" varStatus="i" >
	                     <ul class="${i.index eq 0 ? 'row item active':'row item'}">
	                       <c:forEach items="${p}" var="b" varStatus="i" >
	                         <li class="col-xs-4">
	                            <img src="${prc }/image/${b.imgUrl}/cut002" class="img-responsive">
	                            <p>${b.title }</p>
	                         </li>
	                        </c:forEach>
	                    </ul>
	                  </c:forEach>
	                </div>
	                <a class="carousel-control left" href="#full" data-slide="prev">Previous</a>
	                <a class="carousel-control right" href="#full" data-slide="next">Next</a>
	            </div>
	        </div>
	    </section>
	    <!-- 轮播  结束 -->
    </c:if>
    <div id="detailWrap" class="container">
	    ${bean.detail }
	</div>
    
    <%-- 底栏  header --%>
    <%@include file="../footer.jsp" %>
    
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/index.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/share.js"></script>
<script>
	window._bd_share_config = {
		common : {
			bdText : '${bean.name}',	
			bdDesc : '${bean.des}',	
			bdUrl : '${base_path}/share/${bean.id}', 	
			bdPic : '${base_path}/image/${bean.imgUrl}/cut002'
		},
		share : [{
			"bdSize" : 16
		}]
	}
</script>
</html>

