package com.nbcedu.cases.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.cases.dao.ProjectCaseMapper;
import com.nbcedu.cases.model.ProjectCase;
import com.nbcedu.cases.service.IProjectCaseService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:38:31</p>
 * <p>类全名：com.nbcedu.cases.service.impl.ProjectCaseServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class ProjectCaseServiceImpl implements IProjectCaseService{
	
	@Autowired
	private ProjectCaseMapper projectCaseMapper;
	
	@Autowired
	FileUploadUtils fileUpload;

	@Override
	public int deleteByPrimaryKey(String id) {
		return projectCaseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProjectCase record) {
		return projectCaseMapper.insert(record);
	}

	@Override
	public int insertSelective(ProjectCase record) {
		return projectCaseMapper.insertSelective(record);
	}

	@Override
	public ProjectCase selectByPrimaryKey(String id) {
		ProjectCase bean=projectCaseMapper.selectByPrimaryKey(id);
		if(bean.getDateTime()!=null){
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy年MM月dd日");
			bean.setDate_time(dateFm.format(bean.getDateTime()));
		}
		return bean;
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectCase record) {
		return projectCaseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(ProjectCase record) {
		return projectCaseMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(ProjectCase record) {
		return projectCaseMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<ProjectCase> selectByCondition(String typeId, String mId, PageBounds pageBounds) {
		return projectCaseMapper.selectByCondition(typeId, mId, pageBounds);
	}

	@Override
	public List<ProjectCase> selectPage( String mId, int page, int limit) {
		return projectCaseMapper.selectPage(mId, page, limit);
	}

	@Override
	public int delete(String ids) {
		String[] id=ids.split(",");
		if(id.length>0){
			return projectCaseMapper.deletes(id);
		}
		return 0;
	}

	@Override
	public int deletes(String[] ids) {
		return projectCaseMapper.deletes(ids);
	}

	@Override
	public int remove(List<String> ids) {
		return projectCaseMapper.remove(ids);
	}

	@Override
	public List<ProjectCase> selectByName(String name, String mId,int page, int limit) {
		return projectCaseMapper.selectByName(name, mId,page, limit);
	}

	@Override
	public int saveOrUpdate(ProjectCase project, MultipartFile img, boolean action) throws Exception {
		// 更新图片数据
		if(null != img){
			Map<String, String> map = fileUpload.upload_image(img);
			if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
				project.setImgUrl(map.get("file_path"));
			}
		}
		if(!StrUtil.isnull(project.getDate_time())){
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy年MM月dd日");
			project.setDateTime(dateFm.parse(project.getDate_time()));
		}
		if(action){
			project.setUpdateMs(System.currentTimeMillis());
			project.setUpdateTime(new Date());
			return projectCaseMapper.updateByPrimaryKeySelective(project);
		}else{
			return projectCaseMapper.insertSelective(project);
		}
	}

	@Override
	public List<ProjectCase> selectBymId(String mId, int page, int limit) {
		return projectCaseMapper.selectBymId(mId, page, limit);
	}

	

	
}
