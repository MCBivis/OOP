package org.example;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;

class Pizzeria {
    private final OrderQueue orderQueue = new OrderQueue();
    private final Storage storage;
    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);

    public Pizzeria(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        this.storage = new Storage(config.storageCapacity);

        for (int speed : config.bakers) {
            bakers.add(new Baker(orderQueue, storage, speed, isOpen));
        }

        for (int capacity : config.couriers) {
            couriers.add(new Courier(storage, capacity, isOpen));
        }
    }

    public void start() {
        bakers.forEach(Thread::start);
        couriers.forEach(Thread::start);
    }

    public void stop() {
        isOpen.set(false);
        while (!orderQueue.isEmpty() || !storage.isEmpty()) {
            // Ждем обработки всех заказов
        }
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);
        System.out.println("Пиццерия завершила работу.");
    }

    public void acceptOrder(int orderId) {
        if (isOpen.get()) {
            orderQueue.addOrder(orderId);
        } else {
            System.out.println(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
    }

}