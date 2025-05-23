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
public abstract class PizzeriaWithSeriallization implements PizzeriaInterfaceWithSerialization{

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
    public PizzeriaWithSeriallization(String configPath) throws Exception {
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
        bakers.forEach(Worker::joinSafely);
        synchronized (storage) {
            storage.notifyAll();
        }
        couriers.forEach(Worker::joinSafely);
        System.out.println("Пиццерия завершила работу и записала незавершённые заказы в файл: " + filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(orders);
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

    protected abstract void print(String text);
}