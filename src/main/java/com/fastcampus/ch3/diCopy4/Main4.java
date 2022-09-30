package com.fastcampus.ch3.diCopy4;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Car { // 클래스들이 자바 Bean(객체)에 등록된다
//	@Autowired
	@Resource
	Engine engine;
//	@Autowired
//	@Resource
	Door door;

	@Override
	public String toString() {
		return "Car [engine=" + engine + ", door=" + door + "]";
	}

}

@Component
class SportsCar extends Car {
}

@Component
class Truck extends Car {
}

@Component
class Engine { // @Component빼면 등록이 안된다
}

@Component
class Door {
}

class AppContext {
	Map map; // 객체 저장소

	AppContext() {
		map = new HashMap();
		doComponentScan();
		doAutowired();
		doResource();
	}
	
	private void doResource() {
		// map에 저장된 객체의 iv중 @Resource가 붙어있으면
		// map에서 iv의 이름에 맞는 객체를 찾아서 연결(객체의 주소를 iv에 저장)
		try {
			for (Object bean : map.values()) {
				for (Field fld : bean.getClass().getDeclaredFields()) {
					if (fld.getAnnotation(Resource.class) != null) { // by Name
						fld.set(bean, getBean(fld.getType())); // car.engine = obj
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAutowired() {	// 자동으로 객체 연결
		// map에 저장된 객체의 iv중 @Autowired가 붙어있으면
		// map에서 iv의 타입에 맞는 객체를 찾아서 연결(객체의 주소를 iv에 저장)
		try {
			for (Object bean : map.values()) {
				for (Field fld : bean.getClass().getDeclaredFields()) {
					if (fld.getAnnotation(Autowired.class) != null) { // by Type
						fld.set(bean, getBean(fld.getType())); // car.engine = obj
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doComponentScan() { // Guava(구아바)라이브러리 필요. pom.xml에 추가

		try {
			// 1. 패키지내의 클래스 목록을 가져온다
			// 2. 반복문으로 클래스를 하나씩 읽어와서 @Component가 붙어 있는지 확인
			// 3. @Component가 붙어있으면 객체를 생성해서 map에 저장
			ClassLoader classLoader = AppContext.class.getClassLoader();
			ClassPath classPath = ClassPath.from(classLoader);

			Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

			for (ClassPath.ClassInfo classInfo : set) {
				Class clazz = classInfo.load();
				Component component = (Component) clazz.getAnnotation(Component.class);

				if (component != null) { // @Component가 붙어있는 경우
					String id = StringUtils.uncapitalize(classInfo.getSimpleName());
					map.put(id, clazz.newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Object getBean(String key) { // by Name
		return map.get(key);
	}

	Object getBean(Class clazz) { // by Type
		for (Object obj : map.values()) {
			if (clazz.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}

}

public class Main4 {

	public static void main(String[] args) throws Exception {

		AppContext ac = new AppContext();

		Car car = (Car) ac.getBean("car"); // by Name으로 객체를 검색
		Engine engine = (Engine) ac.getBean("engine");
		Door door = (Door) ac.getBean(Door.class);

		// 수동으로 객체를 연결
//		car.engine = engine;
//		car.door = door;

		System.out.println("car = " + car);
		System.out.println("engine = " + engine);
		System.out.println("door = " + door);

	}

}
