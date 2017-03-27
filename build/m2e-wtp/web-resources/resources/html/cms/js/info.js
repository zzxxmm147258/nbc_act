/**
 * Created by wangdandan on 16/12/19.
 */
$(function() {
    Dict.getDict();

    var url = location.search; //获取url中"?"符后的字串
    if (url.indexOf("?id") != -1) {    //判断是否有参数
        updataInfo.init();
    }

    $("#editContent").on("mouseenter",".item", function() {
        InfoCheck.show(this);
    });
    $("#editContent").on("mouseleave", ".item",function() {
        InfoCheck.hide(this);
    });

    if (window.File && window.FileReader && window.FileList && window.Blob){
        //Blob是计算机界通用术语之一，全称写作：BLOB (binary large object)，表示二进制大对象。
        //全部支持
        var handleFileSelect = function(evt) {
            var files = evt.target.files, f = files[0];
            if (!/image\/\w+/.test(f.type)){
                alert("请确保文件为图像类型");
                return false;
            }
            var reader = new FileReader();
            reader.onload = (function(theFile) {
                return function(e) {
                    var show_pic = document.getElementById("updataImg");
                    show_pic.src = e.target.result;
                    console.log(e.target.result);
                };
            })(f);
            reader.readAsDataURL(f);
        };
        document.getElementById('updataImgValue').addEventListener('change', handleFileSelect, false);
    }else {
        alert('该浏览器不全部支持File APIs的功能');
    }
});

//弹出层
var iss, pageii,editorLayer = function(obj, call){
    obj = obj || {};
    pageii = $.layer({
        type: 1,
        title: false,
        area: obj.area ? [obj.area[0]+'px', obj.area[1]+'px'] : ['200px', '200px'],
        offset: ['40px', ''],
        border: [1], //去掉默认边框
        //shade: [0], //去掉遮罩
        closeBtn : 0,
        fix: obj.fix,
        shift: obj.shift || ['bottom', 300, 1],
        page: {
            html: obj.page
        },
        success: function(elem){
            elem.find('.closeLayer').on('click', function(){
                layer.close(pageii);
                call && call();
                TitleObject.isEdit = false;
            });
        }
    });
    return pageii;
};

//富文本编辑
var TextareObict = {
    editor : null,
    save : function(){
        var aHTML = this.editor.$txt.html();
        //$('#editor').destroy();
        layer.close(pageii);
        arguments.length != 0 ? $('#editContent').children().eq(arguments[0]).html(aHTML) : $('#editContent').append('<div class="item" flog="textare">'+ aHTML +'</div>');
    },
    edit : function(){
        var h = '',
            ce = 'onclick="TextareObict.save()"';
        if(arguments.length !== 0){
            h = arguments[0];
            var index = arguments[1];
            ce = 'onclick="TextareObict.save('+ index +')"';
        }
        var str ='<div id="editor" class="note-editor">'+ h +'</div>'
                + '<div class="editBtn">'
                + '<button type="button" class="btn btn-w-m btn-success" id="saveTestare" '+ ce +'>保存</button>'
                + '<button type="button" class="btn btn-w-m btn-default closeLayer">取消</button>'
                + '</div>';

        editorLayer({
            shift: 'left',
            area: [650, 440],
            fix: true,
            where: '左',
            page: str
        }, function () {
            return
            $('html, body').stop().animate({scrollTop: $('#ad336').offset().top}, 500, function () {
                setTimeout(function () {
                    iss || editorLayer({fix: false});
                    iss = true;
                }, 100);

            });
        });

        //加载编辑器
        this.editor = new wangEditor('editor');
        // 上传图片
        this.editor.config.uploadImgUrl = get_root()+'/upload';
        this.editor.config.uploadImgFileName = 'imageFile';
        // 表情显示项
        this.editor.config.emotions = {
            'default': {
                title: '默认',
                data: get_root()+'/resources/dist/emotions.data'
            }
        };
        this.editor.create();

    }
};

