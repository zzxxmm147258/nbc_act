package com.nbcedu.dic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.dic.model.Dictinfos;
import com.nbcedu.dic.service.IDictinfosService;



/** 
 * <p>标题：</p>
 * <p>功能： </p>
 */
@Controller
@RequestMapping("/common/dictinfo")
public class DictinfosCommonController {
	
	@Autowired
	private IDictinfosService dictinfoService;
	
	/**
	 * @功能:根据Dictid查出对应字典数据
	 */
	@RequestMapping(value = "/findByDictid.ajax", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ajaxFindByDictid(int dictid){
		List<Dictinfos> list = dictinfoService.selectByDictid(dictid);
		return GsonUtil.gsonString(list);
	}
}
