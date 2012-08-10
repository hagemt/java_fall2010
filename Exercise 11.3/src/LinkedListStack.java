import java.util.LinkedList;

/**
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class LinkedListStack<T> implements GenericStack<T> {
	private LinkedList<T> elements;
	public LinkedListStack() { elements = new LinkedList<T>(); }
	@Override public T peek() { return elements.peek(); }
	@Override public T pop() { return elements.pop(); }
	@Override public void push(T element) { elements.push(element); }
	@Override public int size() { return elements.size(); }
}
