package org.sysu.service;

import java.util.List;

import org.sysu.pojo.ReaderCollection;

public interface ReaderCollectionService {
	public List<ReaderCollection> selectByAccount(String account);

}
