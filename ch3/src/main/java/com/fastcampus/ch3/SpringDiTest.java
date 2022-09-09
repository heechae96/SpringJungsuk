package com.fastcampus.ch3;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component 
class Engine{}
@Component class SuperEngine extends Engine{}	// <bean id="engine" class="com.fastcampus.ch3.Engine"/>
@Component class TurboEngine extends Engine{}
@Component class Door{}
@Component
class Car{
	@Value("red") String color;
	@Value("100") int oil;
//	@Autowired	// by Type
//	@Qualifier("superEngine")	// by Name
	@Resource(name="superEngine")	// Autowired랑 Qualifier을 대체	// by Name
	Engine engine;	// by Type. @Autowired는 타입으로 먼저 검색, 여러개면 이름으로 검색(engine, super-, turbo-)
	@Autowired Door[] doors;
	
	public Car() {}	// 기본 생성자를 꼭 만들어주자!
	
	// constructor-arg에 이용
	public Car(String color, int oil, Engine engine, Door[] doors) {
		super();
		this.color = color;
		this.oil = oil;
		this.engine = engine;
		this.doors = doors;
	}
	
	// property에 이용
	public void setColor(String color) {
		this.color = color;
	}
	public void setOil(int oil) {
		this.oil = oil;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	public void setDoors(Door[] doors) {
		this.doors = doors;
	}
	
	@Override
	public String toString() {
		return "Car [color=" + color + ", oil=" + oil + ", engine=" + engine + ", doors=" + Arrays.toString(doors)
				+ "]";
	}
	
}

public class SpringDiTest {
	public static void main(String[] args) {
		
		ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
		
//		Car car = (Car)ac.getBean("car");	// by Name
		Car car = ac.getBean("car", Car.class);	// by Name, by Type 같이 사용하면 형변환이 필요없다
//		Car car2 = (Car)ac.getBean(Car.class);	// by Type
		
//		Engine engine = (Engine)ac.getBean("engine");	// by Name
//		Engine engine = (Engine)ac.getBean(Engine.class);	// by Type	// (Engine,Turbo,Super)같은 타입이 3개라서 에러
//		Door door = (Door)ac.getBean("door");
		
//		System.out.println("car = " + car);
//		System.out.println("car2 = " + car2);
//		System.out.println("engine = " + engine);
//		System.out.println("door = " + door);
		
//		car.setColor("red");
//		car.setOil(100);
//		car.setEngine(engine);
//		car.setDoors(new Door[] {ac.getBean("door", Door.class), (Door)ac.getBean("door")});	// scope="prototype" => 다른 객체를 생성 할 수 있도록
		System.out.println("car = " + car);
//		System.out.println("engine = " + engine);
	}
}
