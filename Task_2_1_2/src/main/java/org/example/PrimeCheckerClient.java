package org.example;

import java.io.*;
import java.net.*;

public class PrimeCheckerClient extends Thread {
    private final String host;
    private final int port;
    private final int clientId;

    public PrimeCheckerClient(String host, int port, int clientId) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        while (true) {
            try (Socket socket = new Socket(host, port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                Object received = in.readObject();
                if (received == null) {
                    System.out.println("[CLIENT " + clientId + "] Нет задач. Завершаю работу.");
                    break;
                }

                int[] numbers = ((Task) received).numbers;
                boolean result = hasNonPrime(numbers);
                out.writeObject(result);

                if (result) {
                    System.out.println("[CLIENT " + clientId + "] Найдено составное число. Завершаю работу.");
                    break;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[CLIENT " + clientId + "] Ошибка соединения: " + e.getMessage());
                break;
            }
        }
    }

    private boolean hasNonPrime(int[] numbers) {
        for (int n : numbers) {
            if (!isPrime(n)) return true;
        }
        return false;
    }

    protected boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
