package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeCheckerTest {

    private void runDistributedTest(int[] array, int port, int clientCount, int chunkSize) throws InterruptedException {
        PrimeCheckerServer server = new PrimeCheckerServer(port, array, chunkSize);
        Thread serverThread = new Thread(server);
        serverThread.start();

        Thread.sleep(300);

        Thread[] clients = new Thread[clientCount];
        for (int i = 0; i < clientCount; i++) {
            clients[i] = new Thread(new PrimeCheckerClient("localhost", port, i + 1));
            clients[i].start();
        }

        for (Thread client : clients) {
            client.join();
        }

        serverThread.join();
    }

    @Test
    public void testAllPrimes() {
        int[] array = new int[100];
        Arrays.fill(array, 27644437);
        assertDoesNotThrow(() -> runDistributedTest(array, 12345, 5, 10));
    }

    @Test
    public void testHasNonPrime() {
        int[] array = new int[100];
        Arrays.fill(array, 27644437);
        array[50] = 100;

        assertDoesNotThrow(() -> runDistributedTest(array, 12346, 5, 10));
    }

    @Test
    public void testFewerTasksThanClients() {
        int[] array = new int[10000];
        Arrays.fill(array, 27644437);

        assertDoesNotThrow(() -> runDistributedTest(array, 12347, 5, 5000));
    }
}
