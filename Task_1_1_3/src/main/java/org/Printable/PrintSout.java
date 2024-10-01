package org.Printable;

/**
 * Класс PrintSout реализует интерфейс IPrintable и предназначен для
 * вывода выражений в консоль.
 */
public class PrintSout implements IPrintable {

    /**
     * Выводит переданное выражение в консоль.
     *
     * @param expression выражение, которое нужно вывести
     */
    @Override
    public void print(String expression) {
        System.out.print(expression);
    }
}
