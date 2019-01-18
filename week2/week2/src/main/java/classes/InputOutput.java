package classes;

import org.springframework.beans.factory.annotation.Autowired;

public class InputOutput {
	private Input input;
	private Output output;
	String message =null;

	InputOutput(Input input, Output output){
		this.input= input;
		this.output = output;
		message = input.in();
	}
	
	public void countletterAndPrint() {
		int n = 0;
		//반복문을 parameter로 받은 message의 길이만큼 수행한다.
		for(int i=0; i<message.length(); i++) {
			//반복한 횟수만큼 n갯수 추가
			n++;
		}
		output.out(n);
	}
	
}