package org.example;

import java.util.*;

/**
 * Класс для управления очередью заказов в пиццерии.
 */
public class OrderQueue {
    private final Queue<Integer> orders = new LinkedList<>();

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
        while (orders.isEmpty()) {
            try {
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