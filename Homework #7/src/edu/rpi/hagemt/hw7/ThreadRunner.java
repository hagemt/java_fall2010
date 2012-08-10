/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #7 -- Project 14.1 -- Threads
 */
package edu.rpi.hagemt.hw7;

/**
 * Encapsulates the concept of an independent race participant.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class ThreadRunner extends Thread {
	// Constants
	private static final int SLEEP_TIME = 100, GOAL = 1000;

	// Fields
	private String id;
	private int lazyness, quickness;
	private ThreadRunner counterpart = null;

	/**
	 * Creates a new race participant as a Thread with the given properties.
	 * @param name a String containing the runner's name
	 * @param rest_percentage the chance the runner will rest at the start of every round
	 * @param speed a distance the runner will be able to move each round
	 */
	public ThreadRunner(String name, int rest_percentage, int speed) {
		// Only accept valid percentages
		if (name == null || rest_percentage < 0 || rest_percentage > 100) {
			throw new IllegalArgumentException("Invalid Runner: " + name);
		}
		id = name;
		lazyness = rest_percentage;
		quickness = speed;
	}
	
	/**
	 * Set this ThreadRunner's racing opponent.
	 * @param opponent this runner's counterpart
	 */
	public void setOpponent(ThreadRunner opponent) {
		counterpart = opponent;
	}

	/**
	 * Starts this runner at the beginning of the track.
	 */
	public void run() {
		int progress = 0;
		// Provided we the other runner hasn't finished
		while (!this.isInterrupted()) {
			// Check to see if we've reached the finish line
			if (progress < GOAL) {
				try {
					// Sleep for 100 milliseconds
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException ie) {
					// But make sure we still stop on interrupts
					break;
				}
				// Update our progress if we're not feeling lazy
				if (lazyness < Math.random() * 100) {
					progress += quickness;
					System.out.println(id + " : " + progress);
				}
			} else {
				// We're done
				this.finished();
				return;
			}
		}
		// Here we can assume the thread was interrupted
		System.out.println(id + ": You beat me fair and square.");
	}

	/**
	 * Throws up a notification that this runner has completed the race.
	 */
	public void finished() {
		System.out.println(id + ": I finished!");
		// Interrupt the opponent, if there is one
		if (counterpart != null) {
			counterpart.interrupt();
		}
	}

	/**
	 * Usage: java edu.rpi.hagemt.hw7.ThreadRunner
	 * @param args program arguments
	 */
	public static void main(String... args) {
		// Make the hare rest ten times as often as the hare, but also ten times as fast
		ThreadRunner tortoise = new ThreadRunner("Tortoise", 0, 10);
		ThreadRunner hare = new ThreadRunner("Hare", 90, 100);
		tortoise.setOpponent(hare);
		hare.setOpponent(tortoise);

		// Start the race
		System.out.println("Get set...Go!");		
		tortoise.start(); hare.start();

		// Loop until one of the threads dies, interrupting the other
		while (true) {
			// Test to see if the tortoise finished and notify the hare
			if (!tortoise.isAlive()) {
				System.out.println("The race is over! The Tortoise is the winner."); break;
			}
			// Test to see if the hare finished, and notify the tortoise
			if (!hare.isAlive()) {
				System.out.println("The race is over! The Hare is the winner."); break;
			}
		}
	}
}