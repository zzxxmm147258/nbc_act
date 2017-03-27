(function(win,doc){
	$HPageUtil = {
		buttons : {add:'btn_add',update:'btn_update',delete:'btn_delete',toolBar:'hibo_tools'},
		dataUtils : {
			extend : function(o,n){
				o = o?o:{},n=n?n:{};
				for(var e in o){
					n[e] = o[e];
				}
				return n;
			},
			extendAttr : function(ot,nt){
				for(var i in nt.attributes){
					var attr = this.attributes[i];
					ot.setAttribute(attr.nodeName,attr.nodeValue);
				}
				return ot;
			}
		},
		createTarget : function(v){
			var tar = null;
			var position = v.position?v.position:'inner';
			var target = '<'+(v.target?v.target:'div')+'>';
			if(position=='before'){
				tar = $(target).insertBefore(v.attach);
			}else if(position=='after'){
				tar = $(target).insertAfter(v.attach);
			}else{
				tar = $(target).appendTo(v.attach);
			}
			if(v.attrs){
				for(var i in v.attrs){
					tar.attr(i,v.attrs[i]);
				}
			}
			if(v.html)tar.html(v.html);
			var clazz = this.buttons[v.type];
			if(clazz)tar.addClass(clazz);
			if(typeof(v.fns)=='object'){
				for(var name in v.fns){
					var fn = v.fns[name];
					if(typeof(fn)=='function'){
						tar.on(name,fn);
					}
				}
			}
			return tar;
		},
		createBtn : function(v){
			return this.createTarget(v);
		},
		createBtns : function(bs,attach){
			var buttons = {};
			for(var i in bs){
				var vs = bs[i];
				vs.attach = vs.attach?vs.attach:attach;
				vs.attrs = vs.attrs?vs.attrs:{};
				vs.attrs.id = vs.attrs.id?vs.attrs.id:('btn'+new Date().getTime()+i);
				buttons[vs.attrs.id] = this.createBtn(vs);
			}
			return buttons;
		},
		createToolBar : function(v){
			var rn = {type:'TOOLBAR'};
			rn.id = v.attrs.id = v.attrs.id?v.attrs.id:('toolBar' + new Date().getTime()+i);
			rn[v.attrs.id] = this.createTarget(v);
			rn.buttons = this.createBtns(v.buttons,rn[v.attrs.id]);
			return rn;
		},
		createTable : function(tp){
			var o = {
				type:'TABLE',
				insertTable : function(t){
					this.maxrow = 0;
					t.attrs = t.attrs?t.attrs:{};
					var tableId = this.id = t.attrs.id?t.attrs.id:('table'+new Date().getTime());
					delete t.attrs.id;
					var tbdiv = $HPageUtil.createTarget(t);
					tbdiv.addClass('hibo_table');
					this.table = $HPageUtil.createTarget({
						target:'table',
						attach:tbdiv,
						attrs : {id:tableId,class:'table table-bordered'}
					});
					this.thead = $HPageUtil.createTarget({
						target:'thead',
						attach:this.table,
						attrs : {class:'thead'}
					});
					this.tbody = $HPageUtil.createTarget({
						target:'tbody',
						attach:this.table,
						attrs : {class:'tbody'}
					});
					var columns = this.columns = t.columns?t.columns:[];
					var datas = $.type(t.datas)=='array'?t.datas:[];
					for(var row=-1;row<datas.length;row++){
						if(row<0){
							var data = {};
							var cols = []
							for(var i=0;i<columns.length;i++){
								var col = columns[i];
								data[col.name] = col.comment;
								cols[i] = {name:col.name,hidden:col.hidden,html:('<div class="col_div">'+col.comment+'</div>')};
							}
							var thead_tr = this.insertRow({tableid:tableId,attrs:{row:'th',class:'allBox'},select:t.select,attach:this.thead,columns:cols,data:data});
						}else{
							this.maxrow++;
							var rowData = datas[row];
							var thead_tr = this.insertRow({tableid:tableId,attrs:{row:this.maxrow},select:t.select,attach:this.tbody,columns:columns,data:datas[row]});
							thead_tr.click(function(){
								$(this).parent().find('.hibo_box').removeClass('on');
								$(this).children('td[column="selectBox"]').click();
							});
						}
					}
					this.params = t;
					return this;
				},
				insertRow : function(r){
					r.attrs = r.attrs?r.attrs:{};
					if($.type(r.attrs.row)=='undefined'){
						r.attrs.row = 0;
					}
					r.target = 'tr';
					r.attrs.id = r.tableid+'-'+r.attrs.row;
					var tr = $HPageUtil.createTarget(r);
					tr.addClass('row');
					var columns = r.columns.slice(0);
					columns.unshift({name:'selectBox',html:'<div class="hibo_box"></div>',hidden:r.select==false});
					var _this = this;
					for(var i=0;i<columns.length;i++){
						var colo = columns[i];
						var td = this.insertColumn({tableid:r.tableid,attrs:{row:r.attrs.row,col:i},column:colo,data:r.data,attach:tr});
						if(colo.name=='selectBox'){
							td.css({width:'36px'}).children().addClass('select');
							td.click(function(event){
								var box_td = $(this);
								var box_table = box_td.closest('table');
								var tbody = box_table.children('.tbody');
								tbody.children('.row').removeClass('select');
								var box = box_td.find('.hibo_box');
								var boxs = tbody.find('.hibo_box');
								var count = -1;
								if(box_td.parent().hasClass('allBox')){
									boxs.toggleClass('on',!box.hasClass('on'));
								}else{
									box.toggleClass('on');
								}
								var selectBoxs = tbody.find('.hibo_box[class*="on"]');
								if(boxs.length==selectBoxs.length){
									box_table.children('.thead').find('.hibo_box').addClass('on');
								}else{
									box_table.children('.thead').find('.hibo_box').removeClass('on');
								}
								var sbox_trs = selectBoxs.closest('tr').addClass('select');
								$HPageUtil.attachEvent(_this,'rowSelected',{selectTr:sbox_trs,allTr:boxs});
								event.stopPropagation();
							});
						}
					}
					return tr;
				},
				insertColumn : function(c){
					c.attrs = c.attrs?c.attrs:{};
					if($.type(c.attrs.col)=='undefined'){
						c.attrs.col = 0;
					}
					c.attrs.id = c.tableid+'-'+c.attrs.row+'-'+c.attrs.col;
					c.attrs.column = c.column.name;
					c.attrs.col = c.attrs.col;
					c.target = 'td'
					c.html = '<div class="col_div">' + (c.column.html?c.column.html:'$['+c.column.name+']') + '</div>';
					var v = c.data[c.column.name];
					v = $.type(v)=='string'?v.replace(/</g,'&lt').replace(/>/g,'&gt'):v;
					var reg = eval('/\\$\\['+c.column.name+'\\]/ig');
					if(c.column.list){
						c.html = '<div class="col_div">';
						if(/^bean/ig.test(c.column.list)){
							if($.type(v)=='array'){
								for(var i in v){
									var value = v[i];
									var as = c.column.list.split('.');
									var colname = c.column.name;
									try{
										for(var k=1;k<as.length;k++){
											colname = as[k];
											var vl = value[colname];
											if(vl){
												value=vl;
											}else{
												break;
											}
										}
										if(value){
											if(c.column.dateFmt)value = $.DateFomart(value,'').toText(c.column.dateFmt);
											if(c.column.dict)value = c.column.dict[value]?c.column.dict[v]:value;
											var regs = eval('/\\$\\['+colname+'\\]/ig');
											c.html = c.html + ((c.column.html?c.column.html:'$['+colname+']')).replace(regs,value);
										}
									}catch(e){
											console.log(e);
									}
								}
							}
						}else{
							var as = v?v.split(c.column.list):v;
							for(var k in as){
								if(c.column.dateFmt)as[k] = $.DateFomart(as[k],'').toText(c.column.dateFmt);
								if(c.column.dict)v = c.column.dict[v]?c.column.dict[v]:v;
								if(as[k])c.html = c.html + ((c.column.html?c.column.html:'$['+c.column.name+']')).replace(reg,as[k]);
							}
						}
						c.html = c.html + '</div>';
					}else{
						if(c.column.dateFmt)v = $.DateFomart(v,'').toText(c.column.dateFmt);
						if(c.column.dict)v = c.column.dict[v]?c.column.dict[v]:v;
						c.html = c.html.replace(reg,v);
					}
					var exec = /\$\[.*\]/i.exec(c.html);
					if(exec){
						execs = exec[0].split(/\$\[/ig);
						for ( var i in execs) {
							var na = execs[i].replace(/\].*/ig,'');
							if(na)c.html = c.html.replace(eval('/\\$\\['+na+'\\]/ig'),c.data[na]);
						}
					}
					var td = $HPageUtil.createTarget(c);
					td.addClass('col');
					if(c.column.hidden){
						td.addClass('hidden');
					}
					return td;
				},
				destroy : function(){
					$HPageUtil.removeEvent(this);
					this.remove();
					for(var n in this){delete this[n]};
				},
				remove : function(){
					if(this.table){
						this.table.parent().remove();
					}
					return this;
				},
				rowSelected : function(fn){
					return fn?$HPageUtil.addEvent(this,'rowSelected',fn):$HPageUtil.attachEvent(this,'rowSelected',{selectTr:[],allTr:[]});
				}
			};
			return o.insertTable(tp);
		},
		createPage : function(ps){
			var p = {
				type:'PAGE',
				createPage : function(p){
					var _this = this;
					_this.id = p.id?p.id:(_this.type + '-' + new Date().getTime());
					var num = p.num?p.num:6;
					_this.pageinfo = {pageBar:$('<div id="'+_this.id+'" class="hibo_paginon">').appendTo(p.attach)};
					var total = p.total?p.total:0;
					_this.pageinfo.total = $('<div class="hibo_pagetotal">共<input class="pagetotal page_datas" name="total" type="text" value='+total+' readonly>条</div>').appendTo(_this.pageinfo.pageBar).children('.pagetotal');
					var limit = p.limit?p.limit:10;
					_this.pageinfo.limit = $('<div class="hibo_pagelimit"><span class="limit_span">每页显示</span><input class="ht_numin pagelimit page_datas" type="text" name="limit" value='+limit+' /></div>').appendTo(_this.pageinfo.pageBar).children('.pagelimit');
					var pg = p.page?p.page:1;
					_this.pageinfo.pages = $('<div class="hibo_pages"><ul class="pagination pagination-lg"><li><a class="lt">&lt;</a></li><li><a class="page on" page="1">1</a></li><li><a class="gt">&gt;</a></li></ul></div>').appendTo(_this.pageinfo.pageBar);
					var pgo = $('<div class="hibo_pagego"><span class="page_span">当前第</span><input class="pagenum page_datas" type="text" name="page" value='+pg+' /><span class="page_span">页</span><span class="hibo_go">GO</span></div>').appendTo(_this.pageinfo.pageBar);
					_this.params = {num:num,page:pg,total:total,limit:limit,attach:_this.pageinfo.pages};
					pgo.children('.hibo_go').click(function(){
						_this.reflesh();
					});
					return this;
				},
				addPages : function(s){
					var maxPage = Math.ceil(s.total/s.limit);
					maxPage = maxPage==0?1:maxPage;
					var num = Math.min(maxPage,s.num);
					var page = Math.min(maxPage,s.page);
					var pMax = Math.min(Math.max((page + Math.ceil(num/2)),num),maxPage);
					var pMin = Math.max((pMax-num),0)+1;
					var _this = this;
					$(s.attach).empty().siblings('.hibo_pagego').children('.pagenum').val(s.page);
					var pagination = $('<ul class="pagination pagination-lg">').appendTo(s.attach);
					$('<li><a class="lt">&lt;&lt;</a></li>').appendTo(pagination).click(function(){
						var pagenum = $(this).closest('.hibo_paginon').find('.pagenum');
						pagenum.val(1);
						_this.reflesh();
					});
					$('<li><a class="lt">&lt;</a></li>').appendTo(pagination).click(function(){
						var pagenum = $(this).closest('.hibo_paginon').find('.pagenum');
						var v = $(this).siblings('li').children('.on').attr('page');
						if(v>1){
							pagenum.val(--v);
							_this.reflesh();
						}
					});
					for(var i = pMin;i<=pMax;i++){
						var li = $('<li><a class="page '+(i==page?'on':'')+'" page='+i+'>'+i+'</a></li>').appendTo(pagination).click(function(){
							//if(!$(this).children('.page').hasClass('on')){
								var page = $(this).children('.page').attr('page');
								$(this).closest('.hibo_paginon').find('.pagenum').val(page);
								_this.reflesh();
							//}
						});
					}
					$('<li><a class="gt">&gt;</a></li>').appendTo(pagination).click(function(){
						var pagenum = $(this).closest('.hibo_paginon').find('.pagenum');
						var total = $(this).closest('.hibo_paginon').find('.pagetotal');
						var v = $(this).siblings('li').children('.on').attr('page');
						if(total.length>0){
							if(v<total.val()/s.limit){
								pagenum.val(++v);
								_this.reflesh(s);
							}
						}else{
							pagenum.val(++v);
							_this.reflesh();
						}
					});
					$('<li><a class="gt">&gt;&gt;</a></li>').appendTo(pagination).click(function(){
						var pagenum = $(this).closest('.hibo_paginon').find('.pagenum');
						pagenum.val(maxPage);
						_this.reflesh();
					});
					return this
				},
				changeParams : function(s){
					$(s.attach).closest('.hibo_paginon').find('.page_datas').each(function(){
						var name = $(this).attr('name');
						var sv = s[name];
						if($.type(sv)=='number' && this.value != sv){
							this.value = sv;
						}
					});
					if(s.reflesh==false||s.reflesh=='false')return this;
					return this.addPages(this.params);
				},
				reflesh : function(){
					var _this = this;
					$(this.params.attach).closest('.hibo_paginon').find('.page_datas').each(function(){
						var name = $(this).attr('name');
						var value = this.value?this.value:s[name];
						_this.params[name] = value;
					});
					return $HPageUtil.attachEvent(this,'change',this.params).changeParams(this.params);
				},
				destroy : function(){
					$HPageUtil.removeEvent(this);
					this.remove();
					for(var n in this){delete this[n]};
				},
				remove : function(){
					if(this.pageinfo){
						pageinfo.pageBar.remove();
					}
					return this;
				},
				change : function(fn){
					return fn?$HPageUtil.addEvent(this,'change',fn):$HPageUtil.attachEvent(this,'change',{selectTr:[],allTr:[]});
				}
			}
			return p.createPage(ps);
		},
		/*
			var dictionary={'10':'未上线','true':'<span class="glyphicon glyphicon-ok"/>'};//字典
			columns = [
				{name:'id',comment:'ID',hidden:true},
				{name:'zhTitle',comment:'中文标题',html:'<a style="cursor:pointer;" onclick="$.MenuUtil.open(\'SHOW$[id]\',\'/main/yics/iphone.html?type=20&id=$[id]\',\'活动-$[zhTitle]\')">$[zhTitle]</a>'},
				{name:'istop',comment:'是否置顶',dict:data},
				{name:'imgSmallurl',list:',',comment:'图片',html:'<img style="height:30px;margin:5px;" src="$[imgSmallurl]"/>'},
				{name:'state',comment:'是否上线',dict:{10:'是'，20:'否'}},
				{name:'startDate',comment:'活动开始时间',dateFmt:'YYYY-MMM-DD HH:mm:ss'}
			];
			var BASE_ACTIVITIS = $HPageUtil.createArea({
				id : 'BASE_ACTIVITIS', 			//唯一标识
				attach:'.area_work', 			//所在区域
				reflesh:true,   				//是否立即刷新
				table:{  						//表格
					columns:columns,			//列信息
					//datas:datas				//数据与ajax互斥
					ajax:{						//数据源
						url:$.Url('/common/mm/yic/activities/list.ajax'),
						type:'post',
						success:function(data){if(data.success)return data.datas.list;}
					}
				},
				buttons : {
					add:{						//添加
						click:function(rs){}	//添加事件
					},
					update:{
						click:function(rs){
							if(rs.data){} 		//获取选中的行
						}
					},
					//delete:true  				//注销无按钮无事件 
				},
				page : {page:1,limit:10}		//分页信息
			}).rowSelected(function(rs){		//表格选中事件
				console.log(rs);
			}).pageChange(function(rs){			//页码改变事件
				console.log(rs);
			});
			
			query.click(function(){				//点击查询按钮
				BASE_ACTIVITIS.query({刷新表格的参数对象})
			})
			
		*/
		createArea : function(as){
			var a = {
				createArea : function(a){
					var _this = this;
					a.table.attrs = a.table.attrs?a.table.attrs:{};
					var tableId =  a.table.attrs.id = a.table.id = _this.tableId = a.id;
					_this.area = $('<div class="hibo_workarea"><div class="work_area"></div></div>').appendTo(a.attach);
					var bar = $('<div class="row"><div class="col-md-4 tools_area"></div><div class="col-md-8 page_area"></div></div>');
					a.table.attach = _this.area.find('.work_area');
					if(a.page||a.buttons){
						bar.appendTo(a.table.attach);
					}
					_this.addTable(a.table).table.rowSelected(function(rs){
						if(_this.toolBar&&_this.toolBar.buttons){
							var selectTr = rs.selectTr;
							if(selectTr.length==1){
								var upBtn = _this.toolBar.buttons[_this.tableId+'-UPDATE'];
								if(upBtn&&upBtn.length>0){
									var bt = upBtn.addClass('btn_update_on')[0];
									var events = $._data(bt,"events");
									for(var ev in events){
										for(var i in events[ev]){
											events[ev][i].data = selectTr;
										}
									}
								}
							}else{
								var upBtn = _this.toolBar.buttons[_this.tableId+'-UPDATE'];
								if(upBtn&&upBtn.length>0){
									var bt = upBtn.removeClass('btn_update_on')[0];
									var events = $._data(bt,"events");
									for(var ev in events){
										for(var i in events[ev]){
											events[ev][i].data = undefined;
										}
									}
								}
							}
							
							if(selectTr.length>0){
								var delBtn = _this.toolBar.buttons[_this.tableId+'-DELETE'];
								if(delBtn&&delBtn.length>0){
									var bt = delBtn.addClass('btn_delete_on')[0];
									var events = $._data(bt,"events");
									for(var ev in events){
										for(var i in events[ev]){
											events[ev][i].data = selectTr;
										}
									}
								}
							}else{
								var delBtn = _this.toolBar.buttons[_this.tableId+'-DELETE'];
								if(delBtn&&delBtn.length>0){
									var bt = delBtn.removeClass('btn_delete_on')[0];
									var events = $._data(bt,"events");
									for(var ev in events){
										for(var i in events[ev]){
											events[ev][i].data = undefined;
										}
									}
								}
							}
						}
					});
					if(a.buttons){
						var btns = [];
						for(var i in a.buttons){
							var btn = {type:i,attrs:{id:(tableId+'-'+i.toUpperCase())},html:'<input type="hidden" class='+(tableId+'-'+i.toUpperCase())+' />'};
							var fns = a.buttons[i];
							if($.type(fns)=='object')btn.fns = fns;
							btns.push(btn);
						}
						_this.toolBar = $HPageUtil.createToolBar(
						{
							type:'toolBar',
							attach:bar.children('.tools_area'),
							attrs:{id:tableId+'-toolBar'},
							buttons:btns
						});
					}
					if(a.page){
						a.page.attach = bar.children('.page_area');
						a.page.id = tableId + '-PAGE';
						_this.page = $HPageUtil.createPage(a.page);
						_this.page.change(function(r){
							_this.query(null,true);
						});
					}
					if(a.reflesh)_this.query();
					a.reflesh = true;
					$(window).resize(function(){
						_this.resize();
					});
					return this;
				},
				rowSelected : function(fn){
					this.table.rowSelected(fn);
					return this;
				},
				pageChange : function(fn){
					this.page.change(fn);
					return this;
				},
				resize : function(){
					var work_area = this.area.find('.work_area');
					var areaH = work_area.innerHeight();
					var rowH = work_area.children('.row').outerHeight(true);
					work_area.children('.hibo_table').height(areaH-rowH-10)
				},
				addTable : function(t,start,end){
					var n = $HPageUtil.dataUtils.extend(t);
					n.datas = n.datas?n.datas:[];
					if(end){
						n.datas = n.datas.slice(start,end);
					}else{
						n.datas = n.datas.slice(start);
					}
					if(this.table)this.table.remove().rowSelected();
					this.table = $HPageUtil.createTable(n);
					return this;
				},
				query : function(p,fPage){
					var _this = this;
					if(p){
						_this.queryParams = p;
					}else{
						if(fPage)p = _this.queryParams;
					}
					var isPage = _this.page?true:false;
					if(!fPage&&isPage)_this.page.params.page = 1;
					var page = isPage?_this.page.params.page:1;
					var limit = isPage?_this.page.params.limit:10000;
					var setpage = function(total){
						_this.page.params.reflesh = true;
						_this.page.params.total = total;
						_this.page.changeParams(_this.page.params);
					}
					var tableParams = $HPageUtil.dataUtils.extend(_this.table.params);
					tableParams.attrs.id = _this.tableId;
					if(tableParams.ajax){
						var ajax = $HPageUtil.dataUtils.extend(tableParams.ajax);
						ajax.data = $HPageUtil.dataUtils.extend(p,ajax.data);
						ajax.data.page = page;
						ajax.data.limit = limit;
						var fn = ajax.success;
						ajax.success = function(data){
							tableParams.datas = fn(data);
							_this.addTable(tableParams,0);
							if(isPage)setpage(data.page.totalCount);
							_this.resize();
						}
						ajax.error = function(e){
							_this.addTable(tableParams,0);
							if(isPage)setpage(0);
							_this.resize();
						}
						$.Hajax(ajax);
					}else{
						var total = tableParams.datas.length;
						var start = (page-1)*limit;
						var end = 0;
						if(isPage){
							_this.page.params.total = total;
							end = Math.min(page*limit,_this.page.params.total);
						}
						_this.addTable(tableParams.datas,start,end).table.rowSelected(rowSelected);
						_this.resize();
					}
					return this;
				}
			}
			return a.createArea(as)
		},
		addEvent : function(o,name,fn){
			var key = o.type + "-" + o.id;
			if(!this.events[key])this.events[key]={};
			if(!this.events[key][name])this.events[key][name]=[];
			this.events[key][name].push(fn);
			return o;
		},
		removeEvent : function(o,name){
			var key = o.type + "-" + o.id;
			if(name){
				if(this.events[key])delete this.events[key][name];
			}else{
				delete this.events[key];
			}
			return o;
		},
		attachEvent : function(o,name,params){
			var key = o.type + "-" + o.id;
			var fns = this.events[key]&&this.events[key][name]?this.events[key][name]:null;
			if(fns){
				for(var i in fns){
					o.attachFn = fns[i];
					o.attachFn(params);
					delete o.attachFn;
				}
			}
			return o;
		},
		events : {}
		
	};
	$(function(){
		var rows = $('.main_query').children('.row');
		var len = rows.length;
		function hiboPullHide(){
			if(len>0){
				var col11 = $('<div class="col-md-11 query_col">');
				var col1 = $('<div class="col-md-1 query_col">');
				var first = $(rows[0]);
				first.siblings('.row').hide();
				first.children().appendTo(col11);
				$('.query_button').appendTo(col1);
				col11.appendTo(first);
				col1.appendTo(first);
				$(window).resize();
			}
		}
		function hiboPullShow(){
			if(len>0){
				var first = $(rows[0]);
				first.siblings('.row').show();
				var qCol = first.children('.query_col');
				qCol.children('.query_button').appendTo('.query_buttons');
				qCol.children().appendTo(first);
				qCol.remove();
				$(window).resize();
			}
		}
		if(len==1){
			hiboPullHide();
			$('.hibo_Pull').hide();
		}
		$('.hibo_Pull').click(function(){
			if($(this).hasClass('query_drop')){
				$(this).removeClass('query_drop');
				hiboPullShow();
			}else{
				$(this).addClass('query_drop');
				hiboPullHide();
			}
		});
	});
})(window,document)




