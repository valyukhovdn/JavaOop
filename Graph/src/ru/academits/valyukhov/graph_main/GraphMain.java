package ru.academits.valyukhov.graph_main;

import ru.academits.valyukhov.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class GraphMain {
    public static void main(String[] args) {
        // Несвязный граф из презентации Лекции 14, слайд 26
        ArrayList<String> vertexesList = new ArrayList<>(Arrays.asList(
                "1",  // Index 0
                "2",  // Index 1
                "3",  // Index 2
                "4",  // Index 3
                "5",  // Index 4
                "6",  // Index 5
                "7",  // Index 6
                "A",  // Index 7
                "B",  // Index 8
                "C",  // Index 9
                "D",  // Index 10
                "E",  // Index 11
                "F",  // Index 12
                "G"   // Index 13
        ));

        int[][] edgesCoordinates = new int[][]{
                {0, 1},
                {1, 2},
                {1, 3},
                {1, 4},
                {1, 5},
                {2, 6},
                {4, 5},
                {5, 6},
                {7, 8},
                {8, 9},
                {9, 10},
                {12, 13}
        };

        Graph<String> graph = new Graph<>(edgesCoordinates, vertexesList);

        System.out.println(graph);
        System.out.println();

        System.out.println("Количество вершин в графе: \"" + graph.getVertexesQuantity() + "\"");
        System.out.println("Количество рёбер в графе:  \"" + graph.getEdgesQuantity() + "\"");
        System.out.println();

        int initialVertexIndex = 1;
        Consumer<String> printConsumer = value -> System.out.printf("%3s, ", value);

        // Обход графа в ширину, начиная с заданной вершины
        System.out.println("Обход графа в ширину, начиная с вершины с индексом \"" + initialVertexIndex
                + "\" и значением \"" + graph.getVertexValue(initialVertexIndex) + "\":");
        graph.breadthFirstTraversal(initialVertexIndex, printConsumer);
        System.out.println("\b\b");
        System.out.println();

        // Обход графа в глубину без рекурсии, начиная с заданной вершины
        System.out.println("Обход графа в глубину без рекурсии, начиная с вершины с индексом \"" + initialVertexIndex
                + "\" и значением \"" + graph.getVertexValue(initialVertexIndex) + "\":");
        graph.depthFirstTraversal(initialVertexIndex, printConsumer);
        System.out.println("\b\b");
        System.out.println();

        // Обход графа в глубину с рекурсией, начиная с заданной вершины
        System.out.println("Обход графа в глубину с рекурсией, начиная с вершины с индексом \"" + initialVertexIndex
                + "\" и значением \"" + graph.getVertexValue(initialVertexIndex) + "\":");
        graph.depthFirstTraversalWithRecursion(initialVertexIndex, printConsumer);
        System.out.println("\b\b");
    }
}