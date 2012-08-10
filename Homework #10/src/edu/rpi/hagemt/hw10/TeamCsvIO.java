/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #10 -- Project 17.1 Extended
 */
package edu.rpi.hagemt.hw10;

// I/O Imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Utility class to handle the parsing and CSV formatting of team data files.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public final class TeamCsvIO extends TeamIO {
	/**
	 * Reads in a team list from file line by line using comma-separated data entries.
	 * @param f the specified CSV file to read from
	 * @param team the list to which this team data will be added
	 * @throws IOException if there is a problem reading from the given file
	 */
	public static void getTeam(File f, ArrayList<Player> team) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		try {
			// Prepare the list by emptying it
			team.clear(); String line = null;
			while ((line = reader.readLine()) != null) {
				// Split the line on commas and do a sanity check before adding
				String[] split = line.split(",", 4);
				assert(split.length == 4);
				team.add(new Player(split[0], split[1], split[2], split[3]));
			}
		} finally {
			// Don't forget to release resources
			reader.close();
		}
	}
	
	/**
	 * Writes out team data in comma-separated lines to file.
	 * @param f the specified CSV file to write to
	 * @param team the list from which this team data will be read
	 * @throws IOException if there is a problem writing to the given file
	 */
	public static void saveTeam(File f, ArrayList<Player> team) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(f, false));
		try {
			// Print out each Player's data in a single line, with comma-separated fields
			for (Player p : team) {
				writer.println(p.firstName + "," + p.lastName + "," + p.playerNumber + "," + p.getBirthDate());
			}
		} finally {
			// Don't forget to release resources
			writer.flush();
			writer.close();
		}
	}
}
