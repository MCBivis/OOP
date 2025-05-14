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

    public PrimeCheckerServer(int port, int[] numbers, int chunkSize) {
        this.port = port;

        for (int i = 0; i < numbers.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, numbers.length);
            taskQueue.add(new Task(Arrays.copyOfRange(numbers, i, end)));
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Запущен на порту " + port);
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
                    System.out.println("[SERVER] Соединение закрыто.");
                    break;
                } catch (IOException e) {
                    System.err.println("[SERVER] Ошибка при принятии соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Ошибка запуска сервера: " + e.getMessage());
        } finally {
            if (this.serverSocket != null && !this.serverSocket.isClosed()) {
                try {
                    this.serverSocket.close();
                } catch (IOException e) {
                    System.err.println("[SERVER] Ошибка при закрытии сервера: " + e.getMessage());
                }
            }
            clientHandlers.shutdownNow();
            System.out.println("[SERVER] Завершает работу.");
        }
    }

    private void handleClient(Socket socket) {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Task task = taskQueue.poll();
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
            System.err.println("[SERVER] Ошибка клиента: " + e.getMessage());
        }
    }
    private void checkServerTermination() throws IOException {
        if (foundNonPrime) {
            serverSocket.close();
            clientHandlers.shutdownNow();
        }
    }
}
