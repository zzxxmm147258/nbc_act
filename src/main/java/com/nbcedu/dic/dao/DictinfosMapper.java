package com.nbcedu.dic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbcedu.dic.model.Dictinfos;

public interface DictinfosMapper {
	List<Dictinfos> selectAll();
	
	List<Dictinfos> selectByDictid(@Param("dictid")int dictid,@Param("currentSalesStatus")String currentSalesStatus);
	
	List<Dictinfos> selectAllByDictid(int dictid);

	int delete(@Param("dictid")String dictid,@Param("code")String code);
	
	int insert(Dictinfos dictinfo);
	
	int delectByDictid(int dictid);
	
	
	int updateByOldKey(@Param("oldCode") String oldCode,@Param("dictinfo") Dictinfos dictinfo);
	
	int updateDictidByDictid(@Param("newDictid") int newDictid,@Param("oldDictid") int oldDictid);
	
	Dictinfos selectByDictidCode(@Param("dictid")String dictid,@Param("code")String code);
}