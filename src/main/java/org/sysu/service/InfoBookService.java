package org.sysu.service;

import java.util.List;
import java.util.Map;

import org.sysu.pojo.InfoBook;

public interface InfoBookService {
	public InfoBook selectByID(long id);
	public InfoBook selectAll();
	public List<InfoBook> selectByTitle(String title);
	public List<InfoBook> selectByTitleLimit(String title, int begin, int last);
	public int countAllByTitle(String title);
	public List<InfoBook> selectByFTLimit(String ft, int begin, int last);
	public int countAllByFT(String ft);
	public List<InfoBook> list(Map<String, Object> map);
	//书目信息查询
	public List<InfoBook> selectBySQL(String key);
}
