package com.nbcedu.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.project.model.ProjectConfig;
import com.nbcedu.project.model.ProjectConfigVo;
import com.nbcedu.project.model.ProjectNumConfig;
import com.nbcedu.project.service.IProjectConfigService;
import com.nbcedu.project.service.IProjectNumConfigService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月13日 下午3:43:12</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectConfigController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/config")
public class ProjectConfigController {

	@Autowired
	private IProjectConfigService  projectConfigService;
	
	@Autowired 
	private IProjectNumConfigService projectNumConfigService;
	
	List<ProjectConfig> config;
	public List<ProjectConfig> getConfig() {
		return config;
	}
	public void setConfig(List<ProjectConfig> config) {
		this.config = config;
	}

	/**
	 * <p>功能：配置项添加修改<p>
	 * <p>创建日期：2016年12月13日 下午4:43:11<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param mId
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,String mId){
		/*ProjectNumConfig num = projectNumConfigService.selectByPrimaryKey(mId);
		List<ProjectConfig> config=projectConfigService.selectBymId(mId);
		if(num!=null && config.size()>0){//为更新操作
			model.addAttribute("num",num);//人数配置
			model.addAttribute("config",config);//
		}else{//新增操作
			model.addAttribute("mId",mId);
			List<Dictinfos> grade=dictinfosService.selectByDictid(10010);
			model.addAttribute("grade", grade);//年级
		}
		model.addAttribute("method", "post");*/
		model.addAttribute("mId",mId);
		return "cms/config/add";
	}
	
	/**
	 * <p>功能：根据项目ID查询配置信息<p>
	 * <p>创建日期：2016年12月16日 上午9:53:58<p>
	 * <p>作者：曾小明<p>
	 * @param mId
	 * @return
	 */
	@RequestMapping(value="/selectBymId.ajax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectBymId(String mId,HttpServletResponse response){
		Message message=new Message();
		try {
			Map<String ,Object> map=new HashMap<String ,Object>();
			ProjectNumConfig bean= projectNumConfigService.selectBymId(mId);//活动人数，陪同人数
			List<ProjectConfig> config=projectConfigService.selectBymId(mId);//配置单
			if(bean!=null && config.size()>0){
				map.put("bean", bean);
				map.put("config", config);
				message.setSuccess(true);
				message.setMessage("bean活动人数，陪同人数,config是配置单。");
				message.setDatas(map);
			}else{
				message.setSuccess(false);
				message.setMessage("获取配置信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("服务端异常！");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		return JsonUtil.toJsonString(message);
	}
	/**
	 * <p>功能：配置单信息保存<p>
	 * <p>创建日期：2016年12月16日 下午2:21:47<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param config
	 * @return
	 */
	@RequestMapping(value="/save.ajax",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response){
	    String	s=request.getParameter("datas");
	    ProjectConfigVo vo=GsonUtil.gsonToBean(s, ProjectConfigVo.class);
		Message message=new Message();
		try {
			message=projectConfigService.save(vo.getBean(),vo.getConfig())>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(true);
			message.setMessage("后台错误");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		return JsonUtil.toJsonString(message);
	}
	
	/**
	 * 添加配置信息
	 * 方法名称:save
	 * 作者:lqc
	 * 创建日期:2016年12月21日
	 * 方法描述:  
	 * @param config
	 * @return Message
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Message save(@ModelAttribute("config")ProjectNumConfig config,@RequestParam boolean action){
		return projectNumConfigService.saveOrUpdate(config,action)>0?
				MessageUtils.build_success("操作成功！"):
					MessageUtils.build_error("操作失败！");
	}
	
}
