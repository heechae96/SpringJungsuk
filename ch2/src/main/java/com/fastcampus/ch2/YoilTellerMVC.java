package com.fastcampus.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;

// 년월일을 입력하면 요일을 알려주는 프로그램 -> 스프링 패턴을 사용
@Controller
public class YoilTellerMVC {

	@RequestMapping("/getYoilMVC") // http://localhost/ch2/getYoilMVC?year=2021&month=10&day=1
//	public String main(int year, int month, int day, Model model) throws IOException {
//	public void main(int year, int month, int day, Model model) throws IOException {
	public ModelAndView main(int year, int month, int day) throws IOException {
		
		ModelAndView mv = new ModelAndView();
		
		// 1. 유효성 검사
//		if(!isValid(year, month, day)) {
//			return "yoilError";		//	/WEB-INF/views/yoilError.jsp
//		}
		
		// 2. 요일 계산
		char yoil = getYoil(year, month, day);
		
		// 3. 계산한 결과를 model에 저장
//		model.addAttribute("year", year);
//		model.addAttribute("month", month);
//		model.addAttribute("day", day);
//		model.addAttribute("yoil", yoil);

		mv.addObject("year", year);
		mv.addObject("month", month);
		mv.addObject("day", day);
		mv.addObject("yoil", yoil);
		
//		return "yoil";	//	/WEB-INF/views/yoil.jsp
		
		// 4. 결과를 보여줄 view를 지정
		mv.setViewName("yoil");
		
		return mv; 

	}

	private boolean isValid(int year, int month, int day) {
//		return false;	// 항상 yoilError페이지
		return true;	
	}

	private char getYoil(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);	// 1: 일요일, 2: 월요일, ...
		return " 일월화수목금토".charAt(dayOfWeek);
	}
}