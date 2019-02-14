package Count;

public class WordCount {
	
	private int n = 0;
	
	public int countletter(String message) {
		
		//반복문을 parameter로 받은 message의 길이만큼 수행한다.
		for(int i=0; i<message.length(); i++) {
			//반복한 횟수만큼 n갯수 추가
			n++;
		}
		
		//n의 숫자 반환
		return n;
	}
}