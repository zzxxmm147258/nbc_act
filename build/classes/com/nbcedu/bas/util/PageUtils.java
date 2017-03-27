package com.nbcedu.bas.util;

import javax.servlet.http.HttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public class PageUtils {
	
	/**
	 * 获得当前页
	 * @param request
	 * @return
	 */
	public static int getPageSize(HttpServletRequest request){
		String currentPageSize = request.getParameter("pageSize");
		int pageSize = 10 ;
		try {
			pageSize = Integer.parseInt(currentPageSize);
		} catch (Exception e) {
			pageSize = 10;
		}
		return pageSize;
	}
	
	/**
	 * 获得当前页
	 * @param request
	 * @return
	 */
	public static int getCurrentPage(HttpServletRequest request){
		String currentPageStr = request.getParameter("currentPage");
		int currentPage = 1 ;
		try {
			currentPage = Integer.parseInt(currentPageStr);
			String totalPagesNumber = request.getParameter("totalPagesNumber");
			if(totalPagesNumber != null){
				int totals = Integer.parseInt(totalPagesNumber);
				if(currentPage > totals){
					currentPage = totals;
				}
			}
		} catch (Exception e) {
			currentPage = 1;
		}
		return currentPage;
	}
	
	/**
	 * 默认值10
	 * 方法名称:convertPage
	 * 作者:lqc
	 * 创建日期:2017年1月11日
	 * 方法描述:  
	 * @param page
	 * @return PageBounds
	 */
	public static PageBounds convertPage(PageBounds page){
		if(Integer.MAX_VALUE==page.getLimit()){
			page.setLimit(10);
		}
		return page;
	}
	/**
	 * 没有limit参数时，使用外界传递参数
	 * 方法名称:convertPage
	 * 作者:lqc
	 * 创建日期:2017年1月11日
	 * 方法描述:  
	 * @param page
	 * @param limit
	 * @return PageBounds
	 */
	public static PageBounds convertPage(PageBounds page,int limit){
		if(Integer.MAX_VALUE==page.getLimit()){
			page.setLimit(limit);
		}
		return page;
	}
	
}	
