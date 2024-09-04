package org.example;


public class Main {
    public static void main(String[] args) {
        int[] arr= {5,4,3,2,1,6,7,8,1,1,123,3,4,5,6,7,123,22,3,4,5,0,1,2,-2,-3,-4,-5};
        int[] arr2= {0};
        int[] arr3= {};
        HeapSort.heapsort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        HeapSort.heapsort(arr2);
        for (int i = 0; i < arr2.length; i++) {
            System.out.print(i + "," + arr2[i]);
        }
        HeapSort.heapsort(arr3);
        for (int i = 0; i < arr3.length; i++) {
            System.out.print(i + "," + arr2[i]);
        }
        System.out.println(15%10);
    }
}