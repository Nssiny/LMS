package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoPenaltyMapper;
import org.sysu.pojo.InfoPenalty;
import org.sysu.service.InfoPenaltyService;

@Service
public class InfoPenaltyServiceImpl implements InfoPenaltyService {

	@Autowired
	private InfoPenaltyMapper infoPenaltyMapper;
	
	public List<InfoPenalty> selectByAccount0(String account) {
		// TODO Auto-generated method stub
		return this.infoPenaltyMapper.selectByAccount0(account);
	}
	
	public List<InfoPenalty> selectByAccount1(String account) {
		return this.infoPenaltyMapper.selectByAccount1(account);
	}
	
	public InfoPenalty selectByRbid(Long rbid) {
		return this.infoPenaltyMapper.selectByRbid(rbid);
	}

	public List<InfoPenalty> selectByLikeAccount(String account) {
		return this.infoPenaltyMapper.selectByLikeAccount(account);
	}
	
	public List<InfoPenalty> selectBySQL(String key) {
		return this.infoPenaltyMapper.selectBySQL(key);
	}
}
