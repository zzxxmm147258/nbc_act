package com.nbcedu.project.service.impl;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.gson.reflect.TypeToken;
import com.nbcedu.bas.util.GsonUtil;
import com.nbcedu.bas.util.ObjectId;
import com.nbcedu.bas.util.StrUtil;
import com.nbcedu.project.dao.ProjectConfigMapper;
import com.nbcedu.project.dao.ProjectOrderMapper;
import com.nbcedu.project.model.Detail;
import com.nbcedu.project.model.ProjectConfig;
import com.nbcedu.project.model.ProjectOrder;
import com.nbcedu.project.service.IProjectConfigService;
import com.nbcedu.project.service.IProjectOrderService;

/**   
 * <p>描述： </p>
 * <p>创建日期：2016年12月12日 下午2:05:31</p>
 * <p>类全名：com.nbcedu.project.service.impl.ProjectOrderServiceImpl</p>
 * 作者：曾小明
 */
@Service
public class ProjectOrderServiceImpl implements IProjectOrderService{

	@Autowired
	private ProjectOrderMapper projectOrderMapper;
	
	@Autowired
	private IProjectConfigService  projectConfigService;
	
	@Autowired
	private ProjectConfigMapper  projectConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return projectOrderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ProjectOrder record) {
		return projectOrderMapper.insert(record);
	}

	@Override
	public int insertSelective(ProjectOrder record) {
		return projectOrderMapper.insertSelective(record);
	}

	@Override
	public ProjectOrder selectByPrimaryKey(String id) {
		return projectOrderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectOrder record) {
		return projectOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProjectOrder record) {
		return projectOrderMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProjectOrder> selectPage(String mId, int page, int limit) {
		return projectOrderMapper.selectPage(mId, page, limit);
	}

	@Override
	public PageList<ProjectOrder> selectByCondition(String mId, PageBounds pageBounds) {
		PageList<ProjectOrder> pageList=projectOrderMapper.selectByCondition(mId, pageBounds);
		for(int i=0;i<pageList.size();i++){
			if(!StrUtil.isnull(pageList.get(i).getConfigId())){//订单的配置项
				pageList.get(i).setConfig(projectConfigService.selectByConfigId(pageList.get(i).getConfigId()));
			}
		}
		return pageList;
	}

	@Override
	public PageList<ProjectOrder> selectByOrder(String mId, String typeId, String name,PageBounds page) {
		PageList<ProjectOrder> list=projectOrderMapper.selectByOrder(mId, typeId, name,page);
		return  list;
	}

	@Override
	public List<ProjectOrder> selectByUserId(String userId, String typeId,int page, int limit) {
		List<ProjectOrder> list=projectOrderMapper.selectByUserId(userId, typeId,page, limit);
		for(int i=0;i<list.size();i++){
			if(!StrUtil.isnull(list.get(i).getConfigId())){
				list.get(i).setConfig(getConfigList(list.get(i).getConfigId()));
			}
		}
		return list;
	}

	@Override
	public int updateDelStatus(String id) {
		return projectOrderMapper.updateDelStatus(id);
	}

	private List<ProjectConfig> getConfigList(String configIds){
		String[] ids=configIds.split(",");
		List<ProjectConfig> config=projectConfigMapper.selectByIds(ids);
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
	public int save(ProjectOrder order) {
		int now=0;
		ProjectOrder bean=projectOrderMapper.selectByPrimaryKey(order.getId());
		if(StrUtil.isnull(order.getId())) order.setId(ObjectId.getUUId());
		order.setDateTime(new Date());
		if(bean==null){
			now=projectOrderMapper.insertSelective(order);
		}else{
			now=projectOrderMapper.updateByPrimaryKeySelective(order);
		}
		return now;
	}
}
