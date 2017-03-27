package com.nbcedu.project.controller.common;

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

import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.data.DataMap;
import com.nbcedu.project.model.ProjectOrder;
import com.nbcedu.project.service.IProjectOrderService;
import com.nbcedu.user.model.User;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月20日 上午10:46:01</p>
 * <p>类全名：com.nbcedu.project.controller.ProjectOrderController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/common/order")
public class ProjectOrderWebController {

	@Autowired
	private IProjectOrderService  projectOrderService;
	
	
	/**
	 * <p>功能：查询我的配置单<p>
	 * <p>创建日期：2016年12月30日 下午4:31:59<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,String typeId,HttpServletRequest request){
		int[] p = StrUtil.getPageLimit("1", "3", 100);
		User user =(User) request.getSession(true).getAttribute(ContextConstant.SESSION_USER);
		if(user!=null)
		model.addAttribute("list",projectOrderService.selectByUserId(user.getId(), typeId,p[0], p[1]));
		model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
		model.addAttribute("orderType", DataMap.load(DataMap.PRO_TYPE));//体系
		model.addAttribute("orderTypeId", typeId);
		return "front/order/list";
	}
	/**
	 * <p>功能：点击加载<p>
	 * <p>创建日期：2017年1月5日 下午3:12:00<p>
	 * <p>作者：曾小明<p>
	 * @param typeId
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/page.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByAd(String typeId,String page, String limit,HttpServletRequest request){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		User user =(User) request.getSession(true).getAttribute(ContextConstant.SESSION_USER);
		return JsonUtil.toJsonString(projectOrderService.selectByUserId(user!=null?user.getId():"", typeId,p[0], p[1]));
	}
	
	/**
	 * <p>功能：用户删除配置单<p>
	 * <p>创建日期：2016年12月30日 下午4:39:03<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method={RequestMethod.POST})
	@ResponseBody
	public Message delete(String  id){
		try {
			int now=projectOrderService.updateDelStatus(id);
			return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtils.build_error("操作失败！");
		}
	}
	
	/**
	 * <p>功能：编辑提交订单<p>
	 * <p>创建日期：2017年1月12日 上午9:00:14<p>
	 * <p>作者：曾小明<p>
	 * @param order
	 * @param response
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String editor(@ModelAttribute("order") ProjectOrder order,HttpServletResponse response,@RequestParam boolean action) throws Exception{
		ProjectOrder bean=projectOrderService.selectByPrimaryKey(order.getId());
		if(bean==null){
			projectOrderService.insertSelective(order);
		}else{
			projectOrderService.updateByPrimaryKeySelective(order);
		}
		return "redirect:/common/order/list";
	}
	
	
	@RequestMapping(value="/save.ajax",method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response){
		User user =(User) request.getSession(true).getAttribute(ContextConstant.SESSION_USER);
	    String	datas=request.getParameter("datas");   
	    ProjectOrder order=GsonUtil.gsonToBean(datas, ProjectOrder.class);
	    Message message=new Message();
	    try {
	    	order.setUserId(user!=null?user.getId():"");
	    	message=projectOrderService.save(order)>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
	    } catch (Exception e) {
		   e.printStackTrace();
		   message.setSuccess(true);
		   message.setMessage("后台错误");
	   }
	   response.setHeader("Access-Control-Allow-Origin", "*");
	   return JsonUtil.toJsonString(message);}
}
