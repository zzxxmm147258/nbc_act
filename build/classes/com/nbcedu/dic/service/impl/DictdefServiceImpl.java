package com.nbcedu.dic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbcedu.dic.dao.DictdefMapper;
import com.nbcedu.dic.model.Dictdef;
import com.nbcedu.dic.service.IDictdefService;



@Service
public class DictdefServiceImpl implements IDictdefService {

	@Autowired
	private DictdefMapper dictdefMapper;
	
	@Override
	public List<Dictdef> selectAll() {
		return dictdefMapper.selectAll();
	}

	@Override
	public int insert(Dictdef dictdef) {
		return dictdefMapper.insert(dictdef);
	}

	@Override
	public int deleteByDictid(int dictid) {
		return dictdefMapper.deleteByDictid(dictid);
	}

	@Override
	public int updateByDictid(int oldDictid, Dictdef dictdef) {
		return dictdefMapper.updateByDictid(oldDictid, dictdef);
	}

	@Override
	public Dictdef getById(int dictid) {
		return dictdefMapper.getById(dictid);
	}

}
