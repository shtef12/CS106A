/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	public void run() {
		setupPlayers();
		initDisplay();
		playGame();
	}
	
	/**
	 * Prompts the user for information about the number of players, then sets up the
	 * players array and number of players.
	 */
	private void setupPlayers() {
		nPlayers = chooseNumberOfPlayers();	
		dice = new int[N_DICE];
		scores = new int[nPlayers];
		upperScores = new int[nPlayers];
		lowerScores = new int[nPlayers];
		
		/* Set up the players array by reading names for each player. */
		playerNames = new String[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			/* IODialog is a class that allows us to prompt the user for information as a
			 * series of dialog boxes.  We will use this here to read player names.
			 */
			IODialog dialog = getDialog();
			playerNames[i] = dialog.readLine("Enter name for player " + (i + 1));
		}
		
		IODialog dialog = getDialog();
		customDice = dialog.readBoolean("Would you like to manually choose dice?");
		if(customDice){
		for(int i = 0; i < dice.length; i++){
			IODialog  dDialog = getDialog();
			dice[i] = dialog.readInt("Enter value for dice " + (i+1));
		}
		}
	}
	
	/**
	 * Prompts the user for a number of players in this game, reprompting until the user
	 * enters a valid number.
	 * 
	 * @return The number of players in this game.
	 */
	private int chooseNumberOfPlayers() {
		/* See setupPlayers() for more details on how IODialog works. */
		IODialog dialog = getDialog();
		
		while (true) {
			/* Prompt the user for a number of players. */
			int result = dialog.readInt("Enter number of players");
			
			/* If the result is valid, return it. */
			if (result > 0 && result <= MAX_PLAYERS)
				return result;
			
			dialog.println("Please enter a valid number of players.");
		}
	}
	
	/**
	 * Sets up the YahtzeeDisplay associated with this game.
	 */
	private void initDisplay() {
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
	}

	/**
	 * Actually plays a game of Yahtzee.  This is where you should begin writing your
	 * implementation.
	 */
	private void playGame() {
		
		while(turnNum <= N_SCORING_CATEGORIES){
			for(int i = 0; i < playerNames.length; i++){
				playerTurn(i);
			}
			turnNum++;
		}
		
		//checks for score bonus
		for(int i = 0; i < upperScores.length; i++){
			if(upperScores[i] > 63){
				scores[i] += 35;
				upperScores[i] += 35;
				display.updateScorecard(UPPER_BONUS, i, 35);
				display.updateScorecard(UPPER_SCORE, i, scores[i]);
			}
		}
		
		calculateWinner();
	}
	
	private void playerTurn(int playerIndex){
		int rollNum = 1;
		display.waitForPlayerToClickRoll(playerIndex);
		if(!customDice)
			rollAllDice();  //roll first dice
		rollNum++;
		display.displayDice(dice);
		while(rollNum <= 3){
			display.waitForPlayerToSelectDice();
			if(rollUnselectedDice()){             //if selects dice, roll again
				display.displayDice(dice);
				rollNum++;
			}else{
				chooseCategory(playerIndex);
				return;
			}
		
		}
		chooseCategory(playerIndex);
	}
	
	private void calculateWinner(){
		int max = 0;
		int index = -1;
		for(int i = 0; i < scores.length; i++){
			if(scores[i] > max){
				max = scores[i];
				index = i;
			}
		}
		
		display.printMessage("Player " + (index+1) + " wins!");
		
		//updates the scorecard for each player and display the total score
		for(int j = 0; j < scores.length; j++){
			display.updateScorecard(TOTAL, j, scores[j]);
		}
	}
	
	//waits for player to choose a category and updates score
	private void chooseCategory(int playerIndex){
		display.printMessage("waiting for player to select category");
		int category = display.waitForPlayerToSelectCategory();
		if(checkCategory(category)){
			display.printMessage("that works!" + category);
			calculateScore(category,playerIndex);
			return;
		}
		else{
			display.printMessage("not right");
			//calculateScore(category,playerIndex);
			display.updateScorecard(category, playerIndex, 0);
		}
	}
	
	private void rollAllDice(){
		for(int i = 0; i < dice.length; i++){
			rollDie(i);
		}
	}
	
	private void rollDie(int index){
		RandomGenerator rg = RandomGenerator.getInstance();
		dice[index] = rg.nextInt(1, 6);
	}
	
	//returns whether any dice were selected, and if so, rolls the selected die
	private boolean rollUnselectedDice(){
		ArrayList selectedIndices = new ArrayList(); //holds the indices of the selected dice
		for(int i = 0; i < dice.length; i++){
			if(display.isDieSelected(i)){
				selectedIndices.add(i);
			}
		}
		if(selectedIndices.isEmpty()){
			return false;
		}else{
			for(int i = 0; i < selectedIndices.size(); i++){ //rolls the dice for the selected dice
				rollDie((int)selectedIndices.get(i));
			}
			return true;
		}
	}
	
	private void calculateScore(int category, int player){
		display.printMessage("Calculating score for category " + category);
		int score = 0;
		switch(category){
		case ONES: score = calculateSingles(category, score, player); break;
		case TWOS: score = calculateSingles(category, score, player); break;
		case THREES: score = calculateSingles(category, score, player); break;
		case FOURS: score = calculateSingles(category, score, player); break;
		case FIVES: score = calculateSingles(category, score, player); break;
		case SIXES: score = calculateSingles(category, score, player); break;
		case THREE_OF_A_KIND: score = calculateMultiple(3,score, player); break;
		case FOUR_OF_A_KIND: score = calculateMultiple(4,score, player); break;
		case FULL_HOUSE: score = calculateConstantScore(score, category, player); break;
		case SMALL_STRAIGHT: score = calculateConstantScore(score, category, player); break;
		case LARGE_STRAIGHT: score = calculateConstantScore(score, category, player); break;
		case YAHTZEE: score = calculateConstantScore(score, category, player); break;
		case CHANCE: score = calculateChance(score, player);
		default: display.printMessage("didnt find category"); break;
		}
		
		//update the score card
		display.updateScorecard(category, player, score);
		display.updateScorecard(UPPER_SCORE, player, upperScores[player]);
		display.updateScorecard(LOWER_SCORE, player, lowerScores[player]);
		scores[player] += score;
	}
	
	//calculates the score for categories with constant values
	private int calculateConstantScore(int score, int category, int player){
		int nScore = score;
		if(category == FULL_HOUSE){
			nScore = 25;
		}else if(category == SMALL_STRAIGHT){
			nScore = 30;
		}else if(category == LARGE_STRAIGHT){
			nScore = 40;
		}else if(category == YAHTZEE){
			nScore = 50;
		}
		lowerScores[player] += nScore;
		return nScore;
	}
	
	//calculate the score for the chance category
	private int calculateChance(int score, int player){
		int nScore = score;
		for(int i = 0; i < dice.length; i++){
			nScore += dice[i];
		}
		lowerScores[player] += nScore;
		return nScore;
	}
	
	//calculates the score for three/four/yahtzee categories
	private int calculateMultiple(int minNeeded, int score, int player){
		int nScore = score;
		ArrayList matches = new ArrayList();
		matches = getMatching(minNeeded,dice);
		
		for(int i = 0; i < matches.size(); i++){
			nScore += (int)matches.get(i);
		}
		
		lowerScores[player] += nScore;
		return nScore;
	}
	
	private int calculateSingles(int category, int score, int player){
		int nScore = score;
		for(int i = 0; i < dice.length; i++){
			if(dice[i] == category+1){
				nScore += dice[i];
			}
		}
		upperScores[player] += nScore;
		return nScore;
	}
	
	//checks if there are matching dice, at least min needed
	private boolean hasMatching(int minNeeded){
		ArrayList matches = getMatching(minNeeded,dice);
		if(matches.size() > minNeeded)
			return true;
		return false;
	}
	
	//checks to see if the dice equal a full house
	private boolean hasFullHouse(){
		int[] temp = dice;
		ArrayList threeKind = getMatching(3,dice); //get 3 matching values
		if(threeKind.size() != 3 || threeKind.isEmpty()){
			return false;
		}
		
		for(int i = 0; i < threeKind.size(); i++){
			if(temp[i] == (int)threeKind.get(0)){
				temp[i] = -i;
			}
		}
		ArrayList twoKind = getMatching(2,temp);  //get matching values from temp
		if(twoKind.size() != 2 || twoKind.isEmpty()){
			return false;
		}
		else return true;
	}
	
	private boolean hasStraight(int minNeeded){
		//sort the dice in ascending order
		for(int i = 0; i < dice.length-1; i++){
			if(dice[i] > dice[i+1]){
				int temp = dice[i];
				dice[i] = dice[i+1];
				dice[i+1] = temp;
			}
		}
		
		//check if there is a straight of at least minNeeded
		int numStraight = 0;
		for(int j = 0; j < dice.length; j++){
			for(int k = j+1; k < minNeeded; k++){
				if(dice[k] != dice[j]+1){
					numStraight = 0;
				}else{
					numStraight++;
				}
			}
			if(numStraight == minNeeded) return true;
		}
		return false;
	}
	
	private boolean hasSingle(int num){
		for(int i = 0; i < dice.length; i++){
			if(dice[i] == num) return true;
		}
		return false;
	}
	
	private boolean checkCategory(int category){
		switch(category){
		case ONES:  if(hasSingle(1)) return true; break;
		case TWOS: if(hasSingle(2)) return true; break;
		case THREES: if(hasSingle(3)) return true; break;
		case FOURS: if(hasSingle(4)) return true; break;
		case FIVES: if(hasSingle(5)) return true; break;
		case SIXES: if(hasSingle(6)) return true; break;
		case THREE_OF_A_KIND: if(hasMatching(3)) return true; break;
		case FOUR_OF_A_KIND: if(hasMatching(4)) return true; break;
		case FULL_HOUSE: if(hasFullHouse())return true; break;
		case SMALL_STRAIGHT: if(hasStraight(3)) return true; break;
		case LARGE_STRAIGHT: if(hasStraight(4)) return true; break;
		case YAHTZEE: if(hasStraight(5)) return true; break;
		case CHANCE: return true;
		default: return false;
		}
		System.out.println("check cat is false");
		return false;
	}
	//returns a list of the matching dice, the values
	private ArrayList getMatching(int minNeeded, int[] array){
		ArrayList matching = new ArrayList();    //holds the values of the dice that match
		
		//for each element, see if there is another that is equal to it and add to array
		for(int i = 0; i < array.length; i++){
			matching.add(array[i]);
			for(int j = i + 1; j < array.length; j++){
				if(array[j] == array[i])
					matching.add(array[j]);
			}
			
			//if there are not enough matches, clear array
			if(matching.size() < minNeeded)
				matching.clear();
			else 
				return matching;
		}
		return matching;
	}
	
	private void printArray(ArrayList ls){
		display.printMessage("array is: ");
		System.out.println("array is: ");
		for(int i = 0; i < ls.size(); i++){
			display.printMessage(ls.get(i).toString());
			System.out.print(ls.get(i));
		}
	}
		
	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[] dice;
	private int[] scores;
	private int[] upperScores;
	private int[] lowerScores;
	private YahtzeeDisplay display;
	private int turnNum = 1;
	private boolean customDice = false;
}
