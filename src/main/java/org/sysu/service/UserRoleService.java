package org.sysu.service;

import java.util.List;

import org.sysu.pojo.UserRole;

public interface UserRoleService {
	public List<UserRole> selectByLoginId(Long lid);
}
