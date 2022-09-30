package com.fastcampus.ch3;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class UserDaoImplTest {
	@Autowired
	UserDao userDao;

	@Test
	public void testDeleteUser() {
	}

	@Test
	public void testSelectUser() {
	}

	@Test
	public void testInsertUser() {
	}

	// 이전 메서드들은 앞에서 테스트 해봤음.
	@Test
	public void testUpdateUser() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.clear();	// 모든 필드 초기화
		cal.set(2021, 1, 1);	// 뒤에 시간은 일부러 자르기 위해 설정
		
		userDao.deleteAll();
		User user = new User("asdf", "1234", "abc", "aaa@aaa.com", new Date(cal.getTimeInMillis()), "fb", new Date());
		int rowCnt = userDao.insertUser(user);
		assertTrue(rowCnt==1);
		
		user.setPwd("4321");
		user.setEmail("bbb@bbb.com");
		rowCnt = userDao.updateUser(user);
		assertTrue(rowCnt==1);
		
		// 업데이트가 되었어도 업데이트 내용을 볼 것
		User user2 = userDao.selectUser(user.getId());
		
		System.out.println("user = " + user);
		System.out.println("user2 = " + user2);
		
		assertTrue(user.equals(user2));
	}

}
