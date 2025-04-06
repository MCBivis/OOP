package org.example;


import org.example.console.ConsolePizzeria;
import org.example.interfaces_abstractClasses.PizzeriaInterface;

/**
 * Главный класс для запуска пиццерии и тестирования её функциональности.
 * В этом классе создается экземпляр пиццерии, принимаются заказы, затем пиццерия закрывается,
 * и выполняется попытка принять новый заказ после закрытия.
 */
public class Main {

    /**
     * Главный метод для запуска пиццерии.
     * @param args Параметры командной строки.
     * @throws Exception Если возникает ошибка при работе с конфигурацией или других этапах работы пиццерии.
     */
    public static void main(String[] args) throws Exception {
        PizzeriaInterface pizzeria = new ConsolePizzeria("src/main/resources/config.json");
        pizzeria.start();

        for (int i = 1; i <= 5; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stop();
        pizzeria.acceptOrder(11);
    }
}
