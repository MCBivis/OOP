package org.example;

import java.util.Random;

/** Класс Deck представляет собой колоду карт. */
public class Deck {

  /** Массив карт, представляющий колоду. */
  public Card[] deck;

  /** Объект Random для случайного перемешивания колоды. */
  private static final Random random = new Random();

  /** Индекс верхней карты в колоде, которая будет вытянута. */
  private int topDeck;

  /** Конструктор класса Deck. Создает новую колоду из 52 карт (по 13 карт на каждую масть). */
  public Deck() {
    deck = new Card[52];
    topDeck = 0;
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        String name = Card.NAMES[i];
        String suit = Card.SUITS[j];
        int value;
        if (i < 9) {
          value = i + 2;
        } else if (i < 12) {
          value = 10;
        } else {
          value = 11;
        }
        deck[i + 13 * j] = new Card(suit, name, value);
      }
    }
  }

  /**
   * Метод shuffle() перемешивает колоду, изменяя порядок карт случайным образом. Используется
   * алгоритм перемешивания Фишера-Йетса.
   */
  public void shuffle() {
    for (int i = deck.length - 1; i >= 0; i--) {
      int index = random.nextInt(i + 1);
      Card temp = deck[i];
      deck[i] = deck[index];
      deck[index] = temp;
    }
  }

  /**
   * Метод draw() вытягивает верхнюю карту из колоды.
   *
   * @return Вытянутая карта, или null, если все карты уже вытянуты.
   */
  public Card draw() {
    if (topDeck >= deck.length) {
      return null;
    }
    Card temp = deck[topDeck];
    deck[topDeck++] = null;
    return temp;
  }
}
