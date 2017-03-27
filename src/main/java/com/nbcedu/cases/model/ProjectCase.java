package com.nbcedu.cases.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.model.ModelBas;
import com.nbcedu.bas.util.RandomUtils;

public class ProjectCase extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8407904913378092307L;

	private String number=String.valueOf(RandomUtils.get_random_num());//案例编号
	
	private String mId;

    private String name;

    private Date dateTime;

    private String imgUrl;

    private String school;

    private String gradeId;

    private String des;

    private Integer sort;

    private String status="10";//10=有效，20=无效

    private String delStatus="0";//0：未删除，1：已删除

    private String attr;

    private String detail;
    
    private Integer imgNum;//图片数量

    private MultipartFile file;//上传的展示图
    
    private String date_time;//辅助字段 活动时间
    
    

    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId == null ? null : gradeId.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus == null ? null : delStatus.trim();
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getImgNum() {
		return imgNum;
	}

	public void setImgNum(Integer imgNum) {
		this.imgNum = imgNum;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
    
}