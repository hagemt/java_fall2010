package edu.rpi.hagemt.hw10;

/**
 * Enumerates a list of file extensions paired with their handler classes.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public enum Mode {
	CSV(TeamCsvIO.class), DAT(TeamObjectIO.class);
	private Class<? extends TeamIO> handler;

	/**
	 * Demands each mode specifies a "handler" that subclasses TeamIO.
	 * @param tio a type descriptor of a class that extends TeamIO, from which getTeam and saveTeam can be invoked
	 * @see TeamIO#getTeam()
	 * @see TeamIO#saveTeam(java.util.ArrayList)
	 */
	private Mode(Class<? extends TeamIO> c) {
		handler = c;
	}

	/**
	 * Get this Mode's class for handling getTeam and saveTeam operations
	 * @return the handler class paired with this file extension
	 */
	public Class<? extends TeamIO> getHandler() {
		return handler;
	}
}
