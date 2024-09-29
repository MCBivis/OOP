package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Div.
 */
class DivTest {

    /** Объект для печати результатов тестов. */
    private static final IPrintable printable = new PrintSout();

    /**
     * Тест для проверки базовой операции деления двух чисел.
     * Ожидается, что результат деления 10 на 2 будет 5.
     */
    @Test
    void testEvalDivNumbers() {
        Expression division = new Div(new Number(10, printable), new Number(2, printable), printable);
        assertEquals(5, division.eval(""), "10 / 2 должно вычисляться как 5.");
    }

    /**
     * Тест для проверки деления переменной и числа.
     * Ожидается, что результат выражения x / 2 при x = 10 будет 5.
     */
    @Test
    void testEvalDivVariableAndNumber() {
        Expression division = new Div(new Variable("x", printable), new Number(2, printable), printable);
        assertEquals(5, division.eval("x=10"), "x / 2 должно вычисляться как 5 при x=10.");
    }

    /**
     * Тест для проверки деления переменной самой на себя.
     * Ожидается, что результат выражения x / x будет 1 для любого значения x.
     */
    @Test
    void testSimplifyDivVariableByItself() {
        Expression division = new Div(new Variable("x", printable), new Variable("x", printable), printable);
        Expression simplified = division.simplify();
        assertEquals(1, simplified.eval("x=10"), "Результат x / x должен быть 1 при любом значении x.");
    }

    /**
     * Тест для проверки деления на единицу.
     * Ожидается, что результат выражения 5 / 1 будет 5.
     */
    @Test
    void testDivByOne() {
        Expression division = new Div(new Number(5, printable), new Number(1, printable), printable);
        Expression simplified = division.simplify();
        assertEquals(5, simplified.eval(""), "Результат 5 / 1 должен быть 5.");
    }

    /**
     * Тест для проверки деления на ноль.
     * Проверяет, что при делении на ноль вызывается исключение ArithmeticException.
     */
    @Test
    void testDivByZero() {
        Expression division = new Div(new Number(5, printable), new Number(0, printable), printable);
        assertThrows(ArithmeticException.class, () -> division.eval(""), "Деление на 0 должно вызывать ArithmeticException.");
    }

    /**
     * Тест для проверки производной от деления.
     * Ожидается, что производная от (x / (x + 1)) по x будет корректно вычислена.
     */
    @Test
    void testDerivativeOfDiv() {
        Expression x = new Variable("x", printable);
        Expression division = new Div(x, new Add(x, new Number(1, printable), printable), printable);
        Expression derivative = division.derivative("x");
        assertNotNull(derivative, "Производная от x / (x + 1) должна вычисляться корректно.");
    }

    /**
     * Тест для проверки сложного выражения с делением и упрощения.
     * Ожидается, что выражение ((4 * x) / (2 * x)) упрощается до 2.
     */
    @Test
    void testSimplifyComplexDiv() {
        Expression complexDivision = new Div(
                new Mul(new Number(4, printable), new Variable("x", printable), printable),
                new Mul(new Number(2, printable), new Variable("x", printable), printable),
                printable
        );
        Expression simplified = complexDivision.simplify();
        assertEquals(2, simplified.eval("x=1"), "Выражение (4 * x) / (2 * x) должно упрощаться до 2.");
    }
}
