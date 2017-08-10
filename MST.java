import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class MST {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		for(int i=0;i<T;i++){
			int n = s.nextInt();
			int m = s.nextInt();
			int Gewicht = 0;
			LinkedList<LinkedList<Edge>> adj = new LinkedList<LinkedList<Edge>>();
			for(int j=0;j<n;j++){
				adj.add(new LinkedList<Edge>());
			}
			
			
			for(int j = 0;j<m;j++){
				int u = s.nextInt();
				int v = s.nextInt();
				int gewicht = s.nextInt();
				
				adj.get(u).add(new Edge(u,v,gewicht));
				adj.get(v).add(new Edge(v,u,gewicht));
				
			}
			//finished adjanzentelist generating
			PriorityQueue<Edge> minHeap = new PriorityQueue<Edge>();
			boolean[] visited = new boolean[n];
			Arrays.fill(visited, false);
			
			for(Edge e : adj.get(0)){
				minHeap.add(e);
			}
			visited[0] = true;
			
			while(minHeap.size()!=0){
				Edge top = minHeap.poll(); // the top is automatically smallest
				
				if(!visited[top.v]){
					visited[top.v] = true;
					Gewicht = Gewicht + top.gewicht;
					for(Edge e:adj.get(top.v) ){
						if(!visited[e.v]){
						minHeap.add(e);
						}
					}
				}
				
				
			}
			
			System.out.println(Gewicht);
			
			
			
		}
	}
}
class Edge implements Comparable<Edge>{
	int u ;
	int v ;
	int gewicht;
	
	Edge(int u , int v, int gewicht){
		this.u = u;
		this.v = v;
		this.gewicht = gewicht;
	}
	
	public int compareTo(Edge o2) {
		// TODO Auto-generated method stub
	
		return Integer.valueOf(this.gewicht).compareTo(Integer.valueOf(o2.gewicht));
	}


	
}