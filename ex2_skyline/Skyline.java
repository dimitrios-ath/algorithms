/**
 *  Name:    Dimitrios Athanasiadis
 *  AEM:     3724
 *  Email:   dimitrioca@csd.auth.gr
 * 
 *  based on divide and conquer algorithm of https://datalab-old.csd.auth.gr/~apostol/pubs/iisa2015_tpm.pdf
 */

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

class Skyline {

    // print given array
    public static void printArrayList(ArrayList<int[]> array) {
        for (int i=0; i<array.size(); i++)
            System.out.println(array.get(i)[0]+" "+array.get(i)[1]);
    }

    // remove dublicate points of given array
    public static ArrayList<int[]> removeDuplicates(ArrayList<int[]> array)
    {
        ArrayList<int[]> newArray = new ArrayList<int[]>();
        for (int[] item : array)
            if (!newArray.contains(item))
                newArray.add(item);
        return newArray;
    }

    // find skyline of given array
    public static ArrayList<int[]> findSkyline(ArrayList<int[]> array) {
        int size = array.size();
        for (int i=0; i<size; i++)
            for (int j=0; j<size; j++)
                if (i!=j && array.get(i)[0] <= array.get(j)[0] && array.get(i)[1] <= array.get(j)[1]) { // point i dominates j
                    array.remove(j);
                    size--;
                    i=j=0;
                }
        return array;
    }

    // recurcive divide and conquer algorithm
    public static ArrayList<int[]> divideNConquer(ArrayList<int[]> points) {
        double medianX = 0;
        double medianY = 0;
        
        for (int i=0; i<points.size(); i++) {
            medianX += points.get(i)[0];
            medianY += points.get(i)[1];
        }
        
        medianX /= (double) points.size();
        medianY /= (double) points.size();

        ArrayList<int[]> P11 = new ArrayList<>();
        ArrayList<int[]> P12 = new ArrayList<>();
        ArrayList<int[]> P21 = new ArrayList<>();
        ArrayList<int[]> P22 = new ArrayList<>();

        // add every point to the appropriate partition
        for (int i=0; i<points.size(); i++) {
            if (points.get(i)[0] < medianX) { // belongs to {P11, P12}
                if (points.get(i)[1] < medianY) // belongs to {P11}
                    P11.add(points.get(i));
                else // belongs to {P12}
                    P12.add(points.get(i));
            }
            else { // belongs to {P21, P22}
                if (points.get(i)[1] < medianY) // belongs to {P21}
                    P21.add(points.get(i));
                else // belongs to {P22}
                    P22.add(points.get(i));
            }
        }

        // if partitions are big enough, recursively divide them
        if (P11.size()>50)
            P11 = divideNConquer(P11);
        if (P12.size()>50)
            P12 = divideNConquer(P12);
        if (P21.size()>50)
            P21 = divideNConquer(P21);
        if (P22.size()>50)
            P22 = divideNConquer(P22);

        // find skylines of each partition and discard P22
        P11 = findSkyline(P11);
        P12 = findSkyline(P12);
        P21 = findSkyline(P21);
        P22 = null;

        // compare skyline of P12 with P11
        ArrayList<int[]> P12_P11 = new ArrayList<>();
        P12_P11.addAll(P12);
        P12_P11.addAll(P11);
        P12_P11 = findSkyline(P12_P11);
        
        // compare skyline of P21 with P11
        ArrayList<int[]> P21_P11 = new ArrayList<>();
        P21_P11.addAll(P21);
        P21_P11.addAll(P11);
        P21_P11 = findSkyline(P21_P11);
        
        ArrayList<int[]> finalSkyline = new ArrayList<>();
        finalSkyline.addAll(P11);
        finalSkyline.addAll(P12_P11);
        finalSkyline.addAll(P21_P11);

        return removeDuplicates(finalSkyline);
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int n = Integer.parseInt(br.readLine());

            ArrayList<int[]> points = new ArrayList<>();
            
            // read points from file
            for (int i=0; i<n; i++) {
                try {
                    String line = br.readLine();
                    String[] splitted = line.split(" ");
                    points.add(new int[] {Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])});
                } catch (IOException ignored) {}
            }
            
            // run divide and conquer algorithm
            ArrayList<int[]> finalSkyline = divideNConquer(points);

            // sort in ascending order based on x value 
            Collections.sort(finalSkyline, new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return b[b.length-1]-a[a.length-1];
                }
            });
            
            // print results
            printArrayList(finalSkyline);
        } catch (IOException ignored) { System.exit(-1);}
        
    }
}