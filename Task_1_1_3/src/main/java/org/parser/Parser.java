package org.parser;

import org.Printable.IPrintable;
import org.expressions.*;
import org.expressions.Number;

/**
 * Класс Parser отвечает за разбор математического выражения
 * и преобразование его в структуру объектов Expression.
 */
public class Parser {

    /** Строка, содержащая выражение для разбора. */
    private String expr;

    /** Текущая позиция в строке выражения. */
    private int pos;

    /** Объект, реализующий интерфейс IPrintable, для вывода выражений. */
    private IPrintable printable;

    /**
     * Начинает процесс разбора выражения.
     *
     * @param expr      строка выражения, которое нужно разобрать
     * @param printable объект, реализующий интерфейс IPrintable, для вывода
     * @return объект Expression, представляющий разобранное выражение
     */
    public Expression parse(String expr, IPrintable printable) {
        this.expr = expr;
        this.pos = 0;
        this.printable = printable;
        return parseExpr();
    }

    /**
     * Разбирает выражение, состоящее из сумм и разностей.
     *
     * @return объект Expression, представляющий разобранное выражение
     */
    private Expression parseExpr() {
        Expression result = parseTerm();

        while (pos < expr.length()) {
            char op = expr.charAt(pos);

            if (op == '+' || op == '-') {
                pos++;
                if (op == '+') {
                    result = new Add(result, parseTerm(), printable);
                } else {
                    result = new Sub(result, parseTerm(), printable);
                }
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Разбирает терм, состоящий из произведений и делений.
     *
     * @return объект Expression, представляющий разобранный терм
     */
    private Expression parseTerm() {
        Expression result = parseFactor();

        while (pos < expr.length()) {
            char operator = expr.charAt(pos);

            if (operator == '*' || operator == '/') {
                pos++;
                if (operator == '*') {
                    result = new Mul(result, parseFactor(), printable);
                } else {
                    result = new Div(result, parseFactor(), printable);
                }
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Разбирает фактор, который может быть числом, переменной или
     * вложенным выражением в скобках.
     *
     * @return объект Expression, представляющий разобранный фактор
     */
    private Expression parseFactor() {
        char ch = expr.charAt(pos);

        if (Character.isDigit(ch)) {
            return parseNumber();
        }

        if (Character.isLetter(ch)) {
            return parseVariable();
        }

        if (ch == '(') {
            pos++;
            Expression result = parseExpr();
            pos++; // Пропускаем закрывающую скобку
            return result;
        }

        return new Number(-1, printable); // Возвращаем "ошибочное" значение
    }

    /**
     * Разбирает число в строке и создает объект Number.
     *
     * @return объект Number, представляющий разобранное число
     */
    private Expression parseNumber() {
        StringBuilder number = new StringBuilder();
        while (pos < expr.length() && Character.isDigit(expr.charAt(pos))) {
            number.append(expr.charAt(pos++));
        }
        return new Number(Integer.parseInt(number.toString()), printable);
    }

    /**
     * Разбирает переменную в строке и создает объект Variable.
     *
     * @return объект Variable, представляющий разобранную переменную
     */
    private Expression parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (pos < expr.length() && Character.isLetter(expr.charAt(pos))) {
            variable.append(expr.charAt(pos++));
        }
        return new Variable(variable.toString(), printable);
    }
}
