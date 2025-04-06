package org.example;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс для управления очередью заказов в пиццерии.
 */
public class OrderQueue {
    private final Queue<Integer> orders = new LinkedList<>();
    private final CountDownLatch startLatch;
    private final AtomicBoolean isOpen;

    public OrderQueue(CountDownLatch startLatch, AtomicBoolean isOpen) {
        this.startLatch = startLatch;
        this.isOpen = isOpen;
    }
    /**
     * Добавляет заказ в очередь.
     * При добавлении уведомляются все потоки, ожидающие на этой очереди.
     *
     * @param orderId Идентификатор заказа.
     */
    public synchronized void addOrder(int orderId) {
        orders.add(orderId);
        notifyAll();
    }

    /**
     * Извлекает заказ из очереди. Если очередь пуста, поток блокируется до появления нового заказа.
     *
     * @return Идентификатор заказа или null, если очередь пуста.
     */
    public synchronized Integer takeOrder() {
        while (orders.isEmpty() && isOpen.get()) {
            try {
                startLatch.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }
        return orders.poll();
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true, если очередь пуста, иначе false.
     */
    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}