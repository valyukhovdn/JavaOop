package ru.academits.valyukhov.graph_main;

import ru.academits.valyukhov.graph.Graph;
import ru.academits.valyukhov.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class GraphMain {
    public static void main(String[] args) {
        // Несвязный граф из презентации Лекции 14, слайд 26
        ArrayList<Vertex<String>> vertexesList = new ArrayList<>(Arrays.asList(
                new Vertex<>("1"),  // Index 0
                new Vertex<>("2"),  // Index 1
                new Vertex<>("3"),  // Index 2
                new Vertex<>("4"),  // Index 3
                new Vertex<>("5"),  // Index 4
                new Vertex<>("6"),  // Index 5
                new Vertex<>("7"),  // Index 6
                new Vertex<>("A"),  // Index 7
                new Vertex<>("B"),  // Index 8
                new Vertex<>("C"),  // Index 9
                new Vertex<>("D"),  // Index 10
                new Vertex<>("E"),  // Index 11
                new Vertex<>("F"),  // Index 12
                new Vertex<>("G")   // Index 13
        ));

        List<int[]> edgesList = new LinkedList<>(Arrays.asList(
                new int[]{0, 1},
                new int[]{1, 2},
                new int[]{1, 3},
                new int[]{1, 4},
                new int[]{1, 5},
                new int[]{2, 6},
                new int[]{4, 5},
                new int[]{5, 6},
                new int[]{7, 8},
                new int[]{8, 9},
                new int[]{9, 10},
                new int[]{12, 13}
        ));

        Graph<String> graph = new Graph<>(vertexesList, edgesList);

        System.out.println(graph);
        System.out.println();
        System.out.println("Количество вершин в графе: \"" + graph.vertexQuantity() + "\"");
        System.out.println("Количество рёбер в графе:  \"" + graph.edgesQuantity() + "\"");
        System.out.println();

        int initialVertexIndex = 1;
        Consumer<String> printConsumer = value -> System.out.printf("%3s, ", value);

        // Обход графа в ширину, начиная с заданной вершины
        graph.breadthFirstTraversal(initialVertexIndex, printConsumer);
        System.out.println("\b\b");

        // Обход графа в глубину, начиная с заданной вершины
        graph.depthFirstTraversal(initialVertexIndex, printConsumer);
        System.out.println("\b\b");
    }
}