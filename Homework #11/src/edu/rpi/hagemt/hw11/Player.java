/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #11 -- Project 17.1 Extended Again
 */
package edu.rpi.hagemt.hw11;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Encapsulates the data associated with an athlete.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Player implements Comparable<Player>, Serializable {
	// Representation, publicly accessible field information
	public String firstName, lastName;
	public int playerNumber;
	
	// Private date utilities: storage, parser and validator
	private GregorianCalendar dateOfBirth;
	private static DateFormat date_format = new SimpleDateFormat("MM/dd/yyyy");
	private static final long serialVersionUID = -7278634080456339863L;
	
	/**
	 * Constructs a Player object with the given information.
	 * @param given a String containing the Player's first (given) name.
	 * @param family a String containing the Player's last (family) name.
	 * @param number a String containing the Player's designated number.
	 * @param dob a String containing the Player's date of birth in MM/DD/YYYY format.
	 */
	public Player(String given, String family, String number, String dob) {
		// Validate for empty name fields
		if (given == null || family == null || given.isEmpty() || family.isEmpty()) {
			throw new IllegalArgumentException("Please enter full names in the appropriate fields.");
		}
		firstName = given;
		lastName = family;
		// Get the numeric data from text
		try {
			playerNumber = Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Please enter a valid number.");
		}
		// Get the date data from text, using the default locale and parser
		dateOfBirth = new GregorianCalendar(Locale.getDefault());
		try {
			dateOfBirth.setTime(date_format.parse(dob));
		} catch (ParseException pe) {
			throw new IllegalArgumentException("Please enter dates in the following format: MM/dd/yyyy");
		}
	}
	
	/**
	 * Get a textual description of this Player's birth date.
	 * @return a String containing this Player's date of birth, formatted MM/DD/YYYY.
	 */
	public String getBirthDate() {
		return date_format.format(dateOfBirth.getTime());
	}
	
	/**
	 * Get how many years-old this Player is.
	 * @return a numeric value expressing this Player's age in years.
	 */
	public int getAge() {
		GregorianCalendar today = new GregorianCalendar(Locale.getDefault());
		int years = today.get(GregorianCalendar.YEAR) - dateOfBirth.get(GregorianCalendar.YEAR);
		int months = today.get(GregorianCalendar.MONTH) - dateOfBirth.get(GregorianCalendar.MONTH);
		int days = today.get(GregorianCalendar.DAY_OF_MONTH) - dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH);		
		return (months > 0 || months == 0 && days >= 0) ? years : years - 1;
	}

	@Override
	public int compareTo(Player p) {
		// Compare two players first alphabetically by name, last first, then by number, and finally by birth date.
		// Players a and b having all these fields identical will make the call a.compareTo(b) == 0.
		if (lastName.equals(p.lastName)) {
			if (firstName.equals(p.firstName)) {
				if (playerNumber == p.playerNumber) {
					return dateOfBirth.compareTo(p.dateOfBirth);
				}
				return playerNumber - p.playerNumber;
			}
			return firstName.compareToIgnoreCase(p.firstName);
		}
		return lastName.compareToIgnoreCase(p.lastName);
	}
	
	@Override
	public String toString() {
		// A textual description of a Player is his/her name followed by number in parentheses.
		return firstName + " " + lastName + " (" + playerNumber + ")";
	}
}
