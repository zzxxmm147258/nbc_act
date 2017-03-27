package com.nbcedu.dic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbcedu.dic.model.Dictdef;

public interface DictdefMapper {
	
	int insert(Dictdef dictdef);
	
	List<Dictdef> selectAll();
	
	int deleteByDictid(int dictid);
	
	int updateByDictid(@Param("oldDictid") int oldDictid,@Param("dictdef") Dictdef dictdef);
	
	Dictdef getById(int dictid);
}