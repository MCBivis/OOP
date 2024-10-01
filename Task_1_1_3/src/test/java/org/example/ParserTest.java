package org.example;

import org.Printable.*;
import org.expressions.Expression;

import org.junit.jupiter.api.Test;
import org.parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс ParserTest содержит тесты для проверки работы класса Parser,
 * который разбирает математические выражения и вычисляет их значения.
 */
class ParserTest {

    /** Объект для печати результатов тестов. */
    private static final IPrintable printable = new PrintSout();
    /** Объект парсера для разбора выражений. */
    private static Parser parser = new Parser();

    /**
     * Тестирует разбор и вычисление простого числа.
     * Проверяет, что выражение "5" вычисляется как 5.
     */
    @Test
    void testParseNumber() {
        Expression expression = parser.parse("5", printable);
        assertEquals(5, expression.eval(""), "Число 5 должно вычисляться как 5.");
    }

    /**
     * Тестирует разбор и вычисление простого сложения.
     * Проверяет, что выражение "3+7" вычисляется как 10.
     */
    @Test
    void testParseAdd() {
        Expression expression = parser.parse("3+7", printable);
        assertEquals(10, expression.eval(""), "Выражение 3+7 должно вычисляться как 10.");
    }

    /**
     * Тестирует разбор и вычисление простого вычитания.
     * Проверяет, что выражение "10-4" вычисляется как 6.
     */
    @Test
    void testParseSub() {
        Expression expression = parser.parse("10-4", printable);
        assertEquals(6, expression.eval(""), "Выражение 10-4 должно вычисляться как 6.");
    }

    /**
     * Тестирует разбор и вычисление простого умножения.
     * Проверяет, что выражение "5*2" вычисляется как 10.
     */
    @Test
    void testParseSimpleMultiplication() {
        Expression expression = parser.parse("5*2", printable);
        assertEquals(10, expression.eval(""), "Выражение 5*2 должно вычисляться как 10.");
    }

    /**
     * Тестирует разбор и вычисление простого деления.
     * Проверяет, что выражение "8/2" вычисляется как 4.
     */
    @Test
    void testParseSimpleDivision() {
        Expression expression = parser.parse("8/2", printable);
        assertEquals(4, expression.eval(""), "Выражение 8/2 должно вычисляться как 4.");
    }

    /**
     * Тестирует разбор и вычисление сложного выражения с использованием
     * операций сложения и деления.
     * Проверяет, что выражение "(3+7)*(8/4)" вычисляется как 20.
     */
    @Test
    void testParseComplexExpression() {
        Expression expression = parser.parse("(3+7)*(8/4)", printable);
        assertEquals(20, expression.eval(""), "Выражение (3+7)*(8/4) должно вычисляться как 20.");
    }

    /**
     * Тестирует разбор и вычисление выражения с переменными.
     * Проверяет, что выражение "xxxx*(yy+2)" вычисляется как 12
     * при значениях xxxx=3 и yy=2.
     */
    @Test
    void testParseExpressionWithVariables() {
        Expression expression = parser.parse("xxxx*(yy+2)", printable);
        assertEquals(12, expression.eval("xxxx=3;yy=2"), "Выражение xxxx*(yy+2) должно вычисляться как 12 при xxxx=3 и yy=2.");
    }
}
