package org.example.logger;

import org.apache.logging.log4j.*;
import org.example.interfaces_abstractClasses.*;

/**
 * Класс, представляющий пиццерию, которая обрабатывает заказы.
 * Пиццерия включает пекарей, курьеров, склад и очередь заказов.
 */
public class LoggerPizzeriaWithSerialization extends PizzeriaWithSeriallization {

    private static final Logger logger = LogManager.getLogger(LoggerPizzeria.class);

    /**
     * Конструктор для создания пиццерии с конфигурацией из файла.
     *
     * @param configPath Путь к конфигурационному файлу
     * @throws Exception Если при чтении конфигурации возникла ошибка
     */
    public LoggerPizzeriaWithSerialization(String configPath) throws Exception {
        super(configPath);

        for (int speed : config.bakers) {
            bakers.add(new LoggerBaker(orderQueue, storage, speed, isOpen));
        }

        for (int capacity : config.couriers) {
            couriers.add(new LoggerCourier(storage, capacity, isOpen));
        }
    }

    protected void print(String text){
        logger.info(text);
    }
}
