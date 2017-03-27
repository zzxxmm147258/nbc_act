package com.nbcedu.user.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.CutStyle;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.MapUtils;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.bas.util.PageUtils;
import com.nbcedu.user.model.User;
import com.nbcedu.user.service.IUserService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月9日 上午10:04:09</p>
 * <p>类全名：com.nbcedu.user.controller.UserController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/user")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	FileUploadUtils fileUpload;
	
	/**
	 * 系统会员列表
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
		PageList<User> pageList = userService.selectByCondition(PageUtils.convertPage(page,5));
		model.addAttribute("data", pageList);
		model.addAttribute("page", pageList.getPaginator());
		return "cms/user/list";
	}

	/**
	 * 注册会员信息
	 * 方法名称:save
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param model
	 * @param user
	 * @return Message
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ResponseBody
	public Message save(@ModelAttribute User user)throws Exception{
		return userService.insertSelective(user)>0?
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
		return userService.checkUnique(MapUtils.build_map(key, value))>0?
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
	@RequestMapping(value="/del/{userid}",method={RequestMethod.GET})
	public String del(@RequestHeader(value = "referer") final String referer,@PathVariable String userid)throws Exception{
		userService.deleteByPrimaryKey(userid);
		return "redirect:".concat(referer);
	}
	
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
	@RequestMapping(value="/login",method={RequestMethod.GET})
	@ResponseBody
	public Message login(HttpServletRequest request,@RequestParam String user_name,@RequestParam String password)throws Exception{
		User session_user = userService.login(user_name,password);
		if(null!=session_user){
			request.getSession(true).setAttribute(ContextConstant.SESSION_USER, session_user);
			return MessageUtils.build_success();
		}else{
			return MessageUtils.build_error();
		}
	}
	
	@RequestMapping(value="/test",method={RequestMethod.GET})
	public String test(){
		return "test";
	}
	
	@RequestMapping(value="/upload",method={RequestMethod.POST})
	@ResponseBody
	public String upload(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("imageFile")MultipartFile file ) throws Exception{
		// 文件类型限制
        Map<String, String> map = fileUpload.upload_image(file);
        if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
        	return fileUpload.get_image_uri(request,map.get("file_path"));
        }else{
        	return map.get("msg");
        }
	}
	
	@RequestMapping(value="/image/{file_path}")
	@ResponseBody
	public void image(HttpServletResponse response,@PathVariable String file_path) throws IOException{
        try (OutputStream os = response.getOutputStream()){  
            os.write(FileUtils.readFileToByteArray(fileUpload.get_image_file(file_path, CutStyle.DEFAULT)));  
        } catch (Exception e) {
			// TODO: handle exception
        	System.out.println("文件不存在,可以指定默认丢失图片。");
		}
	}
	
	@RequestMapping(value="/image/{file_path}/{style}")
	@ResponseBody
	public void image(HttpServletResponse response,@PathVariable String file_path,@PathVariable String style) throws IOException{
		try (OutputStream os = response.getOutputStream()){  
            os.write(FileUtils.readFileToByteArray(fileUpload.get_image_file(file_path, CutStyle.getStyleType(style))));  
        } catch (Exception e) {
			// TODO: handle exception
        	System.out.println("文件不存在,可以指定默认丢失图片。");
		}
	}
	
	/**
	 * <p>功能：修改会员真实姓名和密码<p>
	 * <p>创建日期：2016年12月22日 上午11:13:59<p>
	 * <p>作者：曾小明<p>
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/update.ajax",method={RequestMethod.POST})
	@ResponseBody
	public Message update(User user){
		try {
			int now=userService.updateUser(user);
			return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtils.build_error("操作失败！");
		}
	}
	
	/**
	 * <p>功能：删除会员<p>
	 * <p>创建日期：2016年12月22日 上午11:17:04<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method={RequestMethod.POST})
	@ResponseBody
	public Message delete(String  id){
		try {
			int now=userService.deleteByPrimaryKey(id);
			return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtils.build_error("操作失败！");
		}
	}
}
