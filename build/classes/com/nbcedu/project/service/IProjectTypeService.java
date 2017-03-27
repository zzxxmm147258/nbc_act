package com.nbcedu.project.service;

import java.util.List;

import com.nbcedu.project.model.ProjectType;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:09:24</p>
 * <p>类全名：com.nbcedu.project.service.IProjectTypeService</p>
 * 作者：曾小明
 */
public interface IProjectTypeService {

	int deleteByPrimaryKey(String id);
	
	int delete(String id);

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
    
    /**
     * <p>功能：查询所有的父子连级<p>
     * <p>创建日期：2016年12月15日 下午4:25:51<p>
     * <p>作者：曾小明<p>
     * @return
     */
    List<ProjectType> selectByAll();
    
    /**
     * <p>功能：修改名称<p>
     * <p>创建日期：2016年12月21日 下午2:26:50<p>
     * <p>作者：曾小明<p>
     * @param id
     * @param title
     * @return
     */
    int updateName(String id ,String title);
    /**
     * <p>功能：查询前端首页数据<p>
     * <p>创建日期：2016年12月28日 下午4:24:24<p>
     * <p>作者：曾小明<p>
     * @return
     */
    List<ProjectType> selectByProject();
}
