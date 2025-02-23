package org.example;

import java.util.*;

public class OrderQueue {
    private final Queue<Integer> orders = new LinkedList<>();

    public synchronized void addOrder(int orderId) {
        orders.add(orderId);
        notifyAll();
    }

    public synchronized Integer takeOrder() {
        while (orders.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        return orders.poll();
    }

    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}