package org.example;

public class Hand {
    Card[] cards;
    int InHand;
    int score;

    public Hand() {
        cards = new Card[21];
        InHand = 0;
        score = 0;
    }
    public void add(Card card) {
        cards[InHand++] = card;
        score += card.value;
        updateScore();
    }
    public String HandShow() {
        String temp = "[";
        for (int i = 0; i < InHand-1; i++) {
            temp += cards[i].CardShow() + ", ";
        }
        temp += cards[InHand-1].CardShow();
        return temp + "]";
    }
    public String HandShow(int hidden) {
        String temp = "[";
        for (int i = 0; i < InHand-1; i++) {
            temp += cards[i].CardShow() + ", ";
        }
        temp += "<закрытая карта>";
        return temp + "]";
    }
    private void updateScore(){
        for(int i = 0; i < InHand; i++){
            if(score>21 && cards[i].value==11){
                cards[i].value=1;
                score -= 10;
            }
        }
    }
}
