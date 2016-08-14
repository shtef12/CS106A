/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	//GUI elements
	JLabel nameLabel;
	JTextField textField;
	JButton addButton;
	JButton deleteButton;
	JButton lookupButton;
	JTextField statusField;
	JButton statusButton;
	JTextField picField;
	JButton picButton;
	JTextField friendField;
	JButton friendButton;
	
	FacePamphletCanvas canvas;
	FacePamphletDatabase data;
	FacePamphletProfile currentProfile;
	
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		data = new FacePamphletDatabase();
		currentProfile = null;
		canvas = new FacePamphletCanvas();
		add(canvas);
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		drawNorth();
		drawWest();
		addActionListeners();
    }
    
  
	private void drawNorth(){
		nameLabel = new JLabel("Name");
		add(nameLabel,BorderLayout.NORTH);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(150,20));
		add(textField,BorderLayout.NORTH);
		
		addButton = new JButton("Add");
		add(addButton,BorderLayout.NORTH);
		
		deleteButton = new JButton("Delete");
		add(deleteButton,BorderLayout.NORTH);
		
		lookupButton = new JButton("Lookup");
		add(lookupButton,BorderLayout.NORTH);
	}
	
	private void drawWest(){
		statusField = new JTextField();
		statusField.setPreferredSize(new Dimension(150,20));
		statusField.addActionListener(this);
		statusField.setActionCommand("status");
		statusButton = new JButton("Change Status");
		statusButton.setActionCommand("status");
		add(statusField,BorderLayout.WEST);
		add(statusButton,BorderLayout.WEST);
		
		JLabel space = new JLabel(EMPTY_LABEL_TEXT);
		add(space,BorderLayout.WEST);
		
		picField = new JTextField();
		picField.setPreferredSize(new Dimension(150,20));
		picField.addActionListener(this);
		picField.setActionCommand("picture");
		picButton = new JButton("Change Picture");
		picButton.setActionCommand("picture");
		add(picField,BorderLayout.WEST);
		add(picButton,BorderLayout.WEST);
		
		JLabel space2 = new JLabel(EMPTY_LABEL_TEXT);
		add(space2,BorderLayout.WEST);
		
		friendField = new JTextField();
		friendField.setPreferredSize(new Dimension(150,20));
		friendField.addActionListener(this);
		friendField.setActionCommand("friend");
		friendButton = new JButton("Add Friend");
		friendButton.setActionCommand("friend");
		friendField.setActionCommand("friend");
		add(friendField,BorderLayout.WEST);
		add(friendButton,BorderLayout.WEST);
	}
	
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods

    	//handles the actions for buttons and fields on west side
    	if(e.getActionCommand().equals("friend")){
    		if(!friendField.getText().isEmpty())
    			addFriend();
    	}else if(e.getActionCommand().equals("picture")){
    		if(!picField.getText().isEmpty())
    			addPicture();
    	}else if(e.getActionCommand().equals("status")){
    		if(!statusField.getText().isEmpty())
    			addStatus();
    	}
    	
    	//handles the actions on the north side
    	if(!textField.getText().isEmpty()){
    		if(e.getSource().equals(addButton)){
    			createProfile();
    		}else if(e.getSource().equals(deleteButton)){
    			deleteProfile();
    		}else if(e.getSource().equals(lookupButton)){
    			lookupProfile();
    		}
    	}
	}
    
    private void addFriend(){
    	System.out.println("add friend");
    	if(currentProfile != null){
    		//if current profile does not have the friend already, add them
			//and add the current profile to the other profile's friend list
    		if(data.containsProfile(friendField.getText())){
    			if(!currentProfile.hasFriend(friendField.getText())){
    				currentProfile.addFriend(friendField.getText());
    				FacePamphletProfile prof = data.getProfile(friendField.getText());
    				prof.addFriend(currentProfile.getName());
    				canvas.displayProfile(currentProfile);
    			}
    		}
    	}else{
    		canvas.showMessage("please select a profile");
    	}
    }
    
    private void addPicture(){
    	//add a picture if the image is valid
    	if(currentProfile != null){
    		GImage image = null;
    		try{
    			image = new GImage(picField.getText());
    			currentProfile.setImage(image);
    			canvas.displayProfile(currentProfile);
    		}catch(Exception e){
    			System.out.println(e.getMessage());
    		}
    	}else{
    		canvas.showMessage("please select a profile");
    	}
    }
    
    private void addStatus(){
    	//add a status to the current profile
    	if(currentProfile != null){
    		currentProfile.setStatus(statusField.getText());
    		canvas.displayProfile(currentProfile);
    	}else{
    		canvas.showMessage("please select a profile");
    	}
    }
    
    private void createProfile(){
    	//if the database does not contain the profile, create it
    	if(data.containsProfile(textField.getText())){
    		currentProfile = data.getProfile(textField.getText());
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Displaying " + currentProfile.getName());
    	}else{
    		FacePamphletProfile pam = new FacePamphletProfile(textField.getText());
    		data.addProfile(pam);
    		currentProfile = pam;
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Displaying " + currentProfile.getName());
    	}
    }
    
    private void deleteProfile(){
    	//deletes the profile if it is in the database
    	if(data.containsProfile(textField.getText())){
    		data.deleteProfile(textField.getText());
    		System.out.println("Profile was deleted");
    		currentProfile = null;
    	}else{
    		canvas.showMessage("Database does not contain profile");
    	}
    }
    
    private void lookupProfile(){
    	if(data.containsProfile(textField.getText())){
    		//display the profile if the database contains the profile
    		currentProfile = data.getProfile(textField.getText());
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Displaying " + currentProfile.getName());
    	}else{
    		canvas.showMessage("database does not have profile");
    	}
    }

}
