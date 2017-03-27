<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%@include file="../head_content.jsp" %>
</head>



<body style="background:#f8f8f8;">

     <%--导航以上顶部  --%>
    <jsp:include page="../header.jsp" />
    
    <!-- 配置单内容  -->
    <div class="container clear">
        <!-- 分类选项 -->
        <div class="all">
            <div class="col-lg-12">
                <span class="curd font16">所属产品体系：</span>
                <a href="${prc}/common/order/list"  class="${empty orderTypeId?'btn btn-list current_a':'btn btn-list' }" >全部</a>
                <c:forEach items="${orderType }" var="b" varStatus="i">
                     <a href="${prc}/common/order/list?typeId=${b.id}"   class="${b.id eq orderTypeId ?'btn btn-list current_a':'btn btn-list'}" >${b.name }</a>
                 </c:forEach>
                 
            </div>
        </div>
        <!-- 第1个-->
   <div id="htm">
     <c:forEach items="${list}" var="p" varStatus="i" >
        <div class="deploy-box" id="${p.id}">
            <div class="deployCon overHidden">
                <div class="deployLeft">
                    <img src="${prc }/image/${p.imgUrl}/cut002" alt="">
                </div>
                <div class="deployRight">
                    <span>产品体系：</span><span>${p.tName}</span>
                    <br>
                    <span>项目名称：</span><span>${p.pName}</span>
                    <br>
                    <span>联系方式：</span><span class="mr20">${p.userName}</span><span>${p.phone}</span>
                    <br>
                    <a href="${prc}/common/project/details/${p.mId}?orderId=${p.id}" class="color02 mr30"><img src="${prc}/resources/html/front/images/iconedit.png" alt="" class="mr5 mt-2">编辑</a>
                    <a href="javascript:show_remove('${p.id }');" class="color02" data-toggle="modal" data-target="">
                        <img src="${prc}/resources/html/front/images/iconlaji.png" alt="" class="mr5 mt-2">删除</a>
                </div>
            </div>
            <div class="deployCon clear pd10 pb15">
                <span>活动人数：</span><span>${p.num}人</span>
                <br>
                <span>已选项目：</span>
                <a href="#" class="color02 dropdown-toggle count-info" data-toggle="dropdown">
                    <img src="${prc}/resources/html/front/images/chakan.png" alt="" class="mr5 mt-2"> 查看明细
                </a>
                <ul class="dropdown-menu dropdown-messages alert01">
                   <c:forEach items="${p.config}" var="con" varStatus="i" > 
                     
                      <li class="borderBottom02">
                        <p>${con.typeName }(
                           <c:choose> 
                             <c:when test="${con.priceType  eq '10'}">  
                                 ${con.price}元/人
                             </c:when>
                             <c:otherwise >  
                                 ${con.price}元
                             </c:otherwise>
                            </c:choose>
                          )</p>
                        <ol>
                          <c:forEach items="${con.det}" var="d" varStatus="i" >  
                              <li>
                                <span class="mr15">${d.key }</span>
                                <span>${d.val }元</span>
                              </li>
                            </c:forEach>
                        </ol>
                      </li>
                   </c:forEach>
                </ul>
                <div class=" ml01  mb5">
                    <c:forEach items="${p.config}" var="b" varStatus="i" >
                        <span class="mr46">${b.typeName }：
                          <i>
                           <c:choose> 
                             <c:when test="${b.priceType  eq '10'}">  
                                 ${b.price}元/人
                             </c:when>
                             <c:otherwise >  
                                 ${b.price}元
                             </c:otherwise>
                            </c:choose>
                           </i>
                        </span>
                    </c:forEach>
                </div>
                <span class="color06  font18 mr60">总费用：<i>${p.totalPrice}元</i></span>
                <span class="color06  font18">人均费用：<i>${p.numberPrice}元/人</i></span>
            </div>
            <div class="deployCon clear pd10 pb15">
                <span>陪同人数：</span><span>${p.escortNumber}人</span>
                <br>
                <span class="color06  font18 mr60">总费用：<i>${p.escortTotalPrice}元</i></span>
                <span class="color06  font18">人均费用：<i>${p.escortPrice}元/人</i></span>
            </div>

        </div>
    </c:forEach>
   </div>
        <!-- 第1个结束 -->
        <!-- 第2个-->


        <!-- 第3个-->

    </div>

    <!-- 加载更多按钮 -->
    <div class="btn01" onclick="load()">加载更多</div>
    <%-- 底栏  header --%>
    <%@include file="../footer.jsp" %>


    <!-- 删除  提示弹窗内容如下 -->
    <div class="modal inmodal" id="myAlert" tabindex="-1" role="dialog" aria-hidden="true">
        <input type="hidden" id="del_id"/>
        <div class="modal-dialog" style="width:400px; margin: 150px auto 0;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="min-height: 110px; border:0">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">关闭</span>
                    </button>
                    <h5 class=" font16 mt40 textCenter">删除后不可恢复，确定删除？</h5>
                </div>
                <div class="modal-footer" style=" border:0; padding-bottom: 40px;">
                    <button type="button" class="btn btn-white" data-dismiss="modal" style="width:100px">关闭</button>
                    <button onclick="remove()" type="button" class="btn btn-primary" data-dismiss="modal" style="width:100px; margin:0 50px 0 50px;">确定</button>
                </div>
            </div>
        </div>
    </div>


