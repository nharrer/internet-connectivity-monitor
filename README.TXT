Internet Connectivity Monitor v1.41


-= Changelog =-


v1.41 (August 11, 2013)
---------------------------

- The default save location is now the current location of the executable. This makes it more cross-platform friendly. (Thanks Anders Wenhaug)

- (Code) Synchronized the two Disconnection class methods. (Thanks Anders Wenhaug)


v1.40 (July 11, 2013)
---------------------------

- (Windows only) Added option to play disconnect sound notification continuously until connection is reestablished.

- Added option to automatically create and write to a log file while monitoring is active.

- (Code) Switched from Swing BoxLayout for certain interface components to a 3rd party Java layout manager (BasicGridLayout as found on: http://jhlabs.com/java/layout/index.html).


v1.30 (June 18, 2013)
---------------------------

- (Windows only) Added the option to play sound notifications on disconnect. The Disconnected.wav sound file needs to be located in the same directory as the main program file. No sound will be played if the file is missing, but the software will continue to function properly.


v1.20 (Sept 3, 2012)
---------------------------

- The user can now monitor two websites at the same time. This is very helpful when trying to differentiate between flaky connections and websites experiencing downtime.

- Added a disconnection counter. Idea and initial implementation by Geovanny Morillo. When monitoring two websites, both sites need to be unreachable for the counter to go up.

- The user interface has been improved. Buttons are now larger, "Nimbus" style is being used for certain elements, plus many other minor changes. The user interface looks best on Windows. It also looks very good on Ubuntu. On Macs it doesn't look as nice (only tested it on OSX 10.5.5), but it's still 100% functional.

- Many other little changes and fixes, such as right-alignment of frequency text, expanded Clear button functionality, output area expansion, etc.


v1.01 (Jul 29, 2012)
---------------------------

- The Windows Notepad issue has been fixed. Saved logs are now formatted correctly in Notepad (Fixed by Geovanny Morillo)

- On the previous version, the connection attempt frequency when disconnected was locked at 2 seconds. This has now been fixed to reflect the custom frequency set by the user.

- Some minor grammar mistakes have been fixed.


v1.00 (Aug 2010)
---------------------------

- Initial release.




-= About =-

Internet Connectivity Monitor is a lightweight program that provides you with a way to monitor your internet connection uptime. It attempts to connect to an internet host of your choice (i.e. google.com) every few seconds, and log whether it was successful or not.

You can leave this running overnight, and then read the log in the morning to see if, when, and for how long your internet connection was down.



-= Instructions =-

You will need Java Runtime Environment 6 Update 10 (JRE 1.6.10) or later. If you don't have it, you can download it at: http://www.java.com/. Since this program contains nothing but pure Java, it can run on Windows, Mac OS, or Linux, as long as the aforementioned JRE has been installed. 

For Windows users I have created an .EXE version. I have also included a .JAR version for Linux/Mac OS users.

After launching the program, you can start the monitoring process by clicking on the "Start Monitoring" button. You can either use the default internet address and frequency (google.com every 5 seconds), or customize them. It's also OK to use an IP address instead of a domain name (as long as the server uses HTTP). You can also pause the monitoring process at any point to change monitoring settings and/or save whatever is currently displayed in the current log window to an external text file.

If the message displayed on the output window says "OK", the connection attempt was successful (which means that the internet connection was up at that particular moment). Likewise, if the message says "NOT CONNECTED", the connection was unsuccessful. An "unsuccessful connection" means the program was unable to get a response from the server. This indicates that either your internet connection was down, or the remote server was down. 

You can either use the default internet address and frequency (google.com every 5 seconds), or customize them. Using an IP address instead of a domain name is OK, as long as the server uses HTTP. You can also pause the monitoring process at any point to change the values, and/or save whatever is currently being displayed in the log window to an external text file.



-= License =-

GNU General Public License. See LICENSE.TXT before distributing/modifying this program.


2010 - 2013 
Written in Java using Eclipse
Developer: Genc Alikaj