package org.example;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс, представляющий направленный граф с использованием матрицы смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    private final List<List<Integer>> matrix;
    private int numVertices;

    /**
     * Конструктор, инициализирующий граф с заданным числом вершин.
     *
     * @param vertices количество вершин в графе
     */
    public AdjacencyMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.matrix = new ArrayList<>(vertices);

        // Инициализируем матрицу нулями
        for (int i = 0; i < vertices; i++) {
            List<Integer> row = new ArrayList<>(vertices);
            for (int j = 0; j < vertices; j++) {
                row.add(0);
            }
            matrix.add(row);
        }
    }

    /**
     * Добавляет новую вершину в граф. Модифицирует матрицу смежности для учета новой вершины.
     */
    @Override
    public void addVertex() {
        numVertices++;
        // Добавляем новую строку с нулями
        List<Integer> row = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            row.add(0);
        }
        matrix.add(row);

        // Добавляем 0 к каждой уже существующей строке
        for (int i = 0; i < numVertices - 1; i++) {
            matrix.get(i).add(0);
        }
    }

    /**
     * Удаляет последнюю вершину из графа и модифицирует матрицу смежности.
     */
    @Override
    public void removeVertex() {
        if (numVertices == 0) return; // Нельзя удалить вершину, если граф пуст
        numVertices--;
        matrix.remove(numVertices); // Удаляем последнюю строку

        // Удаляем последний элемент из каждой строки
        for (int i = 0; i < numVertices; i++) {
            matrix.get(i).remove(numVertices);
        }
    }

    /**
     * Добавляет ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // Проверяем существование вершин
            matrix.get(source).set(destination, 1);
        }
    }

    /**
     * Удаляет ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    @Override
    public void removeEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) {
            matrix.get(source).set(destination, 0);
        }
    }

    /**
     * Возвращает список соседних вершин для указанной вершины.
     *
     * @param vertex вершина для поиска соседей
     * @return список соседних вершин
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (vertex < numVertices) {
            for (int i = 0; i < numVertices; i++) {
                if (matrix.get(vertex).get(i) == 1) {
                    neighbors.add(i);
                }
            }
        }
        return neighbors;
    }

    /**
     * Читает граф из файла. Формат файла: ребра задаются в виде "источник,назначение".
     *
     * @param filePath путь к файлу
     * @throws Exception если файл не удается прочитать
     */
    @Override
    public void readFromFile(String filePath) throws Exception {
        try (FileReader fileReader = new FileReader(filePath)) {
            Scanner scannerFile = new Scanner(fileReader);
            String line = scannerFile.nextLine();
            String[] edges = line.split(" ");
            for (String edge : edges) {
                String[] pair = edge.split(",");
                this.addEdge(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Возвращает строковое представление графа в виде матрицы смежности.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n\t");
        sb.append("* ".repeat(numVertices));
        sb.append("\n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" * ");
            for (Integer cell : matrix.get(i)) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
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
     * Выполняет топологическую сортировку графа (алгоритм Кана).
     *
     * @return список вершин в топологическом порядке
     * @throws GraphCycleException если граф содержит цикл, сортировка невозможна
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        int[] inDegree = new int[numVertices];  // Входная степень для каждой вершины
        List<Integer> topOrder = new ArrayList<>();

        // Подсчет входных степеней вершин
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrix.get(i).get(j) == 1) {
                    inDegree[j]++;
                }
            }
        }

        // Поиск всех вершин с входной степенью 0
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
            throw new GraphCycleException("Граф содержит циклы, топологическая сортировка невозможна.");
        }

        return topOrder;
    }
}
