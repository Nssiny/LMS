package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByLoginID(String login_id);
    
    List<User> selectBySQL(@Param("key") String key);
}