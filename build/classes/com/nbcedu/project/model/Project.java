package com.nbcedu.project.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.model.ModelBas;
import com.nbcedu.bas.util.RandomUtils;
import com.nbcedu.cases.model.ProjectCase;

public class Project extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2430640009989823160L;
	
	private String number=String.valueOf(RandomUtils.get_random_num());//项目编号

	private String typeId;

    private String classId;

    private String gradeId;

    private String name;

    private Integer recommend;

    private Boolean isRecommend;

    private String des;

    private String imgUrl;

    private String details;

    private String arrt;

    private String status;

    private String delStatus="0";//0：未删除，1：已删除
    
    private Integer orderNum;//辅助字段 项目订单数量
    
    private Integer caseNum;//辅助字段 项目案例数量
    private Integer configNum;//辅助字段 配置数量
    
    private List<ProjectCase> cases;//项目案例
    private List<ProjectConfig> config;//项目配置
    private ProjectNumConfig num;//项目人数配置
    private MultipartFile file;//上传的展示图
    
    private String caseIds;//案例ID
    private String caseImages;//案例图片
    private String caseNames;//案例名称
    private String[] ids;//案例ID
    private String[] images;//案例图片
    private String[] names;//案例名称

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId == null ? null : gradeId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public String getArrt() {
        return arrt;
    }

    public void setArrt(String arrt) {
        this.arrt = arrt == null ? null : arrt.trim();
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

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(Integer caseNum) {
		this.caseNum = caseNum;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<ProjectCase> getCases() {
		return cases;
	}

	public void setCases(List<ProjectCase> cases) {
		this.cases = cases;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public List<ProjectConfig> getConfig() {
		return config;
	}

	public void setConfig(List<ProjectConfig> config) {
		this.config = config;
	}

	public ProjectNumConfig getNum() {
		return num;
	}

	public void setNum(ProjectNumConfig num) {
		this.num = num;
	}

	public Integer getConfigNum() {
		return configNum;
	}

	public void setConfigNum(Integer configNum) {
		this.configNum = configNum;
	}

	public String getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(String caseIds) {
		this.caseIds = caseIds;
	}

	public String getCaseImages() {
		return caseImages;
	}

	public void setCaseImages(String caseImages) {
		this.caseImages = caseImages;
	}

	public String getCaseNames() {
		return caseNames;
	}

	public void setCaseNames(String caseNames) {
		this.caseNames = caseNames;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
	
}