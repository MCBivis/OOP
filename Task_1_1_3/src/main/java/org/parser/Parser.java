package org.parser;

import java.io.FileReader;
import java.util.Scanner;
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

    /** Объект, для считывания символов с консоли. */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Начинает процесс разбора выражения.
     *
     * @param printable объект, реализующий интерфейс IPrintable, для вывода
     * @return объект Expression, представляющий разобранное выражение
     * @throws Exception если выражение записано неверно
     */
    public Expression parse(IPrintable printable) throws Exception {
        this.expr = getExprSout();
        this.pos = 0;
        this.printable = printable;

        Expression result = parseExpr();

        if (pos != expr.length()) {
            throw new IllegalArgumentException("Ошибка в выражении: лишний символ '" + expr.charAt(pos) + "'.");
        }

        return result;
    }

    /**
     * Начинает процесс разбора выражения.
     *
     * @param printable объект, реализующий интерфейс IPrintable, для вывода
     * @param fileName имя файла
     * @return объект Expression, представляющий разобранное выражение
     * @throws Exception если выражение записано неверно
     */
    public Expression parse(String fileName,IPrintable printable) throws Exception {
        this.expr = getExprFile(fileName);
        this.pos = 0;
        this.printable = printable;

        Expression result = parseExpr();

        if (pos != expr.length()) {
            throw new IllegalArgumentException("Ошибка в выражении: лишний символ '" + expr.charAt(pos) + "'.");
        }

        return result;
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
        if (pos >= expr.length()) {
            throw new RuntimeException("Ошибка: неожиданное завершение выражения.");
        }

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
            if (pos >= expr.length() || expr.charAt(pos) != ')') {
                throw new RuntimeException("Ошибка: незакрытая скобка.");
            }
            pos++; // Пропускаем закрывающую скобку
            return result;
        }

        throw new RuntimeException("Ошибка: недопустимый символ '" + ch + "'.");
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

    /**
     * Считывает выражение из консоли и убирает пробелы.
     *
     * @return объект String, представляющий выражение
     */
    private String getExprSout() {
        String expression = scanner.nextLine();
        return expression.replaceAll(" ","");
    }

    /**
     * Считывает выражение из файла и убирает пробелы.
     *
     * @param fileName имя файла, с которого будем считывать выражение
     * @return объект String, представляющий выражение
     * @throws Exception если строка пустая
     */
    public String getExprFile(String fileName) throws Exception {
        FileReader fileReader = new FileReader(fileName);
        Scanner scannerFile = new Scanner(fileReader);
        String expression = scannerFile.nextLine();
        fileReader.close();
        return expression.replaceAll(" ","");
    }
}