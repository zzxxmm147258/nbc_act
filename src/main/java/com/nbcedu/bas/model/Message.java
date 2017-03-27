package com.nbcedu.bas.model;

import java.io.Serializable;

/** 
 * <p>标题：提示信息</p>
 * <p>功能： </p>
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 7857188843832143509L;

	private boolean success;

	private String message;
	
	private Object datas;
	
	private int code;
	
	
	public Message() {
	}
	
	public Message(boolean success) {
		this.success = success;
	}
	
	public Message(boolean success,String message) {
		this.success = success;
		this.message = message;
	}
	
	
	public Message(boolean success,Object datas) {
		this.success = success;
		this.datas = datas;
	}
	
	public Message(boolean success,String message,Object datas) {
		this.success = success;
		this.message = message;
		this.datas = datas;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
}
