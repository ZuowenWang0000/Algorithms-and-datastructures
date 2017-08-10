import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class Myflow {
	static ArrayList<ArrayList<Integer>> G;

	static int[][] flow;
	static int[][] capacity;
	static int n;
	
	public static void main(String[] args){
		G = new ArrayList<ArrayList<Integer>>();
		Scanner s = new Scanner(System.in);
		n = s.nextInt();  //including source and sink
		int m = s.nextInt();
		flow = new int[n][n];
		capacity = new int[n][n];
		for(int i =0; i<n;i++){
			G.add(new ArrayList<Integer>());
			for(int j = 0;j<n;j++){
				flow[i][j] = 0;
				capacity[i][j] = 0;
			}
		}
		
		for(int i = 0; i < m ; i++){
			int u = s.nextInt();
			int v = s.nextInt();
			int cap = s.nextInt();
			
			G.get(u).add(v);
			capacity[u][v] = cap;
		}
		
		
		int maxFlow = 0;
		maxFlow = computeMaximumFlow();
		System.out.println(maxFlow);
		
	s.close();}
	
	static boolean augmentingExistence(int[] previousVertexOnPath){
		LinkedList<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[n];
		for(int i =0;i< n;i++){
			visited[i] = false;
		}
		
		//BFS starts at source s == 0  , btw t = n-1
		queue.add(0);
		visited[0] = true;
		
		
		while (queue.size() != 0) {
			int v = queue.poll();
			for (Integer w : G.get(v)) {
				if (!visited[w]&&capacity[v][w]>flow[v][w]) {
					visited[w] = true;
					previousVertexOnPath[w] = v;
					queue.add(w);
					if(w == n-1){
						return true; //when the path end "w" reaches sink t, then 
									// there exists an augmenting path
					}
				}
			}
		}

		return false;
		
	}
	
	static int computeMaximumFlow(){
        int  increaseLimitation;
        // Find paths with BFS and return path in previousVertexOnPath array
        int[] previousVertexOnPath = new int[n];
        Arrays.fill(previousVertexOnPath, -1);
        // Start with empty flow
        int maxFlow = 0;
        // Use augmenting path P as long as possible
		
        while(augmentingExistence(previousVertexOnPath)){
        	increaseLimitation = Integer.MAX_VALUE;
            
        	for(int i = n-1;i!=0;i = previousVertexOnPath[i]){
        		int p = previousVertexOnPath[i];
        		increaseLimitation = Math.min(increaseLimitation, capacity[p][i]-flow[p][i]);
        	}
        	for(int i = n-1;i!=0;i = previousVertexOnPath[i]){
        		int p = previousVertexOnPath[i];
        		flow[p][i] = flow[p][i] + increaseLimitation;
        		flow[i][p] = flow[i][p] - increaseLimitation;
        	}
        	
        	maxFlow = maxFlow + increaseLimitation;
        }
        
		
		return maxFlow;
	}
	
	
}
