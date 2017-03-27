<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%@include file="../head_content.jsp" %>
    <script type="text/javascript" src="${prc}/resources/My97DatePicker/WdatePicker.js"></script>
</head>

<body style="background:#fff;">
    
    <!-- 导航以上顶部  -->
    <jsp:include page="../header.jsp" />


    <!-- 案例内容  -->
    <div class="container clear" >
      <div id="htm">
      <c:forEach items="${list}" var="b" varStatus="i" >
        <!-- 第1个-->
        <div class="case-box">
            <!-- 左侧图片 -->
            <div class="caseLeft">
                <img src="${prc }/image/${b.imgUrl}/cut002" alt="">
            </div>
            <!-- 右侧文字描述 -->
            <div class="caseRight">
                <span class="font24">${b.name}</span>
                <p class="caseRight-p">${b.des}</p>
                <hr>
                <div class="caseRight-book">
                    <p class="col-lg-6 fl bookLeft  mb0">
                        <img src="${prc}/resources/html/front/images/tushu.jpg" alt="" class="vam mr5 mt2">
                        <span class="font14  vam">精彩图片(<a href="#">${b.imgNum}</a>张)</span>
                    </p>
                    <p class="col-lg-6 fr bookRight mb0">
                       <c:choose> 
                            <c:when test="${b.imgNum > 0}">  
                                    <a href="${prc}/common/case/imglist?mId=${b.id}" class="font14 color02">查看图片
                                       <img src="${prc}/resources/html/front/images/see.png" alt="">
                                    </a>
                            </c:when>
                            <c:otherwise >  
                                    <a  class="font14 color08" style="cursor: default;color:#878686;" >查看图片
                                       <img src="${prc}/resources/html/front/images/see.png" alt="">
                                    </a>
                            </c:otherwise>
                        </c:choose>
                        
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
     </div>
        <!-- 加载更多按钮 -->
        <div class="btn01" onclick="load()" >加载更多</div>
    </div>
    <%-- 底栏  header --%>
    <%@include file="../footer.jsp" %>
    
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var page='2';//从第2页开始
    var limit='3';//每页3条
    var mId="${mId}";
    //加载更多
	function load(){
		$.ajax({
		    type: 'post',
		    url:'${prc}/common/case/page.ajax',
		    data:{mId:mId,page:page,limit:limit},
		    dataType: 'json',
		    success: function(data){
		    		if(data&&data.length>0){
		    			var htm="";
		    				for (var i = 0; i < data.length; i++) {
		    					
		    					 var imghtm="";
		    		              if(data[i].imgNum>0){
		    		            	  imghtm=imghtm+'  <a href="${prc}/common/case/imglist?mId='+data[i].id+'" class="font14 color02">查看图片';
		    		            	  imghtm=imghtm+'    <img src="${prc}/resources/html/front/images/see.png" alt="">';
		    		            	  imghtm=imghtm+'  </a>';
		    		              }else{
		    		            	  imghtm=imghtm+'  <a  style="cursor: default;color:#878686;" class="font14 color08">查看图片';
		    		            	  imghtm=imghtm+'    <img src="${prc}/resources/html/front/images/see.png" alt="">';
		    		            	  imghtm=imghtm+'  </a>';
		    		              }
		    				 //var dateTime = data[i].dateTime;
		    				 var newTime = new Date(data[i].dateTime); 
		    				 var dateTime = newTime.Format("yyyy年MM月dd日");
		    				 var phtm='<div class="case-box">'
		    		              +'<div class="caseLeft">'
		    		              +' <img src="${prc }/image/'+data[i].imgUrl+'/cut002" alt="">'
		    		              +' </div>'
		    		              +'<div class="caseRight">'
		    		              +' <span class="font24">'+data[i].name+'</span>'
		    		              +'  <p class="caseRight-p">'+data[i].des+'</p>'
		    		              +'  <hr>'
		    		              +' <div class="caseRight-book">'
		    		              +'      <p class="col-lg-6 fl bookLeft  mb0">'
		    		              +'          <img src="${prc}/resources/html/front/images/tushu.jpg" alt="" class="vam mr5 mt2">'
		    		              +'         <span class="font14  vam">精彩图片(<a href="#">'+data[i].imgNum+'</a>张)</span>'
		    		              +'      </p>'
		    		              +'      <p class="col-lg-6 fr bookRight mb0">'
		    		              +''+imghtm+''
		    		              +'      </p>'
		    		              +'      <hr class="clear">'
		    		              +'      <p class="col-lg-6 fl bookLeft mb0">'
		    		              +'          <img src="${prc}/resources/html/front/images/shijian.jpg" alt="" class="vam  mr5">'
		    		              +'          <span class="font14  vam">'+dateTime+'</span>'
		    		              +'      </p>'
		    		              +'      <p class="col-lg-6 fr bookRight  mb0">'
		    		              +'          <a href="${prc}/common/case/detail?id='+data[i].id+'" class="bookRight-see">查看详情</a>'
		    		              +'      </p>'
		    		              +'  </div>'
		    		              +'</div>'
		    		              +'</div>';
		    				 htm=htm+phtm;
		    				}
		    			$("#htm").append(htm);
		    				
						page++;
		    		}else{
		    			$(".btn01").text("所有加载完成！");
		    			$(".btn01").attr("onclick","");
		    			alert("所有加载完成!");
		    		}
		    },
		    error: function(e){
		    	dropload.resetload(false);
		    	console.log(e);
		    }
		});	
	}
 
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
    
</script>
</html>

