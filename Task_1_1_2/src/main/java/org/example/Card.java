package org.example;

/**
 * Класс Card представляет собой карту с определенной мастью, названием и значением.
 */
public class Card {

    /**
     * Возможные масти карт.
     */
    public static final String[] SUITS = {"Червы", "Пики", "Трефы", "Бубны"};

    /**
     * Возможные названия карт.
     */
    public static final String[] NAMES = {"Двойка", "Тройка", "Четвёрка", "Пятёрка",
            "Шестёрка", "Семёрка", "Восьмёрка", "Девятка",
            "Десятка", "Валет", "Дама", "Король", "Туз"};
    /**
     * Масть карты.
     */
    private final String suit;

    /**
     * Название карты.
     */
    private final String name;

    /**
     * Значение карты.
     */
    public int value;

    /**
     * Конструктор класса Card.
     *
     * @param suit  Масть карты.
     * @param name  Название карты.
     * @param value Значение карты.
     */
    public Card(String suit, String name, int value) {
        this.suit = suit;
        this.name = name;
        this.value = value;
    }

    /**
     * Метод возвращает строковое представление карты.
     *
     * @return Строковое представление карты.
     */
    public String CardShow() {
        return this.name + " " + this.suit + " (" + this.value + ")";
    }
}