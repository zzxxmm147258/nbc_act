package com.nbcedu.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.model.ProjectType;
import com.nbcedu.project.service.IProjectTypeService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月15日 上午9:20:24</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectTypeController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/type")
public class ProjectTypeController {
	
	@Autowired
	private IProjectTypeService projectTypeService;
	
	
	@RequestMapping(value="/list")
	public String list(Model model, HttpServletRequest request){
		List<ProjectType> list=projectTypeService.selectAll();
		model.addAttribute("list", list);//查询条件
		return "cms/type/list";
	}

	/**
	 * <p>功能：根据父表查询字表<p>
	 * <p>创建日期：2016年12月15日 上午9:31:35<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getpId",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getChild(String id,Model model){
		List<ProjectType> list = projectTypeService.selectBypId(id);
		model.addAttribute("bcodeg", list);
		String json = JsonUtil.toJsonString(list);
		return json;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(String id,String pId,Model model){
		ProjectType property=projectTypeService.selectByPrimaryKey(id);
		if(property!=null){//为更新操作
			model.addAttribute("bean",property);
		}else{//新增操作
			ProjectType bean=new ProjectType();
			model.addAttribute("bean",bean);
		}
		
		model.addAttribute("pId", pId);
		return "cms/type/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid ProjectType bean,Model model){
		ProjectType property = projectTypeService.selectByPrimaryKey(bean.getId());
		if(property==null){//如果为空则新增 
			projectTypeService.insertSelective(bean);
		}else{//不为空则修改
			projectTypeService.updateByPrimaryKeySelective(bean);
		}
		DataMap.clearALL();//去缓存
		model.addAttribute("updateSuccess", true);
		return "redirect:/main/type/list";
	}
	
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String id){
		int now=0;
		try {
			now = projectTypeService.delete(id);
			DataMap.clearALL();//去缓存
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.toJsonString(now);
	}
	
	/**
	 * <p>功能：查询产品体系及分类<p>
	 * <p>创建日期：2016年12月15日 下午4:31:28<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
	@RequestMapping(value="/selectBypId.ajax",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectBypId(HttpServletResponse response ){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("projectType",  DataMap.load(DataMap.PRO_TYPE_CLASS));
		map.put("grade", DataMap.load(DataMap.GRADE_TYPE));
		return GsonUtil.gsonString(map);
	}
	
	
	/**
	 * <p>功能：修产品体系标题<p>
	 * <p>创建日期：2016年12月21日 上午10:54:14<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param title
	 * @return
	 */
	@RequestMapping(value="/updateName.ajax",method=RequestMethod.POST)
	@ResponseBody
	public Message updateName(String id,String name){
		int now=projectTypeService.updateName(id, name);
		if(now>0){
			DataMap.clearALL();//去缓存
		}
		return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
	}
}
