package edu.rpi.hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Homework #1 -- Rectangle Area and Perimeter Calculation
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Rectangle {
	private double length, width;

	/**
	 * Constructs a Rectangle of given length and width.
	 * @param x value to set as the Rectangle's length
	 * @param y value to set as the Rectangle's width
	 */
	public Rectangle(double x, double y) {
		length = x;
		width = y;
	}
	
	/**
	 * Calculates the length times width.
	 * @return the area of the Rectangle
	 */
	public double area() {
		return length * width;
	}
	
	/**
	 * Calculates length plus width times two.
	 * @return the perimeter of the Rectangle
	 */
	public double perimeter() {
		return 2 * (length + width);
	}

	/**
	 * USAGE: java edu.rpi.hw1.Rectangle
	 * @param args (program takes no arguments)
	 */
	public static void main(String... args) {
		try {
			System.out.println("Welcome to the Area and Perimeter Calculator");
			// Setup input reader resource, encapsulating System.in
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				// Print the menu
				do {
					try {
						// Fetch the length and width
						System.out.println();
						System.out.print("Enter length: ");
						double x = Double.valueOf(reader.readLine());
						System.out.print("Enter width:  ");
						double y = Double.valueOf(reader.readLine());
						// Setup a Rectangle object and print attributes
						Rectangle r = new Rectangle(x, y);
						System.out.println("Area:         " + r.area());
						System.out.println("Perimeter:    " + r.perimeter());
						System.out.println();
						// Prompt the user to continue
						System.out.print("Continue? (y/n): ");
					} catch (NumberFormatException nfe) {
						// In the case that the user didn't provide numeric input
						System.err.println("Invalid Input! Try again? (y/n) ");
					}
				} while (reader.readLine().toLowerCase().equals("y"));
				System.out.println();
			} catch (IOException ioe) {
				// In the case of I/O error
				ioe.printStackTrace();
			} finally {
				// Make sure to release input reader resource
				reader.close();
			}
		} catch (Exception e) {
			// In the case of catastrophic error
			System.exit(1);
		}
	}
}
