package InOut;

import java.util.Scanner;

public class InConsole extends Input {

	@Override
	public String inconsole() {
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

	@Override
	public String infile() { return null;}
	
}
