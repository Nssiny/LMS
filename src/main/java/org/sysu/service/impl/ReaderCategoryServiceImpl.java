package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.ReaderCategoryMapper;
import org.sysu.pojo.ReaderCategory;
import org.sysu.service.ReaderCategoryService;

@Service
public class ReaderCategoryServiceImpl implements ReaderCategoryService {
	
	@Autowired
	private ReaderCategoryMapper readerCateGoryMapper;

	@Override
	public List<ReaderCategory> selectBySQL(String key) {
		// TODO Auto-generated method stub
		return this.readerCateGoryMapper.selectBySQL(key);
	}

}
