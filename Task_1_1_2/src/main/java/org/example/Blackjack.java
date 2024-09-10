package org.example;


public class Blackjack {
    public static void main(String[] args) {

        System.out.println("Добро пожаловать в Блэкджек!");

        Card[] deck = new Card[52];

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
        for (int j = 0; j < 52; j++) {
            System.out.println(deck[j].CardShow());
        }
    }
}