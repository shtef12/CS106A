/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Canvas;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	GLabel wrongGuesses;
	GLabel currentWord;
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		drawScaffold();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		if(currentWord != null) remove(currentWord);
		currentWord = new GLabel(word,WORD_X,getHeight()-WORD_Y);
		add(currentWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		/* You fill this in */
		String word = "";
		if(wrongGuesses != null) remove(wrongGuesses);
		if(wrongGuesses != null) word = wrongGuesses.getLabel();
		word += letter;
		wrongGuesses = new GLabel(word,INCORRECT_LABEL_X,getHeight()-INCORRECT_LABEL_Y);
		add(wrongGuesses);
	}

	private void drawScaffold(){
		GLine scaffold = new GLine(SCAFFOLD_XPOS,getHeight()-SCAFFOLD_YSTART,SCAFFOLD_XPOS,(getHeight()-SCAFFOLD_YSTART)-SCAFFOLD_HEIGHT);
		GLine beam = new GLine(SCAFFOLD_XPOS,(getHeight()-SCAFFOLD_YSTART)-SCAFFOLD_HEIGHT,SCAFFOLD_XPOS+BEAM_LENGTH,(getHeight()-SCAFFOLD_YSTART)-SCAFFOLD_HEIGHT);
		GLine noose = new GLine(SCAFFOLD_XPOS+BEAM_LENGTH,(getHeight()-SCAFFOLD_YSTART)-SCAFFOLD_HEIGHT,SCAFFOLD_XPOS+BEAM_LENGTH,(getHeight()-SCAFFOLD_YSTART)-SCAFFOLD_HEIGHT+NOOSE_LENGTH);
		add(scaffold);
		add(beam);
		add(noose);
	}
	
	private void drawHead(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH - HEAD_RADIUS;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH;
		GOval head = new GOval(x,y,HEAD_RADIUS*2,HEAD_RADIUS*2);
		add(head);
	}
	
	private void drawBody(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH + HEAD_RADIUS + HEAD_RADIUS;
		GLine body = new GLine(x,y,x,y+BODY_LENGTH);
		add(body);
	}
	
	private void drawLeftArm(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH + HEAD_RADIUS + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine armUp = new GLine(x,y,x-UPPER_ARM_LENGTH,y);
		GLine armL = new GLine(x-UPPER_ARM_LENGTH,y,x-UPPER_ARM_LENGTH,y+LOWER_ARM_LENGTH);
		add(armUp);
		add(armL);
	}
	
	private void drawRightArm(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH + HEAD_RADIUS + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine armUp = new GLine(x,y,x+UPPER_ARM_LENGTH,y);
		GLine armL = new GLine(x+UPPER_ARM_LENGTH,y,x+UPPER_ARM_LENGTH,y+LOWER_ARM_LENGTH);
		add(armUp);
		add(armL);
	}
	
	private void drawLeftLeg(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH + HEAD_RADIUS + HEAD_RADIUS + BODY_LENGTH;
		GLine legUp = new GLine(x,y,x-HIP_WIDTH,y);
		GLine legL = new GLine(x-HIP_WIDTH,y,x-HIP_WIDTH,y+LEG_LENGTH);
		add(legUp);
		add(legL);
	}
	
	private void drawRightLeg(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH;
		double y = getHeight() - SCAFFOLD_HEIGHT - SCAFFOLD_YSTART + NOOSE_LENGTH + HEAD_RADIUS + HEAD_RADIUS + BODY_LENGTH;
		GLine legUp = new GLine(x,y,x+HIP_WIDTH,y);
		GLine legL = new GLine(x+HIP_WIDTH,y,x+HIP_WIDTH,y+LEG_LENGTH);
		add(legUp);
		add(legL);
	}
	
	private void drawLeftFoot(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH - HIP_WIDTH;
		double y = getHeight() - SCAFFOLD_YSTART;
		GLine foot = new GLine(x,y,x-FOOT_LENGTH,y);
		add(foot);
	}
	
	private void drawRightFoot(){
		double x = SCAFFOLD_XPOS + BEAM_LENGTH + HIP_WIDTH;
		double y = getHeight() - SCAFFOLD_YSTART;
		GLine foot = new GLine(x,y,x+FOOT_LENGTH,y);
		add(foot);
	}
	
	public void updateBody(int guess){
		switch(guess){
		case 7: drawHead(); break;
		case 6: drawBody(); break;
		case 5: drawLeftArm(); break;
		case 4: drawRightArm(); break;
		case 3: drawLeftLeg(); break;
		case 2: drawRightLeg(); break;
		case 1: drawLeftFoot(); break;
		case 0: drawRightFoot(); break;
		}
	}
	
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int SCAFFOLD_XPOS = 100;   //distance from left border where scaffold is
	private static final int SCAFFOLD_YSTART = 100; //distance from bottom where scaffold starts
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int NOOSE_LENGTH = 35;
	private static final double INCORRECT_LABEL_X = 100;
	private static final double INCORRECT_LABEL_Y = 50;
	private static final double WORD_X = 100;
	private static final double WORD_Y = 75;

}
