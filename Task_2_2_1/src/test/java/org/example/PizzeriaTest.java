package org.example;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    @Test
    void testStandartWork() throws Exception{
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);

        }

        pizzeria.stop();
        pizzeria.acceptOrder(11);
    }

    @Test
    void testStopWithSerialization() throws Exception {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stopWithSerialization("src/main/resources/OldOrders");

        File file = new File("src/main/resources/OldOrders");
        assertTrue(file.exists());
    }

    @Test
    void testLoadOldOrders() throws Exception {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        pizzeria.loadOldOrders("src/main/resources/OldOrders");

        pizzeria.stop();
    }
}