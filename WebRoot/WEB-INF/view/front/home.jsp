<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglibs.jsp" %>    
<!DOCTYPE html>
<html>

<head>
    <%@include file="head_content.jsp" %>
</head>

<body style="background:#f1f1f1;">
	
	<%-- 顶栏 header --%>
	<jsp:include page="header.jsp" />
    <!-- banner  轮播图 -->
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators sanfan-width">
        	<c:forEach items="${banner_image}" varStatus="vs">
        		<li data-target="#carousel-example-generic" data-slide-to="${vs.index}" ${vs.index==0?'class="active"':'' }></li>
        	</c:forEach>
        </ol>
        <div class="carousel-inner" role="listbox">
        	<c:forEach items="${banner_image}" var="bi" varStatus="vs">
	            <div class="item ${vs.index==0?'active':'' }">
	                <a <c:if test="${bi.href_flag}"> href="${bi.url}" </c:if>  ${bi.blank?' target="_blank"':''}><img src="${prc}/${bi.image_url}" alt="..."></a>
	                <div class="carousel-caption"></div>
	            </div>
            </c:forEach>
        </div>
    </div>

    <!-- 内容  -->
    <!-- 第1个 -->
	<c:forEach items="${list}" var="b" varStatus="i" >
	
		<c:if test="${fn:length(b.project)>0}">
		    <div class="iconCenter clear">
		        <p class="list-top">${b.name }</p>
		        <p class="list-bottom">${b.des }
		        </p>
		    </div>
		    <div class="conBox">
		        <div class="container clear">
		            <div class="row">
		              <c:forEach items="${b.project}" var="p" varStatus="i" begin="0" end="0">
		                <div class="imgLeft    case01">
		                    <a href="${prc}/common/project/details/${p.id}" >
		                        <img src="<c:url value="/image/${p.imgUrl}/cut002"/>" class="imgStyle01"> 
		                    </a>
		                    <a href="${prc}/common/project/details/${p.id}"  class="nameSchool">${p.name }</a>
		                </div>
		               </c:forEach>
		                <div class="imgRight  case01">
		                 <c:forEach items="${b.project}" var="p" varStatus="i" begin="1" end="5">
		                    <div class="littleImg">
		                        <a href="${prc}/common/project/details/${p.id}" >
		                           <img src="<c:url value="/image/${p.imgUrl}/cut001"/>" class="imgStyle01">
		                        </a>
		                        <a href="${prc}/common/project/details/${p.id}" class="nameSchool">${p.name }</a>
		                    </div>
		                  </c:forEach>
		                    <div class="littleImg moreBtn_01">
		                        <a href="${prc}/common/project/list/${b.id}" style="color:#fff;">查看更多</a>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
	    </c:if>
	</c:forEach>

    
    <%-- 底栏  header --%>
    <%@include file="footer.jsp" %>
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>

</html>
