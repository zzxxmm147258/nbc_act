package com.nbcedu.bas.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
 
 
public class GsonUtil {
    public static Gson gson = null;
    static {
        if (gson == null) {
             gson = new GsonBuilder()
            .create();
        }
    }
 
    /**
     * 转成json
     * 
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }
 
    /**
     * 转成bean
     * 
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
    
    /**
     * 转成bean
     * 
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Type type) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, type);
        }
        return t;
    }
 
    /**
     * 转成list
     * 
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }
 
    /**
     * 转成list中有map的
     * 
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }
 
    /**
     * 转成map的
     * 
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
    
    
    
    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, 
	           boolean excludesFieldsWithoutExpose,boolean disableHtmlEscaping) {
	        GsonBuilder builder = new GsonBuilder();  
	        if (excludesFieldsWithoutExpose) builder.excludeFieldsWithoutExposeAnnotation();  
	        return toJson(target, targetType, builder);
	 }
	 
	 public static String toJson(Object target) {  
	        return toJson(target, null, false, true,true);  
	    }  
	 public static String toJson(Object target, Type targetType) {
	        return toJson(target, targetType, null);
	 }
	 
	 public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
	        return toJson(target, targetType, false,excludesFieldsWithoutExpose,false);
	 }
	 private static String toJson(Object target, Type targetType, GsonBuilder builder) {  
	        if (target == null) return "{}";  
	        Gson gsonb = null;  
	        if (builder == null) {  
	            gsonb = gson;
	        } else {  
	            gsonb = builder.create();  
	        }  
	        String result = "{}";  
//	        try {  
	            if (targetType == null) {  
	                result = gsonb.toJson(target);  
	            } else {  
	                result = gsonb.toJson(target, targetType);  
	            }  
//	        } catch (Exception ex) {  
//	        } 
	        return result;  
	}
}   
