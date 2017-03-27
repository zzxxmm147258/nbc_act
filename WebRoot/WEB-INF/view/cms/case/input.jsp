<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/view/common/head.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>详情</title>
    <link href="${prc}/resources/html/cms/css/bootstrap.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/style.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${prc}/resources/dist/css/wangEditor.min.css">

    <link href="${prc}/resources/html/cms/css/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">

    <link href="${prc}/resources/html/cms/css/case_info.css" rel="stylesheet">
    
    <script type="text/javascript" src="${prc}/resources/My97DatePicker/WdatePicker.js"></script>
</head>
<body style="background: #fff">
<div class="ibox-content" id="infoContainer">
    <form method="post" action="${prc}/main/case/editor" class="form-horizontal" id="mainForm" enctype="multipart/form-data">
        <input type="hidden" name="mId" id="mId" value="${mId}"/>
        <input type="hidden" name="action" value="${!empty bean?1:0}" id="action"/>
        <c:if test="${!empty bean}"> 
        	<input type="hidden" name="id" id="xmid" value="${bean.id}"/>
        </c:if>
        <div class="form-group">
            <label class="col-sm-2 control-label"><b>*</b>案例名称</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="name" name="name"/>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><b>*</b>展示图</label>
            <div class="col-sm-10">
                <a href="javascript:;" class="file">选择图片
                    <input type="file" name="img" id="updataImgValue" value="">
                </a>
                <img src="" id="updataImg" width="250"/>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><b>*</b>学校</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="school" name="school"/>
            </div>
            
            <label class="col-sm-2 control-label"><b>*</b>年级</label>
            <div class="col-sm-2">
                <select class="form-control" id="gradeId" name="gradeId"  onchange="Dict.selectProjectType(this)" >

                </select>
            </div>
            
             <label class="col-sm-2 control-label"><b>*</b>时间</label>
             <div class="col-sm-2">
                <input type="text" class="form-control" id="date_time" name="date_time"  onclick="WdatePicker({dateFmt:'yyyy年MM月dd日'})" readonly="true"/>
             </div>
        </div>
         <div class="form-group">
            <label class="col-sm-2 control-label"><b>*</b>简介</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="des" name="des"></textarea>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label">详情编辑</label>
            <div class="col-sm-10">
                <div class="addBtn">
                    <button type="button" class="btn btn-outline btn-primary" onclick="TitleObject.edit()">添加标题</button>
                    <button type="button" class="btn btn-outline btn-success" onclick="TextareObict.edit()">添加富文本</button>
                </div>
                <div id="editContent">

                </div>
                <input type="hidden" value="" name="detail" id="detail"/>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        
        
        
        
        
        <!-- <div class="form-group">
            <label class="col-sm-2 control-label"><b>*</b>所属产品体系</label>
            <div class="col-sm-10">
                <select class="form-control" id="dict" onchange="Dict.selectProjectType(this)">

                </select>
            </div>
        </div> -->
        
       
        <!-- <div class="form-group">
            <label class="col-sm-2 control-label">推荐值</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="recommend" name="recommend"/>
            </div>
            <div class="col-sm-8">
                <span class="help-block m-b-none">可设置0-100的数值，推荐值越高，排名越靠前</span>
            </div>
        </div> -->
        
        <div class="editBtn">
            <button type="button" class="btn btn-w-m btn-success" onclick="FormSave()">保存</button>
            <button type="button" class="btn btn-w-m btn-success" onclick="location.href='${prc}/main/case/list/${mId}'">取消</button>
        </div>
    </form>
</div>

<div style=" display: none;" id="titleEditBox">
    <form method="get" class="form-horizontal" id="titleEditForm" >
        <div class="form-group">
            <label class="col-sm-2 control-label">标题文字</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" name="titleName" value=""/>
            </div>
            <label class="col-sm-2 control-label">文字颜色</label>
            <div class="col-sm-4">
                <div class="input-group colorpicker-demo colorpicker-element">
                    <input type="text" value="#000000" class="form-control" id="fontColor">
                    <span class="input-group-addon"><i style="background-color: rgb(0, 0, 0);"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">字体</label>
            <div class="col-sm-4">
                <select class="form-control" name="fontFamily">
                    <option value="宋体">宋体</option>
                </select>
            </div>
            <label class="col-sm-2 control-label">字号</label>
            <div class="col-sm-4">
                <select class="form-control" name="fontSize">
                    <option value="10">10</option>
                    <option value="12">12</option>
                    <option value="14" selected="selected">14</option>
                    <option value="16">16</option>
                    <option value="18">18</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">选择样式</label>
            <div class="col-sm-12">
                <div class="col-sm-3">
                    <label class="radio-inline"><input type="radio" value="1" checked="checked" name="titleBg"/></label>
                    <h1 class="style-1"><label style="font-size: 12px">样式一</label></h1>
                </div>
            </div>
        </div>

        <div class="editBtn">
            <button type="button" class="btn btn-w-m btn-success" onclick="TitleObject.save()">保存</button>
            <button type="button" class="btn btn-w-m btn-default closeLayer">取消</button>
        </div>
    </form>

</div>

</body>
<script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
<script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>

<script src="${prc}/resources/html/cms/js/plugins/layer/layer.min.js"></script>

<script type="text/javascript" src="${prc}/resources/dist/js/wangEditor.min.js"></script>


<script src="${prc}/resources/html/cms/js/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>

<script src="${prc}/resources/html/cms/js/case_info.js"></script>
<script type="text/javascript">
    layer.use('extend/layer.ext.js'); //载入layer拓展模块
</script>
</html>