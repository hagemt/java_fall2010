/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #9 -- Project 17.1 -- Maintain a team roster
 */
package edu.rpi.hagemt.hw9;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Specialized listener to filter out all non-digit keystrokes.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class IntFilter implements KeyListener {
	@Override public void keyPressed(KeyEvent e) { }
	@Override public void keyReleased(KeyEvent e) { }
	@Override public void keyTyped(KeyEvent e) {
		// Consume all non-digit keystrokes
		if (!Character.isDigit(e.getKeyChar())) { e.consume(); }
	}
}
