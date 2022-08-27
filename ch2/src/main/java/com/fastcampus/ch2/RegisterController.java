package com.fastcampus.ch2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
	
//	@RequestMapping(value="/register/add", method= {RequestMethod.GET, RequestMethod.POST})	// 신규회원 가입 화면
//	@RequestMapping("/register/add")	// 신규회원 가입 화면
//	@GetMapping("/register/add")
//	public String register() {
//		return "registerForm";	// WEB-INF/views/registerForm.jsp
//	}
	
//	@RequestMapping(value="/register/save", method=RequestMethod.POST)	// POST방식만 허용. GET방식은 허용하지 않음
	// 위에 코드가 너무 길기 때문에 간략하게!
	@PostMapping("/register/save")	// 4.3부터 가능
	public String save(User user, Model m) throws Exception {
		// 1. 유효성 검사
		if(!isValid(user)) {
			String msg = URLEncoder.encode("id를 잘못 입력하였습니다", "utf-8");	// 브라우져에 한글을 직접 입력하는것과 다르게 컨트롤러를 통해 url을 만들면, 브라우져가 인코딩이 불가함
			
//			m.addAttribute("msg", msg);
//			return "redirect:/register/add";
			
			// 위 두 줄을 한번에 해결
			return "redirect:/register/add?msg="+msg;	// URL 재작성(rewriting)
		}
		
		// 2. DB에 신규회원 정보를 저장
		//		-> 뒤에서 배울것.. 생략!
		return "registerInfo";	// WEB-INF/views/registerInfo.jsp
	}

	private boolean isValid(User user) {
		return true;
	}
}
