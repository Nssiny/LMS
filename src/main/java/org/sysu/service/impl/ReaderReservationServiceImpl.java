package org.sysu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.ReaderReservationMapper;
import org.sysu.pojo.ReaderReservation;
import org.sysu.service.ReaderReservationService;

@Service
public class ReaderReservationServiceImpl implements ReaderReservationService {

	@Autowired
	private ReaderReservationMapper readerReservationMapper;
	
	public List<ReaderReservation> selectByAccountEffective1(String account) {
		// TODO Auto-generated method stub
		return this.readerReservationMapper.selectByAccountEffective1(account);
	}
	
	public List<ReaderReservation> selectByAccountEffective0(String account) {
		// TODO Auto-generated method stub
		return this.readerReservationMapper.selectByAccountEffective0(account);
	}

	public ReaderReservation selectByBarCodeAndEffective(String barCode, Byte isEffective) {
		return this.readerReservationMapper.selectByBarCodeAndEffective(barCode, isEffective);
	}
	
	public List<ReaderReservation> selectBySQL(String key) {
		return this.readerReservationMapper.selectBySQL(key);
	}
}
