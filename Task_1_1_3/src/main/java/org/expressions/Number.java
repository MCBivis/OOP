package org.expressions;

import org.Printable.IPrintable;

/**
 * Класс Number представляет собой константное числовое выражение.
 * Этот класс используется для представления чисел в математических выражениях.
 */
public class Number extends Expression {

    /** Значение числа. */
    private final int value;

    /**
     * Конструктор для создания объекта числа.
     *
     * @param value    значение числа
     * @param printable объект, реализующий интерфейс IPrintable, для вывода выражений
     */
    public Number(int value, IPrintable printable) {
        super(printable);
        this.value = value;
    }

    /**
     * Печатает значение числа.
     */
    @Override
    public void print() throws Exception {
        printable.print(Integer.toString(value));
    }

    /**
     * Вычисляет производную данного числового выражения.
     * Производная константы равна 0.
     *
     * @param variable переменная, по которой необходимо взять производную
     * @return новое выражение, представляющее производную (0)
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0, printable);
    }

    /**
     * Вычисляет значение числового выражения.
     *
     * @param vars строка с переменными и их значениями (не используется)
     * @return значение числа
     */
    @Override
    public int eval(String vars) {
        return value;
    }

    /**
     * Упрощает числовое выражение.
     * Поскольку число уже является константой, возвращает само себя.
     *
     * @return само себя
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
