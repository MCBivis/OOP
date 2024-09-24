package org.example;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        System.out.println(e.print());
        Expression de = e.derivative("x");
        System.out.println(de.print());
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);
    }
}