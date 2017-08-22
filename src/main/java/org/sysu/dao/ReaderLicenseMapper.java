package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.ReaderLicense;

public interface ReaderLicenseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReaderLicense record);

    int insertSelective(ReaderLicense record);

    ReaderLicense selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReaderLicense record);

    int updateByPrimaryKey(ReaderLicense record);
    
    ReaderLicense selectByAccount(String account);
    
    List<ReaderLicense> selectByLikeAccount(String q);
}