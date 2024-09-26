package org.example;

public class Add extends Expression{
    private final Expression left, right;

    public Add(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    public void print() {
        printable.print("(");
        left.print();
        printable.print("+");
        right.print();
        printable.print(")");
    }

    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable),printable);
    }

    public int eval(String vars) {
        return left.eval(vars) + right.eval(vars);
    }
}
