/*
 * 这道题很重要的一点，首先要画出概率图。 如果我们假设初始状态(i,j,k)是1，那么接下来可能有三个状态
 * (i-1,j,k)  (i,j-1,k)   (i,j,k-1)
 * 这三个状态对应的概率可以用排列组合算出来，分母是所有的可能取法，分子是取到某两个特定tribe的取法
 * 这张概率图的边界是，（XX,0,0） (0,YY,0)(0，0,ZZ)所有的排列
 * 
 * 接下来的问题就是用递归还是iteration。
 * 递归似乎有个无解的问题，就是比如我们算3，3，3的决胜概率，2，3，3和3，3，2都会产生比如1，1，1的状态。
 * 这样就避免不了得用一张表把它们分别产生的1，1，1的概率加和起来，然而递归的运行是，先把一边算到底，
 * 比如先一直算算到1，0，0 再回过去算上面的部分，再回到1，0，0，这样的话会产生很多重复（中间的部分），
 * 
 * 用Iteration可以避免重复加和再算边界的问题，因为Iteration的过程和手绘概率图的过程一样，
 * 用三个for（for（for））循环，表中的每个格子正好访问一遍（状态可能访问了多遍，比如222和123都可以到122，
 * 所以1 2 2 会被用不同的姿势访问很多遍
 * 
 * 
 */


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class IslandTribes {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		for (int l = 0; l < T; l++) {
			int bears = s.nextInt();
			int hunters = s.nextInt();
			int ninjas = s.nextInt();

			double[][][] t = new double[bears + 1][hunters + 1][ninjas + 1];
			t[bears][hunters][ninjas] = 1.0;

			for (int i = bears; i >= 0; i--) {
				for (int j = hunters; j >= 0; j--) {
					for (int k = ninjas; k >= 0; k--) {

				if(i>0&&(i*j+i*k+j*k)!=0) {
				t[i-1][j][k]=t[i][j][k]*((double)i*j/(double)(i*j+i*k+j*k))+t[i-1][j][k];
						}
				if(j>0&&(i*j+i*k+j*k)!=0) {
				t[i][j-1][k]=t[i][j][k]*((double)k*j/(double)(i*j+i*k+j*k))+t[i][j-1][k];
						}
				if(k>0&&(i*j+i*k+j*k)!=0) {
				t[i][j][k-1]=t[i][j][k]*((double)i*k/(double)(i*j+i*k+j*k))+t[i][j][k-1];
						}

					}
				}
			}

			double bearsWin = 0.0;
			double huntersWin = 0.0;
			double ninjaWin = 0.0;
			for (int i = 1; i < bears + 1; i++) {
				bearsWin = bearsWin + t[i][0][0];
			}
			for (int i = 1; i < hunters + 1; i++) {
				huntersWin = huntersWin + t[0][i][0];
			}
			for (int i = 1; i < ninjas + 1; i++) {
				ninjaWin = ninjaWin + t[0][0][i];
			}

			DecimalFormat df = new DecimalFormat("0.0######");
			df.setRoundingMode(RoundingMode.HALF_DOWN);
			System.out.println(df.format(bearsWin) + " "
					+ df.format(huntersWin) + " " + df.format(ninjaWin)); 

		}
		s.close();
	}
	
	
	/*
	 * this was the recursive version , but it failed  >.<
	 */
	
//	static void f(double[][][] t,int n, int q, int r){
//		if(n>0){
//		t[n-1][q][r] = t[n][q][r]*(double)n*q/(double)(n*q+n*r+q*r)+t[n-1][q][r]; }
//		if(q>0){
//		t[n][q-1][r] = t[n][q][r]*(double)r*q/(double)(n*q+n*r+q*r)+t[n][q-1][r]; }
//		if(r>0){
//		t[n][q][r-1] = t[n][q][r]*(double)n*r/(double)(n*q+n*r+q*r)+t[n][q][r-1]; }
//
//		
//		if(n!=0){
//		f(t,n-1,q,r);
//		}
//		if(q!=0){
//		f(t,n,q-1,r);
//		}
//		if(r!=0){
//		f(t,n,q,r-1);}
//	} 
	
	
}
