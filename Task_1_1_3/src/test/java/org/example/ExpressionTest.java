package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс ExpressionTest содержит тесты для проверки работы класса Expression
 * и его подклассов, включая арифметические операции и упрощение выражений.
 */
class ExpressionTest {

    /** Объект для печати результатов тестов. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тестирует упрощение числа.
     * Проверяет, что значение числа остается неизменным после упрощения.
     */
    @Test
    void testSimplifyNumber() {
        Expression number = new Number(5, printable);
        Expression simplified = number.simplify();
        assertEquals(5, simplified.eval(""), "Значение числа 5 должно остаться 5 после упрощения.");
    }

    /**
     * Тестирует упрощение сложения с нулем.
     * Проверяет, что выражение 5 + 0 упрощается до 5.
     */
    @Test
    void testSimplifyAdd() {
        Expression addition = new Add(new Number(5, printable), new Number(0, printable), printable);
        Expression simplified = addition.simplify();
        assertEquals(5, simplified.eval(""), "5+0 должно упроститься до 5.");
    }

    /**
     * Тестирует упрощение вычитания равных чисел.
     * Проверяет, что выражение 5 - 5 упрощается до 0.
     */
    @Test
    void testSimplifySub() {
        Expression subtraction = new Sub(new Number(5, printable), new Number(5, printable), printable);
        Expression simplified = subtraction.simplify();
        assertEquals(0, simplified.eval(""), "5-5 должно упроститься до 0.");
    }

    /**
     * Тестирует упрощение умножения на ноль.
     * Проверяет, что выражение 5 * 0 упрощается до 0.
     */
    @Test
    void testSimplifyMulZero() {
        Expression multiplication = new Mul(new Number(5, printable), new Number(0, printable), printable);
        Expression simplified = multiplication.simplify();
        assertEquals(0, simplified.eval(""), "5*0 должно упроститься до 0.");
    }

    /**
     * Тестирует упрощение умножения на единицу.
     * Проверяет, что выражение 5 * 1 упрощается до 5.
     */
    @Test
    void testSimplifyMulOne() {
        Expression multiplication = new Mul(new Number(5, printable), new Number(1, printable), printable);
        Expression simplified = multiplication.simplify();
        assertEquals(5, simplified.eval(""), "5*1 должно упроститься до 5.");
    }

    /**
     * Тестирует сложение.
     * Проверяет, что выражение 3 + 7 вычисляется как 10.
     */
    @Test
    void testAdd() {
        Expression addition = new Add(new Number(3, printable), new Number(7, printable), printable);
        assertEquals(10, addition.eval(""), "3+7 должно вычислиться как 10.");
    }

    /**
     * Тестирует вычитание.
     * Проверяет, что выражение 10 - 3 вычисляется как 7.
     */
    @Test
    void testSub() {
        Expression subtraction = new Sub(new Number(10, printable), new Number(3, printable), printable);
        assertEquals(7, subtraction.eval(""), "10-3 должно вычислиться как 7.");
    }

    /**
     * Тестирует умножение.
     * Проверяет, что выражение 6 * 4 вычисляется как 24.
     */
    @Test
    void testMul() {
        Expression multiplication = new Mul(new Number(6, printable), new Number(4, printable), printable);
        assertEquals(24, multiplication.eval(""), "6*4 должно вычислиться как 24.");
    }

    /**
     * Тестирует деление.
     * Проверяет, что выражение 10 / 2 вычисляется как 5.
     */
    @Test
    void testDiv() {
        Expression division = new Div(new Number(10, printable), new Number(2, printable), printable);
        assertEquals(5, division.eval(""), "10/2 должно вычислиться как 5.");
    }

    /**
     * Тестирует упрощение деления.
     * Проверяет, что выражение 10 / 2 упрощается до 5.
     */
    @Test
    void testSimplifyDiv() {
        Expression division = new Div(new Number(10, printable), new Number(2, printable), printable);
        Expression simplified = division.simplify();
        assertEquals(5, simplified.eval(""), "10/2 должно упроститься до 5.");
    }

    /**
     * Тестирует упрощение сложного выражения деления.
     * Проверяет, что выражение (4*x) / (2*x) упрощается до 2.
     */
    @Test
    void testSimplifyDivisionExpression() {
        Expression complexDiv = new Div(
                new Mul(new Number(4, printable), new Variable("x", printable), printable),
                new Mul(new Number(2, printable), new Variable("x", printable), printable),
                printable
        );
        Expression simplified = complexDiv.simplify();
        assertEquals(2, simplified.eval("x=1"), "Выражение (4*x)/(2*x) должно упроститься до 2.");
    }

    /**
     * Тестирует упрощение сложного выражения.
     * Проверяет, что выражение (0*(5+3)) - (x-x) упрощается до 0.
     */
    @Test
    void testSimplifyExpression() {
        Expression complex = new Sub(
                new Mul(
                        new Number(0, printable),
                        new Add(new Number(5, printable), new Number(3, printable), printable),
                        printable
                ),
                new Sub(new Variable("x", printable), new Variable("x", printable), printable),
                printable
        );

        Expression simplified = complex.simplify();
        assertEquals(0, simplified.eval(""), "Выражение (0*(5+3))-(x-x) должно упроститься до 0.");
    }

    /**
     * Тестирует вычисление производной.
     * Проверяет, что производная от 3*x + 5 по x равна 3.
     */
    @Test
    void testDerivative() {
        Expression x = new Variable("x", printable);
        Expression expression = new Add(new Mul(new Number(3, printable), x, printable), new Number(5, printable), printable);
        Expression derivative = expression.derivative("x");

        Expression simplifiedDerivative = derivative.simplify();
        assertEquals(3, simplifiedDerivative.eval(""), "Производная от 3*x+5 по x должна быть 3.");
    }
}
