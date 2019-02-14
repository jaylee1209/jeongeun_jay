package main;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Count.WordCount;
import InOut.InConsole;
import InOut.Input;
import InOut.OutConsole;
import InOut.OutFile;
import InOut.Output;

public class ConsoleToFile {

	public static void main(String[] args) throws IOException{
		Input input = new InConsole();
		String message = input.inconsole();
		
		WordCount count = new WordCount();
		int countedletter = count.countletter(message);
//		System.out.println(countedletter);
		
		Output output = new OutFile();
		output.outfile(countedletter);
	}
}