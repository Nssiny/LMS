package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.ReaderBorrow;

public interface ReaderBorrowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReaderBorrow record);

    int insertSelective(ReaderBorrow record);

    ReaderBorrow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReaderBorrow record);

    int updateByPrimaryKey(ReaderBorrow record);
    
    List<ReaderBorrow> selectByAccountBack0(String account);
    
    List<ReaderBorrow> selectByAccountBackNot0(String account);
    
    ReaderBorrow selectByBarCode1Back0(String barCode);
    //根据reader_borrow的id筛选出rb&ib记录
    ReaderBorrow selectByRbid1(Long rbid);
    
    List<ReaderBorrow> selectByLikeBarCode1Back0(String barCode);
    
    List<ReaderBorrow> selectByLikeAccount(@Param("account") String account, @Param("penaltyFlag") Byte penaltyFlag);
    
    ReaderBorrow selectByPrimaryKeyFromBBC(Long rbid);
    
    List<ReaderBorrow> selectBySQL(@Param("key") String key);
}