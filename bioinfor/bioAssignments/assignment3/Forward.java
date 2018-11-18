import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays; 
import java.io.IOException;
import java.io.File;
import java.text.DecimalFormat;



public class Forward{
        static int numStates;
        static float[] iniProb;
        static float[][] transitProb;
        static float[][] emiProb;
        static int numEmiSym;
        static char[] emiSym;
        static List emiSymList;


    public static void main(String[] args) throws Exception{
        // File f = new File("./hmm5.in");
        Scanner sc = new Scanner(System.in);
        //numStates = number of possible states. for instance fair/unfiar, exon/intron
        numStates = sc.nextInt();
        //iniProb = initial probability to enter state i (pi(i)), thus iniProb.length == numStates 
        iniProb = new float[numStates];
        //transitProb = transition probability, from one state to another(can stay in itself)
        transitProb = new float[numStates][numStates];

        for(int i = 0; i < numStates; i++){
            iniProb[i] = sc.nextFloat();
        }
        for(int i = 0; i < numStates; i++){
            for(int j = 0; j < numStates; j++){
                transitProb[i][j] = sc.nextFloat();
            }
        }
        //numEmiSym = number of emitting symbol, for instance 4 for ACGT, 2 for head/tail
        numEmiSym = sc.nextInt();
        //emission symbol, namely ACGT
        emiSym = new char[numEmiSym];
        //emission probability, probabilities for each emission symbol(after the state is fixed)
        emiProb = new float[numStates][numEmiSym];
        //the sequence we want to check (in forward algorithm, we do inference)
        String querySequence;
        for(int i = 0; i < numEmiSym; i++){
            emiSym[i] = sc.next().charAt(0);
        }
        for(int i = 0; i < numStates; i++){
            for(int j = 0; j < numEmiSym; j++){
                emiProb[i][j] = sc.nextFloat();
            }
        }
        querySequence = sc.next();

        //Finished Reading

        //Now start the DP part of forward algorithm
        //First we create a DP table which has querySequence.length rows and numStates columns
        emiSymList = new ArrayList();
        for(char c : emiSym) {
            emiSymList.add(c);
        }
        int m = querySequence.length();
        float[][] dp = new float[m][numStates];
        //initialize the DP table
        for(int i = 0; i < numStates; i++){
            dp[0][i] = iniProb[i]*emiProb[i][emiSymList.indexOf(querySequence.charAt(0))];
        }
        for(int i = 1; i < m; i++){
            for(int j = 0; j < numStates; j++){
                dp[i][j] = 0;
            }
        }

        //fill the table
        for(int i = 1; i < m; i++){
            for(int j = 0; j < numStates; j++){
                for(int k = 0; k < numStates; k++){
                    dp[i][j] = dp[i][j] + dp[i-1][k]
                                        *emiProb[j][emiSymList.indexOf(querySequence.charAt(i))]
                                        *transitProb[k][j];
                }
            }
        }

        float result = 0;
        for(int i = 0; i < numStates; i ++){
            result += dp[m - 1][i];
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(10);
        System.out.println(df.format(result));

        sc.close();
    }
}
