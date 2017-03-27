(function(win,doc){
	$InputUtil = {
		lang : function(){
			var lan = null;
			var r = window.location.search.substr(1).match(new RegExp("(^|&)lang=([^&]*)(&|$)"));
			if (r != null) lan = decodeURI(r[2]);
			if(lan){
				localStorage.setItem('hibo-language',lan);
			}else{
				lan = localStorage.getItem('hibo-language');
				lan = lan?lan:'zh';
			}
			var y = {
				zh:{
					file:{
						select:'请选择文件'
					},
					date:{
						placeholder:'请选择日期'
					},
					check : {
						isNull:'请输入内容!',
						isPhone : '请输入正确的电话!',
						isEmail : '请输入正确的邮箱!',
						isId : '请输入正确的证件号!',
						isDate:'请选择日期 '
					},
					button : {
						sure : '确定',
						cancle : '取消'
					},
					select : {
						chose : '请选择'
					}
				},
				en:{
					file:{
						select:'Please Chose File'
					},
					date:{
						placeholder:'Please Chose Date'
					},
					check : {
						isNull:'Please Enter Content!',
						isPhone : 'Plase Enter Correctly Phone Numbe!',
						isEmail : 'Plase Enter Correctly Email!',
						isId : 'Plase Enter Correctly ID!',
						isDate:'Plase Chose Date '
					},
					button : {
						sure : 'YES',
						cancle : 'NO'
					},
					select : {
						chose : 'Please Chose'
					}
				}
			}
			var l = y[lan];
			l.lan = lan;
			return l;
		}(),
		CheckUtil : {
			system : {isNull:'isNull',isPhone:'isPhone',isEmail:'isEmail',isId:'isId',isDate:'isDate'},
			check : function(target){
				var newTarget = $(target);
				var isPass = true;
				var i = 0;
				var check = newTarget.attr('check'+i);
				while(check){
					var fn = this.system[check];
					var checkmsg = newTarget.attr('checkm'+i);
					if(fn){
						if(!this[fn](target.value)){
							target.value = '';
							$(target).addClass('placeholder').focus().attr('placeholder',checkmsg?checkmsg:$InputUtil.lang.check[fn]);
							break;
						}
					}else{
						var regx = eval(check);
						if(!regx.test(target.value)){
							target.value = '';
							$(target).addClass('placeholder').focus().attr('placeholder',checkmsg);
							isPass = false;
							break;
						}
					}
					i++;
					check = newTarget.attr('check'+i);
				}
				return isPass;
			},
			isDate : function(s){
				try{
					return new Date(s).getTime();
				}catch(e){
					return false;
				}
			},
			isNull : function(s){
				return !/^\s*$/g.test(s);
			},
			isPhone : function(s){
				return /^1[3|4|5|8|7][0-9]\d{8}$/.test(s);
			},
			isEmail : function(s){
				return /^1[3|4|5|8|7][0-9]\d{8}$/.test(s);
			},
			isId : function(s){
				return /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(s);
			},
		},
		FileInput : function(target){
			target = $(target);
			var that = this;
			target.each(function(){
				var thisTarget = $(this);
				var hibo_file = $('<div class="hibo_file" title="'+that.lang.file.select+'">').insertAfter(thisTarget).click(function(event){
					if(!$(this).hasClass('show')){
						$(this).children('input').click();
						$(this).removeClass('show');
					}
				});
				thisTarget.removeClass('hibo_file').addClass('file_input').attr('type','file');
				thisTarget.appendTo(hibo_file).change(function(){
					var val = $(this).val();
					if(val){
						var p = val.lastIndexOf('\\')+1;
						p = val.length>p?p:0;
						val = val.substring(p);
						$(this).next().addClass('file_add_on').text(val);
						$(this).parent().attr('title',val).addClass('hibo_file_on');
					}else{
						$(this).next().removeClass('file_add_on').text(that.lang.file.select);
						$(this).parent().attr('title',that.lang.file.select).removeClass('hibo_file_on');
					}
				}).click(function(){
					$(this).parent().addClass('show');
				});
				$('<div class="file_add">'+that.lang.file.select+'</div>').appendTo(hibo_file);
			});
		},
		DateInput : function(target){
			target = $(target);
			var that = this;
			target.each(function(){
				var thisTarget = $(this);
				var va = thisTarget.val();
				var hibo_date = $('<div class="hibo_date" title="'+(va?va:that.lang.date.placeholder)+'" >').insertAfter(thisTarget);
				var hibo_date_img = $('<div class="date_img"/>').appendTo(hibo_date);
				var val = thisTarget.val();
				if(val){
					hibo_date.addClass('hibo_date_on');
					hibo_date_img.addClass('date_img_on');
				}
				var dateFmt = thisTarget.attr('dateFmt');
				dateFmt = dateFmt?',dateFmt:\''+dateFmt+'\'':'';
				thisTarget.removeClass('hibo_date').addClass('date_input');
				thisTarget.attr('onclick','WdatePicker({onpicked:function(){$(this).change();},oncleared:function(){$(this).change();}'+dateFmt+'})');
				thisTarget.attr('placeholder',that.lang.date.placeholder);
				thisTarget.css({width:'100%',height:'100%'});
				thisTarget.appendTo(hibo_date);
				this.onchange = function(){
					var val = this.value;
					if(val){
						$(this).prev().addClass('date_img_on');
						$(this).parent().attr('title',val).addClass('hibo_date_on');
					}else{
						$(this).prev().removeClass('date_img_on');
						$(this).parent().attr('title',that.lang.date.placeholder).removeClass('hibo_date_on');
					}
				};
			});
		},
		SimpleInput : function(target){
			target = $(target);
			var that = this;
			target.each(function(){
				var thisTarget = $(this);
				var hibo_simple = $('<div class="hibo_simple" >').insertAfter(thisTarget);
				thisTarget.removeClass('hibo_simple').addClass('simple_input');
				thisTarget.appendTo(hibo_simple).focus(function(){
					$(this).next().show();
				}).blur(function(){
					if(!$(this).hasClass('delete-on'))$(this).next().hide();
					that.CheckUtil.check(this);
				});
				$('<img class="simple_delete" src="'+$.Url('/resources/theme_plugin/img/delete.png')+'"/>').appendTo(hibo_simple).click(function(){
					$(this).prev().val('');
				}).mouseover(function(){
					$(this).prev().addClass('delete-on');
				}).mouseout(function(){
					$(this).prev().removeClass('delete-on');
				});
			});
		},
		SelectInput : function(target){
			target = $(target);
			var that = this;
			var changeDate = function(target){
				target = $(target).closest('.hibo_select');
				var val = '';
				$(target).find('.selected').each(function(){
					var thisV = $(this).children('.option_span').attr('value');
					val = val?(val+","+thisV):thisV;
				});
				var input = $(target).find('.hidden_input');
				input[0].value=val;
				input.change();
			};
			var addSpan = function(option,patentNode,isMultiple){
				var valNode = option.children('.option_span');
				option.addClass('selected');
				if(isMultiple){
					var div_span =$('<div class="div_span">').appendTo(patentNode).mouseover(function(){
						$(this).parent().parent().addClass('hibo_select_on').find('.hidden_input').focus();
						$(this).parent().css({height:'auto'}).parent().find('.options_list').removeClass('show').siblings('.div_img').removeClass('div_img_on');;
					});
					addEvent(div_span);
					$('<div class="div_mspan" title="'+valNode.text()+'" style="max-width:'+(patentNode.width()-24)+'px;">').appendTo(div_span).text(valNode.text());
					$('<img class="div_mimg" value="'+valNode.attr('value')+'" src="'+$.Url('/resources/theme_plugin/img/input_remove.png')+'">').appendTo(div_span).click(function(){
						delSpan(this);
						event.stopPropagation();
					});
				}else{
					patentNode.attr('title',valNode.text()).val(valNode.text());
					option.siblings().removeClass('selected');
				}
				changeDate(option.parent());
			};
			var delSpan = function(target){
				var that = $(target);
				if(that.hasClass('div_mimg')){
					var option_span = that.closest('.hibo_select').find('.option_span[value="'+that.attr('value')+'"]');
					that.parent().remove();
					that = option_span.parent().removeClass('selected');
				}
				if(that.hasClass('option')){
					var v = that.children('.option_span').attr('value');
					that.closest('.hibo_select').find('.div_mimg[value="'+v+'"]').parent().remove();
					that.removeClass('selected');
				}
				changeDate(that.parent());
			}
			var addEvent = function(target){
				var tt = $(target);
				var closest = tt.closest('.hibo_select');
				var ism = closest.attr('isMultiple')=='true';
				tt.mouseover(function(){
					closest.children('.options_list').removeClass('focus');
				}).mouseout(function(){
					if(ism){
						closest.children('.hidden_input').focus();
					}else{
						closest.children('.show_div').focus();
					}
					closest.children('.options_list').addClass('focus');
				});
			};
			target.each(function(){
				var thisTarget = $(this);
				var isMultiple = typeof(thisTarget.attr('multiple'))!='undefined';
				var isEditor = typeof(thisTarget.attr('editor'))!='undefined';
				var isQuery = typeof(thisTarget.attr('query'))!='undefined';
				var hibo_select = $('<div class="hibo_select" isMultiple="'+isMultiple+'">').insertAfter(thisTarget).click(function(event){
					var list = $(this).find('.options_list');
					var ism = $(this).attr('isMultiple')=='true';
					if(!list.hasClass('show')){
						$(this).addClass('hibo_select_on').find('.options_list').addClass('show').addClass('focus').siblings('.div_img').addClass('div_img_on');
						if(ism){
							$(this).find('.hidden_input').focus();
							$(this).find('.show_div').css('height','100%');
						}else{
							$(this).find('.show_div').focus();
						}
					}else{
						if(ism){
							$(this).find('.hidden_input').blur();
						}else{
							$(this).find('.show_div').blur();
						}
					}
					event.stopPropagation();
				});
				var input = $('<input type="text"/>').appendTo(hibo_select).blur(function(){
					var list = $(this).siblings('.options_list');
					if(list.hasClass('focus')){
						$(this).parent().removeClass('hibo_select_on');
						list.removeClass('show').siblings('.div_img').removeClass('div_img_on');
						$(this).siblings('.show_div').css('height','100%');
					}
				});
				for(var i in this.attributes){
					var attr = this.attributes[i];
					input.attr(attr.nodeName,attr.nodeValue);
				}
				input.addClass('hidden_input').removeClass('hibo_select')
				var events = $._data(this, "events");
				if(events){
					for(var ev in events){
						var fno = events[ev];
						for(var i in fno){
							input[ev](fno[i].handler);
						}
					}
				}
				var img = $('<div class="div_img"/>').appendTo(hibo_select);
				var show_div=null;
				if(isMultiple){
					show_div= $('<div class="show_div">').appendTo(hibo_select);
				}else{
					var readonly = !isEditor?'readonly':'';
					show_div= $('<input class="show_div" type="text" '+readonly+'/>').appendTo(hibo_select).blur(function(){
						var list = $(this).siblings('.options_list');
						if(list.hasClass('focus')){
							$(this).parent().removeClass('hibo_select_on');
							list.removeClass('show').siblings('.div_img').removeClass('div_img_on');
						}
					});
				}
				show_div.bind('input propertychange', function() {
					var vv = $(this).text();
					$(this).siblings('.hidden_input')[0].value=vv;
					var options_list = $(this).siblings('.options_list');
					options_list.find('.selected').removeClass('selected').children('.option_img').attr('src',$.Url('/resources/theme_plugin/img/no_ok.png'));
					var option_span = options_list.find('.option_span[value="'+vv+'"]');
					option_span.siblings('.option_img').attr('src',$.Url('/resources/theme_plugin/img/ok.png'));
					option_span.parent().addClass('selected');
				});
				var list = $('<div class="options_list">').appendTo(hibo_select);
				if(isQuery){
					var select_query = $('<div class="query">').appendTo(list);
	 				$('<input class="query_input" type="text"/>').appendTo(select_query).click(function(event){
						event.stopPropagation();
					}).blur(function(){
						var list = $(this).closest('.options_list');
						if(list.hasClass('focus')){
							list.removeClass('show').siblings('.div_img').removeClass('div_img_on');
						}
					});
					addEvent(select_query);
					var query_img = $('<img class="query_img" src="'+$.Url('/resources/theme_plugin/img/sou.png')+'"/>').appendTo(select_query);
					addEvent(query_img);
				}
				var Tt_options = thisTarget.children('option');
				if(Tt_options.length>0){
					var options = $('<div class="options">').appendTo(list);
					Tt_options.each(function(i){
						var Tt_option = $(this);
						var selected = typeof(Tt_option.attr('selected'))!='undefined';
						var text = Tt_option.text();
						text = text?text:that.lang.select.chose;
						var value = this.value?this.value:'';
						var option = $('<div class="option" isMultiple='+isMultiple+'>').appendTo(options).click(function(event){
							var ism = $(this).attr('isMultiple')=='true';
							if($(this).hasClass('selected')){
								delSpan(this);
							}else{
								addSpan(option,show_div,ism);
							}
							if(!ism){
								$(this).closest('.options_list').removeClass('show').siblings('.div_img').removeClass('div_img_on');
							}
							event.stopPropagation();
						}).mouseover(function(){
							$(this).addClass('option_on');
						}).mouseout(function(){
							$(this).removeClass('option_on');
						});
						addEvent(option);
						var option_span = $('<div class="option_span" style="max-width:'+thisTarget.width()+'px;" value="'+value+'" title="'+text+'">'+text+'</div>').appendTo(option);
						//单选默认第一个
						if(i==0&&!isMultiple){
							selected = true;
						}
						if(selected){
							addSpan(option,show_div,isMultiple);
						}
					});
				}
				thisTarget.remove();
			});
		},
		init : function(){
			this.FileInput('.hibo_file');
			this.SimpleInput('.hibo_simple');
			this.DateInput('.hibo_date');
			this.SelectInput('.hibo_select');
		}
	}
	$(function(){
		$InputUtil.init();
	});
})(window,document)