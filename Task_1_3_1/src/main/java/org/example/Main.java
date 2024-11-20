package org.example;

import java.io.*;
import java.util.List;

import static org.example.SubstringFinder.find;

/**
 * Основной класс для запуска программы.
 */
public class Main {

    /**
     * Главный метод для запуска поиска подстроки в файле.
     *
     * @param args аргументы командной строки (не используются в этом примере).
     */
    public static void main(String[] args) {
        try {
            List<Integer> result = find("input.txt", "бра");
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
