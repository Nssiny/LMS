package org.sysu.jobs;

public interface InvalidDojobs {
	//每天凌晨1点执行一次，预约失效处理
	public void reservationInvalid();
	//每天凌晨1点执行一次，借阅超期处理
	public void overdueDo();
	//每天凌晨1点执行一次，提前7天提醒归还
	public void beforeBackWarn();
	//每天凌晨1点执行一次，惩罚通知
	public void penaltyWarn();
}
