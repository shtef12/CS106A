import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private static final String SENTINEL = "Quit";
	private HangmanLexicon lex;
	private String secretWord;
	private String guessedWord = "";
	private int guessesLeft = 8;
	private HangmanCanvas canvas;
	
	public void init(){
		setSize(800,500);
		canvas = new HangmanCanvas();
		add(canvas);
		lex = new HangmanLexicon();
	}
	
    public void run() {
		/* You fill this in */
    	getRandomWord();
    	canvas.displayWord(guessedWord);
    	playGame();
	}

    //gets a random word from the lexicon and creates the displayed word
    public void getRandomWord(){
    	RandomGenerator rgn = RandomGenerator.getInstance();
    	int index = rgn.nextInt(0,lex.getWordCount()-1);
    	secretWord = lex.getWord(index);
    	//println(secretWord);
    	
    	for(int i = 0; i < secretWord.length(); i++){
    		guessedWord += "-";
    	}
    	//println(guessedWord);
    }
    
    public void playGame(){
    	String guess;
    	canvas.reset();
    	while(true){
    		guess = readLine("Enter a guess: ");
    		if(guess.equalsIgnoreCase(SENTINEL)){
    			break;
    		}
    		if(guess.length() > 1){
    			println("Guess is illegal, guess again.");
    			continue;
    		}
    		
    		guess = guess.toUpperCase();
    		//checks if the guess is in the secret word
    		if(isInWord(guess)){
    			updateGuessWord(guess);
    			if(guessedWord.equalsIgnoreCase(secretWord)){
    				println("You won!");
    				break;
    			}
    		}else{
    			//subtracts guesses and updates the hangman body
    			guessesLeft--;
    			canvas.noteIncorrectGuess(guess.charAt(0));
    			canvas.updateBody(guessesLeft);
    		}
    		if(guessesLeft == 0){
    			println("You are out of guesses. The word was " + secretWord);
    			break;
    		}
    	}
    	println("Thanks for playing");
    }
    
    public boolean isInWord(String guess){
    	if(secretWord.contains(guess)){
    		return true;
    	}
    	return false;
    }
    
    public void updateGuessWord(String guess){
    	for(int i = 0; i < secretWord.length(); i++){
    		if(secretWord.charAt(i) == guess.charAt(0)){     //if the letter is = to guess
    			//println("i is: " + i);
    			String substr = guessedWord.substring(0, i); //create substring of letters before letter
    			substr += guess;                             //add the letter to the substring
    			if(i < secretWord.length()-1){               //if not at end of string, add rest of string to substring
    				substr += guessedWord.substring(i + 1, secretWord.length());
    			}
    			guessedWord = substr;
    		}
    	}
    	println("updated guess word: " + guessedWord);
    	canvas.displayWord(guessedWord);
    }
   
}
