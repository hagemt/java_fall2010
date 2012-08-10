package edu.rpi.hagemt.hw5;

/**
 * A folder class for methods governing deposit and withdrawal.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Transactions {
	// Hide constructor
	private Transactions() { }

	// Imperative helper methods for each type of transaction.
	public static void deposit(Depositable account, double amount) { account.deposit(amount); }
	public static void withdraw(Withdrawable account, double amount) { account.withdraw(amount); }
}
