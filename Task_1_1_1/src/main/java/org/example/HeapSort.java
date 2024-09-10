package org.example;

/**
 * Класс для реализации алгоритма пирамидальной сортировки (Heap Sort).
 */
public class HeapSort {

    /**
     * Обмен местами элементов массива.
     *
     * @param arr массив
     * @param i индекс первого элемента
     * @param j индекс второго элемента
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Пирамидальная сортировка.
     *
     * @param arr массив для сортировки
     */
    public static void heapSort(int[] arr) {
        int len = arr.length;
        if (heapInit(arr, len) == 0) {
            return;
        }
        swap(arr, 0, --len);
        while (len > 0) {
            heapify(arr, 0, len);
            swap(arr, 0, --len);
        }
    }

    /**
     * Инициализация пирамиды.
     *
     * @param arr массив
     * @param len текущая длина массива
     * @return 1 если пирамидальная сортировка возможна, 0 если нет
     */
    public static int heapInit(int[] arr, int len) {
        if (len < 2) {
            return 0;
        }
        for (int i = (len / 2) - 1; i >= 0; i--) {
            heapify(arr, i, len);
        }
        return 1;
    }

    /**
     * Приведение массива к пирамидальному виду.
     *
     * @param arr массив
     * @param root корень
     * @param len текущая длина массива
     */
    public static void heapify(int[] arr, int root, int len) {
        if (root < len / 2) {
            int right = (root + 1) * 2;
            int left = right - 1;
            if (left < len && arr[left] > arr[root]) {
                swap(arr, left, root);
                heapify(arr, left, len);
            }
            if (right < len && arr[right] > arr[root]) {
                swap(arr, right, root);
                heapify(arr, right, len);
            }
        }
    }
}