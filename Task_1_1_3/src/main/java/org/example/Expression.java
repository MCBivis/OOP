package org.example;

/**
 * Абстрактный класс Expression, представляющий математическое выражение.
 * Этот класс определяет методы для печати выражения, вычисления его производной,
 * оценки значения и упрощения.
 */
public abstract class Expression {

    /** Объект для печати выражения. */
    protected IPrintable printable;

    /**
     * Конструктор для создания выражения с заданным объектом печати.
     *
     * @param printable объект, реализующий интерфейс IPrintable, для вывода выражений
     */
    public Expression(IPrintable printable) {
        this.printable = printable;
    }

    /**
     * Печатает текущее выражение с использованием объекта {@link IPrintable}.
     */
    public abstract void print();

    /**
     * Вычисляет производную данного выражения по указанной переменной.
     *
     * @param variable переменная, по которой необходимо взять производную
     * @return новое выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Вычисляет значение выражения на основе переданных значений переменных.
     *
     * @param vars строка с переменными и их значениями
     * @return результат вычисления выражения
     */
    public abstract int eval(String vars);

    /**
     * Упрощает текущее выражение, используя логические правила упрощения.
     *
     * @return упрощённое выражение
     */
    public abstract Expression simplify();

}