package org.example.logger;

import org.example.PizzeriaInterface;
import org.junit.jupiter.api.Test;

public class LoggerPizzeriaTest {

    @Test
    void testStandartWork() throws Exception{
        PizzeriaInterface pizzeria = new LoggerPizzeria("src/main/resources/config.json");
        pizzeria.start();

        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);

        }

        pizzeria.stop();
        pizzeria.acceptOrder(11);
    }

}
