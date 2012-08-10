package edu.rpi.hagemt.hw5;

/**
 * Abstractly defines the general form of a bank account.
 * A bank account can be deposited into, withdrawn from,
 * and may have its balance fetched or modified.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public abstract class Account implements Balanceable, Depositable, Withdrawable {
	private double m_balance;
	
	/**
	 * Constructs a default Account with no balance.
	 */
	protected Account() { this(0); }
	
	/**
	 * Constructs a default Account with given balance.
	 */
	protected Account(double balance) { m_balance = balance; }

	/**
	 * Force subclasses to define in words how its monthly adjustment functions.
	 */
	public abstract String getAdjustmentDescription();
	
	/**
	 * Force subclasses to define the amount by which the Account is adjusted month to month.
	 */
	public abstract double getMonthlyAdjustments();
	
	/**
	 * Define the way monthly adjustment occurs as the addition of the Account's balance with the monthly adjustment.
	 * This behavior may be overridden or modified to lesser degrees by subclasses.
	 */
	public void applyMonthlyAdjustments() { m_balance += getMonthlyAdjustments(); }
	
	/**
	 * Define the end-all method for obtaining an Account's balance.
	 */
	@Override
	public final double getBalance() {
		return m_balance;
	}
	
	/**
	 * Define the end-all method for modifying an Account's balance.
	 */
	@Override
	public final void setBalance(double amount) {
		m_balance = amount;
	}
	
	/**
	 * Dumbly add the amount to the balance when depositing.
	 * Validation will occur on user input anyway.
	 */
	@Override public void deposit(double amount) {
		m_balance += amount;
	}
	
	/**
	 * Dumbly subtract the amount to the balance when depositing.
	 */
	@Override
	public void withdraw(double amount) {
		m_balance -= amount;
	}
}
