package com.nbcedu.project.model;

import java.math.BigDecimal;
import java.util.List;

import com.nbcedu.bas.model.ModelBas;

public class ProjectConfig extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8936059082966243057L;

	private String mId;

    private String typeId;

    private String typeName;

    private String priceType;//价格类型（10=平均价格，20=固定价格）
    
    private BigDecimal price;

    private BigDecimal fixedPrice;

    private String selectType;

    private Integer sort;

    private String attr;

    private String detail;
    
    private List<Detail> det;

    
    
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType == null ? null : selectType.trim();
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

   public List<Detail> getDet() {
		return det;
	}

	public void setDet(List<Detail> det) {
		this.det = det;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
    
    
}