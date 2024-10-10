package org.example;

/**
 * Главный класс для тестирования работы графа.
 */
public class Main {

    /**
     * Основной метод программы для выполнения тестирования графа.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        // Создаем граф на основе матрицы инцидентности с 5 вершинами
        Graph test = new IncidenceMatrixGraph(5);

        // Добавляем ребра в граф
        test.addEdge(0, 1);
        test.addEdge(0, 2);
        test.addEdge(0, 3);

        // Чтение графа из файла
        try {
            test.readFromFile("graphData.txt");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // Вывод графа
        System.out.println(test);

        // Попытка выполнить топологическую сортировку
        try {
            System.out.println("Топологическая сортировка: " + test.topologicalSort());
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении топологической сортировки: " + e.getMessage());
        }
    }
}
