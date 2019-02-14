package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Count.WordCount;
import InOut.InFile;
import InOut.Input;
import InOut.OutConsole;
import InOut.OutFile;
import InOut.Output;

public class FileToFile {

	public static void main(String[] args) {
		Input input = new InFile();
		String message = input.infile();

		WordCount count = new WordCount();
		int countedletter = count.countletter(message);
		
		Output output = new OutFile();
		output.outfile(countedletter);
		
	}
}