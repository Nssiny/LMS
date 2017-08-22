package org.sysu.testController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.pojo.InfoBook;
import org.sysu.pojo.User;
import org.sysu.pojo.UserRole;
import org.sysu.service.AdminDoService;
import org.sysu.service.InfoBookService;
import org.sysu.service.RolePermissionService;
import org.sysu.service.UserRoleService;
import org.sysu.service.UserService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class OperationTest {
	private static Logger logger = Logger.getLogger(OperationTest.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Test
	public void test1() {
		User user = new User();
		if(user != null) {
			System.out.println("ss");
		}
		System.out.println("aa");
		
	}
}
