package com.nbcedu.bas.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.constant.ContextConstant;

@Component("fileUpload")
public class FileUploadUtils {

	@Autowired
    private ConfigProperty configProperty;
	
	@Autowired
    private TaskExecutor taskExecutor;
	
	private final String[] IMAGEALLOWEDTYPE = { "image/bmp", "image/gif", "image/jpeg", "image/png" };
	
	public Map<String, String> upload_image(MultipartFile file) throws Exception{
		return upload_image(file, true);
	}
	/**
	 * 图片文件上传
	 * 方法名称:upload
	 * 作者:lqc
	 * 创建日期:2016年12月14日
	 * 方法描述:  
	 * @param file
	 * @return
	 * @throws Exception String
	 */
	public Map<String, String> upload_image(MultipartFile file,boolean bl) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String pic_type = file.getContentType();
		if(!check_image_type(pic_type)){
			map.put("code", ContextConstant.ERROR_CODE);
	        map.put("msg", "error|不支持的类型");
			return map;
		}
		String file_ture_name = DateUtils.format(new Date(), "yyyyMM/dd/")+System.currentTimeMillis();
		switch (pic_type) {
			case "image/jpeg":
				file_ture_name = file_ture_name.concat(".jpg");
				break;
			case "image/png":
				file_ture_name = file_ture_name.concat(".png");
				break;
			case "image/bmp":
				file_ture_name = file_ture_name.concat(".bmp");
				break;
			case "image/gif":
				file_ture_name = file_ture_name.concat(".gif");
				break;
			default:
				file_ture_name = file_ture_name.concat(".jpg");
				break;
		}
        File localFile = new File(configProperty.getSave_path()+file_ture_name);
        createFile(localFile);
        int big_2 = 2 * 1024 * 1024; // 2M以上就进行0.6压缩
        int big_5 = 5 * 1024 * 1024; // 5M以上就进行0.6压缩
        double r_size = 0.5f;
        if (file.getSize() > big_5){
        	r_size = 0.2f;
        }else{
        	if (file.getSize() > big_2) {
        		r_size = 0.4f;
	        } else {
	        	r_size = 0.8f;
	        }
        }
        file.transferTo(localFile); 
        System.out.println(r_size);
        thumbnail(localFile,configProperty.getSave_path()+file_ture_name, r_size);
        map.put("code", ContextConstant.SUCCESS_CODE);
        map.put("file_path", DESUtils.encodeStr(file_ture_name));
        if(bl){
        	ImageCutTask.image_cut(configProperty.getSave_path()+file_ture_name);
        }else{
        	taskExecutor.execute(new Task(configProperty.getSave_path()+file_ture_name));
        }
		return map;
	}
	 
	 private static void thumbnail(File ofile,String new_file_path, double size) {
	        try {
	            Thumbnails.of(new_file_path).scale(size).toFile(new_file_path);
	        } catch (IOException e) {
	            try {
	                BufferedImage image = ImageIO.read(ofile);
	                ImageOutputStream output = ImageIO.createImageOutputStream(ofile);
	                if (!ImageIO.write(image, "jpg", output)) {
	                }
	                Thumbnails.of(image).scale(0.4f).toFile(new_file_path);
	            } catch (IOException e1) {
	            }
	        }
	    }
	
	
	
	/**
	 * 获取图片访问路径
	 * 方法名称:get_image_uri
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param request
	 * @param file_path
	 * @return String
	 */
	public String get_image_uri(HttpServletRequest request,String file_path){
		String root = request.getServletContext().getContextPath();
		return root+"/image/"+file_path;
	}
	
	/**
	 * 创建文件
	 * 方法名称:createFile
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param file
	 * @return
	 * @throws IOException boolean
	 */
	public boolean createFile(File file) throws IOException {  
        if(! file.exists()) {  
            makeDir(file.getParentFile());  
        }  
        return file.createNewFile();  
    }  
    
	/**
	 * 创建文件夹
	 * 方法名称:makeDir
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param dir void
	 */
    public void makeDir(File dir) {  
        if(! dir.getParentFile().exists()) {  
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    }  
	
	
	/**
	 * 
	 * 方法名称:check_image_type
	 * 作者:lqc
	 * 创建日期:2016年12月14日
	 * 方法描述:  
	 * @param type
	 * @return boolean
	 */
	public boolean check_image_type(String type){
		 return Arrays.asList(IMAGEALLOWEDTYPE).contains(type);
	}
	
	/**
	 * 获取图片文件
	 * 方法名称:get_image_file
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param file_path
	 * @param style
	 * @return String
	 */
	public File get_image_file(String file_path,CutStyle style){
		file_path = DESUtils.decodeStr(file_path);
		String style_file = null ;
		switch (style) {
			case CUT001:
				style_file = file_path.replace(".", "_s1.");
				break;
			case CUT002:
				style_file = file_path.replace(".", "_s2.");
				break;
			case CUT003:
				style_file = file_path.replace(".", "_s3.");
				break;
			default:
				break;
		}
		File file = null;
		file = new File(configProperty.getSave_path()+style_file);
		// 压缩，裁剪图片 检测是否存在
		if(file.exists()){
			return file;
		}
		file = new File(configProperty.getSave_path()+file_path);
		// 原图 是否存在
		if(file.exists()){
			return file;
		}
		return new File(configProperty.getError_image());
	}
	
	
	public Map<String, String> upload_byte(String strImg) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String[] imagBase =strImg.split(",");//截取图片
		String image="";
		if(imagBase[0].matches("image/jpeg")){
			image=".jpeg";
		}else if(imagBase[0].matches("image/png")){
			image=".png";
		}else if(imagBase[0].matches("image/bmp")){
			image=".bmp";
		}else if(imagBase[0].matches("image/gif")){
			image=".gif";
		}else{
			image=".jpg";
		}
		String file_ture_name = DateUtils.format(new Date(), "yyyyMM/dd/")+System.currentTimeMillis()+image;
		File file=GenerateImage(imagBase[1], configProperty.getSave_path()+file_ture_name);
		if(file.exists() && file.length() == 0){
			map.put("code", ContextConstant.ERROR_CODE);
	        map.put("msg", "error|不支持的类型");
			return map;
		}
		createFile(file);
	    map.put("code", ContextConstant.SUCCESS_CODE);
	    map.put("file_path", DESUtils.encodeStr(file_ture_name));
	    ImageCutTask.image_cut(configProperty.getSave_path()+file_ture_name);
	    return map;
		
	}
	
	  public static File GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		 File file=new File(imgFilePath);
	     try {
	    	 byte[] bytes = Base64.decodeBase64(imgStr);
	         for(int i = 0; i < bytes.length; ++i) {
	            if (bytes[i] < 0) {// 调整异常数据
	               bytes[i] += 256;
	             }
	           }
	         OutputStream out = new FileOutputStream(imgFilePath);
	         out.write(bytes);
	         out.flush();
	         out.close();
	         return file;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return file;
	    }
	  }
	
	
}
