/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		println("Enter the values to compute the pythagorean theorem");
		double a = readDouble("a:");
		double b = readDouble("b:");
		double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		println("c:" + c);
	}
}
