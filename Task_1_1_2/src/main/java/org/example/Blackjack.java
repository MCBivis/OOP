package org.example;

public class Blackjack {
    public static void main(String[] args) {

        System.out.println("Добро пожаловать в Блэкджек!");

        Deck deck = new Deck();
        deck.shuffle();
        for (int j = 0; j < 52; j++) {
            System.out.println(deck.deck[j].CardShow());
        }
    }
}