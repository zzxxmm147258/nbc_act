package com.nbcedu.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.user.dao.UserMapper;
import com.nbcedu.user.model.User;
import com.nbcedu.user.service.IUserService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月9日 下午2:09:24</p>
 * <p>类全名：com.nbcedu.user.service.impl.UserServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(User record) {
		User bean=userMapper.login(record.getUserName());
		if(bean==null){
			return userMapper.insertSelective(record);
		}else{
			record.setId(bean.getId());
			record.setDelStatus("0");
			return userMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public User selectByPrimaryKey(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<User> selectPage(int page, int limit) {
		return userMapper.selectPage(page, limit);
	}

	@Override
	public PageList<User> selectByCondition(PageBounds pageBounds) {
		return userMapper.selectByCondition(pageBounds);
	}

	@Override
	public int checkUnique(Map<String, String> params) {
		// TODO Auto-generated method stub
		return userMapper.checkUnique(params);
	}

	@Override
	public User login(String user_name, String password) {
		// TODO Auto-generated method stub
		User user = userMapper.login(user_name);
		if(null != user && ContextConstant.DEL_STATUS_NO.equals(user.getDelStatus())
				&& StringUtils.isNotBlank(password) && password.equals(user.getPassword())){
			return user;
		}
		return null;
	}

	@Override
	public int updateUser(User record) {
		return userMapper.updateUser(record);
	}
	
}
