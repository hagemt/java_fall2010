/**
 * Homework #6 -- Define your own Collection class
 */
package rpi.edu.hagemt.hw6;

import java.util.Collection;
import java.util.Iterator;

/**
 * Encapsulates a random-access data structure for storing generic information.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @version 1.0
 */
public class Storage<T extends Object> implements Collection<T> {
	public static int MIN_SIZE = 1, DEFAULT_SIZE = 11;
	private T[] data;
	private int nextEmptySlot;
	
	public Storage() { this(DEFAULT_SIZE); }
	
	public Storage(int capacity) {
		// Impose minimum capacity
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"Storage size: " + capacity + " is < " + MIN_SIZE + ".");
		}
		// The case is necessary because generics in Java suffer from type erasure
		data = (T[])(new Object[capacity]);
		nextEmptySlot = 0;
	}
	
	public Storage(Collection<? extends T> c) {
		// Initializing with a null collection prompts zero-size error
		this(c == null ? 0 : c.size());
		for (T t : c) { add(t); }
	}
	
	@Override
	public boolean add(T t) {
		// Only allow addition or an error
		if (nextEmptySlot < data.length) {
			// Disallow all undefined values
			if (t == null) { return false; }
			data[nextEmptySlot++] = t;
			return true;
		} else {
			throw new UnsupportedOperationException(
					"Adding element to Storage would could capacity overflow.");
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// The addition of empty sets does not prompt modification
		if (c == null) { return false; }
		// Otherwise, assume modification does not occur
		boolean modified = false;
		for (T t : c) {
			// Unless addition is successful
			if (add(t)) { modified = true; }
		}
		return modified;
	}

	@Override
	public void clear() {
		// Allow for the garbage collector to see the elements may be freed and reset
		for (T t : data) { t = null; }
		nextEmptySlot = 0;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < nextEmptySlot; ++i) {
			// If o is undefined, comparison returns false
			if (data[i].equals(o)) { return true; }
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// The empty set is a subset of all collections
		if (c == null) { return true; }
		// Otherwise, only if every object in c is here
		for (Object o : c) {
			if (!contains(o)) { return false; }
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		// By definition, if we have yet to fill the first slot, the structure is empty
		return (nextEmptySlot == 0);
	}

	@Override
	public Iterator<T> iterator() {
		// Define inline implementation of Iterator<T>
		return new Iterator<T>() {
			private int i = 0;
			@Override
			public boolean hasNext() {
				return (i < nextEmptySlot);
			}
			@Override
			public T next() {
				// Provide null in the event that data is not available
				return (i < nextEmptySlot) ? data[i++] : null;
			}
			@Override
			public void remove() {
				// Call remove so as to not invalidate the Iterator<T>
				if (i < nextEmptySlot) { Storage.this.remove(data[i], i); }
			}
		};
	}

	@Override
	public boolean remove(Object o) {
		// Call the private helper function for removal from the beginning
		return remove(o, 0);
	}
	
	private boolean remove(Object o, int starting_index) {
		for (int i = starting_index; i < nextEmptySlot; ++i) {
			// If o is undefined, comparison returns false
			if (data[i].equals(o)) {
				// In the event a match was found
				for (int j = i + 1; j < nextEmptySlot; ++j) {
					// Replace each entry with its next
					data[j - 1] = data[j];
				}
				// Replace the old final slot with null
				data[--nextEmptySlot] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// The addition of empty sets does not prompt modification
		if (c == null) { return false; }
		// Otherwise, assume modification does not occur
		boolean modified = false;
		for (Object o : c) {
			// Unless a removal is successful
			if (remove(o)) { modified = true; }
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// Treat the case when the collection is null specially
		boolean modified = (c == null) ? !isEmpty() : false;
		// Retaining the empty set implies clearing all values
		if (c == null) { clear(); }
		for (int i = 0; i < nextEmptySlot; ++i) {
			// Otherwise, look to see if the collection contains each element
			// And mark as modified if even one was was successfully removed
			if (!c.contains(data[i])) {
				modified |= remove(data[i]);
			}
		}
		// The set is only modified if its state changed in this method
		return modified;
	}

	@Override
	public int size() {
		// By definition, the next available slot is one past the final index
		return nextEmptySlot;
	}

	@Override
	public Object[] toArray() {
		// Simply call the version with generic-type arguments and the proper size
		return toArray(new Object[nextEmptySlot]);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		// If the array passed was too short, reallocate
		// Again, this is messy since we can't actually make a new T[]
		if (array.length < nextEmptySlot) { array = (T[])(new Object[nextEmptySlot]); }
		// Otherwise, try to fill with stored values until it is no longer possible to do so
		for (int i = 0; i < array.length; ++i) {
			array[i] = (i < nextEmptySlot) ? (T)(data[i]) : null;
		}
		return array;
	}

	/**
	 * Test the methods of the Storage class and prints validation.
	 * Recommended arguments for a strong test case: This is a test.
	 * @param args a series of Strings with which to test the Storage structure.
	 */
	public static void main(String... args) {
		if (args.length < 2) {
			System.out.println("Test case requires at least two argument strings.");
			System.exit(1);
		}
		System.out.println("Initializing Storage.");
		Storage<String> store = new Storage<String>(args.length);
		System.out.println("Adding elements.");
		for (String s : args) { store.add(s); }
		System.out.println("Storage empty? " + store.isEmpty());
		System.out.println("Size: " + store.size());
		System.out.println("Printing elements using iterator:");
		for (String s : store) { System.out.println(s); }
		System.out.println("With 'a' removed:"); store.remove("a");
		for (String s : store) { System.out.println(s); }
		System.out.println("Size: " + store.size());
		System.out.println("Contains 'a' now? " + store.contains("a"));
		for (String s : store) { System.out.println(s); }
		System.out.println("Clear contents."); store.clear();
		System.out.println("Contents:");
		for (String s : store) { System.out.println(s); }
		System.out.println("Size: " + store.size());
		System.out.println("Storage empty? " + store.isEmpty());
		System.out.println("Initializing Collection test case.");
		java.util.LinkedList<String> c = new java.util.LinkedList<String>();
		for (String s : args) { c.add(s); }
		System.out.println("Reinitializing Storage using Collection.");
		store = new Storage<String>(c);
		System.out.println("Attempting to overflow Storage:");
		try {
			store.addAll(c);
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Success. Message was: " + uoe.getMessage());
		}
		System.out.println("Storage contains Collection? " + store.containsAll(c));
		System.out.println("Removing two elements from Collection.");
		String front = c.pollFirst(), back = c.pollLast();
		System.out.println("Collection:");
		for (String s : c) { System.out.println(s); }
		System.out.println("Retaining in Storage only elements in Collection.");
		store.retainAll(c);
		System.out.println("Storage:");
		for (String s : store) { System.out.println(s); }
		System.out.println("Adding back in one of the removed elements.");
		store.add(front);
		System.out.println("Storage contents:");
		for (String s : store) { System.out.println(s); }
		System.out.println("Removing elements of Collection from Storage:");
		store.removeAll(c);
		for (String s : store) { System.out.println(s); }
		System.out.println("Adding back in other previously removed element.");
		store.add(back);
		System.out.println("Converting Storage to an array");
		Object[] array = store.toArray();
		System.out.println("Storage contents:");
		for (String s : store) { System.out.println(s); }
		System.out.println("Array contents:");
		for (Object o : array) { System.out.println(o); }
		System.out.println("Testing removal of elements using iterator.");
		for (Iterator<String> it = store.iterator(); it.hasNext(); it.remove());
		System.out.println("Storage empty? " + store.isEmpty());
		System.out.println("Tests complete.");
	}
}
