package org.sysu.service;

import java.util.List;

import org.sysu.pojo.User;

public interface UserService {
	public User getUserById(long id);

	public User selectByLoginID(String login_id);
	
	public List<User> selectBySQL(String key);
}
