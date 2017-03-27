/*$.ajaxFileUpload({  
	url : url, //用于文件上传的服务器端请求地址  
	fileInput : className, //文件上传空间input的class属性  <input type="file" class="file" name="file" />  
	dataInput : className,//提交input的class属性  <input type="text" class="file" name="file" />  
	type : 'post',  
	dataType : 'json', //返回值类型 一般设置为json  
	data : {dataType:'json'},//是否登录判定参数
	success : function(data, status) //服务器成功响应处理函数  
	{ 
		if(data.noLogin){
		   //跳转到登录界面;
		}
	},  
	error : function(data, status, e)//服务器响应失败处理函数  
	{  
	}  
});*/
jQuery.extend({
    createUploadIframe: function(id, uri) {
        // create frame
        var frameId = 'jUploadFrame' + id;
        var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
        if (window.ActiveXObject) {
            if (uri && typeof uri == 'string') {
                iframeHtml += ' src="' + uri + '"';
            } else {
                iframeHtml += ' src="' + 'javascript:false' + '"';
            }
        }
        iframeHtml += ' />';
        jQuery(iframeHtml).appendTo(document.body);
        return jQuery('#' + frameId).get(0);
    },
    createUploadForm: function(id, fileInput, dataInput, data) {
        // create form
        var formId = 'jUploadForm' + id;
        var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
        if (data) {
            for (var i in data) {
                jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
            }
        }
        var oldFile = jQuery('.' + fileInput);
        for (var i = 0; i < oldFile.length; i++) {
            var newFile = jQuery(oldFile[i]).clone();
            newFile[0].files = oldFile[i].files;
            jQuery(newFile).appendTo(form);
        };
        var oldData = jQuery('.' + dataInput);
        for (var i = 0; i < oldData.length; i++) {
            var newData = jQuery(oldData[i]).clone();
            jQuery(newData).appendTo(form);
        };
        // set attributes
        jQuery(form).css('position', 'absolute');
        jQuery(form).css('top', '-1200px');
        jQuery(form).css('left', '-1200px');
        jQuery(form).appendTo('body');
        return form;
    },
    ajaxFileUpload: function(s) {
    	if(s.sendStart){
    		s.sendStart(s);
    	}
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime();
        var form = jQuery.createUploadForm(id, s.fileInput, s.dataInput, (typeof(s.data) == 'undefined' ? false: s.data));
        var io = jQuery.createUploadIframe(id, s.secureuri);
        var frameId = 'jUploadFrame' + id;
        var formId = 'jUploadForm' + id;
        var isTimeOut = false;
        var xml = {
            responseText: null,
            responseXML: null
        };
        if(!s.success){
    		s.success=function(data, status){}
    	}
        if(!s.error){
    		s.error=function(data, status, e){}
    	}
        if(!s.exception){
    		s.exception=function(e){}
    	}
        if ((!s.timeout) || (s.timeout <= 0)) {
            s.timeout = 30 * 1000;
        }
        var timer = null;
        var uploadCallback = function(isTimeout) {
            var isData = false;
            var io = document.getElementById(frameId);
            try {
                if (io.contentWindow) {
                	xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument: io.contentWindow.document;
                    xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerText: null;
                    isData = true;
                } else if (io.contentDocument) {
                    xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerText: null;
                    xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument: io.contentDocument.document;
                    isData = true;
                }
            } catch(e) {
                isData = false;
                if(timer){
                	clearTimeout(timer);
                }
                throw new s.error(xml, "error", e);
            }
            try {
                if (!isData && (isTimeout == "timeout" || isTimeOut)) {
                    s.error("connection timeout", "error", "timeout");
                } else {
                	clearTimeout(timer);
                    var data = jQuery.uploadHttpData(xml, s.dataType);
                    var status = "success";
                    if (s.success) {
                        s.success(data, status);
                    } else {
                         s.error(data, status, e);
                    }
                    if (s.complete){
                    	s.complete(xml, status);
                    }
                }
            } catch(e) {
            	if(timer){
                	clearTimeout(timer);
                }
                throw new s.error(xml, "error" ,e);
            }
            requestDone = true;
            setTimeout(function() {
                try {
                    jQuery(io).remove();
                    jQuery(form).remove();
                } catch(e) {
                	s.exception(e);
                }
            }, 100);
            xml = {
                responseText: null,
                responseXML: null
            };
        }
        timer=setTimeout(function() {
        	isTimeOut = true;
        	uploadCallback("timeout");
        }, s.timeout);
        try {
            var form = jQuery('#' + formId);
            jQuery(form).attr('action', s.url);
            jQuery(form).attr('method', s.type);
            jQuery(form).attr('target', frameId);
            if (form.encoding) {
                jQuery(form).attr('encoding', 'multipart/form-data');
            } else {
                jQuery(form).attr('enctype', 'multipart/form-data');
            }
            jQuery(form).submit();
        } catch(e) {
            throw new s.error(data, "error" ,e);
        }
        jQuery('#' + frameId).load(uploadCallback);
        return {
            abort: function() {}
        };

    },

    uploadHttpData: function(r, type) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML: r.responseText;
        // If the type is "script", eval it in global context
        if (type == "script") jQuery.globalEval(data);
        // Get the JavaScript object, if JSON is used.
        if (type == "json") eval("data = " + data);
        // evaluate scripts within html
        if (type == "html") jQuery("<div>").html(data).evalScripts();
        return data;
    },

});