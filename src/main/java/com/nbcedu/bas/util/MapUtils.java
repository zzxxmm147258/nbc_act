package com.nbcedu.bas.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 MapUtils.java
 * @date         创建日期 2016年12月13日
 * @description  描述
 */
public class MapUtils {

	/**
	 * 
	 * 方法名称:build_map
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param key
	 * @param value
	 * @return Map<String,Object>
	 */
	public static Map<String, String> build_map(String key,String value){
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}
	
	/**
	 * 
	 * 方法名称:build_map
	 * 作者:lqc
	 * 创建日期:2016年12月13日
	 * 方法描述:  
	 * @param keys
	 * @param values
	 * @return Map<String,Object>
	 */
	public static Map<String, String> build_map(String[] keys,String[] values){
		if(keys.length!=values.length){
			throw new ArrayIndexOutOfBoundsException();
		}
		Map<String, String> map = new HashMap<String, String>(keys.length);
		for(int i=0; i<keys.length; i++){
			map.put(keys[i], values[i]);
		}
		return map;
	}
	
	
}
