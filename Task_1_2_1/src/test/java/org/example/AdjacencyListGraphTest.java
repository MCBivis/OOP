package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор тестов для проверки работы графа на основе списка смежности.
 */
public class AdjacencyListGraphTest {

    private AdjacencyListGraph graph;

    /**
     * Тест метода toString().
     * Проверяет, что метод корректно выводит строковое представление графа.
     */
    @Test
    public void testToString() {
        graph = new AdjacencyListGraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        // Ожидаемый вывод
        String expectedOutput = "0: [1]\n1: [2]\n2: []\n";

        // Сравниваем фактический вывод с ожидаемым
        assertEquals(expectedOutput, graph.toString());
    }

    /**
     * Тест метода addVertex().
     * Проверяет, что вершина добавляется корректно.
     */
    @Test
    public void testAddVertex() {
        graph = new AdjacencyListGraph(4);
        assertEquals(4, graph.toString().split("\n").length); // Проверяем, что сейчас 4 вершины
        graph.addVertex();
        assertEquals(5, graph.toString().split("\n").length); // Теперь должно быть 5
    }

    /**
     * Тест метода removeVertex().
     * Проверяет, что вершина корректно удаляется.
     */
    @Test
    public void testRemoveVertex() {
        graph = new AdjacencyListGraph(4);
        graph.addVertex(); // Добавляем вершину
        assertEquals(5, graph.toString().split("\n").length); // Проверяем, что 5 вершин
        graph.removeVertex(); // Удаляем последнюю вершину
        assertEquals(4, graph.toString().split("\n").length); // Должно остаться 4
    }

    /**
     * Тест метода addEdge(int source, int destination).
     * Проверяет, что ребро корректно добавляется.
     */
    @Test
    public void testAddEdge() {
        graph = new AdjacencyListGraph(4);
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1)); // Проверяем, что вершина 1 добавлена в соседи 0
    }

    /**
     * Тест метода removeEdge(int source, int destination).
     * Проверяет, что ребро корректно удаляется.
     */
    @Test
    public void testRemoveEdge() {
        graph = new AdjacencyListGraph(4);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertFalse(neighbors.contains(1)); // Проверяем, что вершина 1 удалена из соседей 0
    }

    /**
     * Тест метода getNeighbors(int vertex).
     * Проверяет, что список соседей вершины возвращается корректно.
     */
    @Test
    public void testGetNeighbors() {
        graph = new AdjacencyListGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size()); // У вершины 0 должно быть 2 соседа
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    /**
     * Тест метода topologicalSort().
     * Проверяет корректность топологической сортировки графа.
     *
     * @throws Exception если в графе присутствуют циклы.
     */
    @Test
    public void testTopologicalSort() throws Exception {
        graph = new AdjacencyListGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);

        List<Integer> expectedOutput = new ArrayList<>(5);
        expectedOutput.add(4);
        expectedOutput.add(0);
        expectedOutput.add(3);
        expectedOutput.add(1);
        expectedOutput.add(2);

        assertEquals(expectedOutput, graph.topologicalSort());
    }

    /**
     * Тест метода equals(Object o).
     * Проверяет корректность сравнения графов.
     */
    @Test
    public void testEquals() {
        graph = new AdjacencyListGraph(4);
        AdjacencyMatrixGraph g1 = new AdjacencyMatrixGraph(4);
        AdjacencyMatrixGraph g2 = new AdjacencyMatrixGraph(4);
        g1.addEdge(0, 1);
        g2.addEdge(0, 1);
        assertTrue(g1.equals(g2));

        g2.addEdge(1, 2);
        assertFalse(g1.equals(g2));
    }

    /**
     * Тест метода readFromFile(String filePath).
     * Проверяет, что граф корректно читается из файла.
     *
     * @throws Exception если при чтении файла возникнет ошибка.
     */
    @Test
    public void testReadFromFile() throws Exception {
        graph = new AdjacencyListGraph(4);
        graph.readFromFile("graphData.txt");
        assertEquals(2, graph.getNeighbors(1).get(0));
        assertEquals(2, graph.getNeighbors(3).get(0));
    }
}