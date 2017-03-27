package com.nbcedu.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.user.model.AdminUser;

public interface AdminUserMapper {
	
	int deleteByPrimaryKey(String id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
    
    /**
     * <p>功能：分页查询用户列表<p>
     * <p>创建日期：2016年12月12日 下午3:18:14<p>
     * <p>作者：曾小明<p>
     * @param page
     * @param limit
     * @return
     */
    List<AdminUser> selectPage(@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年12月12日 下午4:36:38<p>
     * <p>作者：曾小明<p>
     * @param pageBounds
     * @return
     */
    PageList<AdminUser> selectByCondition(PageBounds pageBounds);
    
    /**
     * 检测是否唯一
     * 方法名称:checkUnique
     * 作者:lqc
     * 创建日期:2016年12月13日
     * 方法描述:  
     * @param params
     * @return int
     */
    int checkUnique(@Param("params")Map<String, String> params);

    /**
     * 用户登录
     * 方法名称:login
     * 作者:lqc
     * 创建日期:2016年12月14日
     * 方法描述:  
     * @param user_name
     * @return User
     */
    AdminUser login(String user_name);

	int updateAdmin(AdminUser record);
}