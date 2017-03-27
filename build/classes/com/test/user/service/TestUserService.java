package com.test.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.user.model.User;
import com.nbcedu.user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application.xml")
@TransactionConfiguration(transactionManager="transactionManager") 
public class TestUserService {

	@Autowired
	private IUserService userService;
	
	@Test
	public void test_insertSelective(){
		User user = new User();
		user.setUserName("lqc");
		user.setPassword("123456");
		user.setTrueName("黎先生");
		int record = userService.insertSelective(user);
		System.out.println(record);
	}
	
	@Test
	public void test_deleteByPrimaryKey(){
		int record = userService.deleteByPrimaryKey("6b852dfea4ac41b4bb7c0fb76dbe6203");
		System.out.println(record);
	}
	
	@Test
	public void test_selectByPrimaryKey(){
		User record = userService.selectByPrimaryKey("6b852dfea4ac41b4bb7c0fb76dbe6203");
		System.out.println(GsonUtil.gson.toJson(record));
	}
	
	@Test
	public void test_checkUnique(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_name", "lqc1");
		int count = userService.checkUnique(map);
		System.out.println(count);
	}
	
	@Test
	public void test_selectPage(){
		List<User> list = userService.selectPage(0, 10);
		System.out.println(list.size());
	}
	
	@Test
	public void test_selectByCondition(){
		PageBounds page = new PageBounds(1,10);
		PageList<User> page_list = userService.selectByCondition(page);
		System.out.println(page_list.getPaginator().getTotalCount());
		System.out.println(page_list.getPaginator().getTotalPages());
		System.out.println(GsonUtil.gson.toJson(page_list));
	}
	
	@Test
	public void test_login(){
		User user = userService.login("lqc", "12345611");
		System.out.println(GsonUtil.gson.toJson(user));
	}
	
}
