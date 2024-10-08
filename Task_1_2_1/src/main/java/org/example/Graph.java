package org.example;

import java.util.List;

public interface Graph {
    // Операции над графом
    void addVertex();
    void removeVertex();
    void addEdge(int source, int destination);
    void removeEdge(int source, int destination);

    // Получение списка соседей вершины
    List<Integer> getNeighbors(int vertex);

    // Чтение графа из файла
    void readFromFile(String filePath);

    // Сравнение графов
    boolean equals(Object o);

    // Строковое представление графа
    String toString();

    // Топологическая сортировка
    List<Integer> topologicalSort();
}