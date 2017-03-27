package com.nbcedu.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.PageUtils;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.model.ProjectConfig;
import com.nbcedu.project.model.ProjectOrder;
import com.nbcedu.project.service.IProjectConfigService;
import com.nbcedu.project.service.IProjectOrderService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月20日 上午10:46:01</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectOrderController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/order")
public class ProjectOrderController {

	@Autowired
	private IProjectOrderService  projectOrderService;
	
	@Autowired
	private IProjectConfigService  projectConfigService;
	
	
	@RequestMapping(value="/list")
	public String list(Model model,@RequestParam(required=false) String name,@RequestParam(required=false) String mId,@RequestParam(required=false) String typeId,
			@ModelAttribute("page")PageBounds page){
		PageList<ProjectOrder> pcDatas= projectOrderService.selectByOrder(mId,typeId,name,PageUtils.convertPage(page, 10));
		model.addAttribute("datas",pcDatas );
		model.addAttribute("page", pcDatas.getPaginator());
		model.addAttribute("name",name);
		model.addAttribute("typeId",typeId);
		model.addAttribute("grade", DataMap.load(DataMap.GRADE_TYPE));
		model.addAttribute("type",DataMap.load(DataMap.PRO_TYPE));
		return "cms/order/list";
	}
	
	@RequestMapping(value="/getConfig.ajax",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getConfig(String id){
		Message message=new Message();
		try {
			ProjectOrder order=projectOrderService.selectByPrimaryKey(id);
			List<ProjectConfig> config=projectConfigService.selectByConfigId(order.getConfigId());
			order.setConfig(config);
			message.setSuccess(true);
			message.setDatas(order);
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("查询失败");
		}
		return JsonUtil.toJsonString(message);
	}
	
	
}
