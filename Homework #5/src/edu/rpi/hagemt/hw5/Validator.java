package edu.rpi.hagemt.hw5;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A folder class for methods governing validation.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Validator {
	// Hide constructor
	private Validator() { }

	// The following three functions (re-)prompt the user for input until a valid choice is tendered.
	// Each method defines its own conception of what is and is not acceptable input.

	public static TransactionType getTransactionType(BufferedReader reader) {
		TransactionType type = null;
		do {
			System.out.print("Withdrawal or deposit? (w/d): ");
			try {
				String s = reader.readLine();
				// Look for a "matching" TransactionType in the input
				for (TransactionType t : TransactionType.values()) {
					// "type" will only be set if the user's input begins a TransactionType's name()
					if (t.name().toLowerCase().startsWith(s)) { type = t; }
				}
			} catch (IOException ioe) {
				// In the event of an inaccessible prompt, we need an exit
				return null;
			}
			// Otherwise, try again
		} while (type == null);
		return type;
	}
	
	public static AccountType getAccountType(BufferedReader reader) {
		AccountType type = null;
		do {
			System.out.print("Checking or savings? (c/s): ");
			try {
				String s = reader.readLine();
				// Look for a "matching" AccountType in the input
				for (AccountType t : AccountType.values()) {
					// "type" will only be set if the user's input begins an AccountType's name()
					if (t.name().toLowerCase().startsWith(s)) { type = t; }
				}
			} catch (IOException ioe) {
				// In the event of an inaccessible prompt, we need an exit
				return null;
			}
			// Otherwise, try again
		} while (type == null);
		return type;
	}
	
	public static double getTransactionAmount(BufferedReader reader, double limit) {
		assert (limit > 0) : "transaction limit (" + limit + ") is not positive";
		// To give us a default value
		double amount = Double.NaN;
		do {
			System.out.print("Amount?: ");
			try {
				// Try to get a valid number from the user
				amount = Double.parseDouble(reader.readLine());
			} catch (IOException ioe) {
				// In the event of an inaccessible prompt, we need an exit
				return Double.NaN;
			}
			// Valid inputs are in the interval [0, limit]; otherwise, try again
		} while (!(0 <= amount && amount <= limit));
		return amount;
	}
}
