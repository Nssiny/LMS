package org.sysu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sysu.dao.InfoBookMapper;
import org.sysu.dao.InfoCollectionMapper;
import org.sysu.dao.InfoDuplicateMapper;
import org.sysu.dao.InfoPenaltyMapper;
import org.sysu.dao.InfoStudentMapper;
import org.sysu.dao.InfoTeacherMapper;
import org.sysu.dao.PermissionMapper;
import org.sysu.dao.ReaderBorrowMapper;
import org.sysu.dao.ReaderCategoryMapper;
import org.sysu.dao.ReaderLicenseMapper;
import org.sysu.dao.ReaderReservationMapper;
import org.sysu.dao.RoleMapper;
import org.sysu.dao.RolePermissionMapper;
import org.sysu.dao.UserMapper;
import org.sysu.dao.UserRoleMapper;
import org.sysu.lucene.MyLucene;
import org.sysu.mail.model.MailAttachment;
import org.sysu.pojo.InfoBook;
import org.sysu.pojo.InfoCollection;
import org.sysu.pojo.InfoDuplicate;
import org.sysu.pojo.InfoPenalty;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.InfoTeacher;
import org.sysu.pojo.Permission;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.pojo.ReaderCategory;
import org.sysu.pojo.ReaderLicense;
import org.sysu.pojo.ReaderReservation;
import org.sysu.pojo.Role;
import org.sysu.pojo.RolePermission;
import org.sysu.pojo.User;
import org.sysu.pojo.UserRole;
import org.sysu.service.AdminDoService;
import org.sysu.utils.EmailUtil;
import org.sysu.utils.MyException;

@Service
public class AdminDoServiceImpl implements AdminDoService {

