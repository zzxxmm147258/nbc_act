if (! (window.IScroll || window.iScroll)) {
	var calendarUrl = document.scripts[document.scripts.length - 1].src;
	calendarUrl = calendarUrl.substring(0, calendarUrl.lastIndexOf('/') + 1);
    var scrollloadSrc = calendarUrl.replace(/calendar.js/, 'iscroll.js');
    document.write('<script type="text/javascript" src="' + calendarUrl + 'iscroll.js"><\/script>');
} 
(function(win, doc) {
    win.Calendar = function(o) {
        o = o ? o: {};
        $H(Calendar.Utils.Element.PLUGIN).show();
        Calendar.Utils.target = event.target;
        Calendar.Utils.target.readOnly = true;
        o.dateFmt = o.dateFmt ? o.dateFmt: "YYYY-MM-DD HH:mm:ss";
        Calendar.Utils.initDate(o);
    };
    Calendar.Utils = {
        Scroll: {},
        Element: {},
        dateTime: {},
        step: 40,
        types: {YEAR: /Y/i,MONTH: /M/, DAY: /D/i,HOUR: /H/i,MINUTE: /m/,SECOND: /s/},
        extend: function(target, obj) {
            for (var i in obj) {
                target[i] = obj[i];
            }
        },
        initDate: function(o) {
            var date = Calendar.Format.getDate(this.getDateReplace(this.target.value, o.dateFmt));
            var today = Calendar.Format.getDate();
            this.defaultParams = {
                YEAR: {
                    from: parseInt(today.YEAR) - 20,
                    to: parseInt(today.YEAR) + 10,
                    unit: '年',
                    digit: 4,
                    value: date.YEAR
                },
                MONTH: {
                    from: 1,
                    to: 12,
                    unit: '月',
                    digit: 2,
                    value: date.MONTH
                },
                DAY: {
                    from: 1,
                    to: 31,
                    unit: '日',
                    digit: 2,
                    value: date.DAY
                },
                HOUR: {
                    from: 0,
                    to: 23,
                    unit: '时',
                    digit: 2,
                    value: date.HOUR
                },
                MINUTE: {
                    from: 0,
                    to: 59,
                    unit: '分',
                    digit: 2,
                    value: date.MINUTE
                },
                SECOND: {
                    from: 0,
                    to: 59,
                    unit: '秒',
                    digit: 2,
                    value: date.SECOND
                },
            };
            this.extend(this.defaultParams, o);
            var num = 0;
            for (var type in this.types) {
                num = num + ((this.types[type]).test(this.defaultParams.dateFmt) ? 1 : 0);
            }
            for (var type in this.types) {
                var w = num == 6 ? ('YEAR' == type ? 20 : 16) : Math.floor(100 / num);
                var element = $H(this.Element[type]);
                element.css('width', w + '%');
                if ((this.types[type]).test(this.defaultParams.dateFmt)) {
                    element.show();
                } else {
                    element.hide();
                }
                this.defaultParams[type].type = type;
                this.bindli(this.defaultParams[type]);
            }
        },
        bindButton: function() {
            var dateTime = this.dateTime;
            var _this = this;
            this.Element.YES.onclick = function() {
                var date = 'YEAR/MONTH/DAY HOUR:MINUTE:SECOND';
                for (var key in dateTime) {
                    date = date.replace(key, dateTime[key]);
                }
                date = Calendar.Format.toText(_this.getDateVlaue(date), _this.defaultParams.dateFmt);
                $H(_this.Element.PLUGIN).hide();
                if (_this.target.value != date) {
                    _this.target.value = date;
                    if (_this.target.onchange) _this.target.onchange();
                }
            };
            this.Element.TODAY.onclick = function() {
                var today = Calendar.Format.getDate();
                for (var type in _this.types) {
                    _this.dateTime[type] = today[type];
                    _this.scrollToElement(_this.Scroll[type], today[type])
                }
            };
            this.Element.MARK.onclick = function() {
                $H(_this.Element.PLUGIN).hide();
            };
            this.Element.NO.onclick = function() {
                $H(_this.Element.PLUGIN).hide();
            };
        },
        getDateVlaue: function(dateStr) {
            var dateo = Calendar.Format.getDate();
            for (var type in this.types) {
                dateStr = dateStr.replace(type, dateo[type]);
            }
            return dateStr;
        },
        getDateReplace: function(value, dateFmt) {
            return value ? value: new Date();
        },
        bindli: function(o) {
            o = o ? o: {};
            var _this = this;
            this.createLi(o.type, o.from, o.to, o.digit, o.unit);
            var Scroll = this.Scroll[o.type];
            var ele = this.Element[o.type];
            if (Scroll) {
                Scroll.refresh();
            } else {
                this.Scroll[o.type] = Scroll = new IScroll(ele, {
                    bounceTime: 50
                });
                Scroll.scrllType = o.type;
                Scroll.on('scrollEnd',
                function() {
                    if (!this.isScroll) {
                        var y = -Math.round(this.y / _this.step) * _this.step;
                        var ele = this.scroller.querySelector('span[position="' + y + '"]');
                        var value = ele.getAttribute('value');
                        _this.scrollToElement(Scroll, value);
                        if (this.scrllType == 'YEAR' || this.scrllType == 'MONTH') {
                            _this.setLastDayli();
                        }
                        console.log(this.scrllType + ':' + value);
                    } else {
                        this.isScroll = false;
                    }
                });
            }
            this.scrollToElement(Scroll, o.value);
            Scroll.maxScrollY = Scroll.maxScrollY + this.step - 30;
        },
        scrollToElement: function(Scroll, value) {
            if (value) {
                var upSpan = Scroll.scroller.querySelector('.selected');
                if (upSpan) {
                    upSpan.style.color = '#93969d';
                    $H(upSpan).removeClass('selected');
                }
                this.dateTime[Scroll.scrllType] = value;
                Scroll.isScroll = true;
                var thSpan = Scroll.scroller.querySelector('.calendar_' + Scroll.scrllType + '_' + value);
                $H(thSpan).addClass('selected');
                thSpan.style.color = '#333333';
                Scroll.scrollToElement(thSpan, 1000);

            }
        },

        createLi: function(type, from, to, digit, unit) {
            var ele = this.Element[type].querySelector('ul');
            var li = '';
            var k = 0;
            while (from <= to) {
                var v = (from + "");
                for (var i = 0; i < digit - v.length; i++) {
                    v = '0' + v;
                }
                li += '<li style="height:' + this.step + 'px;text-align: center;"><span value="' + from + '" class="calendar_' + type + '_' + from + '" position=' + k + ' style="height:20px;color:#93969d;font-size:18px;">' + v + unit + '</span></li>';
                k = k + this.step;
                from++;
            }
            ele.innerHTML = li;
        },

        setLastDayli: function() {
            var year = this.dateTime['YEAR'];
            var month = this.dateTime['MONTH'];
            var day = this.dateTime['DAY'];
            if (year && month) {
                var lastDay = Calendar.Format.getMLDay(year, month);
                var fistEle = this.Scroll['DAY'].scroller;
                var liLen = 31 - fistEle.querySelectorAll('li[show]').length;
                var sttep = 0;
                if (liLen > lastDay) {
                    for (var i = lastDay; i < liLen; i++) {
                        fistEle.childNodes[i].style.display = 'none';
                        fistEle.childNodes[i].setAttribute('show', null);
                        sttep = sttep + this.step;
                    }
                    if (day > lastDay) {
                        this.scrollToElement(this.Scroll['DAY'], lastDay)
                    }
                } else if (liLen < lastDay) {
                    for (var i = liLen; i < lastDay; i++) {
                        fistEle.childNodes[i].style.display = 'block';
                        fistEle.childNodes[i].removeAttribute('show');
                        sttep = sttep - this.step;
                    }
                }
                this.Scroll['DAY'].maxScrollY = this.Scroll['DAY'].maxScrollY + sttep;
            }
        },
        getLastDay: function(year, month) {
            return new Date(year, month, 0).getDate();
        },
        createElement: function(parentObject, targetObject) {
            var element = document.createElement(targetObject.target);
            if (targetObject.keep) this.Element[targetObject.keep] = element;
            var attrs = targetObject.attrs;
            if (attrs) {
                for (var attr in attrs) {
                    element.setAttribute(attr, attrs[attr])
                }
                if (!element['style']['font-size']) {
                    element['style']['font-size'] = '18px';
                }
            };
            parentObject.appendChild(element);
            if (targetObject.children) {
                if (targetObject.children instanceof Array) {
                    for (var i in targetObject.children) {
                        keep = this.createElement(element, targetObject.children[i]);
                    }
                } else {
                    element.innerHTML = targetObject.children;
                }
            }
        },
        Path: function() {
            var url = window.document.location.href;
            if (/^http:/.test(url) || /^www/.test(url)) {
                var pathName = window.document.location.pathname;
                return (url.substring(0, url.indexOf(pathName)) + pathName.substring(0, pathName.substr(1).indexOf('/') + 1));
            } else {
                return url.substring(0, url.lastIndexOf('/'));
            }
        } (),
        createUI: function() {
            var CalendarDiv = {
                target: 'div',
                keep: "PLUGIN",
                attrs: {
                    id: 'calendarPlugin',
                    style: 'display:none;width:100%;height:100%;top:0;left:0;position: absolute;z-index:999;'
                },
                children: [{
                    target: 'div',
                    attrs: {
                        id: 'calendarWapper',
                        style: 'background-color:#f7f8fa;width:100%;left:0;bottom:0;position: absolute;z-index: 901;'
                    },
                    children: [{
                        target: 'div',
                        attrs: {
                            id: 'calendarTitle',
                            style: 'height:40px;width:100%;text-align: center;'
                        },
                        children: [{
                            target: 'div',
                            attrs: {
                                id: 'calendarTitleLeft',
                                style: 'float:left;height:100%;'
                            },
                            children: [{
                                target: 'span',
                                attrs: {
                                    style: 'line-height:40px;color:#c2b4b4;font-size:20px;'
                                },
                                children: '请选择日期'
                            },
                            {
                                target: 'img',
                                attrs: {
                                    style: 'float:left;height:16px;margin:12px 5px 0 10px',
                                    src: this.Path + '/resources/image/sysimg/date.png'
                                },
                                children: null
                            }]
                        },
                        {
                            target: 'div',
                            attrs: {
                                id: 'calendarTitleRight',
                                style: 'float:right;line-height:40px;width:50px;margin:5px 10px 0 0;'
                            },
                            children: [{
                                target: 'div',
                                keep: 'TODAY',
                                attrs: {
                                    id: 'calendarButton_T',
                                    style: 'width:100%;height:100%;float:left;text-align: center;line-height:30px;font-size:16px;font-family: 微软雅黑;background-color: #e1e5ec;color:#9c9995;border-radius:5px;'
                                },
                                children: '今天'
                            }]
                        }]
                    },
                    {
                        target: 'div',
                        keep: "DATE",
                        attrs: {
                            id: 'calendarDate',
                            style: 'height:120px;overflow:hidden;background-color: white;margin:6px 0px;position: relative;'
                        },
                        children: [{
                            target: 'div',
                            attrs: {
                                id: 'calendarDate_date',
                                style: 'width:100%;height:20px;margin-top:50px;position: relative;z-index: 902;'
                            },
                            children: [{
                                target: 'div',
                                keep: "YEAR",
                                attrs: {
                                    id: 'calendarDate_Y',
                                    style: 'width:20%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendarDate_YY',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            },
                            {
                                target: 'div',
                                keep: 'MONTH',
                                attrs: {
                                    id: 'calendarDate_M',
                                    style: 'width:16%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendarDate_MM',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            },
                            {
                                target: 'div',
                                keep: 'DAY',
                                attrs: {
                                    id: 'calendarDate_D',
                                    style: 'width:16%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendarDate_DD',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            },
                            {
                                target: 'div',
                                keep: 'HOUR',
                                attrs: {
                                    id: 'calendartime_H',
                                    style: 'width:16%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendartime_HH',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            },
                            {
                                target: 'div',
                                keep: 'MINUTE',
                                attrs: {
                                    id: 'calendartime_M',
                                    style: 'width:16%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendartime_MM',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            },
                            {
                                target: 'div',
                                keep: 'SECOND',
                                attrs: {
                                    id: 'calendartime_S',
                                    style: 'width:16%;height:100%;float:left;'
                                },
                                children: [{
                                    target: 'ul',
                                    attrs: {
                                        id: 'calendartime_SS',
                                        style: 'list-style: none;list-style-type:none;padding:0;margin:0;'
                                    },
                                    children: [{
                                        target: 'li'
                                    }]
                                }]
                            }]
                        },
                        {
                            target: 'div',
                            attrs: {
                                id: 'calendarDate_Mark',
                                style: 'width:100%;height:30px;background-color: #eef2f8;top:44px;position: absolute;'
                            },
                            children: null
                        }]
                    },
                    {
                        target: 'div',
                        attrs: {
                            id: 'calendarButton',
                            style: 'height:50px;'
                        },
                        children: [{
                            target: 'div',
                            attrs: {
                                id: 'calendarButtonWapper',
                                style: 'height:100%;width:100%;border-top:1px #f7f8fa solid;background-color:white;'
                            },
                            children: [{
                                target: 'div',
                                attrs: {
                                    id: 'calendarButton_Y',
                                    style: 'height:100%;width:49%;float:right;text-align:center;line-height:50px;color:#338eff;font-weight:bold;font-family:微软雅黑;font-size:20px;border-left:1px #f7f8fa solid;'
                                },
                                children: [{
                                    target: 'span',
                                    keep: 'YES',
                                    children: '确定'
                                }]
                            },
                            {
                                target: 'div',
                                attrs: {
                                    id: 'calendarButton_F',
                                    style: 'height:100%;width:1px;float:right;background-color: #f7f8fa;'
                                },
                                children: null
                            },
                            {
                                target: 'div',
                                attrs: {
                                    id: 'calendarButton_N',
                                    style: 'height:100%;width:49%;float:left;text-align:center;line-height:50px;color:#999999;font-weight:bold;font-family:微软雅黑;font-size:20px;border-right:1px #f7f8fa solid;'
                                },
                                children: [{
                                    target: 'span',
                                    keep: 'NO',
                                    children: '取消'
                                }]
                            }]
                        }]
                    }]
                },
                {
                    target: 'div',
                    keep: 'MARK',
                    attrs: {
                        id: 'calendarMark',
                        style: 'width:100%;height:100%;top:0;left:0;position: absolute;z-index:600;background-color: black;opacity:0.5;'
                    },
                    children: null
                }]
            };
            return this.createElement(doc.body, CalendarDiv);
        },
        init: function() {
            this.createUI();
            this.bindButton();
        }
    };
    Calendar.Format = {
        ZWeek: ['日', '一', '二', '三', '四', '五', '六'],
        EWeek: ['Sun', 'Mon', 'Tues', 'Wed', 'Thur', 'Fri', 'Sat'],
        getDate: function(date, defaut) {
            if (!date) {
                if (undefined != defaut) {
                    date = defaut;
                } else {
                    date = new Date();
                }
            } else if ('string' == typeof(date)) {
                date = date.replace(/-/g, "/");
                date = new Date(date);
                if (date == 'Invalid Date') {
                    date = '';
                }
            } else {
                date = new Date(date);
            }
            var dateObj = {};
            dateObj.date = date;
            dateObj.isDate = (date instanceof Date);
            dateObj.YEAR = date.getFullYear();
            dateObj.MONTH = (date.getMonth() + 1);
            dateObj.DAY = date.getDate();
            dateObj.ZWEEK = this.ZWeek[date.getDay()];
            dateObj.EWEEK = this.EWeek[date.getDay()];
            dateObj.WEEK = date.getDay();
            dateObj.HOUR = date.getHours();
            dateObj.hour = (dateObj.Hour > 12 ? dateObj.Hour - 12 : dateObj.Hour); // 获取当前小时数(0-23)
            dateObj.EPA = dateObj.HOUR<=12?'AM':'PM';
            dateObj.ZPA = dateObj.HOUR<=12?'上午':'下午';
            dateObj.MINUTE = date.getMinutes();
            dateObj.SECOND = date.getSeconds();
            dateObj.MILLISECOND = date.getMilliseconds();
            return dateObj;
        },
        getTen: function(num, n, s) {
            num = num + "";
            for (var i = 0; i < (n - num.length); i++) {
                num = s + num;
            }
            return num;
        },
        getMFDay: function(year, month) {
            return new Date(year, month-1, 1).getDate();
        },
        getMLDay: function(year, month) {
            return new Date(year, month, 0).getDate();
        },
        getYFDay: function(year) {
            return new Date(year, 0, 1);
        },
        replace: function(isCh, date, format, defaut) {
            var dateObj = this.getDate(date, defaut);
            var ch = {};
            if (isCh) {
                ch.YEAR = this.getCh(dateObj.YEAR, 1);
                ch.MONTH = this.getCh(dateObj.MONTH, 2);
                ch.DAY = this.getCh(dateObj.DAY, 2);
                ch.HOUR = this.getCh(dateObj.HOUR, 3);
                ch.hour = this.getCh(dateObj.hour, 3);
                ch.MINUTE = this.getCh(dateObj.MINUTE, 3);
                ch.SECOND = this.getCh(dateObj.SECOND, 3);
                ch.MILLISECOND = this.getCh(dateObj.MILLISECOND, 3);
            }
            if (dateObj.isDate) {
                format = format.replace(/yyyy|YYYY/, isCh ? ch.YEAR: dateObj.YEAR);
                format = format.replace(/yyy|YYY/, (isCh ? ch.YEAR: dateObj.YEAR.toString()).substring(1, 4));
                format = format.replace(/yy|YY/, (isCh ? ch.YEAR: dateObj.YEAR.toString()).substring(2, 4));
                format = format.replace(/MM/, isCh ? ch.MONTH: this.getTen(dateObj.MONTH, 2, '0'));
                format = format.replace(/M/, isCh ? ch.MONTH: dateObj.MONTH);
                format = format.replace(/DD|dd/, isCh ? ch.DAY: this.getTen(dateObj.DAY, 2, '0'));
                format = format.replace(/D|d/, isCh ? ch.DAY: dateObj.DAY);
                format = format.replace(/EW/, dateObj.EWEEK);
                format = format.replace(/W/, dateObj.ZWEEK);
                format = format.replace(/w/, dateObj.WEEK == 0 ? 8 : dateObj.week);
                format = format.replace(/HH/, isCh ? ch.HOUR: this.getTen(dateObj.HOUR, 2, '0'));
                format = format.replace(/hh/, isCh ? ch.HOUR: this.getTen(dateObj.hour, 2, '0'));
                format = format.replace(/EW/, dateObj.EPA);
                format = format.replace(/ZW/, dateObj.ZPA);
                format = format.replace(/H/, isCh ? ch.HOUR: dateObj.HOUR);
                format = format.replace(/h/, isCh ? ch.HOUR: dateObj.hour);
                format = format.replace(/mm/, isCh ? ch.MINUTE: this.getTen(dateObj.MINUTE, 2, '0'));
                format = format.replace(/m/, isCh ? ch.MINUTE: dateObj.MINUTE);
                format = format.replace(/ss/, isCh ? ch.SECOND: this.getTen(dateObj.SECOND, 2, '0'));
                format = format.replace(/s/, isCh ? ch.SECOND: dateObj.SECOND);
                format = format.replace(/zzz/, isCh ? ch.MILLISECOND: this.getTen(dateObj.MILLISECOND, 3, '0'));
                format = format.replace(/zz/, isCh ? ch.MILLISECOND: this.getTen(dateObj.MILLISECOND, 2, '0'));
                format = format.replace(/z/, isCh ? ch.MILLISECOND: dateObj.MILLISECOND);
            } else {
                format = dateObj.date;
            }
            return format;
        },
        toText: function(date, format, defaut) {
            return this.replace(false, date, format, defaut);
        },
        toCh: function(date, format, defaut) {
            return this.replace(true, date, format, defaut);
        },
        ch: ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '廿'],
        dv: ['', '十', '百', '千', '万'],
        getCh: function(str, type) {
            var reStr = '';
            if (1 == type) {
                for (var i = 0; i < str.length; i++) {
                    reStr = reStr + ch[str.charAt(i)];
                }
            } else if (2 == type) {
                var p = parseInt(str);
                if (p <= 10) {
                    reStr = ch[p];
                } else if (p <= 20) {
                    reStr = (p == 20 ? '二十': ('十' + ch[str.charAt(1)]));
                } else if (p < 100) {
                    reStr = (ch[str.charAt(0)] + '十' + ch[str.charAt(1)]);
                }
            } else if (3 == type) {
                for (var i = 0; i < str.length; i++) {
                    var v = i == 0 && str.length == 2 && str.charAt(i) == 1 ? '': ch[str.charAt(i)];
                    reStr = reStr + v + dv[str.length - i - 1];
                }
            }
            return reStr;
        },
        toDate: function(date, defaut) {
            return this.getDate(date, defaut).date;
        },
        toTime: function(date, defaut) {
            var dateObj = this.getDate(date, defaut);
            if (dateObj.isDate) {
                return dateObj.date.getTime();
            } else {
                return dateObj.date;
            }
        },
        getDateByWeeks : function(year, weeks, format){
    		var days = (weeks)*7;
    		weeks = weeks>52?52:weeks;
    		if(52==weeks){
    			days = days + new Date(year-1, 12, 0).getDay();
    		}else{
    			var firstWeek = new Date(year, 0, 1).getDay();
    			if(firstWeek!=0){
    				days = days + 7-firstWeek;
    			}
    		}
    		if (!format) {
                format = 'YYYY-MM-DD';
            }
    		var rs = {LWDate:new Date(year, 0, days)};//周日
    		rs.LWDateText = this.toText(rs.LWDate, format);
    		rs.FWDate = new Date(new Date(rs.LWDate).setDate(rs.LWDate.getDate()-6));//周一
    		rs.FWDateText = this.toText(rs.FWDate, format);
    		return rs;
    	},
        getWeeks: function(date, num, format) {
            var rs = {};
            var dateObj = this.getDate(date);
            if ('number' == typeof(num) && 0 != num) {
                dateObj = this.getDate(dateObj.date.setDate(dateObj.DAY + num * 7));
            } else if (num) {
                format = num;
            }
            if (!format) {
                format = 'YYYY-MM-DD HH:mm:ss';
            }
            if (dateObj.isDate) {
                var WFDate = new Date(dateObj.date).setDate(dateObj.DAY - dateObj.WEEK); //周的第一天
                var WLDate = new Date(dateObj.date).setDate(dateObj.DAY - dateObj.WEEK + 6); //周的最后天
                rs = {
                    Date: dateObj.date,//要计算的日期
                    DateText: this.toText(dateObj.date, format),
                    WFDate: new Date(WFDate),//周的第一天
                    WLDate: new Date(WLDate),//周的最后天
                    WFDateText: this.toText(WFDate, format),//周的第一天
                    WLDateText: this.toText(WLDate, format),//周的最后天
                    TYear: dateObj.YEAR,//要计算的日期的年
                    WYear: dateObj.YEAR,//第几周的年
                    TMonth: dateObj.MONTH,//传入的日期的年
                    TDay: dateObj.DAY,//要计算的日期的日
                    YWeeks: 0,//第几周
                    ZYWeeks: '',//第几周的中文
                    Week: dateObj.WEEK,//周几
                    ZWeek: dateObj.ZWEEK,//周几中文
                    EWeek: dateObj.EWEEK,//周几英文
                    YDays: 0,//传入的日期的第几天
                    ZYDays: '',//传入的日期的第几天中文
                    WDays: dateObj.WEEK + 1 //第几周的第几天
                };
                var fristWeek = this.getYFDay(rs.TYear).getDay();
                for (var i = 1; i < rs.TMonth; i++) {
                    rs.YDays += this.getMLDay(rs.TYear, i);
                }
                rs.YDays += rs.TDay;
                rs.ZYDays = this.getCh(rs.YDays, 3);;
                var lastDays = rs.YDays - 7 + (fristWeek == 0 ? 7 : fristWeek);
                if (lastDays <= 0) {
                    rs.YWeeks = 52;
                    rs.WYear--;
                } else {
                    rs.YWeeks = Math.ceil(lastDays / 7);
                }
                rs.ZYWeeks = this.getCh(rs.YWeeks, 3);
            }
            return rs;
        }
    };
    win.$H = function(ele) {
        if (ele && ele.isCele) return s;
        var eles = [];
        if ('string' == typeof(ele)) {
            eles = document.querySelectorAll(ele);
        } else if (ele instanceof Element) {
            eles = [ele];
        }
        return {
            eles: eles,
            isCele: true,
            show: function() {
                this.each(function() {
                    this['style']['display'] = 'block';
                });
                return this;
            },
            hide: function() {
                this.each(function() {
                    this['style']['display'] = 'none';
                });
                return this;
            },
            css: function(name, value) {
                this.each(function() {
                    this['style'][name] = value;
                });
                return this;
            },
            addClass: function(className) {
                this.each(function() {
                    if (this.className) this.className = this.className + ' ' + className
                });
            },
            removeClass: function(className) {
                this.each(function() {
                    if (this.className) this.className = this.className.replace(className, '').replace(/ $/g, '');
                });
            },
            attr: function(name, value) {
                if (this.eles && this.eles.length > 0) {
                    if (undefined == value) {
                        return this.eles[0].getAttribute(name);
                    } else {
                        this.each(function() {
                            this.setAttribute(name, value);
                        })
                    }
                }
                return this;
            },
            removeAttr: function() {
                this.each(function() {
                    this.setAttribute(name, value);
                })
            },
            each: function(fn) {
                if (!this.eles) this.eles = [];
                for (var i = 0; i < this.eles.length; i++) {
                    this.eles[i].fun = fn;
                    if (this.eles[i].fun(i) == false) break;
                }
                return this;
            },
            text: function(text) {
                if (this.eles && this.eles.length > 0) {
                    if (undefined == text) {
                        return this.eles[0].innerText;
                    } else {
                        this.each(function() {
                            this.innerText = text;
                        })
                    }
                }
                return this;
            },
            html: function(html) {
                if (this.eles && this.eles.length > 0) {
                    if (undefined == html) {
                        return this.eles[0].innerHTML;
                    } else {
                        this.each(function() {
                            this.innerHTML = html;
                        })
                    }
                }
                return this;
            }
        };
    }
    loaded = function() {
        Calendar.Utils.init();
    }
    if (doc.readyState == 'interactive') {
        loaded();
    } else {
        doc.addEventListener('DOMContentLoaded', loaded, false);
    }
})(window, document)