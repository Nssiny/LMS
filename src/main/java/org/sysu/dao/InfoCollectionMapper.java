package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.InfoCollection;

public interface InfoCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoCollection record);

    int insertSelective(InfoCollection record);

    InfoCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoCollection record);

    int updateByPrimaryKey(InfoCollection record);
    
    List<InfoCollection> selectByBid1(Long bid);
    
    InfoCollection selectByBarCode(String barCode);
    
    List<InfoCollection> selectByLikeBarCode(String q);
    
    InfoCollection selectByICidFromICandIB(Long icid);
    
    List<InfoCollection> selectBySQL(@Param("key") String key);
}