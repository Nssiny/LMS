package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.InfoTeacher;

public interface InfoTeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoTeacher record);

    int insertSelective(InfoTeacher record);

    InfoTeacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoTeacher record);

    int updateByPrimaryKey(InfoTeacher record);
    
    InfoTeacher selectByTeaID(String teaId);
    
    List<InfoTeacher> selectByNotExistsUser();
    
    List<InfoTeacher> selectByNotExistsLicense();
}