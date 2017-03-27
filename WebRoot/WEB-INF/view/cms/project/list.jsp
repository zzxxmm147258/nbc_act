<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/view/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理系统</title>
    <link rel="stylesheet" href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css">  
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/style.css">  
    
     <!-- 分页需要的js -->
    <script src="${prc}/resources/bootstrap/js/jquery.min.js"></script>
    <script src="${prc}/resources/bootstrap/js/bootstrap-paginator.js"></script>
    
    <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
    
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
	    	return "${prc}/main/project/list?typeId=${typeId}&page="+page
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
                        <a href="#">资源管理</a>
                    </li>
                    <li>
                        <strong>项目管理</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row col-lg-12">
                <div class="ibox float-e-margins p-md ibox-title">
                    <span>所属产品体系：</span>
                     <a href="${prc}/main/project/list" <c:if test="${empty typeId }">class="btn btn-xs btn-primary  current_a"</c:if> class="btn btn-xs btn-primary">全部</a>
                     <c:forEach items="${type}" var="b" varStatus="i">
                         <a href="${prc}/main/project/list?typeId=${b.id }" <c:if test="${b.id eq typeId}">class="btn btn-xs btn-primary  current_a"</c:if>  class="btn btn-xs btn-primary">${b.name }</a>
                     </c:forEach>
                     
                    <!-- 删除和增加按钮 -->
                    <div class="ibox-tools">
                        <a href="#" style="margin-right:12px" data-toggle="modal" >
                            <i onclick="show_remove()"  class="fa fa-trash font_16 color9">&nbsp;删除已选</i>
                        </a>
                        <a href="${prc}/main/project/input">
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
                                                <input id="checkAll" onclick="checkAll()"  type="checkbox" name="0"   class="input-mini" >全选
                                            </label>
                                        </div>
                                    </th>
                                    <th>ID</th>
                                    <th>展示图</th>
                                    <th>名称</th>
                                    <th>案例数</th>
                                    <th>已有配置单</th>
                                    <th>推荐值</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${list }" var="p" varStatus="i">
                                <tr id="${p.id }" class="gradeA">
                                    <td>
                                        <div class="checkbox  marginClear">
                                            <label class="checkbox   marginClear" for="closeButton${i.count }">
                                                <input id="closeButton${i.count }" name="ids" type="checkbox" value="${p.id }" class="input-mini" >${i.count }
                                            </label>
                                        </div>
                                    </td>
                                    <td>${p.number }</td>
                                    <td>
                                        <img src="${prc}/image/${p.imgUrl}/cut001" alt="" class="img_box">
                                    </td>
                                    <td>${p.name }</td>
                                    <td><a href="${prc}/main/case/list/${p.id}">${p.caseNum }</a></td>
                                    <td><a href="${prc}/main/order/list?mId=${p.id}">${p.orderNum }</a></td>
                                    <td>
                                        <input id="${p.id }" name="recommend" type="text" class="form-control  inputWidth" value="${p.recommend }">
                                    </td>
                                    <td>
                                        <div class="checkbox   marginClear">
                                            <a href="${prc}/main/project/input?id=${p.id }" >编辑详情</a>
                                            <br> 
                                                <c:choose> 
                                                    <c:when test="${p.configNum >0 }">   
                                                       <a href="${prc}/main/config/add?mId=${p.id }&isEdit=true" >配置项管理</a>
                                                    </c:when> 
                                                    <c:otherwise>   
                                                       <a href="${prc}/main/config/add?mId=${p.id }" >配置项管理</a>
                                                    </c:otherwise> 
                                                 </c:choose>
                                            <br>
                                            <label class="checkbox   marginClear" for="closeButton">
                                                <c:choose> 
                                                    <c:when test="${p.isRecommend == true}">   
                                                    <input id="${p.id }"  onclick="updateIsRecommend('${p.id}')"  name="isRecommend" type="checkbox" value="0"  class="input-mini" checked="checked" >推荐
                                                    </c:when> 
                                                    <c:otherwise>   
                                                       <input id="${p.id }"  onclick="updateIsRecommend('${p.id}')" name="isRecommend" type="checkbox" value="1"  class="input-mini"   >推荐
                                                    </c:otherwise> 
                                                 </c:choose> 
                                           </label>
                                        </div>
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
    <!-- 删除  提示弹窗内容如下 -->
    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        <input type="hidden" id="del_id"/>
        <div class="modal-dialog" style="width:400px; margin-top: 150px;">
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
  
    <script>
        $(function() {
            $('a.btn-primary').click(function(event) {
                $(this).addClass('current_a').siblings().removeClass('current_a');
            });
            $('.btn-white').click(function(event) {
                $(this).addClass('active').siblings().removeClass('active');
            });
        });
        
        //全选
        function checkAll(){
        	var obj=$("#checkAll").attr("name");
        	if(obj=='0'){
        	    $("input[name='ids']").prop("checked",true);
        	    $("#checkAll").attr("name",'1');
        	}else{
        		$("input[name='ids']").prop("checked",false);
        		$("#checkAll").attr("name",'0');
        	}
        }
        
        //删除已选事件
        function show_remove(){
        	var obj=$("input[name='ids']");
            var idList = []; 
            for(k in obj){
                if(obj[k].checked){
                	idList.push(obj[k].value)
                }
            }
            if(idList.length == 0){
            	alert("请选择需要删除项！");
     			return false;
            }else{
            	$('#del_id').val(idList);//把需要删除的数据放到模态框隐藏
            	$('#myModal').modal();
            }
        	
        }
        function remove(){
        	del_id = $('#del_id').val();//取出隐藏的需要删除的ID
        	location.href='${prc}/main/project/remove?idList='+del_id
        }
        
      //当前文本框失去焦点时
        $("input[name='recommend']").blur(function(){
        	 var r = /^\+?[1-9][0-9]*$/;
    		 var tr = this.parentNode.parentNode;
     		 var id = tr.getAttribute("id");
     		 var recommend=$("#"+id+" input[name='recommend']" ).val();
     		 if(recommend){
     			if(!r.test(recommend)){
         			alert("请输入正整数！");
         			$("#"+id+" input[name='recommend']" ).val("");
         			return false;
         		 } 
     		 }
     		   $.ajax({
    			url:'${prc}/main/project/recommend',
    			data:{id:id,recommend:recommend},
    			type:'post',
    			success:function(data){
    				if(data.success){
    				    $("#"+id+" input[name='recommend']" ).val(recommend);
    				}
    			},
    		 })
    	 });
      
       /*  $("input[name='isRecommend']").blur(function(){
       	     var r = /^\+?[1-9][0-9]*$/;
   		     var tr = this.parentNode.parentNode.parentNode.parentNode;
    		 var id = tr.getAttribute("id");
    		 var isRecommend=$("#"+id+" input[name='isRecommend']").val();
    		 $.ajax({
   			     url:'${prc}/main/project/recommend',
   			     data:{id:id,isRecommend:isRecommend},
   			     type:'post',
   			     success:function(data){
   				 if(data.success){
   					if(isRecommend == '1'){
   					   $("#"+id+" input[name='isRecommend']" ).val(0);
   					}else{
   						$("#"+id+" input[name='isRecommend']" ).val(1);
   					}
   					
   				}
   			},
   		 })
   	 }); */
        function updateIsRecommend(id){
        	var isRecommend=$("#"+id+" input[name='isRecommend']").val();
        	$.ajax({
  			     url:'${prc}/main/project/recommend',
  			     data:{id:id,isRecommend:isRecommend},
  			     type:'post',
  			     success:function(data){
  				 if(data.success){
  					if(isRecommend == '1'){
  					   $("#"+id+" input[name='isRecommend']" ).val(0);
  					}else{
  						$("#"+id+" input[name='isRecommend']" ).val(1);
  					}
  					
  				}
  			},
  		 })
        }
        
    </script>
</body>

</html>
