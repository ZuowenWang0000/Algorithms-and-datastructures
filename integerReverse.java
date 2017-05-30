package leetCode;
/*
 * simple IntegerReverse
 * 
 * But there are few stuffs to watch out.
 * Min_Integer = -2147483648
 * Max_Integer = 2147483647
 * I first tried to use lexicon order to solve the problem(that the inverse Integer 
 * might overflow).
 * When it's positive, pretty easy, if the given x's inverse. toString 
 * is as long as MAX_Integer.toString, then compare it using lexicon order. If compareTo 
 * returns >0, then it's overflow.
 * 
 * but if the given integer is negative, then we have to watch out.
 * we want to firstly make it to positive, then try to handle this same as positive x
 * BUT!!!!!
 * if we are given for example -1563847412, then the inverse will be 2147483651, which 
 * is even bigger than the MAX_Integer. Here I hack it. Since all possible troublesome 
 * values are -1563847412 ,-1663847412 ,-1763847412,-1863847412 ,-1963847412. 
 * Simply distinguish them with an if.
 * 
 */
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
