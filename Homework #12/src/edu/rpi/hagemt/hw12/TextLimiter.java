package edu.rpi.hagemt.hw12;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Specialized Document format that restricts the length of its representation.
 * Used specifically to limit the characters stored in a text field,
 * so as to avoid overflow issues in database storage and retrieval.
 * @see TeamJdbcIO#createDatabase()
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class TextLimiter extends PlainDocument {
	private static final long serialVersionUID = 5120966789927286299L;
	private int max;
	
	/**
	 * Constructs a new limiter with the given length.
	 * @param limit the maximal length of representation
	 */
	public TextLimiter(int limit) {
		super();
		max = limit;
	}
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null) { return; }
		int capacity = Math.min(max - getLength(), str.length());
		if (capacity >= 0) {
			super.insertString(offs, str.substring(0, capacity), a);
		}
	}
}
