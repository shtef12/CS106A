/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		message.setLocation(LEFT_MARGIN, getHeight() - BOTTOM_MESSAGE_MARGIN);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// You fill this in
		removeAll();
		
		//add name label
		drawNameLabel(profile);
		
		//add the image
		drawImage(profile);
		
		//add the status
		drawStatus(profile);
		
		//add friends
		drawFriends(profile);
	}
	
	private void drawNameLabel(FacePamphletProfile profile){
		GLabel nameLab = new GLabel(profile.getName());
		nameLab.setFont(PROFILE_NAME_FONT);
		nameLab.setLocation(LEFT_MARGIN, TOP_MARGIN);
		add(nameLab);
	}
	
	private void drawImage(FacePamphletProfile profile){
		if(profile.getImage() == null){
			GRect rect = new GRect(LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN,IMAGE_WIDTH,IMAGE_HEIGHT);
			add(rect);
			GLabel imgLab = new GLabel("No image");
			imgLab.setFont(PROFILE_IMAGE_FONT);
			imgLab.setLocation(LEFT_MARGIN + (IMAGE_WIDTH/2-(imgLab.getWidth()/2)), TOP_MARGIN+IMAGE_MARGIN+(IMAGE_HEIGHT/2));
			add(imgLab);
		}else{
			GImage img = profile.getImage();
			img.setLocation(LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN);
			img.scale(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img);
		}
	}

	private void drawStatus(FacePamphletProfile profile){
		GLabel statLab = new GLabel("No status");
		statLab.setFont(PROFILE_STATUS_FONT);
		statLab.setLocation(LEFT_MARGIN, TOP_MARGIN+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN);
		if(profile.getStatus().isEmpty() || profile.getStatus() == null){
			add(statLab);
		}else{
			statLab.setLabel(profile.getName() + " is " + profile.getStatus());
			add(statLab);
		}
	}
	
	private void drawFriends(FacePamphletProfile profile){
		GLabel friendLab = new GLabel("Friends:");
		friendLab.setFont(PROFILE_FRIEND_LABEL_FONT);
		friendLab.setLocation(getWidth()/2, TOP_MARGIN+IMAGE_MARGIN);
		add(friendLab);
		
		//add friends sequentially and on their own line below friendLab
		Iterator<String> friendItr = profile.getFriends();
		int count = 1;
		while(friendItr.hasNext()){
			GLabel fLab = new GLabel(friendItr.next());
			fLab.setLocation(getWidth()/2, TOP_MARGIN+IMAGE_MARGIN+(fLab.getHeight()*count));
			fLab.setFont(PROFILE_FRIEND_FONT);
			add(fLab);
			count++;
		}
	}
}
