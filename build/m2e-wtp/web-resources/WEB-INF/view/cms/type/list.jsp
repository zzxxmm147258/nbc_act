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
    
    <script src="${prc}/resources/bootstrap/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/style.css">  
    
<title>Insert title here</title>
</head>
<script type="text/javascript">


</script>

<body style="background:#fff">
    <div class="gray-bg dashbard-1">
        <div class="row wrapper white-bg page-heading">
            <div class="col-lg-12">
                <h2>资源管理</h2>
                <ol class="breadcrumb" style="font-size:14px">
                    <li>
                        <a href="#">资源管理</a>
                    </li>
                    <li>
                        <strong>产品体系管理</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="col-lg-12" style="padding-left:2px; padding-right:2px">
            <div class="ibox float-e-margins ibox-content">
                <table class="table table-hover" id="table_name">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>产品体系名称</th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${list}" var="b" varStatus="i">  
                        <tr id="${b.id }">
                            <td>${i.count }</td>
                            <td>
                                <div style="width:320px; margin:0 auto ">
                                    <input id="${b.id }"  name="name" type="text" class="form-control  inputEdit  fl" value="${b.name }">
                                    <label for="${b.id }" class="fl btn btn-md btn-primary   current_a">编辑</label>
                                    <!-- 注意此处input的id名需要变化，保证每个input的id都不一样，lable的for="input的id名"，input和lable是一 一对应的，因此每一个input的id都要不同，其对应的 label的for值==input的id值-->
                                </div>
                            </td>
                        </tr>
                      </c:forEach>  
                        
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <script>
  //当前文本框失去焦点时
    $('input').blur(function(){
		 var tr = this.parentNode.parentNode.parentNode;
 		 var id = tr.getAttribute("id");
 		 var name=$("#"+id+" input[name='name']" ).val();
 		 if(name){
 		  $.ajax({
			url:'<c:url value="/main/type/updateName.ajax" />',
			data:{id:id,name:name},
			type:'post',
			success:function(data){
				if(data.success){
					if(trueName){
					   $("#"+id+" input[name='name']" ).val(name);
					}
				}
			},
		 })
 		}    
	 });
      
    </script>
</body>

</html>
