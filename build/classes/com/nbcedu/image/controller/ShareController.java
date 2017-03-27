package com.nbcedu.image.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nbcedu.cases.model.ProjectCase;
import com.nbcedu.cases.service.IProjectCaseService;
/**
 * 分享
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 ShareController.java
 * @date         创建日期 2017年1月16日
 * @description  描述
 */
@Controller
public class ShareController {

	@Autowired
	private IProjectCaseService projectCaseService;
	
	private static final String ERROR = "error";
	
	/**
	 * 案例分享
	 * 方法名称:share
	 * 作者:lqc
	 * 创建日期:2017年1月16日
	 * 方法描述:  
	 * @param response
	 * @param mode
	 * @param id
	 * @return
	 * @throws IOException String
	 */
	@RequestMapping(value="/share/{id}",method={RequestMethod.GET})
	public String share(HttpServletResponse response,
			Model mode,@PathVariable String id) throws IOException{
		if(StringUtils.isNotBlank(id)){
			ProjectCase pro_case= projectCaseService.selectByPrimaryKey(id);
			if(null != pro_case){
				mode.addAttribute("bean", pro_case);
				return "front/case/share";
			}
		}
		return ERROR;
	}
	
}
