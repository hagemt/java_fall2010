package edu.rpi.hagemt.contacts;

/**
 * Encapsulates the concept of a customer-type person.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public class Customer extends Person {
	private String m_customer_number;
	
	/**
	 * Constructs a default <code>Customer</code> object with no information.
	 */
	public Customer() { super(); }
	
	/**
	 * Constructs a <code>Customer</code> object with the given information.
	 * @param first_name the <code>Customer</code>'s first name
	 * @param last_name the <code>Customer</code>'s last name
	 * @param email_address the <code>Customer</code>'s email address
	 * @param customer_number the <code>Customer</code>'s customer number
	 */
	public Customer(String first_name, String last_name, String email_address, String customer_number) {
		super(first_name, last_name, email_address);
		m_customer_number = customer_number;
	}

	/**
	 * Returns the customer number of this <code>Customer</code>.
	 * @return a <code>String</code> containing this <code>Customer</code>'s customer number
	 */
	public String getCustomerNumber() {
		return m_customer_number;
	}
	
	/**
	 * Sets this <code>Customer</code>'s customer number field using the given information.
	 * @param customer_number a <code>String</code> containing this <code>Customer</code>'s new email address
	 */
	public void setCustomerNumber(String customer_number) {
		m_customer_number = customer_number;
	}

	/**
	 * Provides a textual description for all <code>Customer</code> objects.
	 * @return a <code>String</code> containing that representation
	 * @see edu.rpi.hagemt.contacts.Person#toString()
	 */
	@Override
	public String getDisplayText() {
		return super.toString() + "\nCustomer number: " + m_customer_number;
	}
}
