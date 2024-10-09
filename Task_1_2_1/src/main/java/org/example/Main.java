package org.example;

public class Main {
    public static void main(String[] args) {

        Graph test = new AdjacencyMatrixGraph(5);

        test.addEdge(0, 1);
        test.addEdge(0, 2);
        test.addEdge(0, 3);
        try {
            test.readFromFile("C:\\Users\\Artemiy\\Desktop\\tst.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(test);
    }
}