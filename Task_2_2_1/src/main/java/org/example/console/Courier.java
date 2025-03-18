package org.example.console;

import org.example.Storage;
import org.example.Worker;

import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * Класс, представляющий курьера, который забирает пиццы из хранилища и доставляет их.
 */
public class Courier extends Thread implements Worker {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final Storage storage;
    private final int capacity;
    private final AtomicBoolean isOpen;

    /**
     * Конструктор для создания курьера с указанными параметрами.
     *
     * @param storage Хранилище, из которого курьер будет забирать пиццы.
     * @param capacity Вместимость курьера, максимальное количество пицц, которые он может забрать за один раз.
     * @param isOpen Флаг, указывающий, открыта ли пиццерия для приема заказов.
     */
    public Courier(Storage storage, int capacity, AtomicBoolean isOpen) {
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
            if (pizzas.isEmpty()) continue;
            System.out.println("Курьер " + id + " забрал " + pizzas);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            System.out.println("Курьер " + id + " доставил " + pizzas);
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