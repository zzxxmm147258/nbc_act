package com.nbcedu.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.cases.dao.ProjectCaseImgMapper;
import com.nbcedu.cases.model.ProjectCaseImg;
import com.nbcedu.cases.service.IProjectCaseImgService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:47:17</p>
 * <p>类全名：com.nbcedu.cases.service.impl.ProjectCaseImgServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class ProjectCaseImgServiceImpl implements IProjectCaseImgService{
	
	@Autowired
	private ProjectCaseImgMapper projectCaseImgMapper;
	

	@Override
	public int deleteByPrimaryKey(String id) {
		return projectCaseImgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProjectCaseImg record) {
		return projectCaseImgMapper.insert(record);
	}

	@Override
	public int insertSelective(ProjectCaseImg record) {
		return projectCaseImgMapper.insertSelective(record);
	}

	@Override
	public ProjectCaseImg selectByPrimaryKey(String id) {
		return projectCaseImgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectCaseImg record) {
		return projectCaseImgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProjectCaseImg record) {
		return projectCaseImgMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProjectCaseImg> selectPage(String mId, int page, int limit) {
		return projectCaseImgMapper.selectPage(mId, page, limit);
	}

	@Override
	public PageList<ProjectCaseImg> selectByCondition(String mId, PageBounds pageBounds) {
		return projectCaseImgMapper.selectByCondition(mId, pageBounds);
	}

	@Override
	public int deletes(String ids) {
		String[] id=ids.split(",");
		if(id.length>0){
			return projectCaseImgMapper.deletes(id);
		}
		return 0;
	}

	@Override
	public int updateTitle(String id, String title) {
		ProjectCaseImg img=projectCaseImgMapper.selectByPrimaryKey(id);
		img.setTitle(title);
		return projectCaseImgMapper.updateByPrimaryKeySelective(img);
	}

	@Override
	public List<ProjectCaseImg> selectBymId(String mId) {
		return projectCaseImgMapper.selectBymId(mId);
	}
}
