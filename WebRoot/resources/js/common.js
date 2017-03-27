(function($) {
	var zIndex = 100;
	// 消息框
	var $message;
	var messageTimer;
	$.message = function() {
		var message = {};
		if ($.isPlainObject(arguments[0])) {
			message = arguments[0];
		} else if (typeof arguments[0] === "string"
				&& typeof arguments[1] === "string") {
			message.type = arguments[0];
			message.content = arguments[1];
		} else {
			return false;
		}
		if (message.type == null || message.content == null) {
			return false;
		}
		if ($message == null) {
			$message = $('<div class="xxMessage"><div class="messageContent message'
					+ message.type + 'Icon"><\/div><\/div>');
			if (!window.XMLHttpRequest) {
				$message.append('<iframe class="messageIframe"><\/iframe>');
			}
			$message.appendTo("body");
		}
		$message.children("div").removeClass(
				"messagewarnIcon messageerrorIcon messagesuccessIcon")
				.addClass("message" + message.type + "Icon").html(
						message.content);
		$message.css({
			"margin-left" : -parseInt($message.outerWidth() / 2),
			"z-index" : zIndex++
		}).show();
		clearTimeout(messageTimer);
		messageTimer = setTimeout(function() {
			$message.hide();
		}, 3000);
		return $message;
	}
	// 对话框
	$.dialog = function(options) {
		var settings = {
			width : 320,
			height : "auto",
			modal : true,
			ok : "确定",
			cancel : "取消",
			onShow : null,
			onClose : null,
			onOk : null,
			onCancel : null
		};
		$.extend(settings, options);

		if (settings.content == null) {
			return false;
		}

		var $dialog = $('<div class="xxDialog"><\/div>');
		var $dialogTitle;
		var $dialogClose = $('<div class="dialogClose"><\/div>').appendTo($dialog);
		var $dialogContent;
		var $dialogBottom;
		var $dialogOk;
		var $dialogCancel;
		var $dialogOverlay;
		if (settings.title != null) {
			$dialogTitle = $('<div class="dialogTitle"><\/div>').appendTo(
					$dialog);
		}
		if (settings.type != null) {
			$dialogContent = $(
					'<div class="dialogContent dialog' + settings.type
							+ 'Icon"><\/div>').appendTo($dialog);
		} else {
			$dialogContent = $('<div class="dialogContent"><\/div>').appendTo(
					$dialog);
		}
		if (settings.ok != null || settings.cancel != null) {
			$dialogBottom = $('<div class="dialogBottom"><\/div>').appendTo(
					$dialog);
		}
		if (settings.ok != null) {
			$dialogOk = $(
					'<input type="button" class="button" value="' + settings.ok
							+ '" \/>').appendTo($dialogBottom);
		}
		if (settings.cancel != null) {
			$dialogCancel = $(
					'<input type="button" class="button" value="'
							+ settings.cancel + '" \/>')
					.appendTo($dialogBottom);
		}
		if (!window.XMLHttpRequest) {
			$dialog.append('<iframe class="dialogIframe"><\/iframe>');
		}
		$dialog.appendTo("body");
		if (settings.modal) {
			$dialogOverlay = $('<div class="dialogOverlay"><\/div>')
					.insertAfter($dialog);
		}

		var dialogX;
		var dialogY;
		if (settings.title != null) {
			$dialogTitle.text(settings.title);
		}
		$dialogContent.html(settings.content);
		$dialog.css({
			"width" : settings.width,
			"height" : settings.height,
			"margin-left" : -parseInt(settings.width / 2),
			"z-index" : zIndex++
		});
		dialogShow();

		if ($dialogTitle != null) {
			$dialogTitle.mousedown(function(event) {
				$dialog.css({
					"z-index" : zIndex++
				});
				var offset = $(this).offset();
				if (!window.XMLHttpRequest) {
					dialogX = event.clientX - offset.left;
					dialogY = event.clientY - offset.top;
				} else {
					dialogX = event.pageX - offset.left;
					dialogY = event.pageY - offset.top;
				}
				$("body").bind("mousemove", function(event) {
					$dialog.css({
						"top" : event.clientY - dialogY,
						"left" : event.clientX - dialogX,
						"margin" : 0
					});
				});
				return false;
			}).mouseup(function() {
				$("body").unbind("mousemove");
				return false;
			});
		}

		if ($dialogClose != null) {
			$dialogClose.click(function() {
				dialogClose();
				return false;
			});
		}

		if ($dialogOk != null) {
			$dialogOk.click(function() {
				if (settings.onOk && typeof settings.onOk == "function") {
					if (settings.onOk($dialog) != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
				return false;
			});
		}

		if ($dialogCancel != null) {
			$dialogCancel
					.click(function() {
						if (settings.onCancel
								&& typeof settings.onCancel == "function") {
							if (settings.onCancel($dialog) != false) {
								dialogClose();
							}
						} else {
							dialogClose();
						}
						return false;
					});
		}

		function dialogShow() {
			if (settings.onShow && typeof settings.onShow == "function") {
				if (settings.onShow($dialog) != false) {
					$dialog.show();
					$dialogOverlay.show();
				}
			} else {
				$dialog.show();
				$dialogOverlay.show();
			}
		}
		function dialogClose() {
			if (settings.onClose && typeof settings.onClose == "function") {
				if (settings.onClose($dialog) != false) {
					$dialogOverlay.remove();
					$dialog.remove();
				}
			} else {
				$dialogOverlay.remove();
				$dialog.remove();
			}
		}
		return $dialog;
	}

	$.fn.extend({
				browser : function(options) {
					var settings = {
						type : "image",
						title : "选择文件",
						isUpload : true,
						browserUrl : basePath + "main/file/browser",
						uploadUrl : basePath + "main/file/upload",
						callback : null
					};
					$.extend(settings, options);

					var token = getCookie("token");
					var cache = {};
					return this
							.each(function() {
								var browserFrameId = "browserFrame"
										+ (new Date()).valueOf()
										+ Math.floor(Math.random() * 1000000);
								var $browserButton = $(this);
								$browserButton
										.click(function() {
											var $browser = $('<div class="xxBrowser"><\/div>');
											var $browserBar = $(
													'<div class="browserBar"><\/div>')
													.appendTo($browser);
											var $browserFrame;
											var $browserForm;
											var $browserUploadButton;
											var $browserUploadInput;
											var $browserParentButton;
											var $browserOrderType;
											var $browserLoadingIcon;
											var $browserList;
											if (settings.isUpload) {
												$browserFrame = $(
														'<iframe id="'
																+ browserFrameId
																+ '" name="'
																+ browserFrameId
																+ '" style="display: none;"><\/iframe>')
														.appendTo($browserBar);
												$browserForm = $(
														'<form action="'
																+ settings.uploadUrl
																+ '" method="post" encType="multipart/form-data" target="'
																+ browserFrameId
																+ '"><input type="hidden" name="token" value="'
																+ token
																+ '" \/><input type="hidden" name="fileType" value="'
																+ settings.type
																+ '" \/><\/form>')
														.appendTo($browserBar);
												$browserUploadButton = $(
														'<a href="javascript:;" class="browserUploadButton button">'
																+ "上传"
																+ '<\/a>')
														.appendTo($browserForm);
												$browserUploadInput = $(
														'<input type="file" name="file" \/>')
														.appendTo(
																$browserUploadButton);
											}
											$browserParentButton = $(
													'<a href="javascript:;" class="button">'
															+ "上级目录" + '<\/a>')
													.appendTo($browserBar);
											$browserBar.append("排序方式" + ": ");
											$browserOrderType = $(
													'<select name="orderType" class="browserOrderType"><option value="name">'
															+ "名称"
															+ '<\/option><option value="size">'
															+ "大小"
															+ '<\/option><option value="type">'
															+ "类型"
															+ '<\/option><\/select>')
													.appendTo($browserBar);
											$browserLoadingIcon = $(
													'<span class="loadingIcon" style="display: none;">&nbsp;<\/span>')
													.appendTo($browserBar);
											$browserList = $(
													'<div class="browserList"><\/div>')
													.appendTo($browser);

											var $dialog = $.dialog({
												title : settings.title,
												content : $browser,
												width : 470,
												modal : true,
												ok : null,
												cancel : null
											});

											browserList("/");

											function browserList(path) {
												var key = settings.type
														+ "_"
														+ path
														+ "_"
														+ $browserOrderType
																.val();
												if (cache[key] == null) {
													$
															.ajax({
																url : settings.browserUrl,
																type : "GET",
																data : {
																	fileType : settings.type,
																	orderType : $browserOrderType
																			.val(),
																	path : path
																},
																dataType : "json",
																cache : false,
																beforeSend : function() {
																	$browserLoadingIcon
																			.show();
																},
																success : function(
																		data) {
																	createBrowserList(
																			path,
																			data);
																	cache[key] = data;
																},
																complete : function() {
																	$browserLoadingIcon
																			.hide();
																}
															});
												} else {
													createBrowserList(path,
															cache[key]);
												}
											}

											function createBrowserList(path,
													data) {
												var browserListHtml = "";
												$
														.each(
																data,
																function(i,
																		fileInfo) {
																	var iconUrl;
																	var title;
																	if (fileInfo.isDirectory) {
																		iconUrl = basePath
																				+ "/resources/image/folder_icon.gif";
																		title = fileInfo.name;
																	} else if (new RegExp(
																			"^\\S.*\\.(jpg|jpeg|bmp|gif|png)$",
																			"i")
																			.test(fileInfo.name)) {
																		iconUrl = fileInfo.url;
																		title = fileInfo.name
																				+ " ("
																				+ Math
																						.ceil(fileInfo.size / 1024)
																				+ "KB, "
																				+ new Date(
																						fileInfo.lastModified)
																						.toLocaleString()
																				+ ")";
																	} else {
																		iconUrl = basePath
																				+ "/resources/admin/images/file_icon.gif";
																		title = fileInfo.name
																				+ " ("
																				+ Math
																						.ceil(fileInfo.size / 1024)
																				+ "KB, "
																				+ new Date(
																						fileInfo.lastModified)
																						.toLocaleString()
																				+ ")";
																	}
																	browserListHtml += '<div class="browserItem"><img src="'
																			+ iconUrl
																			+ '" title="'
																			+ title
																			+ '" url="'
																			+ fileInfo.url
																			+ '" isDirectory="'
																			+ fileInfo.isDirectory
																			+ '" \/><div>'
																			+ fileInfo.name
																			+ '<\/div><\/div>';
																});
												$browserList
														.html(browserListHtml);

												$browserList
														.find("img")
														.bind(
																"click",
																function() {
																	var $this = $(this);
																	var isDirectory = $this
																			.attr("isDirectory");
																	if (isDirectory == "true") {
																		var name = $this
																				.next()
																				.text();
																		browserList(path
																				+ name
																				+ "/");
																	} else {
																		var url = $this
																				.attr("url");
																		if (settings.input != null) {
																			settings.input
																					.val(url);
																		} else {
																			$browserButton
																					.prev(
																							":text")
																					.val(
																							url);
																		}
																		if (settings.callback != null
																				&& typeof settings.callback == "function") {
																			settings
																					.callback(url);
																		}
																		$dialog
																				.next(
																						".dialogOverlay")
																				.andSelf()
																				.remove();
																	}
																});

												if (path == "/") {
													$browserParentButton
															.unbind("click");
												} else {
													var parentPath = path
															.substr(
																	0,
																	path
																			.replace(
																					/\/$/,
																					"")
																			.lastIndexOf(
																					"/") + 1);
													$browserParentButton
															.unbind("click")
															.bind(
																	"click",
																	function() {
																		browserList(parentPath);
																	});
												}
												$browserOrderType.unbind(
														"change").bind(
														"change", function() {
															browserList(path);
														});
											}

											$browserUploadInput
													.change(function() {
														var allowedUploadExtensions;
														if (settings.type == "flash") {
															allowedUploadExtensions = setting.uploadFlashExtension;
														} else if (settings.type == "media") {
															allowedUploadExtensions = setting.uploadMediaExtension;
														} else if (settings.type == "file") {
															allowedUploadExtensions = setting.uploadFileExtension;
														} else {
															allowedUploadExtensions = "此类型未定义";
														}
														if ($
																.trim(allowedUploadExtensions) == "") { // ||
															// !new
															// RegExp("^\\S.*\\.("
															// +
															// allowedUploadExtensions.replace(/,/g,
															// "|")
															// +
															// ")$",
															// "i").test($browserUploadInput.val())
															$.message("warn",
																	"类型未定义");
															return false;
														}
														$browserLoadingIcon
																.show();
														$browserForm.submit();
													});

											$browserFrame
													.load(function() {
														var text;
														var io = document
																.getElementById(browserFrameId);
														if (io.contentWindow) {
															text = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML
																	: null;
														} else if (io.contentDocument) {
															text = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML
																	: null;
														}
														if ($.trim(text) != "") {
															$browserLoadingIcon
																	.hide();
															var data = $
																	.parseJSON(text);
															if (data.message.type == "success") {
																if (settings.input != null) {
																	settings.input
																			.val(data.url);
																} else {
																	$browserButton
																			.prev(
																					":text")
																			.val(
																					data.url);
																}
																if (settings.callback != null
																		&& typeof settings.callback == "function") {
																	settings
																			.callback(data.url);
																}
																cache = {};
																$dialog
																		.next(
																				".dialogOverlay")
																		.andSelf()
																		.remove();
															} else {
																$
																		.message(data.message);
															}
														}
													});

										});
							});
				}
			});

})(jQuery);

// 获取项目根路径
var rootPath = getContextPath();
// 获取项目路径
function getContextPath() {
	// 获取当前网址，如： http://127.0.0.1:8080/HIBO/common/mall/cart/list
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /HIBO/common/mall/cart/list
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://127.0.0.1:8080
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/HIBO
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

window.onload = function() {
	// 修改请求链接
	changeUrl();
	function changeUrl() {
		var attr = [ 'src', 'action', 'href' ];
		$('.rootPath').each(function() {
			for (var k = 0; k < attr.length; k++) {
				var url = $(this).attr(attr[k]);
				if (url) {
					$(this).attr(attr[k], rootPath + url);
				}
			}
		});
	}
	// 格式化货币
	$('.currency').each(function() {
		var cur = $(this).text();
		var price = currency(cur, true);
		$(this).text(price);
	});
}

// 获取链接上的参数，name:参数名
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}

// 写cookies
function setCookie(name, value, time) {
	document.cookie = name + "=" + escape(value) + ";expires=" + time;
}
// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name))
				+ "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}
// 删除cookies
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null){
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
	}
}

