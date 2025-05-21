package org.example;

public class ConsoleOutput implements OutputHandler {
    private final String prefix;

    public ConsoleOutput(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void info(String message) {
        System.out.println("[" + prefix + "] " + message);
    }

    @Override
    public void error(String message) {
        System.err.println("[" + prefix + "] " + message);
    }
}