//title编辑
var TitleObject = {
    oldData : null,
    index : 0,
    isEdit : false,
    edit : function(){
        var str = $('#titleEditBox').html();
        editorLayer({
            shift: 'left',
            area: [600, 380],
            fix: true,
            where: '左',
            page: str
        }, function () {
            return
            $('html, body').stop().animate({scrollTop: $('#ad336').offset().top}, 500, function () {
                setTimeout(function () {
                    iss || editorLayer({fix: false});
                    iss = true;
                }, 100);

            });
        });


        $('.colorpicker-demo').colorpicker().on('changeColor', function(ev){
            $('#fontColor').val(ev.color.toHex());
        });

        if(TitleObject.isEdit){
            var input = $('.xubox_layer').find('input[type="text"]'),
                select = $('.xubox_layer').find('select'),
                radio = $('.xubox_layer').find('input[type="radio"]');
            $(input[0]).val(TitleObject.oldData.name);
            $(input[1]).val(TitleObject.oldData.color);
            $('.colorpicker-demo').find('i').css('background-color',TitleObject.oldData.color);
            $(select[0]).find('option').each(function(){
                var v = $(this).val();
                if(v === TitleObject.oldData.fontFamily){
                    $(this).attr('selected',true);
                    return;
                }
            });
            $(select[1]).find('option').each(function(){
                var v = $(this).val();
                if(v === TitleObject.oldData.fontsize.split('px')[0]){
                    $(this).attr('selected',true);
                    return;
                }
            });
            $(radio).each(function(){
                var v = $(this).val();
                if(v === TitleObject.oldData.class){
                    $(this).attr('checked',true);
                    return;
                }
            });
        }
    },
    save : function(){
        var input = $('.xubox_layer').find('input[type="text"]'),
            select = $('.xubox_layer').find('select');
        var style = 'color:' + $('#fontColor').val() + ';font-size:' + $(select[1]).find("option:selected").val() +'px;font-family:'+ $(select[0]).find("option:selected").val() +';';

        var title = $(input[0]).val();
        title = $.trim(title).length === 0 ? '标题' : title;
        var classIndex = $('input[name="titleBg"]:checked').length !== 0 ? 'style-' + $('input[name="titleBg"]:checked').val() : '';

        if(this.isEdit){
            $('#editContent').children().eq(this.index).html('<div class="item" flog="title"><h1 class="'+ classIndex+'" style="'+ style +'"><label>'+ title +'</label></h1></div>');
        }else{
            $('#editContent').append('<div class="item" flog="title"><h1 class="'+ classIndex+'" style="'+ style +'"><label>'+ title +'</label></h1></div>');
        }
        this.isEdit = false;
        layer.close(pageii);
    }
};

//上下移动编辑删除
var InfoCheck = {
    show : function (o) {
        if(!o.contains(event.fromElement)) {
            $('#editContent').find('.checkEdit').remove();

            var btn = '<div class="checkEdit">' +
                '<button onclick="InfoCheck.MoveUp(this)" class="btn btn-warning btn-circle" type="button"><i class="fa fa-arrow-up"></i></button>' +
                '<button onclick="InfoCheck.MoveDown(this)" class="btn btn-warning btn-circle" type="button"><i class="fa fa-arrow-down"></i></button>' +
                '<button onclick="InfoCheck.edit(this)" class="btn btn-warning btn-circle" type="button"><i class="fa fa-edit"></i></button>' +
                '<button onclick="InfoCheck.remove(this)" class="btn btn-warning btn-circle" type="button"><i class="fa fa-trash"></i></button>' +
                '</div>';

            $(o).append(btn);
        }
    },
    hide : function(o){
         $(o).find('.checkEdit').remove();
    },
    MoveUp : function(o) {
        var $div = $(o).parents(".item");
        if ($div.index() != 0) {
            $div.prev().before($div);
        }
     },
    MoveDown : function (o) {
        var length = $('#editContent').children().length,
        $div = $(o).parents(".item");
        if ($div.index() != length - 1) {
            $div.next().after($div);
        }
    },
    remove : function(o){
        var $div = $(o).parents(".item");
        $div.remove();
    },
    edit : function(o){
        var $div = $(o).parents(".item"),
            flog = $div.attr('flog'),
            index = $div.index();
        if(flog === 'textare'){
            this.hide($div);
            var content = $div.html();
            console.log(content);
            TextareObict.edit(content,index);
        }else{
            var object = new Object();
            var dom = $div.find('h1'),
                classes = dom.attr('class');

            if (classes.indexOf("-") != -1) {
                object.class = classes.split('-').pop();
            }else{
                object.class = null;
            }
            object.fontsize = dom.css('font-size');
            object.color = tool.getHexColor(dom.css('color'));
            object.fontFamily = dom.css('font-family');
            object.name = dom.text();
            TitleObject.oldData = object;
            TitleObject.isEdit = true;
            TitleObject.index = index;

            TitleObject.edit();
        }
    }
};

//请求路径
var Path = {
    base : get_root(),
    dict : '/main/type/selectBypId.ajax',
    save : '/main/project/editor',
    selectById : '/main/project/selectById.ajax'
};

function get_root(){
	var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}

