//package com.fastcampus.ch3;
//
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Component
//@Scope("prototype")
//class Door {
//}
//
//@Component
//class Engine {
//}
//
//@Component
//class TurboEngine extends Engine {
//}
//
//@Component
//class SuperEngine extends Engine {
//}
//
//@Component
//class Car {
//	@Value("red")
//	String color;
//	@Value("100")
//	int oil;
////	@Autowired
//	Engine engine;
////	@Autowired
//	Door[] doors;
//
//	public Car() {
//	}
//
//	@Autowired
//	public Car(@Value("red") String color, @Value("100") int oil, Engine engine, Door[] doors) {
//		this.color = color;
//		this.oil = oil;
//		this.engine = engine;
//		this.doors = doors;
//	}
//
//	@Override
//	public String toString() {
//		return "Car{" + "color='" + color + '\'' + ", oil=" + oil + ", engine=" + engine + ", doors="
//				+ Arrays.toString(doors) + '}';
//	}
//}
//
//@Component
//@PropertySource("setting.properties")
//class SysInfo {
//	@Value("#{systemProperties['user.timezone']}")
//	String timeZone;
//	@Value("#{systemEnvironment['TMPDIR']}")
//	String tempDir;
//	@Value("${autoSaveDir}")
//	String autoSaveDir;
//	@Value("${autoSaveInterval}")
//	int autoSaveInterval;
//	@Value("${autoSave}")
//	boolean autoSave;
//
//	@Override
//	public String toString() {
//		return "SysInfo [timeZone=" + timeZone + ", tempDir=" + tempDir + ", autoSaveDir=" + autoSaveDir
//				+ ", autoSaveInterval=" + autoSaveInterval + ", autoSave=" + autoSave + "]";
//	}
//
//}
//
//public class ApplicationContextTest {
//	public static void main(String[] args) {
//		ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//
//		System.out.println("ac.getBean(SysInfo.class) = " + ac.getBean(SysInfo.class));
//		Map<String, String> map = System.getenv();
//		System.out.println("map = " + map);
//		Properties properties = System.getProperties();
//		System.out.println("properties = " + properties);
//
////      Car car = ac.getBean("car", Car.class); // ????????? ???????????? ????????? ????????????. ????????? ????????? ??????
//		Car car = (Car) ac.getBean("car"); // ???????????? ??? ??????
////        Car car2 = (Car) ac.getBean(Car.class);   // ???????????? ??? ??????
//		System.out.println("car = " + car);
////        System.out.println("car2 = " + car2);
////
////        System.out.println("ac.getBeanDefinitionNames() = " + Arrays.toString(ac.getBeanDefinitionNames())); // ????????? ?????? ????????? ????????? ??????
////        System.out.println("ac.getBeanDefinitionCount() = " + ac.getBeanDefinitionCount()); // ????????? ?????? ????????? ??????
////
////        System.out.println("ac.containsBeanDefinition(\"car\") = " + ac.containsBeanDefinition("car"));  // true ?????? ????????? ???????????? ????????? ??????
////        System.out.println("ac.containsBean(\"car\") = " + ac.containsBean("car")); // true ?????? ???????????? ????????? ??????
////
////        System.out.println("ac.getType(\"car\") = " + ac.getType("car")); // ?????? ???????????? ????????? ????????? ??? ??????.
////        System.out.println("ac.isSingleton(\"car\") = " + ac.isSingleton("car")); // true ?????? ??????????????? ??????
////        System.out.println("ac.isPrototype(\"car\") = " + ac.isPrototype("car")); // false ?????? ????????????????????? ??????
////        System.out.println("ac.isPrototype(\"door\") = " + ac.isPrototype("door")); // true
////        System.out.println("ac.isTypeMatch(\"car\", Car.class) = " + ac.isTypeMatch("car", Car.class)); // "car"?????? ????????? ?????? ????????? Car?????? ??????
////        System.out.println("ac.findAnnotationOnBean(\"car\", Component.class) = " + ac.findAnnotationOnBean("car", Component.class)); // ??? car??? @Component??? ??????????????? ??????
////        System.out.println("ac.getBeanNamesForAnnotation(Component.class) = " + Arrays.toString(ac.getBeanNamesForAnnotation(Component.class))); // @Component??? ?????? ?????? ????????? ????????? ??????
////        System.out.println("ac.getBeanNamesForType(Engine.class) = " + Arrays.toString(ac.getBeanNamesForType(Engine.class))); // Engine ?????? ??? ?????? ????????? ?????? ????????? ????????? ??????
//	}
//}
//
///*
// * [src/main/resources/config.xml] <?xml version="1.0" encoding="UTF-8"?> <beans
// * xmlns="http://www.springframework.org/schema/beans"
// * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
// * xmlns:context="http://www.springframework.org/schema/context" xsi:
// * schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
// * > <context:annotation-config/> <context:component-scan
// * base-package="com.fastcampus.ch3"/> </beans>
// */
//
///*
// * [????????????] car = Car{color='red', oil=100,
// * engine=com.fastcampus.ch3.Engine@df6620a,
// * doors=[com.fastcampus.ch3.Door@205d38da]} car2 = Car{color='red', oil=100,
// * engine=com.fastcampus.ch3.Engine@df6620a,
// * doors=[com.fastcampus.ch3.Door@205d38da]} ac.getBeanDefinitionNames() =
// * [org.springframework.context.annotation.
// * internalConfigurationAnnotationProcessor,
// * org.springframework.context.annotation.internalAutowiredAnnotationProcessor,
// * org.springframework.context.annotation.internalRequiredAnnotationProcessor,
// * org.springframework.context.annotation.internalCommonAnnotationProcessor,
// * org.springframework.context.event.internalEventListenerProcessor,
// * org.springframework.context.event.internalEventListenerFactory, car, door,
// * engine, superEngine, turboEngine] ac.getBeanDefinitionCount() = 11
// * ac.containsBeanDefinition("car") = true ac.containsBean("car") = true
// * ac.getType("car") = class com.fastcampus.ch3.Car ac.isSingleton("car") = true
// * ac.isPrototype("car") = false ac.isPrototype("door") = true
// * ac.isTypeMatch("car", Car.class) = true ac.findAnnotationOnBean("car",
// * Component.class) = @org.springframework.stereotype.Component(value="")
// * ac.getBeanNamesForAnnotation(Component.class) = [car, door, engine,
// * superEngine, turboEngine] ac.getBeanNamesForType(Engine.class) = [engine,
// * superEngine, turboEngine]
// */