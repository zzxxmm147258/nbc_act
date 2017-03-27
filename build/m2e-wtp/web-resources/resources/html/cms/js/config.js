/**
 * Created by wangdandan on 16/12/26.
 */
//请求路径
var Path = {
    base :get_root(),
    selectBymId : '/main/config/selectBymId.ajax',
    save :'/main/config/save.ajax'
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

var mId;
var isEdit = false;
$(function () {
    mId = tool.getQueryString('mId');
    var Edit = tool.getQueryString('isEdit');
    if(Edit) {
        isEdit = true;
    }
    PageObject.init();

    $('input[name="selectType"]').each(function(){
        $(this).click(function(){
            var v = $(this).val(),
                p = $(this).parents('form'),
                input = p.find('input'),
                button = p.find('button');
            if(v == 10){
                for(var i = 3; i < 7; i++){
                    $(input[i]).attr('disabled',true);
                }
                $(button[0]).attr('disabled',true);
            }else{
                p.find('input[name="radioPrice"]').attr('disabled',false);
                var o = p.find('input[type="radio"]:checked ');
                o.attr('disabled',false);
                PageObject.selectPrice(o);

                $(button[0]).attr('disabled',false);
            }
        });
    });

    $('input[name="radioPrice"]').each(function(){
        var o = $(this);
        o.click(function(){PageObject.selectPrice(o)});
    });

});


//弹出层
var iss, pageii,editorLayer = function(obj, call){
    obj = obj || {};
    pageii = $.layer({
        type: 1,
        title: '编辑明细',
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
            });
        }
    });
    return pageii;
};

