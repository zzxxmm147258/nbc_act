package com.nbcedu.project.dao;

import java.util.List;

import com.nbcedu.project.model.ProjectConfig;

public interface ProjectConfigMapper {
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
    
    List<ProjectConfig> selectByIds(String[] item);
}