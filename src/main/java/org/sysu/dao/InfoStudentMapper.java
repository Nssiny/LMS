package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.InfoStudent;

public interface InfoStudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoStudent record);

    int insertSelective(InfoStudent record);

    InfoStudent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoStudent record);

    int updateByPrimaryKey(InfoStudent record);
    
    InfoStudent selectByLoginID(String login_id);
    
    List<InfoStudent> selectByNotExistsUser();
    
    List<InfoStudent> selectByNotExistsLicense();
}