package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для поиска всех вхождений подстроки в файле.
 */
public class SubstringFinder {

    /**
     * Находит все вхождения заданной подстроки в файле.
     *
     * @param fileName имя файла, в котором осуществляется поиск.
     * @param searchString подстрока, которую нужно найти.
     * @return список позиций начала каждого вхождения подстроки.
     * @throws IOException если возникла ошибка при чтении файла.
     */
    public static List<Integer> find(String fileName, String searchString) throws IOException {
        List<Integer> indices = new ArrayList<>();
        int searchLength = searchString.length();
        StringBuilder window = new StringBuilder();

        int position = 0;

        try (FileReader reader = new FileReader(fileName, StandardCharsets.UTF_8)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                window.append((char) ch);

                if (window.length() > searchLength) {
                    window.deleteCharAt(0);
                }

                if (equals(window, searchString, searchLength)) {
                    indices.add(position - searchLength + 1);
                }

                position++;
            }
        }

        return indices;
    }

    /**
     * Проверяет, совпадает ли строка в StringBuilder с искомой подстрокой.
     *
     * @param strBuild строка в StringBuilder.
     * @param str искомая подстрока.
     * @param length длина искомой подстроки.
     * @return true, если строки совпадают, иначе false.
     */
    private static boolean equals(StringBuilder strBuild, String str, int length) {
        if (strBuild.length() != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (strBuild.charAt(i) != str.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
