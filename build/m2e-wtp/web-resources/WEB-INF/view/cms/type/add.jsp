<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/view/common/head.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/bootstrap-multiselect/js/bootstrap-multiselect.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap-multiselect/css/bootstrap-multiselect.css'/>"
	type="text/css" />
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;"></h1>
	</strong>
	<c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if>
	
	<div class="content scrollWH" scrollH='100'>
		<form:form id="userForm" class="form-horizontal" method="post" modelAttribute="bean" enctype="multipart/form-data">
		    <%-- <input type="hidden" name="id" value="${bean.id }"> --%>
			<%-- <form:hidden path="id" /> --%>
			<%-- <form:hidden path="mId" value="${mId }"/> --%>
		
		   <div class="form-group">
			    <label for="firstname" class="col-sm-2 control-label">名称：</label>
			    <div class="col-sm-4">
			         <form:input type="text" class="form-control" path="name" />
			    </div>
		   </div>
		   <div class="form-group">
			    <label for="firstname" class="col-sm-2 control-label">排序：</label>
			    <div class="col-sm-4">
			         <form:input type="text" class="form-control" path="sort" />
			    </div>
		   </div>
		   
		   
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div style="padding:1px" class="col-md-5">
						<input id="submit" class="btn btn-primary col-md-12" type="submit" value="提交">
					</div><div class="col-md-2"></div>
					<div style="padding:1px" class="col-md-5">
						<a class="btn btn-default col-md-12" id="close" >取消</a>
					</div>
				</div>
			</div>
		</form:form>
	</div>	
</body>
<script type="text/javascript">
	
</script>
</html>