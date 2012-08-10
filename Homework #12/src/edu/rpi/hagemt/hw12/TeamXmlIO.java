/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #12 -- Project 17.1 Extended Yet Again
 */
package edu.rpi.hagemt.hw12;

// IO Imports
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// XML Stream Imports
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Utility class to handle the parsing and XML formatting of team data files.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public final class TeamXmlIO extends TeamIO {
	// XML I/O Factories
	private static final XMLInputFactory inputFactory = javax.xml.stream.XMLInputFactory.newInstance();
	private static final XMLOutputFactory outputFactory = javax.xml.stream.XMLOutputFactory.newInstance();
	
	// Prevent direct instantiation
	protected TeamXmlIO() { }

	/**
	 * Reads in team data from an XML file.
	 * @param f the file from which data will be read
	 * @param team the list into which data will be read
	 * @throws IOException if there is a problem reading from the given file
	 * @throws XMLStreamException if there is a problem with the underlying encoding of the given file
	 */
	public static void getTeam(File f, ArrayList<Player> team) throws IOException, XMLStreamException {
		// Initialize a specialized reader to wrap the standard one for file I/O
		XMLStreamReader reader = inputFactory.createXMLStreamReader(new FileReader(f));
		try {
			String given = null, family = null, number = null, dob = null;
			// Attempt to read in as much as we can
			while (reader.hasNext()) {
				try {
					switch (reader.getEventType()) {
						// At opening tags, varying behavior must occur
						case XMLStreamConstants.START_ELEMENT:
							String tag = reader.getLocalName();
							// If we hit a Player tag, parse for the attribute
							if (tag.equals("Player")) {
								given = family = number = dob = null;
								number = reader.getAttributeValue(0);
							// Otherwise handle the element properly
							} else if (tag.equals("FirstName")) {
								given = reader.getElementText();
							} else if (tag.equals("LastName")) {
								family = reader.getElementText();
							} else if (tag.equals("DateOfBirth")) {
								dob = reader.getElementText();
							// But encounter this first, at the opening of Players
							} else {
								team.clear();
							}
							break;
						// At the close tag of each Player, the fields should be filled
						case XMLStreamConstants.END_ELEMENT:
							if (reader.getLocalName().equals("Player")) {
								team.add(new Player(given, family, number, dob));
							}
							break;
						default: break;
					}
				} catch (Exception e) {
					// Technical problem, hide from user and continue on if possible
					System.err.println("Problem parsing location: " + reader.getLocation());
				} finally {
					// Always move along through the file regardless
					reader.next();
				}
			}
		} finally {
			// Always remember to release resources
			reader.close();
		}
	}

	/**
	 * Persists team data in the given file in XML format.
	 * @param f the file in which the serialized Player data will be stored
	 * @param team the list in which the team data to be serialized resides
	 * @throws XMLStreamException if there is a problem with the underlying encoding of the given file
	 * @throws IOException if there is a problem writing to the given file
	 */
	public static void saveTeam(File f, ArrayList<Player> team) throws IOException, XMLStreamException {
		// Initialize a specialized writer to wrap the standard one for file I/O
		XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(f));
		try {
			// Write a default header and start tag
			writer.writeStartDocument();
			writer.writeStartElement("Players");
			// Write each Player object's specific data in XML format
			for (Player p : team) {
				try {
					// Open Player tag with id attribute equal to player number
					writer.writeStartElement("Player");
					writer.writeAttribute("id", Integer.toString(p.playerNumber));
					// First name
					writer.writeStartElement("FirstName");
					writer.writeCharacters(p.firstName);
					writer.writeEndElement();
					// Last name
					writer.writeStartElement("LastName");
					writer.writeCharacters(p.lastName);
					writer.writeEndElement();
					// Date of birth
					writer.writeStartElement("DateOfBirth");
					writer.writeCharacters(p.getBirthDate());
					writer.writeEndElement();
					// Close Player tag
					writer.writeEndElement();
				} catch (Exception e) {
					// Insert comment into file describing error
					writer.writeComment("Error printing " + p + " DOB: " + p.getBirthDate());
				}
			}
			// Write the Players close tag and mark footer
			writer.writeEndElement();
			writer.writeEndDocument();
		} finally {
			// Always remember to flush and release resources
			writer.flush();
			writer.close();
		}
	}
}
