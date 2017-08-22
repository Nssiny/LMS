package org.sysu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoTeacherMapper;
import org.sysu.pojo.InfoTeacher;
import org.sysu.service.InfoTeacherService;

@Service
public class InfoTeacherServiceImpl implements InfoTeacherService {

	@Autowired
	private InfoTeacherMapper infoTeacherMapper;
	
	public InfoTeacher selectByTeaID(String teaId) {
		// TODO Auto-generated method stub
		return infoTeacherMapper.selectByTeaID(teaId);
	}

}
