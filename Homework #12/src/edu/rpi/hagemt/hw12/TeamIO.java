/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #12 -- Project 17.1 Extended Yet Again
 */
package edu.rpi.hagemt.hw12;

import java.util.ArrayList;

/**
 * Utility class to simulate the passing of input in the form of Player lists.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public abstract class TeamIO {
	// Data Storage
	private static ArrayList<Player> player_list = new ArrayList<Player>();

	static {
		// Large test case -- populate the team with one person born on each day with successive numbering
		final int[] days_in_month = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		for (int i = 1, c = 1; i <= days_in_month.length; ++i) {
			for (int j = 1; j <= days_in_month[i - 1]; ++j, ++c) {
				player_list.add(new Player("Test" + i, "Player" + j, Integer.toString(c), i + "/" + j + "/1991"));
			}
		}
	}
	
	// Prevent direct instantiation
	protected TeamIO() { }
	
	/**
	 * Gets a team list.
	 * @return a List of Players
	 */
	public static ArrayList<Player> getTeam() {
		return player_list;
	}
	
	/**
	 * Prints out a team list to standard output.
	 * @param players the List of Players to print
	 */
	public static void saveTeam(ArrayList<Player> players) {
		System.out.println("Players List:");
		for (Player p : players) {
			System.out.println(p + " DOB: " + p.getBirthDate());
		}
		System.out.println("Total: " + players.size());
	}
}
