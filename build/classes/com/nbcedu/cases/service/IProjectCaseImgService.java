package com.nbcedu.cases.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.cases.model.ProjectCaseImg;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:47:02</p>
 * <p>类全名：com.nbcedu.cases.service.IProjectCaseImgService</p>
 * 作者：曾小明
 */
public interface IProjectCaseImgService {

	int deleteByPrimaryKey(String id);

    int insert(ProjectCaseImg record);

    int insertSelective(ProjectCaseImg record);

    ProjectCaseImg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectCaseImg record);

    int updateByPrimaryKey(ProjectCaseImg record);
    
    /**
     * <p>功能：根据案列ID查询案例图片<p>
     * <p>创建日期：2016年12月13日 上午11:29:37<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param page
     * @param limit
     * @return
     */
    List<ProjectCaseImg> selectPage(@Param("mId")String mId,@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：分页查询图片管理<p>
     * <p>创建日期：2016年12月15日 下午1:41:19<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param pageBounds
     * @return
     */
    PageList<ProjectCaseImg> selectByCondition(@Param("mId")String mId,PageBounds pageBounds);
    
    /**
     * <p>功能：批量删除<p>
     * <p>创建日期：2016年12月19日 上午8:56:05<p>
     * <p>作者：曾小明<p>
     * @param ids
     * @return
     */
    int deletes(String ids);
    
    
    int updateTitle(String id ,String title);
    
    /**
     * <p>功能：查询案例所有图片<p>
     * <p>创建日期：2016年12月30日 下午4:21:33<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @return
     */
    List<ProjectCaseImg> selectBymId(@Param("mId")String mId);
}
