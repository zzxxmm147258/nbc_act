package com.nbcedu.cases.controller.common;

import java.util.List;

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
import com.nbcedu.cases.model.ProjectCase;
import com.nbcedu.cases.model.ProjectCaseImg;
import com.nbcedu.cases.service.IProjectCaseImgService;
import com.nbcedu.cases.service.IProjectCaseService;
import com.nbcedu.data.DataMap;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月14日 上午10:25:00</p>
 * <p>类全名：com.nbcedu.cases.controller.ProjectCaseController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/common/case")
public class ProjectCaseWebController {
	
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IProjectCaseImgService projectCaseImgService;
	
	
	
	
	
	/**
	 * <p>功能：查询案例详情<p>
	 * <p>创建日期：2016年12月30日 下午4:16:29<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String detail(Model model, String id){
		model.addAttribute("bean",projectCaseService.selectByPrimaryKey(id));//案例详情
		List<ProjectCaseImg> list=projectCaseImgService.selectBymId(id);
		model.addAttribute("count",list.size());//案例图片
		List<List<?>> img=StrUtil.splitList(list, 3);
		model.addAttribute("img",img);//案例图片
		DataMap.menu_loading(model);
		model.addAttribute("caseList", "active");//为当前页
		return "front/case/detail";
	}
	
   /**
    * <p>功能：案例更多图片<p>
    * <p>创建日期：2016年12月30日 下午4:20:15<p>
    * <p>作者：曾小明<p>
    * @param model
    * @param mId
    * @return
    */
	@RequestMapping(value="/imglist")
	public String imglist(Model model, String mId){
		model.addAttribute("img",projectCaseImgService.selectBymId(mId));//案例图片
		model.addAttribute("bean",projectCaseService.selectByPrimaryKey(mId));//案例
		DataMap.menu_loading(model);
		model.addAttribute("caseList", "active");//为当前页
		return "front/case/img_list";
	}
	
	
	/**
	 * <p>功能：精彩案例<p>
	 * <p>创建日期：2016年12月14日 上午10:32:22<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param typeId
	 * @param mId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model, String name,String mId,String page, String limit){
		int[] p = StrUtil.getPageLimit("1", "3", 10);
		model.addAttribute("list",projectCaseService.selectByName(name,mId, p[0], p[1]));
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
		model.addAttribute("mId", mId);
		model.addAttribute("caseList", "active");//为当前页
		return "front/case/list";
	}
	
	@RequestMapping(value="/page.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByAd(String name,String mId,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 10);
		return JsonUtil.toJsonString(projectCaseService.selectByName(name, mId, p[0], p[1]));
	}
	
	/**
	 * ajax case 分页
	 * 方法名称:page
	 * 作者:lqc
	 * 创建日期:2017年1月11日
	 * 方法描述:  
	 * @param pro_id
	 * @return Message
	 */
	@RequestMapping(value="/loading/{pro_id}",method=RequestMethod.GET)
	public String page(@PathVariable(value="pro_id") String pro_id,@RequestParam int start,
			@RequestParam int limit, Model model){
		List<ProjectCase> pageList = projectCaseService.selectPage(pro_id, start, limit);
		model.addAttribute("data", pageList);
		return "front/page/case";
	}
	

}
