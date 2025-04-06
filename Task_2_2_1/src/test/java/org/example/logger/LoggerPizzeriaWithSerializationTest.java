package org.example.logger;

import org.example.interfaces_abstractClasses.PizzeriaInterfaceWithSerialization;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerPizzeriaWithSerializationTest {

    @Test
    void testStopWithSerialization() throws Exception {
        PizzeriaInterfaceWithSerialization pizzeria = new LoggerPizzeriaWithSerialization("src/main/resources/config.json");
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
        PizzeriaInterfaceWithSerialization pizzeria = new LoggerPizzeriaWithSerialization("src/main/resources/config.json");
        pizzeria.start();

        pizzeria.loadOldOrders("src/main/resources/OldOrders");
        pizzeria.stopWithSerialization("src/main/resources/OldOrders");
    }
}
