/* 
 * Internet Connectivity Monitor v1.41
 * Disconnections.java class
 * Genc Alikaj - August 2013
 */

import java.awt.Color;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Disconnections{
	
	//audio file to play when disconnected
	Clip clpDisconnected;

	public synchronized void run(){
		
		//if both websites are disconnected increase counter by one
		if (UserInterface.CHKSECONDARYADDRESS.isSelected() == true & 
				UserInterface.DISCONNECTEDPRIMARYSITE == (byte) 1 & 
				UserInterface.DISCONNECTEDSECONDARYSITE == (byte) 1 & 
				UserInterface.SAMEDISCONNECTION == (byte) 0){
			
			UserInterface.SAMEDISCONNECTION = (byte) 1;
			UserInterface.DISCONNECTIONCOUNTER++;
			UserInterface.LBLDISCONNECTIONCOUNTER.setText(Integer.toString(UserInterface.DISCONNECTIONCOUNTER));
			UserInterface.LBLDISCONNECTIONCOUNTER.setForeground(Color.RED);
			
			//play sound when disconnected (when counter goes up by one)
			if (UserInterface.CHKPLAYSOUND.isSelected() == true)
			{						        
		        try {
		        	Clip clpDisconnected = AudioSystem.getClip();
		        	clpDisconnected.open(AudioSystem.getAudioInputStream(new File("Disconnected.wav")));
		        	clpDisconnected.start();
				} catch (Exception e) {
					UserInterface.OUTPUT.append("(Information) Cannot play sound" + System.getProperty("line.separator"));
					UserInterface.OUTPUT.setCaretPosition(UserInterface.OUTPUT.getDocument().getLength());					
				}	        
		    }
		}
		
		//increase disconnection counter when ONLY one website is being monitored
		if (UserInterface.CHKSECONDARYADDRESS.isSelected() == false & 
				UserInterface.DISCONNECTEDPRIMARYSITE == (byte) 1 & 
				UserInterface.SAMEDISCONNECTION == (byte) 0){
			
			UserInterface.SAMEDISCONNECTION = (byte) 1;
			UserInterface.DISCONNECTIONCOUNTER++;
			UserInterface.LBLDISCONNECTIONCOUNTER.setText(Integer.toString(UserInterface.DISCONNECTIONCOUNTER));
			UserInterface.LBLDISCONNECTIONCOUNTER.setForeground(Color.RED);
			
			//play sound when disconnected (when counter goes up by one)
			if (UserInterface.CHKPLAYSOUND.isSelected() == true)
			{				
		        try {
		        	Clip clpDisconnected = AudioSystem.getClip();
		        	clpDisconnected.open(AudioSystem.getAudioInputStream(new File("Disconnected.wav")));
		        	clpDisconnected.start();
				} catch (Exception e) {
					UserInterface.OUTPUT.append("(Information) Cannot play sound" + System.getProperty("line.separator"));
					UserInterface.OUTPUT.setCaretPosition(UserInterface.OUTPUT.getDocument().getLength());					
				}
			}
		}
		
		//the following two conditionals tell the program that connection has been re-established.
		if (UserInterface.CHKSECONDARYADDRESS.isSelected() == true & 
				(UserInterface.DISCONNECTEDPRIMARYSITE == (byte) 0 || 
				UserInterface.DISCONNECTEDSECONDARYSITE == (byte) 0) & 
				UserInterface.SAMEDISCONNECTION == (byte) 1){
			
			UserInterface.SAMEDISCONNECTION = (byte) 0;
		}
		
		if (UserInterface.CHKSECONDARYADDRESS.isSelected() == false & 
				UserInterface.DISCONNECTEDPRIMARYSITE == (byte) 0 & 
				UserInterface.SAMEDISCONNECTION == (byte) 1){
			
			UserInterface.SAMEDISCONNECTION = (byte) 0;
		}
		
		
		//loop disconnection sound if option is selected.
		if (UserInterface.CHKPLAYSOUNDLOOP.isSelected() == true & UserInterface.SAMEDISCONNECTION == (byte) 1){
			
		        try {
		        	clpDisconnected = AudioSystem.getClip();
		        	clpDisconnected.open(AudioSystem.getAudioInputStream(new File("Disconnected.wav")));
		        	clpDisconnected.start();
				} catch (Exception e) {
					UserInterface.OUTPUT.append("(Information) Cannot play sound" + System.getProperty("line.separator"));
					UserInterface.OUTPUT.setCaretPosition(UserInterface.OUTPUT.getDocument().getLength());					
				}	        
		}
		
	}
	
	//free clip memory resources
	public synchronized void clearAudioFile()
	{
		clpDisconnected = null;
		System.gc();
	}
	
}
