/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		int num = readInt("Enter a number: ");
		int steps = 0;
		
		while(num != 1){
			print(num);
			if(isEven(num)){        //num is even
				num = num / 2;      //n / 2
				println(" is even so I take half:    " + num);
			}else{                  //num is odd
				num = 3 * num + 1;  //3n+1
				println(" is odd so I make 3n + 1:    " + num);
			}
			steps++;
		}
		println("The process took " + steps + " steps to reach 1");	
	}
	
	public boolean isEven(int num){
		if(num % 2 == 0){
			return true;
		}
		return false;
	}
}

