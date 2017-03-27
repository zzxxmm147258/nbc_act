package com.nbcedu.bas.util;

import com.nbcedu.bas.model.Message;

/**
 * 
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 MessageUtils.java
 * @date         创建日期 2016年12月13日
 * @description  描述
 */
public class MessageUtils {

	public static Message build_success(String msg,Object obj){
		Message message = new Message(true,msg,obj);
		return message;
	}
	
	public static Message build_success(String msg){
		return build_success(msg,null);
	}
	
	public static Message build_success(){
		return build_success(null);
	}
	
	public static Message build_error(String msg,Object obj){
		Message message = new Message(false,msg,obj);
		return message;
	}
	
	public static Message build_error(String msg){
		return build_error(msg,null);
	}
	
	public static Message build_error(){
		return build_error(null);
	}
	
}
