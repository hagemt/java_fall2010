/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #10 -- Project 17.1 Extended
 */
package edu.rpi.hagemt.hw10;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.text.JTextComponent;

/**
 * Specialized listener to automatically select all the text in a textual input element.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class AutoSelect implements FocusListener {
	@Override public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if (source instanceof JTextComponent) {
			// If we can, select all the text
			((JTextComponent)(source)).selectAll();
		}
	}
	@Override public void focusLost(FocusEvent e) { }
}
