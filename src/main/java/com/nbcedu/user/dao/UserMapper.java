package com.nbcedu.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.user.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * <p>功能：分页查询用户列表<p>
     * <p>创建日期：2016年12月12日 下午3:18:14<p>
     * <p>作者：曾小明<p>
     * @param page
     * @param limit
     * @return
     */
    List<User> selectPage(@Param("page")int page, @Param("limit")int limit);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年12月12日 下午4:36:38<p>
     * <p>作者：曾小明<p>
     * @param pageBounds
     * @return
     */
    PageList<User> selectByCondition(PageBounds pageBounds);
    
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
	User login(String user_name);

	int updateUser(User record);
}