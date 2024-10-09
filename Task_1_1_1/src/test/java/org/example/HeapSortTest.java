package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

    @Test
    void standart() {
        int[] arr = {5, 4, 3, 2, 1};
        arr = HeapSort.heapSort(arr);
        int[] expectArr = {1, 2, 3, 4, 5};
        assertArrayEquals(arr, expectArr);
    }
    @Test
    void oneElem() {
        int[] arr2 = {0};
        arr2 = HeapSort.heapSort(arr2);
        int[] expectArr2 = {0};
        assertArrayEquals(arr2, expectArr2);
    }
    @Test
    void emptyArray() {
        int[] arr3 = {};
        arr3 = HeapSort.heapSort(arr3);
        int[] expectArr3 = {};
        assertArrayEquals(arr3, expectArr3);
    }

}