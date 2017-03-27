package com.nbcedu.bas.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.nbcedu.bas.util.ObjectId;

/**
 * <p>标题：父类</p>
 * <p>功能： </p>
 */
public class ModelBas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 941169337350943119L;

	private String id = ObjectId.getUUId();
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime = new Date();
	
    private Long createMs=System.currentTimeMillis(); //时间戳
	
	private Long updateMs=System.currentTimeMillis();//编辑时间戳

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateMs() {
		return createMs;
	}

	public void setCreateMs(Long createMs) {
		this.createMs = createMs;
	}

	public Long getUpdateMs() {
		return updateMs;
	}

	public void setUpdateMs(Long updateMs) {
		this.updateMs = updateMs;
	}
	
}
