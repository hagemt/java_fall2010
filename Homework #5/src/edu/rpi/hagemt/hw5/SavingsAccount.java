package edu.rpi.hagemt.hw5;

/**
 * Encapsulates the specialized Account used for savings, with associated accruing interest rate.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class SavingsAccount extends Account {
	private double m_monthly_interest;
	
	/**
	 * Construct a default SavingsAccount with no balance or interest rate.
	 */
	public SavingsAccount() { this(0, 0); }
	
	/**
	 * Construct a SavingsAccount with a given balance and interest rate, as a percentage.
	 * (monthly_interest = 1 = 100%)
	 */
	public SavingsAccount(double balance, double monthly_interest) {
		super(balance);
		m_monthly_interest = monthly_interest;
	}
	
	/**
	 * A SavingsAccount's adjustment is its accruing interest.
	 */
	@Override
	public String getAdjustmentDescription() {
		return "Savings interest payment:\t";
	}
	
	/**
	 * The monthly adjustment to a SavingsAccount constitutes adding a percentage of the balance as interest accrued.
	 * Therefore, the difference in balance between next month and this one is m_balance * m_monthly_interest.
	 */
	@Override
	public double getMonthlyAdjustments() {
		return getBalance() * m_monthly_interest;
	}
}
