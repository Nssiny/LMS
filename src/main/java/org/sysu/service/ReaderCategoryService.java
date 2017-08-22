package org.sysu.service;

import java.util.List;

import org.sysu.pojo.ReaderCategory;

public interface ReaderCategoryService {
	public List<ReaderCategory> selectBySQL(String key);
}
