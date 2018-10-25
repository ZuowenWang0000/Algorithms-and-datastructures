import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays; 


import java.lang.Runtime;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File; 
import java.io.FileNotFoundException;

public class eularianPath{
    static ArrayList<LinkedList<String>> adjList = new ArrayList<>();
    //we user adjacent list to store the graph.
    //the i th entry in the array- adjlist stores the edges (represented by nodes)
    //and the value of i is mapped from string to integer using hash table.


    static Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
    static ArrayList<Integer> inMinusOut = new ArrayList<Integer>();
    static int numEdge = 0;
    static ArrayList<String> verticesRecord = new ArrayList<String>();
    static Stack<String> current = new Stack<String>();
    static Stack<String> completed = new Stack<String>();
    static int startIndex = -1;
    static int endIndex = -1;
    static ArrayList<Boolean[]> visited = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("hello");
        
        //first we handle input, we use adjacency list to store the graph
        Scanner sc = new Scanner(System.in);
        String line;    
        line = sc.nextLine();
        numEdge ++;
        int length = line.length();
        System.out.println("length is : " + length);
        // prefix and suffix are both length 2
        String prefix = line.substring(0, length - 1);
        String suffix = line.substring(1, length );
        System.out.println("prefix is : " + prefix + "   suffix is : " + suffix);
        
        ht.put(prefix, 0); // we read the first node, and put its prefix in the hashtable.
                            // now if we want to visit its adj list, we need to access adjlist[get(prefix)]
        inMinusOut.add(new Integer(-1)); // bc edge is from prefix to suffix, and prefix's index is 0
        verticesRecord.add(prefix);
        ht.put(suffix, 1);
        verticesRecord.add(suffix);
        inMinusOut.add(new Integer(+1)); // the suffix's index is 1, and it's in degree is 1

        System.out.println("index of first prefix is : " + ht.get(prefix));
        System.out.println("index of first suffix is : " + ht.get(suffix));
        LinkedList<String> newNode= new LinkedList<>();
        
        newNode.add(suffix);
        adjList.add(newNode);

        LinkedList<String> emptyNode = new LinkedList<>();
        adjList.add(emptyNode); // empty list for suffix node

        System.out.println("first edge is :" + adjList.get(0)); 

        int verticesCounter = 1; // since we already have 0 and 1 indices , so we start from 2 here.
                                //but all of them will increment first so I ... pseudo -1

        
        line = sc.nextLine();
        while(!line.isEmpty()){
            numEdge ++;
            prefix = line.substring(0, length - 1);
            suffix = line.substring(1, length );
            
            
            //first we check the existence of prefix and suffix in the hashtable
            Integer idPrefix = ht.get(prefix);
            Integer idSuffix = ht.get(suffix);


            if(idPrefix!=null && idSuffix!=null){
                System.out.println("both exist!");
                //first update the degrees
                int idp = idPrefix.intValue();
                int ids = idSuffix.intValue();
                int degp = inMinusOut.get(idp);
                int degs = inMinusOut.get(ids);
                System.out.println("prefix is : " + prefix + "ID: "+idPrefix.intValue()+"  || suffix is : " + suffix + "ID : " +idSuffix.intValue());

                inMinusOut.set(idp, degp - 1);
                inMinusOut.set(ids, degs + 1);
                //finished updating degrees

                //now we append the suffix string behind prefix node, namely adding the edge
                adjList.get(idp).add(suffix);
                System.out.println(adjList.toString());
                //finished apending
                System.out.println("the adj List is :" + adjList.toString());

            }else if (idPrefix == null && idSuffix != null){
                System.out.println("prefix = NO, suffix = YES");
                verticesCounter ++; 
                //first we have to hash the new vertex
                ht.put(prefix, verticesCounter);
                verticesRecord.add(prefix);
                //now the corresponding index of this new vertex is verticesCounter
                //then add to both list and update them

                //first we update degree list
                inMinusOut.add(new Integer(-1));
                int ids = idSuffix.intValue();
                int degs = inMinusOut.get(ids);
                System.out.println("suffix is : " + suffix + "  ID : " +idSuffix.intValue());

                inMinusOut.set(ids, degs + 1);

                //then we update adjList
                LinkedList<String> newV = new LinkedList<String>();
                newV.add(suffix);
                adjList.add(newV); // add this linkedlist to the end of the addList

                System.out.println("the adj List is :" + adjList.toString());
            }else if (idPrefix != null && idSuffix == null){
                verticesCounter ++;
                System.out.println("prefix = YES, suffix = NO");

                ht.put(suffix, verticesCounter);
                verticesRecord.add(suffix);
                inMinusOut.add(new Integer(+1));
                int idp = idPrefix.intValue();
                int degp = inMinusOut.get(idp);
                System.out.println("prefix is : " + idp );

                inMinusOut.set(idp, degp - 1);
                
                adjList.get(idp).add(suffix);
                LinkedList<String> newV = new LinkedList<String>();
                adjList.add(newV);  // and an empy vertex into the adjList, since it doesn't have in edge yet
                System.out.println("the adj List is :" + adjList.toString());
            }else{ // both are null
                System.out.println("both doesn't exist yet!");
                verticesCounter++;
                //we handle SUFFIX first
                ht.put(suffix, verticesCounter);
                verticesRecord.add(suffix);
                inMinusOut.add(new Integer(+1));
                LinkedList<String> newV1 = new LinkedList<String>();
                adjList.add(newV1);  //it's suffix, and it's in edge, so empty linkedlist

                //now we handle PREFIX
                verticesCounter++;
                ht.put(prefix, verticesCounter);
                verticesRecord.add(prefix);
                inMinusOut.add(new Integer(-1));
                LinkedList<String> newV2 = new LinkedList<String>();
                newV2.add(suffix);
                adjList.add(newV2);
             
                System.out.println("the adj List is :" + adjList.toString());
            }
            line = sc.nextLine();
        }


        System.out.println("there are in total " + (verticesCounter+1) + "vertices");
        System.out.println("there are in total " + numEdge + "edges");

        System.out.println("the adj List is : " + adjList.toString());
        System.out.println("the degreeDiff is : " + inMinusOut.toString());

        int checkPreCon = 0;
        for(int i = 0; i < verticesCounter + 1; i++){
            Boolean[] subVisited = new Boolean[adjList.get(0).size()];
            Arrays.fill(subVisited, true);
            visited.add(subVisited);

            int temp = inMinusOut.get(i).intValue();
            System.out.println("temp = " + temp);
            if(temp == -1){startIndex = i;  checkPreCon++;}
            if(temp == 1){endIndex = i;  checkPreCon++;}
            if(temp!=0 && temp!=1 && temp!=-1){System.out.println("no euler path!1"); System.exit(0);}

        }
        System.out.println("checkPreCon = " + checkPreCon);
        if(checkPreCon != 0 && checkPreCon != 2){System.out.println("no euler path!2"); System.exit(0);}
        
        //Now startIndex and endIndex are storing the POSSIBLE start and end indices of euler path.
        // if both are -1, then it should be an euler circuit.

        System.out.println("the visited records are :  " + visited.toString());
        System.out.println("memory used : " + (Runtime.getRuntime().totalMemory()/1000000) + "MB");
        
        sc.close();
        
    }

}