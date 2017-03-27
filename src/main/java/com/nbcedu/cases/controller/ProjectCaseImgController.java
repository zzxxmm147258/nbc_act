package com.nbcedu.cases.controller;

import java.util.Map;

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
import com.nbcedu.bas.model.Message;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.MessageUtils;
import com.nbcedu.bas.util.PageUtils;
import com.nbcedu.cases.model.ProjectCaseImg;
import com.nbcedu.cases.service.IProjectCaseImgService;
import com.nbcedu.cases.service.IProjectCaseService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月15日 下午1:46:41</p>
 * <p>类全名：com.nbcedu.cases.controller.ProjectCaseImgController</p>
 * 作者：曾小明
 */
@Controller
@RequestMapping("/main/caseimg")
public class ProjectCaseImgController {
	
	@Autowired
	private IProjectCaseImgService projectCaseImgService;
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private FileUploadUtils fileUpload;

	/**
	 * <p>功能：分页查询图片列表<p>
	 * <p>创建日期：2016年12月15日 下午1:48:40<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param typeId
	 * @param mId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,String mId,@ModelAttribute("page")PageBounds page){
			PageList<ProjectCaseImg> pageList = projectCaseImgService.selectByCondition(mId,PageUtils.convertPage(page,12));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("list", pageList);
		    model.addAttribute("mId", mId);
		    model.addAttribute("pId", projectCaseService.selectByPrimaryKey(mId).getmId());
		   return "cms/case/img_list";
	}
	
	/**
	 * <p>功能：修改图片标题<p>
	 * <p>创建日期：2016年12月21日 上午10:54:14<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param title
	 * @return
	 */
	@RequestMapping(value="/updateTitle.ajax",method=RequestMethod.POST)
	@ResponseBody
	public Message updateTitle(String id,String title){
		int now=projectCaseImgService.updateTitle(id, title);
		return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
	}
	
	
	/**
	 * <p>功能：多张图片上传<p>
	 * <p>创建日期：2016年12月15日 下午3:54:22<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param style
	 * @param img
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(String mId,String style,@RequestParam(value = "img") MultipartFile[] img,Model model){
		for(int i=0;i<img.length;i++){
		  if(!img[i].isEmpty()){
			try {
				Map<String,String> url = fileUpload.upload_image(img[i]);
				ProjectCaseImg bean=new ProjectCaseImg();
				bean.setmId(mId);
				bean.setImgUrl(url.get("file_path"));
				String title=img[i].getOriginalFilename();//获得upload部分
				bean.setTitle(title.substring(0,title.lastIndexOf(".")));//图片标题
				bean.setSort(i);
				projectCaseImgService.insertSelective(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		}
		model.addAttribute("mId", mId);
		return "redirect:/main/caseimg/list";
	}
	
	/**
	 * 文件上传
	 * 方法名称:upload
	 * 作者:lqc
	 * 创建日期:2017年1月3日
	 * 方法描述:  
	 * @param file void
	 */
	@RequestMapping(value="/upload/{mId}",method=RequestMethod.POST)
	@ResponseBody
	public void upload(@RequestParam(value = "img", required = false) MultipartFile img,@PathVariable(value="mId") String mId){
		try {
			Map<String,String> map = fileUpload.upload_image(img);
			ProjectCaseImg bean=new ProjectCaseImg();
			bean.setmId(mId);
			bean.setImgUrl(map.get("file_path"));
			String title=img.getOriginalFilename();//获得upload部分
			bean.setTitle(title.substring(0,title.lastIndexOf(".")));//图片标题
			projectCaseImgService.insertSelective(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2016年12月12日 上午9:33:18<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public Message delete(String ids){
		int now=projectCaseImgService.deletes(ids);
		return now>0?MessageUtils.build_success("操作成功！"):MessageUtils.build_error("操作失败！");
	}
	
	@RequestMapping(value="/del/{id}",method={RequestMethod.GET})
	public String del(@RequestHeader(value = "referer") final String referer,@PathVariable String id)throws Exception{
		projectCaseImgService.deleteByPrimaryKey(id);
		return "redirect:".concat(referer);
	}
}
