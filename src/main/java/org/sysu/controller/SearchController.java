package org.sysu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.lucene.MyLucene;
import org.sysu.pojo.InfoBook;
import org.sysu.pojo.InfoCollection;
import org.sysu.pojo.InfoDuplicate;
import org.sysu.realm.ShiroDbRealm;
import org.sysu.service.InfoBookService;
import org.sysu.service.InfoCollectionService;
import org.sysu.service.InfoDuplicateService;
import org.sysu.service.ReaderDoService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class SearchController {
	private static Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Resource
	private InfoBookService infoBookService;
	@Resource
	private InfoDuplicateService infoDuplicateService;
	@Resource
	private InfoCollectionService infoCollectionService;
	@Resource
	private ReaderDoService readerDoService;
/*	
	@RequestMapping(value="/advancedSearch.do")
	public String toSearch(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		System.out.println("llllll");
		String str_input = new String(request.getParameter("search_input").getBytes("iso-8859-1"),"utf-8");
		model.addAttribute("str_input", str_input);
		logger.debug("来自IP[" + request.getRemoteHost() + "]的访问");
		return "search_reasults";
		
	}
	*/
	@RequestMapping(value="/q.do")
	public @ResponseBody Object ftsearch(@RequestParam("search_input") String search_input, @RequestParam("pid") String pid, @RequestParam("pageSize") String pageSize) throws Exception {
		search_input = URLDecoder.decode(search_input, "UTF-8");
		int currentPage = Integer.parseInt(pid);
		int size = Integer.parseInt(pageSize);
		JSONObject jo = new JSONObject();
		MyLucene myLucene = new MyLucene();
		List<InfoBook> infoBookList = myLucene.searchBook(search_input);
		System.out.println("infoBookList.size():"+infoBookList.size());
		Integer toIndex = infoBookList.size() >= currentPage*size ? currentPage*size : infoBookList.size();
		List<InfoBook> sendList = infoBookList.subList((currentPage-1)*size, toIndex);
		jo.put("list", sendList);
		jo.put("size", infoBookList.size());
		List<InfoDuplicate> infoDuplicates = new LinkedList<InfoDuplicate>();
		for(InfoBook infoBook1:sendList){
			System.out.println(infoBook1.getPublishedin()+"|"+infoBook1.getId());
			InfoDuplicate infoDuplicate = this.infoDuplicateService.selectByBid(infoBook1.getId());
			infoDuplicates.add(infoDuplicate);
		}
		jo.put("duplicate", infoDuplicates);
		System.out.println(jo.toString());
		return jo;
	}
	
	@RequestMapping(value="/ftSearch.do")
	public String loadSubmit(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException{
		System.out.println("submit.do调用");
		String str_input = new String(request.getParameter("search_input").getBytes("iso-8859-1"),"utf-8");
		System.out.println("submit.do:"+str_input);
		model.addAttribute("str_input", str_input);
		return "search_reasults";
	}
/*	
	@RequestMapping(value="/ftSearch-ing.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public @ResponseBody List<InfoBook> search(@RequestParam("search_input") String search_input, @RequestParam("pid") String pid, @RequestParam("pageSize") String pageSize) throws UnsupportedEncodingException {
		search_input = URLDecoder.decode(search_input, "UTF-8");
		System.out.println("search.do/{search_input}/{pid} 已经调用");
		System.out.println("search.do/{search_input}/{pid}:"+search_input);
		System.out.println("search.do/{search_input}/{pid}:"+pid);
		int currentPage = Integer.parseInt(pid);
		int size = Integer.parseInt(pageSize);
		//String search_input1 = request.getParameter("search_input");
		//System.out.println(search_input1);
		List<InfoBook> infoBooks = this.infoBookService.selectByFTLimit(search_input, (currentPage-1)*size, size*currentPage);
		for(InfoBook infoBook:infoBooks){
			System.out.println(infoBook.toString());
		}
		return infoBooks;
	}
	*/
/*	@RequestMapping(value="/ftCount.do", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public @ResponseBody String searchCount(@RequestParam("search_input") String search_input, HttpServletRequest request, Model model) throws Exception {
		System.out.println("ftCount.do 已经调用");
		search_input = URLDecoder.decode(search_input, "UTF-8");
		MyLucene myLucene = new MyLucene();
		List<InfoBook> infoBookList = myLucene.searchBook(search_input);
		int countAll = infoBookList.size();
		String str1 = "{\"countAll\":" + countAll + "}";
		System.out.println(str1);
		return str1;
	}*/
	
	@RequestMapping("/bdetail.do")
	public String showBookDetail(HttpServletRequest request, Model model) {
		Long bid = Long.parseLong(request.getParameter("bid"));
		System.out.println("bdetail.do?bid=:"+bid);
		InfoBook infoBook = this.infoBookService.selectByID(bid);
		System.out.println("bdetail.do:"+infoBook.toString());
		model.addAttribute("infoBook", infoBook);
		List<InfoCollection> list = this.infoCollectionService.selectByBid1(bid);
		for(InfoCollection infoCollection:list){
			System.out.println("bdetail.do:"+infoCollection.toString());
		}
		model.addAttribute("list", list);
		return "book_detail";
	}
	
	@RequestMapping("/rreservation.do")
	public @ResponseBody Object rreservationdo(HttpServletRequest request, Model model) {
		JSONObject jo = new JSONObject();
		String reasult = null;
		String barCode = request.getParameter("barCode");
		Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		//update Collection and insert reservation
		try{
			this.readerDoService.rreservationdo(loginID, barCode);
			reasult = "预约成功！";
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			if(e.getMessage().equals("读者借书证被冻结或注销！")) {
				reasult = e.getMessage();
			} else if(e.getMessage().equals("该书状态不允许被预约！")) {
				reasult = e.getMessage();
			} else {
				reasult = "预约失败";
			}
		}
		jo.put("reasult", reasult);
		System.out.println(JSON.toJSONString(jo));
		return jo;
	}
}
