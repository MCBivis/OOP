package org.example;

public class Sub extends Expression{
    private final Expression left, right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    public int eval(String vars) {
        return left.eval(vars) - right.eval(vars);
    }
}
