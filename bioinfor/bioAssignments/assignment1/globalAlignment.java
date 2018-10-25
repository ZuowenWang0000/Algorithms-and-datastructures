import java.util.LinkedList;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;

public class globalAlignment{
    public static void main(String[] args) {
      //  System.out.println("Hello World!");

        // File file = 
        // new File("input2.txt"); 

        
        Scanner sc = new Scanner(System.in);


        int sMatch = sc.nextInt();
        int sMismatch = sc.nextInt();
        int sIndel = sc.nextInt();

        sc.nextLine();//to consume the last \n of the previous int input
        String r = sc.nextLine();
        String s = sc.nextLine();

        int rlength = r.length();
        int slength = s.length();
        //check if the inputs are correct, the following line will be commented out for the OJ
        // System.out.println(sMatch + " " + sMismatch + " " + " " + 
        // sIndel + " " + r + " " + s + " " + r.charAt(0) + " " + s.charAt(0) + " r length: "+rlength + " s length: " + slength);

        //We need two tables for DP, one int table for alignment score and one LinkedList<String[]> table for alignments
        //(we can append different possible allignments on every entry, very convinent, each position of linkedlist contains a String[2] array).
        int[][] score = new int[rlength + 1][slength + 1];
        @SuppressWarnings("unchecked")LinkedList<String[]> alig[][] = new LinkedList[rlength + 1][slength + 1];

        //initialization for table "score" and table "alig", i.e. base cases
        score[rlength][slength] = 0;
        //initialize a the linkedlist object for each entry of the alig table
        for(int i = 0; i < rlength+1; i++){
            for(int j = 0; j<slength+1;j++){
                alig[i][j] = new LinkedList<String[]>();
            }
        }
        // this temp string array is a container, will be frequently used for initialization ,and later on, updates of DP table
        String temp[] = new String[2]; 
        temp[0] = "";temp[1] = "";
        alig[rlength][slength].add(temp);;

        for(int i = rlength-1; i>-1; i-- ){
            score[i][slength] = score[i + 1][slength] + sIndel;
            //since these are base cases, so there is only one possiblity for each entry. 
            //BUT we will need to traverse the entirelinkedlist later on for updating none trivial entries in the DP table
        //    temp[0] = r.charAt(i) + alig[i+1][slength].getFirst()[0];
        //    temp[1] = "_" + alig[i+1][slength].getFirst()[1];

        // System.out.println("HAHA:" + temp[0] + " " +temp[1]);
           String newTemp[] = new String[2]; 
        //    String s0,s1;
        //    s0 = temp[0]; s1 = temp[1];
           newTemp[0] = new String(r.charAt(i) + alig[i+1][slength].getFirst()[0]);
           newTemp[1] = new String("_" + alig[i+1][slength].getFirst()[1]);
           alig[i][slength].add(newTemp);
        }
        for(int j = slength-1; j>-1; j -- ){
            score[rlength][j] = score[rlength][j + 1] + sIndel;
        //    temp[0] = "_" + alig[rlength][j + 1].getFirst()[0];
        //    temp[1] = s.charAt(j) + alig[rlength][j + 1].getFirst()[1];

        //    System.out.println("HEHE:" + temp[0] + " " +temp[1]);
           String newTemp[] = new String[2]; 
        //    String s0,s1;
        //    s0 = temp[0]; s1 = temp[1];
           newTemp[0] = new String("_" + alig[rlength][j + 1].getFirst()[0]);
           newTemp[1] = new String(s.charAt(j) + alig[rlength][j + 1].getFirst()[1]);
           alig[rlength][j].add(newTemp);
        }

        // for(int i = 0 ; i < 6; i++){
        //     System.out.println(i + " " + alig[5][i].getFirst()[0] + " | " + alig[5][i].getFirst()[1]);
        // }

        // for(int i = 0 ; i < 6; i++){
        //     System.out.println(i + " " + alig[i][5].getFirst()[0] + " | " + alig[i][5].getFirst()[1]);
        // }

        //fill the two DP tables. When the score table got updated (same score or higher score)
        for(int i = rlength - 1; i > -1; i--){
            for(int j = slength - 1; j > -1; j--){
                // we first try to update the scores
                //If the update(s) comes from right or bottom side, then it must be an indel.
                //If the update comes from right bottom side, it can be a match or mismatch.

                //this is a boolean list, 0 -RIGHT , 1 - BOTTOM, 2 - DIAGONAL
                boolean[] max = new boolean[3];  max[0] = false; max[1] = false; max[2] = false;
                //first check the update score from diagonal is a match or mismatch
                int diagonalDiff;
                if(r.charAt(i) == s.charAt(j)){diagonalDiff = sMatch;}
                else{diagonalDiff = sMismatch;}

                // some nasty ordering stuff, in order to mark all possible direction where the sub best alignments come from
                if(score[i+1][j] + sIndel > score[i][j+1] + sIndel){ //bottom > right
                    if(score[i+1][j+1] + diagonalDiff > score[i+1][j] + sIndel){ // diagonal > bottom > right
                        max[2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                    }else if (score[i+1][j+1] + diagonalDiff < score[i+1][j] + sIndel){ //bottom > diagonal, right 
                        max[1] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                    }else { // diagonal == bottom > right
                        max[2] = true;
                        max[1] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                    }
                }else if(score[i+1][j] + sIndel < score[i][j+1] + sIndel){ // right > bottom
                    if(score[i+1][j+1] + diagonalDiff > score[i][j+1] + sIndel){ // diagonal > right > bottom
                        max[2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                    }else if(score[i+1][j+1] + diagonalDiff < score[i][j+1] + sIndel){ // right > diagonal, bottom
                        max[0] = true;
                        score[i][j] = score[i][j+1] + sIndel;
                    }else{ //diagonal == right > bottom
                        max[2] = true;
                        max[0] = true;
                        score[i][j] = score[i][j+1] + sIndel;
                    }
                }else{ // bottom == right
                    if(score[i+1][j+1] + diagonalDiff > score[i+1][j] + sIndel){ // diagonal >  bottom == right
                        max[2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                    }else if(score[i+1][j+1] + diagonalDiff < score[i+1][j] + sIndel){ // bottom == right > diagonal
                        max[0] = true;
                        max[1] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                    }else{ // bottom == right == diagonal
                        max[0] = true;
                        max[1] = true;
                        max[2] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                    }
                }
                //END of deciding which direction(s) the update(s) is(are) from. information in "max"
                int count = 0; //number of "red arrows"
                if(max[0]){ //RIGHT
                    // for (String[] str: alig[i+1][j]) {
                    //     count ++;
                    //     System.out.println("LOLOLO1");
                    //     String newTemp[] = new String[2];
                    //     newTemp[1] = new String(r.charAt(i) + str[0]);
                    //     newTemp[0] = new String("_" + str[1]);
                    //     alig[i][j].add(newTemp);
                    // }

                    for(int k = 0; k < alig[i][j+1].size();k++){
                        count ++;
                        String newTemp[] = new String[2];
                        newTemp[0] = new String("_" + alig[i][j+1].get(k)[0]);
                        newTemp[1] = new String(s.charAt(j) + alig[i][j+1].get(k)[1]);
 //                       if(i == 4 && j ==3){System.out.println("*****HERE MAP 0 *****");}
                        alig[i][j].add(newTemp);
                    }
                }

                if(max[2]){ //DIAGONAL
                    // for (String[] str: alig[i+1][j+1]) {
                    //     count++;
                    //     System.out.println("LOLOLO3");
                    //     String newTemp[] = new String[2];
                    //     newTemp[0] = new String(r.charAt(i) + str[0]);
                    //     newTemp[1] = new String(s.charAt(j) + str[1]);
                    //     alig[i][j].add(newTemp);
                    // }
                    for(int k = 0; k < alig[i+1][j+1].size();k++){
                        count ++;
 //                       System.out.println("LOLOLO3");
                        String newTemp[] = new String[2];
                        newTemp[0] = new String(r.charAt(i) + alig[i+1][j+1].get(k)[0]);
                        newTemp[1] = new String(s.charAt(j) + alig[i+1][j+1].get(k)[1]);
//                        if(i == 4 && j ==3){System.out.println("*****HERE MAP 2 *****");}
                        alig[i][j].add(newTemp);
                    }
                }

                if(max[1]){ //BOTTOM
                    // for (String[] str: alig[i][j+1]) {
                    //     count++;
                    //     System.out.println("LOLOLO2");
                    //     String newTemp[] = new String[2];
                    //     newTemp[0] = new String(s.charAt(j) + str[1]);
                    //     newTemp[1] = new String("_" + str[0]);
                    //     alig[i][j].add(newTemp);
                    // }
                    for(int k = 0; k < alig[i+1][j].size();k++){
                        count ++;
//System.out.println("LOLOLO2");
                        String newTemp[] = new String[2];
                        newTemp[0] = new String(r.charAt(i) + alig[i+1][j].get(k)[0]);
                        newTemp[1] = new String("_" + alig[i+1][j].get(k)[1]);
 //                       if(i == 4 && j ==3){System.out.println("*****HERE MAP 1 *****");}
                        alig[i][j].add(newTemp);
                    }
                }



                // if(i==0 &&j==0){System.out.println(max[0]+" "+max[1]+" "+max[2]);}
                // System.out.println("COUNT: "+count);

                //set back the truth table
                max[0] = false; max[1] = false; max[2] = false;

            }
        }

        // for(int i = 0; i < rlength+1; i++){
        //     for(int j = 0; j< slength +1;j++){
        //         System.out.print(score[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        // for(int i = 0; i < rlength+1; i++){
        //     for(int j = 0; j< slength +1;j++){
        //         System.out.print(alig[i][j].size() + " ");
        //     }
        //     System.out.println();
        // }

        // for (String[] str : alig[0][0]) {
        //     System.out.println("r: " + str[0] + "  s: " + str[1]);
        // }

        // for (String[] str : alig[5][5]) {
        //     System.out.println("r: " + str[0] + "  s: " + str[1]);
        // }
        // for (String[] str : alig[4][4]) {
        //     System.out.println("r: " + str[0] + "  s: " + str[1]);
        // }
        System.out.println(score[0][0]);

        int i ;
        for(i = 0; i < alig[0][0].size();i++){
            System.out.println();
            System.out.println(alig[0][0].get(i)[0]);
            System.out.println(alig[0][0].get(i)[1]);
        }

    
        // for(int i = 0; i < alig[4][3].size();i++){
        //     System.out.println("4 r: " + alig[4][3].get(i)[0]);
        //     System.out.println("3 s: " + alig[4][3].get(i)[1]);
        //     System.out.println();
        // }

        // for(int i = 0; i < alig[4][4].size();i++){
        //     System.out.println("4 r: " + alig[4][4].get(i)[0]);
        //     System.out.println("4 s: " + alig[4][4].get(i)[1]);
        //     System.out.println();
        // }






        sc.close();
    }
}