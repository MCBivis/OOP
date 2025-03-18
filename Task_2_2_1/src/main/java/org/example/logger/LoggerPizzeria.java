package org.example.logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.*;
import org.apache.logging.log4j.*;

import java.util.concurrent.CountDownLatch;

/**
 * Класс, представляющий пиццерию, которая обрабатывает заказы.
 * Пиццерия включает пекарей, курьеров, склад и очередь заказов.
 */
public class LoggerPizzeria implements PizzeriaInterface {

    private final OrderQueue orderQueue;
    private final Storage storage;
    private final List<LoggerBaker> bakers = new ArrayList<>();
    private final List<LoggerCourier> couriers = new ArrayList<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);
    private final CountDownLatch startLatch;
    private static final Logger logger = LogManager.getLogger(LoggerPizzeria.class);

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public LoggerPizzeria(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        this.storage = new Storage(config.storageCapacity, startLatch, isOpen);
        this.orderQueue = new OrderQueue(startLatch, isOpen);

        for (int speed : config.bakers) {
            bakers.add(new LoggerBaker(orderQueue, storage, speed, isOpen));
        }

        for (int capacity : config.couriers) {
            couriers.add(new LoggerCourier(storage, capacity, isOpen));
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

        logger.info("Пиццерия готова к приёму заказов!");
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
        bakers.forEach(LoggerBaker::joinSafely);
        couriers.forEach(LoggerCourier::joinSafely);
        logger.info("Пиццерия завершила работу.");
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
            logger.info(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
    }
}
