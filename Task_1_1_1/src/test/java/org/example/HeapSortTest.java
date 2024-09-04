package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void heapsort() {
        int[] arr= {5,4,3,2,1};
        int[] arr2= {0};
        int[] arr3= {};
        HeapSort.heapsort(arr);
        HeapSort.heapsort(arr2);
        HeapSort.heapsort(arr3);
        int[] expectarr = {1,2,3,4,5};
        assertArrayEquals(arr,expectarr);
        int[] expectarr2 = {0};
        assertArrayEquals(arr2,expectarr2);
        int[] expectarr3 = {};
        assertArrayEquals(arr3,expectarr3);
    }

}