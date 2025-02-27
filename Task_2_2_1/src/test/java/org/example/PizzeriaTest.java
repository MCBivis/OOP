package org.example;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    @Test
    void testStandartWork() throws Exception{
        Pizzeria pizzeria = new Pizzeria("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stop();
        pizzeria.acceptOrder(11);
    }

    @Test
    void testStopWithSerialization() throws Exception {
        Pizzeria pizzeria = new Pizzeria("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stopWithSerialization("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\OldOrders");

        File file = new File("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\OldOrders");
        assertTrue(file.exists());
    }

    @Test
    void testLoadOldOrders() throws Exception {
        Pizzeria pizzeria = new Pizzeria("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\config.json");
        pizzeria.start();

        pizzeria.loadOldOrders("D:\\Github\\OOP\\Task_2_2_1\\src\\main\\resources\\OldOrders");

        pizzeria.stop();
    }
}