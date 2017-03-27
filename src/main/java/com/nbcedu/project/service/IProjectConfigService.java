package com.nbcedu.project.service;

import java.util.List;

import com.nbcedu.project.model.ProjectConfig;
import com.nbcedu.project.model.ProjectNumConfig;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午1:55:12</p>
 * <p>类全名：com.nbcedu.project.service.IProjectConfigService</p>
 * 作者：曾小明
 */
public interface IProjectConfigService {

	    int deleteByPrimaryKey(String id);

	    int insert(ProjectConfig record);

	    int insertSelective(ProjectConfig record);

	    ProjectConfig selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(ProjectConfig record);

	    int updateByPrimaryKeyWithBLOBs(ProjectConfig record);

	    int updateByPrimaryKey(ProjectConfig record);
	    
	    /**
	     * <p>功能：根据项目ID查询配置单<p>
	     * <p>创建日期：2016年12月13日 下午2:29:12<p>
	     * <p>作者：曾小明<p>
	     * @param mId
	     * @return
	     */
	    List<ProjectConfig> selectBymId(String mId);
	    
	    int save(ProjectNumConfig bean,List<ProjectConfig> config);
	    
	    /**
	     * <p>功能：查询订单的配置项<p>
	     * <p>创建日期：2016年12月20日 上午9:04:00<p>
	     * <p>作者：曾小明<p>
	     * @param configId
	     * @return
	     */
	    List<ProjectConfig> selectByConfigId(String configId);
}
