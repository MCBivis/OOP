package org.example.console;

import org.example.interfaces_abstractClasses.*;

/**
 * Класс, представляющий пиццерию, которая обрабатывает заказы.
 * Пиццерия включает пекарей, курьеров, склад и очередь заказов.
 */
public class ConsolePizzeria extends Pizzeria {

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public ConsolePizzeria(String configPath) throws Exception {
        super(configPath);

        for (int speed : config.bakers) {
            bakers.add(new Baker(orderQueue, storage, speed, isOpen));
        }

        for (int capacity : config.couriers) {
            couriers.add(new Courier(storage, capacity, isOpen));
        }
    }

    protected void print(String text){
        System.out.println(text);
    }
}
