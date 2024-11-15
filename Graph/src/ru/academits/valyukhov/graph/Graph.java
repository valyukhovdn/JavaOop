package ru.academits.valyukhov.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Graph<E> {
    private ArrayList<E> vertexesList;
    private int[][] edgesMatrix;

    public Graph(ArrayList<E> vertexesList, int[][] edgesMatrix) {
        if (vertexesList == null) {
            throw new NullPointerException("В конструктор в качестве аргумента вместо \"vertexesList\""
                    + " передан \"null\".");
        }

        if (edgesMatrix == null) {
            throw new NullPointerException("В конструктор в качестве аргумента вместо \"edgesMatrix\""
                    + " передан \"null\".");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("В конструктор передана не квадратная матрица вершин.");
        }

        checkVertexesListAndEdgesMatrixDimensionality(vertexesList, edgesMatrix);

        this.vertexesList = new ArrayList<>(vertexesList);
        setEdgesMatrix(edgesMatrix);
    }

    public Graph(int[][] edgesCoordinates, ArrayList<E> vertexesList) {
        if (edgesCoordinates == null) {
            throw new NullPointerException("В конструктор в качестве аргумента вместо \"edgesCoordinates\""
                    + " передан \"null\".");
        }

        if (vertexesList == null) {
            throw new NullPointerException("В конструктор в качестве аргумента вместо \"vertexesList\""
                    + " передан \"null\".");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
        edgesMatrix = new int[vertexesList.size()][vertexesList.size()];

        for (int[] edge : edgesCoordinates) {
            if (edge.length != 2) {
                throw new IllegalArgumentException("В конструктор передан список рёбер, содержащий ребро "
                        + "с количеством координат равным не \"2\", а \"" + edge.length + "\".");
            }

            if (edge[0] < 0 || edge[0] >= vertexesList.size() || edge[1] < 0 || edge[1] >= vertexesList.size()) {
                throw new IllegalArgumentException("В конструктор передан список рёбер, содержащий ребро"
                        + " с координатами: (" + edge[0] + "; " + edge[1] + "). Значения координат ребра должны быть"
                        + " в диапазоне от \"0\" до \"" + (vertexesList.size() - 1) + "\".");
            }

            edgesMatrix[edge[0]][edge[1]] = 1;
            edgesMatrix[edge[1]][edge[0]] = 1;
        }
    }

    public ArrayList<E> getVertexesList() {
        return new ArrayList<>(vertexesList);
    }

    public void setVertexesList(ArrayList<E> vertexesList) {
        if (vertexesList == null) {
            throw new NullPointerException("В метод в качестве аргумента передан \"null\".");
        }

        checkVertexesListAndEdgesMatrixDimensionality(vertexesList, edgesMatrix);

        this.vertexesList = new ArrayList<>(vertexesList);
    }

    public int[][] getEdgesMatrix() {
        int[][] edgesMatrixCopy = new int[edgesMatrix.length][];

        for (int i = 0; i < edgesMatrix.length; i++) {
            edgesMatrixCopy[i] = Arrays.copyOf(edgesMatrix[i], edgesMatrix.length);
        }

        return edgesMatrixCopy;
    }

    public void setEdgesMatrix(int[][] edgesMatrix) {
        if (edgesMatrix == null) {
            throw new NullPointerException("В метод в качестве аргумента передан \"null\".");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("В метод передана не квадратная матрица вершин.");
        }

        checkVertexesListAndEdgesMatrixDimensionality(vertexesList, edgesMatrix);

        this.edgesMatrix = new int[edgesMatrix.length][];

        for (int i = 0; i < edgesMatrix.length; i++) {
            this.edgesMatrix[i] = Arrays.copyOf(edgesMatrix[i], edgesMatrix.length);
        }
    }

    public int getVertexesQuantity() {
        return vertexesList.size();
    }

    public int getEdgesQuantity() {
        int edgesQuantity = 0;
        int firstIndex = 0;

        for (int[] row : edgesMatrix) {
            for (int i = firstIndex; i < edgesMatrix.length; i++) {
                if (row[i] != 0) {
                    edgesQuantity++;
                }
            }

            firstIndex++;
        }

        return edgesQuantity;
    }

    public E getVertex(int vertexIndex) {
        if (vertexIndex < 0 || vertexIndex >= vertexesList.size()) {
            throw new IndexOutOfBoundsException("Попытка передать в метод индекс вершины \"" + vertexIndex + "\". "
                    + "Допустимые значения: от \"0\" до \"" + (vertexesList.size() - 1) + "\".");
        }

        return vertexesList.get(vertexIndex);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%n"));
        stringBuilder.append("          Graph");
        stringBuilder.append(String.format("%n"));

        StringBuilder thinLineStringBuilder = new StringBuilder(String.format("%n"));
        thinLineStringBuilder.append("------------------");
        IntStream.range(0, 5 * getVertexesQuantity()).forEach(number -> thinLineStringBuilder.append('-'));

        stringBuilder.append(thinLineStringBuilder);

        stringBuilder.append(String.format("%n%6s | %6s ||", "Values", ""));

        vertexesList.forEach(vertex -> stringBuilder.append(String.format("%3s |", vertex)));

        stringBuilder.append(thinLineStringBuilder);

        stringBuilder.append(String.format("%n%6s | %6s||", "", "Indexes"));

        IntStream.range(0, getVertexesQuantity()).forEach(number -> stringBuilder.append(String.format("%3s |", number)));

        stringBuilder.append(String.format("%n"));
        stringBuilder.append("==================");
        IntStream.range(0, 5 * getVertexesQuantity()).forEach(number -> stringBuilder.append('='));

        for (int i = 0; i < getVertexesQuantity(); i++) {
            stringBuilder.append(String.format("%n%4s   | %4d   ||", vertexesList.get(i), i));
            Arrays.stream(edgesMatrix[i]).sequential().forEach(number -> stringBuilder.append(String.format("%3s  ", number)));

            String shortThinLine = "------------------";
            stringBuilder.append(String.format("%n")).append(shortThinLine);
        }

        return stringBuilder.toString();
    }

    private void checkVertexesListAndEdgesMatrixDimensionality(ArrayList<E> vertexesList, int[][] edgesMatrix) {
        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("В качестве аргументов переданы список вершин и матрица рёбер разной "
                    + "размерности (\"" + vertexesList.size() + "\" и \"" + edgesMatrix.length + "\" соответственно).");
        }
    }

    private boolean checkInputDataForGraphTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (initialVertexIndex < 0 || initialVertexIndex >= getVertexesQuantity()) {
            throw new IndexOutOfBoundsException("Обход графа невозможен, т.к. задан неверный индекс начальной вершины "
                    + "для обхода графа. Допускаются значения от \"0\" до \"" + (getVertexesQuantity() - 1) + "\", "
                    + "а задано значение \"" + initialVertexIndex + "\".");
        }

        if (consumer == null) {
            throw new NullPointerException("Обход графа невозможен, т.к. для обхода графа не задан \"consumer\".");
        }

        return true;
    }

    // Обход графа в ширину, начиная с заданной вершины
    public void breadthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        boolean[] visitedVertexes = new boolean[vertexesList.size()];

        int currentVertexIndex = initialVertexIndex;

        int firstVertexIndexForNextSubGraph = 0;

        Queue<Integer> vertexIndexesQueue = new LinkedList<>();

        while (firstVertexIndexForNextSubGraph <= vertexesList.size()) {
            vertexIndexesQueue.offer(currentVertexIndex);

            while (!vertexIndexesQueue.isEmpty()) {
                currentVertexIndex = vertexIndexesQueue.poll();

                if (!visitedVertexes[currentVertexIndex]) {
                    consumer.accept(vertexesList.get(currentVertexIndex));

                    visitedVertexes[currentVertexIndex] = true;

                    for (int i = 0; i < getVertexesQuantity(); i++) {
                        if (edgesMatrix[currentVertexIndex][i] != 0) {
                            vertexIndexesQueue.offer(i);
                        }
                    }
                }
            }

            for (int i = firstVertexIndexForNextSubGraph; i < vertexesList.size(); i++) {
                if (!visitedVertexes[i]) {
                    currentVertexIndex = i;
                    firstVertexIndexForNextSubGraph = i + 1;
                    break;
                }

                firstVertexIndexForNextSubGraph = vertexesList.size() + 1;
            }
        }
    }

    // Обход графа в глубину без рекурсии, начиная с заданной вершины
    public void depthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        boolean[] visitedVertexes = new boolean[vertexesList.size()];

        int currentVertexIndex = initialVertexIndex;

        int firstVertexIndexForNextSubGraph = 0;

        Deque<Integer> vertexIndexesStack = new LinkedList<>();

        while (firstVertexIndexForNextSubGraph <= vertexesList.size()) {
            vertexIndexesStack.push(currentVertexIndex);

            while (!vertexIndexesStack.isEmpty()) {
                currentVertexIndex = vertexIndexesStack.pop();

                if (!visitedVertexes[currentVertexIndex]) {
                    consumer.accept(vertexesList.get(currentVertexIndex));
                    visitedVertexes[currentVertexIndex] = true;

                    for (int i = vertexesList.size() - 1; i >= 0; i--) {
                        if (edgesMatrix[currentVertexIndex][i] != 0 && !visitedVertexes[i]) {
                            vertexIndexesStack.push(i);
                        }
                    }
                }
            }

            for (int i = firstVertexIndexForNextSubGraph; i < vertexesList.size(); i++) {
                if (!visitedVertexes[i]) {
                    currentVertexIndex = i;
                    firstVertexIndexForNextSubGraph = i + 1;
                    break;
                }

                firstVertexIndexForNextSubGraph = vertexesList.size() + 1;
            }
        }
    }

    // Вспомогательный метод обхода графа в глубину с рекурсией.
    private void depthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer, boolean[] visitedVertexes) {
        consumer.accept(vertexesList.get(initialVertexIndex));

        visitedVertexes[initialVertexIndex] = true;

        for (int i = 0; i < vertexesList.size(); i++) {
            if (edgesMatrix[initialVertexIndex][i] != 0 && !visitedVertexes[i]) {
                depthFirstTraversalWithRecursion(i, consumer, visitedVertexes);
            }
        }
    }

    // Обход графа в глубину с рекурсией, начиная с заданной вершины
    public void depthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        boolean[] visitedVertexes = new boolean[vertexesList.size()];

        depthFirstTraversalWithRecursion(initialVertexIndex, consumer, visitedVertexes);

        for (int i = 0; i < visitedVertexes.length; i++) {
            if (!visitedVertexes[i]) {
                depthFirstTraversalWithRecursion(i, consumer, visitedVertexes);
            }
        }
    }
}