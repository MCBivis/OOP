package org.example;

import java.util.Random;

public class Deck {

    Card[] deck;
    private static final Random random = new Random();
    public Deck() {
        deck = new Card[52];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 13; i++) {
                String name = Card.NAMES[i];
                String suit = Card.SUITS[j];
                int value;
                if (i<9){
                    value=i+2;
                }else if (i<12){
                    value=10;
                }else{
                    value=11;
                }
                deck[i+13*j] =new Card(suit,name,value);
            }
        }
    }
    public void shuffle() {
        for(int i= deck.length-1; i>=0; i--) {
            int index = random.nextInt(i + 1);
            System.out.println(index);
            Card temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;
        }
    }
}
