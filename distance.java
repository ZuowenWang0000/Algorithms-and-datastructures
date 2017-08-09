import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class distance {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		for(int i =0;i<T;i++){
			int n = s.nextInt();
			int m = s.nextInt();
			int v= s.nextInt();
			LinkedList[] adj = new LinkedList[n];
			boolean[] visited = new boolean[n];
			int[] distance = new int[n];

			for(int j=0;j<n;j++){
				visited[j] = false;
				distance[j] = 0;
				adj[j] = new LinkedList();
			}
			
			for(int j=0;j<m;j++){
				int a = s.nextInt();
				int b = s.nextInt();
				adj[a].add(b);
				adj[b].add(a);
			}


				LinkedList<Integer> queue = new LinkedList<Integer>();
				queue.add(v);
				visited[v] = true;
				while(queue.size()!=0){
					int top = queue.poll();
					Iterator<Integer> itr = adj[top].iterator();
					
					while(itr.hasNext()){
						int next = itr.next();

						if(!visited[next]){
							visited[next] = true;
							distance[next] = distance[top]+1;
							queue.add(next);
						}
					}

					
				}
				
				for(int k =0;k<n;k++){
					if(visited[k]){
						System.out.print(distance[k]+" ");
					}else{
						System.out.print(-1+" ");
					}
				}
				System.out.println();
				
			
			
			
			
		}
		
		
		
	}
}
