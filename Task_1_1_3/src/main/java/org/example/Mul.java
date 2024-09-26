package org.example;

public class Mul extends Expression{
    private final Expression left, right;

    public Mul(Expression left, Expression right, IPrintable printable) {
        super(printable);
        this.left = left;
        this.right = right;
    }

    public void print(){
        printable.print("(");
        left.print();
        printable.print("*");
        right.print();
        printable.print(")");
    }

    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable),right,printable),
                new Mul(left,right.derivative(variable),printable),
                printable
        );
    }

    public int eval(String vars) {
        return left.eval(vars) * right.eval(vars);
    }
}
