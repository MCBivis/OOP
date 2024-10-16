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

        Graph test = new IncidenceMatrixGraph(5);

        test.addEdge(0, 1);
        test.addEdge(0, 2);
        test.addEdge(0, 3);

        try {
            test.readFromFile("graphData.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(test);

        try {
            System.out.println(test.topologicalSort());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
