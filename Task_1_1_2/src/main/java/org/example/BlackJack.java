package org.example;

import java.util.Scanner;

/**
 * Класс BlackJack представляет основную логику игры в Блэкджек.
 */
public class BlackJack {
    private int round;
    private int playerScore;
    private int dealerScore;
    private final Scanner scanner;

    /**
     * Конструктор инициализирует игру с начальным счетом и вводом с клавиатуры.
     */
    public BlackJack() {
        this.round = 0;
        this.playerScore = 0;
        this.dealerScore = 0;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Метод запускает основной цикл игры.
     */
    public void startGame() {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            Deck deck = new Deck();
            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();

            // Начало раунда
            roundStart(deck, playerHand, dealerHand);

            // Проверка на мгновенную победу
            if (playerHand.score == 21) {
                win();
                continue;
            }

            // Ход игрока
            playerTurn(deck, playerHand, dealerHand);

            // Проверка на проигрыш из-за перебора у игрока
            if (playerHand.score > 21) {
                lose();
                continue;
            }

            // Ход дилера
            dealerTurn(deck, playerHand, dealerHand);

            // Проверка результата после хода дилера
            if (dealerHand.score > 21 || dealerHand.score < playerHand.score) {
                win();
            } else if (dealerHand.score > playerHand.score) {
                lose();
            } else {
                draw();
            }
        }
    }

    /**
     * Метод, который перемешивает колоду, раздает карты игроку и дилеру.
     *
     * @param deck       Колода карт.
     * @param playerHand Рука игрока.
     * @param dealerHand Рука дилера.
     */
    private void roundStart(Deck deck, Hand playerHand, Hand dealerHand) {
        this.round++;
        System.out.println("Раунд " + this.round);
        deck.shuffle();

        // Раздача карт
        playerHand.add(deck.draw());
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        dealerHand.add(deck.draw());

        // Вывод информации о раздаче
        System.out.println("Дилер раздал карты");
        System.out.println("\tВаши карты: " + playerHand.HandShow() + " => " + playerHand.score);
        System.out.println("\tКарты дилера: " + dealerHand.HandShow(0) + "\n");
    }

    /**
     * Метод реализует ход игрока, в котором он может брать новые карты.
     *
     * @param deck       Колода карт.
     * @param playerHand Рука игрока.
     * @param dealerHand Рука дилера.
     */
    private void playerTurn(Deck deck, Hand playerHand, Hand dealerHand) {
        System.out.println("Ваш ход\n------");
        int choice = 1;

        while (choice != 0) {
            System.out.println("Введите '1', чтобы взять карту, и '0', чтобы остановиться...");
            choice = this.scanner.nextInt();

            if (choice == 1) {
                playerHand.add(deck.draw());
                System.out.println("Вы открыли карту " + playerHand.cards[playerHand.InHand - 1].CardShow());
                System.out.println("\tВаши карты: " + playerHand.HandShow() + " => " + playerHand.score);
                System.out.println("\tКарты дилера: " + dealerHand.HandShow(0) + "\n");
            }

            // Если игрок набрал более 21 очка, его ход автоматически завершен
            if (playerHand.score > 21) {
                choice = 0;
            }
        }
    }

    /**
     * Метод реализует ход дилера. Дилер берет карты, пока его счет не будет 17 или больше.
     *
     * @param deck       Колода карт.
     * @param playerHand Рука игрока.
     * @param dealerHand Рука дилера.
     */
    private void dealerTurn(Deck deck, Hand playerHand, Hand dealerHand) {
        System.out.println("Ход дилера\n------");
        System.out.println("Дилер открывает закрытую карту " + dealerHand.cards[dealerHand.InHand - 1].CardShow());
        System.out.println("\tВаши карты: " + playerHand.HandShow() + " => " + playerHand.score);
        System.out.println("\tКарты дилера: " + dealerHand.HandShow() + " => " + dealerHand.score + "\n");

        // Дилер берет карты, пока его счет меньше 17
        while (dealerHand.score < 17) {
            dealerHand.add(deck.draw());
            System.out.println("Дилер открывает карту " + dealerHand.cards[dealerHand.InHand - 1].CardShow());
            System.out.println("\tВаши карты: " + playerHand.HandShow() + " => " + playerHand.score);
            System.out.println("\tКарты дилера: " + dealerHand.HandShow() + " => " + dealerHand.score + "\n");
        }
    }

    /**
     * Метод вызывается при победе игрока в раунде.
     */
    private void win() {
        this.playerScore++;
        System.out.println("Вы выиграли раунд! Счет " + this.playerScore + ":" + this.dealerScore + "\n");
    }

    /**
     * Метод вызывается при проигрыше игрока в раунде.
     */
    private void lose() {
        this.dealerScore++;
        System.out.println("Вы проиграли раунд! Счет " + this.playerScore + ":" + this.dealerScore + "\n");
    }

    /**
     * Метод вызывается, если раунд закончился ничьей.
     */
    private void draw() {
        System.out.println("Ничья! Счет " + this.playerScore + ":" + this.dealerScore + "\n");
    }
}
