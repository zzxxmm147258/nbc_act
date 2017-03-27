<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%@include file="../head_content.jsp" %>
</head>

<body style="background:#f8f8f8;">

    <!-- 导航以上顶部  -->
    <jsp:include page="../header.jsp" />
    
    

    <!-- 案例内容  -->
    <div class="container clear" >
      <div id="htm">
   
     <c:forEach items="${list }" var="p" varStatus="i">
        <div class="case-box">
            <!-- 左侧图片 -->
            <div class="caseLeft">
                <a href="${prc}/common/case/detail?id=${p.id}"><img src="${prc}/image/${p.imgUrl}/cut002" alt=""> </a>
            </div>
            <!-- 右侧文字描述 -->
            <div class="caseRight  textCenter">
                <a class="font24" href="${prc}/common/case/detail?id=${p.id}" >${p.name}</a>
                <p class="projectRight01">
                    <span class="color02 mr30">分类：<i class="color05">${p.classId}</i></span>
                    <span class="color02 mr30">适合年纪：<i class="color05">${p.gradeId}</i></span>
                </p>
                <p class="projectRight02">${p.des}</p>
                <div class="more02">
                    <img src="${prc}/resources/html/front/images/aicon.png" alt="" class="fl" style="width: 71px; height: 22px;">
                    <div class="textCenter fl hrWidth">
                        <hr style="margin-bottom: 2px; margin-top: 10px;">
                        <hr style="margin: 0;">
                    </div>
                    <a class="fr curp" href="${prc}/common/case/list?mId=${p.id}"  >更多</a>
                </div>
                <ul class="more03 clear">
                  <c:forEach items="${p.cases}" var="b" varStatus="i"  begin="0" end="3">
                    <li class="position01">
                        <img src="${prc}/image/${b.imgUrl}/cut001" alt="">
                        <a  href="${prc}/common/case/detail?id=${b.id}"  class="nameSchool">${b.name }</a>
                    </li>
                   </c:forEach>  
                 </ul>
            </div>
        </div>
      </c:forEach>  

    </div>
    <!-- 加载更多按钮 -->
    <div class="btn01" onclick="load()">加载更多</div>
    </div>
    <%-- 底栏  header --%>
    <%@include file="../footer.jsp" %>
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
<script>
    $(function() {
        $('a.btn-list').click(function(event) {
            $(this).addClass('current_a').siblings().removeClass('current_a');
        });
    });
    
    
    var page='2';//从第2页开始
    var limit='3';//每页3条
    var name="${name}";
    //加载更多
	function load(){
		$.ajax({
		    type: 'post',
		    url:'${prc}/common/project/search_page.ajax',
		    data:{page:page,limit:limit,name:name,search:"project"},
		    dataType: 'json',
		    success: function(data){
		    		if(data&&data.length>0){
		    			var htm="";
		    				for (var i = 0; i < data.length; i++) {
		    					var htmImg="";
		    					var cases=data[i].cases;
    						    if(cases){
    						    	if(cases.length>=4){
	    						    	 for(var j=0;j<=3;j++){
	    						    		 htmImg=htmImg+'<li class="position01">';
	    						    		 htmImg=htmImg+'   <img src="${prc}/image/'+cases[j].imgUrl+'/cut001" alt="">';
	    						    		 htmImg=htmImg+'   <a  href="${prc}/common/case/detail?id='+cases[j].id+'"  class="nameSchool">'+cases[j].name+'</a>';
	    						    		 htmImg=htmImg+'</li>';
		    						     }
	    						    }else{
	    						    	for(var j=0;j<cases.length;j++){
	    						    		htmImg=htmImg+'<li class="position01">';
	    						    		htmImg=htmImg+'   <img src="${prc}/image/'+cases[j].imgUrl+'/cut001" alt="">';
	    						    		htmImg=htmImg+'   <a  href="${prc}/common/case/detail?id='+cases[j].id+'"  class="nameSchool">'+cases[j].name+'</a>';
	    						    		htmImg=htmImg+'</li>';
	    						         }
	    						    }
    						    } 
		    					var phtm='<div class="case-box">'
		    						    +'    <div class="caseLeft">'
		    						    +'       <a href="${prc}/common/case/detail?id='+data[i].id+'"><img src="${prc}/image/'+data[i].imgUrl+'/cut002" alt=""></a>'
		    						    +'    </div>'
		    						    +'    <div class="caseRight  textCenter">'
		    						    +'       <a class="font24" href="${prc}/common/case/detail?id='+data[i].id+'">'+data[i].name+'</a>'
		    						    +'       <p class="projectRight01">'
		    						    +'            <span class="color02 mr30">分类：<i class="color05">'+data[i].classId+'</i></span>'
		    						    +'            <span class="color02 mr30">适合年纪：<i class="color05">'+data[i].gradeId+'</i></span>'
		    						    +'       </p>'
		    						    +'       <p class="projectRight02">'+data[i].des+'</p>'
		    						    +'       <div class="more02">'
		    						    +'             <img src="${prc}/resources/html/front/images/aicon.png" alt="" class="fl" style="width: 71px; height: 22px;">'
		    						    +'            <div class="textCenter fl hrWidth">'
		    						    +'                  <hr style="margin-bottom: 2px; margin-top: 10px;">'
		    						    +'                  <hr style="margin: 0;">'
		    						    +'             </div>'
		    						    +'             <a class="fr curp" href="${prc}/common/case/list?mId='+data[i].id+'" >更多</a>'
		    						    +'        </div>'
		    						    +'        <ul class="more03 clear">'
		    						    
		    						    +''+htmImg+''
		    						    +'</ul>'
		    						    +'</div>'
		    						    +'</div>'
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
    
    
</script>

</html>
