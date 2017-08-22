package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.ReaderBorrowMapper;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.service.ReaderBorrowService;

@Service
public class ReaderBorrowServiceImpl implements ReaderBorrowService {

	@Autowired
	private ReaderBorrowMapper readerBorrowMapper;
	
	public List<ReaderBorrow> selectByAccountBack0(String account) {
		// TODO Auto-generated method stub
		return this.readerBorrowMapper.selectByAccountBack0(account);
	}
	
	public List<ReaderBorrow> selectByAccountBackNot0(String account) {
		return this.readerBorrowMapper.selectByAccountBackNot0(account);
	}

	public List<ReaderBorrow> selectByLikeBarCode1Back0(String barCode) {
		return this.readerBorrowMapper.selectByLikeBarCode1Back0(barCode);
	}
	
	public List<ReaderBorrow> selectByLikeAccount(String account, Byte penaltyFlag) {
		return this.readerBorrowMapper.selectByLikeAccount(account, penaltyFlag);
	}
}
