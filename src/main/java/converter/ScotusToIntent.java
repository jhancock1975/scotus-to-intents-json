package main.java.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScotusToIntent {
	public static void main(String[] args) {
		ScotusToIntent  sti = new ScotusToIntent();
		if (sti.validate(args)) {
			
			sti.initOutputFile(args[1]);
			sti.convert(args[0], args[1]);
			sti.finishOutputFile(args[1]);
			
		} else {
			
			sti.printUsage();
		}

	}

	private void finishOutputFile(String outputFile) {
		try {
			
			//open file for appending
			PrintWriter pw = new PrintWriter(new FileOutputStream( new File(outputFile), true));

			pw.println("   ]\n}");
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initOutputFile(String outputFile) {
		try {
			
			PrintWriter pw = new PrintWriter(new File( outputFile));
			pw.println("{\"intents\": [");
			pw.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
	private void convert(String inputFile, String outputFile) {
		try {
			Scanner scanner = new Scanner(new File(inputFile));

			while (scanner.hasNextLine()) {
				String line1 = scanner.nextLine();
				if (scanner.hasNextLine()) {
					String line2 = scanner.nextLine();
					addOutput (line1, line2, outputFile);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private boolean firstLine = true;
	private long tagNumber = 0;
	
	private void addOutput(String line1, String line2, String outputFile) {
		try {
			
			//open file for appending
			PrintWriter pw = new PrintWriter(new FileOutputStream( new File(outputFile), true));
			
			if (!firstLine) {
				pw.print(",");
			}
			firstLine = false;
			
			pw.println("{ \"tag\" : \"" + tagNumber + "\",");
			tagNumber++;
			
			pw.println("\t\"patterns\" : [\"" + escapeDoubleQuotes(removeSpeakerName(line1)) + "\"],");
			
			pw.println("\t\"responses\" : [\"" + removeSpeakerName(line2) + "\"]");
			
			pw.println("}");
			
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private String escapeDoubleQuotes(String removeSpeakerName) {
		return removeSpeakerName.replace("\"", "\\\"");
	}

	/**
	 * the input file's lines start with '>' followed by the speakers name, followed by ':'
	 * example of how the line starts: > MR. MILLER:
	 * this data is not relevant for the chatbot
	 * 
	 * @param line1
	 * @return
	 */
	private String removeSpeakerName(String line1) {
		return line1.replaceAll(">.*?\\:", "");
	}

	private void printUsage() {
		System.out.println("Usage: java ScotusToIntent <<input file name>> <<output file name>>");
		
	}

	private boolean validate(String[] args) {
		if (args.length < 2) {
			return false;
		} else {
			return true;
		}
	}
}