// 货币格式化
function currency(value, halfUp, showSign) {
	if (value != null) {
		var price;
		if (halfUp == "HalfUp") {
			price = (Math.round(value * Math.pow(10, 2)) / Math.pow(10, 2))
					.toFixed(2);
		} else if (halfUp == "roundUp") {
			price = (Math.ceil(value * Math.pow(10, 2)) / Math.pow(10, 2))
					.toFixed(2);
		} else {
			price = (Math.floor(value * Math.pow(10, 2)) / Math.pow(10, 2))
					.toFixed(2);
		}
		if (showSign) {
			price = "￥" + price;
		}
		return price;
	}
}

// 浏览器类型
var browser = {
	versions : function() {
		var u = navigator.userAgent, app = navigator.appVersion;
		return { // 移动终端浏览器版本信息
			trident : u.indexOf('Trident') > -1,
			// IE内核
			presto : u.indexOf('Presto') > -1,
			// opera内核
			webKit : u.indexOf('AppleWebKit') > -1,
			// 苹果、谷歌内核
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
			// 火狐内核
			mobile : !!u.match(/AppleWebKit.*Mobile.*/),
			// 是否为移动终端
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
			// ios终端
			android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
			// android终端或uc浏览器
			iPhone : u.indexOf('iPhone') > -1,
			// 是否为iPhone或者QQHD浏览器
			iPad : u.indexOf('iPad') > -1,
			// 是否iPad
			webApp : u.indexOf('Safari') == -1
		// 是否web应该程序，没有头部与底部
		};
	}(),
	language : (navigator.browserLanguage || navigator.language).toLowerCase()
}
function mainClick(title, id) {
	href = rootPath + '/common/article/article?id=' + id + '&title=' + title;
	window.location.href = href;
}
function urlClick(title,id,isUrl,url,isHead) {
	if(isUrl){
		if(!url){
			return;
		}
		var pi = url.substring(0,4);
		url=url.indexOf('title')>0?url:(url.indexOf('?')>0?(url+'&title='+title):(url+'?title='+title));
		if(!('http'==pi||'www.'==pi)){
			url=rootPath+url;
		}
	}else{
		url = rootPath + '/common/article/article?id=' + id + '&title=' + title;
	}
	window.location.href = url + ((isHead==false||isHead=='false')?("&isHead="+isHead):"");
}
function ciClick(title, category, articleCategory) {
	href = rootPath + '/common/article/categroyArticle?categroy=' + category
			+ '&articleCategory=' + articleCategory + '&title=' + title;
	window.location.href = href;
}

