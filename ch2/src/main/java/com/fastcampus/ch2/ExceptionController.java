package com.fastcampus.ch2;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController { // 컨트롤러 내에서만 사용 가능

	// 예외 처리를 중복하지 않기 위해
	// NullPointerException, FileNotFoundException을 제외하고 다 여기서 처리
	@ExceptionHandler(Exception.class)
	public String catcher(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
		// 동일한 모델을 받는지 테스트
		System.out.println("m=" + m);

		System.out.println("catcher() in ExcecptionController");
		m.addAttribute("ex", ex);
		return "error";
	}

	@ExceptionHandler({ NullPointerException.class, FileNotFoundException.class })
	public String catcher2(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
		m.addAttribute("ex", ex);
		return "error";
	}

	@RequestMapping("/ex")
	public String main(Model m) throws Exception {
		m.addAttribute("msg", "message from ExceptionController.main()");
		throw new Exception("예외가 발생했습니다");
	}

	@RequestMapping("/ex2")
	public String main2() throws Exception {
		throw new NullPointerException("예외가 발생했습니다");
	}

	@RequestMapping("/ex3")
	public String main3() throws Exception {
		throw new FileNotFoundException("예외가 발생했습니다");
	}

//	@RequestMapping("/ex")
//	public String main() throws Exception {
//		try {
//			throw new Exception("예외가 발생했습니다");	// 500번대 서버 에러 발생
//		} catch (Exception e) {
//			// 예외 처리
//			// 	반환타입이 void이므로 ex.jsp로 간다.
//			// 	400번대 클라이언트 에러 발생
//			//		반환타입 String으로 바꾸고 error.jsp로 가도록
//			return "error";
//		}
//	}
//	
//	@RequestMapping("/ex2")
//	public String main2() throws Exception {
//		try {
//			throw new Exception("예외가 발생했습니다");	// 500번대 서버 에러 발생
//		} catch (Exception e) {
//			// 예외 처리
//			// 	반환타입이 void이므로 ex.jsp로 간다.
//			// 	400번대 클라이언트 에러 발생
//			//		반환타입 String으로 바꾸고 error.jsp로 가도록
//			return "error";
//		}
//	}
}
