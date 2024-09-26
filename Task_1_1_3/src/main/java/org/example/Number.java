package org.example;

public class Number extends Expression{
    private final int value;

    public Number(int value, IPrintable printable) {
        super(printable);
        this.value = value;
    }

    public void print() {
        printable.print(Integer.toString(value));
    }

    public Expression derivative(String variable) {
        return new Number(0,printable);
    }

    public int eval(String vars) {
        return value;
    }
}
