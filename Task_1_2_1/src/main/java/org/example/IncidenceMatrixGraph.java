package org.example;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс, представляющий граф с помощью матрицы инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private final List<List<Integer>> matrix;
    private int numVertices;
    private int numEdges;

    /**
     * Конструктор, инициализирующий граф с заданным количеством вершин.
     *
     * @param vertices количество вершин в графе
     */
    public IncidenceMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.numEdges = 0;
        this.matrix = new ArrayList<>(vertices);

        // Инициализируем матрицу пустыми строками для каждой вершины
        for (int i = 0; i < vertices; i++) {
            List<Integer> row = new ArrayList<>();
            matrix.add(row);
        }
    }

    /**
     * Добавляет новую вершину в граф. Модифицирует матрицу инцидентности.
     */
    @Override
    public void addVertex() {
        numVertices++;
        // Добавляем новую строку для новой вершины
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < numEdges; i++) {
            row.add(0);
        }
        matrix.add(row);
    }

    /**
     * Удаляет последнюю вершину из графа.
     */
    @Override
    public void removeVertex() {
        if (numVertices == 0) return; // Нельзя удалить вершину, если граф пуст
        numVertices--;
        matrix.remove(numVertices);
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
            // Добавляем новый столбец для ребра
            for (int i = 0; i < numVertices; i++) {
                matrix.get(i).add(0); // Инициализируем новый столбец нулями
            }

            matrix.get(source).set(numEdges, 1); // Исходная вершина (начало ребра)
            matrix.get(destination).set(numEdges, 2); // Конечная вершина (конец ребра)
            numEdges++;
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
        for (int j = 0; j < numEdges; j++) {
            if (matrix.get(source).get(j) == 1 && matrix.get(destination).get(j) == 2) {
                for (int i = 0; i < numVertices; i++) {
                    matrix.get(i).remove(j); // Удаляем столбец
                }
                numEdges--;
                break;
            }
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
            for (int j = 0; j < numEdges; j++) {
                if (matrix.get(vertex).get(j) == 1) { // Если вершина — начальная вершина ребра
                    for (int i = 0; i < numVertices; i++) {
                        if (matrix.get(i).get(j) == 2) { // Найдём конечную вершину ребра
                            neighbors.add(i);
                            break;
                        }
                    }
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
     * Возвращает строковое представление графа в виде матрицы инцидентности.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i = 0; i < numEdges; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n\t");
        sb.append("* ".repeat(numEdges));
        sb.append("\n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" * ");
            for (int j = 0; j < numEdges; j++) {
                sb.append(matrix.get(i).get(j)).append(" ");
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
            for (int j = 0; j < numEdges; j++) {
                if (matrix.get(i).get(j) == 2) {
                    inDegree[i]++;
                }
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
            throw new GraphCycleException("Граф содержит циклы, топологическая сортировка невозможна.");
        }

        return topOrder;
    }
}
