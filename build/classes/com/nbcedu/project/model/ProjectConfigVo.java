package com.nbcedu.project.model;

import java.util.List;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月29日 上午10:08:34</p>
 * <p>类全名：com.nbcedu.project.model.ProjectConfigVo</p>
 * 作者：曾小明
 */
public class ProjectConfigVo {
	
	private ProjectNumConfig bean;
	
	private List<ProjectConfig> config;

	public ProjectNumConfig getBean() {
		return bean;
	}

	public void setBean(ProjectNumConfig bean) {
		this.bean = bean;
	}

	public List<ProjectConfig> getConfig() {
		return config;
	}

	public void setConfig(List<ProjectConfig> config) {
		this.config = config;
	}
	

}
