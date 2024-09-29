package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Add.
 */
class AddTest {

    /** Объект для вывода в консоль, используемый для тестирования. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тест для проверки базовой операции сложения двух чисел.
     * Ожидается, что результат сложения 3 и 5 будет 8.
     */
    @Test
    void testEvalAddNumbers() {
        Expression addition = new Add(new Number(3, printable), new Number(5, printable), printable);
        assertEquals(8, addition.eval(""), "3 + 5 должно вычисляться как 8.");
    }

    /**
     * Тест для проверки сложения переменной и числа.
     * Ожидается, что результат выражения x + 5 при x=3 будет 8.
     */
    @Test
    void testEvalAddVariableAndNumber() {
        Expression addition = new Add(new Variable("x", printable), new Number(5, printable), printable);
        assertEquals(8, addition.eval("x=3"), "x + 5 должно вычисляться как 8 при x=3.");
    }

    /**
     * Тест для проверки производной от суммы.
     * Ожидается, что производная от выражения (x + 5) по x будет равна 1.
     */
    @Test
    void testDerivativeOfAdd() {
        Expression x = new Variable("x", printable);
        Expression addition = new Add(x, new Number(5, printable), printable);
        Expression derivative = addition.derivative("x");
        assertEquals(1, derivative.eval(""), "Производная от x + 5 по x должна быть 1.");
    }

    /**
     * Тест для проверки упрощения выражения, где один из операндов является нулем.
     * Ожидается, что выражение 0 + x упрощается до x.
     */
    @Test
    void testSimplifyAddZeroLeft() {
        Expression addition = new Add(new Number(0, printable), new Variable("x", printable), printable);
        Expression simplified = addition.simplify();
        assertEquals("x", ((Variable) simplified).name, "0 + x должно упрощаться до x.");
    }

    /**
     * Тест для проверки упрощения выражения, где один из операндов является нулем.
     * Ожидается, что выражение x + 0 упрощается до x.
     */
    @Test
    void testSimplifyAddZeroRight() {
        Expression addition = new Add(new Variable("x", printable), new Number(0, printable), printable);
        Expression simplified = addition.simplify();
        assertEquals("x", ((Variable) simplified).name, "x + 0 должно упрощаться до x.");
    }

    /**
     * Тест для проверки упрощения выражения, где оба операнда являются числами.
     * Ожидается, что выражение 3 + 5 упрощается до числа 8.
     */
    @Test
    void testSimplifyAddNumbers() {
        Expression addition = new Add(new Number(3, printable), new Number(5, printable), printable);
        Expression simplified = addition.simplify();
        assertEquals(8, simplified.eval(""), "Упрощённое выражение должно быть равно 8.");
    }

    /**
     * Тест для проверки печати выражения сложения.
     * Ожидается, что выражение x + 5 выводится в формате (x+5).
     */
    @Test
    void testPrintAddExpression() {
        Expression addition = new Add(new Variable("x", printable), new Number(5, printable), printable);
        // Проверяем вывод выражения в консоль (визуальная проверка)
        addition.print();  // Ожидаемый вывод: (x+5)
    }

    /**
     * Тест для проверки сложения переменных.
     * Ожидается, что выражение x + y вычисляется корректно при заданных значениях переменных.
     */
    @Test
    void testEvalAddVariables() {
        Expression addition = new Add(new Variable("x", printable), new Variable("y", printable), printable);
        assertEquals(10, addition.eval("x=3;y=7"), "x + y должно вычисляться как 10 при x=3 и y=7.");
    }
}
