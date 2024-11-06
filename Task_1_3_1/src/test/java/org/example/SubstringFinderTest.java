package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса SubstringFinder.
 */
class SubstringFinderTest {

    /**
     * Тест для проверки нахождения подстроки в файле.
     * Ищется подстрока "бра" в файле "input.txt".
     * Ожидаемые индексы начала вхождений: 1 и 8.
     *
     * @throws IOException если произошла ошибка при чтении файла.
     */
    @Test
    public void testFindSubstring() throws IOException {
        List<Integer> indices = SubstringFinder.find("input.txt", "бра");

        assertEquals(2, indices.size());

        assertTrue(indices.contains(1));
        assertTrue(indices.contains(8));
    }

    /**
     * Тест для проверки поиска несуществующей подстроки в файле.
     * Ищется подстрока "неприсутственная" в файле "input.txt".
     * Ожидается, что подстрока не будет найдена.
     *
     * @throws IOException если произошла ошибка при чтении файла.
     */
    @Test
    public void testFindNonExistingSubstring() throws IOException {
        List<Integer> indices = SubstringFinder.find("input.txt", "такой строки нет в файле");

        assertTrue(indices.isEmpty());
    }

    /**
     * Тест для проверки нахождения подстроки, расположенной в начале строки.
     * Ищется подстрока "абра" в файле "input.txt".
     * Ожидается, что подстрока будет найдена на позиции 0.
     *
     * @throws IOException если произошла ошибка при чтении файла.
     */
    @Test
    public void testFindSubstringAtStart() throws IOException {
        List<Integer> indices = SubstringFinder.find("input.txt", "абра");

        assertEquals(2, indices.size());

        assertTrue(indices.contains(0));
    }

    /**
     * Тест для проверки нахождения подстроки в большом файле.
     * Генерируется файл с миллионами повторяющихся подстрок "абра", и ищется подстрока "бра".
     * Ожидается, что количество вхождений будет равно количеству повторений.
     *
     * @throws IOException если произошла ошибка при чтении файла.
     */
    @Test
    public void testFindSubstringInBigString() throws IOException {
        try (FileWriter writer = new FileWriter("test.txt", true)) {
            writer.write("абра".repeat(2000000));
        }

        List<Integer> indices = SubstringFinder.find("test.txt", "бра");

        assertEquals(2000000, indices.size());

        File file = new File("test.txt");
        if (file.exists()) {
            assertTrue(file.delete());
        }
    }
}
