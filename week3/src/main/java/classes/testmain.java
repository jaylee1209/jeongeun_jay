package classes;

import java.util.Scanner;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class testmain {

	public static void main(String[] args) {
		 
		//Bean 객체 생성
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext("classes/applicationContext.xml");
		
		//InputOutput Bean 갖고오기
		InputOutput io = ac.getBean("io",InputOutput.class);
		
		//countletterAndPrint() 메소드 실행
		io.countletterAndPrint();
		
		//컨테이너 종료
		ac.close();
	}
}