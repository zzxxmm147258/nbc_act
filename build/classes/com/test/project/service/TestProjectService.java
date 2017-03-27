package com.test.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.nbcedu.project.model.Project;
import com.nbcedu.project.service.IProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application.xml")
@TransactionConfiguration(transactionManager="transactionManager") 
public class TestProjectService {

	@Autowired
	private IProjectService projectService;
	
	@Test
	public void test_insertSelective(){
		Project p = new Project();
		p.setTypeId("");
		int record = projectService.insertSelective(p);
		System.out.println(record);
	}
	
	@Test
	public void test_deleteByPrimaryKey(){
		int record = projectService.deleteByPrimaryKey("82f9c7d0a25c429fae578d7ee5ad63d6");
		System.out.println(record);
	}
	
	
}
