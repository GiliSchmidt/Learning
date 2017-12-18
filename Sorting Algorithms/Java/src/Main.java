
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
 */
/**
 *
 * @author Giliardi Schmidt
 */
public class Main {

    /**
     *
     * @param args0 Sorting algorithm: -q = QuickSort, -qt = QuickSortThreads,
     * -m = MergeSort, -h = HeapSort or -all = All
     *
     * @param args1 Array with non repeated numbers = -nr, with repeated = -r
     *
     * @param args2 Size of the array (must be an int)
     */
    public static void main(String[] args) {
        int[] array = null;
        int size = 0;
        if (args == null) {
            System.out.println("Invalid parameters");
            return;
        }

        if (args.length != 3) {
            System.out.println("Invalid parameters");
            return;
        }

        try {
            size = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Incorrect size of the array to be ordered informed!");
            return;
        }

        switch (args[1]) {
            case "-r":
                array = generateRandomNumbers(size);
                break;
            case "-nr":
                array = generateNotRepeated(size);
                break;
            default:
                System.out.println("Invalid array type informed!");
                return;
        }

        switch (args[0]) {
            case "-all":
                sortAll(new Sort[]{new QuickSort(), new QuickSortThreads(), new MergeSort(), new HeapSort()}, array);
                break;
            case "-q":
                sortOne(new QuickSort(), array);
                break;
            case "-h":
                sortOne(new HeapSort(), array);
                break;
            case "-qt":
                sortOne(new QuickSortThreads(), array);
                break;
            case "-m":
                sortOne(new MergeSort(), array);
                break;
            default:
                System.out.println("Invalid algorithm informed!");
                return;
        }
    }

    public static void sortOne(Sort algorithm, int[] array) {
        System.out.println("Sorting " + array.length + "  numbers with " + algorithm.getClass().getName() + ":");
        System.out.println("------------------------------------------------");
        System.out.println("");

        for (int i = 1; i <= 5; i++) {
            int[] copy = array;

            long start = System.nanoTime();
            algorithm.sort(copy);
            long totalTime = System.nanoTime() - start;
            long totalTimeMili = totalTime / 1000000;

            System.out.println("Run :" + i + " \tTime taken: " + totalTimeMili + " ms" + " \tor " + totalTime + " ns");
            System.out.println("");
        }
    }

    public static void sortAll(Sort[] algorithms, int[] array) {
        for (Sort sort : algorithms) {
            sortOne(sort, array);
        }
    }

    /**
     *
     * Generates an array containing non repeated numbers
     *
     * @param size Size of the Array
     * @return Array with numbers
     */
    public static int[] generateRandomNumbers(int size) {
        Random r = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = r.nextInt(Integer.MAX_VALUE);
        }

        return array;
    }

    /**
     * Generates an array containing non repeated numbers
     *
     * @param size Size of the Array
     * @return Array with numbers
     */
    public static int[] generateNotRepeated(int size) {
        Random r = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        Collections.shuffle(Arrays.asList(array));
        System.out.println(Arrays.toString(array));
        return array;
    }
}
