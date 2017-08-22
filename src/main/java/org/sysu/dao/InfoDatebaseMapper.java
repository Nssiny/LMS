package org.sysu.dao;

import org.sysu.pojo.InfoDatebase;

public interface InfoDatebaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoDatebase record);

    int insertSelective(InfoDatebase record);

    InfoDatebase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoDatebase record);

    int updateByPrimaryKey(InfoDatebase record);
}