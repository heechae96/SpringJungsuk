package com.fastcampus.ch2;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

// 사용자 정의 예외
@ResponseStatus(HttpStatus.BAD_REQUEST)	// 500 -> 400
class MyException extends RuntimeException{
	MyException(String msg){
		super(msg);
	}
	
	MyException(){
		this("");
	}
}

@Controller
public class ExceptionController2 {	// 컨트롤러 내에서만 작동하므로 (ExceptionController와)중복이 생긴 경우 

//	// 예외 처리를 중복하지 않기 위해
//	// NullPointerException, FileNotFoundException을 제외하고 다 여기서 처리
//	@ExceptionHandler(Exception.class)
//	public String catcher(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
//		m.addAttribute("ex", ex);
//		return "error";
//	}
//
//	@ExceptionHandler({ NullPointerException.class, FileNotFoundException.class })
//	public String catcher2(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
//		m.addAttribute("ex", ex);
//		return "error";
//	}

	@RequestMapping("/ex4")
	public String main() throws Exception {
		throw new MyException("예외가 발생했습니다");
	}

	@RequestMapping("/ex5")
	public String main2() throws Exception {
		throw new NullPointerException("예외가 발생했습니다");
	}

	@RequestMapping("/ex6")
	public String main3() throws Exception {
		throw new FileNotFoundException("예외가 발생했습니다");
	}

}
