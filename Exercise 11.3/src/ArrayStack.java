import java.util.Iterator;


public class ArrayStack<T> implements GenericStack<T>, Iterable<T> {
	private T[] data;
	private int nextEmptySlot;

	public ArrayStack(int capacity) {
		data = (T[])(new Object[capacity]);
		nextEmptySlot = 0;
	}

	@Override
	public T peek() {
		return (nextEmptySlot > 0 && nextEmptySlot < data.length) ? data[nextEmptySlot - 1] : null;
	}

	@Override
	public T pop() {
		T t = (nextEmptySlot >= 0) ? data[--nextEmptySlot] : null;
		if (t != null) { data[nextEmptySlot] = null; } return t;
	}

	@Override
	public void push(T element) {
		if (nextEmptySlot < data.length) { data[nextEmptySlot++] = element; }
	}

	@Override public int size() { return nextEmptySlot; }

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int i = 0;
			@Override public boolean hasNext() { return (i < nextEmptySlot); }
			@Override public T next() { return (i < nextEmptySlot) ? data[i++] : null; }
			@Override public void remove() { throw new UnsupportedOperationException(); }
		};
	}
}