var PageObject = {
    data : null,
    init : function(){
        if(isEdit){
            $.ajax({
                url: Path.base + Path.selectBymId + '?mId=' + mId,
                type:'POST',
                dataType: 'json',
                data:'',
                success: function(data){
                    if(data.success){
                        PageObject.data = data.datas;
                        console.log(data.datas);
                        PageObject.pageLayout();
                    }else{
                        alert(data.message);
                    }
                },
                error:function(error){
                    console.log(error);
                    alert("查找失败,稍候重试");
                }
            });
        }else{
            PageObject.data = new Object();
            PageObject.data.bean = new Object();
            PageObject.data.config = [];
        }
    },
    pageLayout : function(){
        var self = this,
            base = this.data.bean,
            data = this.data.config;

        var baseInput = $('#bean_base').find('input');
        $(baseInput[0]).val(base.actMinNumber);
        $(baseInput[1]).val(base.actMaxNumber);

        if(base.isEscort){
            $('#isEscort').attr('checked',true);
            var escortInput = $('#bean_escort').find('input[type="number"]');
            $(escortInput[0]).val(base.escortMinNumber);
            $(escortInput[1]).val(base.escortMaxNumber);
            $(escortInput[2]).val(base.price);
        }else{
            $('#isEscort').attr('checked',false);
            self.isEscort();
        }

        for(var i = 0; i < data.length; i++){
            var id = data[i].typeId;
            if(!!data[i].detail){
                var d = JSON.parse(data[i].detail);
                data[i].detail = d;
                this.data.config[i].detail = d;
            }
            self.itemLayout(data[i]);
            $('#items_' + id).attr('index',i);
        }
    },
    itemLayout : function(data){
        var dom = $('#items_'+data.typeId),
            input = dom.find('input[type="number"]'),
            selectType = dom.find('input[name="selectType"]'),
            radioPrice = dom.find('input[name="radioPrice"]');
        for(var i=0; i < selectType.length; i++){
            var n = $(selectType[i]);
            if(n.val() == data.selectType){
                n.attr('checked',true);
                if(data.selectType == 10){
                    radioPrice.attr('disabled',true);
                    input.attr('disabled',true);
                    dom.find('button').attr('disabled',true);
                }
                break;
            }
        }
        if(!!data.price){
            $(radioPrice[0]).attr('checked',true);
            $(radioPrice[0]).parent().next().val(data.price);
            PageObject.selectPrice($(radioPrice[0]));
        }
        if(!!data.fixedPrice){
            $(radioPrice[1]).attr('checked',true);
            $(radioPrice[1]).parent().next().val(data.fixedPrice);
            PageObject.selectPrice($(radioPrice[1]));
        }

        if(!!data.detail){
            var str = '';
            for(var i = 0; i < data.detail.length; i++){
                var d = data.detail[i];
                str += '<li><label>'+ d.key +'</label><span>'+ d.val +'元</span></li>';
            }
            $('#items_'+ data.typeId +'_list').html(str);
        }
    },
    isEscort : function(){
        var isChecked = $('#isEscort').is(':checked'),
            input = $('#bean_escort').find('input[type="number"]');

        input.each(function(){
            if(isChecked){
                $(this).attr('disabled',false);
            }else{
                $(this).attr('disabled',true);
            }
        });
    },
    selectPrice : function(o){
        $(o).parent().next().attr('disabled',false);

        var s = $(o).parents('.col-sm-4').siblings();
        s.find('input[type="number"]').attr('disabled',true);
    },
    save : function(){
        var self = this,
            upData = new Object(),
            bean = new Object();
        upData.config = [];
        bean.id = self.data.bean.id;
        bean.mId = mId;
        //活动人数设置
        var baseInput = $('#bean_base').find('input'),
            actMinNumber = $(baseInput[0]).val(),
            actMaxNumber = $(baseInput[1]).val();

        if(!actMinNumber || !actMaxNumber){
            alert('活动人数设置项不能为空');
            return;
        }else if(parseInt(actMinNumber) > parseInt(actMaxNumber)) {
            alert("活动人数设置项最小人数必须小于最大人数");
            return;
        }else{
            bean.actMinNumber = actMinNumber;
            bean.actMaxNumber = actMaxNumber;
        }
        //陪同人员设置
        if($('#isEscort').is(':checked')){
            var escortInput = $('#bean_escort').find('input[type="number"]');
            var escortMinNumber = $(escortInput[0]).val(),
                escortMaxNumber = $(escortInput[1]).val(),
                price = $(escortInput[2]).val();
            if(!escortMinNumber || !escortMaxNumber || !price){
                alert('陪同人员设置项不能为空');
                return;
            }else if(parseInt(escortMinNumber) > parseInt(escortMaxNumber)) {
                alert("陪同人员设置项最小人数必须小于最大人数");
                return;
            }else{
                bean.escortMinNumber = escortMinNumber;
                bean.escortMaxNumber = escortMaxNumber;
                bean.price = price;
                bean.isEscort = true;
            }
        }else{
            bean.isEscort = false;
        }
        upData.bean = bean;

        //config
        var form = $('form[id^="items_"]');
        for(var i = 0; i < form.length; i ++){
            var dom = $(form[i]),
                typeId = dom.attr('id').split('_').pop(),
                selectType = dom.find('input[name="selectType"]:checked ').val(),
                index = dom.attr('index'),
                typeName = dom.find('h3').text();

            var c = new Object();
            c.mId = mId;
            c.typeId = typeId;
            c.typeName = typeName;
            c.selectType = selectType;
            if(selectType != 10){
                var pRadio = dom.find('input[name="radioPrice"]:checked '),
                    pVal = pRadio.val(),
                    pValue = pRadio.parent().next().val();
                if(!pValue){
                    var msg = pVal == 10 ? '人均价格' : '固定价格';
                    alert(typeName + '项' + msg + '不能为空');
                    return;
                }else{
                	c.priceType = pVal;
                    c.price = pValue;
                }

            }

            if(!index){
                var i = self.data.config.length;
                self.data.config.push(c);
                dom.attr("index",i);

            }else{
                isEdit ? c.id = self.data.config[index].id : '';
                if(!!self.data.config[index].detail){
                    c.detail = self.data.config[index].detail;
                }
                //self.data.config[index] = c;
            }
            upData.config.push(c);
        }

        for(var i = 0; i < upData.config.length; i++){
            var d = upData.config[i];
            if(!!d.detail){
                upData.config[i].detail = JSON.stringify(d.detail);
            }
        }
        console.log(JSON.stringify(upData));
        $('#saveBtn').attr('disabled',true);
        $.ajax({
            url: Path.base + Path.save,
            type:'POST',
            dataType: 'json',
            data: 'datas=' + JSON.stringify(upData),
            success: function(data){
                if(data.success){
                    alert('保存成功');
                    location.href=Path.base+'/main/project/list';
                }else{
                    alert(data.message);
                }
            },
            error:function(error){
                console.log(error);
                alert("保存失败,稍候重试");
            }
        });
    }
};

