package org.example;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Класс для управления хранилищем пицц на складе.
 */
public class Storage {
    private final int capacity;
    private final Queue<Integer> storage = new LinkedList<>();
    private final CountDownLatch startLatch;

    /**
     * Конструктор для создания хранилища с заданной ёмкостью.
     *
     * @param capacity Максимальная ёмкость склада.
     */
    public Storage(int capacity, CountDownLatch startLatch) {
        this.capacity = capacity;
        this.startLatch = startLatch;
    }

    /**
     * Сохраняет пиццу на складе. Если склад полон, поток блокируется, пока не освободится место.
     *
     * @param orderId Идентификатор заказа, который будет помещён на склад.
     */
    public synchronized void storePizza(int orderId) {
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        storage.add(orderId);
        notifyAll();
    }

    /**
     * Извлекает пиццы из склада. Если склад пуст, поток блокируется до появления новых пицц.
     *
     * @param maxCount Максимальное количество пицц, которое можно забрать за один раз.
     * @return Список заказов, который был забран с склада.
     */
    public synchronized List<Integer> takePizzas(int maxCount) {
        while (storage.isEmpty()) {
            try {
                startLatch.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }

        List<Integer> taken = new ArrayList<>();
        while (!storage.isEmpty() && taken.size() < maxCount) {
            taken.add(storage.poll());
        }
        notifyAll();
        return taken;
    }

    /**
     * Проверяет, пуст ли склад.
     *
     * @return true, если склад пуст, иначе false.
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}