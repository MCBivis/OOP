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
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
        int chunkSize = (numbers.length / threadCount) + 1;

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);
            completionService.submit(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) return true;
                }
                return false;
            });
        }

        executor.shutdown();
        for (int i = 0; i < threadCount; i++) {
            if (completionService.take().get()) { // Ждем завершения задачи
                executor.shutdownNow(); // Прерываем все потоки
                return true;
            }
        }
        return false;
    }
}
