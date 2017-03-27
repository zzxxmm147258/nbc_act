package com.nbcedu.bas.util;

import org.apache.commons.codec.binary.Base64;

/**
 * 字符串加密解密处理（图片保存路径）
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 DESUtils.java
 * @date         创建日期 2016年12月15日
 * @description  描述
 */
public class DESUtils{
	
	/**
	 * 加密
	 * 方法名称:encodeStr
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param plainText
	 * @return String
	 */
    public static String encodeStr(String plainText){
        byte[] b=plainText.getBytes();
        Base64 base64=new Base64();
        b=base64.encode(b);
        String s=new String(b);
        return s;
    }
    
    /**
     * 解密
     * 方法名称:decodeStr
     * 作者:lqc
     * 创建日期:2016年12月15日
     * 方法描述:  
     * @param encodeStr
     * @return String
     */
    public static String decodeStr(String encodeStr){
        byte[] b=encodeStr.getBytes();
        Base64 base64=new Base64();
        b=base64.decode(b);
        String s=new String(b);
        return s;
    }
    
}