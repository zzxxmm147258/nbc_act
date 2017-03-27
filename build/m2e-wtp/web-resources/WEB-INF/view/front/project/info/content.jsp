<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="../../../common/taglibs.jsp" %>
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