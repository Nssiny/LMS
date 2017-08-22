package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoCollectionMapper;
import org.sysu.pojo.InfoCollection;
import org.sysu.service.InfoCollectionService;

@Service
public class InfoCollectionServiceImpl implements InfoCollectionService {

	@Autowired
	private InfoCollectionMapper infoCollectionMapper;
	
	public List<InfoCollection> selectByBid1(Long bid) {
		// TODO Auto-generated method stub
		return infoCollectionMapper.selectByBid1(bid);
	}
	
	public InfoCollection selectByPrimaryKey(Long id) {
		return this.infoCollectionMapper.selectByPrimaryKey(id);
	}
	
	public List<InfoCollection> selectBySQL(String key) {
		return this.infoCollectionMapper.selectBySQL(key);
	}

}
