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
    
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/bootstrap.min.css">  
    <link rel="stylesheet" href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css">  
    <link href="${prc}/resources/html/cms/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${prc}/resources/html/cms/css/style.css">  
    
    <!-- 分页需要的js -->
    <script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
    <script src="${prc}/resources/bootstrap/js/bootstrap-paginator.js"></script>
    <script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
    <script src="${prc}/resources/bootstrap/js/bootstrap-paginator.js"></script>
    
	
	<link href="${prc}/resources/html/cms/js/plugins/fileupload/css/uploadfile.css" rel="stylesheet">
	<script src="${prc}/resources/html/cms/js/plugins/fileupload/js/jquery.form.js"></script>
	<script src="${prc}/resources/html/cms/js/plugins/fileupload/js/jquery.uploadfile.js"></script>
    
	<title>图片管理</title>

	<style type="text/css">
		#uploadCainter{display:none;position: fixed; left: 0; top:0; width: 100%; height: 100%; background: rgba(0,0,0,0.7); z-index: 999;}
		#uploadCainter .box{ background: #fff; padding:15px; border-radius: 8px; position: absolute; top:100px; left: 50%; margin-left: -250px;}
		#uploadCainter #colse{position: absolute; right:-8px; top: -8px; z-index: 1; background: #fff; border-radius: 50%; width: 25px; height: 25px; color: #898989; text-align: center; padding-top: 5px; box-sizing: border-box; text-decoration: none;}
	</style>

</head>
<script type="text/javascript">
  $(function(){
	  
	// 初始化插件
  	$("#fileuploader").uploadFile({
		url:"${prc}/main/caseimg/upload/${mId}",
		allowedTypes:"jpg,jpeg,png,gif",
		fileName:"img",
		uploadStr : '选择图片',
		maxFileCount : 100,
		dragDropStr: "<span><b>支持jpg,jpeg,png格式</b></span>",
		showProgress : true
	});
	  
	  // 初始化分页
	  var options = {
			currentPage: ${page.page},    
		    totalPages: ${page.limit},    
		    size:"normal",    
		    bootstrapMajorVersion: 3,    
		    alignment:"right",    
		    numberOfPages:10,
		    totalPages:${page.totalPages},
		    pageUrl:function(type,page, current){
		    	return "${prc}/main/caseimg/list?mId=${mId }&page="+page
		    }
		}
	$('#pagination').bootstrapPaginator(options);
	
  });
  
  function close_div(){
	  $('#uploadCainter').hide();
	  $('.ajax-file-upload-container').html('');
	  location.href = '${prc}/main/caseimg/list?mId=${mId}'	
  }
  
</script>

<body style="background:#fff">
    <div class="gray-bg dashbard-1">
        <div class="row wrapper white-bg page-heading">
            <div class="col-lg-6  fl">
                <h2 style="max-width:200px; margin-right:0">资源管理</h2>
                <ol class="breadcrumb  fl" style="font-size:14px">
                    <li>
                        <a href="${prc}/main/project/list">资源管理</a>
                    </li>
                    <li>
                        <a href="${prc}/main/project/list">项目管理</a>
                    </li>
                    <li>
                        <a href="${prc}/main/case/list/${pId}">案例管理</a>
                    </li>
                    <li>
                        <strong>图片管理</strong>
                    </li>
                </ol>
            </div>
            <!-- 上传按钮 -->
             <div class="btn-primary  fr  fileUp col-lg-6">上传图片
                <input type="button" id="upload_bottom1" onclick="javascript:$('#uploadCainter').show()" class="form-control  posFile">
             </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row" style="background: #FFF;">
                <div class="col-lg-12">
                    <c:forEach items="${list}" var="b" varStatus="i">
                       <div id="${b.id}" class="file-box  file">
                           <div class="image">
                               <img alt="image" class="img-responsive" src="<c:url value="/image/${b.imgUrl}/cut001"/>">
                           </div>
                           <div  class="file-name">
                                <input id="title${i.count}" type="text" name="title" class="form-control  input_b" placeholder="${b.title}">
                                <label for="title${i.count}" class="fl color5 m-l-sm">编辑</label>
                                <i  onclick="show_remove('${b.id }')"  class="fr fa fa-trash-o  color5 font_24 m-r-sm" data-toggle="modal" ></i>
                           </div>
                           </div>
                     </c:forEach>
                </div>
            </div>
        </div>
        <div class="btn-group  fr" >
             <ul id="pagination">
             </ul>
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
    <%-- 文件上传DIV --%>
    <div id="uploadCainter">
    	<input id="redirect_uri" type="hidden" value="${prc}/main/caseimg/list?mId=${mId}" />
	    <div class="box">
	        <a href="javascript:close_div();" id="colse">X</a>
	        <div id="fileuploader"></div>
	    </div>
	</div>


    
  <script type="text/javascript">
    function show_remove(del_id){
    	$('#del_id').val(del_id);
    	$('#myModal').show();
    	//$('#myModal').modal('show');
    }
    
    function remove(){
    	del_id = $('#del_id').val();
    	location.href='${prc}/main/caseimg/del/'+del_id
    }
     
    
      //删除按钮事件
	 $("i[name=remove]").click(function(){
		/* $('#myModal').modal('show'); */
	  if(confirm("是否确定要删除？")){
		var div = this.parentNode.parentNode;
		var id = div.getAttribute("id");
		$.ajax({
			url:'<c:url value="/main/caseimg/del.ajax" />',
			data:{ids:id},
			type:'post',
			success:function(data){
				if(data.success){
					div.remove();
				}
			},
			error:function(){
				alert("提交失败");
			}
		})
		}
	});
     
	//当前文本框失去焦点时
     $('input').blur(function(){
    	 var div = this.parentNode.parentNode;
 		 var id = div.getAttribute("id");
  		 var title=$("#"+id+" input[name='title']" ).val();
  		 if(title){
  		  $.ajax({
 			url:'<c:url value="/main/caseimg/updateTitle.ajax" />',
 			data:{id:id,title:title},
 			type:'post',
 			success:function(data){
 				if(data.success){
 					   $("#"+id+" input[name='title']" ).val("");
 					   $("#"+id+" input[name='title']" ).attr("placeholder",title);
 				}
 			},
 		 })
  		}    
 	 });
      
      
    </script>
</body>

</html>
