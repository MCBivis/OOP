package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса GradeBook.
 */
class GradeBookTest {

    /**
     * Проверка корректного добавления оценок.
     */
    @Test
    public void testAddGradeWithValidData() {
        GradeBook gradeBook = new GradeBook(true);
        gradeBook.addGrade("Математика", "Экзамен", 1, 5);
        gradeBook.addGrade("Английский", "Зачет", 2, 4);
        assertEquals(4.5, gradeBook.calculateAverageGrade());
    }

    /**
     * Проверка добавления оценки с некорректным номером семестра.
     */
    @Test
    public void testAddGradeWithInvalidSemester() {
        GradeBook gradeBook = new GradeBook(true);
        assertThrows(IllegalArgumentException.class, () -> gradeBook.addGrade("Математика", "Экзамен", 9, 4));
    }

    /**
     * Проверка добавления оценки с некорректным значением балла.
     */
    @Test
    public void testAddGradeWithInvalidGrade() {
        GradeBook gradeBook = new GradeBook(true);
        assertThrows(IllegalArgumentException.class, () -> gradeBook.addGrade("Математика", "Экзамен", 1, 6));
    }

    /**
     * Проверка вычисления среднего балла, если нет ни одной добавленной оценки.
     */
    @Test
    public void testCalculateAverageGradeWithEmptyGrades() {
        GradeBook gradeBook = new GradeBook(true);
        assertEquals(0.0, gradeBook.calculateAverageGrade());
    }

    /**
     * Проверка возможности перевода на бюджетную форму обучения.
     */
    @Test
    public void testCanTransferToBudget() {
        GradeBook gradeBook = new GradeBook(true);
        gradeBook.addGrade("Математика", "Экзамен", 1, 4);
        gradeBook.addGrade("Английский", "Зачет", 2, 4);
        gradeBook.addGrade("ООП", "Экзамен", 3, 3);

        assertTrue(gradeBook.canTransferToBudget());

        gradeBook.addGrade("Оси", "Коллоквиум", 3, 3);

        assertFalse(gradeBook.canTransferToBudget());
    }

    /**
     * Проверка возможности получения красного диплома.
     */
    @Test
    public void testCanGetRedDiploma() {
        GradeBook gradeBook = new GradeBook(true);
        gradeBook.addGrade("Математика", "Экзамен", 1, 5);
        gradeBook.addGrade("Пак", "Экзамен", 2, 5);
        gradeBook.addGrade("ООП", "Экзамен", 3, 5);

        assertTrue(gradeBook.canGetRedDiploma());

        gradeBook.addGrade("Оси", "Защита ВКР", 8, 4);

        assertFalse(gradeBook.canGetRedDiploma());
    }

    /**
     * Проверка возможности получения повышенной стипендии.
     */
    @Test
    public void testCanGetIncreasedScholarship() {
        GradeBook gradeBook = new GradeBook(true);
        gradeBook.addGrade("Математика", "Экзамен", 1, 3);
        gradeBook.addGrade("Пак", "Экзамен", 2, 5);
        gradeBook.addGrade("ООП", "Экзамен", 2, 4);

        assertTrue(gradeBook.canGetIncreasedScholarship());

        gradeBook.addGrade("Оси", "Коллоквиум", 2, 3);

        assertFalse(gradeBook.canGetIncreasedScholarship());
    }

    /**
     * Проверка корректности вывода метода toString().
     */
    @Test
    public void testToStringOutput() {
        GradeBook gradeBook = new GradeBook(true);
        gradeBook.addGrade("Математика", "Экзамен", 1, 5);
        gradeBook.addGrade("Пак", "Экзамен", 1, 4);
        gradeBook.addGrade("ООП", "Экзамен", 2, 4);
        gradeBook.addGrade("Английский", "Зачет", 3, 4);
        gradeBook.addGrade("Оси", "Экзамен", 4, 4);

        String output = gradeBook.toString();
        assertTrue(output.contains("Зачетная книжка:"));
        assertTrue(output.contains("Средний балл: 4,20"));
        assertTrue(output.contains("Перевод на бюджет: Да"));
        assertTrue(output.contains("Красный диплом: Да"));
        assertTrue(output.contains("Повышенная стипендия: Да"));
    }
}
