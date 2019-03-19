package Part_2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogFile {

	private static LogFile single_Instance = null;

	// Private constructor restricted only to this class
	private LogFile() {
		this.generateLogFile();
	}

	// Static method instance of Singleton Class
	public static synchronized LogFile getInstance() {

		// to ensure only one instance is created
		if (single_Instance == null) {
			synchronized (LogFile.class) {
				if (single_Instance == null) {
					single_Instance = new LogFile();
				}
			}
		}
		return single_Instance;
	}

	public synchronized void generateLogFile() {

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

	/** Method used to write the the log file of 
	 * the time customers are server
	 * when an online order is added
	 * when all orders have been processed **/
	
	public synchronized void writeToLogFile(String inputString) {

		BufferedWriter writer = null;

		try {
			String filename = "Log File " + new Date().toString().substring(0, 10) + ".txt";
			File outputFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(outputFile, true));

			writer.write(getCurrentTime() + "\t " + inputString + "\n");

			// System.out.println("Log File saved!");
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

	String getCurrentTime() {
		String result;
		Date currentDate = new Date();
		result = currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds();
		return result;
	}
}
