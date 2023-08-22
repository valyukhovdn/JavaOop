package ru.academits.valyukhov.vector;

import java.util.Arrays;
import java.util.Formatter;

public class Vector {
    private double[] elements;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Размерность массива вектора должна быть > 0. "
                    + "Сейчас size = %d ", size));
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
            throw new IllegalArgumentException(String.format("Размерность массива создаваемого вектора (size) "
                    + "должна быть > 0. Сейчас size = %d ", size));
        }

        this.elements = Arrays.copyOf(elements, size);
    }

    //  Получение одного элемента вектора по индексу.
    public double getElement(int index) {
        return elements[index];
    }

    //  Установка элемента вектора по индексу.
    public void setElement(int index, double element) {
        elements[index] = element;
    }

    //  Получение размера массива элементов данного вектора.
    public int getSize() {
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

    //  Прибавление к вектору другого вектора.
    public void add(Vector vector) {
        if (elements.length < vector.elements.length) {
            elements = Arrays.copyOf(elements, vector.elements.length);
        }

        for (int i = 0; i < vector.elements.length; ++i) {
            elements[i] += vector.elements[i];
        }
    }

    //  Вычитание из вектора другого вектора.
    public void subtract(Vector vector) {
        if (elements.length < vector.elements.length) {
            elements = Arrays.copyOf(elements, vector.elements.length);
        }

        for (int i = 0; i < vector.elements.length; ++i) {
            elements[i] -= vector.elements[i];
        }
    }

    //  Умножение вектора на скаляр.
    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < elements.length; ++i) {
            elements[i] *= scalar;
        }
    }

    //  Разворот вектора (умножение всех элементов на -1).
    public void reverse() {
        multiplyByScalar(-1);
    }

    //  Получение длины вектора.
    public double getLength() {
        double Sum = 0;

        for (double e : elements) {
            Sum += e * e;
        }

        return Math.sqrt(Sum);
    }

    //  Сложение двух векторов (static).
    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(Math.max(vector1.elements.length, vector2.elements.length), vector1.elements);

        resultVector.add(vector2);

        return resultVector;
    }

    //  Вычитание вектора (static).
    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(Math.max(vector1.elements.length, vector2.elements.length), vector1.elements);

        resultVector.subtract(vector2);

        return resultVector;
    }

    //  Скалярное произведение векторов.
    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double result = 0;
        int minVectorSize = Math.min(vector1.elements.length, vector2.elements.length);

        for (int i = 0; i < minVectorSize; ++i) {
            result += vector1.elements[i] * vector2.elements[i];
        }

        return result;
    }
}
