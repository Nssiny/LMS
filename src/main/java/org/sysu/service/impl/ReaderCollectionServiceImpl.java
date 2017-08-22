package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.ReaderCollectionMapper;
import org.sysu.pojo.ReaderCollection;
import org.sysu.service.ReaderCollectionService;

@Service
public class ReaderCollectionServiceImpl implements ReaderCollectionService {

	@Autowired
	private ReaderCollectionMapper readerCollectionMapper;
	
	@Override
	public List<ReaderCollection> selectByAccount(String account) {
		// TODO Auto-generated method stub
		return this.readerCollectionMapper.selectByAccount(account);
	}

}
