package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.UserRoleMapper;
import org.sysu.pojo.UserRole;
import org.sysu.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public List<UserRole> selectByLoginId(Long lid) {
		// TODO Auto-generated method stub
		return this.userRoleMapper.selectByLoginId(lid);
	}

}
