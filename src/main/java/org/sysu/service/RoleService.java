package org.sysu.service;

import java.util.List;

import org.sysu.pojo.Role;

public interface RoleService {
	
	public List<Role> selectBySQL(String key);

}
