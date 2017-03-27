package com.nbcedu.dic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.data.DataMap;
import com.nbcedu.dic.model.Dictinfos;
import com.nbcedu.dic.service.IDictinfosService;

@Controller
@RequestMapping("/admin/dictinfo")
public class DictinfosController {
	
	@Autowired
	private IDictinfosService dictinfoService;
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public int del(String dictid,String code){
		DataMap.clear(DataMap.GRADE_TYPE);//去缓存
		int num = dictinfoService.delete(dictid, code);
		return num;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Dictinfos dictinfo){
		int num = dictinfoService.insert(dictinfo);
		if(num > 0){
			DataMap.clear(DataMap.GRADE_TYPE);//去缓存
			return JsonUtil.toJsonString(dictinfo.getCode());
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public int update(String old_code,Dictinfos dictinfo){
		int num = dictinfoService.updateByOldKey(old_code, dictinfo);
		DataMap.clear(DataMap.GRADE_TYPE);//去缓存
		return num;
	}
	
}
