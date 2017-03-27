package com.nbcedu.cases.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.cases.model.ProjectCase;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:37:48</p>
 * <p>类全名：com.nbcedu.cases.IProjectCaseService</p>
 * 作者：曾小明
 */
public interface IProjectCaseService {

	int deleteByPrimaryKey(String id);
	
	int delete(String ids);

    int insert(ProjectCase record);

    int insertSelective(ProjectCase record);

    ProjectCase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectCase record);

    int updateByPrimaryKeyWithBLOBs(ProjectCase record);

    int updateByPrimaryKey(ProjectCase record);
    
    /**
     * <p>功能：查询案例列表<p>
     * <p>创建日期：2016年12月14日 上午10:29:11<p>
     * <p>作者：曾小明<p>
     * @param typeId
     * @param mId
     * @param pageBounds
     * @return
     */
    PageList<ProjectCase> selectByCondition(@Param("typeId")String typeId,@Param("mId")String mId,PageBounds pageBounds);
    
    /**
     * <p>功能：根据项目ID分页查询项目案例数<p>
     * <p>创建日期：2016年12月13日 上午10:29:05<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param page
     * @param limit
     * @return
     */
    List<ProjectCase> selectPage(@Param("mId")String mId,@Param("page")int page, @Param("limit")int limit);

    
    /**
     * <p>功能：批量删除<p>
     * <p>创建日期：2016年12月19日 上午8:56:05<p>
     * <p>作者：曾小明<p>
     * @param ids
     * @return
     */
    int deletes(String[] ids);
    
    /**
     * <p>功能：批量修改案列为删除状态<p>
     * <p>创建日期：2016年12月26日 下午4:49:11<p>
     * <p>作者：曾小明<p>
     * @param ids
     * @return
     */
    int remove(@Param("ids") List<String> ids);

   /**
    * <p>功能：按标题搜索案例<p>
    * <p>创建日期：2016年12月30日 下午4:59:00<p>
    * <p>作者：曾小明<p>
    * @param name
    * @param i
    * @param limit
    * @return
    */
	List<ProjectCase> selectByName(@Param("name")String name,@Param("mId")String mId, @Param("page")int page, @Param("limit")int limit);
	
	int saveOrUpdate(ProjectCase project, MultipartFile img,boolean action)throws Exception;

	/**
     * <p>功能：根据项目ID查询案例<p>
     * <p>创建日期：2017年1月4日 下午3:58:13<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param page
     * @param limit
     * @return
     */
    List<ProjectCase> selectBymId(@Param("mId")String mId,@Param("page")int page, @Param("limit")int limit);
 
}
