package com.nbcedu.cases.controller;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.nbcedu.cases.model.ProjectCase;
import com.nbcedu.cases.service.IProjectCaseService;
import com.nbcedu.data.DataMap;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月14日 上午10:25:00</p>
 * <p>类全名：com.nbcedu.cases.controller.ProjectCaseController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/case")
public class ProjectCaseController {
	
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private FileUploadUtils fileUpload;
	
	
	/**
	 * <p>功能：查询案例列表<p>
	 * <p>创建日期：2016年12月14日 上午10:32:22<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param typeId
	 * @param mId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list/{mId}")
	public String list(Model model,@RequestParam(required=false) String typeId,@PathVariable String mId,
			@ModelAttribute("page")PageBounds page){
		PageList<ProjectCase> pcDatas= projectCaseService.selectByCondition(typeId,mId,PageUtils.convertPage(page, 5));
		model.addAttribute("datas", pcDatas);
		model.addAttribute("page", pcDatas.getPaginator());
		model.addAttribute("typeId",typeId);
		model.addAttribute("mId",mId);
		model.addAttribute("type",DataMap.load(DataMap.PRO_TYPE));
		return "cms/case/list";
	}
	
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(Model model,@RequestParam(required=false) String id,String mId){
		model.addAttribute("mId",mId);
		if(StringUtils.isNotBlank(id)){//为更新操作
			ProjectCase bean=projectCaseService.selectByPrimaryKey(id);
			model.addAttribute("bean",bean);
			model.addAttribute("mId",mId!=null?mId:bean.getmId());
		}
		model.addAttribute("grade", DataMap.load(DataMap.GRADE_TYPE));//年级
		return "cms/case/input";
	}
	
	/**
	 * <p>功能：修改新增提交<p>
	 * <p>创建日期：2016年12月12日 上午9:03:22<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid ProjectCase bean,@RequestParam(value = "img") MultipartFile img,Model model){
		String mId="";
		ProjectCase property = projectCaseService.selectByPrimaryKey(bean.getId());
		bean.setDateTime(new Date());
		if(!img.isEmpty()){
			try {
				 Map<String, String> map = fileUpload.upload_image(img);
				bean.setImgUrl(map.get("file_path"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(property==null){//如果为空则新增 
			mId=bean.getmId();
			projectCaseService.insertSelective(bean);
		}else{//不为空则修改
			mId=property.getmId();
			projectCaseService.updateByPrimaryKeySelective(bean);
		}
		model.addAttribute("mId", mId);
		model.addAttribute("updateSuccess", true);
		return "redirect:/main/case/list";
	}
	
	/**
	 * <p>功能：查询项目案例<p>
	 * <p>创建日期：2016年12月23日 上午10:52:07<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/selectById.ajax",method={RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectById(String id){
		Message message=new Message();
		ProjectCase bean = projectCaseService.selectByPrimaryKey(id);
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
	
	
	@RequestMapping(value="/editor",method=RequestMethod.POST)
	public String editor(@ModelAttribute("project") ProjectCase project,HttpServletResponse response,
			@RequestParam(value = "img",required=false) MultipartFile img,@RequestParam boolean action) throws Exception{
		projectCaseService.saveOrUpdate(project,img,action);
		return "redirect:/main/case/list/".concat(project.getmId());
	}
	/**
	 * <p>功能：项目案例提交<p>
	 * <p>创建日期：2016年12月23日 上午10:51:17<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/save.ajax",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(ProjectCase bean,HttpServletResponse response ){
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
			if(!StrUtil.isnull(bean.getDetail())){
				bean.setDetail(URLDecoder.decode(bean.getDetail(), "UTF-8"));
			}
			ProjectCase property = projectCaseService.selectByPrimaryKey(bean.getId());
			if(property==null){//如果为空则新增 
				now=projectCaseService.insertSelective(bean);
			}else{//不为空则修改
				now=projectCaseService.updateByPrimaryKeySelective(bean);
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
	 * <p>功能：批量修改案列为删除状态<p>
	 * <p>创建日期：2016年12月26日 下午4:51:16<p>
	 * <p>作者：曾小明<p>
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/remove",method={RequestMethod.POST,RequestMethod.POST})
	@ResponseBody
	public Message remove(@RequestParam List<String> ids){
		return projectCaseService.remove(ids)>0?
				MessageUtils.build_success("操作成功！"):
					MessageUtils.build_error("操作失败！");
	}
	
	@RequestMapping(value="/del")
	public String del(String ids,String mId,Model model){
		if(!StrUtil.isnull(ids)){
			String[] item=ids.split(",");
			List<String> list = Arrays.asList(item);  
			projectCaseService.remove(list);
		}
		model.addAttribute("mId", mId);
		return "redirect:/main/case/list/".concat(mId);
	}
	

}
