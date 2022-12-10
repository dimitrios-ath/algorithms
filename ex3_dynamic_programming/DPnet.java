/**
 *  Name:    Dimitrios Athanasiadis
 *  AEM:     3724
 *  Email:   dimitrioca@csd.auth.gr
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class DPnet {
    static int n, m;
    static int[][] process_costs, communication_costs;

    // find minimum of 1d-array
    public static int min(int[] array) {
        int min = array[0]; // set minimum to the first element of array
        for (int num : array)
            if (num < min)
                min = num;
        return min;
    }

    // print 2d-array in specified format
    public static void printArray(int[][] array) {
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                System.out.print(array[i][j]);
                if (j!=m-1)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    // dynamic programming algorithm
    public static int[][] dynamicProgramming(int[][] costs, int row) {
        if (row == 0) // only for first row
            for (int i=0; i<m; i++)
                costs[0][i] = process_costs[0][i];
        else {
            int[][] row_costs = new int[m][m];
            for (int i=0; i<m; i++)
                for (int j=0; j<m; j++) {
                    row_costs[i][j] = costs[row-1][j] + communication_costs[j][i] + process_costs[row][i]; // total cost per process per vm
                    costs[row][i] = min(row_costs[i]); // calculate minimum cost for vm
                }
        }
        return costs; // return updated costs
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            n = Integer.parseInt(br.readLine());
            m = Integer.parseInt(br.readLine());
            br.readLine();

            process_costs = new int[n][m];
            communication_costs = new int[m][m];
            
            // read process costs per vm
            for (int i=0; i<n; i++) {
                String line = br.readLine();
                String[] splitted = line.split(" ");
                for (int j=0; j<m; j++) {
                    process_costs[i][j] = Integer.parseInt(splitted[j]);
                }
            }

            br.readLine();

            // read vm communication costs
            for (int i=0; i<m; i++) {
                String line = br.readLine();
                String[] splitted = line.split(" ");
                for (int j=0; j<m; j++) {
                    communication_costs[i][j] = Integer.parseInt(splitted[j]);
                }
            }

            int[][] costs = new int[n][m];

            // run dynamic programming algorithm (one step per iteration)
            for (int current_row=0; current_row<n; current_row++)
                costs = dynamicProgramming(costs, current_row);
            
            // print results
            printArray(costs);
        } catch (IOException ignored) { System.exit(-1);}
    }
}