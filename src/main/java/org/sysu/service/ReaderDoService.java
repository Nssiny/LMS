package org.sysu.service;

import org.sysu.pojo.ReaderCollection;

public interface ReaderDoService {
	//读者预约
	public void rreservationdo(String account, String barCode);
	//读者续借
	public void rreborrowdo(String account, Long rbid);
	//读者取消预约
	public void rcancelReservationdo(Long rrid);
	//读者添加收藏
	public void readerCollectionAdd(ReaderCollection readerCollection);
	//读者删除收藏
	public void readerCollectionDel(Long rcid);
}
