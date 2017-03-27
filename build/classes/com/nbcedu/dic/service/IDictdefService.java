package com.nbcedu.dic.service;

import java.util.List;

import com.nbcedu.dic.model.Dictdef;


public interface IDictdefService {
	List<Dictdef> selectAll();
	
	int insert(Dictdef dictdef);
	
	int deleteByDictid(int dictid);
	
	int updateByDictid(int oldDictid,Dictdef dictdef);
	
	Dictdef getById(int dictid);
}
