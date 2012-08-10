
public interface GenericStack<T> {
	void push(T element); // Adds an element to the top of the stack.
	T pop(); // Returns and removes the element at the top of the stack.
	T peek(); // Returns but does not remove the element at the top of the stack.
	int size(); // Returns the number of entries in the stack.
}
