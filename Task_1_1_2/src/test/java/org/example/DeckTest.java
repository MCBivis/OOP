package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void shuffle() {
        Deck deck = new Deck();
        Deck testdeck = new Deck();
        testdeck.shuffle();
        boolean isShuffled = false;
        for (int i=0;i<deck.deck.length;i++){
            if (!deck.deck[i].equals(testdeck.deck[i])) {
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled, "Колода должна быть перемешана, хотя бы одна карта должна сменить позицию.");
    }

    @Test
    void drawCards() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            Card card = deck.draw();
            assertNotNull(card, "Карты должны быть не null после вытягивания.");
        }
    }

    @Test
    void NullCheck() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            Card card = deck.draw();
            assertNull(deck.deck[i], "Карты в колоде должны быть null после вытягивания.");
        }
        assertNull(deck.draw(), "При вытягивании карты из пустой колоды должна возвращаться null.");
    }
}