/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	public static final double outerRadius = 72;
	public static final double midRadius = 55;
	public static final double innerRadius = 25;
	
	public void run() {
		/* You fill this in. */
		double centerX = getWidth() - (getWidth() / 2);
		double centerY = getHeight() - (getHeight() / 2);
		
		//draws the outer circle
		GOval outerCircle = new GOval(outerRadius,outerRadius);
		outerCircle.setFilled(true);
		outerCircle.setFillColor(Color.RED);
		double x = centerX - (outerCircle.getWidth() / 2);
		double y = centerY - (outerCircle.getHeight() / 2);
		outerCircle.setLocation(x, y);
		getGCanvas().add(outerCircle);
		
		//draws the middle circle
		GOval midCircle = new GOval(midRadius,midRadius);
		midCircle.setFilled(true);
		midCircle.setFillColor(Color.WHITE);
		double xmid = centerX - (midCircle.getWidth() / 2);
		double ymid = centerY - (midCircle.getHeight() / 2);
		midCircle.setLocation(xmid, ymid);
		getGCanvas().add(midCircle);
		
		//draws the inner circle
		GOval innerCircle = new GOval(innerRadius,innerRadius);
		innerCircle.setFilled(true);
		innerCircle.setFillColor(Color.RED);
		double xin = centerX - (innerCircle.getWidth() / 2);
		double yin = centerY - (innerCircle.getHeight() / 2);
		innerCircle.setLocation(xin, yin);
		getGCanvas().add(innerCircle);
	}
}
