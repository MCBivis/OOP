package org.example;

public class Mul extends Expression{
    private final Expression left, right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String print(){
        return "(" + left.print() + "*" + right.print() + ")";
    }

    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable),right),
                new Mul(left,right.derivative(variable))
        );
    }

    public int eval(String vars) {
        return left.eval(vars) * right.eval(vars);
    }
}
