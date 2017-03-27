package com.nbcedu.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.project.model.ProjectOrder;

public interface ProjectOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectOrder record);

    int insertSelective(ProjectOrder record);

    ProjectOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectOrder record);

    int updateByPrimaryKey(ProjectOrder record);
    
    /**
     * <p>功能：根据项目ID查询项目的订单<p>
     * <p>创建日期：2016年12月13日 下午2:19:25<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param page
     * @param limit
     * @return
     */
    List<ProjectOrder> selectPage(@Param("mId")String mId,@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：分页查询项目的订单<p>
     * <p>创建日期：2016年12月20日 上午8:54:21<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param pageBounds
     * @return
     */
    PageList<ProjectOrder> selectByCondition(@Param("mId")String mId,PageBounds pageBounds);

    /**
     * <p>功能：查看配置单<p>
     * <p>创建日期：2016年12月20日 上午10:50:32<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param typeId
     * @param name
     * @return
     */
    PageList<ProjectOrder> selectByOrder(@Param("mId")String mId, @Param("typeId")String typeId, @Param("name")String name,PageBounds page);

    /**
     * <p>功能：查询我的配置单<p>
     * <p>创建日期：2016年12月30日 下午4:29:50<p>
     * <p>作者：曾小明<p>
     * @param userId
     * @param typeId
     * @return
     */
    List<ProjectOrder> selectByUserId(@Param("userId")String userId,@Param("typeId")String typeId,@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：修改订单未删除状态<p>
     * <p>创建日期：2016年12月30日 下午4:38:00<p>
     * <p>作者：曾小明<p>
     * @param id
     * @return
     */
    int updateDelStatus(String id);
}