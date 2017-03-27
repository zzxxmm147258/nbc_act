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
import com.nbcedu.user.model.AdminUser;
import com.nbcedu.user.service.IAdminUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application.xml")
@TransactionConfiguration(transactionManager="transactionManager") 
public class TestAdminUserService {

	@Autowired
	private IAdminUserService adminService;
	
	@Test
	public void test_insertSelective(){
		for(int i=1; i<25; i++){
			AdminUser user = new AdminUser();
			user.setUserName("lqc"+i);
			user.setPassword("nbc@123"+i);
			user.setTrueName("黎先生");
			int record = adminService.insertSelective(user);
			System.out.println(record);
		}
	}
	
	@Test
	public void test_deleteByPrimaryKey(){
		int record = adminService.deleteByPrimaryKey("8addeeb489e149d8b9975558b6a96a25");
		System.out.println(record);
	}
	
	@Test
	public void test_selectByPrimaryKey(){
		AdminUser record = adminService.selectByPrimaryKey("8addeeb489e149d8b9975558b6a96a25");
		System.out.println(GsonUtil.gson.toJson(record));
	}
	
	
	@Test
	public void test_checkUnique(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_name", "lqc19");
		int count = adminService.checkUnique(map);
		System.out.println(count);
	}
	
	@Test
	public void test_selectPage(){
		List<AdminUser> list = adminService.selectPage(0, 10);
		System.out.println(GsonUtil.gson.toJson(list));
	}
	
	@Test
	public void test_selectByCondition(){
		PageBounds page = new PageBounds(1,10);
		PageList<AdminUser> page_list = adminService.selectByCondition(page);
		System.out.println(page_list.getPaginator().getTotalCount());
		System.out.println(page_list.getPaginator().getTotalPages());
		System.out.println(GsonUtil.gson.toJson(page_list));
	}
	
	@Test
	public void test_login(){
		AdminUser user = adminService.login("lqc19", "nbc@12319");
		System.out.println(GsonUtil.gson.toJson(user));
	}
	
}
