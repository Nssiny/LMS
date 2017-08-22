package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoDuplicateMapper;
import org.sysu.pojo.InfoDuplicate;
import org.sysu.service.InfoDuplicateService;

@Service
public class InfoDuplicateServiceImpl implements InfoDuplicateService {
	
	@Autowired
	private InfoDuplicateMapper infoDuplicateMapper;
	
	public InfoDuplicate selectByBid(Long bid){
		return infoDuplicateMapper.selectByBid(bid);
	}
}
