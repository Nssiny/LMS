package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.RoleMapper;
import org.sysu.pojo.Role;
import org.sysu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> selectBySQL(String key) {
		return this.roleMapper.selectBySQL(key);
	}

}
