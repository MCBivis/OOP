package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void add() {
        Hand hand = new Hand();
        Card card1 = new Card("Червы", "Туз", 11);
        Card card2 = new Card("Пики", "Десятка", 10);

        hand.add(card1);
        assertEquals(1, hand.InHand,
                "Количество карт в руке должно быть 1.");
        assertEquals(11, hand.score,
                "Счет должен быть 11 после добавления Туза.");

        hand.add(card2);
        assertEquals(2, hand.InHand,
                "Количество карт в руке должно быть 2.");
        assertEquals(21, hand.score,
                "Счет должен быть 21 после добавления Туза и Десятки.");

        Card card3 = new Card("Бубны", "Десятка", 10);
        hand.add(card3);
        assertEquals(3, hand.InHand,
                "Количество карт в руке должно быть 3.");
        assertEquals(21, hand.score,
                "Счет должен быть уменьшен до 21, так как Туз меняет значение на 1.");
    }

    @Test
    void handShow() {
        Hand hand = new Hand();
        Card card1 = new Card("Червы", "Туз", 11);
        Card card2 = new Card("Пики", "Десятка", 10);

        hand.add(card1);
        hand.add(card2);

        String handShow = "[Туз Червы (11), Десятка Пики (10)]";
        assertEquals(handShow, hand.HandShow(),
                "Функция должна выводить строку, ожидаемого формата.");

        String handShowHidden = "[Туз Червы (11), <закрытая карта>]";
        assertEquals(handShowHidden, hand.HandShow(1),
                "Функция должна выводить строку, ожидаемого формата.");
    }
}