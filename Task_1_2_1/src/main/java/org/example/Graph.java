package org.example;

import java.util.List;

public interface Graph {
    // Добавление новой вершины
    void addVertex();

    // Удаление последней вершины
    void removeVertex();

    // Добавление ребра
    void addEdge(int source, int destination);

    // Удаление ребра
    void removeEdge(int source, int destination);

    // Получение списка соседей вершины
    List<Integer> getNeighbors(int vertex);

    // Чтение графа из файла
    void readFromFile(String filePath) throws Exception;

    // Вывод графа в строку
    String toString();

    // Сравнение графов
    boolean equals(Object o);

    // Топологическая сортировка
    List<Integer> topologicalSort();
}