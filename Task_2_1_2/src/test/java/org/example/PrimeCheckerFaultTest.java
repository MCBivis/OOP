package org.example;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeCheckerFaultTest {

    @Test
    void testClientDisconnectsBeforeResponding() throws Exception {
        int[] array = new int[1000001];
        Arrays.fill(array, 27644437);
        array[1000000] = 4;

        PrimeCheckerServer server = new PrimeCheckerServer(12347, array, 600000);
        Thread serverThread = new Thread(server);
        serverThread.start();
        Thread.sleep(300);

        Thread client1 = new Thread(new PrimeCheckerClient("localhost", 12347, 1));
        client1.start();

        Thread client2 = new Thread(new FaultyClient("localhost", 12347, 2));
        client2.start();

        client1.join();
        serverThread.join();
        assertFalse(serverThread.isAlive(), "Сервер не должен зависнуть при сбое клиента");
    }

    private class FaultyClient implements Runnable {
        private final String host;
        private final int port;
        private final OutputHandler logger;

        FaultyClient(String host, int port, int clientId) {
            this.host = host;
            this.port = port;
            this.logger = new ConsoleOutput("FAULTY CLIENT " + clientId);
        }

        @Override
        public void run() {
            try (Socket socket = new Socket(host, port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                Object received = in.readObject();
                logger.info("Получил задачу и аварийно завершается.");

            } catch (Exception e) {
                logger.error("Ошибка: " + e.getMessage());
            }
        }
    }
}
