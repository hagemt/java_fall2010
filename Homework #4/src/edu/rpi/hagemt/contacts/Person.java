package edu.rpi.hagemt.contacts;

/**
 * Encapsulates the concept of a person by name and email address.
 * Does not allow instantiation in the general sense.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public abstract class Person {
	private String m_first_name, m_last_name, m_email_address;

	/**
	 * Provides subclasses with a basic mechanism to fill a <code>Person</code> object with <code>null</code> name and email.
	 */
	protected Person() { }

	/**
	 * Provides subclasses with a mechanism to fill a <code>Person</code> object with the given information.
	 * @param first_name the <code>Person</code>'s first name
	 * @param last_name the <code>Person</code>'s last name
	 * @param email_address the <code>Person</code>'s email address
	 */
	protected Person(String first_name, String last_name, String email_address) {
		m_first_name = first_name;
		m_last_name = last_name;
		m_email_address = email_address;
	}

	/**
	 * Returns the first name of this <code>Person</code>.
	 * @return a <code>String</code> containing this <code>Person</code>'s first name
	 */
	public String getFirstName() {
		return m_first_name;
	}

	/**
	 * Returns the last name of this <code>Person</code>.
	 * @return a <code>String</code> containing this <code>Person</code>'s last name
	 */
	public String getLastName() {
		return m_last_name;
	}

	/**
	 * Returns the email address of this <code>Person</code>.
	 * @return a <code>String</code> containing this <code>Person</code>'s email address
	 */
	public String getEmailAddress() {
		return m_email_address;
	}

	/**
	 * Sets this <code>Person</code>'s first name field using the given information.
	 * @param first_name a <code>String</code> containing this <code>Person</code>'s new first name
	 */
	public void setFirstName(String first_name) {
		m_first_name = first_name;
	}
	
	/**
	 * Sets this <code>Person</code>'s last name field using the given information.
	 * @param last_name a <code>String</code> containing this <code>Person</code>'s new last name
	 */
	public void setLastName(String last_name) {
		m_last_name = last_name;
	}
	
	/**
	 * Sets this <code>Person</code>'s email address field using the given information.
	 * @param email_address a <code>String</code> containing this <code>Person</code>'s new email address
	 */
	public void setEmailAddress(String email_address) {
		m_email_address = email_address;
	}
	
	/**
	 * An abstract method to be overridden according to that subtype's specification.
	 * NOTE: Overriding methods should call back to this class's {@link #toString()} method for a generic representation.
	 * @return A textual description of this <code>Person</code>
	 */
	public abstract String getDisplayText();
	
	/**
	 * Provides a textual description for all <code>Person</code> objects.
	 * @return a <code>String</code> containing that representation
	 * @see #getDisplayText()
	 */
	@Override
	public String toString() {
		return "Name: " + m_first_name + " " + m_last_name + "\nEmail: " + m_email_address;
	}
}
