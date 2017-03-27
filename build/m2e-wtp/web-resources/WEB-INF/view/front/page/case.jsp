<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<c:forEach items="${data}" var="b">
	<div class="case-box padClear">
	    <!-- 左侧图片 -->
	    <div class="caseLeft">
	        <img src="${prc }/image/${b.imgUrl}/cut002" alt="">
	    </div>
	    <!-- 右侧文字描述 -->
	    <div class="caseRight">
	        <span class="font24">${b.name }</span>
	        <p class="caseRight-p">${b.des }</p>
	        <hr>
	        <div class="caseRight-book">
	            <p class="col-lg-6 fl bookLeft  mb0">
	                <img src="${prc}/resources/html/front/images/tushu.jpg" alt="" class="vam mr5 mt2">
	                <span class="font14  vam">精彩图片(<a href="#">${b.imgNum}</a>张)</span>
	            </p>
	            <p class="col-lg-6 fr bookRight mb0">
	                <a href="${prc}/common/case/imglist?mId=${b.id}" class="font14 color02">查看图片
	                            <img src="${prc}/resources/html/front/images/see.png" alt="">
	                                      </a>
	            </p>
	            <hr class="clear">
	            <p class="col-lg-6 fl bookLeft mb0">
	                <img src="${prc}/resources/html/front/images/shijian.jpg" alt="" class="vam  mr5">
	                <span class="font14  vam"><fmt:formatDate value="${b.dateTime }" pattern="yyyy年MM月dd日"/></span>
	            </p>
	            <p class="col-lg-6 fr bookRight  mb0">
	                <a href="${prc}/common/case/detail?id=${b.id}" class="bookRight-see">查看详情</a>
	            </p>
	        </div>
	    </div>
	</div>
</c:forEach>