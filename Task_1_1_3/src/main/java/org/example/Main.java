package org.example;

/**
 * Главный класс для запуска программы и демонстрации работы
 * математических выражений.
 */
public class Main {

    /**
     * Точка входа в программу. Создаёт и выводит различные
     * математические выражения, а также демонстрирует их
     * упрощение и дифференцирование.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        PrintSout sout = new PrintSout();

        Expression e = new Add(new Number(3, sout), new Mul(new Number(2, sout),
                new Variable("x", sout), sout), sout);

        e.print();
        System.out.println();

        Expression simpE = e.simplify();
        simpE.print();
        System.out.println();

        Expression de = e.derivative("x");
        de.print();
        System.out.println();

        Expression simpDe = de.simplify();
        simpDe.print();
        System.out.println();

        int result = e.eval("x = 10; y = 13");
        System.out.println(result);

        Parser parser = new Parser();
        Expression expr = parser.parse("5+10*(2-x)/4", sout);
        expr.print();
    }
}
