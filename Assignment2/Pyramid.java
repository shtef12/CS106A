/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		/* You fill this in. */
		drawPyramid();
	}
	
	public void drawPyramid(){
		//calculates the location of the first brick and starts the recursive draw function
		double firstBrickXLoc = getCenterScreen().getX() - (BRICK_WIDTH / 2);
		double firstBrickYLoc = getHeight() - (BRICK_HEIGHT * BRICKS_IN_BASE);
		recDrawPyramid(firstBrickXLoc,firstBrickYLoc, 1);
	}
	
	public void recDrawPyramid(double x, double y, int numBricks){
		//recursively draws the pyramid, if the number of bricks is greater than
		//then amount of bricks in base then it returns
		if(numBricks > BRICKS_IN_BASE) return;
		else{
			for(int i = 0; i < numBricks; i++){
				//calculate the brick x location and draw
				double brickX = x + (BRICK_WIDTH*i);
				drawBrick(brickX,y);
			}
			//calculate the new brick x and y locations and recursively call draw pyramid
			double newX = x - (BRICK_WIDTH/2);
			double newY = y + BRICK_HEIGHT;
			int newBricks = numBricks + 1;
			recDrawPyramid(newX,newY,newBricks);
		}
	}
	
	public void drawBrick(double x, double y){
		GRect brick = new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
		getGCanvas().add(brick);
	}
	
	public GPoint getCenterScreen(){
		double x = getWidth() - (getWidth()/2);
		double y = getHeight() - (getHeight()/2);
		GPoint p = new GPoint(x,y);
		return p;
	}
}

