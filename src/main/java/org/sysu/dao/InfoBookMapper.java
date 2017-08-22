package org.sysu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.InfoBook;

public interface InfoBookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InfoBook record);

    int insertSelective(InfoBook record);

    InfoBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InfoBook record);

    int updateByPrimaryKey(InfoBook record);
    
    InfoBook selectAll();
    
    List<InfoBook> selectByTitle(String title);
    
    List<InfoBook> selectByTitleLimit(@Param("title") String title, @Param("begin") int begin, @Param("last") int last);
    
    int countAllByTitle(String title);
    
    List<InfoBook> selectByFTLimit(@Param("ft") String ft, @Param("begin") int begin, @Param("last") int last);
    
    int countAllByFT(String ft);
    
    List<InfoBook> list(Map<String, Object> map);
    
    List<InfoBook> selectBySQL(@Param("key") String key);
}