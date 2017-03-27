<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%@include file="../head_content.jsp" %>
    <link rel="stylesheet" href="${prc}/resources/html/front/css/bigimg.css" type="text/css" />
    <link rel="stylesheet" href="${prc}/resources/html/front/css/pubu.css" type="text/css" />
</head>
<body>
   
    <!-- 导航以上顶部  -->
   <jsp:include page="../header.jsp" />
    
    <!-- title -->
    <p class="title01">${bean.name }</p>
    <div>
	    <p class="font14 textCenter" >
	        <a href="#" class="mr20">${bean.school }</a>
	        <a href="#" class="mr20"><fmt:formatDate value="${bean.dateTime }" pattern="yyyy年MM月dd日"/></a>
	    </p>
	    
	</div>
    <!--  精彩图片  开始-->
    <div style="max-width:1170px; margin:0 auto; overflow:hidden; margin-bottom:15px">
        <div class="titleBg">
            <img src="${prc}/resources/html/front/images/left.png" alt="" class="fl">
            <span class="fl  greenIcon">精彩图片<i class="font14">(${fn:length(img)})</i></span>
            <img src="${prc}/resources/html/front/images/right.png" alt="" class="fl">
        </div>
    </div>
    <!-- 精彩图片  结束 -->
    <!--  瀑布流  开始-->
    <div id="wrapper" class="clear">
        <div id="container" style="width:995px;">
           <c:forEach items="${img}" var="b" varStatus="i" >
            <div class="grid">
                <div class="imgholder">
                    <img class="lazy thumb_photo" title="${i.count }" src="${prc }/images/pixel.gif" data-original="${prc}/image/${b.imgUrl}/cut003" width="225" />
                    <div class="grid-btm">${b.title}</div>
                </div>
            </div>
           </c:forEach>
        </div>
    </div>
    <!--瀑布流 end-->

    <!--大图弹出层 start-->
    <div class="container">
        <div class="close_div">
            <img src="${prc}/resources/html/front/images/closelabel.gif" class="close_pop" title="关闭" alt="关闭" style="cursor:pointer">
        </div>
        <div class="content">
            <span style="display:none"><img src="${prc}/resources/html/front/images/load.gif" /></span>
            <div class="left"></div>
            <div class="right"></div>
            <c:forEach items="${img}" var="b" varStatus="i">
               <img class="img" src="${prc }/image/${b.imgUrl}" >
            </c:forEach>
            
        </div>
        <div class="bottom">共 <span id="img_count">x</span> 张 / 第 <span id="xz">x</span> 张</div>
    </div>
    <!--end-->
    <!--底栏  footer -->
    <div class="footer" style="margin-top:30px">
        <div style="padding-right:15px; max-width:1170px; margin:0 auto">
            <p class="fl" style="width:50%; font-size:12px; line-height:20px; margin-top:30px">
                北京三芳科技有限公司&nbsp;&nbsp;
                <br>Copyright © 2012-2015 sunfuedu.com</p>
            <p class="fr phone" style="width:50%">
                客服热线：400 5798 987</p>
        </div>
    </div>
    
</body>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/notification.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/bigimg.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/blocksit.min.js"></script>
<script type="text/javascript" src="${prc}/resources/html/front/js/pubu.js"></script>
<script type="text/javascript">
    var count = 14;
    $(document).ready(function() {
    	window.scroll();
        // 点击加载更多
        $('.load_more').click(function() {
            var html = "";
            var img = '';
            for (var i = count; i < count + 13; i++) {
                var n = Math.round(Math.random(1) * 13);
                var src = '${prc}/resources/html/front/images/' + n + '.jpg';
                html = html + "<div class='grid'>" +
                    "<div class='imgholder'>" +
                    "<img class='lazy thumb_photo' title='" + i + "' src='${prc}/resources/html/front/images/pixel.gif' data-original='" + src + "' width='225' onclick='seeBig(this)'/>" +
                    "</div>" +
                    "</div>";
                img = img + "<img class='img' src='" + src + "'>";
            }
            count = count + 13;
            $('#container').append(html);
            $('.content').append(img);
            $('#container').BlocksIt({
                numOfCol: 4, //每行显示数
                offsetX: 5, //图片的间隔
                offsetY: 5 //图片的间隔
            });
            $("img.lazy").lazyload();
        });
    });
    
    
</script>

</html>
