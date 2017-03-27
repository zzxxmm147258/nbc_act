package com.nbcedu.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.nbcedu.bas.constant.DicConstant;
import com.nbcedu.dic.service.IDictinfosService;
import com.nbcedu.project.model.ProjectType;
import com.nbcedu.project.service.IProjectTypeService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月28日 下午1:46:15</p>
 * <p>类全名：com.nbcedu.data.DataMap</p>
 * 作者：曾小明
 */
public class DataMap {

	/**
	 * 项目产品体系
	 */
	public static final String PRO_TYPE="pro_type";
	/**
	 * 项目产品体系及类型
	 */
	public static final String PRO_TYPE_CLASS="pro_type_class";
	/**
	 * 年级
	 */
	public static final String GRADE_TYPE="grade_type";
	
	/**
	 *前端首页 
	 */
	public static final String PROJECT_HOME="project_home";
	
	
	public static Map<String, List<?>> datas =Collections.synchronizedMap(new HashMap<String, List<?>>());
	
	/**
	 * <p>功能：<p>
	 * <p>创建日期：2016年12月28日 下午1:54:46<p>
	 * <p>作者：曾小明<p>
	 * @param key
	 * @return
	 */
	public static List<?> load(String key){
		List<?> list = datas.get(key);
		if(list == null){
			list = get_datas(key);
			datas.put(key, list);
		}
		return list;
	}
	
	/**
	 * 获取子类型
	 * 方法名称:load_type_class
	 * 作者:lqc
	 * 创建日期:2017年1月9日
	 * 方法描述:  
	 * @param id
	 * @return List<?>
	 */
	@SuppressWarnings("unchecked")
	public static List<?> load_type_class(String id){
		if(StringUtils.isBlank(id)){
			return null;
		}
		List<ProjectType> list = (List<ProjectType>) load(PRO_TYPE_CLASS);
		if(list!=null && list.size()>0){
			for(ProjectType pt : list){
				if(id.equals(pt.getId())){
					return pt.getType();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 加载缓存
	 * 方法名称:init
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param keys void
	 */
	public static void init(String[] keys){
		for(String key : keys){
			datas.put(key, get_datas(key));
		}
	}
	
	/**
	 * DB查询
	 * 方法名称:get_datas
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param key
	 * @return List<?>
	 */
	private static List<?> get_datas(String key){
		IProjectTypeService projectTypeService = null;
		IDictinfosService dictinfosService = null;
		WebApplicationContext acx= ContextLoader.getCurrentWebApplicationContext();
		switch (key) {
			case PRO_TYPE:
				projectTypeService = (IProjectTypeService) acx.getBean("projectTypeServiceImpl");
				return projectTypeService.selectAll();
			case PRO_TYPE_CLASS:
				projectTypeService = (IProjectTypeService) acx.getBean("projectTypeServiceImpl");
				return projectTypeService.selectByAll();
			case GRADE_TYPE:
				dictinfosService=(IDictinfosService) acx.getBean("dictinfosServiceImpl");
				return dictinfosService.selectByDictid(DicConstant.GRADE_TYPE);
			case PROJECT_HOME:
				projectTypeService = (IProjectTypeService) acx.getBean("projectTypeServiceImpl");
				return projectTypeService.selectByProject();
		}
		return null;
	}
	
	/**
	 * 清除缓存
	 * 方法名称:clear
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param key void
	 */
	public static void clear(String key){
		datas.remove(key);
	}
	
	/**
	 * 清除缓存
	 * 方法名称:clear
	 * 作者:lqc
	 * 创建日期:2016年12月28日
	 * 方法描述:  
	 * @param keys void
	 */
	public static void clear(String... keys){
		if(keys!=null && keys.length>0){
			for(String key : keys){
				System.out.println(key+"---------");
				datas.remove(key);
			}
		}
	}
	
	/**
	 * 清空缓存信息
	 * 方法名称:clearALL
	 * 作者:lqc
	 * 创建日期:2017年1月4日
	 * 方法描述:   void
	 */
	public static void clearALL(){
		datas.remove(PRO_TYPE);
		datas.remove(PRO_TYPE_CLASS);
		datas.remove(GRADE_TYPE);
		datas.remove(PROJECT_HOME);
	}
	
	/**
	 * 加载栏目菜单
	 * 方法名称:menu_loading
	 * 作者:lqc
	 * 创建日期:2017年1月18日
	 * 方法描述:  
	 * @param model void
	 */
	public static void menu_loading(Model model,String typeId){
		if(StringUtils.isNotBlank(typeId)){
			model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
			model.addAttribute("typeClass", DataMap.load_type_class(typeId));//产品体系分类
			model.addAttribute("grade", DataMap.load(DataMap.GRADE_TYPE));//年级
		}else{
			model.addAttribute("type", DataMap.load(DataMap.PRO_TYPE));//体系
			model.addAttribute("grade", DataMap.load(DataMap.GRADE_TYPE));//年级
		}
	}
	
	/**
	 * 加载菜单
	 * 方法名称:menu_loading
	 * 作者:lqc
	 * 创建日期:2017年1月18日
	 * 方法描述:  
	 * @param model void
	 */
	public static void menu_loading(Model model){
		menu_loading(model, null);
	}
	
}
