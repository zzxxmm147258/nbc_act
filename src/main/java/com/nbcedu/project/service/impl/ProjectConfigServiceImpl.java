package com.nbcedu.project.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.project.dao.ProjectConfigMapper;
import com.nbcedu.project.dao.ProjectNumConfigMapper;
import com.nbcedu.project.model.Detail;
import com.nbcedu.project.model.ProjectConfig;
import com.nbcedu.project.model.ProjectNumConfig;
import com.nbcedu.project.service.IProjectConfigService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午1:55:24</p>
 * <p>类全名：com.nbcedu.project.service.impl.ProjectConfigServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class ProjectConfigServiceImpl implements IProjectConfigService{

	@Autowired
	private ProjectConfigMapper projectConfigMapper;
	
	@Autowired
	private ProjectNumConfigMapper projectNumConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return projectConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProjectConfig record) {
		return projectConfigMapper.insert(record);
	}

	@Override
	public int insertSelective(ProjectConfig record) {
		return projectConfigMapper.insertSelective(record);
	}

	@Override
	public ProjectConfig selectByPrimaryKey(String id) {
		return projectConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectConfig record) {
		return projectConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(ProjectConfig record) {
		return projectConfigMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(ProjectConfig record) {
		return projectConfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProjectConfig> selectBymId(String mId) {
		List<ProjectConfig> config=projectConfigMapper.selectBymId(mId);
		for(int j=0;j<config.size();j++){
			if(!StrUtil.isnull(config.get(j).getDetail())){
				Type type = new TypeToken<List<Detail>>(){}.getType();
				List<Detail> list = GsonUtil.gson.fromJson(config.get(j).getDetail(), type);
				config.get(j).setDet(list);
			}
		}
		return config;
	}

	@Override
	public int save(ProjectNumConfig bean, List<ProjectConfig> config) {
		int now=0;
		ProjectNumConfig num=projectNumConfigMapper.selectByPrimaryKey(bean.getId());
		if(num==null){
			projectNumConfigMapper.insertSelective(bean);
		}else{
			projectNumConfigMapper.updateByPrimaryKeySelective(bean);
		}
		for(int i=0;i<config.size();i++){
			ProjectConfig con=projectConfigMapper.selectByPrimaryKey(config.get(i).getId());
			//config.get(i).setDetail(JsonUtil.toJsonString(config.get(i).getDet()));
			if(con==null){
				now=projectConfigMapper.insertSelective(config.get(i));
			}else{
				now=projectConfigMapper.updateByPrimaryKeySelective(config.get(i));
			}
		}
		return now;
	}

	@Override
	public List<ProjectConfig> selectByConfigId(String configId) {
		String[] config=configId.split(",");
		List<ProjectConfig> list=new ArrayList<ProjectConfig>();
		for(int i=0;i<config.length;i++){
			list.add(projectConfigMapper.selectByPrimaryKey(config[i]));
		}
		return list;
	}

}
