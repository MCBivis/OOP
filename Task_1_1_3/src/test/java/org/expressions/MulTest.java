package org.expressions;

import org.Printable.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Mul.
 */
class MulTest {

    /** Объект для вывода выражений в консоль. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тест на правильность вычисления значения выражения умножения.
     * Проверяем, что 3 * 4 возвращает 12.
     */
    @Test
    void testEval() {
        Expression multiplication = new Mul(new Number(3, printable), new Number(4, printable), printable);
        assertEquals(12, multiplication.eval(""), "3 * 4 должно вычислиться как 12.");
    }

    /**
     * Тест на упрощение выражения, когда оба операнда — это числа.
     * Проверяем, что 3 * 5 упрощается до числа 15.
     */
    @Test
    void testSimplifyNumbers() {
        Expression multiplication = new Mul(new Number(3, printable), new Number(5, printable), printable);
        Expression simplified = multiplication.simplify();
        assertEquals(15, simplified.eval(""), "3 * 5 должно упроститься до 15.");
    }

    /**
     * Тест на упрощение выражения, когда один из операндов равен 0.
     * Проверяем, что 0 * x упрощается до 0.
     */
    @Test
    void testSimplifyZero() {
        Expression multiplication = new Mul(new Number(0, printable), new Variable("x", printable), printable);
        Expression simplified = multiplication.simplify();
        assertEquals(0, simplified.eval("x=5"), "0 * x должно упроститься до 0.");
    }

    /**
     * Тест на упрощение выражения, когда один из операндов равен 1.
     * Проверяем, что 1 * x упрощается до x.
     */
    @Test
    void testSimplifyOne() {
        Expression multiplication = new Mul(new Number(1, printable), new Variable("x", printable), printable);
        Expression simplified = multiplication.simplify();
        assertTrue(simplified instanceof Variable, "1 * x должно упроститься до x.");
    }

    /**
     * Тест на правильность вычисления производной выражения.
     * Проверяем, что производная выражения x * x * x по x равна 3 * x * x.
     */
    @Test
    void testDerivative() {
        Expression multiplication = new Mul(new Variable("x", printable), new Mul(new Variable("x", printable),
                new Variable("x", printable), printable), printable);
        Expression derivative = multiplication.derivative("x");

        assertEquals(75, derivative.eval("x=5"), "Производная x * x * x по x должна быть 3 * x * x.");
    }

    /**
     * Тест на вычисление сложного выражения, содержащего переменные и константы.
     * Проверяем выражение x * x * x * x + 42 / 2 при x=2, которое должно вычисляться как 16 + 21 = 37.
     */
    @Test
    void testEvalComplexExpression() {
        Expression complexExpr = new Add(
                new Mul(new Variable("x", printable),
                        new Mul(new Variable("x", printable),
                                new Mul(new Variable("x", printable),
                                        new Variable("x", printable), printable), printable), printable),
                new Div(new Number(42, printable), new Number(2, printable), printable), printable);

        assertEquals(37, complexExpr.eval("x=2"), "Выражение x * x * x * x + 42 / 2 при x=2 должно вычисляться как 37.");
    }

    /**
     * Тест на упрощение сложного выражения.
     * Проверяем, что (x * 1) упрощается до x.
     */
    @Test
    void testSimplifyComplexExpression() {
        Expression complexExpr = new Mul(new Variable("x", printable), new Number(1, printable), printable);
        Expression simplified = complexExpr.simplify();

        assertTrue(simplified instanceof Variable, "(x * 1) должно упроститься до x.");
    }
}
