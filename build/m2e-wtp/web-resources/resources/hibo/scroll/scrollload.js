var scrollloadUrl = document.scripts[document.scripts.length-1].src;
scrollloadUrl = scrollloadUrl.substring(0,scrollloadUrl.lastIndexOf('/')+1);
if(!(window.IScroll||window.iScroll)){
	document.write('<script type="text/javascript" src="'+ scrollloadUrl + 'iscroll.js"><\/script>');
}
document.write('<link rel="stylesheet" type="text/css" href="'+ scrollloadUrl + 'scrollbar.css"/>');
/**
var dropload = HiboScroll({
		scroll:'#wrapper',
		loadUp : function(){
			dropload.resetload(isReload);//初始满屏不出现上拉加载
		},
		loadDown : function(){
			dropload.resetload();//延时重置
		},
		load : function(){
			dropload('scroll',function () {
				console.log(this.y);			
			});
		}
	});
*/
HiboScroll = function(o){
		var Scroll = {Scroll:null,pullDownEl:null,pullUpEl:null,loadUp:o.loadUp,loadDown:o.loadDown,isload:(o.loadUp||o.loadDown),load:o.load};
		Scroll.lang = {
			lan : function(){
				var lan = null;
				var r = window.location.search.substr(1).match(new RegExp("(^|&)" + name + "=([^&]*)(&|$)"));
				if (r != null) lan = decodeURI(r[2]);
				if(lan){
					localStorage.setItem('hibo-language',lan);
				}else{
					lan = localStorage.getItem('hibo-language');
					lan = lan?lan:'zh';
				}
				return lan;
			}(),
			zh:{
				down_all:'下拉刷新',
				down_load : '松手开始加载',
				up_more : '上拉加载更多',
				up_refresh : '松手开始更新',
				load : '加载中...',
			},
			en:{
				down_all:'Refresh all',
				down_load : 'Let Go Load',
				up_more : 'Load More',
				up_refresh : 'Let Go Refresh',
				load : 'loading...'
			}
		},
		/**
		 * 上拉加载
		 * isFullScreen：是否满屏;
		 * */
		Scroll.loadUpFn = function(isFullScreen) {
			if(this.loadUp){
				this.loadUp(isFullScreen);
			}else{
				this.resetload();
			}
			return this;
		}
		/**
		 * 下拉刷新
		 * isFullScreen：是否满屏;
		 * */
		Scroll.loadDownFn = function(isFullScreen) {
			if(this.loadDown){
				this.loadDown(isFullScreen);
			}else{
				this.resetload();
			}
			return this;
		}
		Scroll.resetload = function(isReload,time){
			var that = this;
			if(!time){
				time=500;
			};
			setTimeout(function () {
				if(that.pullUpEl) that.pullUpEl.className = isReload==false?'HiboScroll_stop':'HiboScroll_show';
				that.Scroll.refresh();
			}, time);
			return this;
		}
		Scroll.refresh = function(isReload){
			if(this.pullUpEl) this.pullUpEl.className = isReload==false?'HiboScroll_stop':'HiboScroll_show';
			this.Scroll.refresh();
			return this;
		}
		/**
		 * 内容是否满屏可滚动
		 * */
		Scroll.isScroll = function(){
			return this.Scroll.scrollerHeight>this.Scroll.wrapperHeight;
			return this;
		}
		Scroll.on = function(type, fn){
			this.Scroll.on(type, fn);
			return this;
		}
		Scroll.init=function(){
			var wrapper = document.querySelector(o.scroll);
			var scroller = document.createElement("div");
			scroller.id = 'HiboScroll_scroller';
			scroller.setAttribute('style','overflow:hidden;transition-property: transform; transform-origin: 0px 0px 0px; transform: translate(0px, -51px) scale(1) translateZ(0px);');
			if(wrapper.children){
				while(wrapper.children.length>0){
					var child = wrapper.children[0];
					scroller.appendChild(child);
				}
			}
			var sc = {probeType: 2,preventDefault:false,mouseWheel:false,bounceTime:400};
			wrapper.appendChild(scroller);
			if(this.loadDown){
				var pullDownEl = document.createElement("div");
				pullDownEl.id = 'HiboScroll_pullDown';
				pullDownEl.className='HiboScroll_show';
				pullDownEl.innerHTML = '<div class="HiboScroll_box"><span class="HiboScroll_pullDownIcon"></span><span class="HiboScroll_pullDownLabel">'+this.lang[this.lang.lan].down_all+'</span></div>';
				if(scroller.firstElementChild){
					scroller.insertBefore(pullDownEl, scroller.firstElementChild);
				}else{
					scroller.appendChild(pullDownEl);
				}
				this.pullDownEl = pullDownEl;
				sc.startY=-pullDownEl.offsetHeight;
			}
			if(this.loadUp){
				var pullUpEl = document.createElement("div");
				pullUpEl.id = 'HiboScroll_pullUp';
				pullUpEl.className='HiboScroll_show';
				pullUpEl.innerHTML = '<div class="HiboScroll_box"><span class="HiboScroll_pullUpIcon"></span><span class="HiboScroll_pullUpLabel">'+this.lang[this.lang.lan].up_more+'</span></div>'
				scroller.appendChild(pullUpEl);
				this.pullUpEl = pullUpEl;
			}
			for (i in o) sc[i] = o[i];
			delete sc.scroll;
			delete sc.loadUp;
			delete sc.loadDown;
			var newScroll = new IScroll(wrapper,sc);
			if(this.isload){
				var _this = this;
				newScroll.on('refresh',function () {
					if (Scroll.loadDown&&pullDownEl.className.match('HiboScroll_loading')) {
						pullDownEl.className = 'HiboScroll_show';
						pullDownEl.querySelector('.HiboScroll_pullDownLabel').innerHTML = _this.lang[_this.lang.lan].down_all;
					} else if (Scroll.loadUp&&pullUpEl.className.match('HiboScroll_loading')) {
						pullUpEl.querySelector('.HiboScroll_pullUpLabel').innerHTML = _this.lang[_this.lang.lan].up_more;
					}
					if(Scroll.loadUp){
						this.maxScrollY = this.maxScrollY + pullUpEl.offsetHeight;
					}
					if(Scroll.loadDown){
						this.options.startY = -pullDownEl.offsetHeight;
					}					
				});
				newScroll.on('scroll',function () {
					if (Scroll.loadDown&&this.y > 0 && pullDownEl.className.match('HiboScroll_show')) {
						pullDownEl.className = 'HiboScroll_flip';
						pullDownEl.querySelector('.HiboScroll_pullDownLabel').innerHTML = _this.lang[_this.lang.lan].up_refresh;
						this.options.startY = 0;
					} else if (Scroll.loadDown&&this.y < 0 && pullDownEl.className.match('HiboScroll_flip')) {
						pullDownEl.className = 'HiboScroll_show';
						pullDownEl.querySelector('.HiboScroll_pullDownLabel').innerHTML = _this.lang[_this.lang.lan].down_all;
						this.options.startY = -pullDownEl.offsetHeight;
					} else if (Scroll.loadUp&&this.y < (this.maxScrollY - pullUpEl.offsetHeight) && pullUpEl.className.match('HiboScroll_show')) {
						pullUpEl.className = 'HiboScroll_flip';
						pullUpEl.querySelector('.HiboScroll_pullUpLabel').innerHTML = _this.lang[_this.lang.lan].down_load;
						this.maxScrollY = this.maxScrollY - pullUpEl.offsetHeight;
					} else if (Scroll.loadUp&&this.y > this.maxScrollY && pullUpEl.className.match('HiboScroll_flip')) {
						pullUpEl.className = 'HiboScroll_show';
						pullUpEl.querySelector('.HiboScroll_pullUpLabel').innerHTML = _this.lang[_this.lang.lan].up_more;
						this.maxScrollY = this.maxScrollY + pullUpEl.offsetHeight;
					}
				});
				newScroll.on('scrollEnd',function () {
					if (Scroll.loadDown&&pullDownEl.className.match('HiboScroll_flip')) {
						pullDownEl.className = 'HiboScroll_loading';
						pullDownEl.querySelector('.HiboScroll_pullDownLabel').innerHTML = _this.lang[_this.lang.lan].load;				
						Scroll.loadDownFn(Scroll.isScroll());
					} else if (Scroll.loadUp&&pullUpEl.className.match('HiboScroll_flip')) {
						pullUpEl.className = 'HiboScroll_loading';
						pullUpEl.querySelector('.HiboScroll_pullUpLabel').innerHTML = _this.lang[_this.lang.lan].load;
						Scroll.loadUpFn(Scroll.isScroll());
					}
				});
			}
			this.Scroll=newScroll;
			if(this.load){
				this.load(document);
			}
			this.loadUpFn(Scroll.isScroll());
			//取消默认事件
			scroller.addEventListener('touchmove', function (e) { e.preventDefault(); }, true);
			scroller.addEventListener('mousemove', function (e) { e.preventDefault(); }, true);
			return this;
		};
		/**
		 * 初始化iScroll控件
		 */
		window.onload = function(){
			Scroll.init();
		}
		/*loaded = function() {
			Scroll.init();
		}
		if(document.readyState=='interactive'){
			loaded();
		}else{
			document.addEventListener('DOMContentLoaded', loaded, false); 
		}*/
		return Scroll;
};