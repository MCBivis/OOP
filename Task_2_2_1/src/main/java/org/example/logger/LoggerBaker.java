package org.example.logger;

import org.apache.logging.log4j.*;
import org.example.OrderQueue;
import org.example.Storage;
import org.example.Worker;

import java.util.concurrent.atomic.*;

/**
 * Класс, представляющий пекаря, который готовит пиццы на основе заказов из очереди.
 */
public class LoggerBaker extends Thread implements Worker {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final int speed;
    private final AtomicBoolean isOpen;
    private static final Logger logger = LogManager.getLogger(LoggerBaker.class);

    /**
     * Конструктор для создания пекаря с указанными параметрами.
     *
     * @param orderQueue Очередь заказов, из которой пекарь будет забирать заказы.
     * @param storage Хранилище, куда пекарь будет помещать готовые пиццы.
     * @param speed Скорость приготовления пиццы в секундах.
     * @param isOpen Флаг, указывающий, открыта ли пиццерия для приема заказов.
     */
    public LoggerBaker(OrderQueue orderQueue, Storage storage, int speed, AtomicBoolean isOpen) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.speed = speed;
        this.isOpen = isOpen;
    }

    /**
     * Основной метод пекаря. Он выполняет заказы: забирает их из очереди, готовит пиццу
     * и кладет в хранилище.
     */
    @Override
    public void run() {
        while (isOpen.get() || !orderQueue.isEmpty()) {
            Integer orderId = orderQueue.takeOrder();
            if (orderId == null) continue;
            logger.info(orderId + " Пекарь " + id + " начал готовить.");
            try {
                Thread.sleep(speed * 1000L);
            } catch (InterruptedException ignored) {}

            logger.info(orderId + " Пекарь " + id + " закончил готовить.");
            storage.storePizza(orderId);
            logger.info(orderId + " Пицца на складе.");
        }
    }

    /**
     * Безопасное завершение работы потока пекаря.
     */
    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }
}
