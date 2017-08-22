package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sysu.dao.UserMapper;
import org.sysu.pojo.User;
import org.sysu.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	public User getUserById(long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public User selectByLoginID(String login_id) {
		return userMapper.selectByLoginID(login_id);
	}
	
	public List<User> selectBySQL(String key) {
		return userMapper.selectBySQL(key);
	}

}
