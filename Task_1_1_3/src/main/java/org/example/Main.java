package org.example;

import org.Printable.*;
import org.expressions.*;
import org.expressions.Number;
import org.parser.Parser;

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

        Expression e = new Add(new org.expressions.Number(3, sout), new Mul(new Number(2, sout),
                new Variable("x", sout), sout), sout);
        try {
            e.print();
            System.out.println();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Expression simpE = e.simplify();
        try {
            simpE.print();
            System.out.println();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Expression de = e.derivative("x");
        try {
            de.print();
            System.out.println();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Expression simpDe = de.simplify();
        try {
            simpDe.print();
            System.out.println();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        int result = e.eval("x = 10; y = 13");
        System.out.println(result);

        Parser parser = new Parser();
        System.out.println("Введите выражение.");
        try{
            var console = parser.parse(sout);
            console.print();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        System.out.println();
    }
}
