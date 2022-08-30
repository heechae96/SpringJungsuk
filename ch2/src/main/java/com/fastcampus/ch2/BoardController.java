package com.fastcampus.ch2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		if(!loginCheck(request)) {
										// URL : 전체 주소 다 나옴. URI : ContextRoot부터 나옴
			return "redirect:/login/login?toURL=" + request.getRequestURL();		// 로그인을 안했으면 로그인 화면으로 이동
		}
		
		return "boardList";		// 로그인을 한 상태이면, 게시판 화면으로 이동
	}

	private boolean loginCheck(HttpServletRequest request) {
		// 1. 세션을 얻는다
		HttpSession session = request.getSession();
		// 2. 세션에 id가 있는지 확인
//		if(session.getAttribute("id") != null) {
//			return true;
//		}else {
//			return false;
//		}
		
		// 한 줄로 간단하게
		// id가 있으면 true를 반환
		return session.getAttribute("id")!=null;
		
	}
}
