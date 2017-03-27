package com.nbcedu.listener;

import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.nbcedu.bas.util.BannerImage;
import com.nbcedu.bas.util.RandomUtils;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.service.IProjectService;

public class InitDataLister implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext acx= ContextLoader.getCurrentWebApplicationContext();
		IProjectService projectService=(IProjectService) acx.getBean("projectServiceImpl");
		Set<Integer> set=projectService.selectNumber();
		RandomUtils.init(set);
		final String[] CACHE_TYPE={DataMap.PRO_TYPE,DataMap.GRADE_TYPE,
				DataMap.PRO_TYPE_CLASS,DataMap.PROJECT_HOME};
		DataMap.init(CACHE_TYPE);
		arg0.getServletContext().setAttribute("banner_image", BannerImage.get_banner_image());
	}

}
