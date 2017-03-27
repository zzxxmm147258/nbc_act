package com.nbcedu.project.model;

import java.math.BigDecimal;

import com.nbcedu.bas.model.ModelBas;

public class ProjectNumConfig extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5691722498504747525L;

	private String mId;

    private Integer actMinNumber;

    private Integer actMaxNumber;

    private Integer escortMinNumber;

    private Integer escortMaxNumber;

    private Boolean isEscort;

    private BigDecimal price;

    private String attr;


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public Integer getActMinNumber() {
        return actMinNumber;
    }

    public void setActMinNumber(Integer actMinNumber) {
        this.actMinNumber = actMinNumber;
    }

    public Integer getActMaxNumber() {
        return actMaxNumber;
    }

    public void setActMaxNumber(Integer actMaxNumber) {
        this.actMaxNumber = actMaxNumber;
    }

    public Integer getEscortMinNumber() {
        return escortMinNumber;
    }

    public void setEscortMinNumber(Integer escortMinNumber) {
        this.escortMinNumber = escortMinNumber;
    }

    public Integer getEscortMaxNumber() {
        return escortMaxNumber;
    }

    public void setEscortMaxNumber(Integer escortMaxNumber) {
        this.escortMaxNumber = escortMaxNumber;
    }

    public Boolean getIsEscort() {
        return isEscort;
    }

    public void setIsEscort(Boolean isEscort) {
        this.isEscort = isEscort;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

   
}