package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Pizzeria pizzeria = new Pizzeria("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\config.json");
        pizzeria.start();

        for (int i = 1; i <= 20; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stop();
    }
}
