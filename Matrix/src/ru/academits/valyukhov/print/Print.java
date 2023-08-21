package ru.academits.valyukhov.print;

import ru.academits.valyukhov.matrix.Matrix;
import ru.academits.valyukhov.vector.Vector;

public class Print {
    public static void print(Matrix matrix) {
        for (int i = 0; i < matrix.getRowsQuantity(); ++i) {
            System.out.println(matrix.getRowByIndex(i));
        }

        System.out.println();
    }

    public static void printColumn(Vector column) {            //  Печать вектора в столбец (для печати вектора-столбца).
        for (int i = 0; i < column.getSize(); ++i) {
            System.out.printf("%.2f%n", column.getComponent(i));
        }

        System.out.println();
    }
}
