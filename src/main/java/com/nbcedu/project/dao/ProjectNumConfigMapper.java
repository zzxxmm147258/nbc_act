package com.nbcedu.project.dao;

import com.nbcedu.project.model.ProjectNumConfig;

public interface ProjectNumConfigMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(ProjectNumConfig record);

    ProjectNumConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectNumConfig record);

    ProjectNumConfig selectBymId(String mId);
}