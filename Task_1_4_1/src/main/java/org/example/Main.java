package org.example;

/**
 * Главный класс для демонстрации работы с классом GradeBook.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки (не используются в данной программе).
     */
    public static void main(String[] args) {
        GradeBook book = new GradeBook(true);

        book.addGrade("Математика", "Экзамен", 1, 5);
        book.addGrade("Пак", "Экзамен", 1, 4);
        book.addGrade("ООП", "Экзамен", 2, 4);
        book.addGrade("Английский", "Зачет", 3, 4);
        book.addGrade("Оси", "Экзамен", 4, 4);

        System.out.println(book);
    }
}
