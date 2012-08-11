public class BlackBelt {
	public static void main(String args[]) {
		/* ONLY change data types and/or initial values */
		int i1 = 0, i2 = Integer.MAX_VALUE;
		Integer i = new Integer(1), j = new Integer(1), i6 = Integer.MIN_VALUE; String i3 = new String();
		double i4 = Double.POSITIVE_INFINITY, i7 = Double.NaN;

		// Do NOT modify any code below this. It must execute without "cheating".
		assert (i1 < i1 + 1) : "Yellow Belt";
		assert (i2 > i2 + 1) : "Orange Belt";
		assert (i3 != i3 + 0) : "Green Belt";
		assert (i4 == i4 + 1) : "Blue Belt";
		assert (i <= j && j <= i && i != j) : "Purple Belt";
		assert (i6 != 0 && i6 == -i6) : "Red Belt";
		assert (i7 != i7) : "Black Belt";

		System.out.println("You're a black belt");
	}
}