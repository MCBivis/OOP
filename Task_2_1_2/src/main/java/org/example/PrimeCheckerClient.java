package org.example;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class PrimeCheckerClient extends Thread {
    private final String host;
    private final int port;
    private final OutputHandler logger;

    public PrimeCheckerClient(String host, int port, int clientId) {
        this.host = host;
        this.port = port;
        this.logger = new ConsoleOutput("CLIENT " + clientId);
    }

    @Override
    public void run() {
        while (true) {
            try (Socket socket = new Socket(host, port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                Object received = in.readObject();
                if (received == null) {
                    logger.info("Нет задач. Завершаю работу.");
                    break;
                }

                int[] numbers = ((Task) received).numbers;
                boolean result = hasNonPrime(numbers);
                out.writeObject(result);

                if (result) {
                    logger.info("Найдено составное число. Завершаю работу.");
                    break;
                }

            } catch (IOException | ClassNotFoundException e) {
                logger.error("Ошибка соединения: " + e.getMessage());
                break;
            }
        }
    }

    public boolean hasNonPrime(int[] numbers) {
        List<Integer> numberList = Arrays.stream(numbers).boxed().toList();
        return numberList.parallelStream().anyMatch(number -> !isPrime(number));
    }

    protected boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
