package cafepackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public abstract class FileInput {

	/**
	 * File input for both orders and items (menu)
	 * @param filename
	 */
	public void inputFile(String filename) {
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String inputLine = scanner.nextLine();
				if (inputLine.length() != 0) {
					processLine(inputLine);
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException fnf){
			System.out.println( filename + " not found ");
			System.exit(0);
		}
	}

protected abstract void processLine(String inputLine);

}
