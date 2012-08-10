/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #11 -- Project 17.1 Extended Again
 */
package edu.rpi.hagemt.hw11;

// I/O Imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Utility class to handle the serialization of Player objects from a DAT (team data) file.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public final class TeamObjectIO extends TeamIO {
	/**
	 * Reads in team data from a DAT file.
	 * @param f the file from which data will be read
	 * @param team the list into which data will be read
	 * @throws IOException if there is a problem reading from the given file
	 * @throws ClassNotFoundException if the Player object cannot be deserialized
	 */
	public static void getTeam(File f, ArrayList<Player> team) throws IOException, ClassNotFoundException {
		// Initialize a special reader that wraps the usual file stream reader
		ObjectInputStream reader = new ObjectInputStream(new FileInputStream(f));
		try {
//			// Restore the list, or do so as below, they're equivalent
//			team = (ArrayList<Player>)(reader.readObject());
			/*
			 * I decided for a number of reasons, the deserialization method performed below is superior to that
			 * commented-out above, despite the recommendation we received in lecture to use the simpler method of
			 * deserializing the entire list at once.
			 * 1) It provides the implementation details about how many Players exist, and are (have been) persisted
			 * 2) It is not reliant on (read: is resistant to differences in) the ArrayList's (de)serialization
			 * 3) It avoids an unsafe cast to a type that employs (demands) a generic type parameter
			 * Nonetheless, as the former was the "preferred" method, I have included it.
			 */
			// Initialize the list to the proper state
			team.clear();
			int toRead = reader.readInt();
			team.ensureCapacity(toRead);
			// Try deserializing each indexed Player
			for (int i = 0; i < toRead; ++i) {
				team.add((Player)reader.readObject());
			}
		} finally {
			// Don't forget to release resources
			reader.close();
		}
	}
	
	/**
	 * Persists team data in the given file in object format.
	 * @param f the file in which the serialized Player data will be stored
	 * @param players the list in which the team data to be serialized resides
	 * @throws IOException if there is a problem writing to the given file
	 */
	public static void saveTeam(File f, ArrayList<Player> team) throws IOException {
		// Initialize a special writer that wraps the usual file stream writer
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(f));
		try {
//			// Save the list, or do so as below, they're equivalent
//			writer.writeObject(team);
			/*
			 * See the note above for justification of this method.
			 */
			// First include how large the collection is
			writer.writeInt(team.size());
			// Serialize each Player
			for (Player p : team) {
				writer.writeObject(p);
			}
		} finally {
			// Don't forget to release resources
			writer.flush();
			writer.close();
		}
	}
}
