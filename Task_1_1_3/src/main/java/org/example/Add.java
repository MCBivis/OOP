package org.example;

/**
 * Класс Add представляет операцию сложения двух выражений.
 */
public class Add extends Expression {
    /** Левое подвыражение. */
    private final Expression left;
    /** Правое подвыражение. */
    private final Expression right;

    /**
     * Конструктор для создания объекта сложения.
     *
     * @param left      левое выражение
     * @param right     правое выражение
     * @param printable объект, реализующий интерфейс IPrintable, для вывода выражений
     */
    public Add(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение сложения в формате "(left + right)".
     */
    @Override
    public void print() {
        printable.print("(");
        left.print();
        printable.print("+");
        right.print();
        printable.print(")");
    }

    /**
     * Вычисляет производную данного выражения по заданной переменной.
     * Производная суммы — это сумма производных.
     *
     * @param variable переменная, по которой необходимо взять производную
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable), printable);
    }

    /**
     * Вычисляет значение выражения на основе переданных значений переменных.
     *
     * @param vars строка с переменными и их значениями
     * @return результат вычисления выражения
     */
    @Override
    public int eval(String vars) {
        return left.eval(vars) + right.eval(vars);
    }

    /**
     * Упрощает выражение, следуя правилам:
     * a. Если оба операнды являются числами, возвращает результат их сложения.
     * b. Если одна из операнд равна нулю, возвращает вторую.
     * c. В остальных случаях возвращает новое упрощённое выражение.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(simplifiedLeft.eval("") + simplifiedRight.eval(""), printable);
        }

        if (simplifiedLeft.eval("") == 0) {
            return simplifiedRight;
        }
        if (simplifiedRight.eval("") == 0) {
            return simplifiedLeft;
        }

        return new Add(simplifiedLeft, simplifiedRight, printable);
    }
}