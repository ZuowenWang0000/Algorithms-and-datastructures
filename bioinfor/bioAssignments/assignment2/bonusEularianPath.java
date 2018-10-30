// CSCI3220 2018-19 First Term Assignment 2

// I declare that the assignment here submitted is original except for source material explicitly acknowledged, and that the same or closely related material has not been previously submitted for another course. I also acknowledge that I am aware of University policy and regulations on honesty in academic work, and of the disciplinary guidelines and procedures applicable to breaches of such policy and regulations, as contained in the following websites.

// University Guideline on Academic Honesty:
// http://www.cuhk.edu.hk/policy/academichonesty/

// Student Name: Zuowen Wang
// Student ID  : 1155123906


//final final version
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays; 
import java.io.IOException;
import java.io.File;


public class bonusEularianPath{
    //we user adjacent list to store the graph.
    //the i th entry in the array- adjlist stores the edges (represented by nodes)
    //and the value of i is mapped from string to integer using hash table.
    static ArrayList<LinkedList<String>> adjList = new ArrayList<>();

    static Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
    static ArrayList<Integer> inMinusOut = new ArrayList<Integer>();
    static int numEdge = 0;
    static ArrayList<String> verticesRecord = new ArrayList<String>();

    public static void main(String[] args) throws Exception{
        int startIndex = -1;
        int endIndex = -1;
        ArrayList<Boolean[]> visited = new ArrayList<>();
        // System.out.println("hello");
        // File file = new File("./testcase4");
        //first we handle input, we use adjacency list to store the graph
        // Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        String line;    
        line = sc.nextLine();
        numEdge ++;
        int length = line.length();
        // System.out.println("length is : " + length);
        // prefix and suffix are both length 2
        String prefix = line.substring(0, length - 1);
        String suffix = line.substring(1, length );
        int verticesCounter;
        if(prefix.equals(suffix)){ // here handles cases like AAA
            ht.put(prefix, 0);
            inMinusOut.add(new Integer(0));
            verticesRecord.add(prefix);
            LinkedList<String> newNode = new LinkedList<>();
            newNode.add(suffix);
            adjList.add(newNode);



            verticesCounter = 0; // since we already have first indice , so we start from 1 here.
            //but all of them will increment first so I ... pseudo -1
        }else{ // the following are normal cases like AAG
        // System.out.println("prefix is : " + prefix + "   suffix is : " + suffix);
        
        ht.put(prefix, 0); // we read the first node, and put its prefix in the hashtable.
                            // now if we want to visit its adj list, we need to access adjlist[get(prefix)]
        inMinusOut.add(new Integer(-1)); // bc edge is from prefix to suffix, and prefix's index is 0
        verticesRecord.add(prefix);
        ht.put(suffix, 1);
        verticesRecord.add(suffix);
        inMinusOut.add(new Integer(+1)); // the suffix's index is 1, and it's in degree is 1

        // System.out.println("index of first prefix is : " + ht.get(prefix));
        // System.out.println("index of first suffix is : " + ht.get(suffix));
        LinkedList<String> newNode= new LinkedList<>();
        
        newNode.add(suffix);
        adjList.add(newNode);

        LinkedList<String> emptyNode = new LinkedList<>();
        adjList.add(emptyNode); // empty list for suffix node

        // System.out.println("first edge is :" + adjList.get(0)); 

            verticesCounter = 1; // since we already have 0 and 1 indices , so we start from 2 here.
                                //but all of them will increment first so I ... pseudo -1
        }
        // if(sc.hasNextLine()){
        
        // }
        while(sc.hasNextLine()){
            line = sc.nextLine();
            numEdge ++;
            prefix = line.substring(0, length - 1);
            suffix = line.substring(1, length );
            // System.out.println("pre : " + prefix);
            // System.out.println("suf : " + suffix);
            
            if(prefix.equals(suffix)){ // this handles cases like AAA by adding self loop
                // System.out.println("self loop about : " + suffix);
                Integer id = ht.get(prefix); //get(suffix) also works
                if(id == null){ // create a vertice with the same string in it's list
                    verticesCounter++;
                    ht.put(prefix, verticesCounter);
                    verticesRecord.add(prefix);
                    inMinusOut.add(new Integer(0)); //self loop vertice doesn't affect the in-out, but we need a placeholder
                    //now we update adjlist
                    LinkedList<String> newV = new LinkedList<String>();
                    newV.add(suffix);
                    adjList.add(newV); // add this linkedlist to the end of the addList



                }else{ // id != null, this vertice already exist, we only need to append the same string in the adjlist
                    adjList.get(ht.get(prefix)).add(suffix);  
                }
            }else{
                
                
                //first we check the existence of prefix and suffix in the hashtable
                Integer idPrefix = ht.get(prefix);
                Integer idSuffix = ht.get(suffix);


                if(idPrefix!=null && idSuffix!=null){
                    // System.out.println("both exist!");
                    //first update the degrees
                    int idp = idPrefix.intValue();
                    int ids = idSuffix.intValue();
                    int degp = inMinusOut.get(idp);
                    int degs = inMinusOut.get(ids);
                    // System.out.println("prefix is : " + prefix + "ID: "+idPrefix.intValue()+"  || suffix is : " + suffix + "ID : " +idSuffix.intValue());

                    inMinusOut.set(idp, degp - 1);
                    inMinusOut.set(ids, degs + 1);
                    //finished updating degrees

                    //now we append the suffix string behind prefix node, namely adding the edge
                    adjList.get(idp).add(suffix);
                    // System.out.println(adjList.toString());
                    //finished apending
                    // System.out.println("the adj List is :" + adjList.toString());

                }else if (idPrefix == null && idSuffix != null){
                    // System.out.println("prefix = NO, suffix = YES");
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
                    // System.out.println("suffix is : " + suffix + "  ID : " +idSuffix.intValue());

                    inMinusOut.set(ids, degs + 1);

                    //then we update adjList
                    LinkedList<String> newV = new LinkedList<String>();
                    newV.add(suffix);
                    adjList.add(newV); // add this linkedlist to the end of the addList

                    // System.out.println("the adj List is :" + adjList.toString());
                }else if (idPrefix != null && idSuffix == null){
                    verticesCounter ++;
                    // System.out.println("prefix = YES, suffix = NO");

                    ht.put(suffix, verticesCounter);
                    verticesRecord.add(suffix);
                    inMinusOut.add(new Integer(+1));
                    int idp = idPrefix.intValue();
                    int degp = inMinusOut.get(idp);
                    // System.out.println("prefix is : " + idp );

                    inMinusOut.set(idp, degp - 1);
                    
                    adjList.get(idp).add(suffix);
                    LinkedList<String> newV = new LinkedList<String>();
                    adjList.add(newV);  // and an empy vertex into the adjList, since it doesn't have in edge yet
                    // System.out.println("the adj List is :" + adjList.toString());
                }else{ // both are null
                    // System.out.println("both doesn't exist yet!");
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
                
                    // System.out.println("the adj List is :" + adjList.toString());
                }

            }
           
        }


        // System.out.println("there are in total " + (verticesCounter+1) + " vertices");
        // System.out.println("there are in total " + numEdge + " edges");
        
        // System.out.println("the adj List is : " + adjList.toString());
        // System.out.println("the degreeDiff is : " + inMinusOut.toString());
        // System.out.println("records : " + verticesRecord.toString());
        boolean triviallyWrong = false;
        int checkPreCon = 0;
        for(int i = 0; i < verticesCounter + 1; i++){
            Boolean[] subVisited = new Boolean[adjList.get(i).size()];
            Arrays.fill(subVisited, true);
            visited.add(subVisited);

            int temp = inMinusOut.get(i).intValue();
            // System.out.println("temp = " + temp);
            if(temp == -1){startIndex = i;  checkPreCon++;}
            if(temp == 1){endIndex = i;  checkPreCon++;}
            if(temp!=0 && temp!=1 && temp!=-1){
                // System.out.println("no euler path!1"); 
                triviallyWrong = true;}

        }
        // System.out.println("checkPreCon = " + checkPreCon);
        if(checkPreCon != 0 && checkPreCon != 2){
            // System.out.println("no euler path!2"); 
            triviallyWrong = true;}
        
        //Now startIndex and endIndex are storing the POSSIBLE start and end indices of euler path.
        // if both are -1, then it should be an euler circuit.

        // for(int i = 0; i < verticesCounter + 1; i++){
        //     // System.out.println("the visited records are :  " + Arrays.toString(visited.get(i)));
        // }

        // System.out.println("We should start from : " + verticesRecord.get(startIndex));
        // System.out.println("The path should end at : " + verticesRecord.get(endIndex));

        // START OUR PUSH AND POP!
        if(startIndex == -1 && endIndex == -1){
            startIndex = 0;
        }
        // System.out.println("check trivial : " + triviallyWrong);
        if(!triviallyWrong){
            
            int edgeCounter = 0; 

            if(checkPreCon == 0){
                //System.out.println("HAHA");
                for(int j = 0; j < verticesRecord.size(); j++){
                  //  System.out.println("J = " + j);
                    String output = "" + verticesRecord.get(j).charAt(0);
                    DFS(output, visited, edgeCounter, j);
                }


            }else{
            String output = "" + verticesRecord.get(startIndex).charAt(0);
            DFS(output, visited, edgeCounter, startIndex);
            }
        }

        // System.out.println("memory used : " + (Runtime.getRuntime().totalMemory()/1000000) + "MB");
        sc.close();
    }

