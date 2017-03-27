<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">  
    <link rel="stylesheet" href="<c:url value="/resources/html/cms/css/style.css"/>">  
    <link rel="stylesheet" href="<c:url value="/resources/html/cms/font-awesome/css/font-awesome.css"/>">  
    
    <script src="<c:url value="/resources/bootstrap/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-paginator.js"/>"></script>
    
     <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
    
   <!--  <link href="css/bootstrap.min.css" rel="stylesheet">
     <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet"> -->
    
<title>Insert title here</title>
</head>
<script type="text/javascript">
$(function(){
	
	var options = {
		currentPage: ${page.page},    
	    totalPages: ${page.limit},    
	    size:"normal",    
	    bootstrapMajorVersion: 3,    
	    alignment:"right",    
	    numberOfPages:10,
	    totalPages:${page.totalPages},
	    pageUrl:function(type,page, current){
	    	return "${prc}/main/order/list?mId=${mId}&typeId=${typeId}&page="+page
	    }
	}
	
	$('#pagination').bootstrapPaginator(options);
});

</script>

<body style="background:#fff">
    <div class="gray-bg dashbard-1">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-6 fl">
                <h2>配置单查看</h2>
                <ol class="breadcrumb" style="font-size:14px">
                    <li>
                        <strong>配置单查看</strong>
                    </li>
                </ol>
            </div>

            <div class="input-group  fr" style="width:280px; margin-top:29px; margin-right:10px ">
                <form action="${prc}/main/order/list?mId=${mId}" method="get">
                <input type="text" value="${name }" name="name" placeholder="项目名称搜索" class="input-sm form-control" style="width: 183px;height:34px;">
                <span class="input-group-btn">
                      <input type="submit" value="搜索" class="btn btn-sm btn-primary" style="width: 83px; font-size: 15px;" />
                </span>
                </form>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">
            <!-- 分类 -->
            <div class="row col-lg-12">
                <div class="ibox float-e-margins  ibox-title  iboxPadding" style="padding-top:20px">
                    <span>所属产品体系：</span>
                     <!--<a href="#" class="btn btn-xs btn-primary  current_a">全部</a>
                    <a href="#" class="btn btn-xs btn-primary">社会综合实践活动</a>
                    <a href="#" class="btn btn-xs btn-primary">科学系列</a>
                    <a href="#" class="btn btn-xs btn-primary">心理与健康系列</a>
                    <a href="#" class="btn btn-xs btn-primary">艺术系列</a> -->
                    
                    <a href="<c:url value="/main/order/list?mId=${mId }"/>" <c:if test="${empty typeId }">class="btn btn-xs btn-primary  current_a"</c:if> class="btn btn-xs btn-primary">全部</a>
                    <c:forEach items="${type}" var="b" varStatus="i">
                         <a href="<c:url value="/main/order/list?mId=${mId }&typeId=${b.id }"/>" <c:if test="${b.id==typeId }">class="btn btn-xs btn-primary  current_a"</c:if>  class="btn btn-xs btn-primary">${b.name }</a>
                    </c:forEach>

                    <!--  table 表格-->
                    <div class="ibox-content" style="overflow:hidden; border:none;">
                        <table class="table table-striped table-bordered table-hover " id="editable">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>所属产品体系</th>
                                    <th>项目名称</th>
                                    <th>会员账号</th>
                                    <th>联系人</th>
                                    <th>联系方式</th>
                                    <th>总金额（元）</th>
                                    <th>配置单明细查看</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${datas}" var="p" varStatus="i">
							        <tr class="gradeA">
                                    <td>${p.number }</td>
                                    <td>${p.tName }</td>
                                    <td>${p.pName }</td>
                                    <td>${p.uName }</td>
                                    <td>${p.userName }</td>
                                    <td>${p.phone }</td>
                                    <td>${p.total }</td>
                                    <td>
                                        <a href="javascript:show_remove('${p.id }');" data-toggle="modal" >配置明细</a>
                                    </td>
                                </tr>
						        </c:forEach>
                            </tbody>
                        </table>
                        <div class="btn-group  fr" >
                            <ul id="pagination">
          
                            </ul>
	                    </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 查看明细 提示弹窗内容如下 -->
    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog  modal-content animated bounceInRight" style="position: relative; padding: 44px 0 70px; ">
            <!-- 右上角关闭 符号-->
            <button type="button" class="close  close01" data-dismiss="modal">
                <span aria-hidden="true" class="topRight">&times;</span>
                <span class="sr-only">关闭</span>
            </button>
            <!-- 底部确定按钮 -->
            <button type="button" class="btn btn-primary  btn_ture" data-dismiss="modal">确定</button>

            <!-- 弹窗盒子 -->
            <ol class="alertBox">
                 <li class="alert01">
                      <p class=" font_16">陪同人数：
                        <span class="color2" id="escortNumber" >人</span>
                    </p>
                    <p class="color1 font_16">
                      <strong class="m-r-md">费用合计：<i id="escortTotalPrice">元</i></strong>
                      <strong class="m-r-md">人均费用：<i id="escortPrice" >元/人</i></strong>
                    </p>
                    <hr>
                </li>
                
                <li class="alert01">
                     <p class=" font_16">活动人数：
                        <span id="num" class=" color2">人</span> 
                    </p>
                     <p class=" font_16" style="line-height:40px" id="config" >已选项目：
                       
                       <!--  <span class="m-r-md">：<i class=" color2">元</i></span> -->
                       
                     </p>
                     <p class="color1 font_16">
                       <strong class="m-r-md">费用合计：<i id="totalPrice" >元</i></strong>
                       <strong class="m-r-md">人均费用：<i id="numberPrice" >元/人</i></strong>
                      </p>
                    <hr>
                </li>
                
            </ol>
        </div>
    </div>

    <script>
        $(function() {
            $('a.btn-primary').click(function(event) {
                $(this).addClass('current_a').siblings().removeClass('current_a');
            });
            $('.btn-white').click(function(event) {
                $(this).addClass('active').siblings().removeClass('active');
            });
        });
        
        function show_remove(id){
        	$.ajax({
    			url:'<c:url value="/main/order/getConfig.ajax" />',
    			data:{id:id},
    			type:'post',
    			success:function(data){
    				 var json = eval('(' + data + ')');//解析JSON数据
    				if(json.success){
    				   var datas=json.datas;
    				   $("#num").text(datas.num+"人");
    				   $("#numberPrice").text(datas.numberPrice+"元/人");
    				   $("#escortNumber").text(datas.escortNumber+"人");
    				   $("#escortTotalPrice").text(datas.escortTotalPrice+"元");
    				   $("#escortPrice").text(datas.escortPrice+"元/人");
    				   $("#totalPrice").text(datas.totalPrice+"元");
    				   var config=datas.config;
    				   var htm="";
    				   for(var i=0;i<config.length;i++){
    					   if(config[i].priceType == '10'){
    					     htm=htm+'<span class="m-r-md">'+config[i].typeName+':<i class=" color2">'+config[i].price+'元/人</i></span>'
    					   }else{
    						   htm=htm+'<span class="m-r-md">'+config[i].typeName+':<i class=" color2">'+config[i].price+'元</i></span>'
    					   }
    				   }
    				   $("#config").html("已选项目："+htm);
    				 
    				 $('#myModal').modal();
    				}
    			},
    			error:function(){
    				alert("提交失败");
    			}
    		})
        }
        
    </script>
</body>

</html>
