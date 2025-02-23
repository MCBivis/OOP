package org.example;

import java.util.*;
import java.util.concurrent.atomic.*;

public class Courier extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final Storage storage;
    private final int capacity;
    private final AtomicBoolean isOpen;

    public Courier(Storage storage, int capacity, AtomicBoolean isOpen) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.storage = storage;
        this.capacity = capacity;
        this.isOpen = isOpen;
    }

    @Override
    public void run() {
        while (isOpen.get() || !storage.isEmpty()) {
            List<Integer> pizzas = storage.takePizzas(capacity);
            if (pizzas.isEmpty()) continue;

            System.out.println("Курьер " + id + " забрал " + pizzas);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            System.out.println("Курьер " + id + " доставил " + pizzas);
        }
    }

    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }
}