package org.sysu.testController;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.service.ReaderDoService;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class RReborrowTest {
	private static Logger logger = Logger.getLogger(UserControllerTest.class);
	@Autowired
	private ReaderDoService readerDoService;
	
	@Test
	public void test1() {
		String loginID = "S13366014";
		Long rbid = (long) 3;
		try{
			this.readerDoService.rreborrowdo(loginID, rbid);
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("续借失败！");
		}
	}
	
}
