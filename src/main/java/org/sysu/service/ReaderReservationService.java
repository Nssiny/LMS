package org.sysu.service;

import java.util.List;

import org.sysu.pojo.ReaderReservation;

public interface ReaderReservationService {
	public List<ReaderReservation> selectByAccountEffective1(String account);
	public List<ReaderReservation> selectByAccountEffective0(String account);
	public ReaderReservation selectByBarCodeAndEffective(String barCode, Byte isEffective);
	public List<ReaderReservation> selectBySQL(String key);
}
