package org.expressions;

import org.Printable.IPrintable;

/**
 * Класс Mul представляет операцию умножения двух выражений.
 */
public class Mul extends Expression {

    /** Левое подвыражение. */
    private final Expression left;

    /** Правое подвыражение. */
    private final Expression right;

    /**
     * Конструктор для создания объекта умножения.
     *
     * @param left      левое выражение
     * @param right     правое выражение
     * @param printable объект, реализующий интерфейс IPrintable, для вывода выражений
     */
    public Mul(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение умножения в формате "(left * right)".
     */
    @Override
    public void print() throws Exception{
        printable.print("(");
        left.print();
        printable.print("*");
        right.print();
        printable.print(")");
    }

    /**
     * Вычисляет производную данного выражения по заданной переменной
     * с использованием правила произведения.
     *
     * @param variable переменная, по которой необходимо взять производную
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right, printable),
                new Mul(left, right.derivative(variable), printable),
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
        return left.eval(vars) * right.eval(vars);
    }

    /**
     * Упрощает выражение умножения, следуя заданным правилам:
     * a. Если оба операнда являются числами, возвращает результат их произведения.
     * b. Если один из операндов равен 0, возвращает 0.
     * c. Если один из операндов равен 1, возвращает другой операнд.
     * d. В остальных случаях возвращает новое упрощённое выражение.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        // Упрощение левого и правого операнда
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        // Если оба операнда - числа, возвращаем их произведение
        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(simplifiedLeft.eval("") * simplifiedRight.eval(""), printable);
        }

        // Если один из операндов равен 0, возвращаем 0
        if (simplifiedLeft.eval("") == 0 || simplifiedRight.eval("") == 0) {
            return new Number(0, printable);
        }

        // Если один из операндов равен 1, возвращаем другой операнд
        if (simplifiedLeft.eval("") == 1) {
            return simplifiedRight;
        }
        if (simplifiedRight.eval("") == 1) {
            return simplifiedLeft;
        }

        // Возвращаем новое упрощённое выражение
        return new Mul(simplifiedLeft, simplifiedRight, printable);
    }
}
