package org.example;

public class Div extends Expression{
    private final Expression left,right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String print(){
        return "(" + left.print() + "/" + right.print() + ")";
    }

    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    public int eval(String vars) {
        return left.eval(vars) / right.eval(vars);
    }
}
