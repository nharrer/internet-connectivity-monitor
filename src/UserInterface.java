/* 
 * Internet Connectivity Monitor v1.41
 * UserInterface.java class
 * Genc Alikaj - August 2013
 */

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jhlabs.awt.BasicGridLayout;
import com.jhlabs.awt.ParagraphLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UserInterface {
	
	//Disconnections counts the number of times internet connectivity was lost.
	Disconnections disconnectionCounter = new Disconnections();
	
	// HttpConn is the class that performs the actual testing. I am creating one object for each connection
	HttpConn netRun = new HttpConn("Primary", disconnectionCounter); 	
	Thread netThread = new Thread(netRun);
	
	HttpConn netRunSecondary = new HttpConn("Secondary", disconnectionCounter); 	
	Thread netThreadSecondary = new Thread(netRunSecondary);
	

	//flags used to start/stop monitor threads
	byte threadFlag = 0;
	byte threadFlagSecondary = 0;
		
	
	//disconnection flags and counter. Static variables.
	public static byte DISCONNECTEDPRIMARYSITE = 0;
	public static byte DISCONNECTEDSECONDARYSITE = 0;	
	public static int DISCONNECTIONCOUNTER = 0;
	public static byte SAMEDISCONNECTION = 0;
	
	//Results are sent here from the Net instance (OUTPUT is a "global" variable)
	public static JTextArea OUTPUT = new JTextArea(); 
	JScrollPane scrOutput = new JScrollPane(OUTPUT);
	
	
	//user interface elements
	JFrame frame = new JFrame("Internet Connectivity Monitor 1.41");
	JLabel lblAddressInstruction = new JLabel("Monitor the following address:" ,JLabel.LEFT);
	JLabel lblAddress = new JLabel("http://");
	JTextField txtAddress = new JTextField(18);
	JLabel lblFrequency = new JLabel("Frequency:");
	JTextField txtFrequency = new JTextField(3);
	JLabel lblSeconds = new JLabel("seconds");
	JButton btnMonitor = new JButton("Start Monitoring");
	JButton btnClear = new JButton("Clear Log");
	JButton btnSave = new JButton("Save Log");
	JButton btnExit = new JButton("Exit");
	JButton btnAbout = new JButton("About");
	
	public static JCheckBox CHKSECONDARYADDRESS = new JCheckBox("Monitor a second address:");
	JLabel lblSecondaryAddress = new JLabel("http://");
	JTextField txtSecondaryAddress = new JTextField(18);
	JLabel lblSecondaryFrequency = new JLabel("Frequency:");
	JTextField txtSecondaryFrequency = new JTextField(3);
	JLabel lblSecondarySeconds = new JLabel("seconds");
	
	public static JCheckBox CHKPLAYSOUND = new JCheckBox("Play sound on disconnect");
	public static JCheckBox CHKPLAYSOUNDLOOP = new JCheckBox("Play sound continuously until reconnected");
	
	public static JCheckBox CHKAUTOSAVE = new JCheckBox("Automatically save log");
	JLabel lblAutoSaveLabel = new JLabel("Save path: ");
	public static JTextField TXTAUTOSAVEPATH = new JTextField(17);
	
	JLabel lblDisconnectionsLabel = new JLabel("Disconnections: ");
	public static JLabel LBLDISCONNECTIONCOUNTER = new JLabel(Integer.toString(DISCONNECTIONCOUNTER));
	
	
	
	//panels to hold UI elements (used to properly arrange elements)
	JPanel pnlAddressInstruction = new JPanel();
	JPanel pnlAddress = new JPanel();
	JPanel pnlFrequency = new JPanel();	
	JPanel pnlMonitorButton = new JPanel();
	JPanel pnlLeftSide = new JPanel();
	JPanel pnlRightSide = new JPanel();
	JPanel pnlBlankAfterFirstAddressGroup = new JPanel();
	JPanel pnlBlankAfterSecondAddressGroup = new JPanel();
	JPanel pnlBlankAfterMonitorButton = new JPanel();
	JPanel pnlBlankAfterDisconnectionsCounter = new JPanel();
	JPanel pnlBlankAfterPlaySoundGroup = new JPanel();
	JPanel pnlBlankAfterAutosaveGroup = new JPanel();
	JPanel pnlControlButtons = new JPanel();
	
	JPanel pnlNewCheckbox = new JPanel();
	JPanel pnlNewAddress = new JPanel();
	JPanel pnlNewFrequency = new JPanel();
	
	JPanel pnlPlaySound = new JPanel();
	JPanel pnlLoopSound = new JPanel();
	
	JPanel pnlAutosaveCheckbox = new JPanel();
	JPanel pnlAutosavePath = new JPanel();
	
	JPanel pnlDisconnections = new JPanel();
	
	JPanel pnlAbout = new JPanel();
		
	
	//here is where everything starts
	public static void main(String[] args) {
				
		UserInterface start = new UserInterface();		
		start.BuildInterface();	
		
	}
	
	
	public void BuildInterface(){	
	
		
		//Set look and feel. Prefer Nimbus if available
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			System.out.println("Error when trying to set Nimbus Look and Feel. Using Metal Look and Feel.");
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
				
		//format user interface elements		
						
		txtAddress.setText("www.google.com");
		txtSecondaryAddress.setText("www.yahoo.com");
		txtSecondaryAddress.setEnabled(false);
		txtFrequency.setText("5");
		txtFrequency.setHorizontalAlignment(JTextField.RIGHT);
		txtSecondaryFrequency.setText("5");
		txtSecondaryFrequency.setEnabled(false);
		txtSecondaryFrequency.setHorizontalAlignment(JTextField.RIGHT);
		lblSecondaryAddress.setForeground(Color.GRAY);
		lblSecondaryFrequency.setForeground(Color.GRAY);
		lblSecondarySeconds.setForeground(Color.GRAY);
		txtAddress.setDisabledTextColor(Color.GRAY);
		txtSecondaryAddress.setDisabledTextColor(Color.GRAY);
		txtFrequency.setDisabledTextColor(Color.GRAY);
		txtSecondaryFrequency.setDisabledTextColor(Color.GRAY);
		CHKSECONDARYADDRESS.setSelected(false);		
		lblDisconnectionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		LBLDISCONNECTIONCOUNTER.setFont(new Font("Arial", Font.BOLD, 20));
		LBLDISCONNECTIONCOUNTER.setForeground(Color.GREEN);
		CHKPLAYSOUNDLOOP.setEnabled(false);	
		lblAutoSaveLabel.setForeground(Color.GRAY);
		TXTAUTOSAVEPATH.setText(System.getProperty("user.dir") + // Directory from where the application was launched
                File.separator +
                "Log.txt");
		TXTAUTOSAVEPATH.setEnabled(false);
		TXTAUTOSAVEPATH.setDisabledTextColor(Color.GRAY);
		
		
		OUTPUT.setLineWrap(true);
		scrOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		OUTPUT.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));		
			
		scrOutput.setPreferredSize(new Dimension(523, 600));
		pnlLeftSide.setPreferredSize(new Dimension(300, 700));
		pnlRightSide.setPreferredSize(new Dimension(525, 700));
		pnlBlankAfterFirstAddressGroup.setPreferredSize(new Dimension(230, 20));
		pnlBlankAfterSecondAddressGroup.setPreferredSize(new Dimension(230, 20));
		pnlBlankAfterMonitorButton.setPreferredSize(new Dimension(230, 5));
		pnlBlankAfterDisconnectionsCounter.setPreferredSize(new Dimension(230, 84));
		pnlBlankAfterPlaySoundGroup.setPreferredSize(new Dimension(230, 10));
		pnlBlankAfterAutosaveGroup.setPreferredSize(new Dimension(230, 30));
		btnMonitor.setPreferredSize(new Dimension(170, 26));
		btnSave.setPreferredSize(new Dimension(103, 26));
		btnClear.setPreferredSize(new Dimension(103, 26));
		btnExit.setPreferredSize(new Dimension(103, 26));
		btnAbout.setPreferredSize(new Dimension(103, 26));
		
		
		// Create panels that will contain elements. Each panel contains elements aligned horizontally. 
		// For each "line" of elements I am using a separate panel
		pnlAddressInstruction.setLayout(new BasicGridLayout(1,1));
		pnlAddressInstruction.add(lblAddressInstruction, ParagraphLayout.NEW_PARAGRAPH);
		
		pnlAddress.setLayout(new BasicGridLayout(1,2));
		pnlAddress.add(lblAddress);
		pnlAddress.add(txtAddress);
		
		pnlFrequency.setLayout(new BasicGridLayout(1,3));
		pnlFrequency.add(lblFrequency);
		pnlFrequency.add(txtFrequency);
		pnlFrequency.add(lblSeconds);	
		
		pnlNewCheckbox.setLayout(new BasicGridLayout(1,1));
		pnlNewCheckbox.add(CHKSECONDARYADDRESS);
		
		pnlNewAddress.setLayout(new BasicGridLayout(1,2));
		pnlNewAddress.add(lblSecondaryAddress);
		pnlNewAddress.add(txtSecondaryAddress);
		
		pnlNewFrequency.setLayout(new BasicGridLayout(1,3));
		pnlNewFrequency.add(lblSecondaryFrequency);
		pnlNewFrequency.add(txtSecondaryFrequency);
		pnlNewFrequency.add(lblSecondarySeconds);
		
		pnlPlaySound.setLayout(new BasicGridLayout(1,1));
		pnlPlaySound.add(CHKPLAYSOUND);
		
		pnlLoopSound.setLayout(new BasicGridLayout(1,1));
		pnlLoopSound.add(CHKPLAYSOUNDLOOP);		
		
		pnlAutosaveCheckbox.setLayout(new BasicGridLayout(1,1));
		pnlAutosaveCheckbox.add(CHKAUTOSAVE);
		
		pnlAutosavePath.setLayout(new BasicGridLayout(1,2));
		pnlAutosavePath.add(lblAutoSaveLabel);
		pnlAutosavePath.add(TXTAUTOSAVEPATH);
		
		pnlDisconnections.add(lblDisconnectionsLabel);
		pnlDisconnections.add(LBLDISCONNECTIONCOUNTER);
		
		pnlMonitorButton.add(btnMonitor);	
		
		pnlAbout.add(btnAbout);
		
		pnlControlButtons.add(btnSave);
		pnlControlButtons.add(btnClear);
		pnlControlButtons.add(btnExit);
		
		pnlLeftSide.setLayout(new BasicGridLayout(0,1,5,5,25,15));
		pnlLeftSide.add(pnlAddressInstruction);		
		pnlLeftSide.add(pnlAddress);
		pnlLeftSide.add(pnlFrequency);
		pnlLeftSide.add(pnlBlankAfterFirstAddressGroup);
		pnlLeftSide.add(pnlNewCheckbox);
		pnlLeftSide.add(pnlNewAddress);
		pnlLeftSide.add(pnlNewFrequency);
		pnlLeftSide.add(pnlBlankAfterSecondAddressGroup);		
		pnlLeftSide.add(pnlMonitorButton);
		pnlLeftSide.add(pnlBlankAfterMonitorButton);
		pnlLeftSide.add(pnlDisconnections);
		pnlLeftSide.add(pnlBlankAfterDisconnectionsCounter);
		pnlLeftSide.add(Box.createVerticalStrut(5));
		pnlLeftSide.add(new JSeparator(SwingConstants.HORIZONTAL));
		pnlLeftSide.add(Box.createVerticalStrut(5));
		pnlLeftSide.add(pnlPlaySound);
		pnlLeftSide.add(pnlLoopSound);
		pnlLeftSide.add(pnlBlankAfterPlaySoundGroup);
		pnlLeftSide.add(pnlAutosaveCheckbox);
		pnlLeftSide.add(pnlAutosavePath);
		pnlLeftSide.add(Box.createVerticalStrut(5));
		pnlLeftSide.add(new JSeparator(SwingConstants.HORIZONTAL));
		pnlLeftSide.add(Box.createVerticalStrut(5));
		pnlLeftSide.add(pnlBlankAfterAutosaveGroup);

		pnlLeftSide.add(pnlAbout);
		
		pnlRightSide.add(scrOutput);	
		pnlRightSide.add(pnlControlButtons);				
		
		frame.getContentPane().add(BorderLayout.WEST, pnlLeftSide);
		frame.getContentPane().add(BorderLayout.EAST, pnlRightSide);
		frame.setResizable(false);
		
		frame.setLocationByPlatform(true);
		frame.setSize(850, 700);
		frame.setVisible(true);
		
		btnMonitor.addActionListener(new StartMonitor());	
		btnClear.addActionListener(new ClearTextArea());
		btnSave.addActionListener(new SaveLog());
		btnExit.addActionListener(new ExitProgram());
		CHKSECONDARYADDRESS.addActionListener(new CheckboxStatus());
		CHKPLAYSOUND.addActionListener(new CheckboxStatus());
		CHKPLAYSOUNDLOOP.addActionListener(new CheckboxStatus());
		CHKAUTOSAVE.addActionListener(new CheckboxStatus());
		btnAbout.addActionListener(new AboutMessage());
		
	}
	

	class StartMonitor implements ActionListener {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			
			OUTPUT.setEditable(false);
			
			//start primary website monitor
			try{
				netRun.setFrequency(Integer.parseInt(txtFrequency.getText()));
			} 
			catch (NumberFormatException e){				
				OUTPUT.append("ERROR: The primary \"Frequency\" field does not have a proper integer number!" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				return;
			}	
						
			netRun.setAddress("http://" + txtAddress.getText());			
			
			netRun.setFlag((byte) 0);
			
			//start secondary website monitor
			
			if (CHKSECONDARYADDRESS.isSelected() == true){
				
				try{
					netRunSecondary.setFrequency(Integer.parseInt(txtSecondaryFrequency.getText()));
				} 
				catch (NumberFormatException e){				
					OUTPUT.append("ERROR: The secondary \"Frequency\" field does not have a proper integer number!" + System.getProperty("line.separator") + System.getProperty("line.separator"));
					return;
				}	
							
				netRunSecondary.setAddress("http://" + txtSecondaryAddress.getText());			
				
				netRunSecondary.setFlag((byte) 0);
			}
			
						
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			btnMonitor.setText("Pause Monitoring");			
			OUTPUT.append("Monitoring started" + System.getProperty("line.separator"));
			
			
			//disable user interface controls while the program is monitoring
			
			txtAddress.setEnabled(false);
			txtFrequency.setEnabled(false);		
			lblAddressInstruction.setForeground(Color.GRAY);
			lblAddress.setForeground(Color.GRAY);
			lblFrequency.setForeground(Color.GRAY);
			lblSeconds.setForeground(Color.GRAY);
			CHKSECONDARYADDRESS.setEnabled(false);
			txtSecondaryAddress.setEnabled(false);
			txtSecondaryFrequency.setEnabled(false);
			lblSecondaryAddress.setForeground(Color.GRAY);
			lblSecondaryFrequency.setForeground(Color.GRAY);
			lblSecondarySeconds.setForeground(Color.GRAY);
			CHKPLAYSOUND.setEnabled(false);
			CHKPLAYSOUNDLOOP.setEnabled(false);
			CHKAUTOSAVE.setEnabled(false);
			TXTAUTOSAVEPATH.setEnabled(false);
			lblAutoSaveLabel.setForeground(Color.GRAY);
			btnClear.setEnabled(false);
			btnSave.setEnabled(false);
			
			
			//switch button functionality to from Start to End monitoring
			btnMonitor.removeActionListener(this);
			btnMonitor.addActionListener(new EndMonitor());	
			
			
			
			//start monitor threads
			if (threadFlag == 0){
				netThread.start();
				threadFlag = 1;
			} else{
				netThread.resume();
			}
			
			if (CHKSECONDARYADDRESS.isSelected() == true){
				if (threadFlagSecondary == 0){
					netThreadSecondary.start();
					threadFlagSecondary = 1;
				} else{
					netThreadSecondary.resume();
				}
			}
		}						
	}
	
	
	class EndMonitor implements ActionListener {		
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {		
			
			//pause monitor threads
			try {
				netThread.suspend();
			} catch (Exception e) {
				e.printStackTrace();
				OUTPUT.append("ERROR: Primary monitor thread suspend exception" + System.getProperty("line.separator") + System.getProperty("line.separator"));
			}
			
			netRun.setFlag((byte) 1);						
			
			
			if (CHKSECONDARYADDRESS.isSelected() == true){
				try {
					netThreadSecondary.suspend();
				} catch (Exception e) {
					e.printStackTrace();
					OUTPUT.append("ERROR: Secondary monitor thread suspend exception" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				}
				
				netRunSecondary.setFlag((byte) 1);	
				
				//re-enable user interface elements				
				txtSecondaryAddress.setEnabled(true);
				txtSecondaryFrequency.setEnabled(true);
				lblSecondaryAddress.setForeground(Color.BLACK);
				lblSecondaryFrequency.setForeground(Color.BLACK);
				lblSecondarySeconds.setForeground(Color.BLACK);
			}
			
			if (CHKPLAYSOUND.isSelected() == true){
				CHKPLAYSOUNDLOOP.setEnabled(true);
			}
			
			if (CHKAUTOSAVE.isSelected() == true){
				TXTAUTOSAVEPATH.setEnabled(true);
				lblAutoSaveLabel.setForeground(Color.BLACK);
			}
			
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			
			//re-enable user interface elements
			btnMonitor.setText("Resume Monitoring");		
			OUTPUT.append("Monitoring stopped by user" + System.getProperty("line.separator") + System.getProperty("line.separator"));
						
			OUTPUT.setEditable(true);
			CHKSECONDARYADDRESS.setEnabled(true);
			lblAddressInstruction.setForeground(Color.BLACK);
			lblAddress.setForeground(Color.BLACK);
			lblFrequency.setForeground(Color.BLACK);
			lblSeconds.setForeground(Color.BLACK);
			txtAddress.setEnabled(true);
			txtFrequency.setEnabled(true);
			CHKPLAYSOUND.setEnabled(true);
			CHKAUTOSAVE.setEnabled(true);
			btnClear.setEnabled(true);
			btnSave.setEnabled(true);
			
			//switch button functionality to from End to Start monitoring
			btnMonitor.removeActionListener(this);
			btnMonitor.addActionListener(new StartMonitor());			
		}	
	}
	
	
	class ClearTextArea implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {	
			
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			
			//clear output window text
			OUTPUT.setText("");
			
			btnMonitor.setText("Start Monitoring");
			
			//clear disconnection counter elements
			DISCONNECTEDPRIMARYSITE = 0;
			DISCONNECTEDSECONDARYSITE = 0;
			DISCONNECTIONCOUNTER = 0;
			SAMEDISCONNECTION = 0;
			LBLDISCONNECTIONCOUNTER.setText(Integer.toString(DISCONNECTIONCOUNTER));
			LBLDISCONNECTIONCOUNTER.setForeground(Color.GREEN);
		}	
	}
	
	
	class SaveLog implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			
			//ask user where to save output file
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(frame);
			
			File file = fileChooser.getSelectedFile();			
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(OUTPUT.getText());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				OUTPUT.append("File write error. The file is unavailable or read-only." + System.getProperty("line.separator") + System.getProperty("line.separator"));				
				e.printStackTrace();
			} catch (NullPointerException e)	{
				System.out.println("Save file dialog canceled. File was not saved.");
			}
		}	
	}
	
	class CheckboxStatus implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			if (CHKSECONDARYADDRESS.isSelected() == true) {
				txtSecondaryAddress.setEnabled(true);
				txtSecondaryFrequency.setEnabled(true);
				lblSecondaryAddress.setForeground(Color.BLACK);
				lblSecondaryFrequency.setForeground(Color.BLACK);
				lblSecondarySeconds.setForeground(Color.BLACK);
			}
			else {
				txtSecondaryAddress.setEnabled(false);
				txtSecondaryAddress.setEnabled(false);
				txtSecondaryFrequency.setEnabled(false);
				lblSecondaryAddress.setForeground(Color.GRAY);
				lblSecondaryFrequency.setForeground(Color.GRAY);
				lblSecondarySeconds.setForeground(Color.GRAY);
			}
			
			if (CHKPLAYSOUND.isSelected() == true) {
				CHKPLAYSOUNDLOOP.setEnabled(true);
			}
			else {
				CHKPLAYSOUNDLOOP.setEnabled(false);
				CHKPLAYSOUNDLOOP.setSelected(false);
				
			}
			
			if (CHKAUTOSAVE.isSelected() == true) {
				TXTAUTOSAVEPATH.setEnabled(true);
				lblAutoSaveLabel.setForeground(Color.BLACK);
			}
			else {
				TXTAUTOSAVEPATH.setEnabled(false);
				lblAutoSaveLabel.setForeground(Color.GRAY);
			}
			
			
			
		}
	}
	


	class AboutMessage implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {			
			
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			

			//show message box with application version information
			
			String aboutMessage = "<html><body><b style='font-size:15px'>Internet Connectivity Monitor</b>" +
					"<br>v1.41 - August 2013" +
					"<br>Developed by <b>Genc Alikaj</b> (<a href='mailto:gencalikaj@gmail.com' style='text-decoration: none'>gencalikaj@gmail.com</a>)" + 
					"<br><br>The following portions were developed/fixed by <b>Jhobanny Morillo</b>:" + 
					"<ul><li>Disconnections counter</li>" +
					"<li>Text formatting in log file</li></ul><br>" +
					"For more info and the latest version of the program visit:<br>" +
					"<a href='http://code.google.com/p/internetconnectivitymonitor/' style='text-decoration: none'>http://code.google.com/p/internetconnectivitymonitor/</a></body></html>";			
				
			JOptionPane.showMessageDialog(null,aboutMessage, "About Internet Connectivity Monitor", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	
	
	class ExitProgram implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {	
			
			//changing element focus back to the main frame to avoid ugly rectangles around button text
			frame.requestFocus();
			
			//exit program
			System.exit(0);			
		}	
	}
}
