package org.sysu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> selectBySQL(@Param("key") String key);
}