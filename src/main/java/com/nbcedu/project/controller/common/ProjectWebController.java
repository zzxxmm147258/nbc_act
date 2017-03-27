package com.nbcedu.project.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.cases.service.IProjectCaseService;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.model.Project;
import com.nbcedu.project.service.IProjectConfigService;
import com.nbcedu.project.service.IProjectNumConfigService;
import com.nbcedu.project.service.IProjectOrderService;
import com.nbcedu.project.service.IProjectService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月13日 下午3:22:55</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/common/project")
public class ProjectWebController {
	
	@Autowired
	private IProjectService projectService;
	
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IProjectConfigService projectConfigService;
	
	@Autowired
	private IProjectNumConfigService projectNumConfigService;
	
	@Autowired
	private IProjectOrderService  projectOrderService;
	
    /**
     * <p>功能：前台首页<p>
     * <p>创建日期：2016年12月28日 下午2:44:04<p>
     * <p>作者：曾小明<p>
     * @param model
     * @return
     */
	@RequestMapping(value="/home")
	public String home(Model model){
		model.addAttribute("list", DataMap.load(DataMap.PROJECT_HOME));
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));
		model.addAttribute("home", "active");//为当前页
		return "front/home";
	}
	/**
	 * <p>功能：跳转项目列表二级页<p>
	 * <p>创建日期：2016年12月28日 下午3:22:00<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param typeId
	 * @param classId
	 * @param gradeId
	 * @return
	 */
	@RequestMapping(value="/list/{typeId}")
	public String list(Model model,@PathVariable String typeId,String classId,String gradeId,String name,String page, String limit){
		int[] p = StrUtil.getPageLimit("1", "3", 100);
		model.addAttribute("typeId", typeId);
		model.addAttribute("classId", classId);
		model.addAttribute("gradeId", gradeId);
		DataMap.menu_loading(model, typeId);
		model.addAttribute("list", projectService.selectSecondLevel(typeId, classId, gradeId,name, p[0], p[1]));
		return "front/project/list";
	}
	

	/**
	 * <p>二级页面ajax加载：按标题搜索项目<p>
	 * <p>创建日期：2016年12月30日 下午4:00:04<p>
	 * <p>作者：曾小明<p>
	 * @param typeId
	 * @param classId
	 * @param gradeId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/page.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByAd(String typeId,String classId,String gradeId,String name,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		return JsonUtil.toJsonString(projectService.selectSecondLevel(typeId, classId, gradeId,name, p[0], p[1]));
	}
	
	/**
	 * 项目内容详情
	 * 方法名称:details
	 * 作者:lqc
	 * 创建日期:2017年1月10日
	 * 方法描述:  
	 * @param model
	 * @param id
	 * @param type
	 * @return String
	 */
	@RequestMapping(value="/details/{id}",method=RequestMethod.GET)
	public String details(Model model,@PathVariable String id,
			@RequestParam(value="tab_type",required=false) String tab_type,String orderId){
		Project project = projectService.selectById(id);
		model.addAttribute("bean", project);
		model.addAttribute("tab_type",tab_type);
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
		model.addAttribute("typeId", project.getTypeId());
		model.addAttribute("orderId",orderId);//订单ID
		return "front/project/details";
	}
	
	/**
	 * 内容详情
	 * 方法名称:info
	 * 作者:lqc
	 * 创建日期:2017年1月10日
	 * 方法描述:  
	 * @param model
	 * @param type
	 * @param id
	 * @return String
	 */
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public String info(Model model, @RequestParam(value="tab_type",required=false,defaultValue="content") String tab_type,
			@RequestParam(value="id") String id,String orderId){
		switch (tab_type) {
			case "case":
				return "front/project/info/case";
			case "config":
				model.addAttribute("mId", id);
				model.addAttribute("config", projectConfigService.selectBymId(id));
				model.addAttribute("num", projectNumConfigService.selectBymId(id));
				if(!StrUtil.isnull(orderId))
				model.addAttribute("order",projectOrderService.selectByPrimaryKey(orderId));
				return "front/project/info/config";
			default:
				model.addAttribute("bean", projectService.selectByPrimaryKey(id));
				return "front/project/info/content";
		}
	}
	
	
	/**
	 * <p>功能：查询<p>
	 * <p>创建日期：2017年1月6日 下午2:05:58<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param name
	 * @param search
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/search")
	public String search(Model model,String name,String search, String page, String limit){
		int[] p = StrUtil.getPageLimit("1", "3", 100);
		model.addAttribute("name", name);
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
		if(search.equals("project")){
			model.addAttribute("list", projectService.selectSecondLevel(null, null, null,name, p[0], p[1]));
			return "front/project/search_project";
		}else{
			model.addAttribute("list",projectCaseService.selectByName(name,null, p[0], p[1]));
			return "front/case/search_case";
		}
	}
	
	@RequestMapping(value="/search_page.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByAd(String name,String search,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		if(search.equals("project")){
			return JsonUtil.toJsonString(projectService.selectSecondLevel(null, null, null,name, p[0], p[1]));
		}else{
			return JsonUtil.toJsonString(projectCaseService.selectByName(name,null, p[0], p[1]));
		}
	}
}
