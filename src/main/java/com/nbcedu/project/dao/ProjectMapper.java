package com.nbcedu.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.project.model.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    /**
     * <p>功能：分页查询项目<p>
     * <p>创建日期：2016年12月12日 下午4:13:22<p>
     * <p>作者：曾小明<p>
     * @param page
     * @param limit
     * @return
     */
    List<Project> selectPage(@Param("typeId")String typeId,@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：根据类型分页查询<p>
     * <p>创建日期：2016年12月13日 上午9:49:07<p>
     * <p>作者：曾小明<p>
     * @param typeId
     * @param pageBounds
     * @return
     */
    PageList<Project> selectByCondition(@Param("typeId")String typeId ,PageBounds pageBounds);
    
    /**
     * <p>功能：查询项目订单数量<p>
     * <p>创建日期：2016年12月13日 上午10:13:55<p>
     * <p>作者：曾小明<p>
     * @param id
     * @return
     */
    int selectByOrderNum(String id);
    
    /**
     * <p>功能：查询项目案例数<p>
     * <p>创建日期：2016年12月13日 上午10:20:46<p>
     * <p>作者：曾小明<p>
     * @param id
     * @return
     */
    int selectByCaseNum(String id);

    /**
     * 项目推荐
     * 方法名称:recommend
     * 作者:lqc
     * 创建日期:2016年12月16日
     * 方法描述:  
     * @param project
     * @return int
     */
	int recommend(Project project);

	/**
	 * 批量删除
	 * 方法名称:remove
	 * 作者:lqc
	 * 创建日期:2016年12月16日
	 * 方法描述:  
	 * @param ids
	 * @return int
	 */
	int remove(@Param("ids") List<String> ids);

	/**
	 * 项目列表
	 * 方法名称:list
	 * 作者:lqc
	 * 创建日期:2016年12月19日
	 * 方法描述:  
	 * @param typeId
	 * @param page
	 * @return PageList<Project>
	 */
	PageList<Project> list(@Param("typeId")String typeId,@Param("page")PageBounds page);
    
	/**
	 * ID查询
	 * 方法名称:selectNumber
	 * 作者:lqc
	 * 创建日期:2016年12月19日
	 * 方法描述:  
	 * @return List<Integer>
	 */
	List<Integer> selectNumber();
	
	/**
	 * <p>功能：前端二级页面分页加载查询<p>
	 * <p>创建日期：2016年12月20日 下午2:51:44<p>
	 * <p>作者：曾小明<p>
	 * @param typeId
	 * @param classId
	 * @param gradeId
	 * @param page
	 * @param limit
	 * @return
	 */
    List<Project> selectSecondLevel(@Param("typeId")String typeId,@Param("classId")String classId,@Param("gradeId")String gradeId,@Param("name")String name,@Param("page")int page, @Param("limit")int limit);

}