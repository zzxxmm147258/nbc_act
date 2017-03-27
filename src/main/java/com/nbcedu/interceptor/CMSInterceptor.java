package com.nbcedu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nbcedu.bas.constant.ContextConstant;
/**
 * 后台功能权限拦截
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 CMSInterceptor.java
 * @date         创建日期 2016年12月29日
 * @description  描述
 */
public class CMSInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean bl = request.getSession(true).
				getAttribute(ContextConstant.SESSION_ADMIN)!=null?true:false;
		if(bl){
			return bl;
		}
		response.sendRedirect(request.getSession(true).getServletContext().
				getContextPath()+ContextConstant.CMS_LOGIN_URL);
		return bl;
	}
	
}
