package org.sysu.service;

import org.sysu.pojo.ReaderLicense;

public interface ReaderLicenseService {
	public ReaderLicense selectByAccount(String account);
}
