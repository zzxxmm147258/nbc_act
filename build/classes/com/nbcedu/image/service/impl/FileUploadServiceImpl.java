package com.nbcedu.image.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.util.ConfigProperty;
import com.nbcedu.bas.util.CutStyle;
import com.nbcedu.bas.util.DESUtils;
import com.nbcedu.bas.util.DateUtils;
import com.nbcedu.image.service.IFileUploadService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月14日 下午3:58:43</p>
 * <p>类全名：com.nbcedu.image.service.impl.FileUploadServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService{

	public final String FILE_PATH = "/upload/";
	
	@Autowired
    private ConfigProperty configProperty;
	
	private final String[] IMAGEALLOWEDTYPE = { "image/bmp", "image/gif", "image/jpeg", "image/png" };
	
	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();  
        File tempFile = new File(FILE_PATH, new Date().getTime() + String.valueOf(fileName));  
        if (!tempFile.getParentFile().exists()) {  
            tempFile.getParentFile().mkdir();  
        }  
        if (!tempFile.exists()) {  
            tempFile.createNewFile();  
        }  
        file.transferTo(tempFile);  
        return "/download?fileName=" + tempFile.getName(); 
	}

	@Override
	public  File getFile(String fileName) {  
        return new File(FILE_PATH, fileName);  
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
	@Override
	public Map<String, String> upload_image(MultipartFile file) throws Exception{
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
        file.transferTo(localFile); 
        map.put("code", ContextConstant.SUCCESS_CODE);
        map.put("file_path", DESUtils.decodeStr(file_ture_name));
		return map;
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
	@Override
	public File get_image_file(String file_path,CutStyle style){
		file_path = DESUtils.decodeStr(file_path);
		switch (style) {
			case CUT001:
				file_path = file_path.replace(".", "_s1.");
				break;
			case CUT002:
				file_path = file_path.replace(".", "_s2.");
				break;
			case CUT003:
				file_path = file_path.replace(".", "_s3.");
				break;
			default:
				break;
		}
		return new File(configProperty.getSave_path()+file_path);
	}
}
