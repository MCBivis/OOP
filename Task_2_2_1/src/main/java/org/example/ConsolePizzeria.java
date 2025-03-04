package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CountDownLatch;

/**
 * Класс, представляющий пиццерию, которая обрабатывает заказы.
 * Пиццерия включает пекарей, курьеров, склад и очередь заказов.
 */
public class ConsolePizzeria implements PizzeriaInterface{

    private final OrderQueue orderQueue;
    private final Storage storage;
    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);
    private final CountDownLatch startLatch;

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public ConsolePizzeria(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        this.storage = new Storage(config.storageCapacity, startLatch);
        this.orderQueue = new OrderQueue(startLatch, isOpen);

        for (int speed : config.bakers) {
            bakers.add(new Baker(orderQueue, storage, speed, isOpen, startLatch));
        }

        for (int capacity : config.couriers) {
            couriers.add(new Courier(storage, capacity, isOpen, startLatch));
        }
    }

    /**
     * Запускает пиццерию, начиная работу пекарей и курьеров.
     */
    public void start() {
        couriers.forEach(Thread::start);
        bakers.forEach(Thread::start);

        try {
            startLatch.await(); // Ждём, пока все потоки перейдут в WAITING
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Пиццерия готова к приёму заказов!");
    }

    /**
     * Останавливает пиццерию, завершая работу пекарей и курьеров после обработки всех заказов.
     */
    public void stop() {
        isOpen.set(false);
        synchronized (storage) {
            while (!orderQueue.isEmpty() || !storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);
        System.out.println("Пиццерия завершила работу.");
    }

    /**
     * Принимает новый заказ и добавляет его в очередь, если пиццерия открыта.
     *
     * @param orderId Идентификатор заказа
     */
    public void acceptOrder(int orderId) {
        if (isOpen.get()) {
            orderQueue.addOrder(orderId);
        } else {
            System.out.println(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
    }
}
