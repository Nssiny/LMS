package org.sysu.mail.model;

/**
 * <pre> 
 * 描述：邮件自定义显示名及邮件地址辅助类
 * 构建组：x5-base-core
 * 作者：gjh
 * 邮箱:guojh@jee-soft.cn
 * 日期:2014-10-30-下午3:25:01
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MailAddress {
	
	protected String address = "";

	protected String name = "";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MailAddress() {

	}

	public MailAddress(String address, String name) {
		this.address = address;
		this.name = name;
	}
}
