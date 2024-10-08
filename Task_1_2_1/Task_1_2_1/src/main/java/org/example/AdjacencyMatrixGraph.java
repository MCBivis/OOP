package org.example;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] matrix;
    private int numVertices;

    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        matrix = new int[numVertices][numVertices];
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= numVertices) {
            resizeMatrix(vertex + 1);
        }
    }

    private void resizeMatrix(int newSize) {
        int[][] newMatrix = new int[newSize][newSize];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        matrix = newMatrix;
        numVertices = newSize;
    }

    @Override
    public void removeVertex(int vertex) {
        // Логика для удаления вершины
        // В данном случае придется обновлять матрицу и удалять соответствующие строки и столбцы
    }

    @Override
    public void addEdge(int source, int destination) {
        matrix[source][destination] = 1;
    }

    @Override
    public void removeEdge(int source, int destination) {
        matrix[source][destination] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (matrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filePath) {
        // Реализация чтения из файла
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) o;
        if (numVertices != that.numVertices) return false;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrix[i][j] != that.matrix[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                sb.append(matrix[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        // Реализация топологической сортировки для матрицы смежности
        return null;
    }
}
