package org.sysu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.InfoBookMapper;
import org.sysu.pojo.InfoBook;
import org.sysu.service.InfoBookService;

@Service
public class InfoBookServiceImpl implements InfoBookService {
	@Autowired
	private InfoBookMapper infoBookMapper;
	
	public InfoBook selectByID(long id) {
		return infoBookMapper.selectByPrimaryKey(id);
	}
	
	public InfoBook selectAll() {
		return infoBookMapper.selectAll();
	}
	
	public List<InfoBook> selectByTitle(String title) {
		return infoBookMapper.selectByTitle(title);
	}
	
	public List<InfoBook> selectByTitleLimit(String title, int begin, int last) {
		return infoBookMapper.selectByTitleLimit(title, begin, last);
	}
	
	public int countAllByTitle(String title) {
		return infoBookMapper.countAllByTitle(title);
	}
	
	public List<InfoBook> selectByFTLimit(String ft, int begin, int last) {
		return infoBookMapper.selectByFTLimit(ft, begin, last);
	}
	
	public int countAllByFT(String ft) {
		return infoBookMapper.countAllByFT(ft);
	}
	
	public List<InfoBook> list(Map<String, Object> map) {
		return infoBookMapper.list(map);
	}

	@Override
	public List<InfoBook> selectBySQL(String key) {
		return this.infoBookMapper.selectBySQL(key);
	}
}
