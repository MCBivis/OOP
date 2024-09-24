package org.example;

public abstract class Expression {
    abstract String print(); // Метод для вывода выражения
    public abstract Expression derivative(String variable); // Метод для дифференцирования
    public abstract int eval(String vars); // Метод для вычисления значения выражения
}
