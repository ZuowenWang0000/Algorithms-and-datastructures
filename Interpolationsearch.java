import java.util.Arrays;
import java.util.Random;

public class Interpolationsearch {

	public int Search(int[] a, int target) {
		int left = 0;
		int right = a.length - 1;
		// indices of left and right
		double ratio;
		int position;
		while (left <= right) {
			ratio = (target - a[left]) / (double) (a[right] - a[left]);
			position = left + (int) ratio * (right - left);

			if (target > a[position]) {
				left = position + 1;// go right part of the array
			} else if (target < a[position]) {
				right = position - 1;// go left part of the array
			} else {
				return position;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Random r = new Random();
		int[] a = new int[1000];

		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(1000);
		}
		Arrays.sort(a);
		Interpolationsearch bs = new Interpolationsearch();

		System.out.println(bs.Search(a, 500));
	}
}
