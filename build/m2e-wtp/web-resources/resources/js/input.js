// 编辑器
function addKindEditor(){
	if(typeof(KindEditor) != "undefined") {
		KindEditor.ready(function(K) {
			editor = K.create("#editor", {
				height: "350px",
				items: [
					"source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste",
					"plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright",
					"justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript",
					"superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/",
					"formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold",
					"italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image",
					"flash", "media", "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak",
					"anchor", "link", "unlink"
				],
				langType: "zh_CN",
				syncType: "form",
				filterMode: false,
				pagebreakHtml: '<hr class="pageBreak" \/>',
				allowFileManager: true,
				filePostName: "file",
				fileManagerJson: ""+basePath + "main/file/browser",
				uploadJson: ""+basePath + "main/file/upload",
				uploadImageExtension: "jpg,jpeg,bmp,gif,png",
				uploadFlashExtension: "swf,flv",
				uploadMediaExtension: "swf,flv,mp3,wav,avi,rm,rmvb",
				uploadFileExtension: "zip,rar,7z,doc,docx,xls,xlsx,ppt,pptx",
				extraFileUploadParams: {
					token: getCookie("token")
				},
				afterChange: function() {
					this.sync();
				}
			});
		});
	}
}

$().ready(function(){
	addKindEditor()
});
