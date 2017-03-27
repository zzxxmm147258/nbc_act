package com.nbcedu.bas.json;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>标题：json工具</p>
 * <p>功能： json转换工具</p>
 */
public class JsonUtil {
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static ObjectMapper getInstance() {
		return objectMapper;
	}

	/**
	 * 使用Jackson 数据绑定 将对象转换为 json字符串
	 * 
	 * 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.error("转换为json字符串失败" + e.toString());
		} catch (JsonMappingException e) {
			log.error("转换为json字符串失败" + e.toString());
		} catch (IOException e) {
			log.error("转换为json字符串失败" + e.toString());
		}
		return null;
	}

	/**
	 * json字符串转化为 JavaBean
	 * 
	 * 还可以直接JsonUtils.getInstance().readValue(String content,Class
	 * valueType)用这种方式
	 * 
	 * @param <T>
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T toJavaBean(String jsonStr, Class<T> clazz) {
		T t = null;
		try {
			t = objectMapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			log.error("json字符串转化为 javabean失败" + e.toString());
		} catch (JsonMappingException e) {
			log.error("json字符串转化为 javabean失败" + e.toString());
		} catch (IOException e) {
			log.error("json字符串转化为 javabean失败" + e.toString());
		}
		return t;
	}

	/**
	 * json字符串转化为list
	 * 
	 * 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new
	 * TypeReference<List<T>>(){})方式
	 * 
	 * @param <T>
	 * @param content
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> toJavaBeanList(String content, TypeReference<List<T>> typeReference) throws IOException {
		try {
			return objectMapper.readValue(content, typeReference);
		} catch (JsonParseException e) {
			log.error("json字符串转化为 list失败,原因:" + e.toString());
			throw new RuntimeException("json字符串转化为 list失败");
		} catch (JsonMappingException e) {
			log.error("json字符串转化为 list失败,原因" + e.toString());
			throw new JsonMappingException("json字符串转化为 list失败");
		} catch (IOException e) {
			log.error("json字符串转化为 list失败,原因" + e.toString());
			throw new IOException("json字符串转化为 list失败");
		}
	}

	/**
	 * 将对象转换为JSON流 add by mjd
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			objectMapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
