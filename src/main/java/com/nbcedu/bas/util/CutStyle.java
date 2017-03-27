package com.nbcedu.bas.util;


/**
 * 图片样式
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 CutStyle.java
 * @date         创建日期 2016年12月15日
 * @description  描述
 */
public enum CutStyle {
	
	CUT001("cut001"), 		//s1 210*180
	CUT002("cut002"), 		//s2 450*380
	CUT003("cut003"), 		//s3 870*480
	DEFAULT("original"); 	// 原图
	
	private String VALUE;

	private CutStyle(String value) {
		this.VALUE = value;
	}

	public String getVALUE() {
		return VALUE;
	}
	
	/**
	 * 获取图片样式（样式不存在返回原图样式）
	 * 方法名称:getMessageType
	 * 作者:lqc
	 * 创建日期:2016年12月15日
	 * 方法描述:  
	 * @param value
	 * @return CutStyle
	 */
	public static CutStyle getStyleType(String value) {
	    for (CutStyle style : CutStyle.values()) {
	         if (value.equals(style.getVALUE())) {
	             return style;
	         }
	     }
	     return CutStyle.DEFAULT;
	 }
	
}
