package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;

/**
 * Класс тестов для проверки корректности реализации Sequential.
 */
class SequentialTest {
    @Test
    void testSequential() throws ExecutionException, InterruptedException {
        PrimeCheckerBase checker = new Sequential();
        assertTrue(checker.hasNonPrime(new int[]{6, 8, 7, 13, 5, 9, 4}));
        assertFalse(checker.hasNonPrime(new int[]{20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053}));
    }
}