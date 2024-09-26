package org.example;

public class Main {
    public static void main(String[] args) {
        PrintSout sout = new PrintSout();

        Expression e = new Add(new Number(3,sout), new Mul(new Number(2,sout),
                new Variable("x",sout),sout),sout);

        e.print();
        System.out.println();
        Expression de = e.derivative("x");
        de.print();
        System.out.println();
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);

        Parser parser = new Parser();
        Expression expr = parser.parse("5+10*(2-x)/4",sout);
        expr.print();
    }
}