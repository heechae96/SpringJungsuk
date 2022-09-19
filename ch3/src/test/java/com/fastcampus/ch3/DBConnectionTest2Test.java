package com.fastcampus.ch3;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class DBConnectionTest2Test {

	@Autowired
	DataSource ds; // 인스턴스 변수이긴 하지만 같은 클래스에 있는 테스트 메소드들이 이 소스를 공유하지는 않는다

	// 각 테스트는 다른 테스트와 독립적이어야 한다!
	// 여러번 테스트를 실행해도 실패하면 안된다!
	// -> 테스트가 성공 할 수 있도록 코드를 작성해야 한다

	@Test
	public void transactionTest() throws Exception {

		Connection conn = null;

		try {
			deleteAll();
			conn = ds.getConnection();
			conn.setAutoCommit(false); // 디폴트가 true

			// insert into user_info (id, pwd, name, email, birth, sns, reg_date) values
			// ('asdf', '1234', 'shin', 'shin@aaa.com', '1996-01-05', 'facebook', now())

			// 값이 들어갈 곳을 물음표 처리
			String sql = "insert into user_info values (?,?,?,?,?,?,now())";
			PreparedStatement pstmt = conn.prepareStatement(sql); // Statement와 다르게 SQL Injection 공격에 방어, 성능 향상

			// 물음표에 값을 채우기
			pstmt.setString(1, "asdf");
			pstmt.setString(2, "1234");
			pstmt.setString(3, "abc");
			pstmt.setString(4, "aaa@aaa.com");
			pstmt.setDate(5, new java.sql.Date(new Date().getTime())); // sql.Date -> Date
			pstmt.setString(6, "fb");

			// 반환값이 int. 실패하면 0, 성공하면 반영된 행(row)만큼.
			// select문을 제외한 insert, update, delete만 가능. select는 executeQuery로.
			int rowCnt = pstmt.executeUpdate();

			pstmt.setString(1, "asdf2");
			rowCnt = pstmt.executeUpdate();

			conn.commit(); // 수동으로 커밋

		} catch (Exception e) {
			conn.rollback(); // 예외 발생하면 롤백
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}

	}

	@Test
	public void springJdbcConnectionTest() throws Exception {
//		ApplicationContext ac = new GenericXmlApplicationContext(
//				"file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//		DataSource ds = ac.getBean(DataSource.class);

		Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

		System.out.println("conn = " + conn);
		assertTrue(conn != null); // 괄호안의 조건식인 true면 테스트 성공. false면 테스트 실패
	}

	@Test
	public void seletUserTest() throws Exception {
		deleteAll();
		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
		insertUser(user);

		User user2 = selectUser("asdf2");

		assertTrue(user2.getId().equals("asdf2")); // user랑 같은게 user2에 들어가있는것
	}

	@Test
	public void deleteUserTest() throws Exception {
		deleteAll();
		int rowCnt = deleteUser("asdf2");
		assertTrue(rowCnt == 0);

		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
		rowCnt = insertUser(user);
		assertTrue(rowCnt == 1);

		rowCnt = deleteUser(user.getId());
		assertTrue(rowCnt == 1);

		assertTrue(selectUser(user.getId()) == null);
	}

	@Test
	public void insertUserTest() throws Exception {
		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
		deleteAll(); // 기존의 데이터가 삭제됨. 반복해도 에러x
		int rowCnt = insertUser(user); // insert문이니 1이 나올것

		assertTrue(rowCnt == 1);
	}

	// updateUser 수정하면서 좀 더 손보기
	@Test
	public void updateUserTest() throws Exception {
		deleteAll();
		int rowCnt = deleteUser("asdf2");
		assertTrue(rowCnt == 0);

		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
		rowCnt = insertUser(user);
		assertTrue(rowCnt == 1);

		String id = "asdf2";
		rowCnt = updateUser(id);
		assertTrue(rowCnt == 1);

		User user2 = selectUser("asdf2");

		assertTrue(!user2.getPwd().equals("1234"));
	}

	public User selectUser(String id) throws Exception {
		Connection conn = ds.getConnection();

		String sql = "select * from user_info where id = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id); // 주의! 인덱스 아님

		// 2차원 테이블 형태
		ResultSet rs = pstmt.executeQuery(); // excuteQuery는 ResultSet라는 객체로 돌려줌

		if (rs.next()) { // 조회한 결과가 있는 경우
			User user = new User();
			user.setId(rs.getString(1));
			user.setPwd(rs.getString(2));
			user.setName(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setBirth(new Date(rs.getDate(5).getTime()));
			user.setSns(rs.getString(6));
			user.setReg_date(new Date(rs.getDate(7).getTime()));

			return user;
		}

		return null; // 조회한 결과가 없는 경우
	}

	public int deleteUser(String id) throws Exception {
		Connection conn = ds.getConnection();

		String sql = "delete from user_info where id = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);

		return pstmt.executeUpdate();
	}

	private void deleteAll() throws Exception {
		Connection conn = ds.getConnection();

		String sql = "delete from user_info";

		PreparedStatement pstmt = conn.prepareStatement(sql); // Statement와 다르게 SQL Injection 공격에 방어, 성능 향상

		pstmt.executeUpdate(); // insert, delete, update
	}

	// 사용자 정보를 user_info테이블에 저장하는 메소드
	public int insertUser(User user) throws Exception {
		Connection conn = ds.getConnection();

//		insert into user_info (id, pwd, name, email, birth, sns, reg_date) values ('asdf', '1234', 'shin', 'shin@aaa.com', '1996-01-05', 'facebook', now())
		// 값이 들어갈 곳을 물음표 처리
		String sql = "insert into user_info values (?,?,?,?,?,?,now())";

		PreparedStatement pstmt = conn.prepareStatement(sql); // Statement와 다르게 SQL Injection 공격에 방어, 성능 향상

		// 물음표에 값을 채우기
		pstmt.setString(1, user.getId());
		pstmt.setString(2, user.getPwd());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getEmail());
		pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime())); // sql.Date -> Date
		pstmt.setString(6, user.getSns());

		// 반환값이 int. 실패하면 0, 성공하면 반영된 행(row)만큼.
		// select문을 제외한 insert, update, delete만 가능. select는 executeQuery로.
		int rowCnt = pstmt.executeUpdate();

		return rowCnt;
	}

	// 매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
	// 과제로 부여받은것. 매개변수를 User user로 할 수 있는법 생각해보자!
	public int updateUser(String id) throws Exception {
		Connection conn = ds.getConnection();

		String sql = "update user_info set pwd='5678', name='def' where id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, id);

		int rowCnt = pstmt.executeUpdate();

		return rowCnt;
	}

}
