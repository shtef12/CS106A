/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;
import java.util.ArrayList;

public class FindRange extends ConsoleProgram {
	public static final int SENTINEL = 0;
	public void run() {
		/* You fill this in */
		println("This program finds the largest and smallest values.");
		ArrayList<Integer> values = new ArrayList<Integer>();
		int x = -1;
		while(true){
			x = readInt("?");
			if(x == SENTINEL) break;
			values.add(x);
		}
		if(values.size() == 0) println("no values were entered.");
		findSmallest(values);
		findLargest(values);
	}
	
	//finds the smallest int from a list and displays it
	public void findSmallest(ArrayList<Integer> list){
		int min = list.get(0);
		for(int i = 1; i < list.size(); i++){
			if(list.get(i) < min){
				min = list.get(i);
			}
		}
		println("min: " + min);
	}
	
	//finds the largest int from a list and displays it
	public void findLargest(ArrayList<Integer> list){
		int max = list.get(0);
		for(int i = 1; i < list.size(); i++){
			if(list.get(i) > max){
				max = list.get(i);
			}
		}
		println("max: " + max);
	}
}

