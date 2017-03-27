package com.nbcedu.dic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbcedu.dic.dao.DictinfosMapper;
import com.nbcedu.dic.model.Dictinfos;
import com.nbcedu.dic.service.IDictinfosService;



@Service("dictinfosServiceImpl")
public class DictinfosServiceImpl implements IDictinfosService {

	@Autowired
	private DictinfosMapper dictinfoMapper;
	
	@Override
	public List<Dictinfos> selectAll() {
		return dictinfoMapper.selectAll();
	}

	@Override
	public List<Dictinfos> selectByDictid(int dictid,String currentSalesStatus) {
		return dictinfoMapper.selectByDictid(dictid,currentSalesStatus);
	}

	@Override
	public int insert(Dictinfos dictinfo) {
		return dictinfoMapper.insert(dictinfo);
	}

	@Override
	public int delectByDictid(int dictid) {
		return dictinfoMapper.delectByDictid(dictid);
	}

	@Override
	public int updateDictidByDictid(int newDictid, int oldDictid) {
		return dictinfoMapper.updateDictidByDictid(newDictid, oldDictid);
	}

	@Override
	public int updateByOldKey(String oldCode, Dictinfos dictinfo) {
		return dictinfoMapper.updateByOldKey(oldCode, dictinfo);
	}

	@Override
	public int delete(String dictid, String code) {
		return dictinfoMapper.delete(dictid, code);
	}

	@Override
	public List<Dictinfos> selectByDictid(int dictid) {
		return dictinfoMapper.selectAllByDictid(dictid);
	}

	@Override
	public Dictinfos selectByDictidCode(String dictid, String code) {
		return dictinfoMapper.selectByDictidCode(dictid, code);
	}

}
