<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link rel="stylesheet" href="${prc}/resources/bootstrap/css/bootstrap.min.css">  
    <link rel="stylesheet" href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css">  
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/animate.css" >
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/style.css">  
    
    <!-- 分页需要的js -->
    <script src="${prc}/resources/bootstrap/js/jquery.min.js"></script>
    <script src="${prc}/resources/bootstrap/js/bootstrap-paginator.js"></script>
    <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
    
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
	    	return "${prc}/main/case/list/${mId }?typeId=${typeId}&page="+page
	    }
	}
	
	$('#pagination').bootstrapPaginator(options);
});

</script>

<body style="background:#fff">
    <div class="gray-bg dashbard-1">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-12">
                <h2>资源管理</h2>
                <ol class="breadcrumb" style="font-size:14px">
                    <li>
                        <a href="${prc}/main/project/list">资源管理</a>
                    </li>
                    <li>
                        <a href="${prc}/main/project/list">项目管理</a>
                    </li>
                    <li>
                        <strong>案例管理</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins  p-md ibox-title">
                        <%-- <span>所属产品体系：</span>
                        <a href="<c:url value="/main/case/list/${mId }"/>" <c:if test="${empty typeId }">class="btn btn-xs btn-primary  current_a"</c:if> class="btn btn-xs btn-primary">全部</a>
                        <c:forEach items="${type}" var="b" varStatus="i">
                         <a href="<c:url value="/main/case/list/${mId }?typeId=${b.id }"/>" <c:if test="${b.id==typeId }">class="btn btn-xs btn-primary  current_a"</c:if>  class="btn btn-xs btn-primary">${b.name }</a>
                        </c:forEach> --%>

                        <!-- 删除和增加按钮 -->
                        <div class="ibox-tools">
                            <a data-toggle="modal"  href="#" style="margin-right:12px">
                                <i onclick="show_remove()"  class="fa fa-trash font_16 color9">&nbsp;删除已选</i>
                            </a>
                            <a href="${prc}/main/case/input?mId=${mId }">
                                <!-- 此处单击跳转到详情管理页-->
                                <i class="fa fa-plus font_16 color2">&nbsp;增加</i>
                            </a>
                        </div>
                        <div class="ibox-content" style="overflow:hidden;">
                            <table class="table table-striped table-bordered table-hover " id="editable">
                                <thead>
                                    <tr>
                                        <th>
                                            <div class="checkbox   marginClear">
                                                <label class="checkbox   marginClear" for="closeButton">
                                                    <input id="checkAll" onclick="checkAll()"  type="checkbox" value="0" name="${mId }"  class="input-mini" >全选
                                                </label>
                                            </div>
                                        </th>
                                        <th>ID</th>
                                        <th>展示图</th>
                                        <th>名称</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <c:forEach items="${datas}" var="p" varStatus="i">
							       <tr class="gradeA">
							           <td>
                                            <div class="checkbox  marginClear">
                                                <label class="checkbox   marginClear" for="closeButton">
                                                    <input id="closeButton"  name="ids" type="checkbox" value="${p.id }" class="input-mini" >${i.count }
                                                </label>
                                            </div>
                                        </td>
                                        <td>${p.number }</td>
                                        <td>
                                            <img src="<c:url value="/image/${p.imgUrl}/cut001"/>" alt="" class="img_box">
                                        </td>
                                        <td>${p.name }</td>
                                        <td>
                                            <div class="checkbox   marginClear">
                                                <a href="${prc}/main/case/input?id=${p.id }">编辑详情</a>
                                                <br>
                                                <a href="<c:url value="/main/caseimg/list?mId=${p.id }"/>">管理图片</a>
                                            </div>
                                        </td>
                                   </tr>
						        </c:forEach>
                                    
                                    
                                </tbody>
                            </table>
                            <!-- <div class="btn-group  fr">
                                <button type="button" class="btn btn-white"><i class="fa fa-chevron-left"></i>
                                </button>
                                <button class="btn btn-white">1</button>
                                <button class="btn btn-white  active">2</button>
                                <button class="btn btn-white">3</button>
                                <button class="btn btn-white">4</button>
                                <button type="button" class="btn btn-white"><i class="fa fa-chevron-right"></i>
                                </button>
                            </div> -->
                            <div class="btn-group  fr" >
                                <ul id="pagination">
          
                               </ul>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 删除  提示弹窗内容如下 -->
    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
         <input type="hidden" id="del_id"/>
         <input type="hidden" id="mId"/>
        <div class="modal-dialog" style="width:400px">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header" style="min-height: 110px; border:0">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h5 class=" font_18">删除后不可恢复，确定删除？</h5>
                </div>
                <div class="modal-footer" style=" border:0; padding-bottom: 40px;">
                    <button type="button" class="btn btn-white" data-dismiss="modal" style="width:100px">关闭</button>
                    <button onclick="remove()" type="button" class="btn btn-primary" data-dismiss="modal" style="width:100px; margin:0 50px 0 50px;">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Mainly scripts -->
   
    <script>
        $(function() {
            $('.btn-primary').click(function(event) {
                $(this).addClass('current_a').siblings().removeClass('current_a');
            });

            $('.btn-white').click(function(event) {
                $(this).addClass('active').siblings().removeClass('active');
            });
        });
        
        //全选
        function checkAll(){
        	var obj=$("#checkAll").val();
        	//alert(obj);
        	if(obj=='0'){
        	    $("input[name='ids']").prop("checked",true);
        	    $("#checkAll").val("1");
        	}else{
        		//$("input[name='ids']").removeAttr("checked");
        		$("input[name='ids']").prop("checked",false);
        		$("#checkAll").val("0");
        	}
        }
        
      //删除已选事件
        function show_remove(){
        	var mId=$("#checkAll").prop("name");
        	var obj=$("input[name='ids']");
            ids ="";//收集需要删除的数据
            for(k in obj){
                if(obj[k].checked)
                	ids=ids+obj[k].value+",";
            }
            if(!ids){
            	alert("请选择需要删除项！");
     			return false;
            }else{
            	$('#del_id').val(ids);//把需要删除的数据放到模态框隐藏
            	$('#mId').val(mId);//把需要删除的数据放到模态框隐藏
            	$('#myModal').modal();
            }
        	
        }
        function remove(){
        	del_id = $('#del_id').val();//取出隐藏的需要删除的ID
        	mId = $('#mId').val();//取出隐藏的需要删除的ID
        	location.href='${prc}/main/case/del?ids='+del_id+'&mId='+mId
        }
        
    </script>
</body>

</html>
