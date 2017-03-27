package com.nbcedu.bas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 ImageBase64.java
 * @date         创建日期 2016年12月28日
 * @description  描述
 */
public class ImageBase64 {  
    
	/**
	 * image 转  base64 码
	 * 方法名称:image2Base64
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param file_path
	 * @return String
	 * @throws IOException 
	 */
	public static String image2Base64(String file_path){
		File file = new File(file_path);
		if(file.exists()){
			return image2Base64(file);
		}
		return null;
	}
	
	/**
	 * image 转  base64 码
	 * 方法名称:image2Base64
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param imgFile
	 * @return
	 * @throws IOException String
	 */
	public static String image2Base64(File imgFile){  
		byte[] data = get_bytes(imgFile);
		if(data==null){
			return null;
		}
        data = Base64.encodeBase64(data);  
        StringBuffer sb = new StringBuffer();  
        for (byte bt : data) {  
            sb.append((char) bt);  
        }
        return sb.toString();  
    }  
    
	/**
	 * 获取文件字节数组
	 * 方法名称:get_bytes
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param file
	 * @return byte[]
	 */
	public static byte[] get_bytes(File file){
		if(!file.exists()){
			return null;
		}
		try(FileInputStream in = new FileInputStream(file)){
			byte[] data = new byte[in.available()];  
	        in.read(data);
	        return data;
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 文件写入
	 * 方法名称:writeImage
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param base64_str
	 * @param file_path
	 * @throws IOException void
	 */
	public static void writeImage(String base64_str,String file_path) throws IOException {
		byte[] data = Base64.decodeBase64(base64_str);
		writeImage(data, file_path);
	}
	
    /**
     * 文件写入
     * 方法名称:writeImage
     * 作者:lqc
     * 创建日期:2016年12月28日
     * 方法描述:  
     * @param data
     * @param file_path
     * @throws IOException void
     */
	public static void writeImage(byte[] data,String file_path) throws IOException {  
        File file = new File(file_path); 
        createFile(file);
        try(FileOutputStream fos = new FileOutputStream(file)){
        	fos.write(data); 
        }catch (Exception e) {
        	System.out.println("文件写入异常！");
		}
    }
	
	private static boolean createFile(File file) throws IOException {  
        if(! file.exists()) {  
            makeDir(file.getParentFile());  
        }  
        return file.createNewFile();  
    }  
    
	private static void makeDir(File dir) {  
        if(! dir.getParentFile().exists()) {  
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    }
      
}  

