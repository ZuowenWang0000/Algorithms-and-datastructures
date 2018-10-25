import java.util.LinkedList;
import java.util.Scanner;

public class testLinkedLIst{
    public static void main(String[] args) {
        @SuppressWarnings("unchecked") LinkedList<String[]> array[] = new LinkedList[10];
        for(int i = 0; i < 10; i++){
            
            array[i] = new LinkedList<String[]>();
            String[] st = new String[2];
            st[0] = "aaa";
            st[1] = "bbb";

            String[] st2 = new String[2];
            st2[0] = "ccc";
            st2[1] = "ddd";

            String[] st3 = new String[2];
            st3[0] = "eee";
            st3[1] = "fff";
            array[i].add(st);
            array[i].add(st2);
            array[i].add(st3);
        }

        // for(int i = 0; i < 10; i ++){
        //     System.out.println(array[i].getFirst()[0]);
        //     String a = "a";
        //     a = a + "b";
        //     System.out.println(a);
        // }

        // for (String[] var : array[0]) {
        //     System.out.println(var[0]);
        //     System.out.println(var[1]);           
        // }

        System.out.println(array[0].get(0)[0]);
        System.out.println(array[0].get(0)[1]); 

        System.out.println(array[0].get(1)[0]);
        System.out.println(array[0].get(1)[1]); 

        System.out.println(array[0].get(2)[0]);
        System.out.println(array[0].get(2)[1]); 


        //System.out.println("_" + "aaa");
    
    }

}