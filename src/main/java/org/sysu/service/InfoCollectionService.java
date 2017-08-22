package org.sysu.service;

import java.util.List;

import org.sysu.pojo.InfoCollection;

public interface InfoCollectionService {
	public List<InfoCollection> selectByBid1(Long bid);
	public InfoCollection selectByPrimaryKey(Long id);
	public List<InfoCollection> selectBySQL(String key);
}
