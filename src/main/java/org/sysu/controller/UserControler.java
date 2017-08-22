package org.sysu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.sysu.pojo.InfoStudent;
import org.sysu.pojo.InfoTeacher;
import org.sysu.pojo.ReaderLicense;
import org.sysu.pojo.User;
import org.sysu.realm.ShiroDbRealm;
import org.sysu.service.AdminDoService;
import org.sysu.service.InfoStudentService;
import org.sysu.service.InfoTeacherService;
import org.sysu.service.UserService;
import org.sysu.utils.CipherUtil;

import com.alibaba.fastjson.JSON;


@Controller
@SessionAttributes("infoUser")
public class UserControler {
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	private static Logger logger1 = LoggerFactory.getLogger(UserControler.class);

	@Resource
	private InfoStudentService infoStudentService;
	@Resource
	private InfoTeacherService infoTeacherService;
	@Resource
	private UserService userService;
	@Resource
	private AdminDoService adminDoService;
	/**
	 * 初始登陆界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/login.do")
	public String tologin(HttpServletRequest request, HttpServletResponse response, Model model){
		logger.debug("来自IP[" + request.getRemoteHost() + "]的访问");
		return "login";
	}
	
	/**
	 * 验证用户名和密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkLogin.do")
	public String login(HttpServletRequest request, Model model) {
		String result = "login";
		// 取得用户名
		String username = request.getParameter("user");
		//取得 密码，并用MD5加密
		String password = CipherUtil.generatePassword(request.getParameter("password"));
		//String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject currentUser = SecurityUtils.getSubject();
		try {
			System.out.println("----------------------------");
			if (!currentUser.isAuthenticated()){//使用shiro来验证֤
				//token.setRememberMe(true);取消记住用户功能
				currentUser.login(token);//验证角色和权限
				currentUser.getSession().setAttribute("loginID", username);
			}
			if(username.substring(0, 1).equals("T")) {
				//教师账号
				InfoTeacher infoTeacher = this.infoTeacherService.selectByTeaID(username);
				model.addAttribute("infoUser", infoTeacher);
			} else if(username.substring(0, 1).equals("S")) {
				InfoStudent infoStudent = this.infoStudentService.selectByLoginID(username);
				model.addAttribute("infoUser", infoStudent);
			}
			System.out.println("result: " + result);
			result = "redirect:/index.jsp";//验证成功
		} catch (Exception e) {
			logger.error(e.getMessage());
			result = "login";//验证失败
		}
		return result;
	}
  
    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout")  
    //@ResponseBody  
    public String logout() {  
  
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "redirect:/index.jsp";  
        currentUser.logout();  
        return result;  
    }  
    
    /**
     *
     * @return
     */
    @RequestMapping(value = "/chklogin", method = RequestMethod.POST)  
    @ResponseBody  
    public String chkLogin() {  
        Subject currentUser = SecurityUtils.getSubject();  
        if (!currentUser.isAuthenticated()) {  
            return "false";  
        }  
        return "true";  
    }
    
    @RequestMapping("/resetPassword")
    public String resetPassword() {
    	return "resetPassword";
    }
    
    @RequestMapping("/resetPassword.do")
    public String resetPassworddo(HttpServletRequest request, Model model) {
    	String old = request.getParameter("old");
    	String new1 = request.getParameter("new1");
    	String new2 = request.getParameter("new2");
    	Subject currentUser = SecurityUtils.getSubject();
		String loginID = (String) currentUser.getSession().getAttribute("loginID");
		User user = this.userService.selectByLoginID(loginID);
		if(old.equals(user.getPassword()) && new1.equals(new2)) {
			User u = new User();
			u.setId(user.getId());
			u.setPassword(new1);
			System.out.println(JSON.toJSONString(u));
			try {
				this.adminDoService.userEdit(u);
				//currentUser.logout();
				return "redirect:/index.jsp";
			} catch(Exception e) {
				logger1.error(e.getMessage());
				model.addAttribute("result", "密码更改失败！");
				return "resetPassword";
			}
		} else {
			model.addAttribute("result", "原密码不正确！");
			return "resetPassword";
		}
    }
}