</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bootstrap.min.js"></script>
<script src="${prc}/resources/html/front/js/jquery.metisMenu.js"></script>
<script src="${prc}/resources/html/front/js/jquery.slimscroll.min.js"></script>
<script>
    $(function() {
        $('a.btn-list').click(function(event) {
            $(this).addClass('current_a').siblings().removeClass('current_a');
        });
    });
    
    var page='2';//从第2页开始
    var limit='3';//每页3条
    var typeId="${orderTypeId}";
  //加载更多
	function load(){
		$.ajax({
		    type: 'post',
		    url:'${prc}/common/order/page.ajax',
		    data:{page:page,limit:limit,typeId:typeId},
		    dataType: 'json',
		    success: function(data){
		    		if(data&&data.length>0){
		    			var htm="";
		    				for (var i = 0; i < data.length; i++) {
		    				var htmconfig="";	
		    				var config="";	
		    				var con=data[i].config;
		    				for(var j=0;j<con.length;j++){
		    					var det=con[j].det;
		    					//if(det && det.length>0){
		    						htmconfig=htmconfig+'<li class="borderBottom02">';
		    						htmconfig=htmconfig+'<p>'+con[j].typeName+'('
		    						if(con[j].priceType === '10'){
		    							htmconfig=htmconfig+''+con[j].price+'元/人*'+data[i].num+'';
			    					}else{
			    						htmconfig=htmconfig+''+con[j].price+'元';
			    					}
		    						htmconfig=htmconfig+')</p>';
		    						htmconfig=htmconfig+'<ol>';
		    						if(det && det.length>0){
		    						for(var k=0;k<det.length;k++){
		    							htmconfig=htmconfig+'<li>';
			    						htmconfig=htmconfig+'<span class="mr15">'+det[k].key+'</span>';
			    						htmconfig=htmconfig+'<span>'+det[k].val+'元</span>';
			    						htmconfig=htmconfig+'</li>';
		    						}
		    						}
		    						htmconfig=htmconfig+'</ol>';
		    						htmconfig=htmconfig+'</li>';
		    					//}
		    					
		    					config=config+'<span class="mr46">'+con[j].typeName+'：';
		    					config=config+'<i>';
		    					if(con[j].priceType === '10'){
		    						config=config+''+con[j].price+'元/人*'+data[i].num+'';
		    					}else{
		    						config=config+''+con[j].price+'元';
		    					}
		    					config=config+'</i>';
		    					config=config+'</span>';
		    				}
		    			    var phtm='<div class="deploy-box" id="'+data[i].id+'">'
		    				         +'<div class="deployCon overHidden">'
		    				         +'<div class="deployLeft">'
		    				         +'  <img src="${prc }/image/'+data[i].imgUrl+'/cut002" alt="">'
		    				         +'</div>'
		    				         +' <div class="deployRight">'
		    				         +'   <span>产品体系：</span><span>'+data[i].tName+'</span>'
		    				         +'   <br>'
		    				         +'  <span>项目名称：</span><span>'+data[i].pName+'</span>'
		    				         +'   <br>'
		    				         +'   <span>联系方式：</span><span class="mr20">'+data[i].userName+'</span><span>'+data[i].phone+'</span>'
		    				         +'   <br>'
		    				         +'   <a href="${prc}/common/project/details/'+data[i].mId+'?orderId='+data[i].id+'" class="color02 mr30"><img src="${prc}/resources/html/front/images/iconedit.png" alt="" class="mr5 mt-2">编辑</a>'
		    				         +'   <a href="javascript:show_remove(\''+data[i].id+'\');" class="color02" data-toggle="modal" data-target="">'
		    				         +'       <img src="${prc}/resources/html/front/images/iconlaji.png" alt="" class="mr5 mt-2">删除</a>'
		    				         +'</div>'
		    				         +' </div>'
		    				         +' <div class="deployCon clear pd10 pb15">'
		    				         +'<span>活动人数：</span><span>'+data[i].num+'人</span>'
		    				         +' <br>'
		    				         +' <span>已选项目：</span>'
		    				         +' <a href="#" class="color02 dropdown-toggle count-info" data-toggle="dropdown">'
		    				         +'   <img src="${prc}/resources/html/front/images/chakan.png" alt="" class="mr5 mt-2"> 查看明细'
		    				         +'</a>'
		    				         +'<ul class="dropdown-menu dropdown-messages alert01">'
		    				         +''+htmconfig+''
		    				         +' </ul>'
		    				         +'<div class=" ml01  mb5">'
		    				         +''+config+''
		    				         +'</div>'
		    				         +'<span class="color06  font18 mr60">总费用：<i>'+data[i].totalPrice+'元</i></span>'
		    				         +'<span class="color06  font18">人均费用：<i>'+data[i].numberPrice+'元/人</i></span>'
		    				         +'</div>'
		    				         +'<div class="deployCon clear pd10 pb15">'
		    				         +' <span>陪同人数：</span><span>'+data[i].escortNumber+'人</span>'
		    				         +'<br>'
		    				         +'<span class="color06  font18 mr60">总费用：<i>'+data[i].escortTotalPrice+'元</i></span>'
		    				         +'<span class="color06  font18">人均费用：<i>'+data[i].escortPrice+'元/人</i></span>'
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
  
  
	 function show_remove(del_id){
	    	$('#del_id').val(del_id);
	    	$('#myAlert').modal();
	    };
	    
	    function remove(){
	        var	del_id = $('#del_id').val();
	    	$.ajax({
				url:'${prc}/common/order/del.ajax',
				data:{id:del_id},
				type:'post',
				success:function(data){
					if(data.success){
					    $("#"+del_id+"").remove();
					}
				},
				error:function(){
					alert("提交失败");
				}
			})
	    	//location.href='${prc}/common/order/del/'+del_id
	    }
    
</script>

</html>