$.extend({
	/** 自定义Map*/
	hMap:function() {
        var hmap = this.map = {};
        this.get = function(key) {
        	key.indexOf(/[0-9]/)?'H'+key:key;
            key = key.replace(/\=|\.|]|\[|,/g,'_' );
            var val = eval("hmap." + key);
            return (typeof val === undefined) ? null : val;
        };
        this.put = function(key, value) {
        	key.indexOf(/[0-9]/)?'H'+key:key;
            key = key.replace(/\=|\.|]|\[|,/g,'_' );
            eval("hmap." + key + "=value");
        };
        this.remove = function (key){
        	key.indexOf(/[0-9]/)?'H'+key:key;
        	key = key.replace(/\=|\.|]|\[|,/g,'_' );
        	delete this.map[key];
        };
        this.size = function(){
        	var i = 0;
        	for(m in this.map){
        		i++;
        	}
        	return i;
        }
    },
	//var dateStr = $.DateFomart(new Date().getTime()).toText("yyyy年MM月dd日 周W HH时mm分ss秒zzz毫秒");
	//var dateStr = $.DateFomart(new Date()).toText("yyyy年MM月dd日 周W HH时mm分ss秒zzz毫秒");
	//var dateStr = $.DateFomart('2016-04-07 18:05:38:695').toText("yyyy年MM月dd日 周W HH时mm分ss秒zzz毫秒");
	//var date = $.DateFomart(dateStr).toDate();
	//var time = $.DateFomart(dateStr).toTime();
	DateFomart:function(date) {
		if(!date){
			date = new Date();
		}
		if ('number' == typeof (date)) {
			date = new Date(date);
		}
		if (date instanceof Date) {
			var Week = [ '日', '一', '二', '三', '四', '五', '六' ];
			this.year = date.getFullYear().toString(); // 获取完整的年份(4位,1970-????)
			this.month = (date.getMonth() + 1).toString(); // 获取当前月份(0-11,0代表1月)
			this.day = date.getDate().toString(); // 获取当前日(1-31)
			this.Week = Week[date.getDay()].toString(); // 获取当前星期X(0-6,0代表星期天)
			this.week = date.getDay().toString(); // 获取当前星期X(0-6,0代表星期天)
			this.Hour = date.getHours().toString(); // 获取当前小时数(0-23)
			this.hour = (this.Hour > 12 ? this.Hour - 12 : this.Hour).toString(); // 获取当前小时数(0-23)
			this.minute = date.getMinutes().toString(); // 获取当前分钟数(0-59)
			this.second = date.getSeconds().toString(); // 获取当前秒数(0-59)
			this.ss = date.getMilliseconds().toString(); // 获取当前毫秒数(0-999)
		}
		getTen = function(num, n , s) {
			num = num + "";
			for (var i = 0; i < (n - num.length); i++) {
				num = s + num;
			}
			return num;
		};
		this.toText = function(fomart) {
			if (date instanceof Date) {
				fomart = fomart.replace(/yyyy|YYYY/, this.year);
				fomart = fomart.replace(/yyy|YYY/, this.year.substring(1,4));
				fomart = fomart.replace(/yy|YY/, this.year.substring(2,4));
				fomart = fomart.replace(/MM/, getTen(this.month, 2, '0'));
				fomart = fomart.replace(/M/, this.month);
				fomart = fomart.replace(/DD|dd/, getTen(this.day, 2, '0'));
				fomart = fomart.replace(/D|d/, this.day);
				fomart = fomart.replace(/W/, this.Week);
				fomart = fomart.replace(/w/, this.week == 0 ? 8 : this.week);
				fomart = fomart.replace(/HH/, getTen(this.Hour, 2, '0'));
				fomart = fomart.replace(/hh/, getTen(this.hour, 2, '0'));
				fomart = fomart.replace(/H/, this.Hour);
				fomart = fomart.replace(/h/, this.hour);
				fomart = fomart.replace(/mm/, getTen(this.minute, 2, '0'));
				fomart = fomart.replace(/m/, this.minute);
				fomart = fomart.replace(/ss/, getTen(this.second, 2, '0'));
				fomart = fomart.replace(/s/, this.second);
				fomart = fomart.replace(/zzz/, getTen(this.ss, 3, '0'));
				fomart = fomart.replace(/zz/, getTen(this.ss, 2, '0'));
				fomart = fomart.replace(/z/, this.ss);
			}
			if ('string' == typeof (date)) {
				date = date.replace(/-/g, "/");
				date = new Date(date);
				fomart = $.DateFomart(date).toText(fomart);
			}
			return fomart;
		};
		this.toCh = function(fomart) {
			if (date instanceof Date) {
				this.year = getCh(this.year,1);
				this.month = getCh(this.month,2);
				this.day = getCh(this.day,2);
				this.Hour = getCh(this.Hour,3);
				this.hour = getCh(this.hour,3);
				this.minute = getCh(this.minute,3);
				this.second = getCh(this.second,3);
				this.ss = getCh(this.ss,3);
				fomart = fomart.replace(/yyyy|YYYY/, this.year);
				fomart = fomart.replace(/yyy|YYY/, this.year.substring(1,4));
				fomart = fomart.replace(/yy|YY/, this.year.substring(2,4));
				fomart = fomart.replace(/MM|M/, this.month);
				fomart = fomart.replace(/DD|dd/, getTen(this.day, 2, '0'));
				fomart = fomart.replace(/D|d/, this.day);
				fomart = fomart.replace(/W/, this.Week);
				fomart = fomart.replace(/w/, this.week == 0 ? 8 : this.week);
				fomart = fomart.replace(/HH/, getTen(this.Hour, 2, '0'));
				fomart = fomart.replace(/hh/, getTen(this.hour, 2, '0'));
				fomart = fomart.replace(/H/, this.Hour);
				fomart = fomart.replace(/h/, this.hour);
				fomart = fomart.replace(/mm|m/, this.minute);
				fomart = fomart.replace(/ss|s/, this.second);
				fomart = fomart.replace(/zzz|zz|z/, this.ss);
			}
			if ('string' == typeof (date)) {
				date = date.replace(/-/g, "/");
				date = new Date(date);
				fomart = $.DateFomart(date).toText(fomart);
			}
			return fomart;
		};
		var ch = [ '〇', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '廿'];
		var dv = ['','十','百','千','万'];
		getCh=function(str,type){
			var reStr = '';
			if(1==type){
				for (var i = 0; i < str.length; i++) {
					reStr = reStr + ch[str.charAt(i)];
				}
			}else if(2==type){
				var p = parseInt(str);
				if(p<=10){
					reStr = ch[p];
				}else if(p<=20){
					reStr = (p==20?'二十':('十'+ch[str.charAt(1)]));
				}else if(p<100){
					reStr = (ch[str.charAt(0)] + '十' + ch[str.charAt(1)]);
				}
			}else if(3==type){
				for (var i = 0; i < str.length; i++) {
					var v = i==0&&str.length==2&&str.charAt(i)==1?'':ch[str.charAt(i)]
					reStr = reStr + v + dv[str.length-i-1];
				}
			}
			return reStr;
		}
		this.toDate = function() {
			if ('string' == typeof (date)) {
				date = date.replace(/-/g, "/");
				date = new Date(date);
				return date;
			}else if(date instanceof Date){
				return date;
			}
		};
		this.toTime = function() {
			if ('string' == typeof (date)) {
				date = date.replace(/-/g, "/");
				date = new Date(date);
				return date.getTime();
			}else if(date instanceof Date){
				return date.getTime();
			}
		};
		return this;
	},
	/*$.sendSms({
		wait:30,//时间
		timeBox:$('.times'),//秒数倒数
		code:$('.code'),//验证码输入框
		message:'message',//信息框可以是函数、class、id
		phone:$('.userPhone'),//电话号
		sbtn:$('.send')//点击发送的按钮
		tempNo:1008,//模板号
		url:rootPath+'/common/sms/ajaxSendSMS'//发送地址
	});*/
	canSend:true,
	waitTime:0,
	time:function(timeBox,vals,waitTime,code) {//time是时间$('.time') 、val是获取里面的$('.code')、btn值的是输入手机号的那个input
	            if (waitTime == 0) {
	            	if(code.length>0){
	            		//code.attr('disabled','disabled');
	            	}
	            	if(vals.length>0){
		                vals.val("发送验证码")
		                vals.text('重新发送');
	                }
	            	if(timeBox.length>0){
	            		timeBox.hide();
	            	}
	                $.canSend = true;
	            } else { 
	            	if(code.length>0){
	            		code.removeAttr('disabled');
	            	}
	            	if(timeBox.length>0){
	            		timeBox.text(waitTime + 's');
	            	}
	            	$.waitTime = waitTime--;
	                setTimeout(function() {
	                	$.time(timeBox,vals,waitTime,code);
	                },1000)
	            }
	},
	sendSms:function(s){
		var message;
		if(s.message&&typeof(s.message)=='function'){
			message = s.message;
		}else if(typeof(s.message)=='string'){
			message = function(text){
				$(s.message).html(text);
			}
		}else{
			var color = s.color?s.color:'white';
			var msgDiv='';
			if(s.isPc){
				msgDiv+='<div id="smsError" style="width:100%;height:50px;position:fixed;top:30%;z-index:999;display:none;">';
				msgDiv+='<div id="sms_error_mask" style="background:#000;opacity: .6;filter: alpha(opacity=60%);height:100%;position:absolute;left:0;top:0;"></div>';
				msgDiv+='<p id="smsMessage" style="position:absolute;left:0;top:11px;font:18px \'微软雅黑\';color:'+color+';text-align:center;vertical-align:middle;"></p>';
				msgDiv+='</div>';
			}else{
				msgDiv+='<div id="smsError" style="width:6rem;height:.88rem;position:fixed;left:10%;top:10%;z-index:10;display:none;">';
				msgDiv+='<div id="sms_error_mask" style="background:#000;opacity: .6;filter: alpha(opacity=60%);width:100%;height:100%;position:absolute;left:0;top:0;"></div>';
				msgDiv+='<p id="smsMessage" style="width:100%;height:100%;position:absolute;left:0;top:0;font:.32rem/.88rem \'微软雅黑\';color:'+color+';text-align:center;"></p>';
				msgDiv+='</div>';
			}
			$(msgDiv).appendTo(document.body)
			message = $.fadeMessage;
		}
		//点击验证码发送请求
		$(s.sbtn).click(function(){
			var wait = s.wait?s.wait:60;
			if(!$.canSend){
				message("验证码已发送,"+$.waitTime+"s后可再次发送!");
				return;
			}
			var userPhone = $(s.phone).val();
			if($.hiboTest.testPhone(userPhone)){
				$.canSend = false;
				var url=s.url?s.url:rootPath+'/common/bas/sms/ajaxSendSMS';
				var type = s.type?type:'post';
				var tempNo = s.tempNo?s.tempNo:1008;
				$.ajax({
					url : url,
					type : type,
					data : {tempNo : tempNo,phone:userPhone},
					dataType : 'json',
					success : function(data) {
						if(data&&data.success){
							$(s.timeBox).show();
							$.time($(s.timeBox),$(s.sbtn),wait,$(s.code));
						}else if(data){
							$.canSend = true;
							$(s.timeBox).hide();
							message(data.message);
						}else{
							message("短信发送失败!");
						}
					},
					error:function(e){
						$.canSend = true;
					}
				});
			}else{
				$(s.times).hide();
				message("请输入正确手机号!");
			}
		});
	},
	fadeMessage:function(text){
		$('#smsError').fadeIn(0,function(){
			var zWidth = $('#smsError').show().width();
			var width = $('#smsMessage').text(text).width();
			$('#sms_error_mask').width(width+40);
			var l = (zWidth-width)/2;
			$('#smsMessage').css('left',l);
			$('#sms_error_mask').css('left',(l-20));
			$('#smsError').fadeOut(3000);
	    });
	},
	hiboTest : {
		testPhone : function(phone) {
			var isPhone = /^1[3|4|5|8|7][0-9]\d{8}$/;
			return isPhone.test(phone);
		},
		testIdCard : function(idNo) {
			var isIDCard = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
			return isIDCard.test(idNo);
		},
		testMail : function(mail) {
			var isMail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			return isMail.test(mail);
		},
	},
	//随机数
	random : {
		getRanStr : function(n){
			n = n?n:1;
			var s = "";
			for (var i = 0; i < n; i++) {
				s = s + Math.floor(Math.random()*10);
			}
			return s;
		}
	},
	ImgZip : {
		then : function(file,s,fn){
			try{
				 var reader = new FileReader();
			            // 绑定load事件自动回调函数 
			            reader.onload = function(e){ 
			            var image = new Image();
			            image.src=e.target.result;
				            image.onload = function(evt){
				                var im = evt.target;
				                var iWidth = im.width;
				                 var iHeight = im.height;
				                 if(s.width&&s.width<iWidth){
				                	 s.height = s.width/iWidth*iHeight;
				                 }else if(s.height&&s.height<iHeight){
				                	 s.width = s.height/iHeight*iWidth
				                 }else if(iWidth>1920){
				                	 s.width = 1920;
				                	 s.height = s.width/iWidth*iHeight;
				                 }else{
				                	 s.width = iWidth;
				                	 s.height = iHeight;
				                 }
				                 s.width = Math.ceil(s.width);
				                 s.height = Math.ceil(s.height)
				                var canvas = document.createElement('canvas');
				                canvas.width = s.width;
				                canvas.height = s.height;
				                var ctx = canvas.getContext("2d");
				                ctx.drawImage(image, 0, 0, s.width, s.height);
				                fn = typeof(s)=="function"?s:fn;
				                fn({base64:canvas.toDataURL("image/jpeg",0.5),width:s.width,height:s.height});
				            }
			            }; 
			            reader.readAsDataURL(file); 
			}catch(e){
				console.log(e);
			}
		}	
	},
	AppShare : function(){
		
	},
});
(function(doc, win) {
	var docEl = doc.documentElement, resizeEvt = 'orientationchange' in window ? 'orientationchange': 'resize', recalc = function() {
		var clientWidth = docEl.clientWidth;
		if (!clientWidth)
			return;
		docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
	};
	if (!doc.addEventListener)
		return;
	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);