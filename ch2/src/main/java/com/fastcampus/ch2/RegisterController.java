package com.fastcampus.ch2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
	
	// 에러가 나지 않도록 타입 변환
	@InitBinder
	public void toDate(WebDataBinder binder) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
		//											특정 이름의 필드를 지정 가능 ex) hobby
		binder.registerCustomEditor(String[].class, "hobby", new StringArrayPropertyEditor("#"));
		
		// 단방향 타입 변환 목록 출력
		ConversionService conversionService =  binder.getConversionService();
		System.out.println("conversionService = " + conversionService);
	}
	
//	@RequestMapping(value="/register/add", method= {RequestMethod.GET, RequestMethod.POST})	// 신규회원 가입 화면
	@RequestMapping("/register/add")	// 신규회원 가입 화면
//	@GetMapping("/register/add")
	public String register() {
		return "registerForm";	// WEB-INF/views/registerForm.jsp
	}
	
//	@RequestMapping(value="/register/save", method=RequestMethod.POST)	// POST방식만 허용. GET방식은 허용하지 않음
	// 위에 코드가 너무 길기 때문에 간략하게!
	@PostMapping("/register/save")	// 4.3부터 가능
	public String save(User user, BindingResult result, Model m) throws Exception {
		//							순서조심! 반드시 바인딩할 객체 바로 뒤에 오도록
		System.out.println("result: " + result);
		System.out.println("user: " +  user);
		
		// 1. 유효성 검사
		if(!isValid(user)) {
			String msg = URLEncoder.encode("id를 잘못 입력하였습니다", "utf-8");	// 브라우져에 한글을 직접 입력하는것과 다르게 컨트롤러를 통해 url을 만들면, 브라우져가 인코딩이 불가함
			
//			m.addAttribute("msg", msg);
//			return "redirect:/register/add";
			
			// 위 두 줄을 한번에 해결
			return "forward:/register/add?msg="+msg;	// URL 재작성(rewriting)
		}
		
		// 2. DB에 신규회원 정보를 저장
		//		-> 뒤에서 배울것.. 생략!
		return "registerInfo";	// WEB-INF/views/registerInfo.jsp
	}

	private boolean isValid(User user) {
		return true;
	}
}
