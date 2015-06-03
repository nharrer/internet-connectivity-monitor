<b><u>Internet Connectivity Monitor</u></b>
<p>This a lightweight program that provides you with a way to monitor your internet connection uptime. It attempts to connect to an internet host of your choice (i.e. google.com) every few seconds, and log whether it was successful or not.</p>

<p>You can leave this running overnight, and then read the log in the morning to see if, when, and for how long your internet connection was down.</p>

<img src='http://internetconnectivitymonitor.googlecode.com/svn/wiki/ICM4.PNG' alt='Internet Connectivity Monitor screenshot' />

<b>Instructions</b>

<p>You will need Java Runtime Environment 6 Update 10 (JRE 1.6.10) or later. If you don't have it, you can download the JRE at: <a href='http://www.java.com/'>http://www.java.com/</a>. Since this program contains nothing but pure Java, it can run on Windows, Mac OS, or Linux, as long as the aforementioned JRE has been installed.</p>

<p>After launching the program, you can immediately start the monitoring process by clicking on the "Start Monitoring" button. You can either use the default internet address and frequency (google.com every 5 seconds), or customize them. It's also OK to use an IP address instead of a domain name (as long as the server uses HTTP). You can also pause the monitoring process at any point to change monitoring settings, and/or save whatever is currently displayed in the log window to an external text file.</p>

<p>If the message displayed on the output window says "OK", the connection attempt was successful (which means that the internet connection was up at that particular moment). Likewise, if the message says "NOT CONNECTED", the connection was unsuccessful. An "unsuccessful connection" means the program was unable to get a response from the server. This indicates that either your internet connection was down, or the remote server was down.</p>

<br />
<b>Version 1.41 Released - Changelog</b><br />

<ul>
<li>The default save location is now the current location of the executable. This makes it more cross-platform friendly. (Thanks Anders Wenhaug)</li>

<li>(Code) Synchronized the two Disconnection class methods. (Thanks Anders Wenhaug)</li>
</ul>

<br />
<b>Version 1.40 Released - Changelog</b><br />

<ul>
<li>(Windows only) Added option to play disconnect sound notification continuously until connection is reestablished.</li>

<li>Added option to automatically create and write to a log file while monitoring is active.</li>
</ul>

<br />
<b>Version 1.30 Released - Changelog</b><br />

<ul>
<li>(Windows only) Added the option to play sound notifications on disconnect. The Disconnected.wav sound file needs to be located in the same directory as the main program file. No sound will be played if the file is missing, but the software will continue to function properly.</li>
</ul>

<br />
<b>Version 1.20 Released - Changelog</b><br />

<ul>
<li>The user can now monitor two websites at the same time. This is very helpful when trying to differentiate between flaky connections and websites experiencing downtime.</li>

<li>Added a disconnection counter (<i>idea and initial implementation by Geovanny Morillo</i>). When monitoring two websites, both sites need to be unreachable for the counter to go up.</li>

<li>The user interface has been improved. Buttons are now larger, "Nimbus" style is being used for certain elements, plus many other minor changes. The user interface looks best on Windows. It also looks very good on Ubuntu. On Mac OS it doesn't look as nice (only tested it on OS X 10.5.5), but it's still 100% functional.</li>

<li>Many other little changes and fixes, such as right-alignment of frequency text, expanded Clear button functionality, output area expansion, etc.</li>

</ul>
<br />
<b>Version 1.01 Released - Changelog</b><br />

<ul>
<li>The Windows Notepad issue has been fixed. Saved logs are now formatted correctly in Notepad (<i>Fixed by Geovanny Morillo</i>)</li>

<li>On the previous version the connection attempt frequency was locked at 2 seconds. This has now been fixed to reflect the custom frequency set by the user.</li>

<li>A few grammatical mistakes have been fixed.</li>
</ul><br />
Enjoy!

Genc Alikaj<br /><br />


<b>Additional Screenshots:</b>

<img src='http://internetconnectivitymonitor.googlecode.com/svn/wiki/ICM5.PNG' alt='Internet Connectivity Monitor screenshot 2' /><br />
<img src='http://internetconnectivitymonitor.googlecode.com/svn/wiki/ICM6.PNG' alt='Internet Connectivity Monitor screenshot 3' />