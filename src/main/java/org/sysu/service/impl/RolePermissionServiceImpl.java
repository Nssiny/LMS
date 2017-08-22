package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.RolePermissionMapper;
import org.sysu.pojo.RolePermission;
import org.sysu.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public List<RolePermission> selectByRoleId(Long roleId) {
		return this.rolePermissionMapper.selectByRoleId(roleId);
	}

}
