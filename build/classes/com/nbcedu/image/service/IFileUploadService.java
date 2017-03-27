package com.nbcedu.image.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nbcedu.bas.util.CutStyle;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月14日 下午3:57:49</p>
 * <p>类全名：com.nbcedu.image.service.IFileUploadService</p>
 * 作者：曾小明
 */
public interface IFileUploadService {
	
	public String uploadFile(MultipartFile file) throws IOException;
	
	public  File getFile(String fileName);
	
	/**
	 * <p>功能：上传图片<p>
	 * <p>创建日期：2016年12月15日 下午2:53:14<p>
	 * <p>作者：曾小明<p>
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> upload_image(MultipartFile file) throws Exception;
	
	/**
	 * <p>功能：浏览图片<p>
	 * <p>创建日期：2016年12月15日 下午2:53:26<p>
	 * <p>作者：曾小明<p>
	 * @param file_path
	 * @param style
	 * @return
	 */
	public File get_image_file(String file_path,CutStyle style);

}
