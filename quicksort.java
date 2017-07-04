import java.util.Scanner;

public class quicksort {

	public static void swap(int[] a, int indexA, int indexB) {
		int temp = a[indexA];
		a[indexA] = a[indexB];
		a[indexB] = temp;
	}

	public static int partition(int[] a, int left, int right) {
		int i = left;
		int j = right - 1;
		int p = a[right]; // pivot

		while (i < j) {
			while (i < right && a[i] < p) {
				i++;
			}

			while (j > left && a[j] > p) {
				j--;
			}

			if (i < j) {
				swap(a, i, j);
			}
		}
		swap(a, i, right);
		return i;
	}

	public static void sort(int[] a, int left, int right) {
		if (left < right) {
			int k = partition(a, left, right);
			sort(a, left, k - 1);
			sort(a, k + 1, right);
		}
	}
	public static void main(String[] args){
		System.out.println("number of cases : ");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		for(int i =0; i<n;i++){
			int length = s.nextInt();
			int a[] = new int[length];
			for(int j = 0; j<length; j++){
				a[j] = s.nextInt();
			}
			
			sort(a,0,length-1);
			
			for(int k =0;k<length;k++){
				
				System.out.print(a[k]+" ");
				
			}
			System.out.println();
		}
	s.close();}
}
