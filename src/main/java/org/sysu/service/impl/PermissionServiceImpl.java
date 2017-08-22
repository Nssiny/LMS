package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.PermissionMapper;
import org.sysu.pojo.Permission;
import org.sysu.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public List<Permission> selectBySQL(String key) {
		return this.permissionMapper.selectBySQL(key);
	}

}
