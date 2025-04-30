package org.example;

/** Класс Main запускает игру в Блэкджек. */
public class Main {

  /**
   * Точка входа в приложение. Создает объект игры BlackJack и запускает игру.
   *
   * @param args Аргументы командной строки (не используются).
   */
  public static void main(String[] args) {
    BlackJack game = new BlackJack();

    game.startGame();
  }
}
