package classes;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class InConsole implements Input {
	String message;
	
	@Override
	public String in() {
		
		System.out.print("입력해주세요: ");
		
		//Console에서 입력을 받기 위해 Scanner객체 사용
		Scanner sc = new Scanner(System.in);
		
		//입력받은 값을 message 라는 문자열 값에 대입
		String message = sc.nextLine();
//		System.out.println(message);
		
		//Scanner객체 종료
		sc.close();
		
		//message라는 문자열 반환
		return message;
	}

	
}
