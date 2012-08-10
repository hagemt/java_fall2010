package edu.rpi.hagemt.hw5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Homework #5 -- Bank Accounts
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class AccountApp {
	// The definition of how dollar amounts are expressed is a currency symbol (the '$')
	// This is followed by a string of numeric characters delineated per thousand units.
	// That is, until the ones, where after two decimal places (cents) appear.
	private static final DecimalFormat money_format = new DecimalFormat("$#,###.00");

	/**
	 * USAGE: java edu.rpi.hagemt.hw5.AccountApp
	 * @param args program takes no arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Account application");
		System.out.println();
		
		// Setup a collection to house the accounts, and add one of each type
		TreeMap<String, Account> accounts = new TreeMap<String, Account>();
		accounts.put("Checking", new CheckingAccount(1000.00, 1.00));
		accounts.put("Savings", new SavingsAccount(1000.00, 0.01));

		// Print the starting balances of everything
		System.out.println("Starting Balances");
		for (Entry<String, Account> e : accounts.entrySet()) {
			System.out.println(e.getKey() + ": " + money_format.format(e.getValue().getBalance()));
		}
		System.out.println();

		// Begin the interactive portion
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the transactions for the month");
			try {
				// Loop this prompt for each continual transaction
				do {
					System.out.println();

					// Get details of the transaction, validated for user error
					TransactionType transaction_type = Validator.getTransactionType(reader);
					AccountType account_type = Validator.getAccountType(reader);
					Account a = accounts.get(account_type.toString());
					double transaction_amount = Validator.getTransactionAmount(reader, a.getBalance());

					// Perform the relevant modifications to the account, a, given properties of the transaction
					switch (transaction_type) {
						case WITHDRAWAL: Transactions.withdraw(a, transaction_amount); break;
						case DEPOSIT: Transactions.deposit(a, transaction_amount); break;
					}
					
					// Prompt to go on
					System.out.println();
					System.out.print("Continue? (y/n): ");
				} while (reader.readLine().toUpperCase().equals("Y"));

				// Print the monthly adjustments of each account, and then do those adjustments
				System.out.println();
				System.out.println("Monthly Payments and Fees");
				for (Entry<String, Account> e : accounts.entrySet()) {
					Account a = e.getValue();
					System.out.println(a.getAdjustmentDescription() + money_format.format(Math.abs(a.getMonthlyAdjustments())));
					a.applyMonthlyAdjustments();
				}
				
				// Print out the final balance for each account
				System.out.println();
				System.out.println("Final Balances");
				for (Entry<String, Account> e : accounts.entrySet()) {
					System.out.println(e.getKey() + ":\t" + money_format.format(e.getValue().getBalance()));
				}
				System.out.println();
			} catch (IOException ioe) {
				// In the case of catastrophic error, we have an exit
				System.err.println("Error! Could not read/write on the console!");
			} finally {
				// Cleanup the console resources
				reader.close();
			}
		} catch (Exception e) {
			// Catch-all for other problems
			e.printStackTrace();
		}
	}
}
