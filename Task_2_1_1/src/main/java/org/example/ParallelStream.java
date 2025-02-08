package org.example;

import java.util.Arrays;
import java.util.List;

/**
 * Параллельная проверка массива чисел с использованием parallelStream().
 */
public class ParallelStream extends PrimeCheckerBase {
    @Override
    public boolean hasNonPrime(int[] numbers) {
        List<Integer> numberList = Arrays.stream(numbers).boxed().toList();
        return numberList.parallelStream().anyMatch(number -> !isPrime(number));
    }
}
