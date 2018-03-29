package debryan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateAllKeyboardWordCombinations {
	
	private int minChars;
	private int maxChars;
	
	private static List<Character> keyboardCharacters = new ArrayList<Character>();
	
	private Map<Integer, List<Integer>> lengthVsNextGeneratePositionMapping = null;
	private Map<Integer, Boolean> digitVsProgress = null;
	
	static {
		addKeyboardCharacters();
	}

	private static void addKeyboardCharacters() {
		int ascii;
		// Lower Case English Alphabets
		for(ascii = 97; ascii <= 122; ascii++)
			keyboardCharacters.add((char) ascii);
		// Upper Case English Alphabets
		for(ascii = 65; ascii <= 90; ascii++)
			keyboardCharacters.add((char) ascii);
		// Numeric Digits
		for(ascii = 48; ascii <= 57; ascii++)
			keyboardCharacters.add((char) ascii);
		// Special Characters
		char[] specialCharacters = new char[] {'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=',
				'{', '[', '}', ']', '|', '\\', ':', ';', '"', '\'', '<', ',', '>', '.', '?', '/', ' '};
		for(char special : specialCharacters)
			keyboardCharacters.add(special);
	}
	
	public GenerateAllKeyboardWordCombinations(int minChars, int maxChars) {
		this.minChars = minChars;
		this.maxChars = maxChars;
		lengthVsNextGeneratePositionMapping = new HashMap<Integer, List<Integer>>();
		digitVsProgress = new HashMap<Integer, Boolean>();
		for(int n = minChars; n <= maxChars; n++) {
			List<Integer> listOfPositionsOfSizeN = new ArrayList<Integer>(n);
			for(int index = 0; index < n; index++)
				listOfPositionsOfSizeN.add(index, 0);
			lengthVsNextGeneratePositionMapping.put(n, listOfPositionsOfSizeN);
			digitVsProgress.put(n, false);
		}
		digitVsProgress.put(minChars, true);
	}
	
	private Integer getWordLengthToBeGenerated() {
		for(Integer digit : digitVsProgress.keySet()) {
			if(digitVsProgress.get(digit)) {
				return digit;
			}
		}
		return null;
	}
	
	private void incrementProgress(Integer currentLength, List<Integer> currentPosition) {
		for(int index = currentLength-1; index >= 0; index--) {
			int value = currentPosition.get(index);
			if(value == keyboardCharacters.size()-1) {
				currentPosition.set(index, 0);
				if(index == 0) {
					if(currentLength == maxChars) {
						digitVsProgress.put(currentLength, false);
					} else {
						digitVsProgress.put(currentLength, false);
						digitVsProgress.put(currentLength+1, true);
					}
				} else {
					continue;
				}
			} else {
				currentPosition.set(index, value+1);
				break;
			}
		}
	}
	
	public String getNextWord() {
		Integer length = getWordLengthToBeGenerated();
		List<Integer> nextGeneratePosition = null;
		if(null != length)
			nextGeneratePosition = lengthVsNextGeneratePositionMapping.get(length);
		if(null != nextGeneratePosition) {
			StringBuffer word = new StringBuffer();
			for(int indexPointer : nextGeneratePosition) {
				word.append(keyboardCharacters.get(indexPointer));
			}
			incrementProgress(length, nextGeneratePosition);
			return word.toString();
		} else {
			return null;
		}
	}

}
