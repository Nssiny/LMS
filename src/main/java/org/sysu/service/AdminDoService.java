package org.sysu.service;

import java.util.List;

import org.sysu.pojo.InfoBook;
import org.sysu.pojo.InfoCollection;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.InfoTeacher;
import org.sysu.pojo.Permission;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.pojo.ReaderCategory;
import org.sysu.pojo.ReaderLicense;
import org.sysu.pojo.Role;
import org.sysu.pojo.User;

public interface AdminDoService {
	//读者密码重置、借书证信息修改
	public void readerManagementS(User user, ReaderLicense readerLicense, InfoStudent infoStudent);
	public void readerManagementT(User user, ReaderLicense readerLicense, InfoTeacher infoTeacher);
	//删除书目信息
	public void infoBookDel(String[] ids) throws Exception;
	//增加书目信息
	public void infoBookAdd(InfoBook infoBook) throws Exception;
	//修改书目信息
	public void infoBookEdit(InfoBook infoBook) throws Exception;
	//下拉框显示barcode
	public List<InfoCollection> selectByLikeBarCode(String q);
	//下拉框显示account
	public List<ReaderLicense> selectByLikeAccount(String q);
	//书目信息、馆藏信息查询（by bid）
	public InfoCollection selectByICidFromICandIB(Long icid);
	//图书借阅业务
	public void readerBorrowDo(String account, String[] icidList);
	//删除读者类型
	public void readerCategoryDel(String[] ids) throws Exception;
	//增加读者类型
	public void readerCategoryAdd(ReaderCategory readerCategory) throws Exception;
	//修改读者类型
	public void readerCategoryEdit(ReaderCategory readerCategory) throws Exception;
	//读者归还图书（正常）
	public boolean doReturnBook(String[] ids) throws Exception;
	//图书遗失处理
	public void doReturnBookLose(String[] isLose) throws Exception;
	//图书损坏处理
	public void doReturnBookDamage(String[] isDamage) throws Exception;
	//惩罚管理--读者欠费缴费
	public boolean doInfoPenalty(String[] ipids, String account) throws Exception;
	//预约管理--取消预约
	public void doCancelReservation(String[] rrids) throws Exception;
	//修改馆藏信息
	public void infoCollectionEdit(InfoCollection infoCollection);
	//添加馆藏书目
	public void infoCollectionAdd(InfoCollection infoCollection);
	//根据学生/教职工信息生成初始账号以及初始借阅证并分配权限
	public void doCreateUserAndLicense();
	//定时器：处理超期未归还图书
	public void doOverdues(List<ReaderBorrow> rbList) throws Exception;
	//删除user记录
	public void userDel(String[] ids) throws Exception;
	//添加user记录
	public void userAdd(User user) throws Exception;
	//修改user记录
	public void userEdit(User user) throws Exception;
	//删除role记录
	public void roleDel(String[] ids) throws Exception;
	//添加role记录
	public void roleAdd(Role role) throws Exception;
	//修改role记录
	public void roleEdit(Role role) throws Exception;
	//删除permission记录
	public void permissionDel(String[] ids) throws Exception;
	//添加permission记录
	public void permissionAdd(Permission permission) throws Exception;
	//修改permission记录
	public void permissionEdit(Permission permission) throws Exception;
	//根据user.id, role.id[]修改user_role记录
	public void userRoleChange(String uid, String[] ids) throws Exception;
	//根据role.id, permission.id[]修改role_permission记录
	public void rolePermissionChange(String rid, String[] ids) throws Exception;
}
