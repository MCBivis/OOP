package org.example;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;

public class AdjacencyMatrixGraph implements Graph {
    private List<List<Integer>> matrix;
    private int numVertices;

    // Конструктор: инициализируем граф с заданным числом вершин
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

    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) {
            matrix.get(source).set(destination, 1); // Устанавливаем связь
        }
    }

    @Override
    public void removeEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) {
            matrix.get(source).set(destination, 0); // Убираем связь
        }
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i=0;i<numVertices;i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n\t");
        sb.append("* ".repeat(numVertices));
        sb.append("\n");
        for (int i=0;i<numVertices;i++) {
            sb.append(i).append(" * ");
            for (Integer cell : matrix.get(i)) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString());
    }

    @Override
    public List<Integer> topologicalSort() {
        // Реализация топологической сортировки
        return null;
    }
}
