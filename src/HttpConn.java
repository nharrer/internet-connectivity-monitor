
/* 
 * Internet Connectivity Monitor v1.41
 * HttpConn.java class
 * Genc Alikaj - August 2013
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.*;
import java.util.Date;

public class HttpConn implements Runnable {
			
	private byte runFlag = 0;
	
	private byte fileLocked = 0;
	
	String objectName = "Primary";
	
	Disconnections disconnectionObject;
	
	private String address;
	private int frequency;
	
	//using a custom constructor that assigns a name to this object 
	//and brings a reference to the Disconnections class	
	public HttpConn(String name, Disconnections dis){
		objectName = name;
		disconnectionObject = dis;
	}
	
	//run method required for implementing Runnable interface.
	//The loop begins when the thread starts (this method is the first thing that runs). \
	//Process depends on byte flag at the top of the class.
	public void run() {
		while(runFlag == 0){
			StartTest();			
		}
	}
	
	//mutator that sets flag value in order to start or stop test loop
	public void setFlag(byte x){		
		runFlag = x;
	}
	
	//web address mutator
	public void setAddress(String adr){
		address = adr;
	}
	
	//frequency mutator
	public void setFrequency(int f){
		frequency = f;
	}
	
	public void StartTest(){		
											
			try {
				
				//write or update the automatic log file if the option is selected
				AutoWriteLogFile();
				
				//define URL and try to connect to server
				URL url = new URL(address);            
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				
				//current time saved in a String
				String date = String.format("%ta %<tb %<td %<tT", new Date());
				
				//The following object is used to temporarily hold server response data (if connection is successful).
				//This is the key that determines whether the connection was successful or not.
				//If the server response data is successfully retrieved, internet connectivity is assumed to be good.
				//If server response data cannot be retrieved for any reason, internet connectivity is assumed to be lost.
				//When data is not retrieved the method throws an exeption. 
				@SuppressWarnings("unused")
				Object testObject = connection.getContent();
				 				
				//send result to the output textbox on the main UserInterface class
				UserInterface.OUTPUT.append(date + "\t" + address + "\t" + "OK" + System.getProperty("line.separator"));
				UserInterface.OUTPUT.setCaretPosition(UserInterface.OUTPUT.getDocument().getLength());
				
				//free resources
				connection.disconnect();
				connection = null;
				testObject = null;
				url = null;
				
				//announce to the disconnection flag that the connection was successful
				if (objectName == "Primary"){
					UserInterface.DISCONNECTEDPRIMARYSITE = 0;
				} else {
					UserInterface.DISCONNECTEDSECONDARYSITE = 0;
				}
				
				//perform a disconnection counter check
				disconnectionObject.run();
				
				
				
				//write or update the automatic log file if the option is selected
				AutoWriteLogFile();
				
				//wait a few seconds before repeating the process. The waiting time is defined by the user (in the "frequency" field)
				try {					
					Thread.sleep(frequency * 1000);					
                } 
				catch (Exception ex){					
                	System.out.println("Thread.sleep exception on TRY");
                }						
        	} 
			catch (Exception e) {
        		
				//write or update the automatic log file if the option is selected
				AutoWriteLogFile();
				
        		//current time saved in a String
        		String date = String.format("%ta %<tb %<td %<tT", new Date());
        		        		
        		//send result to the output textbox on the main UserInterface class
        		UserInterface.OUTPUT.append(date + "\t" + address + "\t" + "NOT CONNECTED" + System.getProperty("line.separator"));
        		UserInterface.OUTPUT.setCaretPosition(UserInterface.OUTPUT.getDocument().getLength());
        		
				//announce to the disconnection flag that the connection was unsuccessful
				if (objectName == "Primary"){
					UserInterface.DISCONNECTEDPRIMARYSITE = 1;
				} else {
					UserInterface.DISCONNECTEDSECONDARYSITE = 1;
				}
				
				//perform a disconnection counter check
				disconnectionObject.clearAudioFile();
				disconnectionObject.run();
				
				

				//write or update the automatic log file if the option is selected
				AutoWriteLogFile();
				
        		//wait a few seconds before repeating the process. The waiting time is defined by the user (in the "frequency" field)
        		try {
        			Thread.sleep(frequency * 1000);
        		} 
        		catch (Exception ex){
        			System.out.println("Thread.sleep exception on CATCH");
        		}
        	}
		}
	
	public void AutoWriteLogFile()
	{
		if (fileLocked != 1 && UserInterface.CHKAUTOSAVE.isSelected()) 
		{
			try {
				//get path from textbox, and then add escape characters to slashes to avoid errors.
				String rawPath = UserInterface.TXTAUTOSAVEPATH.getText();
				String fixedPath = rawPath.replace("\\", "\\\\");
				
				File logFile = new File(fixedPath);		
				
				//create path if missing
				logFile.getParentFile().mkdirs();
				
				//write contents of Output textarea to file
				BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));
				bw.write(UserInterface.OUTPUT.getText());
				
				//close filewriter
				bw.flush();
				bw.close();
				
			} catch (Exception e) {
				UserInterface.OUTPUT.append(System.getProperty("line.separator") + "Cannot automatically create or write to file. Please check path." + System.getProperty("line.separator"));
				UserInterface.OUTPUT.append("It's possible that access to the selected location is denied. Try using a different path." + System.getProperty("line.separator") + System.getProperty("line.separator"));
				e.printStackTrace();
				//stop trying to automatically write to file if it's not accessible
				fileLocked = 1;
			}
		}
	}
	
}
