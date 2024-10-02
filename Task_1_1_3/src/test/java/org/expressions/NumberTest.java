package org.expressions;

import org.Printable.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Number.
 */
class NumberTest {

    /** Объект для вывода выражений в консоль. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тест на правильность вычисления значения числового выражения.
     * Проверяем, что объект Number с числом 10 возвращает 10 при вызове метода eval.
     */
    @Test
    void testEval() {
        Expression number = new org.expressions.Number(10, printable);
        assertEquals(10, number.eval(""), "Число 10 должно возвращать 10.");
    }

    /**
     * Тест на правильность вычисления производной числового выражения.
     * Производная числа всегда равна 0.
     */
    @Test
    void testDerivative() {
        Expression number = new org.expressions.Number(10, printable);
        Expression derivative = number.derivative("x");
        assertEquals(0, derivative.eval(""), "Производная числа 10 должна быть равна 0.");
    }

    /**
     * Тест на правильность работы метода simplify.
     * Поскольку объект Number уже является упрощенным выражением, метод simplify должен возвращать этот же объект.
     */
    @Test
    void testSimplify() {
        Expression number = new Number(10, printable);
        Expression simplified = number.simplify();
        assertSame(number, simplified, "Метод simplify для числового выражения должен возвращать тот же объект.");
    }
}
