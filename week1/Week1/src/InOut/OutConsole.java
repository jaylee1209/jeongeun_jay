package InOut;

public class OutConsole extends Output{
	
	@Override
	public void outconsole(int wordcount) {
		System.out.println("입력한 값의 갯수는:" + wordcount + "입니다.");
	}

	@Override
	public void outfile(int countedletter) {}

}
