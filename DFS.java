import java.util.ArrayList;
import java.util.Scanner;


public class DFS {
	
	static ArrayList<ArrayList<Integer>> G;
	static boolean[] visited;
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int m = s.nextInt();
		
		G = new ArrayList<ArrayList<Integer>>();
		visited = new boolean[n];
		
		for(int i = 0;i<n;i++){
			G.add(new ArrayList<Integer>());
			visited[i] = false;
		}
		for(int i = 0;i<m;i++){
			int u = s.nextInt();
			int v = s.nextInt();
			
			G.get(u).add(v);
			G.get(v).add(u);
		}
		
		visited[0] = true;
		System.out.print("0 ");
		dfs(G, 0);
		
	}
	
	
	static void dfs(ArrayList<ArrayList<Integer>> G,int v){
		
		for(Integer neighbor: G.get(v)){
			if(!visited[neighbor]){
				visited[neighbor] = true;
				System.out.print(neighbor+" ");
				
				dfs(G, neighbor);
			}
		}
	}
}