    public static void DFS(String output, ArrayList<Boolean[]> visited, int edgeCounter, int startNode){

        // System.out.println("DFS depth " + depth);
        // System.out.println("current node :" + verticesRecord.get(startNode));
        //exit of recursion
        int numNeighbor = adjList.get(startNode).size();
        // System.out.println("numNeighbor : " + numNeighbor);
        // System.out.println("numNeighbor : " + numNeighbor);
        boolean allVisited = true;
        
        for(int i = 0; i < numNeighbor; i ++){
            // System.out.println("i WANT TO BREAK FREE");
            if(visited.get(startNode)[i]){
                // System.out.println("visited ?? " + visited.get(startNode)[i]);
                allVisited = false;
                break;
            }
        }

        if(numNeighbor == 0 || allVisited){ // this node only has one in- or it stuck
            //System.out.println("HERE!2");
            if(edgeCounter == numEdge ){ //stuck but finished eularpath
                // System.out.println("I am tired : " + verticesRecord.get(startNode).substring(1, verticesRecord.get(startNode).length()));
                output = output + verticesRecord.get(startNode).substring(1, verticesRecord.get(startNode).length());
                System.out.println(output);
                return;
            }else{
                return;
            }
        }else{ 
            //System.out.println("HERE!3");
            for(int i = 0; i< numNeighbor; i++){
                // we have to first check if this node has active out-edge
                if(visited.get(startNode)[i]){ // find an active out-edge!
                    //System.out.println("HERE!1");
                    // System.out.println("interm. output " + output);
                    String output2= output + adjList.get(startNode).get(i).charAt(0);
                    visited.get(startNode)[i] = false;
                    int counter2 = edgeCounter + 1 ;
                    int newStartNode = ht.get(adjList.get(startNode).get(i));
                    DFS(output2, visited, counter2, newStartNode);
                    visited.get(startNode)[i] = true;
                }
            }
        }
    }



}

