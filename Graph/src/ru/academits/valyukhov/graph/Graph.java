package ru.academits.valyukhov.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Graph<E> {
    private ArrayList<E> vertexesList;
    private final ArrayList<Boolean> visitedVertexes;
    private int[][] edgesMatrix;

    public Graph(ArrayList<E> vertexesList, int[][] edgesMatrix) {
        if (vertexesList == null || edgesMatrix == null) {
            throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, int[][] edgesMatrix)\"" +
                    " в качестве аргумента(-ов) передан \"null\".");
        }

        if (vertexesList.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, int[][] edgesMatrix)\"" +
                    " передан пустой список вершин.");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, int[][] edgesMatrix)\"" +
                    " в качестве аргументов переданы список вершин и матрица рёбер разной размерности.");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, int[][] edgesMatrix)\"" +
                    " в передана несимметричная матрица вершин.");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
        this.edgesMatrix = Arrays.copyOf(edgesMatrix, edgesMatrix.length);
        visitedVertexes = new ArrayList<>(vertexesList.size());

        for (int i = 0; i < vertexesList.size(); i++) {
            visitedVertexes.add(false);
        }
    }

    public Graph(int[][] edgesCoordinates, ArrayList<E> vertexesList) {
        if (vertexesList == null || edgesCoordinates == null) {
            throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, List<int[]> edgesList)\"" +
                    " в качестве аргумента(-ов) передан \"null\".");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
        edgesMatrix = new int[vertexesList.size()][vertexesList.size()];

        for (int[] edge : edgesCoordinates) {
            if (edge.length != 2) {
                throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, List<int[]> edgesList)\"" +
                        " передан список рёбер, содержащий ребро с количеством координат равным не \"2\", а \"" + edge.length + "\".");
            }

            if (edge[0] < 0 || edge[0] >= vertexesList.size() || edge[1] < 0 || edge[1] >= vertexesList.size()) {
                throw new IllegalArgumentException("Ошибка! В конструктор \"Graph(ArrayList<E> vertexesList, List<int[]> edgesList)\"" +
                        " передан список рёбер, содержащий ребро с координатами: (" + edge[0] + "; " + edge[1]
                        + "). Значения координат ребра должны быть в диапазоне от \"0\" до \"" + (vertexesList.size() - 1) + "\".");
            }

            edgesMatrix[edge[0]][edge[1]] = 1;
            edgesMatrix[edge[1]][edge[0]] = 1;
        }

        visitedVertexes = new ArrayList<>(vertexesList.size());

        for (int i = 0; i < vertexesList.size(); i++) {
            visitedVertexes.add(false);
        }
    }

    public ArrayList<E> getVertexesList() {
        return new ArrayList<>(vertexesList);
    }

    public void setVertexesList(ArrayList<E> vertexesList) {
        if (vertexesList == null) {
            throw new IllegalArgumentException("Ошибка! В метод \"setVertexesList(ArrayList<E> vertexesList)\"" +
                    " в качестве аргумента передан \"null\".");
        }

        if (vertexesList.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! В метод \"setVertexesList(ArrayList<E> vertexesList)\"" +
                    " передан пустой список вершин.");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В метод \"setEdgesMatrix(int[][] edgesMatrix)\"" +
                    " в качестве аргумента передан список вершин с размерностью, отличной от размерности матрицы рёбер.");
        }

        this.vertexesList = new ArrayList<>(vertexesList);
    }

    public int[][] getEdgesMatrix() {
        int[][] edgesMatrixCopy = new int[][]{};

        for (int i = 0; i < edgesMatrix.length; i++) {
            edgesMatrixCopy = Arrays.copyOf(edgesMatrix, edgesMatrix.length);
        }

        return edgesMatrixCopy;
    }

    public void setEdgesMatrix(int[][] edgesMatrix) {
        if (edgesMatrix == null) {
            throw new IllegalArgumentException("Ошибка! В метод \"setEdgesMatrix(int[][] edgesMatrix)\"" +
                    " в качестве аргумента передан \"null\".");
        }

        if (vertexesList.size() != edgesMatrix.length) {
            throw new IllegalArgumentException("Ошибка! В метод \"setEdgesMatrix(int[][] edgesMatrix)\"" +
                    " в качестве аргумента передана матрица рёбер с размерностью, отличной от размерности списка вершин.");
        }

        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("Ошибка! В метод \"setEdgesMatrix(int[][] edgesMatrix)\"" +
                    " в передана несимметричная матрица вершин.");
        }

        this.edgesMatrix = Arrays.copyOf(edgesMatrix, edgesMatrix.length);
    }

    public int getVertexesQuantity() {
        return vertexesList.size();
    }

    public int getEdgesQuantity() {
        int edgesQuantity = 0;
        int firstIndex = 0;

        for (int[] raw : edgesMatrix) {
            for (int i = firstIndex; i < edgesMatrix.length; i++) {
                if (raw[i] == 1) {
                    edgesQuantity++;
                }
            }

            firstIndex++;
        }

        return edgesQuantity;
    }

    public E getVertexValue(int vertexIndex) {
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

    private boolean isCorrectInputDataForGraphTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (getVertexesQuantity() == 0) {
            System.out.print("Обход графа невозможен, т.к. граф пуст.");
            return false;
        }

        if (initialVertexIndex < 0 || initialVertexIndex >= getVertexesQuantity()) {
            System.out.println("Обход графа невозможен, т.к. задан неверный индекс начальной вершины для обхода графа.");
            System.out.print("Допускаются значения от \"0\" до \"" + (getVertexesQuantity() - 1) + "\", "
                    + "а задано значение \"" + initialVertexIndex + "\".");

            return false;
        }

        if (consumer == null) {
            System.out.print("Обход графа невозможен, т.к. для обхода графа не задан \"Consumer<E> consumer\".");

            return false;
        }

        return true;
    }

    // Обход графа в ширину, начиная с заданной вершины
    public void breadthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (!isCorrectInputDataForGraphTraversal(initialVertexIndex, consumer)) {
            return;
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
                    if (edgesMatrix[currentVertexIndex][i] == 1 && !visitedVertexes.get(i)) {
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

        Collections.fill(visitedVertexes, false);
    }

    // Обход графа в глубину без рекурсии, начиная с заданной вершины
    public void depthFirstTraversal(int initialVertexIndex, Consumer<E> consumer) {
        if (!isCorrectInputDataForGraphTraversal(initialVertexIndex, consumer)) {
            return;
        }

        int currentVertexIndex = initialVertexIndex;

        int currentVisitedVertexIndex = 0;

        int processedVertexesQuantity = 0;              // Количество обработанных вершин

        while (processedVertexesQuantity < getVertexesQuantity()) {
            Deque<Integer> vertexIndexesStack = new LinkedList<>();

            vertexIndexesStack.offerFirst(currentVertexIndex);

            while (!vertexIndexesStack.isEmpty()) {
                currentVertexIndex = vertexIndexesStack.pollFirst();

                E currentVertex = vertexesList.get(currentVertexIndex);

                if (!visitedVertexes.get(currentVertexIndex)) {
                    consumer.accept(currentVertex);
                    visitedVertexes.set(currentVertexIndex, true);
                    processedVertexesQuantity++;
                }

                for (int i = 0; i < getVertexesQuantity(); i++) {
                    if (edgesMatrix[currentVertexIndex][i] == 1 && !visitedVertexes.get(i)) {
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

        Collections.fill(visitedVertexes, false);
    }

    // Вспомогательный метод обхода графа в глубину с рекурсией (для последующего заполнения списка "visitedVertexes" значениями "false").
    private void auxiliaryDepthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer) {
        consumer.accept(vertexesList.get(initialVertexIndex));
        visitedVertexes.set(initialVertexIndex, true);

        for (int i = 0; i < getVertexesQuantity(); i++) {
            if (edgesMatrix[initialVertexIndex][i] == 1 && !visitedVertexes.get(i)) {
                auxiliaryDepthFirstTraversalWithRecursion(i, consumer);
            }
        }

        for (int i = 0; i < getVertexesQuantity(); i++) {
            if (!visitedVertexes.get(i)) {
                auxiliaryDepthFirstTraversalWithRecursion(i, consumer);
            }
        }
    }

    // Обход графа в глубину с рекурсией, начиная с заданной вершины
    public void depthFirstTraversalWithRecursion(int initialVertexIndex, Consumer<E> consumer) {
        if (!isCorrectInputDataForGraphTraversal(initialVertexIndex, consumer)) {
            return;
        }

        auxiliaryDepthFirstTraversalWithRecursion(initialVertexIndex, consumer);

        Collections.fill(visitedVertexes, false);
    }
}