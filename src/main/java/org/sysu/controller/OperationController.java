package org.sysu.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.lucene.MyLucene;
import org.sysu.pojo.InfoBook;
import org.sysu.pojo.InfoCollection;
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
import org.sysu.service.InfoBookService;
import org.sysu.service.InfoCollectionService;
import org.sysu.service.InfoPenaltyService;
import org.sysu.service.InfoStudentService;
import org.sysu.service.InfoTeacherService;
import org.sysu.service.PermissionService;
import org.sysu.service.ReaderBorrowService;
import org.sysu.service.ReaderCategoryService;
import org.sysu.service.ReaderLicenseService;
import org.sysu.service.ReaderReservationService;
import org.sysu.service.RolePermissionService;
import org.sysu.service.RoleService;
import org.sysu.service.UserRoleService;
import org.sysu.service.UserService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class OperationController {
	private static Logger logger = LoggerFactory.getLogger(OperationController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private ReaderLicenseService readerLicenseService;
	@Resource
	private InfoStudentService infoStudentService;
	@Resource
	private InfoTeacherService infoTeacherService;
	@Resource
	private AdminDoService adminDoService;
	@Resource
	private InfoCollectionService infoCollectionService;
	@Resource
	private ReaderReservationService readerReservationService;
	@Resource
	private ReaderCategoryService readerCategoryService;
	@Resource
	private InfoBookService infoBookService;
	@Resource
	private ReaderBorrowService readerBorrowService;
	@Resource
	private InfoPenaltyService infoPenaltyService;
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RolePermissionService rolePermissionService;
	
	@RequestMapping("/adminIndex")
	public String showAdminIndex(HttpServletRequest request, Model model) {
		String reasult = "admin_index";
		return reasult;
	}
	
	@RequestMapping("/ReaderManagement")
	public String showAdminReader(HttpServletRequest request, Model model) {
		return "admin_reader";
	}
	
	@RequestMapping("/BorrowManagement")
	public String showAdminBorrow() {
		return "admin_borrow";
	}
	
	@RequestMapping("/InfoBookManagement")
	public String showAdminInfoBook() {
		return "admin_infoBook";
	}
	
	@RequestMapping("/ReaderCategoryManagement")
	public String showAdminReaderCategory() {
		return "admin_readerCategory";
	}
	
	@RequestMapping("/ReturnManagement")
	public String showAdminReturn() {
		return "admin_return";
	}
	
	@RequestMapping("/ReturnErrorManagement")
	public String showAdminReturnError() {
		return "admin_returnError";
	}
	
	@RequestMapping("/InfoPenaltyManagement")
	public String showAdminInfoPenalty() {
		return "admin_infoPenalty";
	}
	
	@RequestMapping("/ReservationManagement")
	public String showAdminReservation() {
		return "admin_reservation";
	}
	
	@RequestMapping("/InfoCollectionManagement")
	public String showAdminInfoCollection() {
		return "admin_infoCollection";
	}
	
	@RequestMapping("/LuceneManagement")
	public String showAdminLucene() {
		return "admin_lucene";
	}
	
	@RequestMapping("/NewAccountManagement")
	public String showAdminNewAccount() {
		return "admin_newaccount";
	}
	
	@RequestMapping("/UserManagement")
	public String showAdminUser() {
		return "admin_user";
	}
	
	@RequestMapping("/RoleManagement")
	public String showAdminRole() {
		return "admin_role";
	}
	@RequestMapping("/PermissionManagement")
	public String showAdminPermission() {
		return "admin_permission";
	}
	
	@RequestMapping("/ReaderManagement/rmShow.do")
	public @ResponseBody Object rmidFormdo(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		int reasult = 0;
		String login_id = request.getParameter("login_id");
		User user = this.userService.selectByLoginID(login_id);
		if(user == null) {
			reasult = 0;
		} else {
			ReaderLicense readerLicense = this.readerLicenseService.selectByAccount(login_id);
			jo.put("user", user);
			if(readerLicense == null) {
				reasult = 1;
			} else {
				if(login_id.substring(0, 1).equals("S")) {
					InfoStudent infoStudent = this.infoStudentService.selectByLoginID(login_id);
					jo.put("userInfo", infoStudent);
				} else if(login_id.substring(0, 1).equals("T")) {
					InfoTeacher infoTeacher = this.infoTeacherService.selectByTeaID(login_id);
					jo.putIfAbsent("userInfo", infoTeacher);
				}
				jo.put("readerLicense", readerLicense);
				reasult = 2;
			}
		}
		jo.put("reasult", reasult);
		System.out.println(jo.toString());
		return jo;
	}
	
	//@SuppressWarnings("null")
	@RequestMapping("/ReaderManagement/rmidForm.do")
	public @ResponseBody Object updateReader(@RequestParam("rmLoginID") String rmLoginID,
			@RequestParam("rmName") String rmName, @RequestParam("rmPassword") String rmPassword, @RequestParam("rmPhone") String rmPhone,
			@RequestParam("rmEmail") String rmEmail, @RequestParam("rmCategory") String rmCategory, @RequestParam("rmAllocate") String rmAllocate,
			@RequestParam("rmExpire") String rmExpire, @RequestParam("rmPenalty") String rmPenalty, @RequestParam("rmBorrowed") String rmBorrowed,
			@RequestParam("rmStatus") String rmStatus) throws ParseException {
		JSONObject jo = new JSONObject();
		int reasult = 0;
		//User
		Long userID = this.userService.selectByLoginID(rmLoginID).getId();
		User user = new User();
		user.setId(userID);
		user.setPassword(rmPassword);
		//System.out.println("测试User："+JSON.toJSONString(user));
		//ReaderLicense
		Long readerLicenseID = this.readerLicenseService.selectByAccount(rmLoginID).getId();
		ReaderLicense readerLicense = new ReaderLicense();
		readerLicense.setId(readerLicenseID);
		readerLicense.setCategoryId(Long.parseLong(rmCategory));
		readerLicense.setGmtAllocate(new SimpleDateFormat("yyyy-MM-dd").parse(rmAllocate));
		readerLicense.setGmtExpire(new SimpleDateFormat("yyyy-MM-dd").parse(rmExpire));
		readerLicense.setPenaltyCount(Integer.parseInt(rmPenalty));
		readerLicense.setBorrowed(Integer.parseInt(rmBorrowed));
		readerLicense.setStatus((byte) Integer.parseInt(rmStatus));
		//System.out.println("测试readerLicense："+JSON.toJSONString(readerLicense));
		//Teacher or Student
		try {
			if(rmLoginID.substring(0, 1).equals("S")) {
				Long infoStudentID = this.infoStudentService.selectByLoginID(rmLoginID).getId();
				InfoStudent infoStudent = new InfoStudent();
				infoStudent.setId(infoStudentID);
				infoStudent.setName(rmName);
				infoStudent.setPhone(rmPhone);
				infoStudent.setEmail(rmEmail);
				System.out.println("测试infoStudent："+JSON.toJSONString(infoStudent));
				this.adminDoService.readerManagementS(user, readerLicense, infoStudent);
				reasult = 1;
			} else {
				Long infoTeacherID = this.infoTeacherService.selectByTeaID(rmLoginID).getId();
				InfoTeacher infoTeacher = new InfoTeacher();
				infoTeacher.setId(infoTeacherID);
				infoTeacher.setName(rmName);
				infoTeacher.setPhone(rmPhone);
				infoTeacher.setEmail(rmEmail);
				System.out.println("测试infoTeacher："+JSON.toJSONString(infoTeacher));
				this.adminDoService.readerManagementT(user, readerLicense, infoTeacher);
				reasult = 1;
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			reasult = 0;
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/jqGridData")
	public @ResponseBody Object showInfoBook(HttpServletRequest request) throws UnsupportedEncodingException {
		//request.setCharacterEncoding("utf-8");
		//JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
		String table = request.getParameter("table");
		JSONObject jo = new JSONObject();
		String key = "";
		//每页中显示的记录行数
		int rows = Integer.valueOf(request.getParameter("rows"));
		//当前的页码
		int page = Integer.valueOf(request.getParameter("page"));
		//排序方式
		String sord = request.getParameter("sord");
		//排序列名
		String sidx = request.getParameter("sidx");
		//是否用于查询请求
		Boolean search = (request.getParameter("_search").equals("true"))?true:false;
		//返回结果集
		//List<InfoBook> allList = new LinkedList<InfoBook>();
		if(search) {
			key = " WHERE ";
			String filters = new String(request.getParameter("filters").getBytes("iso-8859-1"),"utf-8");//具体的条件
			//System.out.println(filters);
			JSONObject jo_filters = JSONObject.parseObject(filters);
			String groupOp = "AND";//每个规则之间的关系and/or
			if (jo_filters.getString("groupOp") != null && !"".equals(jo_filters.getString("groupOp"))) {
				if (jo_filters.getString("groupOp").equals("OR")) {
					groupOp = "OR";
				}
			}
			JSONArray rulesjson = jo_filters.getJSONArray("rules");
			//遍历每个条件
			for (int z = 0; z < rulesjson.size(); z++) {
				JSONObject rulejson = rulesjson.getJSONObject(z);
				String field = rulejson.getString("field");
				String op = rulejson.getString("op");
				String data = rulejson.getString("data");
				String string = "";//用于存储单个条件sql语句片段
				switch (op) {
				case "eq":
					string = " = '" + data + "' ";
					break;
				case "ne":
					string = " != '" + data + "' ";
					break;
				case "li":
					string = " < '" + data + "' ";
					break;
				case "le":
					string = " <= '" + data + "' ";
					break;
				case "gt":
					string = " > '" + data + "' ";
					break;
				case "ge":
					string = " >= '" + data + "' ";
					break;
				case "bw":
					{
						if (data.split(",").length == 2) {
							string = " BETWEEN '" + data.split(",")[0] + "' AND '" + data.split(",")[1] + "' ";
						} else {
							string = " = '" + data + "' ";//数据错误时处理
						}
					}
					break;
				case "bn":
					{
						if (data.split(",").length == 2) {
							string = " NOT BETWEEN '" + data.split(",")[0] + "' AND '" + data.split(",")[1] + "' ";
						} else {
							string = " <> '" + data + "' ";//数据错误时处理
						}
					}
					break;
				case "ew":
					string = " LIKE '%" + data + "' ";
					break;
				case "en":
					string = " NOT LIKE '%" + data + "' ";
					break;
				case "cn":
					string = " LIKE '%" + data + "%' ";
					break;
				case "nc":
					string = " NOT LIKE '%" + data + "%' ";
					break;
				case "in":
					{
						string = " IN ( ";
						String[] datas = data.split(",");
						for (int i = 0; i < datas.length; i++) {
							string += " '" + datas[i] + "' ";
							if (i != datas.length - 1) {
								string += ",";
							} else {
								string += " ) ";
							}
						}
					}
					break;
				case "ni":
					{
						string=" NOT IN ( ";
						String[] datas = data.split(",");
						for (int i = 0; i < datas.length; i++) {
							string += " '" + datas[i] + "' ";
							if (i != datas.length - 1) {
								string += ",";
							} else {
								string += " ) ";
							}
						}
					}
					break;
				default:
					op = null;
					System.out.println("OP符号错误");//OP符号错误
				}
				if (op != null) {
					if (z == rulesjson.size() - 1) {
						key += " " + field + " " + string + " ";
					} else {
						key += " " + field + " " + string + " " + groupOp + " ";
					}
				}
			}
		}
		//升降序SQL语句转换
		if (sidx != null && !"".equals(sidx)) {
			System.out.println(sidx);
			key += " ORDER BY " + sidx;
			System.out.println("sord="+sord);
			if (!sord.equals("asc")) {
				key += " DESC ";
			}
		}
		//allList = this.adminDoService.selectBySQL(key);
		JSONArray allList = new JSONArray();
		switch (table) {
		case "infoBook":
			{
				List<InfoBook> infoBookList = new LinkedList<InfoBook>();
				infoBookList = this.infoBookService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(infoBookList, "yyyy-MM-dd HH:mm"));
			}
			break;
		case "readerCategory":
			{
				List<ReaderCategory> readerCategory = new LinkedList<ReaderCategory>();
				readerCategory = this.readerCategoryService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(readerCategory, "yyyy-MM-dd HH:mm"));
			}
			break;
		case "readerReservation":
			{
				List<ReaderReservation> readerReservation = new LinkedList<ReaderReservation>();
				readerReservation = this.readerReservationService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(readerReservation, "yyyy-MM-dd HH:mm"));
			}
			break;
		case "infoCollection":
			{
				List<InfoCollection> infoCollection = new LinkedList<InfoCollection>();
				infoCollection = this.infoCollectionService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(infoCollection, "yyyy-MM-dd HH:mm"));;
			}
			break;
		case "user":
			{
				List<User> user = new LinkedList<User>();
				user = this.userService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm"));
			}
			break;
		case "role":
			{
				List<Role> role = new LinkedList<Role>();
				role = this.roleService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm"));
			}
			break;
		case "permission":
			{
				List<Permission> permission = new LinkedList<Permission>();
				permission = this.permissionService.selectBySQL(key);
				allList = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(permission, "yyyy-MM-dd HH:mm"));
			}
			break;
		default:
			System.out.println("table参数出错");
		}
		//分页部分
		int total=0;
		total = (allList.size() % rows == 0)?(allList.size() / rows):((allList.size() / rows) + 1);
		int m = (page - 1) * rows;
		int n = (page - 1) * rows + rows;
		JSONArray showArray = new JSONArray();
		for (int j = m; j < allList.size() && j < n; j++) {
			showArray.add(allList.get(j));
		}
		jo.put("page", page);
		jo.put("total", total);
		jo.put("records", allList.size());
		jo.put("rows", showArray);
		//System.out.println(jo.toString());
		return jo;
	}
	
	@RequestMapping("/infoBookChange")
	public @ResponseBody Boolean infoBookChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "del":
			{
				String[] ids = request.getParameter("id").split(",");
				try {
					this.adminDoService.infoBookDel(ids);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "add":
			{
				InfoBook infoBook = new InfoBook();
				infoBook.setIsbn(request.getParameter("isbn"));
				infoBook.setTitle(request.getParameter("title"));
				infoBook.setAuthor(request.getParameter("author"));
				infoBook.setTranslator(request.getParameter("translator"));
				infoBook.setPainter(request.getParameter("painter"));
				infoBook.setPublisher(request.getParameter("publisher"));
				infoBook.setPublishedin(new SimpleDateFormat("yyyy").parse(request.getParameter("publishedin")));
				infoBook.setDescription(request.getParameter("description"));
				infoBook.setClc(request.getParameter("clc"));
				infoBook.setVolume(request.getParameter("volume"));
				infoBook.setLanguages(request.getParameter("languages"));
				infoBook.setPrice(BigDecimal.valueOf(Float.parseFloat(request.getParameter("price"))));
				infoBook.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(infoBook));
				try {
					this.adminDoService.infoBookAdd(infoBook);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "edit":
			{
				Long id = Long.parseLong(request.getParameter("id"));
				InfoBook infoBook = new InfoBook();
				infoBook.setId(id);
				infoBook.setIsbn(request.getParameter("isbn"));
				infoBook.setTitle(request.getParameter("title"));
				infoBook.setAuthor(request.getParameter("author"));
				infoBook.setTranslator(request.getParameter("translator"));
				infoBook.setPainter(request.getParameter("painter"));
				infoBook.setPublisher(request.getParameter("publisher"));
				infoBook.setPublishedin(new SimpleDateFormat("yyyy").parse(request.getParameter("publishedin")));
				infoBook.setDescription(request.getParameter("description"));
				infoBook.setClc(request.getParameter("clc"));
				infoBook.setVolume(request.getParameter("volume"));
				infoBook.setLanguages(request.getParameter("languages"));
				infoBook.setPrice(BigDecimal.valueOf(Float.parseFloat(request.getParameter("price"))));
				System.out.println(JSON.toJSONString(infoBook));
				try {
					this.adminDoService.infoBookEdit(infoBook);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/infoCollectionChange")
	public @ResponseBody Boolean infoCollectionChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "edit":
			{
				Long icid = Long.parseLong(request.getParameter("id"));
				InfoCollection ic = new InfoCollection();
				ic.setId(icid);
				ic.setBarCode(request.getParameter("barCode"));
				ic.setBid(Long.parseLong(request.getParameter("bid")));
				ic.setCallno(request.getParameter("callno"));
				ic.setLocated(request.getParameter("located"));
				ic.setType(request.getParameter("type"));
				System.out.println(JSON.toJSONString(ic));
				try {
					this.adminDoService.infoCollectionEdit(ic);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/userChange")
	public @ResponseBody Boolean userChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "del":
			{
				String[] ids = request.getParameter("id").split(",");
				try {
					this.adminDoService.userDel(ids);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "add":
			{
				User user = new User();
				user.setLoginId(request.getParameter("loginId"));
				user.setPassword(request.getParameter("password"));
				user.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(user));
				try {
					this.adminDoService.userAdd(user);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "edit":
			{
				Long id = Long.parseLong(request.getParameter("id"));
				User user = new User();
				user.setId(id);
				user.setLoginId(request.getParameter("loginId"));
				user.setPassword(request.getParameter("password"));
				System.out.println(JSON.toJSONString(user));
				try {
					this.adminDoService.userEdit(user);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/roleChange")
	public @ResponseBody Boolean roleChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "del":
			{
				String[] ids = request.getParameter("id").split(",");
				try {
					this.adminDoService.roleDel(ids);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "add":
			{
				Role role = new Role();
				role.setParentId(Long.parseLong(request.getParameter("parentId")));
				role.setName(request.getParameter("name"));
				role.setDescription(request.getParameter("description"));
				role.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(role));
				try {
					this.adminDoService.roleAdd(role);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "edit":
			{
				Long id = Long.parseLong(request.getParameter("id"));
				Role role = new Role();
				role.setId(id);
				role.setParentId(Long.parseLong(request.getParameter("parentId")));
				role.setName(request.getParameter("name"));
				role.setDescription(request.getParameter("description"));
				System.out.println(JSON.toJSONString(role));
				try {
					this.adminDoService.roleEdit(role);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/permissionChange")
	public @ResponseBody Boolean permissionChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "del":
			{
				String[] ids = request.getParameter("id").split(",");
				try {
					this.adminDoService.permissionDel(ids);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "add":
			{
				Permission permission = new Permission();
				permission.setParentId(Long.parseLong(request.getParameter("parentId")));
				permission.setName(request.getParameter("name"));
				permission.setDescription(request.getParameter("description"));
				permission.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(permission));
				try {
					this.adminDoService.permissionAdd(permission);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "edit":
			{
				Long id = Long.parseLong(request.getParameter("id"));
				Permission permission = new Permission();
				permission.setId(id);
				permission.setParentId(Long.parseLong(request.getParameter("parentId")));
				permission.setName(request.getParameter("name"));
				permission.setDescription(request.getParameter("description"));
				System.out.println(JSON.toJSONString(permission));
				try {
					this.adminDoService.permissionEdit(permission);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/readerBorrow.showAccount")
	public @ResponseBody Object showAccount(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String q = request.getParameter("q");
		System.out.println(q);
		List<ReaderLicense> readerLicenses = this.adminDoService.selectByLikeAccount("%"+q+"%");
		JSONArray ja = new JSONArray();
		for (int i = 0; i < readerLicenses.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("id", readerLicenses.get(i).getAccount());
			item.put("text", readerLicenses.get(i).getAccount());
			ja.add(item);
		}
		//String test = "[{\"id\": \"text1\", \"text\":\"text1\"},{\"id\": \"text2\", \"text\":\"text2\"},{\"id\": 2, \"text\":\"text3\"},{\"id\": 3, \"text\":\"text4\"},{\"id\": 4, \"text\":\"text5\"},{\"id\": 5, \"text\":\"text6\"},{\"id\": 6, \"text\":\"text7\"}]";
		//JSONArray ja = JSONArray.parseArray(test);
		System.out.println(ja.toJSONString());
		jo.put("items", ja);
		return jo;
	}
	
	@RequestMapping("/readerBorrow.showBarCode")
	public @ResponseBody Object readerBorrowShowBarCode(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String q = request.getParameter("q");
		System.out.println(q);
		List<InfoCollection> infoCollections = this.adminDoService.selectByLikeBarCode("%"+q+"%");
		JSONArray ja = new JSONArray();
		for (int i = 0; i < infoCollections.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("id", infoCollections.get(i).getId());
			item.put("text", infoCollections.get(i).getBarCode());
			ja.add(item);
		}
		//String test = "[{\"id\": \"text1\", \"text\":\"text1\"},{\"id\": \"text2\", \"text\":\"text2\"},{\"id\": 2, \"text\":\"text3\"},{\"id\": 3, \"text\":\"text4\"},{\"id\": 4, \"text\":\"text5\"},{\"id\": 5, \"text\":\"text6\"},{\"id\": 6, \"text\":\"text7\"}]";
		//JSONArray ja = JSONArray.parseArray(test);
		System.out.println(ja.toJSONString());
		jo.put("items", ja);
		return jo;
	}
	
	@RequestMapping("/saccountSelect")
	public @ResponseBody Object showSaccountSelect(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String account = request.getParameter("account");
		System.out.println(account);
		ReaderLicense readerLicense = this.readerLicenseService.selectByAccount(account);
		jo.put("readerLicense", readerLicense);
		if(account.substring(0, 1).equals("S")) {
			InfoStudent infoStudent = this.infoStudentService.selectByLoginID(account);
			jo.put("userInfo", infoStudent);
		} else {
			InfoTeacher infoTeacher = this.infoTeacherService.selectByTeaID(account);
			jo.put("userInfo", infoTeacher);
		}
		System.out.println(jo.toJSONString());
		return jo;
	}
	
	@RequestMapping("/sbarCodeSelect")
	public @ResponseBody Object showSbarCodeSelect(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		System.out.println("showSbarCodeSelect");
		String[] reslist = request.getParameterValues("reslist[]");
		JSONArray ja = new JSONArray();
		for (int i = 0; i < reslist.length; i++) {
			System.out.println(reslist[i]);
			Long icid = Long.parseLong(reslist[i]);
			InfoCollection infoCollection = this.adminDoService.selectByICidFromICandIB(icid);
			ja.add(infoCollection);
		}
		jo.put("item", ja);
		return jo;
	}
	
	@RequestMapping("/readerBorrow.do")
	public @ResponseBody Object doReaderBorrow(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String[] idList = request.getParameterValues("sbarCode");
		String account = request.getParameter("saccount");
		ReaderLicense readerLicense = this.readerLicenseService.selectByAccount(account);
		if(readerLicense.getStatus() == 1) {
			if(readerLicense.getReaderCategory().getMaxBookBorrow() > readerLicense.getBorrowed()) {
				for (int i = 0; i < idList.length; i++) {
					Long id = Long.parseLong(idList[i]);
					InfoCollection infoCollection = this.infoCollectionService.selectByPrimaryKey(id);
					if(infoCollection.getIsBorrowing() == 1) {
						reasult = "借阅失败，请先归还未归还的书籍！";
						break;
					} else {
						if(infoCollection.getIsReservation() == 1) {
							ReaderReservation readerReservation = this.readerReservationService.selectByBarCodeAndEffective(infoCollection.getBarCode(), (byte) 1);
							if(account.equals(readerReservation.getAccount()) == false) {
								reasult = "借阅失败，部分书籍已被他人预约！";
								break;
							}
						}
					}
				}
			} else {
				reasult = "借阅失败，借阅数量已达上限！";
			}
		} else {
			reasult = "借阅失败，读者借阅证被冻结或注销！";
		}
		try {
			if(reasult.equals("") == true) {
				this.adminDoService.readerBorrowDo(account, idList);
				reasult = "借阅成功！";
			}
		} catch(Exception e) {
			logger.debug(e.getMessage());
			reasult = "借阅失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/readerCategoryChange")
	public @ResponseBody Boolean readerCategoryChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Boolean flag = false;
		String oper = request.getParameter("oper");
		switch (oper) {
		case "del":
			{
				String[] ids = request.getParameter("id").split(",");
				try {
					this.adminDoService.readerCategoryDel(ids);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "add":
			{
				ReaderCategory readerCategory = new ReaderCategory();
				readerCategory.setName(request.getParameter("name"));
				readerCategory.setMaxBookBorrow(Integer.parseInt(request.getParameter("maxBookBorrow")));
				readerCategory.setBookBorrowAgain(Integer.parseInt(request.getParameter("bookBorrowAgain")));
				readerCategory.setBookBorrowTime(Integer.parseInt(request.getParameter("bookBorrowTime")));
				readerCategory.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(readerCategory));
				try {
					this.adminDoService.readerCategoryAdd(readerCategory);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		case "edit":
			{
				Long id = Long.parseLong(request.getParameter("id"));
				ReaderCategory readerCategory = new ReaderCategory();
				readerCategory.setId(id);
				readerCategory.setName(request.getParameter("name"));
				readerCategory.setMaxBookBorrow(Integer.parseInt(request.getParameter("maxBookBorrow")));
				readerCategory.setBookBorrowAgain(Integer.parseInt(request.getParameter("bookBorrowAgain")));
				readerCategory.setBookBorrowTime(Integer.parseInt(request.getParameter("bookBorrowTime")));
				readerCategory.setGmtCreate(new Date());
				System.out.println(JSON.toJSONString(readerCategory));
				try {
					this.adminDoService.readerCategoryEdit(readerCategory);
					flag = true;
				} catch(Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}
	
	@RequestMapping("/returnBorrow.showBarCode")
	public @ResponseBody Object returnBorrowShowBarCode(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String q = request.getParameter("q");
		System.out.println(q);
		List<ReaderBorrow> readerBorrows = this.readerBorrowService.selectByLikeBarCode1Back0("%"+q+"%");
		JSONArray ja = new JSONArray();
		for (int i = 0; i < readerBorrows.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("id", readerBorrows.get(i).getId());
			item.put("text", readerBorrows.get(i).getBarCode());
			item.put("icid", readerBorrows.get(i).getInfoCollection().getId());
			ja.add(item);
		}
		System.out.println(ja.toJSONString());
		jo.put("items", ja);
		return jo;
	}
	
	@RequestMapping("/readerReturn.do")
	public @ResponseBody Object doReaderReturn(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String[] idList = request.getParameterValues("sbarCode");
		System.out.println(idList.toString());
		try {
			Boolean isPenalty = this.adminDoService.doReturnBook(idList);
			if(isPenalty == false) {
				reasult = "成功归还！";
			} else {
				reasult = "成功归还！提示：部分图书超期归还，账号被冻结！";
			}
		} catch(Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			reasult = "归还失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/returnBorrowError.showAccount")
	public @ResponseBody Object showReturnBorrowAccount(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String q = request.getParameter("q");
		System.out.println(q);
		List<ReaderBorrow> readerBorrows = this.readerBorrowService.selectByLikeAccount("%"+q+"%", (byte) 0);
		JSONArray ja = new JSONArray();
		for (int i = 0; i < readerBorrows.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("id", readerBorrows.get(i).getAccount());
			item.put("text", readerBorrows.get(i).getAccount());
			ja.add(item);
		}
		System.out.println(ja.toJSONString());
		jo.put("items", ja);
		return jo;
	}
	
	@RequestMapping("/saccountSelectShowBook")
	public @ResponseBody Object showBookSaccountSelect(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		System.out.println(request.getParameter("res"));
		String account = request.getParameter("res");
		List<ReaderBorrow> readerBorrows = this.readerBorrowService.selectByAccountBack0(account);
		System.out.println(JSON.toJSONString(readerBorrows));
		jo.put("item", readerBorrows);
		return jo;
	}
	
	@RequestMapping("/readerReturnError.do")
	public @ResponseBody Object doReaderReturnError(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		//String saccount = request.getParameter("saccount");
		String[] isLose = request.getParameterValues("isLose");
		String[] isDamage = request.getParameterValues("isDamage");
		try {
			if(isLose.length != 0) {
				this.adminDoService.doReturnBookLose(isLose);
			}
			if(isDamage.length != 0) {
				this.adminDoService.doReturnBookDamage(isDamage);
			}
			reasult = "成功受理！相关读者账号被冻结，请尽快交齐罚款。";
		} catch(Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			reasult = "受理失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/infoPenalty.showAccount")
	public @ResponseBody Object showInfoPenaltyAccount(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String q = request.getParameter("q");
		System.out.println(q);
		List<InfoPenalty> infoPenaltys = this.infoPenaltyService.selectByLikeAccount("%"+q+"%");
		JSONArray ja = new JSONArray();
		for (int i = 0; i < infoPenaltys.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("id", infoPenaltys.get(i).getAccount());
			item.put("text", infoPenaltys.get(i).getAccount());
			ja.add(item);
		}
		System.out.println(ja.toJSONString());
		jo.put("items", ja);
		return jo;
	}
	
	@RequestMapping("/saccountSelectShowPenalty")
	public @ResponseBody Object showPenaltySaccountSelect(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		System.out.println(request.getParameter("res"));
		String account = request.getParameter("res");
		//List<ReaderBorrow> readerBorrows = this.readerBorrowService.selectByAccountBack0(account);
		List<InfoPenalty> infoPenaltyList = this.infoPenaltyService.selectByAccount0(account);
		System.out.println(JSON.toJSONString(infoPenaltyList));
		int total = 0;
		for(int i = 0; i < infoPenaltyList.size(); i++) {
			total += infoPenaltyList.get(i).getPricePenalty();
		}
		jo.put("rmb", total);
		jo.put("size", infoPenaltyList.size());
		jo.put("infoPenaltyList", infoPenaltyList);
		return jo;
	}
	
	@RequestMapping("/infoPenalty.do")
	public @ResponseBody Object doInfoPenalty(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String saccount = request.getParameter("saccount");
		String[] ipid = request.getParameterValues("ipid");
		System.out.println(saccount);
		System.out.println(JSON.toJSONString(ipid));
		try {
			Boolean flag = this.adminDoService.doInfoPenalty(ipid, saccount);
			if(flag == true) {
				reasult = "缴费成功，读者借书证已恢复正常！";
			} else {
				reasult = "缴费成功，读者仍有其他欠费记录，请尽快交齐！";
			}
		} catch(Exception e){
			logger.debug(e.getMessage());
			e.printStackTrace();
			reasult = "缴费失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/acancelReservation.do")
	public @ResponseBody Object acancelReservationdo(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String[] rrids = request.getParameterValues("rrids[]");
		System.out.println(JSON.toJSONString(rrids));
		try {
			this.adminDoService.doCancelReservation(rrids);
			reasult = "成功取消预约！";
		} catch(Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			reasult = "取消预约失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/addInfoCollection.do")
	public @ResponseBody Object addInfoCollectiondo(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		JSONObject jo = new JSONObject();
		String reasult = "";
		InfoCollection ic = new InfoCollection();
		ic.setBarCode(request.getParameter("icBarCode"));
		ic.setBid(Long.parseLong(request.getParameter("icBid")));
		ic.setCallno(request.getParameter("icCallno"));
		ic.setLocated(request.getParameter("icLocated"));
		ic.setType(request.getParameter("icType"));
		System.out.println(JSON.toJSONString(ic));
		try {
			this.adminDoService.infoCollectionAdd(ic);
			reasult = "成功添加！";
		} catch(Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			reasult = "添加馆藏书目失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/createAllIndex.do")
	public @ResponseBody Object createAllIndex() throws Exception {
		JSONObject jo = new JSONObject();
		String reasult = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<InfoBook> infoBooks = this.infoBookService.list(map);
			for(InfoBook infoBook:infoBooks) {
				MyLucene myLucene = new MyLucene();
				myLucene.deleteIndex(infoBook.getId()+"");
			}
			for(InfoBook infoBook:infoBooks) {
				MyLucene myLucene = new MyLucene();
				myLucene.addIndex(infoBook);
			}
			reasult = "成功生成索引！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			reasult = "生成索引失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/createAccounts.do")
	public @ResponseBody Object createAccountsdo() {
		JSONObject jo = new JSONObject();
		String reasult = "";
		try {
			this.adminDoService.doCreateUserAndLicense();
			reasult = "成功执行！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			reasult = "执行失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/role/treedata")
	public @ResponseBody Object treedataRole(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		JSONArray ja = new JSONArray();
		List<Role> roleList = this.roleService.selectBySQL("");
		List<UserRole> userRoleList = this.userRoleService.selectByLoginId(Long.parseLong(uid));
		for(int i = 0; i < roleList.size(); i++) {
			Role role = roleList.get(i);
			JSONObject j = new JSONObject();
			j.put("id", role.getId());
			j.put("pId", role.getParentId());
			j.put("name", role.getName());
			for(UserRole userRole : userRoleList) {
				if(userRole.getRoleId() == role.getId()) {
					j.put("checked", true);
				}
			}
			ja.add(j);
		}
		return ja;
	}
	
	@RequestMapping("/userRole/change")
	public @ResponseBody Object changeUserRole(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String uid = request.getParameter("uid");
		String[] ids = request.getParameterValues("ids[]");
		System.out.println(uid);
		System.out.println(JSON.toJSONString(ids));
		try {
			this.adminDoService.userRoleChange(uid, ids);
			reasult = "角色分配成功！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			reasult = "角色分配失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
	
	@RequestMapping("/permission/treedata")
	public @ResponseBody Object treedataPermission(HttpServletRequest request) {
		String rid = request.getParameter("rid");
		JSONArray ja = new JSONArray();
		List<Permission> permsList = this.permissionService.selectBySQL("");
		List<RolePermission> rolePermsList = this.rolePermissionService.selectByRoleId(Long.parseLong(rid));
		for(int i = 0; i < permsList.size(); i++) {
			Permission perm = permsList.get(i);
			JSONObject j = new JSONObject();
			j.put("id", perm.getId());
			j.put("pId", perm.getParentId());
			j.put("name", perm.getName());
			for(RolePermission rolePermission : rolePermsList) {
				if(rolePermission.getPermissionId() == perm.getId()) {
					j.put("checked", true);
				}
			}
			ja.add(j);
		}
		return ja;
	}
	
	@RequestMapping("/rolePermission/change")
	public @ResponseBody Object changeRolePermission(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String reasult = "";
		String rid = request.getParameter("rid");
		String[] ids = request.getParameterValues("ids[]");
		System.out.println(rid);
		System.out.println(JSON.toJSONString(ids));
		try {
			this.adminDoService.rolePermissionChange(rid, ids);
			reasult = "权限分配成功！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			reasult = "权限分配失败！";
		}
		jo.put("reasult", reasult);
		return jo;
	}
}
