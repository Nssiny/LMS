package org.sysu.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sysu.controller.OperationController;
import org.sysu.mail.MailUtil;
import org.sysu.mail.model.*;
import org.sysu.utils.mailComponent.AppUtil;
import org.sysu.utils.mailComponent.BeanUtils;
import org.sysu.utils.mailComponent.StringUtil;

/**
 * 这个类用于发送邮件。
 * <pre> 
 * 构建组：x5-base-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-5-12-上午8:37:37
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class EmailUtil {
	
	private static Logger logger = LoggerFactory.getLogger(OperationController.class);
	
	/**
	 * 发送邮件。
	 * <pre>
	 * 	调用方法：
	 * 	EmailUtil.sendEmail("收信人地址","抄送人地址","秘密抄送","发送人地址","主题","邮件内容");
	 * </pre>
	 * @param to			收件人
	 * @param cc			抄送人
	 * @param bcc			密送人
	 * @param from			发件人
	 * @param subject		标题
	 * @param content		内容
	 * @throws MessagingException 
	 * void
	 */
	/*public static void sendEmail(String to,String cc,String bcc,String from, String subject,String content) throws MessagingException{
		  sendEmail(to,cc,bcc,from, subject,content,null);
	}*/
	
	/**
	 * 发送邮件。
	 * <pre>
	 * 	调用方法：
	 * 	EmailUtil.sendEmail("收信人地址","抄送人地址","秘密抄送","发送人地址","主题","邮件内容",""附件);
	 * </pre>
	 * @param to			收件人
	 * @param cc			抄送人
	 * @param bcc			密送人
	 * @param from			发件人
	 * @param subject		标题
	 * @param content		内容
	 * @throws MessagingException 
	 * void
	 */
	public static void sendEmail(String to,String cc,String bcc,String from, String subject,
			String content,List<MailAttachment> attachments) throws MessagingException{
		
		MailUtil mailSender =(MailUtil) AppUtil.getBean("mailSender");
		Mail mail=new Mail();
		mail.setSubject(subject);
		mail.setSenderName(from);
		mail.setContent(content);
		mail.setReceiverAddresses(to);
		mail.setSenderAddress(from);
		if(BeanUtils.isNotEmpty(attachments)){
		   mail.setMailAttachments(attachments);
		}
		if(StringUtil.isNotEmpty(cc)){
			mail.setCopyToAddresses(cc);
		}
		if(StringUtil.isNotEmpty(bcc)){
			mail.setBcCAddresses(bcc);
		}
		mail.setSendDate(new Date());
		try {
			mailSender.send(mail);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}

}
