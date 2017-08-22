package org.sysu.testController;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.pojo.User;
import org.sysu.service.ReaderDoService;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserControllerTest {
	private static Logger logger = Logger.getLogger(UserControllerTest.class);
	@Autowired
	private ReaderDoService readerDoService;
	
	@Test
	public void test1() {
		try{
			readerDoService.rreservationdo("S13366014", "Z0220296");
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("预约失败！");
		}
	}
	
}
