<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查找子表数据</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<script type="text/javascript" src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript">
	function selectId() {
		$("input[name='${flag }']").val($("input[type='radio']:checked").val());
		//$('#modals').on('hide.bs.modal');
		//$('#modals').css('display','none');
		//$('#modals').modal('hide');
	}
</script>
</head>
<body>
	<input type="hidden" id="results" value="${flag }"/>
    <!-- <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">请选择</h4>
      </div> -->
      <div class="modal-body" style="overflow: auto;height: 300px;">
        <ul class="list-inline">
        <table class="table table-bordered">
        	<tr>
        		<th>ID</th>
        		<th>名称</th>
       		</tr>
			<c:forEach items="${diciInfoList }" var="d">
				<tr><td><input type="radio" name="radio" value="${d.cname }"/>${d.code }</td><td>${d.cname }</td></tr><li></li>
			</c:forEach>
        </table>
		</ul>
	</div>	
      <div class="modal-footer">
        <input type="button" id="" class="btn btn-success" value="确定" onclick="selectId();" aria-hidden="true" data-dismiss="modal"/>
      </div>
<!--       </div>
    </div> -->
</body>
</html>