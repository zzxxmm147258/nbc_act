$(function() {
	var Heditor = $('.editor');
	var h = Heditor.css('height');
	var w = Heditor.css('width');
	Heditor.summernote({
    	height:h,
    	minHeight:Heditor.css('min-height'),
    	maxHeight:Heditor.css('max-height'),
		toolbar: [
			['style', ['style','bold','italic','underline','strikethrough','superscript','subscript','clear']],
			['fontname', ['fontname','fontsize']],
			['color', ['color']],
			['para', ['ul', 'ol', 'paragraph']],
			['height',['height']],
			['table', ['table']],
			['insert', ['hr','link', 'picture', 'video']],
			['view', ['undo','redo','fullscreen', 'codeview']]
		],
		fontNames: [
            '宋体','微软雅黑','Arial', 'Arial Black', 'Comic Sans MS', 'Courier New',
            'Helvetica Neue', 'Helvetica', 'Impact', 'Lucida Grande',
            'Tahoma', 'Times New Roman', 'Verdana'
        ],
		lang: 'zh-CN',
		//placeholder : Heditor.attr("placeholder"),
		dialogsFade : true,
		dialogsInBody : true,
		callbacks:{
			onInit : function($editor){
				$editor.editor.css({width:w});
			},
			onImageUpload: function(files) {
				var formData = new FormData();
			    for(f in files){
			        formData.append("files", files[f]);
			    }
			    $.ajax({
			        data: formData,
			        type: "POST",
			        url: editor_path+"/main/file/uploadFiles",
			        cache: false,
			        contentType: false,
			        processData: false,
			        success: function(datas) {
			            if(datas){
							datas = JSON.parse(datas);
							for ( var i in datas) {
								if(datas[i].success){
									$('.editor').summernote('editor.insertImage',datas[i].datas);
								}
							}
			            }
			        },
			        error: function() {
			            console.log("uploadError");
			        }
			    });
			},
		    onChange: function (contents) {
		    	$(this).text(contents);
		    }
		},
	});
});