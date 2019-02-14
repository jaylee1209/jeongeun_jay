package InOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class InFile extends Input{

	@Override
	public String inconsole() { return null;}

	@Override
	public String infile() {
		//읽어올 파일의 해당 위치로 File객체 생성
		File file    =  new File("C:\\Users\\meta\\Desktop\\test1.txt");
		
		//변수 초기화
		String val = "";
		String line = null;
		BufferedReader br;

		//euc-kr 인코딩 방법으로 파일을 읽어 문자열로 변형시켜 BufferedReader에 담는다
		try {
		  br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
			
		  	//while문을 돌며 line 이 null이 아니면은 계속 반복문을 돈다
			while((line = br.readLine()) != null) {
				//반복문을 돌며 읽은 line의 값을 val에 추가한다.
				val += line;
			}

			//BufferedReader를 닫는다.
			br.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}

		return val;
	}
}
