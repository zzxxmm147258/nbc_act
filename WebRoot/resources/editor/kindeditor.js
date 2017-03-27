var editor_scripts = document.scripts;
var editor_styleSheets = document.styleSheets
var editor_url = window.location.href;
var editor_pathName = window.document.location.pathname;
var editor_path = editor_url.substring(0, editor_url.indexOf(editor_pathName)) + editor_pathName.substring(0, editor_pathName.substr(1).indexOf('/') + 1);
var editor_bcs = (editor_path+'/resources/bootstrap/css/bootstrap.css');
var editor_bjs = (editor_path+'/resources/bootstrap/js/bootstrap.js');
for ( var i in editor_scripts) {
	var baseURI = editor_scripts[i].src;
	if(baseURI){
		var p = baseURI.indexOf('?');
		baseURI = p>0?(baseURI.substring(0, p)):baseURI;
		if(editor_bjs==baseURI){
			editor_bjs = null;
		}
	}
}
for ( var i in editor_styleSheets) {
	var baseURI = editor_styleSheets[i].href;
	if(baseURI){
		var p = baseURI.indexOf('?');
		baseURI = p>0?(baseURI.substring(0, p)):baseURI;
		if(editor_bcs==baseURI){
			editor_bcs = null;
		}
	}
}
if(editor_bcs){
	document.write('<link rel="stylesheet" type="text/css" href="'+ editor_bcs + '"/>');
}
if(editor_bjs){
	document.write('<script type="text/javascript" src="'+ editor_bjs + '"><\/script>');
}
document.write('<link rel="stylesheet" type="text/css" href="'+ editor_path + '/resources/editor/summernote.css"/>');
document.write('<script type="text/javascript" src="'+ editor_path + '/resources/editor/summernote.js"><\/script>');
document.write('<script type="text/javascript" src="'+ editor_path + '/resources/editor/summernote-zh-CN.js"><\/script>');
document.write('<script type="text/javascript" src="'+ editor_path + '/resources/editor/sumeditor.js"><\/script>');
