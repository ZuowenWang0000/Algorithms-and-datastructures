/* CSCI3220 2018-19 First Term Assignment 1
I declare that the assignment here submitted is original except for source
material explicitly acknowledged, and that the same or closely related material
has not been previously submitted for another course. I also acknowledge that I
am aware of University policy and regulations on honesty in academic work, and
of the disciplinary guidelines and procedures applicable to breaches of such
policy and regulations, as contained in the following websites.
University Guideline on Academic Honesty:
http://www.cuhk.edu.hk/policy/academichonesty/
Student Name: <Zuowen Wang>
 Student ID : <1155123906> */


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException; 


public class globalAlignmentSimplified{
    public static String r;
    public static String s;
    public static int rlength;
    public static int slength;
    public static int[][] score;
    public static boolean[][][] max; //0 -RIGHT , 1 - BOTTOM, 2 - DIAGONAL

    public static void main(String[] args)throws FileNotFoundException {

        // File file = new File("input.txt");
        // Scanner sc = new Scanner(file);

        Scanner sc = new Scanner(System.in);
        int sMatch = sc.nextInt();
        int sMismatch = sc.nextInt();
        int sIndel = sc.nextInt();

        sc.nextLine();//to consume the last \n of the previous int input
        r = sc.nextLine();
        s = sc.nextLine();

        rlength = r.length();
        slength = s.length();
        // int rlength = r.length();
        // int slength = s.length();
        //check if the inputs are correct, the following line will be commented out for the OJ
        // System.out.println(sMatch + " " + sMismatch + " " + " " + 
        // sIndel + " " + r + " " + s + " " + r.charAt(0) + " " + s.charAt(0) + " r length: "+rlength + " s length: " + slength);

        //We need two tables for DP, one int table for alignment score and one boolean[]][][3] table for recording the "arrows"
        score = new int[rlength + 1][slength + 1];
        max = new boolean[rlength + 1][slength + 1][3];

        //initialization for table "score" and table "alig", i.e. base cases
        score[rlength][slength] = 0;


        for(int i = rlength-1; i>-1; i-- ){
            score[i][slength] = score[i + 1][slength] + sIndel;
            max[i][slength][1] = true;
        }
        for(int j = slength-1; j>-1; j -- ){
            score[rlength][j] = score[rlength][j + 1] + sIndel;
            max[rlength][j][0] = true;
        }


        //fill the two DP tables. When the score table got updated (same score or higher score)
        for(int i = rlength - 1; i > -1; i--){
            for(int j = slength - 1; j > -1; j--){
                // we first try to update the scores
                //If the update(s) comes from right or bottom side, then it must be an indel.
                //If the update comes from right bottom side, it can be a match or mismatch.

                //this is a boolean list, 0 -RIGHT , 1 - BOTTOM, 2 - DIAGONAL

                //first check the update score from diagonal is a match or mismatch
                int diagonalDiff;
                if(r.charAt(i) == s.charAt(j)){diagonalDiff = sMatch;}
                else{diagonalDiff = sMismatch;}

                // some nasty ordering stuff, in order to mark all possible direction where the sub best alignments come from
                if(score[i+1][j] + sIndel > score[i][j+1] + sIndel){ //bottom > right
                    if(score[i+1][j+1] + diagonalDiff > score[i+1][j] + sIndel){ // diagonal > bottom > right
                        max[i][j][2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                        max[i][j][0] = false;
                        max[i][j][1] = false;
                    }else if (score[i+1][j+1] + diagonalDiff < score[i+1][j] + sIndel){ //bottom > diagonal, right 
                        max[i][j][1] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                        max[i][j][0] = false;
                        max[i][j][2] = false;
                    }else { // diagonal == bottom > right
                        max[i][j][2] = true;
                        max[i][j][1] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                        max[i][j][0] = false;
                    }
                }else if(score[i+1][j] + sIndel < score[i][j+1] + sIndel){ // right > bottom
                    if(score[i+1][j+1] + diagonalDiff > score[i][j+1] + sIndel){ // diagonal > right > bottom
                        max[i][j][2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                        max[i][j][0] = false;
                        max[i][j][1] = false;
                    }else if(score[i+1][j+1] + diagonalDiff < score[i][j+1] + sIndel){ // right > diagonal, bottom
                        max[i][j][0] = true;
                        score[i][j] = score[i][j+1] + sIndel;
                        max[i][j][2] = false;
                        max[i][j][1] = false;
                    }else{ //diagonal == right > bottom
                        max[i][j][2] = true;
                        max[i][j][0] = true;
                        score[i][j] = score[i][j+1] + sIndel;
                        max[i][j][1] = false;
                    }
                }else{ // bottom == right
                    if(score[i+1][j+1] + diagonalDiff > score[i+1][j] + sIndel){ // diagonal >  bottom == right
                        max[i][j][2] = true;
                        score[i][j] = score[i+1][j+1] + diagonalDiff;
                        max[i][j][0] = false;
                        max[i][j][1] = false;
                    }else if(score[i+1][j+1] + diagonalDiff < score[i+1][j] + sIndel){ // bottom == right > diagonal
                        max[i][j][0] = true;
                        max[i][j][1] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                        max[i][j][2] = false;
                    }else{ // bottom == right == diagonal
                        max[i][j][0] = true;
                        max[i][j][1] = true;
                        max[i][j][2] = true;
                        score[i][j] = score[i+1][j] + sIndel;
                    }
                }
            }
        }
        // for(int i = 0; i < rlength + 1; i++ ){
        //     for(int j = 0; j<slength + 1; j++){
        //         System.out.print(score[i][j] + " ");
        //     }System.out.println();
        // }

        for(int i = 0; i < rlength + 1; i++ ){
            for(int j = 0; j<slength + 1; j++){
                for(int k =0; k<3;k++){
                    System.out.print(max[i][j][k] + " ");
                }System.out.print(" | ");
            }System.out.println();
        }

        System.out.println(score[0][0]);

        String initialr = "";
        String initials = "";

        traceBack(0,0,initialr,initials);
        sc.close();
    }

    public static void traceBack(int i, int j, String outputr, String outputs){

        if(i == r.length() && j == s.length()){//exit
            System.out.println();
            System.out.println(outputr);
            System.out.println(outputs);

        }

        if(max[i][j][0]){//RIGHT
            if(j != s.length()){
                String temp1 = new String( outputr + "_");
                String temp2 = new String(outputs + s.charAt(j));
                //System.out.println("r: " + temp1 + "  s: " + temp2);
                j++;
                traceBack(i, j, temp1, temp2);
                j--;
            }
        }

        if(max[i][j][1]){//BOTTOM
            if(i != r.length()){
                //System.out.println("HERE1");
                String temp1 = new String(outputr + r.charAt(i));
                String temp2 = new String(outputs + "_");
                //System.out.println("r: " + temp1 + "  s: " + temp2);
                i++;
                traceBack(i, j, temp1, temp2);
                i--;
            }
        }

        if(max[i][j][2]){//DIAGONAL
            if(i != r.length() && j != s.length()){
                //System.out.println("HERE2");
                String temp1  = new String(outputr + r.charAt(i));
                String temp2 = new String(outputs = outputs + s.charAt(j));
                //System.out.println("r: " + temp1 + "  s: " + temp2);
                i++; j++;
                traceBack(i, j, temp1, temp2);
                i--;j--;
            }
        }

    }
}