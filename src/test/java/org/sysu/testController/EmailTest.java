package org.sysu.testController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.dao.InfoStudentMapper;
import org.sysu.dao.InfoTeacherMapper;
import org.sysu.dao.ReaderBorrowMapper;
import org.sysu.mail.model.MailAttachment;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.InfoTeacher;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.utils.EmailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class EmailTest {
	
	private static Logger logger = Logger.getLogger(OperationTest.class);
	
	@Autowired
	private ReaderBorrowMapper readerBorrowMapper;
	@Autowired
	private InfoTeacherMapper infoTeacherMapper;
	@Autowired
	private InfoStudentMapper infoStudentMapper;
	
	@Test
	public void test1() {
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
					String content = name+"，您好！您预约的图书："+titleSet.toString()+"将于 "+dateReturn+" 到期。请及时将图书归还！谢谢，祝好！";
					EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：预约图书遗失", content, emailList);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
}
