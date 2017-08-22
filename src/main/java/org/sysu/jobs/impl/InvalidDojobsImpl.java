package org.sysu.jobs.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.sysu.dao.InfoCollectionMapper;
import org.sysu.dao.InfoPenaltyMapper;
import org.sysu.dao.InfoStudentMapper;
import org.sysu.dao.InfoTeacherMapper;
import org.sysu.dao.ReaderBorrowMapper;
import org.sysu.dao.ReaderReservationMapper;
import org.sysu.jobs.InvalidDojobs;
import org.sysu.mail.model.MailAttachment;
import org.sysu.pojo.InfoCollection;
import org.sysu.pojo.InfoPenalty;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.InfoTeacher;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.pojo.ReaderReservation;
import org.sysu.service.AdminDoService;
import org.sysu.utils.EmailUtil;

@Component
public class InvalidDojobsImpl implements InvalidDojobs {
	
	private static Logger logger = LoggerFactory.getLogger(InvalidDojobsImpl.class);
	
	@Autowired
	private ReaderReservationMapper readerReservationMapper;
	@Autowired
	private InfoCollectionMapper infoCollectionMapper;
	@Autowired
	private ReaderBorrowMapper readerBorrowMapper;
	@Autowired
	private AdminDoService adminDoService;
	@Autowired
	private InfoTeacherMapper infoTeacherMapper;
	@Autowired
	private InfoStudentMapper infoStudentMapper;
	@Autowired
	private InfoPenaltyMapper infoPenaltyMapper;
	
	@Override
	@Scheduled(cron = "0 0 1 * * ?")
	public void reservationInvalid() {
		List<ReaderReservation> rrList = this.readerReservationMapper.selectByEffective((byte) 1);
		List<String> rridList = new ArrayList<String>();
		for(int i = 0; i < rrList.size(); i++) {
			if(rrList.get(i).getDateEffective().compareTo(new Date()) == -1) {
				//检查图书is_borrowing == 1
				InfoCollection infoCollection = this.infoCollectionMapper.selectByBarCode(rrList.get(i).getBarCode());
				if(infoCollection.getIsBorrowing() == 0) {
					//置预约失效
					rridList.add(rrList.get(i).getId()+"");
				}
			}
		}
		String[] rrids = rridList.toArray(new String[rridList.size()]);
		try {
			this.adminDoService.doCancelReservation(rrids);
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	@Scheduled(cron = "0 0 1 * * ?")
	public void overdueDo() {
		List<ReaderBorrow> rbList = new LinkedList<ReaderBorrow>();
		String key = " where penalty_flag = 0 and NOW() > date_return";
		rbList = this.readerBorrowMapper.selectBySQL(key);
		if(rbList.size() != 0) {
			try {
				this.adminDoService.doOverdues(rbList);
				logger.info("sql:"+key+"  执行超期计划事件成功！");
			} catch(Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	@Override
	@Scheduled(cron = "0 0 1 * * ?")
	public void beforeBackWarn() {
		List<ReaderBorrow> rbList = new LinkedList<ReaderBorrow>();
		String key = " where date_back is null and TO_DAYS(date_return) - TO_DAYS(NOW()) = 7";
		rbList = this.readerBorrowMapper.selectBySQL(key);
		Set<String> account = new HashSet<String>();
		try {
			if(rbList.size() != 0) {
				for(int i = 0; i < rbList.size(); i++) {
					account.add(rbList.get(i).getAccount());
				}
				List<String> accounts = new ArrayList<String>(account);
				//System.out.println(accounts.toString());
				for(int i = 0; i < accounts.size(); i++) {
					String key1 = " where date_back is null and TO_DAYS(date_return) - TO_DAYS(NOW()) = 7 and account = '" + accounts.get(i)+"'";
					List<ReaderBorrow> rbs = this.readerBorrowMapper.selectBySQL(key1);
					Set<String> titleSet = new HashSet<String>();
					String dateReturn = "";//图书应还时间
					for(int j = 0; j < rbs.size(); j++) {
						ReaderBorrow rb = this.readerBorrowMapper.selectByPrimaryKeyFromBBC(rbs.get(j).getId());
						titleSet.add(rb.getInfoBook().getTitle());
						dateReturn = new SimpleDateFormat("yyyy-MM-dd").format(rb.getDateReturn());
					}
					List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
					String to = "";//收件人
					String name = "";//收件人姓名
					if(accounts.get(i).substring(0, 1).equals("T")) {
						InfoTeacher it = this.infoTeacherMapper.selectByTeaID(accounts.get(i));
						to = it.getEmail();
						name = it.getName();
					} else {
						InfoStudent is = this.infoStudentMapper.selectByLoginID(accounts.get(i));
						to = is.getEmail();
						name = is.getName();
					}
					String content = name+"，您好！您借阅的图书："+titleSet.toString()+"将于 "+dateReturn+" 到期。请及时将图书归还！谢谢，祝好！";
					EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：借阅图书即将到期", content, emailList);
					//System.out.println(to);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}

	}
	
	@Override
	@Scheduled(cron = "0 0 1 * * ?")
	public void penaltyWarn() {
		List<InfoPenalty> ipList = new LinkedList<InfoPenalty>();
		String key = " where price_pay is null";
		ipList = this.infoPenaltyMapper.selectBySQL(key);
		Set<String> accountSet = new HashSet<String>();
		try {
			if(ipList.size() != 0) {
				for(int i = 0; i < ipList.size(); i++) {
					accountSet.add(ipList.get(i).getAccount());
				}
				List<String> accounts = new ArrayList<String>(accountSet);
				for(int j = 0; j < accounts.size(); j++) {
					List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
					String to = "";//收件人
					String name = "";//收件人姓名
					String account = accounts.get(j);
					if(account.substring(0, 1).equals("T")) {
						InfoTeacher it = this.infoTeacherMapper.selectByTeaID(account);
						to = it.getEmail();
						name = it.getName();
					} else {
						InfoStudent is = this.infoStudentMapper.selectByLoginID(account);
						to = is.getEmail();
						name = is.getName();
					}
					String content = name+"，您好！您在LMS上有欠罚款未还，账号处于冻结状态，请及时还款！谢谢，祝好！";
					EmailUtil.sendEmail(to, "", "", "LMS管理员", "提示：欠款", content, emailList);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}

	}

}
