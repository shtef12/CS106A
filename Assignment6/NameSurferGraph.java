/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	ArrayList<NameSurferEntry> entries;
	
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
		entries = new ArrayList<NameSurferEntry>();
	}
	
	
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		// You fill this in //
		removeAll();
		entries.clear();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		entries.add(entry);
	}
	
	
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		// You fill this in //
		addHorizontalBars();
		addVerticalBars();
		drawEntries();
	}
	
	private void addHorizontalBars(){
		GLine topHorzBar = new GLine(0,GRAPH_MARGIN_SIZE,getWidth(),GRAPH_MARGIN_SIZE);
		GLine botHorzBar = new GLine(0,getHeight()-GRAPH_MARGIN_SIZE,getWidth(),getHeight()-GRAPH_MARGIN_SIZE);
		add(topHorzBar);
		add(botHorzBar);
	}
	
	private void addVerticalBars(){
		int xPos = 5;
		GLine firstLine = new GLine(xPos,GRAPH_MARGIN_SIZE,xPos,getHeight()-GRAPH_MARGIN_SIZE);
		add(firstLine);
		for(int i = 1; i < NDECADES; i++){
			xPos += (getWidth()/NDECADES);
			GLine newLine = new GLine(xPos,GRAPH_MARGIN_SIZE,xPos,getHeight()-GRAPH_MARGIN_SIZE);
			add(newLine);
		}
	}
	
	private void drawEntries(){
		double low = GRAPH_MARGIN_SIZE;
		double high = getHeight() - GRAPH_MARGIN_SIZE + 20;
		int xPos = 5;
		for(int i = 0; i < entries.size(); i++){
			Color color = randomColor();
			NameSurferEntry entry = entries.get(i);
			for(int j = 0; j < NDECADES-1; j++){
				int rank = entry.getRank(j);
				if(rank == 0) rank = 1000;
				int rank2 = entry.getRank(j+1);
				if(rank2 == 0) rank2 = 1000;
				
				//normalize the values
				double y = 1 + ((rank - 1)*(high - low))/(1000-1);
				double y2 = 1 + ((rank2 - 1)*(high - low))/(1000-1);
				
				drawLabels(xPos,y,entry.getName() + " " + rank,color);
				//draw the line
				GLine lrank = new GLine(xPos,y,xPos+(getWidth()/NDECADES),y2);
				lrank.setColor(color);
				add(lrank);
				xPos += (getWidth()/NDECADES);
			}
			xPos = 5;
		}
		//System.out.println("rank 1: " + rank);
		//System.out.println("rank 2: " + rank2);
	}
	
	private Color randomColor(){
		RandomGenerator rand = RandomGenerator.getInstance();
		return rand.nextColor();
	}
	private void drawLabels(double x, double y, String text, Color color){
		GLabel lab = new GLabel(text);
		lab.setLocation(x, y);
		lab.setColor(color);
		add(lab);
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
