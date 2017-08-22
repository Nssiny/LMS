package org.sysu.service;

import java.util.List;

import org.sysu.pojo.Permission;

public interface PermissionService {
	
	public List<Permission> selectBySQL(String key);
}
