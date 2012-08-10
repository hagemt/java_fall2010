package edu.rpi.hagemt.hw5;

/**
 * Enumerates the possible subtypes of Account: CheckingAccount and SavingsAccount.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public enum AccountType {
	CHECKING, SAVINGS;
	
	/**
	 * Returns a user-friendly name for each account type.
	 */
	@Override
	public String toString() {
		switch (this) {
			case CHECKING : return "Checking";
			case SAVINGS : return "Savings";
			default : return null;
		}
	}
}
