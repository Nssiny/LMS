package org.sysu.service;

import org.sysu.pojo.InfoStudent;

public interface InfoStudentService {

	public InfoStudent selectByLoginID(String login_id);
}
