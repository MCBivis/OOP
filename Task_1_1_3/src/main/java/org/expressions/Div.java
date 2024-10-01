package org.expressions;

import org.Printable.IPrintable;

/**
 * Класс Div представляет операцию деления двух выражений.
 */
public class Div extends Expression {
    /** Левое подвыражение. */
    private final Expression left;
    /** Правое подвыражение. */
    private final Expression right;

    /**
     * Конструктор для создания объекта деления.
     *
     * @param left      левое выражение
     * @param right     правое выражение
     * @param printable объект, реализующий интерфейс IPrintable, для вывода выражений
     */
    public Div(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение деления в формате "(left / right)".
     */
    @Override
    public void print() {
        printable.print("(");
        left.print();
        printable.print("/");
        right.print();
        printable.print(")");
    }

    /**
     * Вычисляет производную данного выражения по заданной переменной.
     * Производная частного выражений считается по правилу Лейбница.
     *
     * @param variable переменная, по которой необходимо взять производную
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right, printable),
                        new Mul(left, right.derivative(variable), printable),
                        printable
                ),
                new Mul(right, right, printable),
                printable
        );
    }

    /**
     * Вычисляет значение выражения на основе переданных значений переменных.
     *
     * @param vars строка с переменными и их значениями
     * @return результат вычисления выражения
     */
    @Override
    public int eval(String vars) {
        return left.eval(vars) / right.eval(vars);
    }

    /**
     * Упрощает выражение деления, следуя следующим правилам:
     * a. Если оба операнда являются числами, возвращает результат их деления.
     * b. Если оба операнда равны, возвращает 1.
     * c. В остальных случаях возвращает новое упрощённое выражение.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(simplifiedLeft.eval("") / simplifiedRight.eval(""), printable);
        }

        if (left.simplify() instanceof Variable && right.simplify() instanceof Variable && (((Variable) left.simplify()).name).equals(((Variable) right.simplify()).name)) {
            return new Number(1, printable);
        }

        return new Div(simplifiedLeft, simplifiedRight, printable);
    }
}
