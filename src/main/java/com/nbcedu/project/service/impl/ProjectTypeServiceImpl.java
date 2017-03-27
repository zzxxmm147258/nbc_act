package com.nbcedu.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbcedu.data.DataMap;
import com.nbcedu.project.dao.ProjectMapper;
import com.nbcedu.project.dao.ProjectTypeMapper;
import com.nbcedu.project.model.ProjectType;
import com.nbcedu.project.service.IProjectTypeService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:09:36</p>
 * <p>类全名：com.nbcedu.project.service.impl.ProjectTypeServiceImpl</p>
 * 作者：曾小明
 */
@Service("projectTypeServiceImpl")
public class ProjectTypeServiceImpl implements IProjectTypeService{
	
	@Autowired
	private ProjectTypeMapper projectTypeMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return projectTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProjectType record) {
		return projectTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(ProjectType record) {
		return projectTypeMapper.insertSelective(record);
	}

	@Override
	public ProjectType selectByPrimaryKey(String id) {
		return projectTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectType record) {
		return projectTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProjectType record) {
		return projectTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProjectType> selectAll() {
		return projectTypeMapper.selectAll();
	}

	@Override
	public List<ProjectType> selectBypId(String pId) {
		return projectTypeMapper.selectBypId(pId);
	}

	@Override
	public int delete(String id) {
		List<ProjectType> list=projectTypeMapper.selectBypId(id);
		int now=projectTypeMapper.deleteByPrimaryKey(id);
		if(now>0&&list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				projectTypeMapper.deleteByPrimaryKey(list.get(i).getId());
			}
		}
		return now;
	}

	@Override
	public List<ProjectType> selectByAll() {
		@SuppressWarnings("unchecked")
		List<ProjectType> list=(List<ProjectType>) DataMap.load(DataMap.PRO_TYPE);
		for(int i=0;i<list.size();i++){
			List<ProjectType> type=projectTypeMapper.selectBypId(list.get(i).getId());
			list.get(i).setType(type);
		}
		return list;
	}

	@Override
	public int updateName(String id, String name) {
		ProjectType type=projectTypeMapper.selectByPrimaryKey(id);
		type.setName(name);
		return projectTypeMapper.updateByPrimaryKeySelective(type);
	}

	@Override
	public List<ProjectType> selectByProject() {
		@SuppressWarnings("unchecked")
		List<ProjectType> type= (List<ProjectType>) DataMap.load(DataMap.PRO_TYPE);
		for(int i=0;i<type.size();i++){
			type.get(i).setProject(projectMapper.selectPage(type.get(i).getId(), 0, 7));//查询条件
		}
		return type;
	}

}
