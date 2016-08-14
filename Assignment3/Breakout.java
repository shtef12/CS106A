/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** The total number of bricks */
	private static final int TOTAL_BRICKS = NBRICKS_PER_ROW * NBRICK_ROWS;
	
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	/** The delta x and y for ball speed */
	private double vx,vy;
	private GRect paddle;
	private GOval ball;
	private int remainingBricks = TOTAL_BRICKS;
	private int remainingTurns = NTURNS;
	private RandomGenerator rg = RandomGenerator.getInstance();
	private AudioClip bounceClip;

	/* Method: init() */
	/** Sets up the Breakout program. */
	public void init() {
		/* You fill this in, along with any subsidiary methods */
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		bounceClip = MediaTools.loadAudioClip("bounce.au");
		drawBricks();
		addMouseListeners();
		//addActionListeners();
		createPaddle();
		createBall();
	}

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		while(remainingBricks > 0 || remainingTurns == 0){
			pause(50);
			moveBall();
		}
	}
	
	public void drawBricks(){
		Color rowColor = Color.RED;
		Color nextColor = Color.RED;
		
		double rowSize = NBRICKS_PER_ROW * (BRICK_WIDTH + BRICK_SEP);
		//calculate the x position of the first brick
		double firstX = (getWidth() - (getWidth()/2)) - (rowSize/2);
		for(int i = 0; i < NBRICK_ROWS; i++){
			//decides if the next row should be another color
			if(i % 2 == 0){
				nextColor = determineNextColor(rowColor);
			}
			//calculate the height of the row
			double y = BRICK_Y_OFFSET + (BRICK_HEIGHT * i);
			for(int j = 0; j < NBRICKS_PER_ROW; j++){
				//draw brick with seperation between  each
				double x = firstX + (j * (BRICK_WIDTH + BRICK_SEP));
			    drawBrick(x,y,rowColor);
			}
			rowColor = nextColor; //update the row color
		}
	}
	
	public Color determineNextColor(Color last){
		if(last == Color.RED) return Color.ORANGE;
		else if(last == Color.ORANGE) return Color.YELLOW;
		else if(last == Color.YELLOW) return Color.GREEN;
		else if(last == Color.GREEN) return Color.CYAN;
		else if(last == Color.CYAN) return Color.RED;
		else return Color.RED;
	}
	
	public void drawBrick(double x, double y, Color color){
		GRect brick = new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
		brick.setFilled(true);
		brick.setFillColor(color);
		getGCanvas().add(brick);
	}
	
	public void createPaddle(){
		paddle = new GRect(getWidth()/2,getHeight()-PADDLE_Y_OFFSET,PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		paddle.addMouseMotionListener(new MouseMotionListener()
				{
			    	@Override
			    	public void mouseMoved(MouseEvent e){
			    		double x = checkCollision(e.getX());
			    		paddle.setLocation(x, getHeight() - PADDLE_Y_OFFSET);
			    	}
			    	
			    	@Override
			    	public void mouseDragged(MouseEvent e){
			    		
			    	}
				});
		getGCanvas().add(paddle);
	}
	
	//checks for collisions and returns adjusted location
	public double checkCollision(double xPos){
		double newRightPos = xPos + (PADDLE_WIDTH/2);
		double newLeftPos = xPos - (PADDLE_WIDTH/2);
		if(newRightPos > getWidth()){
			return getWidth() - (PADDLE_WIDTH);
		}else if(newLeftPos < 0){
			return 0;
		}
		return xPos - (PADDLE_WIDTH/2);
	}
	
	public void createBall(){
		ball = new GOval(getWidth()-(getWidth()/2)-(BALL_RADIUS/2),getHeight()-(getHeight()/2)-(BALL_RADIUS/2),BALL_RADIUS,BALL_RADIUS);
		ball.setFilled(true);
		ball.setFillColor(Color.BLACK);
		getGCanvas().add(ball);
		
		//create a random x speed
		vx = rg.nextDouble(1.0,3.0);
		if(rg.nextBoolean(0.5)) vx = -vx;
		vy = 3.0;
	}
	
	public void moveBall(){
		double newX = ball.getX() + vx;
		double newY = ball.getY() + vy;
		checkWallCollision(newX,newY);
		ball.setLocation(newX, newY);
		GObject obj = getObjectCollision(); //get object if colliding with one
		if(obj != null){
			if(obj == paddle){
				vy = -vy;
				bounceClip.play();
			}
			else{ 
				remove(obj);
				remainingBricks--;
				vy = -vy;
				bounceClip.play();
			}
		}
	}
	
	public void checkWallCollision(double x, double y){
		//check the right side of ball
		if(x + BALL_RADIUS > getWidth()) vx = -vx;
		//check the left side of ball
		if(x < 0) vx = -vx;
		//check the bottom of ball
		if(y + BALL_RADIUS > getHeight()){
			//resets the ball, subtracts a life and pauses to restart
			resetBall();
			remainingTurns--;
			pause(5000);
		}
		//check the top of ball
		if(y < 0) vy = -vy;
		
	}
	
	//checks each corner of the ball to see if it is colliding with an object
	public GObject getObjectCollision(){
		if(getElementAt(ball.getX(),ball.getY()) != null)return getElementAt(ball.getX(),ball.getY());
		else if(getElementAt(ball.getX()+BALL_RADIUS,ball.getY()) != null)return getElementAt(ball.getX()+BALL_RADIUS,ball.getY());
		else if(getElementAt(ball.getX(),ball.getY()+BALL_RADIUS) != null)return getElementAt(ball.getX(),ball.getY()+BALL_RADIUS);
		else if(getElementAt(ball.getX()+BALL_RADIUS,ball.getY()+BALL_RADIUS) != null)return getElementAt(ball.getX()+BALL_RADIUS,ball.getY()+BALL_RADIUS);
		return null;
	}
	
	//resets the ball in the center of the screen
	public void resetBall(){
		ball.setLocation(getWidth() - (getWidth()/2) - (BALL_RADIUS/2), getHeight() - (getHeight()/2) - (BALL_RADIUS/2));
		vx = rg.nextDouble(1.0,3.0);
		if(rg.nextBoolean(0.5)) vx = -vx;
		vy = 3.0;
	}
}
