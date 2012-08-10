package edu.rpi.hagemt.hw5;

/**
 * Marks types from which we can extract and define information regarding a "balance."
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public interface Balanceable {
	public double getBalance();
	public void setBalance(double amount);
}
