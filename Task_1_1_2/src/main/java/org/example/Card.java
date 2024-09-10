package org.example;

public class Card {
    public static final String[] SUITS = {"Червы", "Пики", "Трефы", "Бубны"};
    public static final String[] NAMES = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка", "Валет", "Дама", "Король", "Туз"};

    String suit;
    String name;
    int value;

    public Card(String suit, String name, int value) {
        this.suit = suit;
        this.name = name;
        this.value = value;
    }
    public String CardShow() {
        return this.name +" " + this.suit;
    }
}
