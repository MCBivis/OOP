package org.example.console;

import org.example.interfaces_abstractClasses.PizzeriaInterface;
import org.junit.jupiter.api.*;

class ConsolePizzeriaTest {

    @Test
    void testStandartWork() throws Exception{
        PizzeriaInterface pizzeria = new ConsolePizzeria("src/main/resources/config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);

        }

        pizzeria.stop();
        pizzeria.acceptOrder(11);
    }
}