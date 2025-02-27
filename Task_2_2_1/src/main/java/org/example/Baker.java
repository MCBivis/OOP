package org.example;

import java.util.concurrent.atomic.*;

/**
 * Класс, представляющий пекаря, который готовит пиццы на основе заказов из очереди.
 */
public class Baker extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final int speed;
    private final AtomicBoolean isOpen;

    /**
     * Конструктор для создания пекаря с указанными параметрами.
     *
     * @param orderQueue Очередь заказов, из которой пекарь будет забирать заказы.
     * @param storage Хранилище, куда пекарь будет помещать готовые пиццы.
     * @param speed Скорость приготовления пиццы в секундах.
     * @param isOpen Флаг, указывающий, открыта ли пиццерия для приема заказов.
     */
    public Baker(OrderQueue orderQueue, Storage storage, int speed, AtomicBoolean isOpen) {
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

            System.out.println(orderId + " Пекарь " + id + " начал готовить.");
            try {
                Thread.sleep(speed * 1000L);
            } catch (InterruptedException ignored) {}

            System.out.println(orderId + " Пекарь " + id + " закончил готовить.");
            storage.storePizza(orderId);
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
