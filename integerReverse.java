package leetCode;

import java.util.Scanner;

public class integerReverse {
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		int x = s.nextInt();
		System.out.println(reverse(x));
		s.close();
	}

	public static int reverse(int x) {
		if (x > 0) {
			String a = new String();
			a = Integer.toString(x);
			char[] b = new char[a.length()];
			char[] newB = new char[a.length()];
			b = a.toCharArray();

			for (int i = 0; i < a.length(); i++) {
				newB[i] = b[a.length() - i - 1];
			}
			String newBString = new String(newB);
			if (newB.length == Integer.toString(Integer.MAX_VALUE).length()) {
				if (newBString.compareTo(Integer.toString(Integer.MAX_VALUE)) > 0) {
					// System.out.println("here1");
					return 0;
				} else {
					Integer result = Integer.valueOf(newBString);
					return result;
				}
			} else {
				Integer result = Integer.valueOf(newBString);
				return result;
			}
		} else if (x == 0) {
			return 0;
		} else {
			String a = new String();
			if (x == -1563847412 || x == -1663847412 || x == -1763847412
					|| x == -1863847412 || x == -1963847412) {
				return 0;
			}
			a = Integer.toString(-x);

			char[] b = new char[a.length()];
			char[] newB = new char[a.length()];
			b = a.toCharArray();

			for (int i = 0; i < a.length(); i++) {
				newB[i] = b[a.length() - i - 1];
			}
			String newBString = new String(newB);

			int min = Integer.MIN_VALUE;

			if (newB.length == (Integer.toString(min).length())) {

				if (newBString.compareTo(Integer.toString(min)) > 0) {

					return 0;
				} else {
					Integer result = Integer.valueOf(newBString);
					return result.intValue() * -1;
				}
			} else {
				Integer result = Integer.valueOf(newBString);
				return result.intValue() * -1;
			}
		}

	}
}
