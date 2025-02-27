package org.example;

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
        Pizzeria pizzeria = new Pizzeria("src\\main\\resources\\config.json");
        pizzeria.start();

        for (int i = 1; i <= 20; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stop();
        pizzeria.acceptOrder(21);
    }
}
