<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../common/taglibs.jsp" %>
<!-- 项目配置 -->
<!-- 第1栏  费用说明-->

<c:choose> 
    <c:when test="${empty config}">  
                <div style="text-align: center;padding: 40px 0;">
                     <img src="${prc}/resources/html/front/images/null.png" alt="">
                </div>
    </c:when>
    <c:otherwise >  
                       
<div class="deploy-box  mtClear  mt-3">
	<div class="deployCon padClear">
		<p class="font18 ">费用说明</p>
		<!-- 表格  开始 -->
		<div id="tableDetail">
			<table class="table border01">
				<thead>
					<tr class="font16  trbg01">
					    <c:forEach items="${config }" var="b" varStatus="i" >
						    <th>${b.typeName }<span class="font12">(${b.selectType eq '20' ?'必选':'可选'})</span></th>
					    </c:forEach>
					</tr>
				</thead>
				<tbody>
					<tr class="trbg02">
					   <c:forEach items="${config }" var="b" varStatus="i" >
					       <td>
					        <c:choose> 
                             <c:when test="${b.priceType eq '10' }">  
                                 ${b.price}元/人
                             </c:when>
                             <c:otherwise >  
                                 ${b.price}元
                             </c:otherwise>
                            </c:choose>
						  </td>
					   </c:forEach>
					</tr>

					<tr>
					   <c:forEach items="${config }" var="b" varStatus="i" >
						<td><a  class="color08 dropdown-toggle count-info"
							${empty b.det?'style="cursor: default;color:#878686;"':'data-toggle=dropdown' }   >查看明细</a> <!--  查看明细  弹窗-->
							<ul class="dropdown-menu dropdown-messages alert01">
								<li>
									<p class="pt03">${b.typeName }的明细</p>
									<ol style="padding-left: 0;">
									   <c:forEach items="${b.det }" var="d" varStatus="i" >
										   <li>
										       <span class="mr15">${d.key }</span> 
										       <span >${d.val }元</span>
										   </li>
									    </c:forEach>
									</ol>
								</li>
							</ul>
						</td>
                       </c:forEach>
				
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 选择配置 -->
	<div class="deployCon clear pd10 pb15">
		<p class="font18 ">选择配置</p>
		<div class="overHidden">
			<p class="fl mr50">活动人数：</p>
			<!-- 增减输入框 -->
			<div class="item-amount  item01 fl overHidden mr20">
				<a href="javascript:void(0);"  class="${not empty order.num and order.num>num.actMinNumber?'minus':'minus no-minus' }" data="-" number="${num.actMinNumber }">-</a>
				<input type="text"  value="${order.num>0?order.num:num.actMinNumber }" id="itemAmount01" class="text-amount">
				<c:choose> 
                     <c:when test="${empty order}">  
                           <a href="javascript:void(0);" class="${empty order?'plus':'plus no-plus' }" data="+" number="${num.actMaxNumber }">+</a>
                     </c:when>
                     <c:otherwise >  
                            <a href="javascript:void(0);" class="${order.num<num.actMaxNumber?'plus':'plus no-plus' }" data="+" number="${num.actMaxNumber }">+</a>
                     </c:otherwise>
                </c:choose>
			</div>
			<span class="color07 fl font14">（范围：<i>${num.actMinNumber }-${num.actMaxNumber }</i>人）
			</span>
		</div>
		<!-- end-->

		<table class="check-box">
			<tbody>
				<tr>
				   <c:forEach items="${config }" var="b" varStatus="i" >
					   <td class="mr50 check-mail">
					       <c:choose> 
                             <c:when test="${not empty order and fn:contains(order.configId, b.id)}">  
                                 <input type="checkbox" id="${b.id }" class="i-checks" name="price" priceType="${b.priceType }"  value="${b.price}"  checked="true"  ${b.selectType eq '20' ?'disabled="true"   ':''} >
                             </c:when>
                             <c:otherwise >  
                                 <input type="checkbox" id="${b.id }" class="i-checks" name="price" priceType="${b.priceType }"  value="${b.price}"  ${b.selectType eq '20' ?'disabled="true" checked="true"  ':''}>
                             </c:otherwise>
                            </c:choose>
                            <p>${b.typeName }：
                               <i>
					             <c:choose> 
                                    <c:when test="${b.priceType  eq '10'}">  
                                     ${b.price}元/人
                                    </c:when>
                                    <c:otherwise >  
                                    ${b.price}元
                                    </c:otherwise>
                                 </c:choose>
                               </i>
                            </p>
					   </td>
					  
					</c:forEach>
				</tr>
			</tbody>
		</table>

		<span class="color06  font18 mr60">总费用：<i id="totalPrice">${order.totalPrice>0?order.totalPrice:0 }元</i>
		    <input type="hidden" value="" name="totalPrice" id="totalPriceIput">
		</span>
		<span class="color06  font18">人均费用：<i id="numberPrice">${order.numberPrice>0?order.numberPrice:0 }元/人</i>
			<input type="hidden" value="" name="numberPrice" id="numberPriceIput">
		</span>
	</div>

	<!-- 陪同人数 -->
	<div class="deployCon clear pd10 pb15 pt01" style="${num.isEscort?'':'display:none;'  }">
		<div class="overHidden">
			<p class="fl mr50">陪同人数：</p>
			<!-- 增减输入框 -->
			<div class="item-amount fl item02 overHidden mr20">
				<a href="javascript:void(0);" class="${not empty order.escortNumber and order.escortNumber>num.escortMinNumber?'minus':'minus no-minus' }" data="-" number="${num.escortMinNumber }">-</a>
				<input type="text" value="${order.escortNumber>0?order.escortNumber:num.escortMinNumber }" id="itemAmount02" class="text-amount">
				<c:choose> 
                     <c:when test="${empty order}">  
                           <a href="javascript:void(0);" class="${empty order?'plus':'plus no-plus' }" data="+" number="${num.escortMaxNumber }">+</a>
                     </c:when>
                     <c:otherwise >  
                            <a href="javascript:void(0);" class="${order.escortNumber<num.escortMaxNumber?'plus':'plus no-plus' }" data="+" number="${num.escortMaxNumber }">+</a>
                     </c:otherwise>
                </c:choose>
			</div>
			<span class="color07 fl font14">（范围：<i>${num.escortMinNumber }-${num.escortMaxNumber }</i>人）
			</span>
		</div>
		<span class="color06  font18 mr60">总费用：<i id="escorTotal" >${order.escortTotalPrice>0?order.escortTotalPrice:0 }元</i>
		 <input type="hidden" value="" name="escorTotal" id="escorTotalIput">
		</span> <span
			class="color06  font18">人均费用：<i id="escorPrice" escorPrice="${num.price }" >${num.price }元/人</i>
			<input type="hidden" value="${num.price }" name="escorPrice" >
			</span>
	</div>

	<!-- 项目费用总和 -->
        <div class="deployCon clear pd10 pb02 overHidden borderclear">
            <p class="color06  font18 mr60">项目费用总和：<i id="total">${order.total>0?order.total:0 }元</i></p>
            <div class="overHidden mb02">
                <p>联系方式：</p>
                <input type="text" id="userName" value="${order.userName }" class="form-control  inputEdit   fl" placeholder="请输入联系人">
                <p class="fl">&nbsp;&nbsp;&nbsp;---&nbsp;&nbsp;&nbsp;</p>
                <input type="text" id="phone" value="${order.phone }"  class="form-control mr50 inputEdit   fl" placeholder="请输入联系电话">
            </div>
            <div class="subBox">
                <input type="hidden" value="${mId }" id="mId"/>
                <input type="hidden" value="${order.id }" id="orderId"/>
                <button class="btn btn-primary  width-sub" type="button" id="submit">提&nbsp;交</button>
            </div>
        </div>

</div>
 </c:otherwise>
</c:choose>
<!-- end -->
