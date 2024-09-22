package org.example;

public class Main {
    public static void main(String[] args) {
        Expression e = new Variable("x");
        try {
            System.out.println(e.eval("y=10"));
        } catch (IllegalArgumentException e1) {
            System.out.println(e1.getMessage());
        }
    }
}