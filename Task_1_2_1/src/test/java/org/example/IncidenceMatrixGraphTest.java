package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор тестов для проверки работы графа на основе матрицы инцидентности.
 */
public class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph graph;

    /**
     * Тест метода toString().
     * Проверяет корректность вывода графа в строку.
     */
    @Test
    public void testToString() {
        graph = new IncidenceMatrixGraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        // Ожидаемый вывод
        String expectedOutput = "\t0 1 \n\t* * \n0 * 1 0 \n1 * 2 1 \n2 * 0 2 \n";

        // Сравниваем фактический вывод с ожидаемым
        assertEquals(expectedOutput, graph.toString());
    }

    /**
     * Тест метода addVertex().
     * Проверяет корректность добавления вершины.
     */
    @Test
    public void testAddVertex() {
        graph = new IncidenceMatrixGraph(4);
        assertEquals(4, graph.toString().split("\n").length - 2); // Проверяем, что сейчас 4 вершины
        graph.addVertex();
        assertEquals(5, graph.toString().split("\n").length - 2); // Теперь должно быть 5
    }

    /**
     * Тест метода removeVertex().
     * Проверяет корректность удаления последней вершины.
     */
    @Test
    public void testRemoveVertex() {
        graph = new IncidenceMatrixGraph(4);
        graph.addVertex(); // Добавляем вершину
        assertEquals(5, graph.toString().split("\n").length - 2); // Проверяем, что 5 вершин
        graph.removeVertex(); // Удаляем последнюю вершину
        assertEquals(4, graph.toString().split("\n").length - 2); // Должно остаться 4
    }

    /**
     * Тест метода addEdge(int source, int destination).
     * Проверяет корректность добавления ребра.
     */
    @Test
    public void testAddEdge() {
        graph = new IncidenceMatrixGraph(4);
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1)); // Проверяем, что вершина 1 добавлена в соседи вершины 0
    }

    /**
     * Тест метода removeEdge(int source, int destination).
     * Проверяет корректность удаления ребра.
     */
    @Test
    public void testRemoveEdge() {
        graph = new IncidenceMatrixGraph(4);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertFalse(neighbors.contains(1)); // Проверяем, что вершина 1 удалена из соседей вершины 0
    }

    /**
     * Тест метода getNeighbors(int vertex).
     * Проверяет корректность возвращаемого списка соседей для заданной вершины.
     */
    @Test
    public void testGetNeighbors() {
        graph = new IncidenceMatrixGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size()); // У вершины 0 должно быть 2 соседа
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    /**
     * Тест метода topologicalSort().
     * Проверяет корректность выполнения топологической сортировки графа.
     *
     * @throws Exception если в графе присутствуют циклы.
     */
    @Test
    public void testTopologicalSort() throws Exception {
        graph = new IncidenceMatrixGraph(5);
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
     * Проверяет корректность сравнения графов на основе матрицы инцидентности.
     */
    @Test
    public void testEquals() {
        graph = new IncidenceMatrixGraph(4);
        IncidenceMatrixGraph g1 = new IncidenceMatrixGraph(4);
        IncidenceMatrixGraph g2 = new IncidenceMatrixGraph(4);
        g1.addEdge(0, 1);
        g2.addEdge(0, 1);
        assertTrue(g1.equals(g2));

        g2.addEdge(1, 2);
        assertFalse(g1.equals(g2));
    }

    /**
     * Тест метода readFromFile(String filePath).
     * Проверяет корректность чтения графа из файла.
     *
     * @throws Exception если при чтении файла возникла ошибка.
     */
    @Test
    public void testReadFromFile() throws Exception {
        graph = new IncidenceMatrixGraph(4);
        graph.readFromFile("graphData.txt");
        assertEquals(2, graph.getNeighbors(1).get(0).intValue());
        assertEquals(2, graph.getNeighbors(3).get(0).intValue());
    }
}