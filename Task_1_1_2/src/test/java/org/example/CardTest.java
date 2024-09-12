package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardShow() {
        Card card = new Card("Червы", "Двойка", 2);
        String card1 = "Двойка Червы (2)";
        assertEquals(card.CardShow(), card1,
                "Функция должна выводить строку, ожидаемого формата.");
    }
}