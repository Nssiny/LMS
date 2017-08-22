package org.sysu.service;

import java.util.List;

import org.sysu.pojo.InfoPenalty;

public interface InfoPenaltyService {
	public List<InfoPenalty> selectByAccount0(String account);
	public List<InfoPenalty> selectByAccount1(String account);
	public InfoPenalty selectByRbid(Long rbid);
	public List<InfoPenalty> selectByLikeAccount(String account);
	public List<InfoPenalty> selectBySQL(String key);
}
