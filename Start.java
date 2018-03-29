package debryan;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class Start {

	private static String pathOfFile = "D:\\Swati-Work\\";
	private static String fileName = "BruteForcePasswordHack.zip";
	private static String[] fileExtensionsAllowed = {"zip"};
	
	//private static String originalPassword = "=-_+";
	//private static String deciferedPassword = new String();
	
	private static ZipFile zipFile = null;
	
	private static boolean successful = false;
	
	private static int minLength = 3;
	private static int maxLength = 3;
	
	private static boolean loadZipFile() {
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("N/A", fileExtensionsAllowed);
		// Folder where zip file is present
		File file = new File(pathOfFile + fileName);
		try {
			zipFile = new ZipFile(file);
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return extensionFilter.accept(file);
	}
	
	private static boolean isEncrypted() {
		try {
			return zipFile.isEncrypted();
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean unzipWithPassword(String guessedPassword) {
		try {
			// Your ZIP password
			zipFile.setPassword(guessedPassword);
			List fileHeaderList = zipFile.getFileHeaders();
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
				// Path where you want to Extract
				zipFile.extractFile(fileHeader, pathOfFile);
				successful = true;
			}
			if(successful) {
				System.out.println("Password Cracked !!!!");
				System.out.println("Extracted Successfully");
				System.out.println("Correct Password : " + guessedPassword);
				//deciferedPassword = guessedPassword;
				return successful;
			}
		} catch (Exception e) {
			//System.out.println("Please Try Again");
		}
		return successful;
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		GenerateAllKeyboardWordCombinations wordGenerator = new GenerateAllKeyboardWordCombinations(minLength, maxLength);
		String word = null;
		if(loadZipFile() && isEncrypted())
			while(null != (word = wordGenerator.getNextWord()))
				if(unzipWithPassword(word))
					break;
		long endTime = System.nanoTime();
		System.out.println("Total Time Taken : " + (double)((endTime - startTime)/(long)1000000L) + " milliseconds");
		if(successful)
			System.out.println("----------- Password Found -----------");
		else
			System.out.println("----------- Password Not Found -----------");
	}

}
