package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

//@Component - @Controller, @Repository, @Service, @ControllerAdvice
@Repository	// 메타애너테이션으로 Component가 붙어있다
public class UserDaoImpl implements UserDao {// 예외를 선언하기 보다 메서드들이 예외를 처리할수 있게 try-catch로 묶어주었다
	@Autowired
	DataSource ds;
	final int FAIL = 0;

	@Override
	public int deleteUser(String id) {
		int rowCnt = FAIL; // insert, delete, update

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from user_info where id= ? ";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
//        int rowCnt = pstmt.executeUpdate(); //  insert, delete, update
//        return rowCnt;
			return pstmt.executeUpdate(); // insert, delete, update
		} catch (SQLException e) {
			e.printStackTrace();
			return FAIL;
		} finally {
			// 메모리가 제대로 반환되지 않으면, 메모리 부족으로 이어지므로 닫아줘야한다.
			// close()를 호출하다가 예외가 발생할 수 있으므로, try-catch로 감싸야함.
//            try { if(pstmt!=null) pstmt.close(); } catch (SQLException e) { e.printStackTrace();}
//            try { if(conn!=null)  conn.close();  } catch (SQLException e) { e.printStackTrace();}
			close(pstmt, conn); // private void close(AutoCloseable... acs) {
		}
	}

	@Override
	public User selectUser(String id){
		User user = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from user_info where id= ? ";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
			pstmt.setString(1, id);

			rs = pstmt.executeQuery(); // select

			if (rs.next()) {
				user = new User();
				user.setId(rs.getString(1));
				user.setPwd(rs.getString(2));
				user.setName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setBirth(new Date(rs.getDate(5).getTime()));
				user.setSns(rs.getString(6));
				user.setReg_date(new Date(rs.getTimestamp(7).getTime()));
			}
		} catch (SQLException e) {
			return null;
		} finally {
			// close()를 호출하다가 예외가 발생할 수 있으므로, try-catch로 감싸야함.
			// close()의 호출순서는 생성된 순서의 역순
//            try { if(rs!=null)    rs.close();    } catch (SQLException e) { e.printStackTrace();}
//            try { if(pstmt!=null) pstmt.close(); } catch (SQLException e) { e.printStackTrace();}
//            try { if(conn!=null)  conn.close();  } catch (SQLException e) { e.printStackTrace();}
			close(rs, pstmt, conn); // private void close(AutoCloseable... acs) {
		}

		return user;
	}

	// 사용자 정보를 user_info테이블에 저장하는 메서드
	@Override
	public int insertUser(User user) {
		int rowCnt = FAIL;

		Connection conn = null;
		PreparedStatement pstmt = null;

//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf22', '1234', 'smith', 'aaa@aaa.com', '2022-01-01', 'facebook', now());
		String sql = "insert into user_info values (?, ?, ?, ?,?,?, now()) ";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPwd());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
			pstmt.setString(6, user.getSns());

			return pstmt.executeUpdate(); // insert, delete, update;
		} catch (SQLException e) {
			e.printStackTrace();
			return FAIL;
		} finally {
			close(pstmt, conn); // private void close(AutoCloseable... acs) {
		}
	}

	// 매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
	@Override
	public int updateUser(User user) {
		int rowCnt = FAIL; // insert, delete, update

//        Connection conn = null;
//        PreparedStatement pstmt = null;

		String sql = "update user_info " + "set pwd = ?, name=?, email=?, birth =?, sns=?, reg_date=? "
				+ "where id = ? ";

		// try-with-resources - since jdk7
		// finally가 없는 형태
		// close로 자원을 반환했는데 try-with-resources를 사용하면 자원의 자동 반환이 가능하다
		// 단, AutoCloseable를 구현한 객체들만 사용가능
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); // SQL
																											// Injection공격,
																											// 성능향상
		) {
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));	// 날짜 변환 유의. util.Date -> sql.Date
			pstmt.setString(5, user.getSns());
			pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));	// Timestamp: Date뿐만 아니라 Time도 넣기 위해
			pstmt.setString(7, user.getId());

			rowCnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return FAIL;
		}

		return rowCnt;
	}

	public void deleteAll() throws Exception {
		Connection conn = ds.getConnection();

		String sql = "delete from user_info ";

		PreparedStatement pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
		pstmt.executeUpdate(); // insert, delete, update
	}

	private void close(AutoCloseable... acs) {	// try-catch문 여러번 돌릴수 있도록 한 것. 매개변수에 가변인자가 들어가서 인자로 여러개 들어갈수 있다
		for (AutoCloseable ac : acs)	// 배열로 바뀌어서 들어간후 반복문 실행
			try {
				if (ac != null)
					ac.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}