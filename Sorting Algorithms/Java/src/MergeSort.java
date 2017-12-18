
/**
 *
 * @author Giliardi Schmidt
 */
public class MergeSort implements Sort{

    @Override
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     *
     * @param array to be ordered
     * @param left initial position
     * @param right last position
     */
    private void sort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            sort(array, left, middle);
            sort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        int initLeft = left;
        int initMiddle = middle + 1;
        int initLeft2 = left;

        int[] aux = array.clone();

        while (initLeft <= middle && initMiddle <= right) {
            if (aux[initLeft] > aux[initMiddle]) {
                array[initLeft2] = aux[initMiddle];
                initMiddle++;
            } else {
                array[initLeft2] = aux[initLeft];
                initLeft++;
            }
            initLeft2++;
        }

        while (initLeft <= middle) {
            array[initLeft2] = aux[initLeft];
            initLeft++;
            initLeft2++;
        }

        while (initMiddle <= right) {
            array[initLeft2] = aux[initMiddle];
            initMiddle++;
            initLeft2++;
        }
    }
}
