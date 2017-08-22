package org.sysu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoStudentMapper;
import org.sysu.pojo.InfoStudent;
import org.sysu.service.InfoStudentService;

@Service
public class InfoStudentServiceImpl implements InfoStudentService {

	@Autowired
	private InfoStudentMapper infoStudentMapper;

	public InfoStudent selectByLoginID(String login_id) {
		return infoStudentMapper.selectByLoginID(login_id);
	}

}
