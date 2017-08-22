package org.sysu.service;

import java.util.List;

import org.sysu.pojo.RolePermission;

public interface RolePermissionService {
	
	public List<RolePermission> selectByRoleId(Long roleId);
}
