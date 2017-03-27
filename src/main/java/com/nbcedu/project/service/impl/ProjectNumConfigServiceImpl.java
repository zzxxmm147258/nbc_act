package com.nbcedu.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbcedu.project.dao.ProjectNumConfigMapper;
import com.nbcedu.project.model.ProjectNumConfig;
import com.nbcedu.project.service.IProjectNumConfigService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:01:39</p>
 * <p>类全名：com.nbcedu.project.service.impl.ProjectNumConfigServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class ProjectNumConfigServiceImpl implements IProjectNumConfigService{

	@Autowired
	private ProjectNumConfigMapper projectNumConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return projectNumConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(ProjectNumConfig record) {
		return projectNumConfigMapper.insertSelective(record);
	}

	@Override
	public ProjectNumConfig selectByPrimaryKey(String id) {
		return projectNumConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectNumConfig record) {
		return projectNumConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ProjectNumConfig selectBymId(String mId) {
		return projectNumConfigMapper.selectBymId(mId);
	}

	@Override
	public int saveOrUpdate(ProjectNumConfig config, boolean action) {
		if(action){
			return projectNumConfigMapper.updateByPrimaryKeySelective(config);
		}else{
			return projectNumConfigMapper.insertSelective(config);
		}
	}

}
