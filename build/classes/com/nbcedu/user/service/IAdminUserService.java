package com.nbcedu.user.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.user.model.AdminUser;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:59:49</p>
 * <p>类全名：com.nbcedu.user.service.IAdminUserService</p>
 * 作者：曾小明
 */
public interface IAdminUserService {

	int deleteByPrimaryKey(String userid);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(AdminUser record);

    /**
     * <p>功能：分页查询用户列表<p>
     * <p>创建日期：2016年12月12日 下午3:18:14<p>
     * <p>作者：曾小明<p>
     * @param page
     * @param limit
     * @return
     */
    List<AdminUser> selectPage(int page, int limit);
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年12月12日 下午4:37:43<p>
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
	int checkUnique(Map<String, String> params);

	/**
	 * 用户登录
	 * 方法名称:login
	 * 作者:lqc
	 * 创建日期:2016年12月14日
	 * 方法描述:  
	 * @param user_name
	 * @param password
	 * @return User
	 */
	AdminUser login(String user_name, String password);
	/**
	 * <p>功能：修改真实姓名和密码<p>
	 * <p>创建日期：2016年12月23日 下午3:14:08<p>
	 * <p>作者：曾小明<p>
	 * @param record
	 * @return
	 */
	int updateAdmin(AdminUser record);
}
