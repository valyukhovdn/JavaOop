package ru.academits.valyukhov.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Graph<E> {
    private ArrayList<Vertex<E>> vertexesList;
    private int[][] edgesMatrix;

    public Graph(ArrayList<Vertex<E>> vertexesList, int[][] edgesMatrix) {
        this.vertexesList = vertexesList;
        this.edgesMatrix = edgesMatrix;
    }

    public Graph(ArrayList<Vertex<E>> vertexesList, List<int[]> edgesList) {
        this.vertexesList = vertexesList;
        edgesMatrix = new int[vertexesList.size()][vertexesList.size()];

        edgesList.forEach(edge -> {
            edgesMatrix[edge[0]][edge[1]] = 1;
            edgesMatrix[edge[1]][edge[0]] = 1;
        });
    }

    public ArrayList<Vertex<E>> getVertexesList() {
        return vertexesList;
    }

    public void setVertexesList(ArrayList<Vertex<E>> vertexesList) {
        this.vertexesList = vertexesList;
    }

    public int[][] getEdgesMatrix() {
        return edgesMatrix;
    }

    public void setEdgesMatrix(int[][] edgesMatrix) {
        this.edgesMatrix = edgesMatrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%n"));
        stringBuilder.append("          Graph");
        stringBuilder.append(String.format("%n"));

        StringBuilder thinLineStringBuilder = new StringBuilder(String.format("%n"));
        thinLineStringBuilder.append("------------------");
        IntStream.range(0, 5 * vertexQuantity()).forEach(number -> thinLineStringBuilder.append('-'));

        stringBuilder.append(thinLineStringBuilder);

        stringBuilder.append(String.format("%n%6s | %6s ||", "Values", ""));

        vertexesList.forEach(vertex -> stringBuilder.append(String.format("%3s |", vertex.getValue())));

        stringBuilder.append(thinLineStringBuilder);

        stringBuilder.append(String.format("%n%6s | %6s||", "", "Indexes"));

        IntStream.range(0, vertexQuantity()).forEach(number -> stringBuilder.append(String.format("%3s |", number)));

        stringBuilder.append(String.format("%n"));
        stringBuilder.append("==================");
        IntStream.range(0, 5 * vertexQuantity()).forEach(number -> stringBuilder.append('='));

        for (int i = 0; i < vertexQuantity(); i++) {
            stringBuilder.append(String.format("%n%4s   | %4d   ||", vertexesList.get(i).getValue(), i));
            Arrays.stream(edgesMatrix[i]).sequential().forEach(number -> stringBuilder.append(String.format("%3s  ", number)));

            String shortThinLine = "------------------";
            stringBuilder.append(String.format("%n")).append(shortThinLine);
        }

        return stringBuilder.toString();
    }

    public int vertexQuantity() {
        return vertexesList.size();
    }

    public int edgesQuantity() {
        int edgesQuantity = 0;
        int firstIndex = 0;

        for (int[] raw : edgesMatrix) {
            for (int j = firstIndex; j < edgesMatrix.length; j++) {
                if (raw[j] == 1) {
                    edgesQuantity++;
                }
            }

            firstIndex++;
        }

        return edgesQuantity;
    }

    private boolean isNotCorrectInputDataForGraphTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (vertexQuantity() == 0) {
            System.out.println(" невозможен, т.к. он пуст.");
            return true;
        }

        if (initialVertexIndex < 0 || initialVertexIndex >= vertexQuantity()) {
            System.out.println(" невозможен, т.к. задан неверный индекс начальной вершины для обхода графа. Допускаются " +
                    "значения от \"0\" до \"" + (vertexQuantity() - 1) + "\", а задано значение \"" + initialVertexIndex + "\".");

            return true;
        }

        if (consumer == null) {
            System.out.println(" невозможен, т.к. для обхода графа не задан Consumer<E> consumer.");

            return true;
        }

        return false;
    }

    // Обход графа в ширину, начиная с заданной вершины
    public void breadthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        System.out.print("Обход графа в ширину");

        if (isNotCorrectInputDataForGraphTraversal(initialVertexIndex, consumer)) {
            return;
        }

        System.out.println(", начиная с вершины со значением \"" + vertexesList.get(initialVertexIndex).getValue()
                + "\" (индекс \"" + initialVertexIndex + "\"):");

        int graphOrdinalNumber = 0;                     // Номер текущего подграфа

        int currentVertexIndex = initialVertexIndex;

        int processedVertexesQuantity = 0;              // Количество обработанных вершин

        while (processedVertexesQuantity < vertexQuantity()) {
            ++graphOrdinalNumber;

            System.out.print("Подграф №" + graphOrdinalNumber + ": ");

            Vertex<E> currentVertex = vertexesList.get(currentVertexIndex);

            vertexesList.get(currentVertexIndex).setVisited(true);

            Queue<Vertex<E>> vertexesQueue = new LinkedList<>();

            vertexesQueue.offer(currentVertex);

            Queue<Integer> vertexIndexesQueue = new LinkedList<>();

            vertexIndexesQueue.offer(currentVertexIndex);

            while (!vertexesQueue.isEmpty()) {
                currentVertex = vertexesQueue.poll();
                currentVertexIndex = vertexIndexesQueue.poll();

                consumer.accept(currentVertex.getValue());

                processedVertexesQuantity++;

                for (int i = 0; i < vertexQuantity(); i++) {
                    if (edgesMatrix[currentVertexIndex][i] == 1) {
                        if (!vertexesList.get(i).isVisited()) {
                            vertexesList.get(i).setVisited(true);
                            vertexesQueue.offer(vertexesList.get(i));
                            vertexIndexesQueue.offer(i);
                        }
                    }
                }
            }

            System.out.println("\b\b");

            if (processedVertexesQuantity < vertexQuantity()) {
                for (int j = 0; j < vertexQuantity(); j++) {
                    if (!vertexesList.get(j).isVisited()) {
                        currentVertexIndex = j;
                        break;
                    }
                }
            }
        }

        vertexesList.forEach(vertex -> vertex.setVisited(false));
    }

    // Обход графа в глубину, начиная с заданной вершины
    public void depthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        System.out.print("Обход графа в глубину");

        if (isNotCorrectInputDataForGraphTraversal(initialVertexIndex, consumer)) {
            return;
        }

        System.out.println(", начиная с вершины со значением \"" + vertexesList.get(initialVertexIndex).getValue()
                + "\" (индекс \"" + initialVertexIndex + "\"):");

        int graphOrdinalNumber = 0;                     // Номер текущего подграфа

        int currentVertexIndex = initialVertexIndex;

        int processedVertexesQuantity = 0;              // Количество обработанных вершин

        while (processedVertexesQuantity < vertexQuantity()) {
            ++graphOrdinalNumber;

            System.out.print("Подграф №" + graphOrdinalNumber + ": ");

            Vertex<E> currentVertex = vertexesList.get(currentVertexIndex);

            vertexesList.get(currentVertexIndex).setVisited(true);

            Deque<Vertex<E>> vertexesStack = new LinkedList<>();

            vertexesStack.offerFirst(currentVertex);

            Deque<Integer> vertexIndexesStack = new LinkedList<>();

            vertexIndexesStack.offerFirst(currentVertexIndex);

            while (!vertexesStack.isEmpty()) {
                currentVertex = vertexesStack.pollFirst();
                currentVertexIndex = vertexIndexesStack.pollFirst();

                consumer.accept(currentVertex.getValue());

                processedVertexesQuantity++;

                for (int i = vertexQuantity() - 1; i >= 0; i--) {
                    if (edgesMatrix[currentVertexIndex][i] == 1) {
                        if (!vertexesList.get(i).isVisited()) {
                            vertexesList.get(i).setVisited(true);
                            vertexesStack.offerFirst(vertexesList.get(i));
                            vertexIndexesStack.offerFirst(i);
                        }
                    }
                }
            }

            System.out.println("\b\b");

            if (processedVertexesQuantity < vertexQuantity()) {
                for (int j = 0; j < vertexQuantity(); j++) {
                    if (!vertexesList.get(j).isVisited()) {
                        currentVertexIndex = j;
                        break;
                    }
                }
            }
        }

        vertexesList.forEach(vertex -> vertex.setVisited(false));
    }
}