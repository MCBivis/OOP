package org.expressions;

import org.Printable.*;
import org.junit.jupiter.api.Test;
import org.parser.Parser;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс ExpressionTest содержит тесты для проверки работы класса Expression
 * и его подклассов, включая арифметические операции и упрощение выражений.
 */
class ExpressionTest {

    /** Объект для печати результатов тестов. */
    private static final IPrintable printable = new PrintSout();

    /** Путь к файлу, в который будем записывать вывод метода print(). */
    private static final String FILE_PATH = "test_output.txt";

    /**
     * Тест на правильность вывода выражения в файл.
     * Проверяем, что выражение (x * 4) записывается в файл корректно.
     */
    @Test
    void testPrintToFile() throws Exception {
        IPrintable printable = new PrintFile(FILE_PATH);
        Parser parser = new Parser();
        Expression multiplication = new Mul(new Variable("x", printable), new Number(4, printable), printable);

        multiplication.print();

        String fileContent = parser.getExprFile(FILE_PATH);

        assertEquals("(x*4)", fileContent, "Ожидается, что выражение (x * 4) будет записано в файл.");

        File file = new File(FILE_PATH);
        if (file.exists()) {
            assertTrue(file.delete(), "Файл должен быть успешно удален.");
        }
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
