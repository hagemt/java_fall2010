/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #11 -- Project 17.1 Extended Again
 */
package edu.rpi.hagemt.hw11;

/**
 * Describes the edit-state of a TeamRosterWindow.
 * At any time we are doing one of the following to the team data.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public enum State {
	ADDING, BROWSING, EDITING;
}
