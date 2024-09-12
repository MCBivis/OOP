package org.example;

import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {

        System.out.println("Добро пожаловать в Блэкджек!");
        int round = 0;
        int PlayerScore = 0;
        int DealerScore = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            round++;
            System.out.println("Раунд " + round);
            Deck deck = new Deck();
            deck.shuffle();
            Hand PlayerHand = new Hand();
            Hand DealerHand = new Hand();
            PlayerHand.add(deck.draw());
            PlayerHand.add(deck.draw());
            DealerHand.add(deck.draw());
            DealerHand.add(deck.draw());
            System.out.println("Дилер раздал карты");
            System.out.println("\tВаши карты: " + PlayerHand.HandShow() + " => " + PlayerHand.score);
            System.out.println("\tКарты дилера: " + DealerHand.HandShow(0) + "\n");
            if (PlayerHand.score == 21) {
                PlayerScore++;
                if (PlayerScore > DealerScore) {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " в вашу пользу.\n");
                } else if (PlayerScore < DealerScore) {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " не в вашу пользу.\n");
                } else {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + ".\n");
                }
                continue;
            }
            System.out.println("Ваш ход\n------");
            int choice = 1;
            while (choice != 0) {
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
                choice = scanner.nextInt();
                if (choice == 1) {
                    PlayerHand.add(deck.draw());
                    System.out.println("Вы открыли карту " + PlayerHand.cards[PlayerHand.InHand-1].CardShow());
                    System.out.println("\tВаши карты: " + PlayerHand.HandShow() + " => " + PlayerHand.score);
                    System.out.println("\tКарты дилера: " + DealerHand.HandShow(0) + "\n");
                }
                if (PlayerHand.score>21) {
                    choice = 0;
                }
            }
            if (PlayerHand.score>21) {
                DealerScore++;
                if (PlayerScore > DealerScore) {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " в вашу пользу.\n");
                } else if (PlayerScore < DealerScore) {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " не в вашу пользу.\n");
                } else {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + ".\n");
                }
                continue;
            }
            System.out.println("Ход дилера\n------");
            System.out.println("Дилер открывает закрытую карту " + DealerHand.cards[DealerHand.InHand-1].CardShow());
            System.out.println("\tВаши карты: " + PlayerHand.HandShow() + " => " + PlayerHand.score);
            System.out.println("\tКарты дилера: " + DealerHand.HandShow()+ " => " + DealerHand.score + "\n");
            while (DealerHand.score<17) {
                DealerHand.add(deck.draw());
                System.out.println("Дилер открывает карту " + DealerHand.cards[DealerHand.InHand-1].CardShow());
                System.out.println("\tВаши карты: " + PlayerHand.HandShow() + " => " + PlayerHand.score);
                System.out.println("\tКарты дилера: " + DealerHand.HandShow()+ " => " + DealerHand.score + "\n");
            }
            if (DealerHand.score>21 || DealerHand.score<PlayerHand.score) {
                PlayerScore++;
                if (PlayerScore > DealerScore) {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " в вашу пользу.\n");
                } else if (PlayerScore < DealerScore) {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " не в вашу пользу.\n");
                } else {
                    System.out.println("Вы выиграли раунд! Счет " + PlayerScore + ":" + DealerScore + ".\n");
                }
            } else if (DealerHand.score>PlayerHand.score) {
                DealerScore++;
                if (PlayerScore > DealerScore) {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " в вашу пользу.\n");
                } else if (PlayerScore < DealerScore) {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + " не в вашу пользу.\n");
                } else {
                    System.out.println("Вы проиграли раунд! Счет " + PlayerScore + ":" + DealerScore + ".\n");
                }
            } else {
                if (PlayerScore > DealerScore) {
                    System.out.println("Ничья! Счет " + PlayerScore + ":" + DealerScore + " в вашу пользу.\n");
                } else if (PlayerScore < DealerScore) {
                    System.out.println("Ничья! Счет " + PlayerScore + ":" + DealerScore + " не в вашу пользу.\n");
                } else {
                    System.out.println("Ничья! Счет " + PlayerScore + ":" + DealerScore + ".\n");
                }
            }
        }
    }
}