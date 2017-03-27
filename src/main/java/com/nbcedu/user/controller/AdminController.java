package com.nbcedu.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.MapUtils;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.bas.util.PageUtils;
import com.nbcedu.user.model.AdminUser;
import com.nbcedu.user.service.IAdminUserService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月9日 上午10:04:09</p>
 * <p>类全名：com.nbcedu.user.controller.UserController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/admin")
public class AdminController {
	
	@Autowired
	private IAdminUserService adminService;
	
	/**
	 * 系统管理员列表
	 * 方法名称:list
	 * 作者:lqc
	 * 创建日期:2016年12月14日
	 * 方法描述:  
	 * @param model
	 * @param page
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET})
	public String list(Model model,@ModelAttribute("page")PageBounds page, HttpServletRequest request){
		PageList<AdminUser> pageList = adminService.selectByCondition(PageUtils.convertPage(page, 5));
		model.addAttribute("data", pageList);
		model.addAttribute("page", pageList.getPaginator());
		return "cms/admin/list";
	}

	/**
	 * 添加管理员
	 * 方法名称:save
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param model
	 * @param user
	 * @return Message
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Message save(@ModelAttribute("admin") AdminUser user)throws Exception{
		return adminService.insertSelective(user)>0?
				MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
	}
	
	/**
	 * 效验是否唯一
	 * 方法名称:check
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception Message
	 */
	@RequestMapping(value="/check/{key}/{value}",method={RequestMethod.GET})
	@ResponseBody
	public Message check(@PathVariable String key,@PathVariable String value)throws Exception{
		return adminService.checkUnique(MapUtils.build_map(key, value))>0?
				MessageUtils.build_error("帐号已被使用！"):
					MessageUtils.build_success("恭喜您！帐号可以使用。");
	}
	
	/**
	 * 删除帐号
	 * 方法名称:del
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param model
	 * @param userid
	 * @return
	 * @throws Exception Message
	 */
	@RequestMapping(value="/del/{userid}",method={RequestMethod.GET,RequestMethod.POST})
	public String del(@RequestHeader(value = "referer") final String referer,Model model,@PathVariable String userid)throws Exception{
		adminService.deleteByPrimaryKey(userid);
		return "redirect:".concat(referer);
	}
	
	
	/**
	 *登录后台系统
	 * 方法名称:login
	 * 作者:lqc
	 * 创建日期:2016年12月26日
	 * 方法描述:  
	 * @param request
	 * @param user_name
	 * @param password
	 * @return
	 * @throws Exception Message
	 */
	@RequestMapping(value="/login",method={RequestMethod.POST})
	@ResponseBody
	public Message login(HttpServletRequest request, @RequestParam String username, 
			@RequestParam String password)throws Exception{
		AdminUser session_admin = adminService.login(username,password);
		if(null!=session_admin){
			request.getSession(true).setAttribute(ContextConstant.SESSION_ADMIN, session_admin);
			return MessageUtils.build_success("登录成功！");
		}else{
			return MessageUtils.build_error("帐号或密码错误！");
		}
	}
	
	/**
	 * 后台登录页面
	 * 方法名称:login
	 * 作者:lqc
	 * 创建日期:2016年12月26日
	 * 方法描述:  
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value="/login",method={RequestMethod.GET})
	public String login(HttpServletRequest request)throws Exception{
		return "cms/login";
	}
	
	/**
	 * 退出后台系统
	 * 方法名称:logout
	 * 作者:lqc
	 * 创建日期:2016年12月26日
	 * 方法描述:  
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value="/logout",method={RequestMethod.GET})
	public String logout(HttpServletRequest request)throws Exception{
		request.getSession(true).removeAttribute(ContextConstant.SESSION_ADMIN);
		request.getSession(true).invalidate();
		return "cms/login";
	}
	
	/**
	 * 后台系统首页
	 * 方法名称:main
	 * 作者:lqc
	 * 创建日期:2016年12月26日
	 * 方法描述:  
	 * @return String
	 */
	@RequestMapping(value="/home",method={RequestMethod.GET})
	public String home(){
		return "cms/home";
	}
	/**
	 * <p>功能：修改管理员真实姓名和密码<p>
	 * <p>创建日期：2016年12月22日 上午11:18:23<p>
	 * <p>作者：曾小明<p>
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/update.ajax",method={RequestMethod.POST})
	@ResponseBody
	public Message update(AdminUser user){
		try {
			int now=adminService.updateAdmin(user);
			return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtils.build_error("操作失败！");
		}
	}
	
	/**
	 * <p>功能：删除管理员<p>
	 * <p>创建日期：2016年12月22日 上午11:19:30<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method={RequestMethod.POST})
	@ResponseBody
	public Message delete(String  id){
		try {
			int now=adminService.deleteByPrimaryKey(id);
			return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtils.build_error("操作失败！");
		}
	}
	
}
