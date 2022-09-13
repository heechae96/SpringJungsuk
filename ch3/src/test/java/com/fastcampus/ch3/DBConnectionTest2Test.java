package com.fastcampus.ch3;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {

	@Autowired
	DataSource ds;
	
	@Test
	public void springJdbcConnectionTest() throws Exception {
//		ApplicationContext ac = new GenericXmlApplicationContext(
//				"file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//		DataSource ds = ac.getBean(DataSource.class);

		Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

		System.out.println("conn = " + conn);
		assertTrue(conn != null);	// 괄호안의 조건식인 true면 테스트 성공. false면 테스트 실패
	}

}
