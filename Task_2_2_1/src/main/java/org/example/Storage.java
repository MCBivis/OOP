package org.example;

import java.util.*;

/**
 * Класс для управления хранилищем пицц на складе.
 */
public class Storage {
    private final int capacity;
    private final Queue<Integer> storage = new LinkedList<>();

    /**
     * Конструктор для создания хранилища с заданной ёмкостью.
     *
     * @param capacity Максимальная ёмкость склада.
     */
    public Storage(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Сохраняет пиццу на складе. Если склад полон, поток блокируется, пока не освободится место.
     *
     * @param orderId Идентификатор заказа, который будет помещён на склад.
     */
    public synchronized void storePizza(int orderId) {
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        storage.add(orderId);
        System.out.println(orderId + " Пицца на складе.");
        notifyAll();
    }

    /**
     * Извлекает пиццы из склада. Если склад пуст, поток блокируется до появления новых пицц.
     *
     * @param maxCount Максимальное количество пицц, которое можно забрать за один раз.
     * @return Список заказов, который был забран с склада.
     */
    public synchronized List<Integer> takePizzas(int maxCount) {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        List<Integer> taken = new ArrayList<>();
        while (!storage.isEmpty() && taken.size() < maxCount) {
            taken.add(storage.poll());
        }
        notifyAll();
        return taken;
    }

    /**
     * Проверяет, пуст ли склад.
     *
     * @return true, если склад пуст, иначе false.
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}