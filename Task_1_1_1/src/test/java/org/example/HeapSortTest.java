package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

  @Test
    void heapsort() {
    int[] arr = {5, 4, 3, 2, 1};
    int[] arr2 = {0};
    int[] arr3 = {};
    HeapSort.heapSort(arr);
    HeapSort.heapSort(arr2);
    HeapSort.heapSort(arr3);
    int[] expectArr = {1, 2, 3, 4, 5};
    assertArrayEquals(arr, expectArr);
    int[] expectArr2 = {0};
    assertArrayEquals(arr2, expectArr2);
    int[] expectArr3 = {};
    assertArrayEquals(arr3, expectArr3);
  }

}