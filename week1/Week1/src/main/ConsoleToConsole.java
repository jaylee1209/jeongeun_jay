package main;

import Count.WordCount;
import InOut.InConsole;
import InOut.Input;
import InOut.OutConsole;

public class ConsoleToConsole {

	public static void main(String[] args) {
		Input input = new InConsole();
		String message = input.inconsole();
		
		WordCount count = new WordCount();
		int countedletter = count.countletter(message);
		
		OutConsole output = new OutConsole();
		output.outconsole(countedletter);
		
		
		
	}
}