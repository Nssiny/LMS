package org.sysu.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sysu.pojo.ReaderBorrow;

public interface ReaderBorrowService {
	public List<ReaderBorrow> selectByAccountBack0(String account);
	public List<ReaderBorrow> selectByAccountBackNot0(String account);
	public List<ReaderBorrow> selectByLikeBarCode1Back0(String barCode);
	public List<ReaderBorrow> selectByLikeAccount(String account, Byte penaltyFlag);
}
