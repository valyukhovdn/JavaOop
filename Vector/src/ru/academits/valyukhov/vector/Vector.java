package ru.academits.valyukhov.vector;

import java.util.Arrays;
import java.util.Formatter;

public class Vector {
    private double[] elements;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Размерность массива вектора (vectorArrayDimension) "
                    + "должна быть > 0. Сейчас dimension = %d ", size));
        }

        elements = new double[size];
    }

    public Vector(Vector vector) {
        elements = Arrays.copyOf(vector.elements, vector.elements.length);
    }

    public Vector(double[] elements) {
        if (elements.length == 0) {
            throw new IllegalArgumentException("Передаваемый в качестве аргумента массив пуст!");
        }

        this.elements = Arrays.copyOf(elements, elements.length);
    }

    public Vector(int size, double[] elements) {
        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Размерность массива нового вектора (dimension) "
                    + "должна быть > 0. Сейчас dimension = %d ", size));
        }

        this.elements = Arrays.copyOf(elements, size);
    }

    public double getComponent(int index) {    //  Получение одной компоненты вектора по индексу.
        return elements[index];
    }

    public void setComponent(int index, double component) {    //  Установка компоненты вектора по индексу.
        this.elements[index] = component;
    }

    public int getSize() {                     //  Получение размера массива компонент данного вектора.
        return elements.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append('{');

        for (double e : elements) {
            formatter.format("%.2f; ", e);
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append('}');

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        return prime * hash + Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return Arrays.equals(elements, vector.elements);
    }

    public void add(Vector vector) {    //  Прибавление к вектору другого вектора.
        if (getSize() < vector.getSize()) {
            elements = Arrays.copyOf(elements, vector.getSize());
        }

        for (int i = 0; i < vector.elements.length; ++i) {
            elements[i] += vector.elements[i];
        }
    }

    public void subtract(Vector vector) {    //  Вычитание из вектора другого вектора.
        if (getSize() < vector.getSize()) {
            elements = Arrays.copyOf(elements, vector.getSize());
        }

        for (int i = 0; i < vector.elements.length; ++i) {
            elements[i] -= vector.elements[i];
        }
    }

    public void multiplyByScalar(double scalar) {    //  Умножение вектора на скаляр.
        for (int i = 0; i < elements.length; ++i) {
            elements[i] *= scalar;
        }
    }

    public void reversal() {    //  Разворот вектора (умножение всех компонент на -1).
        multiplyByScalar(-1);
    }

    public double getLength() {    //  Получение длины вектора.
        double squaredVectorCoordinatesSum = 0;

        for (double e : elements) {
            squaredVectorCoordinatesSum += e * e;
        }

        return Math.sqrt(squaredVectorCoordinatesSum);
    }

    public static Vector getSum(Vector vector1, Vector vector2) {    //  Сложение двух векторов (static).
        Vector resultingVector = new Vector(Math.max(vector1.getSize(), vector2.getSize()));

        resultingVector.add(vector1);
        resultingVector.add(vector2);

        return resultingVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {    //  Вычитание вектора (static).
        Vector resultingVector = new Vector(Math.max(vector1.getSize(), vector2.getSize()), vector1.elements);
        resultingVector.subtract(vector2);

        return resultingVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double result = 0;
        int minVectorSize = Math.min(vector1.elements.length, vector2.elements.length);

        for (int i = 0; i < minVectorSize; ++i) {
            result += vector1.elements[i] * vector2.elements[i];
        }

        return result;
    }
}
