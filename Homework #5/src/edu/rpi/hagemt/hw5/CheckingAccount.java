package edu.rpi.hagemt.hw5;

/**
 * Encapsulates the specialized Account used for checking, with associated service fee.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class CheckingAccount extends Account {
	private double m_monthly_fees;

	/**
	 * Construct a default CheckingAccount with no balance or fee.
	 */
	public CheckingAccount() { this(0, 0); }
	
	/**
	 * Construct a CheckingAccount with a given balance and monthly fee.
	 */
	public CheckingAccount(double balance, double monthly_fees) {
		super(balance);
		m_monthly_fees = monthly_fees;
	}
	
	/**
	 * A CheckingAccount's adjustment is its fee.
	 */
	@Override
	public String getAdjustmentDescription() {
		return "Checking fee:\t\t\t";
	}

	/**
	 * The monthly adjustment to a CheckingAccount constitutes subtracting its fees from the balance.
	 * Therefore, the difference in balance between next month and this one is -m_monthly_fees.
	 */
	@Override
	public double getMonthlyAdjustments() {
		return -m_monthly_fees;
	}
}
