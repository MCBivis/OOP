package org.example.console;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConsolePizzeriaWithSerializationTest {

    @Test
    void testStopWithSerialization() throws Exception {
        ConsolePizzeriaWithSerialization consolePizzeria = new ConsolePizzeriaWithSerialization("src/main/resources/config.json");
        consolePizzeria.start();

        for (int i = 1; i <= 10; i++) {
            consolePizzeria.acceptOrder(i);
        }

        consolePizzeria.stopWithSerialization("src/main/resources/OldOrders");

        File file = new File("src/main/resources/OldOrders");
        assertTrue(file.exists());
    }

    @Test
    void testLoadOldOrders() throws Exception {
        ConsolePizzeriaWithSerialization consolePizzeria = new ConsolePizzeriaWithSerialization("src/main/resources/config.json");
        consolePizzeria.start();

        consolePizzeria.loadOldOrders("src/main/resources/OldOrders");
        consolePizzeria.stopWithSerialization("src/main/resources/OldOrders");
    }
}