package org.example;

public class Parser {
    private String expr;
    private int pos;
    private IPrintable printable;

    public Expression parse(String expr, IPrintable printable) {
        this.expr = expr;
        this.pos = 0;
        this.printable = printable;
        return parseExpr();
    }

    private Expression parseExpr() {
        Expression result = parseTerm();

        while (pos < expr.length()) {
            char op = expr.charAt(pos);

            if (op == '+' || op == '-') {
                pos++;
                if (op == '+') {
                    result = new Add(result, parseTerm(),printable);
                }else {
                    result = new Sub(result, parseTerm(),printable);
                }
            }else {
                break;
            }
        }
        return result;
    }

    private Expression parseTerm() {
        Expression result = parseFactor();

        while (pos < expr.length()) {
            char operator = expr.charAt(pos);

            if (operator == '*' || operator == '/') {
                pos++;
                if (operator == '*') {
                    result = new Mul(result, parseFactor(),printable);
                } else {
                    result = new Div(result, parseFactor(),printable);
                }
            } else {
                break;
            }
        }
        return result;
    }

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
            pos++;
            return result;
        }

        return new Number(-1,printable);
    }

    private Expression parseNumber() {
        StringBuilder number = new StringBuilder();
        while (pos < expr.length() && Character.isDigit(expr.charAt(pos))) {
            number.append(expr.charAt(pos++));
        }
        return new Number(Integer.parseInt(number.toString()),printable);
    }

    private Expression parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (pos < expr.length() && Character.isLetter(expr.charAt(pos))) {
            variable.append(expr.charAt(pos++));
        }
        return new Variable(variable.toString(),printable);
    }
}
