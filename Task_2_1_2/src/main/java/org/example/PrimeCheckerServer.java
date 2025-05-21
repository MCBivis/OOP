package org.example;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimeCheckerServer extends Thread {
    private final int port;
    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService clientHandlers = Executors.newCachedThreadPool();
    private volatile boolean foundNonPrime = false;
    ServerSocket serverSocket;
    private final OutputHandler logger;

    public PrimeCheckerServer(int port, int[] numbers, int chunkSize) {
        this.port = port;
        this.logger = new ConsoleOutput("SERVER");

        for (int i = 0; i < numbers.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, numbers.length);
            taskQueue.add(new Task(Arrays.copyOfRange(numbers, i, end)));
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Запущен на порту " + port);
            while (!foundNonPrime && !taskQueue.isEmpty()) {
                try {
                    Socket socket = serverSocket.accept();
                    clientHandlers.submit(() -> {
                        handleClient(socket);
                        try {
                            checkServerTermination();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (SocketException e) {
                    logger.info("Соединение закрыто.");
                    break;
                } catch (IOException e) {
                    logger.error("Ошибка при принятии соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка запуска сервера: " + e.getMessage());
        } finally {
            if (this.serverSocket != null && !this.serverSocket.isClosed()) {
                try {
                    this.serverSocket.close();
                } catch (IOException e) {
                    logger.error("Ошибка при закрытии сервера: " + e.getMessage());
                }
            }
            clientHandlers.shutdownNow();
            logger.info("Завершает работу.");
        }
    }

    private void handleClient(Socket socket) {
        Task task = null;
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            task = taskQueue.poll();
            if (task == null) {
                out.writeObject(null);
                return;
            }

            out.writeObject(task);

            Boolean hasNonPrime = (Boolean) in.readObject();
            if (hasNonPrime != null && hasNonPrime) {
                foundNonPrime = true;
            }

        } catch (Exception e) {
            logger.error("Ошибка клиента: " + e.getMessage());
            if (task != null) {
                taskQueue.add(task);
                logger.info("Задача возвращена в очередь.");
            }
        }
    }
    private void checkServerTermination() throws IOException {
        if (foundNonPrime) {
            serverSocket.close();
            clientHandlers.shutdownNow();
        }
    }
}