var recordEdit = {
    itemDivId : '',
    dataIndex : null,
    data : null,
    open : function(o){
        var self = this, str = '';
        var div = $(o).parents('form');
        self.itemDivId = div.attr('id');
        self.dataIndex = div.attr('index');
        if(!!self.dataIndex){
            var data = PageObject.data.config,
                detail = data[self.dataIndex].detail;
            if(!!detail){
                for(var i = 0; i < detail.length; i++){
                    var d = detail[i];
                    str += '<tr>'+
                        '<td><input type="text" value="'+ d.key +'"/></td>'+
                        '<td><input type="text" value="'+ d.val +'"/></td>'+
                        '<td><label class="fa fa-times" onclick="recordEdit.delItem(this)"></label></td>'+
                        '</tr>';
                }
                self.data = detail;
            }else{
                str = '<tr>'+
                    '<td><input type="text" value=""/></td>'+
                    '<td><input type="text" value=""/></td>'+
                    '<td><label class="fa fa-times" onclick="recordEdit.delItem(this)"></label></td>'+
                    '</tr>';
            }

        }else{
            self.dataIndex = null;
            str = '<tr>'+
                '<td><input type="text" value=""/></td>'+
                '<td><input type="text" value=""/></td>'+
                '<td><label class="fa fa-times" onclick="recordEdit.delItem(this,0)"></label></td>'+
                '</tr>';
        }
        this.show(str);
    },
    addItem : function(){
        var str = '<tr>'+
            '<td><input type="text" value=""/></td>'+
            '<td><input type="text" value=""/></td>'+
            '<td><label class="fa fa-times" onclick="recordEdit.delItem(this,0)"></label></td>'+
            '</tr>';
        $('#editTable').append(str);
    },
    delItem : function(){
        var self = this;
        var p = $(arguments[0]).parents('tr'),
            index = parseInt(p.index()) - 1;

        p.remove();
        if(arguments[1] != 0){
            PageObject.data.config[self.dataIndex].detail.splice(index,1);
            $('#'+ this.itemDivId + '_list li').eq(index).remove();
        }

    },
    save : function(){
        var o = $('#editTable').find('tr'),
            self = this,
            newArray = [],
            str = '';
        for(var i = 1; i < o.length; i++){
            var input = $(o[i]).find('input[type="text"]'),
                r = new Object();

            var v1 = $(input[0]).val(),
                v2 = $(input[1]).val();
            if(!!v1 && !!v2){
                r.key = v1;
                r.val = v2;
                newArray.push(r);
                str += '<li><label>'+v1 +'</label><span>'+ v2 +'</span></li>';
            }else{
                alert('说明或价格不能为空');
                return;
            }
        }
        self.data = newArray;
        if(!!self.dataIndex){
            PageObject.data.config[self.dataIndex].detail = newArray;
        }else{
            var dom = $('#'+this.itemDivId);
            var c = new Object();
            c.detail = newArray;
            c.mid = mId;
            c.typeId = this.itemDivId.split('_').pop();
            c.typeName = dom.find('h3').text();
            c.selectType = dom.find('input[name="selectType"]:checked').val();
            PageObject.data.config.push(c);
            var index = parseInt(PageObject.data.config.length) - 1;
            dom.attr('index',index);
        }

        $('#'+ this.itemDivId +'_list').html(str);
        layer.close(pageii);
    },
    show : function(str){
        var html = '<div class="configEditBox">'+
            '<button type="button" class="btn btn-outline btn-primary" onclick="recordEdit.addItem()" style="float: right; margin: 15px 0;">添加</button>'+
            '<div class="configTable"><table class="table table-bordered configTable" id="editTable">'+
            '<tr><th>说明</th><th>价格</th><th>删除</th></tr>'+
            str +
            '</table></div>'+
            '<div class="editBtn">'+
            '<button type="button" class="btn btn-w-m btn-success" onclick="recordEdit.save()">保存</button>'+
            '<button type="button" class="btn btn-w-m btn-default closeLayer">取消</button>'+
            '</div>'+
            '</div>';
        editorLayer({
            shift: 'left',
            area: [600],
            fix: true,
            where: '左',
            page: html
        }, function () {
            return
            $('html, body').stop().animate({scrollTop: $('#ad336').offset().top}, 500, function () {
                setTimeout(function () {
                    iss || editorLayer({fix: false});
                    iss = true;
                }, 100);

            });
        });
    }
};

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