import java.util.ArrayList;
import java.util.Scanner;


public class P2Q1 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numOfItems = Integer.parseInt(scan.nextLine());
        String[] limits = scan.nextLine().split(" ");
        int W1 = Integer.parseInt(limits[0]);
        int W2 = Integer.parseInt(limits[1]);
        String[] weightStrings = scan.nextLine().split(" ");
        String[] V1Strings = scan.nextLine().split(" ");
        String[] V2Strings = scan.nextLine().split(" ");
        int[] weights = new int[numOfItems];
        int[] V1 = new int[numOfItems];
        int[] V2 = new int[numOfItems];

        for(int i = 0; i < numOfItems; i++){
            weights[i] = Integer.parseInt(weightStrings[i]);
            V1[i] = Integer.parseInt(V1Strings[i]);
            V2[i] = Integer.parseInt(V2Strings[i]);
        }

        int[][][] M = new int[numOfItems][W1+1][W2+1];
        for(int i = 0; i < numOfItems; i++){
            for(int w1 = 0; w1 <= W1; w1++){
                for(int w2 = 0; w2 <= W2; w2++){
                    if(i == 0){
                        if(weights[i] > w1 && weights[i] > w2){
                            M[i][w1][w2] = 0;
                        }
                        else if(weights[i] > w1){
                            M[i][w1][w2] = V2[i];
                        }
                        else if(weights[i] > w2){
                            M[i][w1][w2] = V1[i];
                        }
                        else{
                            M[i][w1][w2] = Math.max(V1[i], V2[i]);
                        }
                    }
                    else if(weights[i] > w1 && weights[i] > w2){
                        M[i][w1][w2] = M[i-1][w1][w2];
                    }
                    else if(weights[i] > w1){
                        M[i][w1][w2] = Math.max(M[i-1][w1][w2], V2[i] + M[i-1][w1][w2 - weights[i]]);
                    }
                    else if(weights[i] > w2){
                        M[i][w1][w2] = Math.max(M[i-1][w1][w2], V1[i] + M[i-1][w1 - weights[i]][w2]);
                    }
                    else{
                        M[i][w1][w2] = Math.max(Math.max(M[i-1][w1][w2],
                                V1[i] + M[i-1][w1 - weights[i]][w2]),
                                V2[i] + M[i-1][w1][w2 - weights[i]]);
                    }
                }
            }
        }

        ArrayList<Integer> bag1 = new ArrayList<Integer>();
        ArrayList<Integer> bag2 = new ArrayList<Integer>();
        int w1 = W1;
        int w2 = W2;
        for(int i = numOfItems - 1; i >= 0 && (w1 > 0 || w2 > 0); i--){
            if(i == 0 && M[i][w1][w2] > 0){
                if(M[i][w1][w2] == V1[i] && weights[i] <= w1){
                    bag1.add(0, i);
                }
                else if(M[i][w1][w2] == V2[i] && weights[i] <= w2){
                    bag2.add(0, i);
                }
                else{
                    //System.out.println("unexpected value for i = 1, value = " + M[i][w1][w2]);
                }
            }
            else if(i > 0){
                int difference = M[i][w1][w2] - M[i - 1][w1][w2];
                if (difference != 0) {
//                    if(difference == V1[i] && weights[i] <= w1 && difference == V2[i] && weights[i] <= w2 &&
//                            w2 < w1){
//                        bag2.add(0, i);
//                        w2 -= weights[i];
//                    }
//                    if (difference == V1[i] && weights[i] <= w1) {
//                        bag1.add(0, i);
//                        w1 -= weights[i];
//                    } else if (difference == V2[i] && weights[i] <= w2) {
//                        bag2.add(0, i);
//                        w2 -= weights[i];
                    if(weights[i] <= w1 && M[i][w1][w2] - M[i-1][w1 - weights[i]][w2] == difference){
                        bag1.add(0, i);
                        w1 -= weights[i];
                    }
                    else if(weights[i] <= w2 && M[i][w1][w2] - M[i-1][w1][w2 - weights[i]] == difference){
                        bag2.add(0, i);
                        w2 -= weights[i];
                    }
                    else {
                        System.out.println("unexpected difference = " + difference);
                    }
                }
            }
        }

        System.out.println(M[numOfItems-1][W1][W2]);
        for(int i = 0; i < bag1.size(); i++){
            if(i == bag1.size() - 1) {
                System.out.print(bag1.get(i) + 1 + "\n");
            }
            else{
                System.out.print(bag1.get(i) + 1 + " ");
            }
        }
        for(int i = 0; i < bag2.size(); i++){
            if(i == bag2.size() - 1) {
                System.out.print(bag2.get(i) + 1 + "\n");
            }
            else{
                System.out.print(bag2.get(i) + 1 + " ");
            }
        }
    }
}