//字典表
var Dict = {
    data : null,
    getDict : function(){
        $.ajax({
            url: Path.base + Path.dict,
            dataType: 'json',
            data: '',
            async: false,
            success: function(data){
                Dict.data = data;
                Dict.grade();
                Dict.projectType();
            },
            error:function(error){
                console.log(error);
                alert("分类请求失败,稍候重试");
            }
        });
    },
    selectProjectType : function(o){
        var index = $(o).children('option:selected').attr('index'),
            data = this.data.projectType[index].type,
            str = '';
        if(!!data){
            for(var i = 0; i < data.length; i++){
                str += '<label class="checkbox-inline"><input name="classId" type="checkbox" value="'+ data[i].id +'"/>'+ data[i].name +'</label>';
            }
        }
        $('#dictType').html(str);

    },
    projectType : function(){
        var data = this.data.projectType,
            str = '';
        if(!!data){
            for(var i = 0; i < data.length; i++){
                var s = i == 0 ? 'selected' : '';
                str += '<option name="typeId" value="'+ data[i].id +'" index="'+ i +'" '+ s +'>'+ data[i].name +'</option>';
            }
        }
        $('#dict').html(str);
        var o = $('#dict');
        this.selectProjectType(o);
    },
    grade : function(){
        var data = this.data.grade,
            str = '';
        if(!!data){
            for(var i = 0; i < data.length; i++){
                str += '<label class="checkbox-inline"><input type="checkbox" name="gradeId" value="'+ data[i].code +'"/>'+ data[i].cname +'</label>';
            }
        }
        $('#grade').html(str);

    }
};

var  FormSave = function(){
    var typeId = $('#dict').val(),
        name = $('#name').val(),
        imgUrl = $('#updataImg').attr('src'),
        des = $('#des').val(),
        gradeId = new Array(),
        classId = new Array();

    $('#dictType').find('input:checkbox:checked').each(function(i){
        classId.push($(this).val());
    });
    $('#grade').find('input:checkbox:checked').each(function(i){
        gradeId.push($(this).val());
    });
    console.log(classId);

    var isSave = true;
    switch(true){
        case $.trim(name).length === 0:
            isSave = false;
            alert('项目名称不能为空');
            break;
        case classId.length === 0:
            isSave = false;
            alert('分类不能为空');
            break;
        case gradeId.length === 0:
            isSave = false;
            alert('适合年级不能为空');
            break;
        case imgUrl.length === 0:
            isSave = false;
            alert('展示图不能为空');
            break;
        case $.trim(des).length === 0:
            isSave = false;
            alert('简介不能为空');
            break;
        default:
            break;
    }

    $('#details').val($('#editContent').html());

    if(isSave){
        $('#mainForm').submit();
    //    var isRecommend = $('#recommend').val(),
    //        details = encodeURIComponent(encodeURIComponent($('#editContent').html()));
    //    var param = "typeId="+typeId+"&name="+name+"&des="+encodeURIComponent(encodeURIComponent(des))+"&gradeId="+gradeId.join(',')+"&classId="+classId.join(',')+"&isRecommend="+isRecommend+"&details="+details+"&imgUrl="+imgUrl;
    //console.log(param);
    //    $.ajax({
    //        url: Path.base + Path.save,
    //        type:'POST',
    //        dataType: 'json',
    //        data: param,
    //        success: function(data){
    //            if(data.success){
    //                alert('保存成功');
    //            }else{
    //                alert(data.message);
    //            }
    //        },
    //        error:function(error){
    //            console.log(error);
    //            alert("保存失败,稍候重试");
    //        }
    //    });
    }
};

//
var tool = {
    getQueryString : function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
        var context = "";
        if (r != null)
            context = r[2];
        reg = null;
        r = null;
        return context == null || context == "" || context == "undefined" ? "" : context;
    },
    getHexColor : function(rgb){
        rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
        function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
        }
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }
};

//编辑
var updataInfo = {
    data : null,
    isEditPage : false,
    id : '',
    init : function(){
        this.id = tool.getQueryString('id');
        if(!!this.id){
            $.ajax({
                url: Path.base + Path.selectById,
                type:'POST',
                dataType: 'json',
                data: 'id=' + updataInfo.id,
                success: function(data){
                    if(data.success){
                        updataInfo.data = data.datas;
                        updataInfo.layoutPage();
                    }else{
                        alert(data.message);
                    }
                },
                error:function(error){
                    console.log(error);
                    alert("获取信息失败,稍候重试");
                }
            });
        }

    },
    layoutPage : function(){
        var self = this;
        //typeid
        $('#dict option').each(function(){
            var v = $(this).val();
            if(v === self.data.typeId){
                $(this).attr('selected',true);
                var o = $('#dict');
                Dict.selectProjectType(o);
                return;
            }
        });
        //classid
        var classIds = self.data.classId.split(',');
        for(var i = 0; i < classIds.length; i++){
            $('#dictType input[name="classId"]').each(function(j){
                var c = $(this).val();
                if(classIds[i] == c){
                    $(this).attr('checked',true);
                }
            });
        }
        //gradeId
        var gradeIds = self.data.gradeId.split(',');
        for(var i = 0; i < gradeIds.length; i++){
            $('#grade input').each(function(h){
                var g = $(this).val();
                if(gradeIds[i] === g){
                    $(this).attr('checked',true);
                }
            });
        }
        //name
        $('#name').val(self.data.name);
        $('#recommend').val(self.data.recommend);
        $('#updataImg').attr('src',Path.base + '/image/'+self.data.imgUrl+"/cut001");
        $('#des').val(self.data.des);
        $('#editContent').html(self.data.details);
    }
};

