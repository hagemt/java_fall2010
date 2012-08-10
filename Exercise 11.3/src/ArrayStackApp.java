public class ArrayStackApp {
	public static void main(String... args) {
		ArrayStack<String> stack = new ArrayStack<String>(10);
		for (int i : new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }) {
			stack.push(Integer.toBinaryString(i));
		}
		for (String s : stack) {
			String string = s;
			while (string.length() < 4) { string = "0" + string; }
			System.out.println(string);
		}
		while (stack.size() > 0) { stack.pop(); }
		for (String s : args) {
			System.out.println("Push: " + s);
			stack.push(s);
		}
		System.out.println("The stack contains " + stack.size() + " items");
		System.out.println();
		System.out.println("Peek: " + stack.peek());
		System.out.println("The stack contains " + stack.size() + " items");
		System.out.println();
		while (stack.size() > 0) { System.out.println("Pop: " + stack.pop()); }
		System.out.println("The stack contains " + stack.size() + " items");
	}
}
