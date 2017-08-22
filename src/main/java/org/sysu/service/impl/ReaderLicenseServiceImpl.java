package org.sysu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.dao.ReaderLicenseMapper;
import org.sysu.pojo.ReaderLicense;
import org.sysu.service.ReaderLicenseService;

@Service
public class ReaderLicenseServiceImpl implements ReaderLicenseService {

	@Autowired
	private ReaderLicenseMapper readerLicenseMapper;
	
	public ReaderLicense selectByAccount(String account) {
		// TODO Auto-generated method stub
		return this.readerLicenseMapper.selectByAccount(account);
	}

}
