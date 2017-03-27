package com.nbcedu.project.dao;

import java.util.List;

import com.nbcedu.project.model.ProjectType;

public interface ProjectTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectType record);

    int insertSelective(ProjectType record);

    ProjectType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectType record);

    int updateByPrimaryKey(ProjectType record);
    
    /**
     * <p>功能：查询所有项目父类型<p>
     * <p>创建日期：2016年12月12日 下午3:21:07<p>
     * <p>作者：曾小明<p>
     * @return
     */
    List<ProjectType> selectAll();
    
    /**
     * <p>功能：根据父项目类型查询子项目类型<p>
     * <p>创建日期：2016年12月12日 下午3:22:50<p>
     * <p>作者：曾小明<p>
     * @param pId
     * @return
     */
    List<ProjectType> selectBypId(String pId);
}