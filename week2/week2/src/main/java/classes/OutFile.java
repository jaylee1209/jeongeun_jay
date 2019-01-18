package classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class OutFile implements Output {
	
	private String path = null;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	@Override
	public void out(int countedletter) {
		try {
			//파일에 글을 입력하기위해 지정된 경로로 FileWriter객체 생성
			FileWriter output = new FileWriter(this.path);
			
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
