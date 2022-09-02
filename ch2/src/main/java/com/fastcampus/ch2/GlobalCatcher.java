package com.fastcampus.ch2;

import java.io.FileNotFoundException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//모든 컨트롤러에서 발생하는 예외를 잡을수 있는 애너테이션
//@ControllerAdvice	// 모든 패키지에 발생한 예외 처리
@ControllerAdvice("com.fastcampus.ch3")	// 지정된 패키지에서 발생한 예외만 처리 // 일부러 ch3
public class GlobalCatcher {

	// 예외 처리를 중복하지 않기 위해
	// NullPointerException, FileNotFoundException을 제외하고 다 여기서 처리
	@ExceptionHandler(Exception.class)
	public String catcher(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
		System.out.println("catcher() in GlobalCatcher");
		m.addAttribute("ex", ex);
		return "error";
	}

	@ExceptionHandler({ NullPointerException.class, FileNotFoundException.class })
	public String catcher2(Exception ex, Model m) { // catch블럭이라고 생각하면 된다
		m.addAttribute("ex", ex);
		return "error";
	}
}
