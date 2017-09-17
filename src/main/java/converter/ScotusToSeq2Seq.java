/**
 * this class splits the supreme court
 * transcript into two files for training
 * the seq2seq model for conversation
 * 
 * seq2seq expects 4 files, in two sets.
 * The first set is for training and the
 * second set is for validation.  Each set
 * contains two files, one for source (input values),
 * and one for target(output values).
 * 
 * We assume the input value on a given line of the
 * input file is the expected output value on the same
 * line of the output file.
 */
package main.java.converter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScotusToSeq2Seq {
	public static void main(String[] args) {
		ScotusToSeq2Seq  sts = new ScotusToSeq2Seq();
		if (sts.validate(args)) {
			
			sts.splitFile(args[0], args[1]);
			
		} else {
			
			sts.printUsage();
		}

	}
	public static final String sep = System.getProperty("file.separator");
	private void splitFile(String inputFile, String outputDir) {
		long numLines = countLines(inputFile);
		try {
			Scanner scanner = new Scanner( new File (inputFile));
			PrintWriter pwTrainInput = new PrintWriter(new File(outputDir + sep + "giga-fren.release2.fixed.en"));
			PrintWriter pwTestInput = new PrintWriter(new File(outputDir + sep + "newstest2013.en"));
			PrintWriter pwTrainOutput = new PrintWriter(new File(outputDir + sep + "giga-fren.release2.fixed.fr"));
			PrintWriter pwTestOutput = new PrintWriter(new File(outputDir + sep + "newstest2013.fr"));
			
			for (long i = 0; i  < 0.8*numLines; i+=2) {
				pwTrainInput.println(scanner.nextLine());
				pwTrainOutput.println(scanner.nextLine());
			}
			
			while(scanner.hasNextLine()) {
				pwTestInput.println(scanner.nextLine());
				if (scanner.hasNextLine()) {
					pwTestOutput.println(scanner.nextLine());
				}
			}
			scanner.close();
			pwTestInput.close();
			pwTestOutput.close();
			pwTrainInput.close();
			pwTestOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * efficiently count lines
	 * code copied from https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private long countLines(String filename) {
		byte[] c = new byte[1024];
		long count = 0;
		int readChars = 0;
		boolean empty = true;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return (count == 0 && !empty) ? 1 : count;
	}
	
	private boolean validate(String[] args) {
		if (args.length < 2) {
			return false;
		} else {
			return true;
		}
	}
	
	private void printUsage() {
		System.out.println("Usage: java ScotusToIntent <<input file name>> <<output file directory>>");
		
	}
}
