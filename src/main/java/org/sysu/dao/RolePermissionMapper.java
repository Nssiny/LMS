package org.sysu.dao;

import java.util.List;

import org.sysu.pojo.RolePermission;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
    
    List<RolePermission> selectByRoleId(Long roleId);
}