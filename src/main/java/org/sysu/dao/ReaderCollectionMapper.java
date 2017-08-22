package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.ReaderCollection;

public interface ReaderCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReaderCollection record);

    int insertSelective(ReaderCollection record);

    ReaderCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReaderCollection record);

    int updateByPrimaryKey(ReaderCollection record);
    
    List<ReaderCollection> selectByAccount(String account);
}