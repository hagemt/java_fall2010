package edu.rpi.hagemt.contacts;

/**
 * Encapsulates the concept of an employee-type person.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public class Employee extends Person {
	private String m_social_security_number;
	
	/**
	 * Constructs a default <code>Employee</code> object with no information.
	 */
	public Employee() { super(); }
	
	/**
	 * Constructs an <code>Employee</code> object with the given information.
	 * @param first_name the <code>Employee</code>'s first name
	 * @param last_name the <code>Employee</code>'s last name
	 * @param email_address the <code>Employee</code>'s email address
	 * @param social_security_number the <code>Employee</code>'s social security number
	 */
	public Employee(String first_name, String last_name, String email_address, String social_security_number) {
		super(first_name, last_name, email_address);
		m_social_security_number = social_security_number;
	}
	
	/**
	 * Returns the social security number of this <code>Employee</code>.
	 * @return a <code>String</code> containing this <code>Employee</code>'s social security number
	 */
	public String getSocialSecurityNumber() {
		return m_social_security_number;
	}
	
	/**
	 * Sets this <code>Employee</code>'s social security number field using the given information.
	 * @param social_security_number a <code>String</code> containing this <code>Employee</code>'s new social security number
	 */
	public void setSocialSecurityNumber(String social_security_number) {
		m_social_security_number = social_security_number;
	}

	/**
	 * Provides a textual description for all <code>Employee</code> objects.
	 * @return a <code>String</code> containing that representation
	 * @see edu.rpi.hagemt.contacts.Person#toString()
	 */
	@Override
	public String getDisplayText() {
		return super.toString() + "\nSocial security number: " + m_social_security_number;
	}
}
