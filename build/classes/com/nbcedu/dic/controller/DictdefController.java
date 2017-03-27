package com.nbcedu.dic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.json.JsonUtil;
import com.nbcedu.dic.model.Dictdef;
import com.nbcedu.dic.model.Dictinfos;
import com.nbcedu.dic.service.IDictdefService;
import com.nbcedu.dic.service.IDictinfosService;



@Controller
@RequestMapping("/admin/dictdef")
public class DictdefController {
	
	@Autowired
	private IDictdefService dictdefService;
	@Autowired
	private IDictinfosService dictinfoService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Dictdef> dictdefs = dictdefService.selectAll();
		model.addAttribute("dictdefs", dictdefs);
		return "component/list";
	}
	
	@RequestMapping(value="/getChild",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getChild(int dictid){
		List<Dictinfos> dictinfos = dictinfoService.selectByDictid(dictid,null);
		String json = JsonUtil.toJsonString(dictinfos);
		return json;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public int add(Dictdef dictdef){
		int num = dictdefService.insert(dictdef);
		return dictdef.getDictid();
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public int del(int dictid){
		int num = dictdefService.deleteByDictid(dictid);
		dictinfoService.delectByDictid(dictid);
		return num;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public int update(int old_dictid,Dictdef dictdef){
		int num = dictdefService.updateByDictid(old_dictid, dictdef);
		dictinfoService.updateDictidByDictid(dictdef.getDictid(), old_dictid);
		return num;
	}
}
