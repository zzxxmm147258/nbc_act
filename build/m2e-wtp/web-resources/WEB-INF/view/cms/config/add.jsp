<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>配置项管理</title>
    <link href="${prc}/resources/html/cms/css/bootstrap.min.css" rel="stylesheet">
    <link href="${prc}/resources/html/cms/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
    <link href="${prc}/resources/html/cms/css/style.css" rel="stylesheet">

    <link href="${prc}/resources/html/cms/css/info.css" rel="stylesheet">
</head>
<body style="background: #fff">
<div class="ibox-content" id="config">
    <form class="form-horizontal" id="bean_base">
        <h3>活动人数设置</h3>
        <div class="form-group">
            <div class="col-sm-4">
                <label>最小人数：</label>
                <input type="number" class="shortInput"/>
            </div>
            <div class="col-sm-4">
                <label>最大人数：</label>
                <input type="number" class="shortInput"/>
            </div>
        </div>
    </form>

    <form class="form-horizontal" id="bean_escort">
        <h3>陪同人员设置</h3>
        <div class="form-group">
            <label></label>
            <input type="checkbox" value="" checked id="isEscort" onclick="PageObject.isEscort(this)"/>允许陪同
        </div>
        <div class="form-group">
            <div class="col-sm-4">
                <label>最小人数：</label>
                <input type="number" class="shortInput"/>
            </div>
            <div class="col-sm-4">
                <label>最大人数：</label>
                <input type="number" class="shortInput"/>
            </div>
        </div>
        <div class="form-group col-sm-12">
            <label>人均价格：</label>
            <input type="number" class="shortInput"/>元/人
        </div>
        <div class="clear"></div>
    </form>

    <form class="form-horizontal" id="items_1003010">
        <h3>物料</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType" checked/>必选
            <input type="radio" value="30" name="selectType" />可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003010_list">

        </ul>
    </form>

    <form class="form-horizontal" id="items_1003020">
        <h3>交通</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType" checked/>必选
            <input type="radio" value="30" name="selectType"/>可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003020_list">

        </ul>
    </form>

    <form class="form-horizontal" id="items_1003030">
        <h3>人力</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType" checked/>必选
            <input type="radio" value="30" name="selectType"/>可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003030_list">

        </ul>
    </form>

    <form class="form-horizontal" id="items_1003040">
        <h3>场地</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType" checked/>必选
            <input type="radio" value="30" name="selectType"/>可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003040_list">

        </ul>
    </form>

    <form class="form-horizontal" id="items_1003050">
        <h3>保险</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType" checked/>必选
            <input type="radio" value="30" name="selectType"/>可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003050_list">

        </ul>
    </form>

    <form class="form-horizontal" id="items_1003060">
        <h3>其他</h3>
        <div class="form-group">
            <label></label>
            <input type="radio" value="10" name="selectType"/>不显示
            <input type="radio" value="20" name="selectType"/>必选
            <input type="radio" value="30" name="selectType" checked/>可选
        </div>
        <div  class="form-group">
            <div class="col-sm-4">
                <label><input type="radio" value="10" name="radioPrice" checked/>人均价格：</label>
                <input type="number" class="shortInput"/>元/人
            </div>
            <div class="col-sm-4">
                <label><input type="radio" value="20" name="radioPrice"/>固定价格：</label>
                <input type="number" class="shortInput" disabled/>元
            </div>
        </div>
        <div  class="form-group col-sm-12">
            <label>明细：</label>
            <button type="button" class="btn btn-outline btn-success" onclick="recordEdit.open(this)">编辑明细</button>
        </div>
        <div class="clear"></div>
        <ul class="configList" id="items_1003060_list">

        </ul>
    </form>

    <div class="editBtn">
        <button type="button" id="saveBtn" class="btn btn-w-m btn-success" onclick="PageObject.save()">保存</button>
        <a href="${prc }/main/project/list" class="btn  btn-w-m  btn-default">返回</a>
    </div>
</div>


<div id="msg"></div>

</body>
<script src="${prc}/resources/html/cms/js/jquery-2.1.1.min.js"></script>
<script src="${prc}/resources/html/cms/js/bootstrap.min.js"></script>
<script src="${prc}/resources/html/cms/js/plugins/layer/layer.min.js"></script>
<script type="text/javascript">
    layer.use('extend/layer.ext.js'); //载入layer拓展模块
</script>
<script src="${prc}/resources/html/cms/js/config.js"></script>
</html>