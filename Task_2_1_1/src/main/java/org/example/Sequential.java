package org.example;

/**
 * Последовательная проверка массива чисел.
 */
class Sequential extends PrimeCheckerBase {
    @Override
    public boolean hasNonPrime(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) return true;
        }
        return false;
    }
}
