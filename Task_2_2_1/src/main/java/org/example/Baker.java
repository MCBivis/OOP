package org.example;

import java.util.concurrent.atomic.*;

public class Baker extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final int speed;
    private final AtomicBoolean isOpen;

    public Baker(OrderQueue orderQueue, Storage storage, int speed, AtomicBoolean isOpen) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.speed = speed;
        this.isOpen = isOpen;
    }

    @Override
    public void run() {
        while (isOpen.get() || !orderQueue.isEmpty()) {
            Integer orderId = orderQueue.takeOrder();
            if (orderId == null) continue;

            System.out.println(orderId + " Пекарь " + id + " начал готовить.");
            try {
                Thread.sleep(speed * 1000L);
            } catch (InterruptedException ignored) {}

            System.out.println(orderId + " Пекарь " + id + " закончил готовить.");
            storage.storePizza(orderId);
        }
    }

    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }
}
