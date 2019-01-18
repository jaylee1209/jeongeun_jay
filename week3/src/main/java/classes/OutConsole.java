package classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class OutConsole implements Output{
	
	@Override
	public void out(int wordcount) {
		System.out.println("입력한 값의 갯수는:" + wordcount + "입니다.");
	}

}
