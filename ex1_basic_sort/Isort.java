import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Isort{

    public static ArrayList<Integer> DeepCopy(ArrayList<Integer> list) {
        ArrayList<Integer> array = new ArrayList<Integer>(list.size());
        for (Integer num : list) {
            array.add(num);
        }
        return array;
    }

    public static ArrayList<Integer> BubbleSort(ArrayList<Integer> unsorted) {
        ArrayList<Integer> sorted = DeepCopy(unsorted);

        for (int j=sorted.size(); j >= 0; j--) {
            for (int i=0; i<sorted.size()-1; i++) {
                if (sorted.get(i)>sorted.get(i+1)) {
                    Integer tmp = sorted.get(i);
                    sorted.set(i, sorted.get(i+1));
                    sorted.set(i+1, tmp);
                }
            }
        }

        return sorted;
    }

    public static void main(String[] args) {
        try {
            File input = new File(args[0]);
            Scanner scanner = new Scanner(input);
            ArrayList<Integer> unsorted = new ArrayList<Integer>();
            
            // Add numbers to ArrayList
            while (scanner.hasNextInt()) { unsorted.add(scanner.nextInt()); }
            // Sort the numbers
            ArrayList<Integer> sorted = BubbleSort(unsorted);
            // Print the result
            for(Integer num : sorted) {System.out.println(num); };
            
            scanner.close();
          } catch (FileNotFoundException ignored) {}
    }
}