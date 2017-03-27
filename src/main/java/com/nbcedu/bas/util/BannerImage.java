package com.nbcedu.bas.util;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 BannerImage.java
 * @date         创建日期 2017年1月4日
 * @description  描述
 */
public class BannerImage implements Comparable<BannerImage>{

	private String title="";
	private String image_url="";
	private String url="";
	private boolean blank=false;
	private int sort=0;
	private boolean href_flag=true;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isBlank() {
		return blank;
	}
	public void setBlank(boolean blank) {
		this.blank = blank;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public boolean isHref_flag() {
		return href_flag;
	}
	public void setHref_flag(boolean href_flag) {
		this.href_flag = href_flag;
	}
	/**
	 * 获取 banner image 图集
	 * 方法名称:get_banner_image
	 * 作者:lqc
	 * 创建日期:2017年1月4日
	 * 方法描述:  
	 * @return List<BannerImage>
	 */
	public static List<BannerImage> get_banner_image(){
		String json_str = loadFile("config/banner_image.json");
		if(StringUtils.isBlank(json_str))
			return null;
		else{
			List<BannerImage> list=new ArrayList<BannerImage>();  
	        Type type = new TypeToken<ArrayList<BannerImage>>() {}.getType();
			list = GsonUtil.gson.fromJson(json_str, type);
			Collections.sort(list);
			return list;
		}
	}
	
	private static String loadFile(String file){
		try {
			return FileUtils.readFileToString(
					new File(Thread.currentThread().
					getContextClassLoader().
					getResource(file).toURI()),"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public int compareTo(BannerImage o) {
		// TODO Auto-generated method stub
		return this.sort-o.getSort();
	}
	
}
