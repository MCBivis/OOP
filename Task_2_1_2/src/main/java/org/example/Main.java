package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int port = 12345;
        int totalNumbers = 1000000;
        int chunkSize = 100000;
        int clientCount = 5;

        int[] numbers = new Random().ints(totalNumbers, 2, 10_000).toArray();
        int[] numbers2 = new int[1000000];
        Arrays.fill(numbers2, 27644437);
        PrimeCheckerServer serverThread = new PrimeCheckerServer(port, numbers, chunkSize);
        serverThread.start();

        Thread.sleep(500);

        for (int i = 1; i <= clientCount; i++) {
            new PrimeCheckerClient("localhost", port, i).start();
        }
    }
}
