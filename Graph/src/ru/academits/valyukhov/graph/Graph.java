package ru.academits.valyukhov.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Graph<E> {
    private ArrayList<E> vertexesList;
    private int[][] edgesMatrix;

    public Graph(ArrayList<E> vertexesList, int[][] edgesMatrix) {
        if (vertexesList == null) {
            throw new NullPointerException("Ошибка! В конструктор в качестве аргумента вместо \"ArrayList<E> vertexesList\""
                    + " передан \"null\".");
        }

        if (edgesMatrix == null) {
            throw new NullPointerException("Ошибка! В конструктор в качестве аргумента вместо \"int[][] edgesMatrix\""
                    + " передан \"null\".");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В конструктор в качестве аргументов переданы список вершин и"
                    + " матрица рёбер разной размерности.");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("Ошибка! В конструктор передана несимметричная матрица вершин.");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
        this.edgesMatrix = Arrays.copyOf(edgesMatrix, edgesMatrix.length);
    }

    public Graph(int[][] edgesCoordinates, ArrayList<E> vertexesList) {
        if (edgesCoordinates == null) {
            throw new NullPointerException("Ошибка! В конструктор в качестве аргумента вместо \"int[][] edgesCoordinates\""
                    + " передан \"null\".");
        }

        if (vertexesList == null) {
            throw new NullPointerException("Ошибка! В конструктор в качестве аргумента вместо \"ArrayList<E> vertexesList\""
                    + " передан \"null\".");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
        edgesMatrix = new int[vertexesList.size()][vertexesList.size()];

        for (int[] edge : edgesCoordinates) {
            if (edge.length != 2) {
                throw new IllegalArgumentException("Ошибка! В конструктор передан список рёбер, содержащий ребро "
                        + "с количеством координат равным не \"2\", а \"" + edge.length + "\".");
            }

            if (edge[0] < 0 || edge[0] >= vertexesList.size() || edge[1] < 0 || edge[1] >= vertexesList.size()) {
                throw new IllegalArgumentException("Ошибка! В конструктор передан список рёбер, содержащий ребро"
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
            throw new IllegalArgumentException("Ошибка! В метод в качестве аргумента передан \"null\".");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В метод в качестве аргумента передан список вершин с размерностью,"
                    + " отличной от размерности матрицы рёбер.");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
    }

    public int[][] getEdgesMatrix() {
        return Arrays.copyOf(edgesMatrix, edgesMatrix.length);
    }

    public void setEdgesMatrix(int[][] edgesMatrix) {
        if (edgesMatrix == null) {
            throw new IllegalArgumentException("Ошибка! В метод в качестве аргумента передан \"null\".");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В метод в качестве аргумента передана матрица рёбер с размерностью,"
                    + " отличной от размерности списка вершин.");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("Ошибка! В метод передана несимметричная матрица вершин.");
        }

        this.edgesMatrix = Arrays.copyOf(edgesMatrix, edgesMatrix.length);
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
            throw new IllegalArgumentException("Ошибка! Попытка передать в метод индекс вершины \"" + vertexIndex + "\". "
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

    private boolean checkInputDataForGraphTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (initialVertexIndex < 0 || initialVertexIndex >= getVertexesQuantity()) {
            throw new IllegalArgumentException("Обход графа невозможен, т.к. задан неверный индекс начальной вершины "
                    + "для обхода графа. Допускаются значения от \"0\" до \"" + (getVertexesQuantity() - 1) + "\", "
                    + "а задано значение \"" + initialVertexIndex + "\".");
        }

        if (consumer == null) {
            throw new IllegalArgumentException("Обход графа невозможен, т.к. для обхода графа не задан \"Consumer<E> consumer\".");
        }

        return true;
    }

    // Обход графа в ширину, начиная с заданной вершины
    public void breadthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        ArrayList<Boolean> visitedVertexes = new ArrayList<>(vertexesList.size());

        for (int i = 0; i < vertexesList.size(); i++) {
            visitedVertexes.add(false);
        }

        int currentVertexIndex = initialVertexIndex;

        int currentVisitedVertexIndex = 0;

        int processedVertexesQuantity = 0;              // Количество обработанных вершин

        while (processedVertexesQuantity < getVertexesQuantity()) {
            Queue<Integer> vertexIndexesQueue = new LinkedList<>();

            vertexIndexesQueue.offer(currentVertexIndex);

            while (!vertexIndexesQueue.isEmpty()) {
                currentVertexIndex = vertexIndexesQueue.poll();

                consumer.accept(vertexesList.get(currentVertexIndex));

                visitedVertexes.set(currentVertexIndex, true);

                processedVertexesQuantity++;

                for (int i = 0; i < getVertexesQuantity(); i++) {
                    if (edgesMatrix[currentVertexIndex][i] != 0 && !visitedVertexes.get(i)) {
                        visitedVertexes.set(i, true);
                        vertexIndexesQueue.offer(i);
                    }
                }
            }

            if (processedVertexesQuantity < getVertexesQuantity()) {
                for (int i = currentVisitedVertexIndex; i < getVertexesQuantity(); i++) {
                    if (!visitedVertexes.get(i)) {
                        currentVertexIndex = i;
                        currentVisitedVertexIndex = i + 1;
                        break;
                    }
                }
            }
        }
    }

    // Обход графа в глубину без рекурсии, начиная с заданной вершины
    public void depthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        ArrayList<Boolean> visitedVertexes = new ArrayList<>(vertexesList.size());

        for (int i = 0; i < vertexesList.size(); i++) {
            visitedVertexes.add(false);
        }

        int currentVertexIndex = initialVertexIndex;

        int currentVisitedVertexIndex = 0;

        int processedVertexesQuantity = 0;              // Количество обработанных вершин

        while (processedVertexesQuantity < getVertexesQuantity()) {
            Deque<Integer> vertexIndexesStack = new LinkedList<>();

            vertexIndexesStack.offerFirst(currentVertexIndex);

            while (!vertexIndexesStack.isEmpty()) {
                currentVertexIndex = vertexIndexesStack.pollFirst();

                if (!visitedVertexes.get(currentVertexIndex)) {
                    consumer.accept(vertexesList.get(currentVertexIndex));
                    visitedVertexes.set(currentVertexIndex, true);
                    processedVertexesQuantity++;
                }

                for (int i = 0; i < getVertexesQuantity(); i++) {
                    if (edgesMatrix[currentVertexIndex][i] != 0 && !visitedVertexes.get(i)) {
                        vertexIndexesStack.offerFirst(currentVertexIndex);
                        vertexIndexesStack.offerFirst(i);
                        break;
                    }
                }
            }

            if (processedVertexesQuantity < getVertexesQuantity()) {
                for (int i = currentVisitedVertexIndex; i < getVertexesQuantity(); i++) {
                    if (!visitedVertexes.get(i)) {
                        currentVertexIndex = i;
                        currentVisitedVertexIndex = i + 1;
                        break;
                    }
                }
            }
        }
    }

    // Вспомогательный метод обхода графа в глубину с рекурсией (для последующего заполнения списка "visitedVertexes" значениями "false").
    private void DepthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer, ArrayList<Boolean> visitedVertexes) {
        consumer.accept(vertexesList.get(initialVertexIndex));

        visitedVertexes.set(initialVertexIndex, true);

        for (int i = 0; i < getVertexesQuantity(); i++) {
            if (edgesMatrix[initialVertexIndex][i] != 0 && !visitedVertexes.get(i)) {
                DepthFirstTraversalWithRecursion(i, consumer, visitedVertexes);
            }
        }
    }

    // Обход графа в глубину с рекурсией, начиная с заданной вершины
    public void depthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer) {
        checkInputDataForGraphTraversal(initialVertexIndex, consumer);

        ArrayList<Boolean> visitedVertexes = new ArrayList<>(vertexesList.size());

        for (int i = 0; i < vertexesList.size(); i++) {
            visitedVertexes.add(false);
        }

        DepthFirstTraversalWithRecursion(initialVertexIndex, consumer, visitedVertexes);

        for (int i = 0; i < visitedVertexes.size(); i++) {
            if (!visitedVertexes.get(i)) {
                DepthFirstTraversalWithRecursion(i, consumer, visitedVertexes);
            }
        }
    }
}