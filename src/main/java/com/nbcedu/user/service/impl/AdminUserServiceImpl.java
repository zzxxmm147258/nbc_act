package com.nbcedu.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.user.dao.AdminUserMapper;
import com.nbcedu.user.model.AdminUser;
import com.nbcedu.user.service.IAdminUserService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:59:59</p>
 * <p>类全名：com.nbcedu.user.service.impl.AdminUserServiceImpl</p>
 * 作者：曾小明
 */
@Service 
public class AdminUserServiceImpl implements IAdminUserService{

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return adminUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(AdminUser record) {
		AdminUser bean=adminUserMapper.login(record.getUserName());
		if(bean==null){
			return adminUserMapper.insertSelective(record);
		}else{
			record.setId(bean.getId());
			record.setDelStatus("0");
			return adminUserMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public AdminUser selectByPrimaryKey(String id) {
		return adminUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AdminUser record) {
		return adminUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<AdminUser> selectPage(int page, int limit) {
		return adminUserMapper.selectPage(page, limit);
	}

	@Override
	public PageList<AdminUser> selectByCondition(PageBounds pageBounds) {
		return adminUserMapper.selectByCondition(pageBounds);
	}

	@Override
	public int checkUnique(Map<String, String> params) {
		// TODO Auto-generated method stub
		return adminUserMapper.checkUnique(params);
	}

	@Override
	public AdminUser login(String user_name, String password) {
		// TODO Auto-generated method stub
		AdminUser user = adminUserMapper.login(user_name);
		if(null != user && ContextConstant.DEL_STATUS_NO.equals(user.getDelStatus()) &&
				StringUtils.isNotBlank(password) && password.equals(user.getPassword())){
			return user;
		}
		return null;
	}

	@Override
	public int updateAdmin(AdminUser record) {
		return adminUserMapper.updateAdmin(record);
	}


}
