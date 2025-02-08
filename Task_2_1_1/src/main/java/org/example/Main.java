package org.example;

import java.util.Arrays;

/**
 * Главный класс для тестирования времени всех трех вариантов проверки.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int[] testNumbersLarge = new int[1000000];
        Arrays.fill(testNumbersLarge, 1046527);
        int threads = Runtime.getRuntime().availableProcessors();

        PrimeCheckerBase sequentialChecker = new Sequential();
        PrimeCheckerBase parallelThreadChecker = new ParallelThread(threads);
        PrimeCheckerBase parallelStreamChecker = new ParallelStream();

        long start, end;

        System.out.println("Sequential:");
        start = System.nanoTime();
        System.out.println(sequentialChecker.hasNonPrime(testNumbersLarge));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6 + " ms");

        System.out.println("Parallel Threads:");
        start = System.nanoTime();
        System.out.println(parallelThreadChecker.hasNonPrime(testNumbersLarge));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6 + " ms");

        System.out.println("Parallel Stream:");
        start = System.nanoTime();
        System.out.println(parallelStreamChecker.hasNonPrime(testNumbersLarge));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6 + " ms");
    }
}