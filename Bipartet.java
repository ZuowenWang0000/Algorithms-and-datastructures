// Java program to find out whether a given graph is Bipartite or not
import java.util.LinkedList;
import java.util.Scanner;
 
class Bipartet
{
	int n;

	Bipartet(int n){
		this.n = n;
	}
	
	

    // This function returns true if graph G[V][V] is Bipartite, else false
    String isBipartite(int G[][],int src)
    {	int V = n;
//        System.out.println("V = "+V);
        // Create a color array to store colors assigned to all veritces.
        // Vertex number is used as index in this array. The value '-1'
        // of  colorArr[i] is used to indicate that no color is assigned
        // to vertex 'i'.  The value 1 is used to indicate first color
        // is assigned and value 0 indicates second color is assigned.
        int colorArr[] = new int[n];
        for (int i=0; i<V; ++i)
            colorArr[i] = -1;
//        System.out.println(src);
        // Assign first color to source
        colorArr[src] = 1;
 
        // Create a queue (FIFO) of vertex numbers and enqueue
        // source vertex for BFS traversal
        LinkedList<Integer>q = new LinkedList<Integer>();
        q.add(src);
 
        // Run while there are vertices in queue (Similar to BFS)
        while (q.size() != 0)
        {
 
            // Dequeue a vertex from queue
            int u = q.poll();
 
            // Find all non-colored adjacent vertices
            for (int v=0; v<V; ++v)
            {
                // An edge from u to v exists and destination v is
                // not colored
                if (G[u][v]==1 && colorArr[v]==-1)
                {
                    // Assign alternate color to this adjacent v of u
                    colorArr[v] = 1-colorArr[u];
                    q.add(v);
                }
 
                // An edge from u to v exists and destination v is
                // colored with same color as u
                else if (G[u][v]==1 && colorArr[v]==colorArr[u])
                    return "no";
            }
        }
        // If we reach here, then all adjacent vertices can
        //  be colored with alternate color
        String neibors = new String();
    	for(int k=0;k<n;k++){
    		if(colorArr[k]==1){
    			neibors = neibors+k+" ";
    		}
    	}
        return neibors;
    }
 
    // Driver program to test above function
    public static void main (String[] args)
    {
    	Scanner sc = new Scanner(System.in);
    	int t = sc.nextInt();
    	for(int x =0; x<t; x++){
    		int n = sc.nextInt();
    		int m = sc.nextInt();
    		int r = sc.nextInt();
//    		System.out.println("n = "+n);
    		Bipartet b = new Bipartet(n);
    		
    		int G[][] = new int[n][n];
    		
    		for(int j=0; j<n; j++){
    			for(int k =0; k<n; k++){
    				G[j][k]=0;
    			}
    		}
    		
    		for(int j = 0; j<m;  j++){
    			int p = sc.nextInt();
    			int q = sc.nextInt();
    			
    			G[p][q] = 1;
    			G[q][p] = 1;
    			
    		}
    		

    		System.out.println(b.isBipartite(G, r));
    	}	
}
    }
