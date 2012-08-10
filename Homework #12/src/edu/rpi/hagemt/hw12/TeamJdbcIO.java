/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #12 -- Project 17.1 Extended Yet Again
 */
package edu.rpi.hagemt.hw12;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Utility class to handle communication with a database via JDBC.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public final class TeamJdbcIO extends TeamIO {
	private static Connection connection;
	private static Statement statement;
	private static Properties credentials = new Properties();
	static {
		// Initialize the username/password pair
		credentials.put("user", "java");
		credentials.put("password", "java");
	}
	
	// Prevent direct instantiation
	protected TeamJdbcIO() { }
	
	/**
	 * Activate this class's internal JDBC connection utilizing the specified URI.
	 * @param uri a textual description of the sought connection's uniform resource identifier
	 * @return <code>true</code> if a connection was opened successfully, <code>false</code> otherwise
	 */
	public static boolean openConnection(String uri) {
		try {
			// Initialize the connection and statement for use
			connection = DriverManager.getConnection(uri, credentials);
			statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Create the underlying table(s) required for I/O communication.
	 * @return <code>true</code> if the table(s) were created successfully, <code>false</code> otherwise
	 */
	public static boolean createDatabase() {
		try {
			// Attempt table creation with default name "players" and row identifier "id"
			// The field lengths correspond to at least the maximum-length data entry in each field
			statement.executeUpdate("CREATE TABLE players" +
					"(FirstName VARCHAR(64), LastName VARCHAR(64), id INTEGER NOT NULL UNIQUE, DateOfBirth VARCHAR(16))");
			// TODO Test-case, insert my name into every newly created database
			insertPlayer(new Player("Tor", "Hagemann", "0", "04/24/1991"));
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Prints out numbered row contents of the entire database to console.
	 * @return <code>true</code> if no exceptions were encountered during the procedure,<code>false</code> otherwise
	 */
	public static boolean printDatabase() {
		try {
			// Get all the rows
			ResultSet rs = statement.executeQuery("SELECT * FROM players");
			for (int i = 1; rs.next(); ++i) {
				// Print each row in the following format:
				// #: FirstName LastName (id) DOB: DateOfBirth
				StringBuilder sb = new StringBuilder(i + ": ");
				sb.append(rs.getString("FirstName").replace("\'\'", "\'") + " ");
				sb.append(rs.getString("LastName").replace("\'\'", "\'") + " ");
				sb.append("(" + rs.getInt("id") + ") DOB: " + rs.getString("DateOfBirth"));
				System.out.println(sb.toString());
			}
			// Cleanup
			rs.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Release the connection and shutdown the underlying JDBC bridge.
	 * @return <code>true</code> if this operation was successful, <code>false</code> otherwise
	 */
	public static boolean closeConnection() {
		if (connection != null) {
			try {
				// Cleanup and shutdown
				statement.close();
				DriverManager.getConnection("jdbc:derby:JDBC;shutdown=true", credentials);
			} catch (SQLException e) {
				// Successful shutdown throws an exception (Error code: 45000)
				return e.getErrorCode() == 45000;
			}
		}
		return true;
	}
	
	/**
	 * Checks to see whether or not a JDBC connection has been established.
	 * @return <code>true</code> if it has, <code>false</code> otherwise
	 */
	public static boolean isConnected() {
		return connection != null;
	}
	
	/**
	 * Fetches all records currently stored in the database and adds player data to the team list.
	 * @param f a file containing the abstract path name to the database
	 * @param team the list to which this team data will be added
	 * @throws SQLException if JDBC communication did not complete properly
	 */
	public static void getTeam(File f, ArrayList<Player> team) throws SQLException {
		// Either clear or initialize the team, depending on its state
		if (team == null) { team = new ArrayList<Player>(); } else { team.clear(); }
		// Fetch every record in the database
		ResultSet result = statement.executeQuery("SELECT * FROM players");
		// Iterate over the results and unescape name strings
		while (result.next()) {
			String given = result.getString("FirstName").replace("\'\'", "\'");
			String family = result.getString("LastName").replace("\'\'", "\'");
			String number = Integer.toString(result.getInt("id"));
			String dob = result.getString("DateOfBirth");
			team.add(new Player(given, family, number, dob));
		}
		result.close();
	}
	
	/**
	 * Removes all records currently stored in the database and replaces them using team data.
	 * @param f a file containing the abstract path name to the database
	 * @param team the list from which this team data will be read
	 * @throws SQLException if JDBC communication did not complete properly
	 */
	public static void saveTeam(File f, ArrayList<Player> team) throws SQLException {
		// Clear the database initially
		statement.executeUpdate("DELETE FROM players");
		// Insert everything we possibly can
		if (team != null) { for (Player p : team) { insertPlayer(p); } }
	}
	
	/**
	 * Remove all records of a given player from the database.
	 * @param p the given player to operate on
	 * @return <code>true</code> if the operation was successful, otherwise <code>false</code>
	 */
	public static boolean deletePlayer(Player p) {
		// Undefined players cannot be deleted
		if (p == null) { return false; }
		try {
			// Delete the specific player according to his/her key (numbers are primary keys)
			statement.executeUpdate("DELETE FROM players WHERE id=" + p.playerNumber + "");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Insert a record into the database using data from a given player.
	 * @param p the given player to operate on
	 * @return <code>true</code> if the operation was successful, otherwise <code>false</code>
	 */
	public static boolean insertPlayer(Player p) {
		// Undefined players cannot be inserted
		if (p == null) { return false; }
		try {
			// Construct the statement, escaping name strings
			StringBuilder sb = new StringBuilder("INSERT INTO players VALUES (");
			sb.append("'" + p.firstName.replace("\'", "'\'\'") + "',");
			sb.append("'" + p.lastName.replace("\'", "\'\'") + "',");
			sb.append(Integer.toString(p.playerNumber) + ",");
			sb.append("'" + p.getBirthDate() + "')");
			statement.executeUpdate(sb.toString());
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Update a given player's record using data from another given player.
	 * Only the first encountered record is replaced, if it exists.
	 * @param a the old player whose record is to be replaced
	 * @param b the new player whose record is to replace <code>a</code>'s
	 * @return <code>true</code> if the operation was successful, otherwise <code>false</code>
	 */
	public static boolean updatePlayer(Player a, Player b) {
		// Undefined players cannot be updated
		if (a == null || b == null) { return false; }
		boolean found = false;
		try {
			// Fetch the set of players matching the old player's id record (should be singleton)
			ResultSet rs = statement.executeQuery("SELECT * FROM players WHERE id=" + a.playerNumber);
			if (found = rs.next()) {
				// Perform a field update using data from the new player
				rs.updateString("FirstName", b.firstName.replace("\'", "\'\'"));
				rs.updateString("LastName", b.lastName.replace("\'", "\'\'"));
				rs.updateInt("id", b.playerNumber);
				rs.updateString("DateOfBirth", b.getBirthDate());
				rs.updateRow();
			}
			rs.close();
		} catch (SQLException e) {
			return false;
		}
		return found;
	}
}
