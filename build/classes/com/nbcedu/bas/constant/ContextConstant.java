package com.nbcedu.bas.constant;


/**
 * 静态常量
 * @author       作者 yuanwang
 * @version      版本 0.01
 * @filename     文件名 ContextConstant.java
 * @date         创建日期 2016年3月7日
 * @description  描述
 */
public class ContextConstant {
	/**
	 * 前台用户Session_key
	 */
	public static final String SESSION_USER = "session_user";
	/**
	 * 后台用户Session_key
	 */	
	public static final String SESSION_ADMIN = "session_admin";
	/**
	 * 操作成功code值
	 */
	public static final String SUCCESS_CODE = "200";
	/**
	 * 操作失败code值
	 */
	public static final String ERROR_CODE = "201";
	/**
	 * 后台登录页面 url
	 */
	public static final String CMS_LOGIN_URL="/main/admin/login";
	/**
	 * 前台登录页面 url
	 */
	public static final String FRONT_LOGIN_URL="/common/user/login";
	/**
	 * 错误页面（400，404，500 等其他页面）
	 */
	public static final String ERROR_PAGE = "";
	
	/**
	 * 已删除标识
	 */
	public static final String DEL_STATUS_YES = "1";
	/**
	 * 未删除标识
	 */
	public static final String DEL_STATUS_NO = "0";
}
