
		var oDiv = document.getElementById('text_ul');
	    var oRess = document.getElementById('ress');
	    var oCreat = document.getElementById('creat');
	    var oiFam = document.getElementById('ifam');
	    var aDiv = oiFam.getElementsByTagName('iframe')[0];

	    //根据后台的json创建一级菜单
	    for(var i=0; i<json.length; i++){
	    	//设置首页标签的href  和 首页iframe的src
	    	$('#index').attr("href","javascript:;");
	    	if(json[0].url==null || json[0].url.length<5){
	    		$('iframe[name=ifrIndex]').attr("src",url+"/main/home");
	    	}else{
	    		$('iframe[name=ifrIndex]').attr("src",url+json[0].url);
	    	}
	    	
	      //先判断一下当i是第一级菜单的时候，让他的id出现是p；
	        var id = '';
	        if(i==0){
	            id='id="p"';
	        }
	        oCreat.innerHTML += '<li '+id+'><a href="javascript:;">' + json[i].menuname +'</a></li>';
	        var oDivstr = '<ul><li class="fixed" id="niu"><a href="javascript:;">菜单列表</a><strong></strong></li>';
	        for(var j=0; j<json[i].children.length;j++){  //循环的是二级菜单
	            var left = '<li><h2><a href="javascript:;">' + json[i].children[j].menuname +'</a><span></span></h2><dl>' ;
	            var right =  '</dl></li>';
	            var moddel = '';
	            if(json[i].children[j].children){
	            	for( var k=0; k<json[i].children[j].children.length; k++){//循环的是三级菜单
		            	var showtype = json[i].children[j].children[k].showType;
		            	switch(showtype){
		            	case '右下角显示':
		            		moddel += '<dd menuid="'+json[i].children[j].children[k].menuid+'">' + ' <a target="ifr'+json[i].children[j].children[k].menuid+'" href="'+url+ json[i].children[j].children[k].url +'">'+json[i].children[j].children[k].menuname+'</a></dd>';
		            		break;
		            	case '当前页面显示':
		            		moddel += '<dd>' + ' <a href="'+url+ json[i].children[j].children[k].url +'">'+json[i].children[j].children[k].menuname+'</a></dd>';
		            		break;
		            	case '新页面显示':
		            		moddel += '<dd>' + ' <a target="_blank" href="'+url+ json[i].children[j].children[k].url +'">'+json[i].children[j].children[k].menuname+'</a></dd>';
		            		break;
		            	default:
		            		moddel += '<dd menuid="'+json[i].children[j].children[k].menuid+'">' + ' <a target="ifr'+json[i].children[j].children[k].menuid+'" href="'+url+ json[i].children[j].children[k].url +'">'+json[i].children[j].children[k].menuname+'</a></dd>';
		            		break;
		            	}
		            }
	            }
	            
	           oDivstr += left +  moddel +right;
	        };
	        oDiv.innerHTML += oDivstr + '</ul>';
	    };

	    var oDd = oDiv.getElementsByTagName('dd');
	    var oLi = oCreat.getElementsByTagName('li');
	    var oH2 = oDiv.getElementsByTagName('ul');
	    var oBow = oDiv.getElementsByTagName('h2');
	    var oDl = oDiv.getElementsByTagName('dl');
	    var oSpan = oDiv.getElementsByTagName('span');
	    var oBtn = document.getElementsByTagName('strong');
	    var oNoff = false //表示没有
	    var oCont_Automatic_list = $('.cont_Automatic_list ul')[0];
	    var oCont_iframe_Whole = $('.cont_iframe_Whole')[0];
	    var arr_menuids = new Array();
	    var tabOn;
	    var prevbOn = "Index";

	    for(var i=0; i<oDd.length; i++){ //三级菜单点击事件
	        oDd[i].index = i;
	        oDd[i].onclick = function(){
	                 for( var i=0;i<oDd.length; i++){
	                        oDd[i].style.background = '';
	                   }
	                 var src = $(this).find("a")[0].getAttribute("href");
	                this.style.background = '#ccc';
	            if( this ){  //先找到第三级菜单有没有子菜单，没有就创建出来那个所在的位置，
	                var rLi = document.createElement( 'li');
	                var s = document.getElementById( 'p' );
	                    rLi.innerHTML = '<li>'+s.innerHTML+'</li><li>&gt;'+this.parentNode.previousSibling.innerHTML+ '</li><li>&gt;' + this.innerHTML + '</li> ';
	                        if( oRess.children[0] ){ //判断如果当前的那个所在位置没有节点，就创建，如果有节点就移除节点，
	                             oRess.replaceChild( rLi , oRess.children[0] );   
	                           }else{
	                             oRess.appendChild( rLi );
	                           };
	                    }else{
	                        return;
	                    }
			            var attr = $(this).attr('menuid');
			            var name = $(this).find("a")[0].text;
			            open_menu(src,attr,name);
			            autoHeight(attr);
			            var target = $($(this).find("a")[0]).attr("target");
			            if(target&&"_blank"!=target){
			            	return false;
			            }
	            }
	        };
	    function open_menu(src,mid,name){
	    	var isReflash = false;
        	var kkk = src.indexOf('select=false');
        	if(kkk<0){
        		isReflash = true;
//        		src=src.indexOf('?')>0?(src+'&select=false'):(src+'?select=false');
        	}
	    	var  ispush = false;
            if(mid){
                $('.cont_iframe_item').hide();
                $('.cont_iframe_li').css('border','1px solid #ccc');
                for(var k=0;k<arr_menuids.length;k++){
                    if(arr_menuids[k]==mid){
                       mommon(mid);
                       ispush=true;
                       break;
                    }
                }
            }else{
            	ispush=true;
            }
            if(!ispush){
            	var ifr = "ifr";
            		oCont_Automatic_list.innerHTML += '<li class="cont_iframe_li"  style="border:1px solid #0000FF" menuid="'+mid+'" onclick="onclickLi(\''+mid+'\')"><a href="javascript:;">' + name +'<span class="dump"  onclick="removeli(\''+mid+'\')"></span></a></li>';
            	var oDiv_item = document.createElement('div');
            		oDiv_item.setAttribute("menuid", mid);
            		oDiv_item.setAttribute('class','cont_iframe_item');
            		oDiv_item.innerHTML = '<iframe id="ifr'+mid+'" name="ifr'+mid+'" isReflash='+isReflash+' src="'+src+'" scrolling="no" onload="autoHeight(\''+mid+'\');"></iframe>';
            		oCont_iframe_Whole.appendChild(oDiv_item);
            		tabOn = mid;
            		arr_menuids = array_stack(arr_menuids, mid);
            }
	    }
	    function array_stack(array,value){
	    	if(array&&array.length>0){
	    		index = findindex(array, value);
	    		if(index>=0){
	    			array.splice(index,1);
	    		}
	    	}
	    	array.push(value);
	    	return array;
	    } 
        var isspre = false; //解除当前span和li之间的冒泡事件
        function onclickLi(menuid){
        		autoHeight(menuid);
                if(!isspre){
                    $('.cont_iframe_item').hide();
                    $('.cont_iframe_li').css('border','1px solid #ccc');
                    mommon(menuid);
                }
                isspre = false;
        }

        function mommon(menuid){ //显示的是当前点击的那个
            $('.cont_Automatic_list').find('li[menuid="'+menuid+'"]').css('border','1px solid #0000FF');
            $('.cont_iframe_Whole').find('div[menuid="'+menuid+'"]').show();
            autoHeight(menuid);
            tabOn = menuid;//把当前点击的那个menuid给了定义的tabOn，tabOn就是当前点击的那个li元素.
            arr_menuids = array_stack(arr_menuids, menuid);
        }

        function removeli(menuid){//点击span移除的事件
            if("Index"==menuid){
            	mommon("Index");
            }else{
                $('.cont_Automatic_list').find('li[menuid="'+menuid+'"]').remove();
                $('.cont_iframe_Whole').find('div[menuid="'+menuid+'"]').remove();
                var index = findindex(arr_menuids,menuid);
                arr_menuids.splice(index,1);
                if(arr_menuids.length==0){
                	mommon("Index");
                }
                if(menuid==tabOn){
                        if(index-1>=0){
                            //显示前一个
                             mommon(arr_menuids[index-1]);
                        }else{
                            //显示后一个
                            mommon(arr_menuids[index]);
                        }
                }
            }
			delete Window['ifr'+menuid];
            isspre = true;
        }
        function findindex(array,attr){ //在数组里面找当前点击元素是第几位。传的是所有数组和当前点击的那位
            var index = -1;
             if(array){
                    for(var i=0;i<array.length;i++){
                        if(array[i]==attr){
                            index=i;
                        }
                    }
                }
            return index;
        }
        
        
      //首页右边三角的点击事件
     // 1/阻止cont_Automatic_list > li的右键点击事件，出现自写的右键元素

     $('.cont_Automatic_list ul').on("contextmenu","li",function(ev){
             var ev = ev||event;
             var xx = ev.clientX+"px";
             var yy = ev.clientY+'px';
             $('.remove_icon').css('left',xx);
             $('.remove_icon').css('top',yy);
             $('.remove_icon').show();
             LeftEv( $('.remove_icon dl dd') );
              return false;
     })

     document.onclick=function(){
             $('.remove_icon').hide();
     }

     // 2、点击a的左键时，出现dl
     var ov = $('.remove_all dl')[0];
     
     $('.remove_all').click(function(e){
         if(1 == e.which){
        	 var display = $('.remove_all dl').css('display');
             if( "none"== display){
                 $('.remove_all dl').show();
                 LeftEv( $('.remove_all dl dd') );
             }else{
                 $('.remove_all dl').hide();
             }
          }
             return false;
     });

      $('body').click(function(){
    	 $('.remove_all dl').hide();
     })
     //3、左键事件dd项目
     function LeftEv(obj){
         obj.bind('click',function(){
             if(obj.index($(this)) == 0){
                 AllRemove();
             }else if( obj.index($(this)) == 1 ){
                 removeli(tabOn);
             }else{
                // alert('刷新页面')
             }
         })
     }

     //dd第一个子节点 关闭所有的页面
     function AllRemove(){
         for (var i = arr_menuids.length-1; i >=0; i--) {
        	 removeli(arr_menuids[i]);
		}
         mommon("Index");
     }
  
	    for(var i=0; i<oLi.length; i++){  //这个是为了一级菜单和二级菜单能够对应上
	        oLi[i].index = i;
	        oLi[i].onclick = function(){
	            for( var j=0; j<oLi.length; j++){ //建立一级菜单的自定义属性
	                oLi[j].removeAttribute( 'id' );
	            }
	                this.id="p";//根据自定义属性找到第一级菜单，
	                firstChange(this);
	            };
	    };

	    function loadChange () {
	         var p = document.getElementById( 'p' );
	         firstChange(p);
	    }
	    //二级菜单出现消失的封装函数，
	    function firstChange(p){
	             for(var i=0; i<oH2.length;i++){
	                oH2[i].style.display = 'none';
	            }
	                oH2[p.index].style.display = 'block';

	        for( var j=0; j<oLi.length; j++){
	                oLi[j].style.height = '37px';
	                oLi[j].style.background = '#F0F0F0';
	            }
	                p.style.height = '38px';
	                p.style.background = '#63A8E9';
	    };

	    //点击二级菜单的时候三级菜单会出现，
	    for(var i=0; i<oBow.length; i++){
	            oBow[i].index = i;
	            oBow[i].onclick = function(){

	            for(var i=0; i<oSpan.length; i++){
	              if( oNoff ) {
	                  oSpan[this.index].className = 'active';
	              }else{
	                oSpan[this.index].className = '';
	              }
	          }  ;  
	          		oNoff =!oNoff;
	                    if( oDl[this.index].className == 'active' ){
                        	oDl[this.index].className = '';
	                    }else{
                            for(var i=0; i<oDl.length; i++){
                            	oDl[i].className = '';
                            }
                            oDl[this.index].className = 'active';
	                    }
	            }
	        };  

	    //span点击的时候会让div的宽度变小点，
	    for( var i=0; i<oBtn.length; i++){
	        oBtn[i].onclick = function(){
	            if( oNoff ){
	                   oDiv.style.width = '40px';
	                   oDiv.className = 'tindex' +' '+ 'list_ul' + ' '+ 'fl';
	                   oiFam.style.marginLeft = '41px';
	               }else{
	                    oDiv.style.width = '190px';
	                    oDiv.className = 'list_ul' +' '+'fl';
	                    oiFam.style.marginLeft = '191px';
	               }
	              oNoff = !oNoff; 
	        }
	    };

	    //初始化先调用一下p，让第0个出现
	    loadChange();
		  function calcPageHeight(doc) {
		      var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
		      var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
		      var height  = Math.max(cHeight, sHeight,450);
		      return height;
		  } 
		 function autoHeight(aa){
		 	 var ifr = $('.cont_iframe_Whole iframe[name=ifr'+aa+']')[0];
		 	 var iDoc = ifr.contentDocument || ifr.document;
	  		 var height = calcPageHeight(iDoc);
	  	     ifr.style.height = height + 'px';
			 ifram_Open(ifr);
		 }

	     function ifram_Open(ifr){//获取ifr里面下拉的元素，创建到上面的列表
	    	 	var contentWindow = ifr.contentWindow;//获取ifram里面的元素
				var document00 = contentWindow.document;
	    	 	var oCutting = $(document00).find(".scrollWH");
	    	 	//如果当前的iframe需要滚动条。加个class=scrollWH。高度scrollH自己定义。
	    	 	if(oCutting.length>0){
	    	 		var aH = oH-oCont_Automatic_H-10;
	    	 		$(ifr).height(aH);
	    	 		//$(ifr).height(oH);//计算右边那个高度
	    	 		//$(ifr).find('body').height(aH);
	    	 		oCutting.each(function(){
	    	 			var ph = $(this).attr('scrollH');
	    	 			var phs = parseInt(ph);
	    	 			$(this).height(aH*phs/100);
	    	 			$(this).css('overflow','auto');
	    	 		})
	    	 	}
	    	 	$(contentWindow).click(function(){
	    	 		$('.remove_all dl').hide();
	    	 	})
				//加个class、cutting自动获取他的高度，获取ifram里面的cutting元素。超出则自动出现滚动条
				var aa = $(document00).find("a[target=_self]"); //获取另一个页面中a链接，让它在列表上显示，本地打开，
				if(aa&&aa.length>0){
					for (var i = 0; i < aa.length; i++) {
						$(aa[i]).click(function(){
							var src = this.href;
							var mid = $(this).attr("menuid");
							var name = $(this).attr("menuname");
							open_menu(src,mid,name);
							$(this).parent().parent().parent().removeClass('open');
							return false;
						});
					}
				}
				// 判断是否需要立即关闭当前标签页
				var oclose = $(document00).find("#close[closeNow=true]")[0];
				if(oclose){
					removeli(tabOn);
				}
				//获取到ifram里面的那个a，然后调用removeli事件，让当前的那个li消失。
				$(document00).on('click','#close',function(){
					removeli(tabOn);
				})
				
				//获取查询参数
				var formInput = $(document00).find("#selectForm");
				if (formInput.length > 0) {
					var formParamsid = $(ifr).attr("name");
					var formWParamids = eval("Window."+formParamsid);
					var formWParamidsHtml = eval("Window."+formParamsid+"html");
					if (!formWParamids) {
						formWParamids = Window[formParamsid] = new $.hMap();
						formWParamidsHtml = Window[formParamsid+"html"] = new $.hMap();
					}
					var input = formInput.find("input");
					var select = formInput.find("select");
					var isInit = false;
					input.each(function() {
						isInit = true;
						var name = $(this).attr("name");
						var value = formWParamids.get(name);
						if (value) {
							$(this).val(value);
						}
						$(this).change(function() {
							if(!isInit){
								value = $(this).val();
								var vv = eval("Window."+formParamsid);
								vv.put(name, value);
							}
						});
					});
					select.each(function() {
						isInit = true;
						var obj = $(this);
						var name = $(this).attr("name");
						var value = formWParamids.get(name);
						var valueh = formWParamidsHtml.get(name);
						if (value) {
							obj.html(valueh);
							var ob = obj.find('option[value='+value+']');
							ob.attr('selected','selected');
						}
						$(this).change(function() {
							if(!isInit){
								var vv = eval("Window."+formParamsid);
								var vvh = eval("Window."+formParamsid+"html");
								value = $(this).val();
								vvh.put(name, obj.html());
								vv.put(name, value);
							}
						});
					});
					isInit = false;
				}
	     }
	//自动获取list_ul的高度，根据不同的分辨率，判断是否出现滚动条.根据页面的布局来调用是否自动获取高度
    var oBody_H = $(window).height();//body的高度，
    var oHead_H = $('.head').height();//head的高度
    var oAdd_H = $('.add').height();
    var oH = oBody_H-oHead_H-oAdd_H-10;
    $('.list_ul').height(oH);
//关系管理--数据权限设置
    var oCont_Automatic_H = $('.cont_Automatic_list').height();//获取cont_Automatic_list的高度
   
	     
	     
	     
	     
	     