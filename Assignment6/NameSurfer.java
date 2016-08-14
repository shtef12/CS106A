/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants, ActionListener {

	private NameSurferGraph graph;
	private JLabel nameLab;
	private JTextField nameEntry;
	private JButton enterBut;
	private JButton clearBut;
	NameSurferDataBase data;
	
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
	    // You fill this in, along with any helper methods //
		//graph = new NameSurferGraph();
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		nameLab = new JLabel("Name:");
		add(nameLab,BorderLayout.NORTH);
		nameEntry = new JTextField();
		nameEntry.setPreferredSize(new Dimension(200,20));
		add(nameEntry,BorderLayout.NORTH);
		enterBut = new JButton("Graph");
		add(enterBut,BorderLayout.NORTH);
		clearBut = new JButton("Clear");
		add(clearBut, BorderLayout.NORTH);
		
		addActionListeners();
		
		data = new NameSurferDataBase("names-data.txt");
		
		graph = new NameSurferGraph();
		add(graph);
		//graph.update();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		if(e.getSource().equals(enterBut)){
			if(!nameEntry.getText().isEmpty()){
				System.out.println("clicked enter " + nameEntry.getText());
				NameSurferEntry entry = data.findEntry(nameEntry.getText());
				System.out.println(entry.toString());
				graph.addEntry(entry);
				graph.update();
			}
		}else if(e.getSource().equals(clearBut)){
			System.out.println("clicked clear");
			nameEntry.setText("");
			graph.clear();
			graph.update();
		}
	}
}
