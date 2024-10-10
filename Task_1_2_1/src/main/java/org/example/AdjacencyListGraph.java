package org.example;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс, представляющий граф с использованием списка смежности.
 */
public class AdjacencyListGraph implements Graph {
    private final List<List<Integer>> adjList;
    private int numVertices;

    /**
     * Конструктор, инициализирующий граф с заданным количеством вершин.
     *
     * @param vertices количество вершин в графе
     */
    public AdjacencyListGraph(int vertices) {
        this.numVertices = vertices;
        this.adjList = new ArrayList<>(vertices);

        // Инициализация списка смежности
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    /**
     * Добавляет новую вершину в граф. Модифицирует список смежности.
     */
    @Override
    public void addVertex() {
        numVertices++;
        adjList.add(new ArrayList<>());
    }

    /**
     * Удаляет последнюю вершину из графа и все ребра, ведущие к ней.
     */
    @Override
    public void removeVertex() {
        if (numVertices == 0) return; // Проверка на пустой граф
        numVertices--;
        adjList.remove(numVertices); // Удаляем последнюю вершину

        // Удаляем все ссылки на эту вершину из смежных списков
        for (List<Integer> neighbors : adjList) {
            neighbors.remove((Integer) numVertices); // Удаляем вершину как объект Integer
        }
    }

    /**
     * Добавляет направленное ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // Проверка существования вершин
            adjList.get(source).add(destination);
        }
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    @Override
    public void removeEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // Проверка существования вершин
            adjList.get(source).remove((Integer) destination); // Удаление ребра
        }
    }

    /**
     * Возвращает список соседних вершин для указанной вершины.
     *
     * @param vertex вершина для поиска соседей
     * @return список смежных вершин
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex < numVertices) {
            return new ArrayList<>(adjList.get(vertex)); // Возвращаем копию списка смежных вершин
        }
        return new ArrayList<>();
    }

    /**
     * Читает граф из файла. Формат файла: ребра задаются в виде "источник,назначение".
     *
     * @param filePath путь к файлу
     * @throws Exception если файл не удается прочитать
     */
    @Override
    public void readFromFile(String filePath) throws Exception {
        FileReader fileReader = new FileReader(filePath);
        Scanner scannerFile = new Scanner(fileReader);
        String line = scannerFile.nextLine();
        fileReader.close();
        String[] edges = line.split(" ");
        for (String edge : edges) {
            String[] pair = edge.split(",");
            this.addEdge(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
        }
    }

    /**
     * Возвращает строковое представление графа в виде списка смежности.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(": ").append(adjList.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Проверяет равенство двух графов по их строковому представлению.
     *
     * @param o объект для сравнения
     * @return true, если графы равны, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString());
    }

    /**
     * Выполняет топологическую сортировку графа с использованием алгоритма Кана.
     *
     * @return список вершин в топологическом порядке
     * @throws Exception если граф содержит цикл, сортировка невозможна
     */
    @Override
    public List<Integer> topologicalSort() throws Exception {
        int[] inDegree = new int[numVertices];  // Входная степень для каждой вершины
        List<Integer> topOrder = new ArrayList<>();

        // Подсчет входных степеней вершин
        for (int i = 0; i < numVertices; i++) {
            for (int neighbor : adjList.get(i)) {
                inDegree[neighbor]++;
            }
        }

        // Найдем все вершины с входной степенью 0
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < numVertices; i++) {
            if (inDegree[i] == 0) {
                stack.push(i);
            }
        }

        // Алгоритм Кана
        while (!stack.isEmpty()) {
            int current = stack.pop();
            topOrder.add(current);

            // Уменьшаем входную степень всех соседей текущей вершины
            for (int neighbor : getNeighbors(current)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    stack.push(neighbor);
                }
            }
        }

        // Проверка на наличие циклов
        if (topOrder.size() != numVertices) {
            throw new Exception("Граф содержит циклы, топологическая сортировка невозможна.");
        }

        return topOrder;
    }
}
