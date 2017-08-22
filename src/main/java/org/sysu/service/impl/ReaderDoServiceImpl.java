package org.sysu.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sysu.dao.InfoCollectionMapper;
import org.sysu.dao.ReaderBorrowMapper;
import org.sysu.dao.ReaderCollectionMapper;
import org.sysu.dao.ReaderLicenseMapper;
import org.sysu.dao.ReaderReservationMapper;
import org.sysu.pojo.InfoCollection;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.pojo.ReaderCollection;
import org.sysu.pojo.ReaderLicense;
import org.sysu.pojo.ReaderReservation;
import org.sysu.service.ReaderDoService;
import org.sysu.utils.MyException;

import com.alibaba.fastjson.JSON;

@Service
public class ReaderDoServiceImpl implements ReaderDoService {

	@Autowired
	private ReaderLicenseMapper readerLicenseMapper;
	@Autowired
	private InfoCollectionMapper infoCollectionMapper;
	@Autowired
	private ReaderReservationMapper readerReservationMapper;
	@Autowired
	private ReaderBorrowMapper readerBorrowMapper;
	@Autowired
	private ReaderCollectionMapper readerCollectionMapper;
	

	@Transactional(rollbackFor=Exception.class)
	public void rreservationdo(String account, String barCode) {
		//读者预约
		//1、检查读者借阅证状态；2、检查图书状态，是否已外借未预约；
		//3、更改图书状态，插入预约记录
		ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(account);
		if (readerLicense.getStatus() != 1) {
			throw new MyException("读者借书证被冻结或注销！");
		} else {
			ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByBarCode1Back0(barCode);
			if (readerBorrow.getInfoCollection().getIsBorrowing()==1 && readerBorrow.getInfoCollection().getIsReservation()==0) {
				//更改图书预约状态
				InfoCollection update = new InfoCollection();
				System.out.println(JSON.toJSONString(readerBorrow));
				update.setId(readerBorrow.getInfoCollection().getId());
				update.setIsReservation((byte) 1);
				this.infoCollectionMapper.updateByPrimaryKeySelective(update);
				//插入预约记录
				ReaderReservation readerReservation = new ReaderReservation();
				readerReservation.setBarCode(barCode);
				readerReservation.setAccount(account);
				readerReservation.setDateReservation(new Date());
				readerReservation.setDateBack(readerBorrow.getDateReturn());
				GregorianCalendar gc =new GregorianCalendar();
				gc.setTime(readerBorrow.getDateReturn());
				gc.add(5, 7);
				readerReservation.setDateEffective(gc.getTime());
				this.readerReservationMapper.insertSelective(readerReservation);
			} else {
				throw new MyException("该书状态不允许被预约！");
			}
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void rreborrowdo(String account, Long rbid) {
		//读者续借
		//1、判断图书未被预约；2、借阅证状态正常
		//3、借阅证最大续借次数>该借阅记录当前续借次数
		ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByRbid1(rbid);
		if(readerBorrow.getInfoCollection().getIsReservation() == 0) {
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(account);
			if(readerLicense.getStatus() != 1) {
				throw new MyException("读者借书证被冻结或注销！");
			} else {
				if(readerBorrow.getReborrowCount() < readerLicense.getReaderCategory().getBookBorrowAgain()) {
					ReaderBorrow update = new ReaderBorrow();
					update.setId(rbid);
					update.setReborrowCount(readerBorrow.getReborrowCount()+1);
					GregorianCalendar gc =new GregorianCalendar();
					gc.setTime(readerBorrow.getDateReturn());
					gc.add(5, 30);
					update.setDateReturn(gc.getTime());
					this.readerBorrowMapper.updateByPrimaryKeySelective(update);
				} else {
					throw new MyException("续借次数已达上限，续借失败！");
				}
			}
		} else {
			throw new MyException("该书已被他人预约，无法续借！");
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void rcancelReservationdo(Long rrid) {
		//读者取消预约
		//1、修改预约表，预约状态为0
		//2、修改图书状态，被预约状态为0
		ReaderReservation readerReservation = this.readerReservationMapper.selectByPrimaryKey(rrid);
		System.out.println(JSON.toJSONString(readerReservation));
		InfoCollection infoCollection = this.infoCollectionMapper.selectByBarCode(readerReservation.getBarCode());
		//1
		ReaderReservation updateRR = new ReaderReservation();
		updateRR.setId(rrid);
		updateRR.setIsEffective((byte) 0);
		this.readerReservationMapper.updateByPrimaryKeySelective(updateRR);
		//2
		InfoCollection updateIC = new InfoCollection();
		updateIC.setId(infoCollection.getId());
		updateIC.setIsReservation((byte) 0);
		this.infoCollectionMapper.updateByPrimaryKeySelective(updateIC);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void readerCollectionAdd(ReaderCollection readerCollection) {
		this.readerCollectionMapper.insertSelective(readerCollection);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void readerCollectionDel(Long rcid) {
		this.readerCollectionMapper.deleteByPrimaryKey(rcid);
	}
}
