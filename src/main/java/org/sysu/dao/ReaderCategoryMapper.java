package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.ReaderCategory;

public interface ReaderCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReaderCategory record);

    int insertSelective(ReaderCategory record);

    ReaderCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReaderCategory record);

    int updateByPrimaryKey(ReaderCategory record);
    
    List<ReaderCategory> selectBySQL(@Param("key") String key);
}