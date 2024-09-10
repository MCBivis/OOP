package org.example;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int length = 5000; length<1000001; length=length*5) {
            int[] nums = new int[length];
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                nums[i] = random.nextInt(100);
            }
            long startTime = System.currentTimeMillis();
            nums = HeapSort.heapSort(nums);
            long endTime = System.currentTimeMillis();
            System.out.println("Для массива из " + length + " элементов, время сортировки: " + (endTime - startTime));
        }
    }
}
