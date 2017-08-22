package org.sysu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.pojo.InfoPenalty;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.ReaderBorrow;
import org.sysu.pojo.ReaderCollection;
import org.sysu.pojo.ReaderLicense;
import org.sysu.pojo.ReaderReservation;
import org.sysu.pojo.User;
import org.sysu.realm.ShiroDbRealm;
import org.sysu.service.InfoPenaltyService;
import org.sysu.service.InfoStudentService;
import org.sysu.service.InfoTeacherService;
import org.sysu.service.ReaderBorrowService;
import org.sysu.service.ReaderCollectionService;
import org.sysu.service.ReaderDoService;
import org.sysu.service.ReaderLicenseService;
import org.sysu.service.ReaderReservationService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class PersonalController {
	private static Logger logger = LoggerFactory.getLogger(PersonalController.class);
	
	@Resource
	private InfoStudentService infoStudentService;
	@Resource
	private InfoTeacherService infoTeacherService;
	@Resource
	private ReaderLicenseService readerLicenseService;
	@Resource
	private ReaderBorrowService readerBorrowService;
	@Resource
	private ReaderReservationService readerReservationService;
	@Resource
	private InfoPenaltyService infoPenaltyService;
	@Resource
	private ReaderDoService readerDoService;
	@Resource
	private ReaderCollectionService readerCollectionService;
	
	@RequestMapping("/personal")
	public String topersonal(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.debug("来自IP[" + request.getRemoteHost() + "]的访问");
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		System.out.println("userID:"+loginID);
		ReaderLicense readerLicense = this.readerLicenseService.selectByAccount(loginID);
		model.addAttribute("readerLicense", readerLicense);
		return "reader_detail";
	}
	
	@RequestMapping("/personalCurrentBorrowedPage.do")
	public @ResponseBody Object personalCurrentBorrowed() {
		JSONObject jo = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		ReaderLicense readerLicense = this.readerLicenseService.selectByAccount(loginID);
		List<ReaderBorrow> readerBorrowList = this.readerBorrowService.selectByAccountBack0(loginID);
		List<ReaderReservation> readerReservationList = this.readerReservationService.selectByAccountEffective1(loginID);
		List<InfoPenalty> infoPenaltyList = this.infoPenaltyService.selectByAccount0(loginID);
		int rbsize = readerBorrowList.size();
		int rrsize = readerReservationList.size();
		int ipsize = infoPenaltyList.size();
		jo.put("readerBorrowList", readerBorrowList);
		jo.put("readerReservationList", readerReservationList);
		jo.put("infoPenaltyList", infoPenaltyList);
		jo.put("rbsize", rbsize);
		jo.put("rrsize", rrsize);
		jo.put("ipsize", ipsize);
		jo.put("reBorrowMax", readerLicense.getReaderCategory().getBookBorrowAgain());
		System.out.println(jo.toString());
		return jo;
	}
	
	@RequestMapping("/personalHistoryPage.do")
	public @ResponseBody Object personalHistoryPage() {
		JSONObject jo = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		List<ReaderBorrow> readerBorrowList = this.readerBorrowService.selectByAccountBackNot0(loginID);
		List<ReaderReservation> readerReservationList = this.readerReservationService.selectByAccountEffective0(loginID);
		List<InfoPenalty> infoPenaltyList = this.infoPenaltyService.selectByAccount1(loginID);
		int rbsize = readerBorrowList.size();
		int rrsize = readerReservationList.size();
		int ipsize = infoPenaltyList.size();
		jo.put("readerBorrowList", readerBorrowList);
		jo.put("readerReservationList", readerReservationList);
		jo.put("infoPenaltyList", infoPenaltyList);
		jo.put("rbsize", rbsize);
		jo.put("rrsize", rrsize);
		jo.put("ipsize", ipsize);
		System.out.println(jo.toString());
		return jo;
	}
	
	@RequestMapping("/rreborrow.do")
	public @ResponseBody Object rreborrowdo(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		String reasult = null;
		Long rbid = Long.parseLong(request.getParameter("rbid"));
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		try {
			this.readerDoService.rreborrowdo(loginID, rbid);
			reasult = "续借成功";
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			if(e.getMessage().equals("该书已被他人预约，无法续借！")) {
				reasult = e.getMessage();
			} else if(e.getMessage().equals("读者借书证被冻结或注销！")) {
				reasult = e.getMessage();
			} else if(e.getMessage().equals("续借次数已达上限，续借失败！")) {
				reasult = e.getMessage();
			} else {
				reasult = "续借失败！";
			}
		}
		jo.put("reasult", reasult);
		System.out.println(JSON.toJSONString(jo));
		return jo;
	}
	
	@RequestMapping("/rcancelReservation.do")
	public @ResponseBody Object rcancelReservationdo(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		String reasult = null;
		Long rrid = Long.parseLong(request.getParameter("rrid"));
		try {
			this.readerDoService.rcancelReservationdo(rrid);
			reasult = "取消预约成功！";
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			reasult = "取消预约失败！";
		}
		jo.put("reasult", reasult);
		System.out.println(JSON.toJSONString(jo));
		return jo;
	}
	
	@RequestMapping("/addCollection.do")
	public @ResponseBody Object addCollection(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		String result = "";
		Long bid = Long.parseLong(request.getParameter("bid"));
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		ReaderCollection readerCollection = new ReaderCollection();
		readerCollection.setAccount(loginID);
		readerCollection.setBid(bid);
		try {
			this.readerDoService.readerCollectionAdd(readerCollection);
			result = "添加成功！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			result = "添加失败！";
		}
		jo.put("result", result);
		return jo;
	}
	
	@RequestMapping("/myCollection")
	public @ResponseBody Object showMyCollection(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		List<ReaderCollection> rcList = this.readerCollectionService.selectByAccount(loginID);
		int rcSize = rcList.size();
		jo.put("rcList", rcList);
		jo.put("result", rcSize);
		return jo;
	}
	
	@RequestMapping("/delCollection.do")
	public @ResponseBody Object delCollection(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		String result = "";
		Long rcid = Long.parseLong(request.getParameter("rcid"));
		try {
			this.readerDoService.readerCollectionDel(rcid);
			result = "删除成功！";
		} catch(Exception e) {
			logger.error(e.getMessage());
			result = "删除失败！";
		}
		jo.put("result", result);
		return jo;
	}
}