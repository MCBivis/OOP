package org.example;

import java.util.List;

/**
 * Интерфейс, описывающий основные операции для работы с графом.
 */
public interface Graph {

    /**
     * Добавляет новую вершину в граф.
     */
    void addVertex();

    /**
     * Удаляет последнюю вершину из графа.
     */
    void removeVertex();

    /**
     * Добавляет направленное ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    void addEdge(int source, int destination);

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param source      исходная вершина
     * @param destination целевая вершина
     */
    void removeEdge(int source, int destination);

    /**
     * Возвращает список соседних вершин для указанной вершины.
     *
     * @param vertex вершина, для которой требуется получить список соседей
     * @return список смежных вершин
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Читает граф из файла. Формат файла: ребра задаются в виде "источник,назначение".
     *
     * @param filePath путь к файлу
     * @throws Exception если возникли ошибки при чтении файла
     */
    void readFromFile(String filePath) throws Exception;

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
    String toString();

    /**
     * Сравнивает графы на равенство.
     *
     * @param o объект для сравнения
     * @return true, если графы равны, иначе false
     */
    boolean equals(Object o);

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @return список вершин в топологическом порядке
     * @throws GraphCycleException если граф содержит цикл, сортировка невозможна
     */
    List<Integer> topologicalSort() throws GraphCycleException;
}
