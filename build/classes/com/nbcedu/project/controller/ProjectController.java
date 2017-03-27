package com.nbcedu.project.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.bas.util.PageUtils;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.model.Project;
import com.nbcedu.project.service.IProjectService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月13日 下午3:22:55</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/project")
public class ProjectController {
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private FileUploadUtils fileUpload;

	/**
	 * 项目列表
	 * 方法名称:list
	 * 作者:lqc
	 * 创建日期:2016年12月16日
	 * 方法描述:  
	 * @param model
	 * @param typeId
	 * @param page
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model,String typeId,@ModelAttribute("page")PageBounds page){
		PageList<Project> list=projectService.list(typeId,PageUtils.convertPage(page, 5));
		model.addAttribute("page", list.getPaginator());
		model.addAttribute("list", list);
		model.addAttribute("typeId", typeId);
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//查询条件
		return "cms/project/list";
	}
	
	/***
	 * <p>功能：跳转项目编辑(新增，修改)页<p>
	 * 方法名称:input
	 * 作者:lqc
	 * 创建日期:2016年12月19日
	 * 方法描述:  
	 * @param model
	 * @param id
	 * @return String
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(Model model,@RequestParam(required=false) String id){
		if(StringUtils.isNotBlank(id)){//为更新操作
			model.addAttribute("bean",projectService.selectByPrimaryKey(id));
		}
		model.addAttribute("grade", DataMap.load(DataMap.GRADE_TYPE));//年级
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//所属产品体系大类
		return "cms/project/input";
	}
	
	/**
	 * <p>功能：项目修改或添加<p>
	 * <p>创建日期：2016年12月13日 下午3:37:16<p>
	 * <p>作者：曾小明<p>
	 * @param project
	 * @param img
	 * @param action true:更新，false：新增
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/editor",method=RequestMethod.POST)
	public String editor(@ModelAttribute("project") Project project,HttpServletResponse response,
			@RequestParam(value = "img",required=false) MultipartFile img,@RequestParam boolean action) throws Exception{
		projectService.saveOrUpdate(project,img,action);
		DataMap.clear(DataMap.PROJECT_HOME);
		return "redirect:/main/project/list";
	}
	
	/**
	 * <p>功能：根据ID获取项目详情<p>
	 * <p>创建日期：2016年12月15日 下午4:12:29<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/selectById.ajax",method={RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectById(String id){
		Message message=new Message();
		Project bean = projectService.selectByPrimaryKey(id);
		if(bean!=null){
			message.setSuccess(true);
			message.setMessage("项目详情");
			message.setDatas(bean);
		}else{
			message.setSuccess(false);
			message.setMessage("获取详情失败");
		}
		
		return JsonUtil.toJsonString(message);
	}
	
	/**
	 * <p>功能：项目提交<p>
	 * <p>创建日期：2016年12月15日 下午4:10:33<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/save.ajax",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(Project bean,HttpServletResponse response ){
		Message message=new Message();
		int now=0;
		try {
		    if(!StrUtil.isnull(bean.getImgUrl())){
			   Map<String, String> map = fileUpload.upload_byte(bean.getImgUrl());
			   if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
			     bean.setImgUrl(map.get("file_path"));
			   }else{
				   bean.setImgUrl("");
			   }
			}
			/*if(!bean.getFile().isEmpty()){
				Map<String, String> map = fileUpload.upload_image(bean.getFile());
				if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
					bean.setImgUrl(map.get("file_path"));
				}
			}*/
			if(!StrUtil.isnull(bean.getDes())){
				bean.setDes(URLDecoder.decode(bean.getDes(), "UTF-8"));
			}
			if(!StrUtil.isnull(bean.getDetails())){
				bean.setDetails(URLDecoder.decode(bean.getDetails(), "UTF-8"));
			}
			Project property = projectService.selectByPrimaryKey(bean.getId());
			if(property==null){//如果为空则新增 
				now=projectService.insertSelective(bean);
			}else{//不为空则修改
				now=projectService.updateByPrimaryKeySelective(bean);
			}
			message=now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(true);
			message.setMessage("后台错误");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		return JsonUtil.toJsonString(message);
	}
	
	/**
	 * 推荐项目
	 * 方法名称:recommend
	 * 作者:lqc
	 * 创建日期:2016年12月16日
	 * 方法描述:  
	 * @param project
	 * @return Message
	 */
	@RequestMapping(value="/recommend",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Message recommend(@ModelAttribute("project") Project project){
		DataMap.clear(DataMap.PROJECT_HOME);
		return projectService.recommend(project)>0?
				MessageUtils.build_success("操作成功！"):
					MessageUtils.build_error("操作失败！");
	}
	
	/**
	 * 批量删除
	 * 方法名称:del
	 * 作者:lqc
	 * 创建日期:2016年12月30日
	 * 方法描述:  
	 * @param idList
	 * @param referer
	 * @return String
	 */
	@RequestMapping(value="/remove",method={RequestMethod.POST,RequestMethod.GET})
	public String remove(@RequestParam List<String> idList,@RequestHeader(value = "referer") final String referer){
		projectService.remove(idList);
		DataMap.clear(DataMap.PROJECT_HOME);
		return "redirect:".concat(referer);
	}
	
}
