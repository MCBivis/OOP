package org.expressions;

import org.Printable.IPrintable;

/**
 * Класс Sub представляет собой вычитание двух выражений.
 */
public class Sub extends Expression {

    /** Левый операнд для вычитания. */
    private final Expression left;

    /** Правый операнд для вычитания. */
    private final Expression right;

    /**
     * Конструктор для создания объекта Sub.
     *
     * @param left      левый операнд для вычитания
     * @param right     правый операнд для вычитания
     * @param printable объект, реализующий интерфейс IPrintable, для вывода
     */
    public Sub(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение в формате "(left - right)".
     */
    @Override
    public void print() throws Exception{
        printable.print("(");
        left.print();
        printable.print("-");
        right.print();
        printable.print(")");
    }

    /**
     * Вычисляет производную выражения по заданной переменной.
     *
     * @param variable переменная, по которой нужно взять производную
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable), printable);
    }

    /**
     * Вычисляет значение выражения для заданных переменных.
     *
     * @param vars строка, содержащая значения переменных
     * @return значение выражения
     */
    @Override
    public int eval(String vars) {
        return left.eval(vars) - right.eval(vars);
    }

    /**
     * Упрощает выражение, если это возможно.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        if (left.simplify() instanceof Number && right.simplify() instanceof Number) {
            return new Number(left.simplify().eval("") - right.simplify().eval(""), printable);
        }

        if (left.simplify() instanceof Variable && right.simplify() instanceof Variable && (((Variable) left.simplify()).name).equals(((Variable) right.simplify()).name)) {
            return new Number(0, printable);
        }

        return new Sub(left.simplify(), right.simplify(), printable);
    }
}
