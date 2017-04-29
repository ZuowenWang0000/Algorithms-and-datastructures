import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;
/*
 * roulette
 * this one seems to be a probability problem, but actually it's a 
 * Dynamic Programming problem. Getting a specific formular seems impossible.
 * Use DP to make a table and check the number would be very fast.
 * 
 */
public class Roulette {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);	
		int t = s.nextInt();
			
		for(int g = 0; g<t; g++){
			int n = s.nextInt();  //number of balls in total
			int m = s.nextInt();  //number of boxes in total
			int k = s.nextInt();  //number of smallest consecutive white balls
			
			int i = 0; //to store number of white balls
			for(int j=0; j<n;j++){
				if(s.nextInt()==0){
					i++;
				}
			}

			DecimalFormat df = new DecimalFormat("0.0######");
			System.out.println(df.format(g(k,m,n,i)
					.divide(BigDecimal.valueOf(n).pow(m),7, RoundingMode.HALF_DOWN)));
		}
	}
	
	static BigDecimal g(int k, int m, int n, int i){//sequential
		BigDecimal t[][] = new BigDecimal[k+1][m+1];
		for(int q=0; q<m+1;q++){
			for(int p=0;p<k+1;p++){
				if(p==1){
					t[1][q] = BigDecimal.valueOf(n).pow(q).subtract(BigDecimal.valueOf(n-i).pow(q));
				}
				else if (p==q){
					t[p][q] = BigDecimal.valueOf(i).pow(q);
				}else if (q<p){
					t[p][q] = BigDecimal.valueOf(0);
				}else {
					t[p][q] =  BigDecimal.valueOf(0);
				}
				
			}
		}
			
		//  q----m,   p----k

		for(int q = k+1;q<m+1;q++){
			BigDecimal temp2,temp1;
			if(q-k-1<0){
				temp2 = BigDecimal.valueOf(0);
				temp1 = BigDecimal.valueOf(0);
			}else {
				temp2 = t[k][q-k-1];
				temp1 = BigDecimal.valueOf(n).pow(q-k-1);
			}
			
		
					t[k][q]= t[k][q-1].multiply(BigDecimal.valueOf(n))
							.add(BigDecimal.valueOf(n-i).multiply(BigDecimal.valueOf(i).pow(k)
							.multiply(temp1.subtract(temp2))));
				}
	
		return t[k][m];
	}
	static BigDecimal f(int k, int m, int n, int i){
		if(m<k){
			return BigDecimal.valueOf(0);
		}
		else if (k>=2 && (m>k)){
			return f(k, m - 1, n, i).multiply(BigDecimal.valueOf(n))
					.add(BigDecimal.valueOf(n-i).multiply(BigDecimal.valueOf(i).pow(k)
					.multiply(BigDecimal.valueOf(n).pow(m-k-1).subtract(f(k,m-k-1,n,i)))));

		}else if (k==1){
			return BigDecimal.valueOf(n).pow(m).subtract(BigDecimal.valueOf(n-i).pow(m));
		}else {
			return BigDecimal.valueOf(i).pow(m);
		}
	}
	
}
