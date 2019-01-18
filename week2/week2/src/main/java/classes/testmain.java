package classes;

import java.util.Scanner;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class testmain {

	public static void main(String[] args) {
		
		GenericXmlApplicationContext ac = new GenericXmlApplicationContext("classes/applicationContext.xml");
		InputOutput io = ac.getBean("io",InputOutput.class);
		
		io.countletterAndPrint();
		
		
		
	}
}