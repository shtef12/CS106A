/*
 * File: Artistry.java
 * Name:
 * Section Leader:
 * ==========================================================
 * Replace these comments with a description of your program.
 * Since this program is more freeform than the rest, tell us
 * a bit about it in these comments!
 */
import java.awt.Color;

import acm.graphics.*;
import acm.program.*;

public class Artistry extends GraphicsProgram {
	public void run() {
		/* You fill this in.  Remember that you must have
		 * 
		 * 1. At most twenty GObjects,
		 * 2. At least one filled object,
		 * 3. At least two different colors of objects, and
		 * 4. At least three different types of objects (not
		 *    counting the GLabel you use to sign your name).
		 * 
		 * Also, be sure to sign your name in the bottom-right
		 * corner of the canvas.
		 */
		
		drawHouse();
		signName();
	}
	
	public void drawHouse(){
		//draws the main rectangle of house
		GRect houseRect = new GRect(100,100);
		double x = getWidth() - (getWidth()/2) - (houseRect.getWidth()/2);
		double y = getHeight() - (getHeight()/2) - (houseRect.getHeight()/2);
		houseRect.setLocation(x, y);
		houseRect.setFilled(true);
		houseRect.setFillColor(Color.RED);
		getGCanvas().add(houseRect);
		
		drawStreet();
		drawSun();
	}
	
	public void drawStreet(){
		//draws a black rectangle and positions it under the house
		GRect street = new GRect(getWidth(),20);
		street.setFilled(true);
		street.setFillColor(Color.BLACK);
		street.setLocation(0, getHeight()/2 + (street.getHeight()*2));
		getGCanvas().add(street);
	}
	
	public void drawSun(){
		//draws a sphere in the upper left corner
		GOval sun = new GOval(50, 50);
		sun.setFilled(true);
		sun.setFillColor(Color.YELLOW);
		sun.setLocation(0, 0);
		getGCanvas().add(sun);
		
		drawRays();
	}
	
	public void drawRays(){
		//draws rays coming off of the sun
		GLine ray1 = new GLine(25, 25, 100, 25);
		GLine ray2 = new GLine(25,25,25,100);
		GLine ray3 = new GLine(25,25,50,0);
		GLine ray4 = new GLine(25,25,50,50);
		GLine ray5 = new GLine(25,25,0,50);
		ray1.setColor(Color.YELLOW);
		ray2.setColor(Color.YELLOW);
		ray3.setColor(Color.YELLOW);
		ray4.setColor(Color.YELLOW);
		ray5.setColor(Color.YELLOW);
		getGCanvas().add(ray1);
		getGCanvas().add(ray2);
		getGCanvas().add(ray3);
		getGCanvas().add(ray4);
		getGCanvas().add(ray5);
	}
	public void signName(){
		GLabel nameLab = new GLabel("Artistry by Stefan");
		double x = getGCanvas().getWidth() - nameLab.getWidth();
		double y = getGCanvas().getHeight() - nameLab.getHeight();
		nameLab.setLocation(new GPoint(x,y));
		getGCanvas().add(nameLab);
	}
}
