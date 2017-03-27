package com.nbcedu.dic.model;

import java.io.Serializable;

public class Dictinfos implements Serializable{
	
	private static final long serialVersionUID = 3006117900426029624L;

	private Integer dictid;
	
	private String code;
	
	private String cname;
	
	private String ename;
	
    private String zhname;
	
	private String enname;
	
	
	
	public String getZhname() {
		return zhname;
	}

	public void setZhname(String zhname) {
		this.zhname = zhname;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public Dictinfos() {
	}
	
	public Dictinfos(String code,String cname) {
		this.code = code;
		this.cname = cname;
	}
	
	public Integer getDictid() {
		return dictid;
	}

	public void setDictid(Integer dictid) {
		this.dictid = dictid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
		this.zhname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
		this.enname = ename;
	}
}
