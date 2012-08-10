/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #10 -- Project 17.1 Extended
 */
package edu.rpi.hagemt.hw10;

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
			// Restore the list, or do so as below, they're equivalent
			team = (ArrayList<Player>)(reader.readObject());
//			// Initialize the list to the proper state
//			team.clear();
//			int toRead = reader.readInt();
//			team.ensureCapacity(toRead);
//			// Try deserializing each indexed Player
//			for (int i = 0; i < toRead; ++i) {
//				Object o = reader.readObject();
//				if (o instanceof Player) { team.add(o); }
//			}
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
			// Save the list, or do so as below, they're equivalent
			writer.writeObject(team);
//			// First include how large the collection is
//			writer.writeInt(players.size());
//			// Serialize each Player
//			for (Player p : players) {
//				writer.writeObject(p);
//			}
		} finally {
			// Don't forget to release resources
			writer.flush();
			writer.close();
		}
	}
}
