package org.example.interfaces_abstractClasses;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Config;
import org.example.OrderQueue;
import org.example.Storage;

import java.util.concurrent.CountDownLatch;

/**
 * Класс, представляющий пиццерию, которая обрабатывает заказы.
 * Пиццерия включает пекарей, курьеров, склад и очередь заказов.
 */
public abstract class Pizzeria implements PizzeriaInterface{

    protected final OrderQueue orderQueue;
    protected final Storage storage;
    protected final List<Worker> bakers = new ArrayList<>();
    protected final List<Worker> couriers = new ArrayList<>();
    protected final AtomicBoolean isOpen = new AtomicBoolean(true);
    protected final CountDownLatch startLatch;
    protected final Config config;

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public Pizzeria(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.config = objectMapper.readValue(new File(configPath), Config.class);

        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        this.storage = new Storage(config.storageCapacity, startLatch, isOpen);
        this.orderQueue = new OrderQueue(startLatch, isOpen);
    }

    /**
     * Запускает пиццерию, начиная работу пекарей и курьеров.
     */
    public void start() {
        couriers.forEach(Worker::start);
        bakers.forEach(Worker::start);

        try {
            startLatch.await(); // Ждём, пока все потоки перейдут в WAITING
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        print("Пиццерия готова к приёму заказов!");
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
            print(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
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
        bakers.forEach(Worker::joinSafely);
        couriers.forEach(Worker::joinSafely);
        print("Пиццерия завершила работу.");
    }

    protected abstract void print(String text);
}