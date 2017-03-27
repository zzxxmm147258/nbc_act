package com.nbcedu.user.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.user.model.User;
import com.nbcedu.user.service.IUserService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月9日 上午10:04:09</p>
 * <p>类全名：com.nbcedu.user.controller.UserController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/common/user")
public class UserWebController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	FileUploadUtils fileUpload;
	
	
	/**
	 * 用户登录
	 * 方法名称:login
	 * 作者:lqc
	 * 创建日期:2016年12月14日
	 * 方法描述:  
	 * @param request
	 * @param user_name
	 * @param password
	 * @return
	 * @throws Exception Message
	 */
	@RequestMapping(value="/login",method={RequestMethod.POST})
	@ResponseBody
	public Message login(HttpServletRequest request,@RequestParam String user_name,
			@RequestParam String password)throws Exception{
		User session_user = userService.login(user_name,password);
		if(null!=session_user){
			request.getSession(true).setAttribute(ContextConstant.SESSION_USER, session_user);
			return MessageUtils.build_success("登录成功！");
		}else{
			return MessageUtils.build_error("帐号或密码错误！");
		}
	}
	
	/**
	 * 登录界面
	 * 方法名称:login
	 * 作者:lqc
	 * 创建日期:2017年1月9日
	 * 方法描述:  
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value="/login",method={RequestMethod.GET})
	public String login(HttpServletRequest request)throws Exception{
		return "front/login";
	}
	
	/**
	 * 退出
	 * 方法名称:logout
	 * 作者:lqc
	 * 创建日期:2017年1月17日
	 * 方法描述:  
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value="/logout",method={RequestMethod.GET})
	public String logout(HttpServletRequest request)throws Exception{
		request.getSession(true).removeAttribute(ContextConstant.SESSION_USER);
		request.getSession(true).invalidate();
		return "front/login";
	}
	
}
