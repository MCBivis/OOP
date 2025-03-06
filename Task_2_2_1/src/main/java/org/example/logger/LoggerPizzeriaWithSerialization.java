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
public class LoggerPizzeriaWithSerialization implements PizzeriaInterfaceWithSerialization {

    private final OrderQueue orderQueue;
    private final Storage storage;
    private final List<LoggerBaker> bakers = new ArrayList<>();
    private final List<LoggerCourier> couriers = new ArrayList<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);
    private final CountDownLatch startLatch;
    private static final Logger logger = LogManager.getLogger(LoggerPizzeriaWithSerialization.class);

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public LoggerPizzeriaWithSerialization(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        this.storage = new Storage(config.storageCapacity, startLatch);
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
     * Останавливает пиццерию с сериализацией незавершенных заказов в файл.
     * После этого пиццерия записывает незавершенные заказы в файл и завершает работу.
     *
     * @param filename Имя файла, в который будут сохранены незавершенные заказы
     * @throws IOException Если произошла ошибка при записи в файл
     */
    public void stopWithSerialization(String filename) throws IOException {
        isOpen.set(false);
        List<Integer> orders = new LinkedList<>();
        while (!orderQueue.isEmpty()) {
            orders.add(orderQueue.takeOrder());
        }
        synchronized (storage) {
            while (!storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }
        bakers.forEach(LoggerBaker::joinSafely);
        couriers.forEach(LoggerCourier::joinSafely);
        logger.info("Пиццерия завершила работу и записала незавершённые заказы в файл: " + filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(orders);
        }
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

    /**
     * Загружает незавершенные заказы из файла и добавляет их в очередь заказов.
     *
     * @param filename Путь к файлу с незавершенными заказами
     * @throws IOException            Если возникла ошибка при чтении файла
     * @throws ClassNotFoundException Если не удается найти класс для объекта в файле
     */
    public void loadOldOrders(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Integer> orders = (List<Integer>) ois.readObject();
            for (int order : orders) {
                acceptOrder(order);
            }
        }
    }
}
