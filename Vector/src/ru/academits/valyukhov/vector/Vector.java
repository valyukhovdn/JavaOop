package ru.academits.valyukhov.vector;

import java.util.Arrays;
import java.util.Formatter;

public class Vector {

    private double[] elements;

    public Vector(int vectorArrayDimension) {
        if (vectorArrayDimension <= 0) {
            throw new IllegalArgumentException(String.format
                    ("Размерность массива вектора (vectorArrayDimension) должна быть > 0. "
                            + "Сейчас arrayDimension = %d ", vectorArrayDimension));
        }

        elements = new double[vectorArrayDimension];
    }

    public Vector(Vector vector) {
        elements = Arrays.copyOf(vector.elements, vector.elements.length);
    }

    public Vector(double[] elements) {
        if (elements.length == 0) {
            throw new NullPointerException("ОШИБКА: Передаваемый в качестве аргумента массив пуст!");
        }

        this.elements = Arrays.copyOf(elements, elements.length);
    }

    public Vector(int vectorArrayDimension, double[] elements) {
        if (vectorArrayDimension <= 0) {
            throw new IllegalArgumentException(String.format("Размерность массива нового вектора (vectorArrayDimension) "
                    + "должна быть > 0. Сейчас vectorArrayDimension = %d ", vectorArrayDimension));
        }

        this.elements = new double[vectorArrayDimension];

        System.arraycopy(elements, 0, this.elements, 0, Math.min(this.elements.length, elements.length));
    }

    public double[] getElements() {
        return elements;
    }

    public void setElements(double[] elements) {
        this.elements = elements;
    }

    public double getComponent(int index) {    //  Получение одной компоненты вектора по индексу.
        return elements[index];
    }

    public void setComponent(int index, double volume) {    //  Установка компоненты вектора по индексу.
        this.elements[index] = volume;
    }

    public int getSize() {                     //  Получение размера массива компонент данного вектора.
        return elements.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append("{");

        for (double e : elements) {
            formatter.format("%.2f; ", e);
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");

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

    public void revers() {    //  Разворот вектора (умножение всех компонент на -1).
        for (int i = 0; i < elements.length; ++i) {
            elements[i] *= -1;
        }
    }

    public double getLength() {    //  Получение длины вектора.
        double temporaryVariable = 0;

        for (double e : elements) {
            temporaryVariable += e * e;
        }

        return Math.sqrt(temporaryVariable);
    }

    public static Vector getVectorsSum(Vector vector1, Vector vector2) {    //  Сложение двух векторов (static).
        Vector resultingVector;

        if (vector1.getSize() >= vector2.getSize()) {
            resultingVector = new Vector(Arrays.copyOf(vector1.elements, vector1.elements.length));

            resultingVector.add(vector2);
        } else {
            resultingVector = new Vector(Arrays.copyOf(vector2.elements, vector2.elements.length));

            resultingVector.add(vector1);
        }

        return resultingVector;
    }

    public static Vector getVectorDifference(Vector vector1, Vector vector2) {    //  Вычитание вектора (static).
        Vector resultingVector;

        if (vector1.getSize() >= vector2.getSize()) {
            resultingVector = new Vector(Arrays.copyOf(vector1.elements, vector1.elements.length));

            resultingVector.subtract(vector2);
        } else {
            resultingVector = new Vector(Arrays.copyOf(vector1.elements, vector2.elements.length));

            resultingVector.subtract(vector2);
        }

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
