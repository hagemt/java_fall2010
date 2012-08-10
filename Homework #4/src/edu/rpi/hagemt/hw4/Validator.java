package edu.rpi.hagemt.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * A folder class for methods governing validation.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public class Validator {
	// Hides the constructor from javadoc
	private Validator() { }
	
	/**
	 * A utility method to prompt the user until "valid" input is given via keyboard.
	 * @param reader the resource on which input from the user my be received
	 * @param prompt the text to display to the user before each attempt to match user input
	 * @param regex a regular expression used to validate the user input
	 * @return a <code>String</code> that matches the <code>regex</code> according to <code>java.util.regex.Pattern.matches(String,String)</code>
	 */
	public static String getValidString(BufferedReader reader, String prompt, String regex) {
		String string;
		do {
			System.out.print(prompt);
			try {
				string = reader.readLine();
			} catch (IOException ioe) {
				return null;
			}
		} while (!Pattern.matches(regex, string));
		return string;
	}
}
