package com.nbcedu.project.model;

import java.util.List;

import com.nbcedu.bas.model.ModelBas;

public class ProjectType extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1549458775681416667L;

	private String name;

    private Integer sort;

    private Boolean isAvailable;

    private String pId;
    
    private String des;//简介
    
    private List<ProjectType> type;
    
    private List<Project> project;
   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

	public List<ProjectType> getType() {
		return type;
	}

	public void setType(List<ProjectType> type) {
		this.type = type;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
    
	
}