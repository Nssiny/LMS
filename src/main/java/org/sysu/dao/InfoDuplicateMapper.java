package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.InfoDuplicate;

public interface InfoDuplicateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoDuplicate record);

    int insertSelective(InfoDuplicate record);

    InfoDuplicate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoDuplicate record);

    int updateByPrimaryKey(InfoDuplicate record);
    
    InfoDuplicate selectByBid(Long bid);
}