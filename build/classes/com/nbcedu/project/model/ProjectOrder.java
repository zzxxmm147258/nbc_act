package com.nbcedu.project.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nbcedu.bas.model.ModelBas;
import com.nbcedu.bas.util.RandomUtils;

public class ProjectOrder extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3819588757578602512L;
	
	private String number=String.valueOf(RandomUtils.get_random_num());//项目编号

	private String mId;

    private String userId;

    private Integer num;

    private String configId;

    private BigDecimal totalPrice;

    private BigDecimal numberPrice;

    private Integer escortNumber;

    private BigDecimal escortTotalPrice;

    private BigDecimal escortPrice;

    private BigDecimal total;

    private String userName;

    private String phone;

    private Date dateTime;

    private String status;

    private String delStatus="0";//0：未删除，1：已删除

    private Integer sort;

    private String attr;

    private List<ProjectConfig> config;//订单的配置项
    
    private String tName;//辅助字段 所属产品体系名称
    private String pName;//辅助字段 项目名称
    private String uName;//辅助字段 会员账号
    private String imgUrl;//项目展示图
    
    
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getNumberPrice() {
        return numberPrice;
    }

    public void setNumberPrice(BigDecimal numberPrice) {
        this.numberPrice = numberPrice;
    }

    public Integer getEscortNumber() {
        return escortNumber;
    }

    public void setEscortNumber(Integer escortNumber) {
        this.escortNumber = escortNumber;
    }

    public BigDecimal getEscortTotalPrice() {
        return escortTotalPrice;
    }

    public void setEscortTotalPrice(BigDecimal escortTotalPrice) {
        this.escortTotalPrice = escortTotalPrice;
    }

    public BigDecimal getEscortPrice() {
        return escortPrice;
    }

    public void setEscortPrice(BigDecimal escortPrice) {
        this.escortPrice = escortPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

	public List<ProjectConfig> getConfig() {
		return config;
	}

	public void setConfig(List<ProjectConfig> config) {
		this.config = config;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
   
}