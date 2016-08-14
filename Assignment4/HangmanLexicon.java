/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {

	ArrayList words;
	
	public HangmanLexicon(){
		words = new ArrayList();
		//Reads words in from a file and adds them to the words arraylist
		try {
			BufferedReader br = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			String line;
			while((line = br.readLine()) != null){
				words.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return (String) words.get(index);
	};
}
