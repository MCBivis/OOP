package org.example.logger;

import org.apache.logging.log4j.*;
import org.example.Storage;
import org.example.interfaces_abstractClasses.Worker;

import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * Класс, представляющий курьера, который забирает пиццы из хранилища и доставляет их.
 */
public class LoggerCourier extends Thread implements Worker {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final Storage storage;
    private final int capacity;
    private final AtomicBoolean isOpen;
    private static final Logger logger = LogManager.getLogger(LoggerCourier.class);

    /**
     * Конструктор для создания курьера с указанными параметрами.
     *
     * @param storage Хранилище, из которого курьер будет забирать пиццы.
     * @param capacity Вместимость курьера, максимальное количество пицц, которые он может забрать за один раз.
     * @param isOpen Флаг, указывающий, открыта ли пиццерия для приема заказов.
     */
    public LoggerCourier(Storage storage, int capacity, AtomicBoolean isOpen) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.storage = storage;
        this.capacity = capacity;
        this.isOpen = isOpen;
    }

    /**
     * Основной метод курьера. Он забирает пиццы из хранилища и доставляет их.
     */
    @Override
    public void run() {
        while (isOpen.get() || !storage.isEmpty()) {
            List<Integer> pizzas = storage.takePizzas(capacity);

            logger.info("Курьер " + id + " забрал " + pizzas);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            logger.info("Курьер " + id + " доставил " + pizzas);
        }
    }

    /**
     * Безопасное завершение работы потока курьера.
     */
    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }
}