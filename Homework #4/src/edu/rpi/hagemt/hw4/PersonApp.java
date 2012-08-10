package edu.rpi.hagemt.hw4;

import edu.rpi.hagemt.contacts.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The main class for Homework #4 -- Person Tester
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public class PersonApp {
	/**
	 * Regular expression strings to match each desired input type
	 */
	// Match all word and digit characters, with a few other characters.
	// Follow that with a single '@', and then text formatted as a domain name.
	private static final String email_address_regex = "[\\w\\d._%+-]+@(?:[\\w\\d-]+\\.)+[a-zA-Z]{2,6}";
	// Match string to a capital letter followed by five digits.
	private static final String customer_number_regex = "[A-Z]\\d{5}";
	// Match any three digits followed by dash, followed by two digits, another dash, and then four more digits.
	private static final String social_security_number_regex = "\\d{3}\\-\\d{2}\\-\\d{4}";
	
	// Hides the constructor from javadoc
	private PersonApp() { }
	
	/**
	 * USAGE: java edu.rpi.hagemt.hw4.PersonApp
	 * @param args command line arguments, program takes none
	 */
	public static void main(String... args) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Welcome to the Person Tester application"); Person p;
			try {
				do {
					System.out.println();
					String type = Validator.getValidString(reader, "Create customer or employee? (c/e): ", "c|e");
					System.out.println();
					if (type != null) {
						System.out.print("Enter first name: ");
						String first_name = reader.readLine();
						System.out.print("Enter last name: ");
						String last_name = reader.readLine();
						String email_address =
							Validator.getValidString(reader, "Enter email address: ", email_address_regex);
						switch (type.toLowerCase().charAt(0)) {
							case 'c' :
								String customer_number =
									Validator.getValidString(reader, "Customer number: ", customer_number_regex);
								p = new Customer(first_name, last_name, email_address, customer_number);
								break;
							case 'e' :
								String social_security_number =
									Validator.getValidString(reader, "Social Security number: ", social_security_number_regex);
								p = new Employee(first_name, last_name, email_address, social_security_number);
								break;
							default  :
								p = null;
						}
						System.out.println();
						System.out.println("You entered:");
						print(p);
						System.out.println();
					}
					System.out.print("Continue? (y/n): ");
				} while (reader.readLine().toUpperCase().equals("Y"));
				System.out.println();
			} catch (IOException ioe) {
				System.out.println();
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.err.println("Error! Could not read/write on console!");
		}
	}
	
	/**
	 * Utility method to print information about general <code>Person</code> objects
	 * @param p the <code>Person</code> object to print information about
	 */
	public static void print(Person p) {
		System.out.println(p.getDisplayText());
	}
}
