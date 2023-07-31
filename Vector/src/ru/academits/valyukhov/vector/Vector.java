package ru.academits.valyukhov.vector;

import java.util.Arrays;
import java.util.Formatter;

public class Vector {
    private int n;
    public double[] vectorArray;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Размерность (n) вектора должна быть > 0. Сейчас n = %d ", n));
        }

        this.n = n;
        vectorArray = new double[n];

        for (int i = 0; i < n; ++i) {
            vectorArray[i] = 0;
        }
    }

    public Vector(Vector vector) {
        n = vector.n;
        vectorArray = Arrays.copyOf(vector.vectorArray, n);
    }

    public Vector(double[] vectorArray) {
        n = vectorArray.length;
        this.vectorArray = Arrays.copyOf(vectorArray, n);
    }

    public Vector(int n, double[] vectorArray) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Размерность (n) вектора должна быть > 0. Сейчас n = %d ", n));
        }

        this.n = n;
        this.vectorArray = Arrays.copyOf(vectorArray, n);
    }

    public int getSize() {
        return this.vectorArray.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        for (double e : vectorArray) {
            formatter.format("%6.2f, ", e);
        }

        sb.delete(sb.length() - 2, sb.length());

        return "{" + sb + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + n;
        hash = prime * hash + Arrays.hashCode(vectorArray);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Vector v = (Vector) o;

        if (n != v.n) {
            return false;
        }

        for (int i = 0; i < vectorArray.length; ++i) {
            if (vectorArray[i] != v.vectorArray[i]) {
                return false;
            }
        }

        return true;
    }

    public void vectorAddition(Vector vector) {    //  Прибавление к вектору другого вектора.
        if (getSize() >= vector.getSize()) {
            for (int i = 0; i < vector.vectorArray.length; ++i) {
                vectorArray[i] += vector.vectorArray[i];
            }

        } else {
            double[] tempArray = Arrays.copyOf(vector.vectorArray, vector.n);

            for (int i = 0; i < vectorArray.length; ++i) {
                tempArray[i] += vectorArray[i];
            }

            vectorArray = tempArray;
            n = tempArray.length;
        }
    }

    public void vectorSubtraction(Vector vector) {    //  Вычитание из вектора другого вектора.
        if (getSize() >= vector.getSize()) {
            for (int i = 0; i < vector.vectorArray.length; ++i) {
                vectorArray[i] -= vector.vectorArray[i];
            }

        } else {
            double[] tempArray = Arrays.copyOf(vectorArray, vector.n);

            for (int i = 0; i < vector.vectorArray.length; ++i) {
                vectorArray[i] = tempArray[i] - vector.vectorArray[i];
            }
        }

        n = vectorArray.length;
    }

    public void vectorMultipliedByScalar(double scalar) {    //  Умножение вектора на скаляр.
        for (int i = 0; i < vectorArray.length; ++i) {
            vectorArray[i] *= scalar;
        }
    }

    public void invertedVector() {    //  Разворот вектора (умножение всех компонент на -1).
        for (int i = 0; i < vectorArray.length; ++i) {
            vectorArray[i] *= -1;
        }
    }

    public double vectorLength() {    //  Получение длины вектора.
        double temp = 0;

        for (double e : vectorArray) {
            temp += e * e;
        }

        return Math.sqrt(temp);
    }

    public double getComponent(int index) {    //  Получение компоненты вектора по индексу
        return vectorArray[index];
    }

    public void setComponent(int index, double volume) {    //  Установка компоненты вектора по индексу
        this.vectorArray[index] = volume;
    }

    public static Vector additionVector(Vector v1, Vector v2) {    //  Сложение двух векторов (static).
        double[] tempArray;

        if (v1.getSize() >= v2.getSize()) {
            tempArray = Arrays.copyOf(v1.vectorArray, v1.n);

            for (int i = 0; i < v2.vectorArray.length; ++i) {
                tempArray[i] += v2.vectorArray[i];
            }

        } else {
            tempArray = Arrays.copyOf(v2.vectorArray, v2.n);

            for (int i = 0; i < v1.vectorArray.length; ++i) {
                tempArray[i] += v1.vectorArray[i];
            }
        }

        return new Vector(tempArray.length, tempArray);
    }

    public static Vector subtractionVector(Vector v1, Vector v2) {    //  Вычитание вектора (static).
        double[] tempArray;

        if (v1.getSize() >= v2.getSize()) {
            tempArray = Arrays.copyOf(v1.vectorArray, v1.n);

            for (int i = 0; i < v2.vectorArray.length; ++i) {
                tempArray[i] -= v2.vectorArray[i];
            }

        } else {
            tempArray = Arrays.copyOf(v1.vectorArray, v2.n);

            for (int i = 0; i < v2.vectorArray.length; ++i) {
                tempArray[i] -= v2.vectorArray[i];
            }
        }

        return new Vector(tempArray.length, tempArray);
    }

    public static double scalarProduct(Vector v1, Vector v2) {
        double result = 0;

        if (v1.getSize() >= v2.getSize()) {
            for (int i = 0; i < v2.vectorArray.length; ++i) {
                result += v1.vectorArray[i] * v2.vectorArray[i];
            }

        } else {
            for (int i = 0; i < v1.vectorArray.length; ++i) {
                result += v1.vectorArray[i] * v2.vectorArray[i];
            }
        }

        return result;
    }
}
