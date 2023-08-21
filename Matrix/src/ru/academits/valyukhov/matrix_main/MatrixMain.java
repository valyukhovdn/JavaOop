package ru.academits.valyukhov.matrix_main;

import ru.academits.valyukhov.matrix.Matrix;
import ru.academits.valyukhov.print.Print;
import ru.academits.valyukhov.vector.Vector;

public class MatrixMain {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(2, 3);  //  Используем конструктор Matrix(n, m) – матрица нулей размера nxm

        System.out.println(matrix);              //  Используем переопределённый метод toString.
        System.out.printf("У этой матрицы количество строк - %d, количество столбцов - %d.%n",
                matrix.getRowsQuantity(), matrix.getColumnsQuantity());
        System.out.println();

        System.out.println("Создадим новую матрицу №1:");

        Matrix matrix1 = new Matrix(new Vector[]{  //  Используем конструктор Matrix(Vector[]) – из массива векторов-строк.
                new Vector(new double[]{1}),
                new Vector(new double[]{2, 2}),
                new Vector(new double[]{3, 3, 3}),
                new Vector(new double[]{4, 4, 4, 4})
        });

        Print.print(matrix1);

        System.out.printf("Вектор-строка с индексом %d имеет значения: %s%n%n", 2, matrix1.getRowByIndex(2));

        Vector vectorToPaste = new Vector(new double[]{5, 6, 7, 8});
        System.out.println("Заменим этот вектор-строку на " + vectorToPaste);
        System.out.println();
        matrix1.setRowByIndex(2, vectorToPaste);
        System.out.println("Матрица №1 стала выглядеть так:");
        Print.print(matrix1);

        System.out.printf("Вектор-столбец с индексом %d имеет значения:%n", 2);
        Print.printColumn(matrix1.getColumnByIndex(2));

        System.out.println("Транспонируем матрицу №1:");
        matrix1.transpose();
        Print.print(matrix1);

        double scalar = 2.0;
        System.out.printf("В результате умножения матрицы №1 на скаляр %.2f она приняла вид:%n", scalar);
        matrix1.multiplyByScalar(scalar);
        Print.print(matrix1);

        System.out.printf("Определитель этой матрицы №1 равен: %.2f%n%n", matrix1.getDeterminant());

        System.out.println("Умножим матрицу №1 на вектор-столбец:");
        Vector columnVectorMultiplier = new Vector(new double[]{1, 2, 3, 4});
        Print.printColumn(columnVectorMultiplier);
        System.out.println("В результате получаем результирующий вектор-столбец:");
        Print.printColumn(matrix1.multiplyByVector(columnVectorMultiplier));

        System.out.println("Создадим матрицу №2:");
        Matrix matrix2 = new Matrix(new double[][]{{4, 4, 4, 4}, {3, 3, 3}, {2, 2}, {1}});
        Print.print(matrix2);

        System.out.println("Прибавим к матрице №1 матрицу №2 (не static)");
        matrix1.add(matrix2);
        System.out.println("В результате матрица №1 стала такой:");
        Print.print(matrix1);

        System.out.println("Вычтем из матрицы №1 матрицу №2 (не static)");
        matrix1.subtract(matrix2);
        System.out.println("В результате матрица №1 вернулась к виду, который она имела до прибавления к ней матрицы №2:");
        Print.print(matrix1);

        System.out.println("При сложении (static) матриц №1 и №2 получаем новую результирующую матрицу:");
        Print.print(Matrix.getSum(matrix1, matrix2));

        System.out.println("При вычитании (static) из матрицы №1 матрицы №2 получаем новую результирующую матрицу:");
        Print.print(Matrix.getDifference(matrix1, matrix2));

        System.out.println("Произведение (static) матрицы №1 и матрицы №2 даёт новую результирующую матрицу:");
        Print.print(Matrix.getProduct(matrix1, matrix2));
    }
}
