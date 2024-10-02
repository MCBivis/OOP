package org.expressions;

import org.Printable.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Sub.
 */
class SubTest {

    /** Объект для вывода выражений в консоль. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тест на правильность вычисления значения выражения вычитания.
     * Проверяем, что 10 - 4 возвращает 6.
     */
    @Test
    void testEval() {
        Expression subtraction = new Sub(new org.expressions.Number(10, printable),
                new org.expressions.Number(4, printable), printable);

        assertEquals(6, subtraction.eval(""), "10 - 4 должно вычислиться как 6.");
    }

    /**
     * Тест на упрощение выражения, когда оба операнда — это числа.
     * Проверяем, что 10 - 5 упрощается до числа 5.
     */
    @Test
    void testSimplifyNumbers() {
        Expression subtraction = new Sub(new org.expressions.Number(10, printable),
                new org.expressions.Number(5, printable), printable);
        Expression simplified = subtraction.simplify();

        assertEquals(5, simplified.eval(""), "10 - 5 должно упроститься до 5.");
    }

    /**
     * Тест на упрощение выражения, когда вычитаемое и уменьшаемое являются одинаковыми переменными.
     * Проверяем, что x - x упрощается до 0.
     */
    @Test
    void testSimplifySameVariables() {
        Expression subtraction = new Sub(new Variable("x", printable),
                new Variable("x", printable), printable);
        Expression simplified = subtraction.simplify();

        assertEquals(0, simplified.eval("x=5"), "x - x должно упроститься до 0.");
    }

    /**
     * Тест на правильность вычисления производной выражения.
     * Проверяем, что производная выражения 3x - 2x по x равна x.
     */
    @Test
    void testDerivative() {
        Expression subtraction = new Sub(
                new Mul(new org.expressions.Number(3, printable), new Variable("x", printable), printable),
                new Mul(new org.expressions.Number(2, printable), new Variable("x", printable), printable),
                printable
        );
        Expression derivative = subtraction.derivative("x");

        assertEquals(1, derivative.eval("x=5"), "Производная 3x - 2x по x должна быть равна 1.");
    }

    /**
     * Тест на упрощение сложного выражения.
     * Проверяем, что (5 - (x - x)) упрощается до 5.
     */
    @Test
    void testSimplifyComplexExpression() {
        Expression complexSub = new Sub(new Number(5, printable),
                new Sub(new Variable("x", printable),
                        new Variable("x", printable), printable),
                printable);
        Expression simplified = complexSub.simplify();

        assertEquals(5, simplified.eval(""), "5 - (x - x) должно упроститься до 5.");
    }

    /**
     * Тест на вычисление выражения с переменными.
     * Проверяем, что выражение x - y при x=10 и y=3 возвращает 7.
     */
    @Test
    void testEvalWithVariables() {
        Expression subtraction = new Sub(new Variable("x", printable),
                new Variable("y", printable), printable);

        assertEquals(7, subtraction.eval("x=10;y=3"),
                "Выражение x - y при x=10 и y=3 должно вычисляться как 7.");
    }
}

