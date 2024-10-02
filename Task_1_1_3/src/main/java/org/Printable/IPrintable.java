package org.Printable;

import java.io.IOException;

/**
 * Интерфейс IPrintable определяет метод для вывода выражения.
 * Классы, реализующие этот интерфейс, должны предоставлять способ печати строки.
 */
public interface IPrintable {

    /**
     * Печатает строку выражения.
     *
     * @param expression строка, представляющая выражение для вывода
     */
    void print(String expression) throws Exception;
}