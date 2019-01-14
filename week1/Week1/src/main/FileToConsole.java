package main;

import Count.WordCount;
import InOut.InFile;
import InOut.Input;
import InOut.OutConsole;
import InOut.Output;

public class FileToConsole {
	public static void main(String[] args) {
		Input input = new InFile();
		String message = input.infile();
		
		WordCount count = new WordCount();
		int countedletter = count.countletter(message);
		
		Output output = new OutConsole();
		output.outconsole(countedletter);
	}
}
