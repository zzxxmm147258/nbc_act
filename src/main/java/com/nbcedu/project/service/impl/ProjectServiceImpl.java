package com.nbcedu.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.nbcedu.bas.constant.ContextConstant;
import com.nbcedu.bas.util.FileUploadUtils;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.cases.dao.ProjectCaseMapper;
import com.nbcedu.cases.model.ProjectCase;
import com.nbcedu.data.DataMap;
import com.nbcedu.dic.model.Dictinfos;
import com.nbcedu.project.dao.ProjectMapper;
import com.nbcedu.project.model.Project;
import com.nbcedu.project.model.ProjectType;
import com.nbcedu.project.service.IProjectService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午1:42:20</p>
 * <p>类全名：com.nbcedu.project.service.impl.ProjectServiceImpl</p>
 * 作者：曾小明
 */
@Service("projectServiceImpl")
public class ProjectServiceImpl implements IProjectService{
	
	@Autowired
	private ProjectMapper  projectMapper;
	
	@Autowired
	private ProjectCaseMapper  projectCaseMapper;
	
	
	@Autowired
	FileUploadUtils fileUpload;
	
    
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return projectMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Project record) {
		return projectMapper.insertSelective(record);
	}

	@Override
	public Project selectByPrimaryKey(String id) {
		return projectMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Project record) {
		return projectMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Project> selectPage(String typeId,int page, int limit) {
		return projectMapper.selectPage(typeId,page, limit);
	}

	@Override
	public PageList<Project> selectByCondition(String typeId, PageBounds pageBounds) {
		return projectMapper.selectByCondition(typeId, pageBounds);
	}

	@Override
	public int selectByOrderNum(String id) {
		return projectMapper.selectByOrderNum(id);
	}

	@Override
	public int selectByCaseNum(String id) {
		return projectMapper.selectByCaseNum(id);
	}

	@Override
	public int delete(String ids) {
		int now=0;
		String[] id=ids.split(",");
		if(id.length>0){
			for(int i=0;i<id.length;i++){
				now+=projectMapper.deleteByPrimaryKey(id[i]);
			}
		}
		return now;
	}

	@Override
	public int recommend(Project project) {
		// TODO Auto-generated method stub
		return projectMapper.recommend(project);
	}

	@Override
	public int saveOrUpdate(Project project, MultipartFile img,boolean action) throws Exception {
		// TODO Auto-generated method stub
		// 更新图片数据
		if(null != img){
			Map<String, String> map = fileUpload.upload_image(img);
			if(ContextConstant.SUCCESS_CODE.equals(map.get("code"))){
				project.setImgUrl(map.get("file_path"));
			}
		}
		if(action){
			project.setUpdateMs(System.currentTimeMillis());
			project.setUpdateTime(new Date());
			return projectMapper.updateByPrimaryKeySelective(project);
		}else{
			return projectMapper.insertSelective(project);
		}
	}

	@Override
	@Transactional
	public int remove(List<String> ids) {
		projectCaseMapper.deletemId(ids);
		return projectMapper.remove(ids);
		
	}

	@Override
	public PageList<Project> list(String typeId, PageBounds page) {
		return projectMapper.list(typeId,page);
	}

	@Override
	public Set<Integer> selectNumber() {
		List<Integer> list=projectMapper.selectNumber();
		Set<Integer> set=new HashSet<Integer>();         
	    set.addAll(list);//给set填充         
	    list.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的         
	    list.addAll(set);
		return set;
	}

	@Override
	public List<Project> selectSecondLevel(String typeId, String classId, String gradeId,String name, int page, int limit) {
		 List<Project> list=projectMapper.selectSecondLevel(typeId, classId, gradeId,name, page, limit);
		 for(int i=0;i<list.size();i++){
			 List<ProjectCase> cases=new ArrayList<ProjectCase>();
			 if(!StrUtil.isnull(list.get(i).getCaseIds())&&!StrUtil.isnull(list.get(i).getCaseImages())&&!StrUtil.isnull(list.get(i).getCaseNames())){
				 String[] ids=list.get(i).getCaseIds().split(",");//案例ID
				 String[] images=list.get(i).getCaseImages().split(",");//案例图片
				 String[] names=list.get(i).getCaseNames().split(",");//案例名称		 
				 if(ids.length==images.length){
					 for(int j=0;j<ids.length;j++){
						 ProjectCase bean=new ProjectCase();
						 bean.setId(ids[j]);
						 bean.setImgUrl(images[j]);
						 bean.setName(names[j]);
						 cases.add(bean);
					 }
				 }
			 }
			 list.get(i).setCases(cases);
			 //产品体系类型
			 if(!StrUtil.isnull(list.get(i).getClassId()))
				 list.get(i).setClassId(this.getClassName(list.get(i).getClassId(),list.get(i).getTypeId()));

			 //年级
			 if(!StrUtil.isnull(list.get(i).getGradeId()))
				 list.get(i).setGradeId(this.getGradeName(list.get(i).getGradeId()));

			 
		 }
		return list;
	}

	@Override
	public Project selectById(String id) {
		Project bean=projectMapper.selectByPrimaryKey(id);
		if(!StrUtil.isnull(bean.getClassId()))
		bean.setClassId(this.getClassName(bean.getClassId(),bean.getTypeId()));
		//年级
		if(!StrUtil.isnull(bean.getGradeId()))
			 bean.setGradeId(this.getGradeName(bean.getGradeId()));
		/*bean.setCases(projectCaseMapper.selectPage(id, 1,100));//项目案例
		bean.setConfig(projectConfigService.selectBymId(id));//项目配置
		bean.setNum(projectNumConfigService.selectBymId(id));//项目人数配置
*/		return bean;
	}

	@SuppressWarnings("unchecked")
	private String getClassName(String classIds,String typeId){
		     String classNames="";
			 List<ProjectType> typeClass= (List<ProjectType>) DataMap.load_type_class(typeId);
			 String[] ids=classIds.split(",");
			 for(int k=0;k<ids.length;k++){
				 for(int z=0;z<typeClass.size();z++){
					 if(ids[k].equals(typeClass.get(z).getId())){
						 classNames+=typeClass.get(z).getName()+"、";
					 }
				 }
			 }
		return classNames.length()>0?classNames.substring(0,classNames.length() - 1):classNames;
	}
	
	@SuppressWarnings({ "unchecked" })
	private String getGradeName(String gradeId){
		String gradeNames="";
		String[] gradeIds=gradeId.split(",");
		List<Dictinfos> grade= (List<Dictinfos>) DataMap.load(DataMap.GRADE_TYPE);
		for(int k=0;k<gradeIds.length;k++){
			for(int z=0;z<grade.size();z++){
				if(gradeIds[k].equals(grade.get(z).getCode())){
						 gradeNames+=grade.get(z).getCname()+"、";
					}
				}
		}
	   return gradeNames.length()>0?gradeNames.substring(0,gradeNames.length() - 1):gradeNames;
	}
}
