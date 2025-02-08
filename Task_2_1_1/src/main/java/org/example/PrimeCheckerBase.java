package org.example;

import java.util.concurrent.*;

/**
 * Абстрактный класс для проверки чисел на простоту.
 */
abstract class PrimeCheckerBase {
    /**
     * Проверяет, является ли число простым.
     * @param n число для проверки
     * @return true, если число простое, иначе false
     */
    protected boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число.
     * @param numbers массив чисел
     * @return true, если есть составное число, иначе false
     * @throws InterruptedException если выполнение потока прерывается
     * @throws ExecutionException если возникает ошибка во время выполнения
     */
    public abstract boolean hasNonPrime(int[] numbers) throws InterruptedException, ExecutionException;
}
