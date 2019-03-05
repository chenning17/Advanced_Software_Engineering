package Part_2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogFile {
	
	private static LogFile single_Instance = null;

	public String s;

	// Private constructor restricted only to this class
	public LogFile() {
		s = " has Read Log File and timestamp.";
		
		this.generateLogFile();
	}


	// Static method instance of Singleton Class
	public static LogFile getInstance() {
		
		// to ensure only one instance is created
		if (single_Instance == null)
			single_Instance = new LogFile();

		return single_Instance;
	}


	public void generateLogFile() {

		BufferedWriter writer = null;

		try {
			String filename = "Log File " + new Date().toString().substring(0, 10) + ".txt";
			File outputFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(outputFile, true));

			writer.write(String.format("Log File created on: %s \n\n", new Date().toString().substring(0, 16)));

			System.out.println("Log File saved!");
		} catch (IOException e) {
			System.out.println("Failed to save log file." + e.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				// Do nothing
			}
		}
			}

	}