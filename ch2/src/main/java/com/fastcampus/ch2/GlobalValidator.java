package com.fastcampus.ch2;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GlobalValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) { // 검증이 가능한가?
		return User.class.equals(clazz); // 검증하려는 객체가 User타입인지 확인
//			return User.class.isAssignableFrom(clazz); // clazz가 User 또는 그 자손인지 확인
	}

	@Override
	public void validate(Object target, Errors errors) {	// 검증할 객체, 검증시 발생한 에러 저장소

		// 위에서 검증했기 때문에 target이 User의 인스턴스인지 확인할 필요가 없다
		System.out.println("GlobalValidator.validate() is called");

		User user = (User) target;

		String id = user.getId();

		// if(id==null || "".equals(id.trim())) {
		// errors.rejectValue("id", "required");
		// }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

		if (id == null || id.length() < 5 || id.length() > 12) {
			//														0부터 시작해서 빈문자 추가
			errors.rejectValue("id", "invalidLength", new String[] {"", "5", "12"}, null);
		}
	}
}