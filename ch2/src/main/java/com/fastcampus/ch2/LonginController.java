package com.fastcampus.ch2;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LonginController {

	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {	// session을 HttpServletRequest말고 HttpSession을 통해 직접 받을수 있다(스프링이 자동으로 넣어주는것)
		// 1. 세션을 종료
		session.invalidate();
		// 2. 홈으로 이동
		return "redirect:/";
	}

	@PostMapping("/login")
	public String login(String id, String pwd, boolean rememberId, 
							HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 쿼리스트링 값들이 어떤식으로 들어오는가 확인
		// rememberId(type="checkbox")를 String으로 하면 on이 찍힘. 디폴트 value값이 on.
		// boolean으로 하면 체크의 경우. true / 체크가 안 된 경우. false
//		System.out.println("id: " + id + ", pwd: " + pwd + ", rememberId: " + rememberId);

		// 1. id와 pwd를 확인
		if (!loginCheck(id, pwd)) {
			// 2-1. 일치하지 않으면, loginForm으로 이동
			String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

			return "redirect:/login/login?msg=" + msg;
		}

		// 2-2. i와 pwd가 일치하면,
		//	-> 세션 객체를 얻어오기
		HttpSession session = request.getSession();
		//	->세션 객체에 id를 저장
		session.setAttribute("id", id);
		
		if (rememberId) { // 아이디 기억 체크박스 선택
			// 1. 쿠키를 생성
			Cookie cookie = new Cookie("id", id);
			// 2. 응답에 저장
			response.addCookie(cookie);
		} else { // 아이디 기억 체크박스 해제
			// 1. 쿠키를 생성
			Cookie cookie = new Cookie("id", id);
			// 2. 쿠키를 삭제
			cookie.setMaxAge(0);
			// 3. 응답에 저장
			response.addCookie(cookie);
		}
		// 3. 홈으로
		return "redirect:/";
	}

	private boolean loginCheck(String id, String pwd) {
		// null체크 필요없다
		return "asdf".equals(id) && "1234".equals(pwd);
	}
}
