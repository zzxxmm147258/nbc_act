/**
 * <p>标题：表格 </p>
 * <p>功能：表格处理</p>
 * <p> 版权： Copyright © 2015 HIBO</p>
 * <p> 公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月2日 上午9:38:00</p>
 * <p>类全名：hibo_table.js</p>
 * 作者：周雷 
 * 初审：周雷 
 * 复审：周雷
 */
(function($) {
	/* tr的事件 */
	$.fn.htrdEvent = function() {
		$(this).click(function() {
			var text = $(this).text();
			
		});
		$(this).mouseover(function() {
			$(this).addClass("mouseActive");
		});
		$(this).mouseout(function() {
			$(this).removeClass("mouseActive");
		});
	}

	/**
	 * table加载时绑定事件
	 */
	$.fn.honload = function() {
		$(this).addAllDiv();
		$(this).dblclick(function() {
			dealhdblclick(this);
		});
	}

	/**
	 * 双击事件
	 * @param  type 如果不填绑定次对象的双击事件，如果不为空为此对象的type对象绑定双击事件
	 */
	$.fn.hdblclick = function(type) {
		if(type){
			$(this).on("dblclick", type, function() {
				$(this).addOneDiv();
				dealhdblclick(this);
			})	
		}else{
			$(this).dblclick(function() {
				$(this).addOneDiv();
				dealhdblclick(this);
			});	
		}
		
	}

	/**
	 * 表格双击事件
	 * @param  type 如果不填绑定次对象的双击事件，如果不为空为此对象的type对象绑定双击事件
	 */
	$.fn.gTableDblclick = function(type) {
		if(type){
			$(this).on("dblclick", type, function() {
				$(this).addOneDiv();
				dealhdblclick(this);
				$(this).closest("tr").find("td[type='id']").addOneDiv();
			});
		}else{
			$(this).on("dblclick", "td", function() {
				$(this).addOneDiv();
				dealhdblclick(this);
				$(this).closest("tr").find("td[type='id']").addOneDiv();
			});
		}
	}

	/**
	 * 处理双击事件
	 */
	function dealhdblclick(obj) {
		var hValueBorder = $(obj).find(".hValueBorder");
		var hdivShowVlaue = hValueBorder.find(".hdivShowVlaue")
		var hdivInput = hValueBorder.find(".hdivInput");
		if(hdivInput.length>0){
			var hinputValue = hdivInput.find(".hinputValue");
			var text = hdivShowVlaue.text();
			hinputValue.val(text);
			hdivInput.show();
			hinputValue.focus()
			hdivShowVlaue.hide();
		}
	}

	/**
	 *为所有绑定对象添加必要数据
	 */
	$.fn.addAllDiv = function() {
		$(this).each(function() {
			$(this).addOneDiv();
		});
	}

	/**
	 * 为单个绑定对象添加必要数据
	 */
	$.fn.addOneDiv = function() {
		/* 显示值控件的父类div */
		var hValueBorder = $(this).find(".hValueBorder");
		if (hValueBorder.length <= 0) {
			inputType(this);
		}
		//绑定鼠标失去焦点事件
		if(hValueBorder.length>0){
			hValueBorder.find(".hinputValue").focusout(function() {
				var neWvalue = $(this).val();
				var hdivShowVlaue = $(this).parent().siblings();
				hdivShowVlaue.text(neWvalue);
				hdivShowVlaue.show();
				$(this).parent().hide();
			});
		}
	}

	/**
	 * 处理输入框类型
	 */
	function inputType(obj){
		var type = $(obj).attr("type");
		var name = $(obj).attr("name");
		$(obj).removeAttr("name");
		var hDivBorder = $(obj).find(".hDivBorder");
		if(hDivBorder.length>0){
			obj = hDivBorder;
		}
		var h = $(obj).height();
		$(obj).css("height",h);
		var width = $(obj).width()+"px";
		var text = $(obj).text();
		var html = "<div class='hValueBorder' style='width:" + width + ";'>";
		if (!type||!name) {
			html = html + "<div class='hdivShowVlaue'>" + $(obj).html() + "</div>";
		}else{
			if(type=='id'){type="text";}
			html = html + "<div class='hdivShowVlaue'>" + text + "</div>";
			html = html + "<div class='hdivInput'>";
			if("select"==type){
				
			}else if("checkbox"==type){
				html = html + textInput(type,name,text,h);
			}else if("radio"==type){
				html = html + textInput(type,name,text,h);
			}else{//输入框
				html = html + textInput(type,name,text,h);
			}
			html = html + "</div>";
		}
		html = html + "</div>";
		$(obj).html(html);
		hValueBorder = $(obj).find(".hValueBorder");	
	}

	//普通输入框
	function textInput(type,name,text,h){
		var html = "<input class='hinputValue'  name='" + name + "' type='" + type + "' value='"+text+"' style='height:"+h+"px'>";
		return html;
	}

	
	$.fn.calcuWH = function(){
		var obj = new Object();
		obj.pw = $(this).innerWidth();
		obj.ph = $(this).innerHeight();
		obj.w = $(this).width();
		obj.h = $(this).height();
		obj.plr = obj.pw - obj.w;
		obj.ptb = obj.ph - obj.h;
		return obj;
	}
	
	/**
	 * 表格尺寸调节
	 */
	$.fn.htablesize= function(){
		var th = $(this).find("th");
		var isMove = false;
		var isDown = false;
		var oMove = null;
		var classMap = {};
		$(this).find("td").each(function(){
			var html = "<div class='hDivBorder'>"+$(this).html()+"</div>";
			$(this).addClass('cellIndex'+this.cellIndex).attr('index',this.cellIndex);
			$(this).html(html);
		});
		th.each(function(index){
			$(this).addClass('cellIndex'+index).attr('index',index);
			var width = $(this).width()+"px";
			var html = "<div class='hDivBorder' style='width:"+width+";'>"+$(this).html()+"</div>";
			$(this).html(html);
			$(this).mousemove(function(e){
				if(!isDown){
					//获取当前对象的属性
					var o =getoparams(this);
					//获取鼠标在当前对象的位置
					var post = getopost(o,e);
					var oldClass = classMap["h"+index];
					$(this).removeClass(oldClass);
					if((post==3||post==7)){
						if(post==7&&index>0){
							oMove = $(this).prev()[0];
						}else{
							oMove = this;
						}
						thClass = getPointerCss(post);
						classMap["h"+index] = thClass;
						$(this).addClass(thClass);
						isMove = true;
					}else{
						isMove = false;
						oMove = null;
					}
				}
			});
			$(document).mousemove(function(e){
				if(isDown){
					var o =getoparams(oMove);
					var x = e.pageX;
					var index = $(oMove).attr('index');
					$(".cellIndex"+index).each(function(){
						hValueBorder = $(this).find(".hValueBorder")
						var hDivBorder = $(this).find(".hDivBorder");
						if(hValueBorder.length>0){
							var tdo = $(this).calcuWH();
							var width = (x-o.l-tdo.plr)+"px";
							hValueBorder.css("width",width);
						}
						if(hDivBorder.length>0){
							var tdo = $(this).calcuWH();
							var width = (x-o.l-tdo.plr)+"px";
							hDivBorder.css("width",width);
						}
					});
				}
			});
			$(document).mousedown(function(e){
				if(isMove){
					isDown = true;
					document.onselectstart = function(){return false;}
				}
			});
			$(document).mouseup(function(e){
				isDown = false;
				isMove = false;
				document.onselectstart = null;
			});
		});
	}

  	/**
  	 * 根据位置获取鼠标样式
  	 */
	function getPointerCss(num){

		if(num==1||num==5){//上下
			return "sn_cursor";
		}else if(num==3||num==7){//左右
			return "ew_cursor";
		}
		else if(num==4||num==8){//上左下右
			return "nwse_cursor";
		}
		else if(num==2||num==6){//上右下左
			return "nesw_cursor";
		}else{
			return "";
		}
	}

	/**
	 * 获取对象长宽信息
	 */
	function getoparams(o){
		var  oo ={};
		var p = $(o).position();
		var width = $(o).width();
		var height = $(o).height();
		oo.t = $(o).offset().top+5;
		oo.l = $(o).offset().left;
		oo.b = oo.t + height;
		oo.r = oo.l + width;
		oo.w = width;
		oo.h =height;
		return oo;
	}

	/**
	 * 获取鼠标在对象中的位置
	 * @param  o 当前对象
	 * @param  e 鼠标对象
	 * @return  位置信息
	 */
	function getopost(o,e){
		var x = e.pageX;
		var y= e.pageY;

		if(x-o.l<4&&y-o.t<4){//左上
			return 8;
		}else if(o.r-x<4&&y-o.t<4){//右上
			return 2;
		}else if(x-o.l<4&&o.b-y<4){//左下
			return 6;
		}else if(o.r-x<4&&o.b-y<4){//右下
			return 4;
		}else if(y-o.t<4){//上
			return 1;
		}else if(o.b-y<4){//下
			return 5;
		}else if(x-o.l<4){//左
			return 7;
		}else if(o.r-x<4){//右
			return 3;
		}else{//外
			return 0;
		}
	}
})(jQuery);
document.write('<style type="text/css">.mouseActive{background-color: #fece69;}'
		+'.hDivBorder{overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}'
		+'.hValueBorder .hdivShowVlaue{width:100%;height:100%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}'
		+'.hValueBorder .hdivInput{width:100%;height:100%;display:none;margin: 0;padding: 0;border: 0;}'
		+'.hValueBorder .hdivInput .hinputValue{width:100%;height:100%;margin: 0;padding: 0;border: 0;width: 100%;height: 100%;}'
		+'.ew_cursor{cursor:w-resize;cursor:e-resize;}'
		+'.sn_cursor{cursor:s-resize;cursor:n-resize;}'
		+'.nwse_cursor{cursor:nw-resize;cursor:se-resize;}'
		+'.nesw_cursor{cursor:sw-resize;cursor:ne-resize;}</style>');
