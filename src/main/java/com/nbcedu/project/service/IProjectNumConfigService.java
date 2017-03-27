package com.nbcedu.project.service;

import com.nbcedu.project.model.ProjectNumConfig;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:00:10</p>
 * <p>类全名：com.nbcedu.project.service.IProjectNumConfigService</p>
 * 作者：曾小明
 */
public interface IProjectNumConfigService {

	int deleteByPrimaryKey(String id);

    int insertSelective(ProjectNumConfig record);

    ProjectNumConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectNumConfig record);

    /**
     * <p>功能：查询项目人数配置<p>
     * <p>创建日期：2016年12月28日 下午3:07:55<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @return
     */
    ProjectNumConfig selectBymId(String mId);

    /**
     * 
     * 方法名称:saveOrUpdate
     * 作者:lqc
     * 创建日期:2016年12月21日
     * 方法描述:  
     * @param config
     * @param action true:更新，false：新增
     * @return int
     */
	int saveOrUpdate(ProjectNumConfig config, boolean action);
}
