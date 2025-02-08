package org.example;

import java.util.concurrent.*;

/**
 * Параллельная проверка массива чисел с использованием потоков (Threads).
 */
class ParallelThread extends PrimeCheckerBase {
    private final int threadCount;

    /**
     * Конструктор с заданием количества потоков.
     * @param threadCount количество потоков
     */
    public ParallelThread(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public boolean hasNonPrime(int[] numbers) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = (numbers.length / threadCount) + 1;
        Future<Boolean>[] futures = new Future[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);
            futures[i] = executor.submit(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) return true;
                }
                return false;
            });
        }

        executor.shutdown();
        for (Future<Boolean> future : futures) {
            if (future.get()) return true;
        }
        return false;
    }
}
