package com.nbcedu.image.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月14日 下午4:10:22</p>
 * <p>类全名：com.nbcedu.image.controller.FileUploadController</p>
 * 作者：曾小明
 */
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.util.CutStyle;
import com.nbcedu.bas.util.FileUploadUtils;  
  

  
@Controller  
public class FileUploadController {  
  
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);  
    
    @Autowired
	FileUploadUtils fileUpload;
    
    /**
     * <p>功能：访问原始图片<p>
     * <p>创建日期：2016年12月15日 下午2:53:45<p>
     * <p>作者：曾小明<p>
     * @param response
     * @param file_path
     * @throws IOException
     */
    @RequestMapping(value="/image/{file_path}")
	@ResponseBody
	public void image(HttpServletResponse response,@PathVariable String file_path) throws IOException{
        try (OutputStream os = response.getOutputStream()){  
            os.write(FileUtils.readFileToByteArray(fileUpload.get_image_file(file_path, CutStyle.DEFAULT)));  
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	System.out.println("文件不存在,可以指定默认丢失图片。");
		}
	}
    
	
    /**
     * <p>功能：访问带样式图片<p>
     * <p>创建日期：2016年12月15日 下午2:54:04<p>
     * <p>作者：曾小明<p>
     * @param response
     * @param file_path
     * @param style
     * @throws IOException
     */
	@RequestMapping(value="/image/{file_path}/{style}")
	@ResponseBody
	public void image(HttpServletResponse response,@PathVariable String file_path,@PathVariable String style) throws IOException{
		try (OutputStream os = response.getOutputStream()){  
            os.write(FileUtils.readFileToByteArray(fileUpload.get_image_file(file_path, CutStyle.getStyleType(style))));  
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	System.out.println("文件不存在,可以指定默认丢失图片。");
		}
	}
	
	/**
	 * <p>功能：图片上传<p>
	 * <p>创建日期：2016年12月15日 下午3:05:55<p>
	 * <p>作者：曾小明<p>
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upload",method={RequestMethod.POST})
	@ResponseBody
	public String upload(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("imageFile")MultipartFile file ) throws Exception{
		// 文件类型限制
        Map<String, String> map = fileUpload.upload_image(file,false);
        if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
        	return fileUpload.get_image_uri(request,map.get("file_path")).concat("/cut003");
        }else{
        	return map.get("msg");
        }
     }  

	@RequestMapping(value="/test",method={RequestMethod.GET})
	public String upload(){
		return "test";
	}
	
}