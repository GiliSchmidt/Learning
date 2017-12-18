
/**
 *
 * @author Giliardi Schmidt
 */
public class HeapSort implements Sort{

    @Override
    public void sort(int[] array) {
        heapfy(array);
        heapSort(array);
    }

    private void heapfy(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int n = i;
            while (n > 0) {
                int pivot = (n - 1) / 2;
                if (array[n] > array[pivot]) {
                    change(array, n, pivot);
                    n = pivot;
                } else {
                    break;
                }
            }
        }
    }

    private void heapSort(int[] array) {
        for (int i = array.length; i > 0;) {
            change(array, 0, --i);
            rollDown(array, i);
        }
    }

    private void rollDown(int[] array, int i) {
        int n = 0;
        while (true) {
            int father = (n * 2) + 1; 

            if (father >= i) {
                break;
            }

            int secondPos = father + 1; 

            if (secondPos >= i) {
                if (array[father] > array[n]) {
                    change(array, father, n);
                }
                break;
            } else if (array[father] > array[n]) {
                if (array[father] > array[secondPos]) {
                    change(array, father, n);
                    n = father;
                } else {
                    change(array, secondPos, n);
                    n = secondPos;
                }
            } else {
                if (array[secondPos] > array[n]) {
                    change(array, secondPos, n);
                    n = secondPos;
                } else {
                    break;
                }
            }
        }
    }

    private void change(int[] array, int a, int b) {
        int aux = array[a];
        array[a] = array[b];
        array[b] = aux;
    }
}
