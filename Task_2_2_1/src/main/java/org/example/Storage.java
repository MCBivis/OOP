package org.example;

import java.util.*;

public class Storage {
    private final int capacity;
    private final Queue<Integer> storage = new LinkedList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void storePizza(int orderId) {
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        storage.add(orderId);
        System.out.println(orderId + " Пицца на складе.");
        notifyAll();
    }

    public synchronized List<Integer> takePizzas(int maxCount) {
        while (storage.isEmpty()) {
            try {
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

    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}