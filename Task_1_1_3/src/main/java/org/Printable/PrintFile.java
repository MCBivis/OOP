package org.Printable;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс PrintFile реализует интерфейс IPrintable и предназначен для
 * вывода выражений в файл.
 */
public class PrintFile implements IPrintable {

    /** Имя файла, в который будет производиться вывод. */
    private final String fileName;

    /**
     * Конструктор для создания объекта PrintFile.
     *
     * @param fileName имя файла, в который будет записываться выражение
     */
    public PrintFile(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Записывает переданное выражение в указанный файл.
     *
     * @param expression выражение, которое нужно записать в файл
     */
    @Override
    public void print(String expression) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(expression);
            writer.close(); // Закрытие потока записи
        } catch (IOException e) {
            System.out.println("Writing in file error: " + e.getMessage());
        }
    }
}
