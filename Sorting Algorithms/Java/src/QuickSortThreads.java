
import java.util.Random;

/**
 *
 * @author Giliardi Schmidt
 */
public class QuickSortThreads implements Sort {

    @Override
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int left, int right) {
        if (left < right) {
            int p = partition(array, left, right);
            new Thread(() -> sort(array, left, p - 1));
            new Thread(() -> sort(array, p + 1, right));

        }
    }

    private int partition(int[] array, int left, int right) {
        int p = new Random().ints(1, left, right + 1).findFirst().getAsInt();
        int i = left;

        change(array, right, p);
        p = array[right];

        for (int j = left; j < right; j++) {
            if (array[j] <= p) {
                change(array, j, i);
                i++;
            }
        }

        if (array[right] < array[i]) {
            change(array, right, i);
        }

        return i;
    }

    private void change(int[] array, int a, int b) {
        int aux = array[a];
        array[a] = array[b];
        array[b] = aux;
    }

}
