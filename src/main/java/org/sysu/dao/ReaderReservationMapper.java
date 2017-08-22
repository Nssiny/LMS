package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.ReaderReservation;

public interface ReaderReservationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReaderReservation record);

    int insertSelective(ReaderReservation record);

    ReaderReservation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReaderReservation record);

    int updateByPrimaryKey(ReaderReservation record);
    
    List<ReaderReservation> selectByAccountEffective1(String account);
    
    List<ReaderReservation> selectByAccountEffective0(String account);
    
    ReaderReservation selectByBarCodeAndEffective(@Param("barCode") String barCode, @Param("isEffective") Byte isEffective);
    
    List<ReaderReservation> selectBySQL(@Param("key") String key);
    
    List<ReaderReservation> selectByEffective(@Param("isEffective") Byte isEffective);
}