	@Autowired
	private ReaderLicenseMapper readerLicenseMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private InfoStudentMapper infoStudentMapper;
	@Autowired
	private InfoTeacherMapper infoTeacherMapper;
	@Autowired
	private InfoBookMapper infoBookMapper;
	@Autowired
	private InfoCollectionMapper infoCollectionMapper;
	@Autowired
	private ReaderBorrowMapper readerBorrowMapper;
	@Autowired
	private InfoDuplicateMapper infoDuplicateMapper;
	@Autowired
	private ReaderCategoryMapper readerCategoryMapper;
	@Autowired
	private InfoPenaltyMapper infoPenaltyMapper;
	@Autowired
	private ReaderReservationMapper readerReservationMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerManagementS(User user, ReaderLicense readerLicense, InfoStudent infoStudent) {
		this.userMapper.updateByPrimaryKeySelective(user);
		this.readerLicenseMapper.updateByPrimaryKeySelective(readerLicense);
		this.infoStudentMapper.updateByPrimaryKeySelective(infoStudent);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerManagementT(User user, ReaderLicense readerLicense, InfoTeacher infoTeacher) {
		this.userMapper.updateByPrimaryKeySelective(user);
		this.readerLicenseMapper.updateByPrimaryKeySelective(readerLicense);
		this.infoTeacherMapper.updateByPrimaryKeySelective(infoTeacher);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void infoBookDel(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			this.infoBookMapper.deleteByPrimaryKey(id);
			//删除索引
			MyLucene myLucene = new MyLucene();
			myLucene.deleteIndex(id+"");
			//外键牵制，无法删除带有子记录的infoBook记录 所以不用其他操作
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void infoBookAdd(InfoBook infoBook) throws Exception {
		this.infoBookMapper.insertSelective(infoBook);
		//添加索引
		MyLucene myLucene = new MyLucene();
		myLucene.addIndex(infoBook);
		//添加info_duplicate记录
		InfoDuplicate infoDuplicate = new InfoDuplicate();
		infoDuplicate.setBid(infoBook.getId());
		infoDuplicate.setGmtCreate(new Date());
		this.infoDuplicateMapper.insertSelective(infoDuplicate);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void infoBookEdit(InfoBook infoBook) throws Exception {
		this.infoBookMapper.updateByPrimaryKeySelective(infoBook);
		//更新索引
		MyLucene myLucene = new MyLucene();
		myLucene.updateIndex(infoBook);
	}
	
	@Override
	public List<InfoCollection> selectByLikeBarCode(String q) {
		return this.infoCollectionMapper.selectByLikeBarCode(q);
	}
	
	@Override
	public List<ReaderLicense> selectByLikeAccount(String q) {
		return this.readerLicenseMapper.selectByLikeAccount(q);
	}
	
	@Override
	public InfoCollection selectByICidFromICandIB(Long icid) {
		return this.infoCollectionMapper.selectByICidFromICandIB(icid);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerBorrowDo(String account, String[] icidList) {
		for (int i = 0; i < icidList.length; i++) {
			InfoCollection infoCollection = this.infoCollectionMapper.selectByPrimaryKey(Long.parseLong(icidList[i]));
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(account);
			//插入reader_borrow记录
			ReaderBorrow readerBorrow = new ReaderBorrow();
			readerBorrow.setBarCode(infoCollection.getBarCode());
			readerBorrow.setAccount(account);
			readerBorrow.setDateBorrow(new Date());
			GregorianCalendar gc =new GregorianCalendar();
			gc.setTime(new Date());
			gc.add(5, readerLicense.getReaderCategory().getBookBorrowTime());
			readerBorrow.setDateReturn(gc.getTime());
			readerBorrow.setGmtCreate(new Date());
			this.readerBorrowMapper.insertSelective(readerBorrow);
			//更新info_collection的is_borrowing
			InfoCollection ic = new InfoCollection();
			ic.setId(Long.parseLong(icidList[i]));
			ic.setIsBorrowing((byte) 1);
			this.infoCollectionMapper.updateByPrimaryKeySelective(ic);
			//更新info_duplicate的num_borrow
			InfoDuplicate infoDuplicate = this.infoDuplicateMapper.selectByBid(infoCollection.getBid());
			InfoDuplicate id = new InfoDuplicate();
			id.setId(infoDuplicate.getId());
			id.setNumBorrow(infoDuplicate.getNumBorrow()+1);
			this.infoDuplicateMapper.updateByPrimaryKeySelective(id);
			//更新reader_license的borrowed
			ReaderLicense rl = new ReaderLicense();
			rl.setId(readerLicense.getId());
			rl.setBorrowed(readerLicense.getBorrowed()+1);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rl);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerCategoryDel(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			this.readerCategoryMapper.deleteByPrimaryKey(id);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerCategoryAdd(ReaderCategory readerCategory) throws Exception {
		this.readerCategoryMapper.insertSelective(readerCategory);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void readerCategoryEdit(ReaderCategory readerCategory) throws Exception {
		this.readerCategoryMapper.updateByPrimaryKeySelective(readerCategory);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean doReturnBook(String[] ids) throws Exception {
		Boolean reasult = false;
		for(int i = 0; i < ids.length; i++) {
			Long rbid = Long.parseLong(ids[i]);
			ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByRbid1(rbid);
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(readerBorrow.getAccount());
			int penaltyFlag = 1;
			//检验是否超期还书
			GregorianCalendar dateReturn = new GregorianCalendar();
			dateReturn.setTime(readerBorrow.getDateReturn());
			dateReturn.add(5, 1);
			if(dateReturn.getTime().compareTo(new Date()) != 1 || readerBorrow.getPenaltyFlag() == 2) {
				//超期还书
				reasult = true;
				penaltyFlag = 2;
				//检验是否已经有惩罚记录
				InfoPenalty infoPenalty = new InfoPenalty();
				infoPenalty = this.infoPenaltyMapper.selectByRbid(rbid);
				if(infoPenalty == null) {
					//插入惩罚记录
					System.out.println("记录为空");
					InfoPenalty ipInsert = new InfoPenalty();
					ipInsert.setRbid(rbid);
					ipInsert.setAccount(readerBorrow.getAccount());
					ipInsert.setDatePenalty(new Date());
					ipInsert.setPricePenalty(2);
					ipInsert.setGmtCreate(new Date());
					this.infoPenaltyMapper.insertSelective(ipInsert);
				}
				//更新reader_License的惩罚记录
				ReaderLicense rlUpdate1 = new ReaderLicense();
				rlUpdate1.setId(readerLicense.getId());
				rlUpdate1.setPenaltyCount(readerLicense.getPenaltyCount()+1);
				rlUpdate1.setStatus((byte) 2);
				this.readerLicenseMapper.updateByPrimaryKeySelective(rlUpdate1);
			}
			//更新reader_borrow
			ReaderBorrow rbUpdate = new ReaderBorrow();
			rbUpdate.setId(rbid);
			rbUpdate.setDateBack(new Date());
			rbUpdate.setPenaltyFlag(penaltyFlag);
			this.readerBorrowMapper.updateByPrimaryKeySelective(rbUpdate);
			//更新info_collection
			InfoCollection icUpdate = new InfoCollection();
			icUpdate.setId(readerBorrow.getInfoCollection().getId());
			icUpdate.setIsBorrowing((byte) 0);
			this.infoCollectionMapper.updateByPrimaryKeySelective(icUpdate);
			//更新reader_license的借阅记录
			ReaderLicense rlUpdate= new ReaderLicense();
			rlUpdate.setId(readerLicense.getId());
			rlUpdate.setBorrowed(readerLicense.getBorrowed()-1);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rlUpdate);
			//更新info_duplicate
			InfoDuplicate infoDuplicate = this.infoDuplicateMapper.selectByBid(readerBorrow.getInfoCollection().getBid());
			InfoDuplicate idUpdate = new InfoDuplicate();
			idUpdate.setId(infoDuplicate.getId());
			idUpdate.setNumBorrow(infoDuplicate.getNumBorrow()-1);
			this.infoDuplicateMapper.updateByPrimaryKeySelective(idUpdate);
			//检验是否被预约，若是，则更新预约记录，通知预约读者图书已到馆
			ReaderReservation readerReservation = new ReaderReservation();
			readerReservation = this.readerReservationMapper.selectByBarCodeAndEffective(readerBorrow.getBarCode(), (byte) 1);
			if(readerReservation != null) {
				ReaderReservation rrUpdate = new ReaderReservation();
				rrUpdate.setId(readerReservation.getId());
				rrUpdate.setDateBack(new Date());
				GregorianCalendar dateEffective = new GregorianCalendar();
				dateEffective.setTime(new Date());
				dateEffective.add(5, 7);
				rrUpdate.setDateEffective(dateEffective.getTime());
				this.readerReservationMapper.updateByPrimaryKeySelective(rrUpdate);
				//邮件通知
				List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
				String to = "";//收件人
				String name = "";//收件人姓名
				String account = readerReservation.getAccount();
				if(account.substring(0, 1).equals("T")) {
					InfoTeacher it = this.infoTeacherMapper.selectByTeaID(account);
					to = it.getEmail();
					name = it.getName();
				} else {
					InfoStudent is = this.infoStudentMapper.selectByLoginID(account);
					to = is.getEmail();
					name = is.getName();
				}
				InfoBook ib = this.infoBookMapper.selectByPrimaryKey(readerBorrow.getInfoCollection().getBid());
				String content = name+"，您好！您预约的图书：《"+ib.getTitle()+"》已经到馆。请于7天内到图书馆借阅，否则预约将自动取消！谢谢，祝好！";
				EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：预约图书到馆", content, emailList);
			}
		}
		return reasult;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doReturnBookLose(String[] isLose) throws Exception {
		for(int i = 0; i < isLose.length; i++) {
			Long rbid = Long.parseLong(isLose[i]);
			ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByPrimaryKeyFromBBC(rbid);
			//更新reader_borrow: date_back = new Date(), penalty_flag = 4
			ReaderBorrow rbUpdate = new ReaderBorrow();
			rbUpdate.setId(rbid);
			rbUpdate.setDateBack(new Date());
			rbUpdate.setPenaltyFlag(4);
			this.readerBorrowMapper.updateByPrimaryKeySelective(rbUpdate);
			//检验是否已经有惩罚记录，若无则添加，若有则代表已经超期，更新惩罚记录
			InfoPenalty infoPenalty = new InfoPenalty();
			infoPenalty = this.infoPenaltyMapper.selectByRbid(rbid);
			if(infoPenalty == null) {
				InfoPenalty ipInsert = new InfoPenalty();
				ipInsert.setRbid(rbid);
				ipInsert.setAccount(readerBorrow.getAccount());
				ipInsert.setDatePenalty(new Date());
				ipInsert.setPricePenalty(readerBorrow.getInfoBook().getPrice().intValue());
				ipInsert.setGmtCreate(new Date());
				this.infoPenaltyMapper.insertSelective(ipInsert);
			} else {
				InfoPenalty ipUpdate = new InfoPenalty();
				ipUpdate.setId(infoPenalty.getId());
				ipUpdate.setDatePenalty(new Date());
				ipUpdate.setPricePenalty(readerBorrow.getInfoBook().getPrice().intValue());
				this.infoPenaltyMapper.updateByPrimaryKeySelective(ipUpdate);
			}
			//更新reader_license: penalty_count+1, borrowed-1,status=2
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(readerBorrow.getAccount());
			ReaderLicense rlUpdate = new ReaderLicense();
			rlUpdate.setId(readerLicense.getId());
			rlUpdate.setPenaltyCount(readerLicense.getPenaltyCount() + 1);
			rlUpdate.setBorrowed(readerLicense.getBorrowed() - 1);
			rlUpdate.setStatus((byte) 2);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rlUpdate);
			//更新info_collection: is_borrowing = 0, status = 3;
			ReaderBorrow rb = this.readerBorrowMapper.selectByRbid1(rbid);
			InfoCollection icUpdate = new InfoCollection();
			icUpdate.setId(rb.getInfoCollection().getId());
			icUpdate.setIsBorrowing((byte) 0);
			icUpdate.setStatus((byte) 3);
			this.infoCollectionMapper.updateByPrimaryKeySelective(icUpdate);
			//更新info_duplicate: num_duplicate - 1, num_borrow - 1
			InfoDuplicate infoDuplicate = this.infoDuplicateMapper.selectByBid(rb.getInfoCollection().getBid());
			InfoDuplicate idUpdate = new InfoDuplicate();
			idUpdate.setId(infoDuplicate.getId());
			idUpdate.setNumBorrow(infoDuplicate.getNumBorrow() - 1);
			idUpdate.setNumDuplicate(infoDuplicate.getNumDuplicate() - 1);
			this.infoDuplicateMapper.updateByPrimaryKeySelective(idUpdate);
			//检验是否被预约，若是，则更新预约记录，通知预约读者图书已遗失，预约记录自动取消
			ReaderReservation readerReservation = new ReaderReservation();
			readerReservation = this.readerReservationMapper.selectByBarCodeAndEffective(readerBorrow.getBarCode(), (byte) 1);
			if(readerReservation != null) {
				ReaderReservation rrUpdate = new ReaderReservation();
				rrUpdate.setId(readerReservation.getId());
				rrUpdate.setDateBack(new Date());
				rrUpdate.setDateEffective(new Date());
				rrUpdate.setIsEffective((byte) 0);
				this.readerReservationMapper.updateByPrimaryKeySelective(rrUpdate);
				//邮件通知
				List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
				String to = "";//收件人
				String name = "";//收件人姓名
				String account = readerReservation.getAccount();
				if(account.substring(0, 1).equals("T")) {
					InfoTeacher it = this.infoTeacherMapper.selectByTeaID(account);
					to = it.getEmail();
					name = it.getName();
				} else {
					InfoStudent is = this.infoStudentMapper.selectByLoginID(account);
					to = is.getEmail();
					name = is.getName();
				}
				String content = name+"，您好！您预约的图书：《"+readerBorrow.getInfoBook().getTitle()+"》已经遗失。该预约记录系统已经自动取消！谢谢，祝好！";
				EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：预约图书遗失", content, emailList);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doReturnBookDamage(String[] isDamage) throws Exception {
		for(int i = 0; i < isDamage.length; i++) {
			Long rbid = Long.parseLong(isDamage[i]);
			ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByPrimaryKeyFromBBC(rbid);
			//更新reader_borrow: date_back = new Date(), penalty_flag = 3
			ReaderBorrow rbUpdate = new ReaderBorrow();
			rbUpdate.setId(rbid);
			rbUpdate.setDateBack(new Date());
			rbUpdate.setPenaltyFlag(3);
			this.readerBorrowMapper.updateByPrimaryKeySelective(rbUpdate);
			//检验是否已经有惩罚记录，若无则添加，若有则代表已经超期，更新惩罚记录
			InfoPenalty infoPenalty = new InfoPenalty();
			infoPenalty = this.infoPenaltyMapper.selectByRbid(rbid);
			if(infoPenalty == null) {
				InfoPenalty ipInsert = new InfoPenalty();
				ipInsert.setRbid(rbid);
				ipInsert.setAccount(readerBorrow.getAccount());
				ipInsert.setDatePenalty(new Date());
				ipInsert.setPricePenalty(readerBorrow.getInfoBook().getPrice().intValue());
				ipInsert.setGmtCreate(new Date());
				this.infoPenaltyMapper.insertSelective(ipInsert);
			} else {
				InfoPenalty ipUpdate = new InfoPenalty();
				ipUpdate.setId(infoPenalty.getId());
				ipUpdate.setDatePenalty(new Date());
				ipUpdate.setPricePenalty(readerBorrow.getInfoBook().getPrice().intValue());
				this.infoPenaltyMapper.updateByPrimaryKeySelective(ipUpdate);
			}
			//更新reader_license: penalty_count+1, borrowed-1,status=2
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(readerBorrow.getAccount());
			ReaderLicense rlUpdate = new ReaderLicense();
			rlUpdate.setId(readerLicense.getId());
			rlUpdate.setPenaltyCount(readerLicense.getPenaltyCount() + 1);
			rlUpdate.setBorrowed(readerLicense.getBorrowed() - 1);
			rlUpdate.setStatus((byte) 2);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rlUpdate);
			//更新info_collection: is_borrowing = 0, status = 2;
			ReaderBorrow rb = this.readerBorrowMapper.selectByRbid1(rbid);
			InfoCollection icUpdate = new InfoCollection();
			icUpdate.setId(rb.getInfoCollection().getId());
			icUpdate.setIsBorrowing((byte) 0);
			icUpdate.setStatus((byte) 2);
			this.infoCollectionMapper.updateByPrimaryKeySelective(icUpdate);
			//更新info_duplicate: num_duplicate - 1, num_borrow - 1
			InfoDuplicate infoDuplicate = this.infoDuplicateMapper.selectByBid(rb.getInfoCollection().getBid());
			InfoDuplicate idUpdate = new InfoDuplicate();
			idUpdate.setId(infoDuplicate.getId());
			idUpdate.setNumBorrow(infoDuplicate.getNumBorrow() - 1);
			idUpdate.setNumDuplicate(infoDuplicate.getNumDuplicate() - 1);
			this.infoDuplicateMapper.updateByPrimaryKeySelective(idUpdate);
			//检验是否被预约，若是，则更新预约记录
			ReaderReservation readerReservation = new ReaderReservation();
			readerReservation = this.readerReservationMapper.selectByBarCodeAndEffective(readerBorrow.getBarCode(), (byte) 1);
			if(readerReservation != null) {
				ReaderReservation rrUpdate = new ReaderReservation();
				rrUpdate.setId(readerReservation.getId());
				rrUpdate.setDateBack(new Date());
				rrUpdate.setDateEffective(new Date());
				rrUpdate.setIsEffective((byte) 0);
				this.readerReservationMapper.updateByPrimaryKeySelective(rrUpdate);
				//邮件通知
				List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
				String to = "";//收件人
				String name = "";//收件人姓名
				String account = readerReservation.getAccount();
				if(account.substring(0, 1).equals("T")) {
					InfoTeacher it = this.infoTeacherMapper.selectByTeaID(account);
					to = it.getEmail();
					name = it.getName();
				} else {
					InfoStudent is = this.infoStudentMapper.selectByLoginID(account);
					to = is.getEmail();
					name = is.getName();
				}
				String content = name+"，您好！您预约的图书：《"+readerBorrow.getInfoBook().getTitle()+"》已经损坏。该预约记录系统已经自动取消！谢谢，祝好！";
				EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：预约图书损坏", content, emailList);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean doInfoPenalty(String[] ipids, String account) throws Exception {
		Boolean flag = false;
		for(int i = 0; i < ipids.length; i++) {
			Long ipid = Long.parseLong(ipids[i]);
			InfoPenalty infoPenalty = this.infoPenaltyMapper.selectByPrimaryKey(ipid);
			//更新info_penalty: date_pay = price_penalty, date_pay = new Date()
			InfoPenalty ipUpdate = new InfoPenalty();
			ipUpdate.setId(ipid);
			ipUpdate.setDatePay(new Date());
			ipUpdate.setPricePay(infoPenalty.getPricePenalty());
			this.infoPenaltyMapper.updateByPrimaryKeySelective(ipUpdate);
		}
		//若所有惩罚记录已解决，则更新reader_license: status = 1
		List<InfoPenalty> infoPenaltys = new LinkedList<InfoPenalty>();
		infoPenaltys = this.infoPenaltyMapper.selectByAccount0(account);
		if(infoPenaltys.size() == 0) {
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(account);
			ReaderLicense rlUpdate = new ReaderLicense();
			rlUpdate.setId(readerLicense.getId());
			rlUpdate.setStatus((byte) 1);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rlUpdate);
			flag = true;
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doCancelReservation(String[] rrids) throws Exception {
		for(int i = 0; i < rrids.length; i++) {
			Long rrid = Long.parseLong(rrids[i]);
			ReaderReservation readerReservation = this.readerReservationMapper.selectByPrimaryKey(rrid);
			InfoCollection infoCollection = this.infoCollectionMapper.selectByBarCode(readerReservation.getBarCode());
			ReaderReservation updateRR = new ReaderReservation();
			updateRR.setId(rrid);
			updateRR.setIsEffective((byte) 0);
			this.readerReservationMapper.updateByPrimaryKeySelective(updateRR);
			InfoCollection updateIC = new InfoCollection();
			updateIC.setId(infoCollection.getId());
			updateIC.setIsReservation((byte) 0);
			this.infoCollectionMapper.updateByPrimaryKeySelective(updateIC);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void infoCollectionEdit(InfoCollection infoCollection) {
		InfoCollection old = this.infoCollectionMapper.selectByPrimaryKey(infoCollection.getId());
		//检查bid是否有更改，若有则修改复本信息，old复本-，new复本+
		if (old.getBid() != infoCollection.getBid()) {
			//bid有更改
			//检查图书是否在流通，若是则不允许更改，抛出异常
			if(old.getIsBorrowing() == 1 || old.getIsReservation() == 1) {
				throw new MyException("该书尚在流通，不允许更改bid");
			} else {
				InfoDuplicate oldID = this.infoDuplicateMapper.selectByBid(old.getBid());
				InfoDuplicate oldUP = new InfoDuplicate();
				oldUP.setId(oldID.getId());
				oldUP.setNumDuplicate(oldID.getNumDuplicate()-1);
				this.infoDuplicateMapper.updateByPrimaryKeySelective(oldUP);
				InfoDuplicate newID = this.infoDuplicateMapper.selectByBid(infoCollection.getBid());
				InfoDuplicate newUP = new InfoDuplicate();
				newUP.setId(newID.getId());
				newUP.setNumDuplicate(newID.getNumDuplicate()+1);
				this.infoDuplicateMapper.updateByPrimaryKeySelective(newUP);
			}
		}
		this.infoCollectionMapper.updateByPrimaryKeySelective(infoCollection);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void infoCollectionAdd(InfoCollection infoCollection) {
		//添加馆藏书目记录
		this.infoCollectionMapper.insertSelective(infoCollection);
		//更新复本信息
		InfoDuplicate oldID = this.infoDuplicateMapper.selectByBid(infoCollection.getBid());
		InfoDuplicate newID = new InfoDuplicate();
		newID.setId(oldID.getId());
		newID.setNumDuplicate(oldID.getNumDuplicate()+1);
		this.infoDuplicateMapper.updateByPrimaryKeySelective(newID);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doCreateUserAndLicense() {
		List<InfoStudent> isList = new LinkedList<InfoStudent>();
		isList = this.infoStudentMapper.selectByNotExistsUser();
		if(isList.size() != 0) {
			for(int i = 0; i < isList.size(); i++) {
				User user = new User();
				user.setLoginId(isList.get(i).getStuId());
				user.setPassword("123");
				this.userMapper.insertSelective(user);
				UserRole ur1 = new UserRole();
				ur1.setUserId(user.getId());
				ur1.setRoleId((long) 2);
				this.userRoleMapper.insertSelective(ur1);
				UserRole ur2 = new UserRole();
				ur2.setUserId(user.getId());
				ur2.setRoleId((long) 6);
				this.userRoleMapper.insertSelective(ur2);
			}
		}
		List<InfoStudent> isList1 = new LinkedList<InfoStudent>();
		isList1 = this.infoStudentMapper.selectByNotExistsLicense();
		if(isList1.size() != 0) {
			for(int i = 0; i < isList1.size(); i++) {
				ReaderLicense rl = new ReaderLicense();
				rl.setAccount(isList1.get(i).getStuId());
				rl.setCategoryId((long) 1);
				rl.setGmtAllocate(isList1.get(i).getYear());
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(isList1.get(i).getYear());
				gc.add((GregorianCalendar.YEAR), isList1.get(i).getSchoolYear());
				rl.setGmtExpire(gc.getTime());
				rl.setGmtCreate(new Date());
				this.readerLicenseMapper.insertSelective(rl);
			}
		}
		List<InfoTeacher> itList = new LinkedList<InfoTeacher>();
		itList = this.infoTeacherMapper.selectByNotExistsUser();
		if(itList.size() != 0) {
			for(int i = 0; i < itList.size(); i++) {
				User user = new User();
				user.setLoginId(itList.get(i).getTeaId());
				user.setPassword("123");
				this.userMapper.insertSelective(user);
				UserRole ur1 = new UserRole();
				ur1.setUserId(user.getId());
				ur1.setRoleId((long) 2);
				this.userRoleMapper.insertSelective(ur1);
				UserRole ur2 = new UserRole();
				ur2.setUserId(user.getId());
				ur2.setRoleId((long) 6);
				this.userRoleMapper.insertSelective(ur2);
			}
		}
		List<InfoTeacher> itList1 = new LinkedList<InfoTeacher>();
		itList1 = this.infoTeacherMapper.selectByNotExistsLicense();
		if(itList1.size() != 0) {
			for(int i = 0; i < itList1.size(); i++) {
				ReaderLicense rl = new ReaderLicense();
				rl.setAccount(itList1.get(i).getTeaId());
				rl.setCategoryId((long) 4);
				rl.setGmtAllocate(new Date());
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(new Date());
				gc.add((GregorianCalendar.YEAR), 3);
				rl.setGmtExpire(gc.getTime());
				rl.setGmtCreate(new Date());
				this.readerLicenseMapper.insertSelective(rl);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void doOverdues(List<ReaderBorrow> rbList) throws Exception {
		for(int i = 0; i < rbList.size(); i++) {
			//reader_borrow: penalty_flag = 2
			ReaderBorrow rb = new ReaderBorrow();
			rb.setId(rbList.get(i).getId());
			rb.setPenaltyFlag(2);
			this.readerBorrowMapper.updateByPrimaryKeySelective(rb);
			//reader_license: penalty_count + 1, status = 2
			ReaderLicense readerLicense = this.readerLicenseMapper.selectByAccount(rbList.get(i).getAccount());
			ReaderLicense rl = new ReaderLicense();
			rl.setId(readerLicense.getId());
			rl.setPenaltyCount(readerLicense.getPenaltyCount() + 1);
			rl.setStatus((byte) 2);
			this.readerLicenseMapper.updateByPrimaryKeySelective(rl);
			//insert info_penalty
			//检验是否已经有惩罚记录
			InfoPenalty infoPenalty = new InfoPenalty();
			infoPenalty = this.infoPenaltyMapper.selectByRbid(rbList.get(i).getId());
			if(infoPenalty == null) {
				//插入惩罚记录
				InfoPenalty ipInsert = new InfoPenalty();
				ipInsert.setRbid(rbList.get(i).getId());
				ipInsert.setAccount(rbList.get(i).getAccount());
				ipInsert.setDatePenalty(new Date());
				ipInsert.setPricePenalty(2);
				ipInsert.setGmtCreate(new Date());
				this.infoPenaltyMapper.insertSelective(ipInsert);
			}
			//检验图书是否被预约，若是，则通值预约读者该书超期未还，请读者等待图书到馆通知
			ReaderReservation readerReservation = new ReaderReservation();
			readerReservation = this.readerReservationMapper.selectByBarCodeAndEffective(rbList.get(i).getBarCode(), (byte) 1);
			if(readerReservation != null) {
				//邮件通知
				List<MailAttachment> emailList = new LinkedList<MailAttachment>(); //附件
				String to = "";//收件人
				String name = "";//收件人姓名
				String account = readerReservation.getAccount();
				if(account.substring(0, 1).equals("T")) {
					InfoTeacher it = this.infoTeacherMapper.selectByTeaID(account);
					to = it.getEmail();
					name = it.getName();
				} else {
					InfoStudent is = this.infoStudentMapper.selectByLoginID(account);
					to = is.getEmail();
					name = is.getName();
				}
				ReaderBorrow readerBorrow = this.readerBorrowMapper.selectByPrimaryKeyFromBBC(rbList.get(i).getId());
				String content = name+"，您好！您预约的图书：《"+readerBorrow.getInfoBook().getTitle()+"》由于借阅者超期未归还，请耐心等候图书到馆通知！谢谢，祝好！";
				EmailUtil.sendEmail(to, "", "", "LMS管理员", "通知：预约图书超期未还，请耐心等候。", content, emailList);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void userDel(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			this.userMapper.deleteByPrimaryKey(id);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void userAdd(User user) throws Exception {
		this.userMapper.insertSelective(user);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void userEdit(User user) throws Exception {
		this.userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void roleDel(String[] ids) throws Exception{
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			this.roleMapper.deleteByPrimaryKey(id);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void roleAdd(Role role) throws Exception {
		this.roleMapper.insertSelective(role);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void roleEdit(Role role) throws Exception {
		this.roleMapper.updateByPrimaryKeySelective(role);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void permissionDel(String[] ids) throws Exception{
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			this.permissionMapper.deleteByPrimaryKey(id);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void permissionAdd(Permission permission) throws Exception {
		this.permissionMapper.insertSelective(permission);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void permissionEdit(Permission permission) throws Exception {
		this.permissionMapper.updateByPrimaryKeySelective(permission);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void userRoleChange(String uid, String[] ids) throws Exception {
		Long userId = Long.parseLong(uid);
		List<UserRole> userRoleList = this.userRoleMapper.selectByLoginId(userId);
		//删除旧记录
		if(userRoleList != null) {
			for (UserRole userRole : userRoleList) {
				this.userRoleMapper.deleteByPrimaryKey(userRole.getId());
			}
		}
		//添加新记录
		if(ids != null) {
			for (int i = 0; i < ids.length; i++) {
				UserRole ur = new UserRole();
				ur.setUserId(userId);
				ur.setRoleId(Long.parseLong(ids[i]));
				ur.setGmtCreate(new Date());
				this.userRoleMapper.insertSelective(ur);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void rolePermissionChange(String rid, String[] ids) throws Exception {
		Long roleId = Long.parseLong(rid);
		List<RolePermission> rolePermissionList = this.rolePermissionMapper.selectByRoleId(roleId);
		//删除旧记录
		if(rolePermissionList != null) {
			for(RolePermission rp : rolePermissionList) {
				this.rolePermissionMapper.deleteByPrimaryKey(rp.getId());
			}
		}
		//添加新记录
		if(ids != null) {
			for(int i = 0; i < ids.length; i++) {
				Long pid = Long.parseLong(ids[i]);
				Permission p = this.permissionMapper.selectByPrimaryKey(pid);
				Role r = this.roleMapper.selectByPrimaryKey(roleId);
				RolePermission rp = new RolePermission();
				rp.setRoleId(roleId);
				rp.setRoleName(r.getName());
				rp.setPermissionId(pid);
				rp.setPermissionName(p.getName());
				rp.setGmtCreate(new Date());
				this.rolePermissionMapper.insertSelective(rp);
			}
		}
	}
}
