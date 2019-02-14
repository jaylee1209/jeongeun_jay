package InOut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class OutFile extends Output {
	
	@Override
	public void outconsole(int wordcount) {}

	@Override
	public void outfile(int countedletter) {
		try {
			//파일에 글을 입력하기위해 지정된 경로로 FileWriter객체 생성
			FileWriter output = new FileWriter("C:\\Users\\meta\\Desktop\\test.txt");
			
			//세어진 
			String data = countedletter + " ";
			data = data.trim();
			output.write(data + "개의 문자를 입력받았습니다.");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
