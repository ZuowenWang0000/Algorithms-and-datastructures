import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class diningTable {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		
		for(int i =0;i<T;i++){
			int n = s.nextInt();
			int m = s.nextInt();
			int r = s.nextInt();
			ArrayList<ArrayList<Integer>> G = new ArrayList<ArrayList<Integer>>();
			boolean[] visited = new boolean[n];
			int[] color = new int[n];
			for(int j = 0;j<n;j++){
				G.add(new ArrayList<Integer>());
				visited[j] = false;
				color[j] = -1;
				
			}
			for(int j =0;j<m;j++){
				int u = s.nextInt();
				int v = s.nextInt();
				G.get(u).add(v);
				G.get(v).add(u);
			}
			
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(r);
			visited[r] = true;
			color[r] = 0;
			boolean isPossible = true;
			
			while(queue.size()!=0){
				int top = queue.poll();
				
				for(Integer friends : G.get(top)){
					if(color[friends]==color[top]){
						isPossible = false;
						break;
					}	
					if(!visited[friends]){
		
					color[friends] = 1-color[top];
					visited[friends] = true;
					queue.add(friends);
					
					}
				}
				
			}
			
			if(!isPossible){
				System.out.println("no");
			}else{
			for(int j = 0;j<n;j++){
				if(color[j] == color[r]){
					System.out.print(j+" ");
				}
			}
			System.out.println();
			}
			
		}
	}
}
