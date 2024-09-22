package org.example;

public class Number extends Expression{
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public String print() {
        return Integer.toString(value);
    }

    public Expression derivative(String variable) {
        return new Number(0);
    }

    public int eval(String vars) {
        return value;
    }
}
