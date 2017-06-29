import java.util.Arrays;
import java.util.Random;


public class BinarySearch {
	public int Search(int[] a, int target){
		int left=0;
		int right = a.length -1;
		//indices of left and right
		
		while (left <= right){
			int middle = (left + right)/2;
			if(a[middle] == target){
				return middle;
			}else if (a[middle] > target){
				right = middle - 1;
			}else {
				left = middle + 1 ;
			}
		}
		return -1;
	}
	
	public static void main(String[] args){
		Random r = new Random();
		int[] a = new int[1000];

		for(int i =0;i<a.length;i++){
			a[i] = r.nextInt(1000);
		}
		Arrays.sort(a);
		BinarySearch bs = new BinarySearch();
		
		System.out.println(bs.Search(a,500));
	
	}

}
