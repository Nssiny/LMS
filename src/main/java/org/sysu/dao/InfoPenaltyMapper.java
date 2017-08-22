package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.InfoPenalty;

public interface InfoPenaltyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoPenalty record);

    int insertSelective(InfoPenalty record);

    InfoPenalty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoPenalty record);

    int updateByPrimaryKey(InfoPenalty record);
    
    List<InfoPenalty> selectByAccount0(String account);
    
    List<InfoPenalty> selectByAccount1(String account);
    
    InfoPenalty selectByRbid(Long rbid);
    
    List<InfoPenalty> selectByLikeAccount(String account);
    
    List<InfoPenalty> selectBySQL(@Param("key") String key);
}