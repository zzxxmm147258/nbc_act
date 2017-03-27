$.extend({
	ImgUtils : {
		Reader : new FileReader(),
		/**
		 读取文件
		 $.ImgUtils.readFile({
	 		file:file, 加载的文件
	 		success:function(){}回掉函数
		 });
		 */
		readFile : function(file,success){//读取文件
			reader = $.ImgUtils.Reader;
			reader.onload = function(e){
				success(e.target);
			}
			reader.readAsDataURL(file);
		},
		/**
		 压缩图片
		 $.ImgUtils.zip({
	 		file:file, 加载的文件
	 		width:1000,
	 		success:function(data){}回掉函数
	 		error:function(e){}
	 	 })
		 */
		zip : function(z){
			$.ImgUtils.readFile(z.file,function(r){
				z.name = z.file.name;
				z.size = z.file.size;
				z.file = r.result;
				$.ImgUtils.imgZip(z);
			});
		},
		/**
		 压缩图片
		 $.ImgUtils.imgZip({
	 			file:loadfile, 加载后的文件
	 			width:1000,
	 			success:function(data){}回掉函数
	 			error:function(e){}
	 	})
		*/
		imgZip : function(iz){//压缩图片
			iz.timeStart = new Date().getTime();
			$.ImgUtils.loadImg({
				id:iz.id,
				src:iz.file,
				success:function(loadImg){
					iz.loadImg = loadImg;
					$.ImgUtils.loadImgZip(iz);
				},
				error:function(e){
					e.id = iz.id;
					if(s.error)serror(e);
				}
			});
		},
		/**
		剪切并压缩图片
		$.ImgUtils.cutZip({
			file:file,
			id : $.Random.getRanStr(16);
			zipImg : {
				width:1000,
			},
			cutImg : {
				ratio:1/2,//宽高比例
				omove : true//移动方式;
			},
			success:function(data){
				//处理异常后函数
			},
			error:function(e){
				//处理异常函数
			}
		});
		 */
		cutZip : function(cz){
			$.ImgUtils.readFile(cz.file,function(r){//读取文件
				cz.zipImg.name = cz.file.name;//读取文件名
				cz.zipImg.size = cz.file.size;//读取文件大小
				cz.image = r.result;
				$.ImgUtils.imgCutZip(cz);
			});
		},
		/**
		 剪切并压缩图片
		 $.ImgUtils.imgCutZip({
	 		image:loadfile, 加载后的文件
	 		id : id,
			zipImg : {
				width:1000,
            },
            cutImg : {
                ratio:15/16,//宽高比例
                omove : false//移动方式;
                top:1rem;
            },
            success:function(data){
                 //处理完成后函数
            },
            error:function(e){
				//处理异常后函数
			}
		});
		 */
		imgCutZip : function(icz){//压缩图片
			var zip = icz.zipImg?icz.zipImg:{};
			zip.timeStart = new Date().getTime();
			var cutLoad = false;
			var isImageLod = false;
			var params = null;
			var imageLod = null;
			var dealCallBack = function(){
				zip.id = icz.id;
				zip.xb = params.xb;
				zip.yb = params.yb;
				zip.wb = params.wb;
				zip.hb = params.hb;
				zip.loadImg = imageLod;
				zip.success = icz.success;
				$.ImgUtils.loadImgZip(zip);
			}
			$.ImgUtils.loadImg({
				id:icz.id,
				src:icz.image,
				success:function(loadImg){//预加载图片
					imageLod = loadImg;
					isImageLod = true;
					if(cutLoad){//都处理完掉dealCallBack
						dealCallBack();
					}
				},
				error:icz.error
			});
			var cut = icz.cutImg;
			cut.image = icz.image;
			cut.id = icz.id;
			cut.sureFn = function(r){//剪切图片
				params = r;
				cutLoad = true;
				if(isImageLod){//都处理完掉dealCallBack
					dealCallBack();
				}
			};
			$.ImgUtils.imgCut().cutImg(cut);
		},
		imgBase64ToBlob : function(DataURL){//将图片编码生成BLOL
			var bytes=window.atob(DataURL.split(',')[1]);
			var ab = new ArrayBuffer(bytes.length);
			var ia = new Uint8Array(ab);
			for (var i = 0; i < bytes.length; i++) {
			    ia[i] = bytes.charCodeAt(i);
			}
			return new Blob( [ab] , {type : 'image/jpeg'});
		},
		/**
		 $.ImgUtils.loadImg({
	 		src:image, 加载前的image图片
	 		success:function(data){},回掉函数
	 		error:function(e){}回掉函数
	 		})
		*/
		loadImg : function(li){//加载图片
			var image = new Image();
			image.src=li.src;
			image.onload = function(e){
					if(li.success){
						li.success(e.target);
					}
				};
			image.onerror = function(e){
					if(li.error){
						e.id = li.id;
						li.error(e)
					}
				};
		},
		/**
		 $.ImgUtils.loadImgZip(
	 		loadImg:loadImg, 加载后的image图片
	 		width:1000,
	 		success:function(){}回掉函数
		*/
		loadImgZip :  function(liz){//压缩加载后的图片
			liz = liz?liz:{};
			var bili = 1;
			var iWidth = liz.loadImg.width;
			var iHeight = liz.loadImg.height;
			var isWid = true;
			if(liz.width&&liz.width<iWidth){
				bili = liz.width/iWidth;
			}else if(liz.height&&liz.height<iHeight){
				isWid = false;
				bili = liz.width/iWidth;
			}
			liz.width = iWidth * bili;
			liz.height = iHeight * bili;
			if(liz.xb){
				liz.sx = liz.xb*iWidth;
			}else{
				liz.sx = 0;
			}
			if(liz.yb){
				liz.sy = liz.yb*iHeight;
			}else{
				liz.sy = 0;
			}
			if(liz.wb){
				liz.sWidth = liz.wb*iWidth;
			}else{
				liz.sWidth = iWidth;
			}
			if(liz.hb){
				liz.sHeight = liz.hb*iHeight;
			}else{
				liz.sHeight = iHeight;
			}
			if(!isWid){
				liz.width = liz.sWidth/liz.sHeight*liz.height;
			}else{
				liz.height = liz.sHeight/liz.sWidth*liz.width;
			}
			var r = {name:liz.name,oldWidth:iWidth,oldHeight:iHeight,oldSize:liz.size,zipWidth:Math.round(liz.width),zipHeight:Math.round(liz.height)};
			var canvas = document.createElement('canvas');
			canvas.width = r.zipWidth;
			canvas.height = r.zipHeight;
			var ctx = canvas.getContext("2d");
			ctx.drawImage(liz.loadImg,liz.sx,liz.sy,liz.sWidth,liz.sHeight,0, 0, canvas.width, canvas.height);
			liz.clarity = liz.clarity?liz.clarity:0.3;
			r.base64 = canvas.toDataURL("image/jpeg",liz.clarity);
			r.file = $.ImgUtils.imgBase64ToBlob(r.base64);
			r.zipSize = r.file.size;
			r.timeStart = liz.timeStart;
			r.timeEnd = new Date().getTime();
			r.useTime = r.timeEnd-r.timeStart;
			if(!liz.id){
				liz.id = $.Random.getRanStr(16);
			}
			r.id = liz.id;
			liz.success(r);
		},
		/**
		 $.ImgUtils.imgCut.cutImg({
		 	   id:id
	 		   image:image,//base64Image串
               ratio:15/16,//宽高比例
               omove:false,//移动方式
               error:function(e){
					//处理异常函数
			   }
           })
		 */
		imgCut : function(){
			touchstart = function(touches){
				var touchTarget = touches.length; //获得触控点数
				if(touchTarget == 1){
					// 获取开始坐标
					_this.startPageY = touches[0].pageY;
					_this.startPageX = touches[0].pageX;
					_this.cutMoveProp= getProp(_this.showimg);//移动框初始位置
				}
			};
			touchmove =  function(touches,type){
				var touchTarget = touches.length; //获得触控点数
				if(touchTarget == 1&&_this.mouseMove){
					var cx = (touches[0].pageX-_this.startPageX);//X轴移动距离
					var cy = (touches[0].pageY-_this.startPageY);//Y轴移动距离
					if(_this.omove){
						cx = -cx;
						cy = -cy;
					}
					var mv = getProp(_this.movediv);
					var cm = getProp(_this.bgimg);
					var dt = (-_this.cutMoveProp.top + cy)<0?0:(-_this.cutMoveProp.top + cy);//移动框距上边的宽
					var dl = (-_this.cutMoveProp.left + cx)<0?0:(-_this.cutMoveProp.left + cx);//移动框距左边的宽
					var dl = (dl+mv.width)>cm.width?(cm.width-mv.width):dl;//移动框可以平行移动的最大距离
					var dt = (dt+mv.height)>cm.height?(cm.height-mv.height):dt;//移动框可以垂直移动的最大距离
					if(type=='move'){
						if(_this.omove){
							_this.bgimg.css({'top':-dt+'px','left':-dl+"px"});
						}else{
							_this.movediv.css({'top':dt+'px','left':dl+"px"});
						}
						_this.showimg.css({'top':-dt+'px','left':-dl+"px"});
					}
				}
				
			};
			getProp = function(o){
				return {top:parseInt(o.css("top")),left:parseInt(o.css("left")),width:o.width(),height:o.height()};
			};
			bind = function(o){
				o.move.bind('touchstart',function(e){
					_this.main.css('overflow-y','hidden');
					var touches = event.targetTouches;
					_this.mouseMove = true;
					touchstart(touches,this.getAttribute('type'));
				});
				o.move.bind('touchend',function(e){
					_this.mouseMove = true;
					if(!_this.omove){
						_this.main.css('overflow-y','scroll');
					}
				});
				o.move.bind('touchmove',function(e){
					var touches = event.targetTouches;
					touchmove(touches,this.getAttribute('type'));
				});
				o.handlediv.children().bind('touchmove',function(e){
					touchmove(event,this.getAttribute('type'));
				});
				o.handlediv.children().bind('touchstart',function(e){
					touchstart(event,this.getAttribute('type'));
				});
				o.move.bind('mousedown',function(e){
					_this.main.css('overflow-y','hidden');
					var touches = [e];
					_this.mouseMove = true;
					touchstart(touches,this.getAttribute('type'));
				});
				$(document).bind('mouseup',function(e){
					_this.mouseMove = false;
					if(!_this.omove){
						_this.main.css('overflow-y','scroll');
					}
				});
				o.move.bind('mousemove',function(e){
					var touches = [e];
					touchmove(touches,this.getAttribute('type'));
				});
				o.sure.bind('click',function(){
					var mv = getProp(_this.movediv);
					var cm = getProp(_this.showimg);
					_this.cutdiv.css('visibility','hidden');
					if(_this.sureFn){
						_this.sureFn({xb:-cm.left/cm.width,yb:-cm.top/cm.height,wb:mv.width/cm.width,hb:mv.height/cm.height});
					}
				});
			};
			this.cutImg  = function(s){
				reset();
				if(s.handle){
					if(s.handle.size){
						hand.size = s.handle.size;
					}
					if(s.handle.unit){
						hand.unit = s.handle.unit;
					}
					if(s.handle.image){
						hand.image = '<img src="'+s.handle.image+'" style="width:100%;height100%;">';
					}
					if(s.handle.image){
						hand.bgColor = s.handle.bgColor;
					}
				}
				if(!s.regulable){
					_this.handlediv.hide();
				}
				_this.ratio = s.ratio;
				_this.sureFn = s.sureFn;
				_this.omove = s.omove;
				_this.error = s.error;
				_this.id = s.id;
				_this.mainTop = s.top?s.top:0;
				_this.showimg.attr('src',s.image);
				var w = _this.cutdiv.width();
				_this.bgimg.width(w).attr('src',s.image);
				_this.bgimg.load(function(){
					var cm = getProp(_this.bgimg);
					var cmw = cm.width;
					var cmh = cm.height;
					var bgw = cmw;
					var bgh = cmh;
					if(_this.ratio){
						var cmhn = Math.round(cmw/_this.ratio);
						if(cmhn>cmh){
							if(_this.omove){
								bgw = cmhn/cmh*cmw;
								cmh = bgh = cmhn;
								_this.main.height(cmh);
							}else{
								cmw = Math.round(cmh*_this.ratio);
							}
						}else{
							_this.main.height(bgh);
							cmh = cmhn;

						}
					}
					if(_this.omove){
						_this.main.height(cmh).css('top',_this.mainTop);
					}else{
						_this.main.height(bgh).css('top',_this.mainTop);
					}
					_this.movediv.width(cmw).height(cmh);
					_this.bgimg.width(bgw).height(bgh);
					_this.showimg.width(bgw).height(bgh);
					_this.cutdiv.css('visibility','visible');
				});
				_this.bgimg.error(function(e){ 
					e.id = _this.id;
					if(_this.error)_this.error(e);
				});
			};
			reset = function(){
				_this.showimg.remove();
				_this.bgimg.remove();
				_this.bgimg = $('<img class="cut-bgImg" style="top:0;left:0;position: absolute;z-index:260;opacity: 0.5; background-color: white;" src="">').appendTo(_this.main);
				_this.showimg = $('<img class="cut-showimg" style="top:0;left:0;position: absolute;z-index:269;" src="">').appendTo(_this.showdiv);
			}
			init = function(o){//初始化
				var init = {};
				if($.imageCutObject){//初始化切图对象
					init = $.imageCutObject;
				}else{
					var hand = {size:20,unit:'px',image:'',bgColor:'white'};
					init.cutdiv = $('<div class="cut-cutdiv" style="width: 100%;height:100%;top:0;left:0;visibility:hidden;z-index: 200;background-color: white;position: absolute;"></div>').appendTo(document.body);
					init.main = $('<div class="cut-main" style="width: 100%;height:70%;top:0;left:0;position: relative;overflow: hidden;z-index: 201;"></div>').appendTo(init.cutdiv);
					init.bgimg = $('<img class="cut-bgImg" style="top:0;left:0;position: absolute;z-index:260;opacity: 0.5; background-color: white;" src="">').appendTo(init.main);
					init.movediv = $('<div class="cut-movediv" type="move" style="cursor: move;top:0;left:0;width:100%;height:100%;position: absolute;z-index:270;"></div>').appendTo(init.main);
					init.showdiv = $('<div class="cut-showdiv" style="width: 100%;height: 100%;position: absolute;z-index:271;overflow: hidden;"></div>').appendTo(init.movediv);
					init.showimg = $('<img class="cut-showimg" style="top:0;left:0;position: absolute;z-index:269;" src="">').appendTo(init.showdiv);
					init.move = $('<div class="cut-move" type="move" style="width: 96%;height: 96%;left:2%;top:2%;position: absolute;z-index:271;overflow: hidden;"></div>').appendTo(init.movediv);
					init.handlediv = $('<div class="cut-handlediv" style="width: 100%;height: 100%;top:0;left:0;position:absolute;"></div>').appendTo(init.movediv);
					init.handlew = $('<div class="cut-handle cut-w" type="w" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;left:0;top:50%;cursor:w-resize;width: '+hand.size+hand.unit+';height:  '+hand.size+hand.unit+';margin-left: '+(-hand.size/2)+hand.unit+';margin-top:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlenw = $('<div class="cut-handle cut-nw" type="nw" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;cursor:nw-resize;left:0;top:0;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-left:'+(-hand.size/2)+hand.unit+';margin-top:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlen = $('<div class="cut-handle cut-n" type="n" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;cursor:n-resize;left:50%;top:0;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-left:'+(-hand.size/2)+hand.unit+';margin-top:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlene = $('<div class="cut-handle cut-ne" type="ne" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;cursor:ne-resize;right:0;top:0;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-right:'+(-hand.size/2)+hand.unit+';margin-top:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlee = $('<div class="cut-handle cut-e" type="e" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;cursor:e-resize;right:0;top:50%;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-right:'+(-hand.size/2)+hand.unit+';margin-top:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlese = $('<div class="cut-handle cut-se" type="se" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;cursor:se-resize;right:0;bottom:0;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-right:'+(-hand.size/2)+hand.unit+';margin-bottom:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handles = $('<div class="cut-handle cut-s" type="s" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;left:50%;bottom:0;cursor:s-resize;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-left:'+(-hand.size/2)+hand.unit+';margin-bottom:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					init.handlesw = $('<div class="cut-handle cut-sw" type="sw" style="position: absolute;background-color: '+hand.bgColor+';z-index: 300;bottom:0;left:0;cursor:sw-resize;width: '+hand.size+hand.unit+';height: '+hand.size+hand.unit+';margin-left:'+(-hand.size/2)+hand.unit+';margin-bottom:'+(-hand.size/2)+hand.unit+';">'+hand.image +'</div>').appendTo(init.handlediv);
					var button = $('<div class="cut-button" style="font-size:50px;position: absolute;z-index:330;bottom:10%;width:60%;left:20%;"></div>').appendTo(init.cutdiv);
					init.sure = $('<img style="width: 100%;" src="'+$.Url('/resources/image/sysimg/cutbtn.png')+'">').appendTo(button);
					bind(init);
					init.cutImg = o.cutImg;
					$.imageCutObject = init;
				}
				return init;
			};
			return  _this = init(this);
		},
	}
});