package ru.academits.valyukhov.matrix;

import ru.academits.valyukhov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsQuantity, int columnsQuantity) {    //  Конструктор Matrix(n, m) – матрица нулей размера n x m.
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; ++i) {
            rows[i] = new Vector(columnsQuantity);
        }
    }

    public Matrix(Matrix matrix) {              //  Конструктор Matrix(Matrix) – конструктор копирования.
        rows = new Vector[matrix.rows.length];  //  Создаём массив пустых векторов-строк.

        int maxRowSize = 0;                     //  Определяем максимальную длину строки передаваемой матрицы.

        for (int i = 0; i < matrix.rows.length; ++i) {
            if (maxRowSize < matrix.rows[i].getSize()) {
                maxRowSize = matrix.rows[i].getSize();
            }
        }

        for (int i = 0; i < rows.length; ++i) {  //  Перебираем строки (векторы) создаваемой матрицы.
            rows[i] = new Vector(maxRowSize);   //  Задаём каждому вектору-строке длину (автоматом заполняется нулями).

            for (int j = 0; j < matrix.rows[i].getSize(); ++j) {  //  Перебираем элементы строк (векторов) переданной матрицы
                rows[i].setComponent(j, matrix.rows[i].getComponent(j));  // Копируем элементы строк (векторов) из переданной матрицы в результирующую.
            }
        }
    }

    public Matrix(double[][] twoDimensionalArray) {
        rows = new Vector[twoDimensionalArray.length];

        int maxRowSize = 0;

        for (double[] array : twoDimensionalArray) {
            if (maxRowSize < array.length) {
                maxRowSize = array.length;
            }
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize);

            for (int j = 0; j < twoDimensionalArray[i].length; ++j) {
                rows[i].setComponent(j, twoDimensionalArray[i][j]);
            }
        }
    }

    public Matrix(Vector[] rowsArray) {    //  Конструктор Matrix(Vector[]) – из массива векторов-строк.
        int maxRowSize = 0;                //  Определяем максимальную длину строки передаваемой матрицы.

        for (Vector vector : rowsArray) {
            if (maxRowSize < vector.getSize()) {
                maxRowSize = vector.getSize();
            }
        }

        rows = new Vector[rowsArray.length];           //  Создаём массив пустых векторов-строк.

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize);    //  Задаём каждому вектору-строке длину (автоматом заполняется нулями).

            for (int j = 0; j < rowsArray[i].getSize(); ++j) {
                rows[i].setComponent(j, rowsArray[i].getComponent(j));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (Vector vector : rows) {
            sb.append(vector);
        }

        sb.append("}");

        return sb.toString();
    }

    public void printMatrix() {
        for (Vector vector : rows) {
            System.out.println(vector);
        }

        System.out.println();
    }

    public int getRowsQuantity() {       // Получение количества строк матрицы.
        return rows.length;
    }

    public int getColumnsQuantity() {    // Получение количества столбцов матрицы.
        return rows[0].getSize();
    }

    public Vector getRowByIndex(int rowIndex) {     //  Получение вектора-строки по индексу.
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("ОШИБКА: Задан не существующий номер строки для получения вектора-строки по индексу.");
        }

        return rows[rowIndex];
    }

    public void setRow(int rowIndex, Vector row) {    // Задание вектора-строки по индексу.
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("ОШИБКА: Задан не существующий номер строки для задания вектора-строки по индексу.");
        }

        if (row.getSize() != rows[rowIndex].getSize()) {
            throw new IllegalArgumentException(String.format("Переданный вектор-строка имеет размер %d, а требуется %d!",
                    row.getSize(), rows[rowIndex].getSize()));
        }

        rows[rowIndex].setElements(row.getElements());
    }

    public Vector getColumnByIndex(int columnIndex) {   //  Получение вектора-столбца по индексу.
        if (columnIndex < 0 || columnIndex >= rows[0].getSize()) {
            throw new IndexOutOfBoundsException("ОШИБКА: Задан не существующий номер столбца для получения вектора-столбца по индексу.");
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            column.setComponent(i, rows[i].getComponent(columnIndex));
        }

        return column;
    }

    public void printColumn(Vector column) {            //  Печать вектора в столбец (для печати вектора-столбца).
        for (double element : column.getElements()) {
            System.out.printf("%.2f%n", element);
        }

        System.out.println();
    }

    public void transposeMatrix() {      //  Транспонирование матрицы.
        int transposedMatrixRowsQuantity = rows[1].getSize();
        int transposedMatrixColumnsQuantity = rows.length;
        Matrix transposedMatrix = new Matrix(transposedMatrixRowsQuantity, transposedMatrixColumnsQuantity);

        for (int i = 0; i < transposedMatrixRowsQuantity; ++i) {
            transposedMatrix.setRow(i, this.getColumnByIndex(i));
        }

        rows = transposedMatrix.rows;
    }

    public void multiplyMatrixByScalar(double scalar) {   //  Умножение на скаляр.
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public Matrix getReducedMatrix(int baseRowIndex, int baseColumnIndex) {    //  Уменьшенная матрица для расчёта определителя основной матрицы.
        Matrix reducedMatrix = new Matrix(rows.length - 1, rows[0].getSize() - 1);

        for (int i = 0, rowCounter = 0; i < rows.length; ++i) {
            if (i == baseRowIndex) {
                continue;
            }

            for (int k = 0, columnCounter = 0; k < rows[0].getSize(); ++k) {
                if (k == baseColumnIndex) {
                    continue;
                }

                reducedMatrix.rows[rowCounter].setComponent(columnCounter, rows[i].getComponent(k));

                ++columnCounter;
            }

            ++rowCounter;
        }

        return reducedMatrix;
    }

    public double getMatrixDeterminant() {      //  Вычисление определителя матрицы
        int baseRowIndex = 0;                   //  Выбираем строку "0" в кач-ве базовой (элементы которой будем умножать определители миноров).
        int rowLength = rows[0].getSize();      // Определяем длину строк матрицы.
        double determinant = 0;

        if (rows.length == 1 && rows[0].getSize() == 1) {    //  Если матрица 1х1, то определитель равен этому элементу.
            return rows[0].getComponent(0);
        } else {
            for (int i = 0; i < rowLength; ++i) {
                determinant += ((i % 2 == 0) ? 1 : -1) * rows[baseRowIndex].getComponent(i)
                        * getReducedMatrix(baseRowIndex, i).getMatrixDeterminant();    //  Рекурсивный вызов функции из самой себя.
            }
        }

        return determinant;
    }

    public Vector multiplyByVector(Vector columnVectorMultiplier) {
        if (rows.length == 0) {
            throw new NullPointerException("ОШИБКА: Исходная матрица пуста!");
        }

        if (columnVectorMultiplier.getSize() == 0) {
            throw new NullPointerException("ОШИБКА: Переданный множитель (вектор-столбец) пуст!");
        }

        if (rows.length != columnVectorMultiplier.getSize()) {
            throw new IllegalArgumentException("ОШИБКА: Число столбцов в матрице не совпадает с числом строк в векторе-столбце.");
        }

        double[] resultArray = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            resultArray[i] = 0;
            for (int j = 0; j < rows[0].getSize(); ++j) {
                resultArray[i] += rows[i].getComponent(j) * columnVectorMultiplier.getComponent(i);
            }
        }

        return new Vector(resultArray);
    }

    public void addMatrix(Matrix matrix) {                 //  Сложение матриц (не static).
        if (rows.length == 0 || matrix.rows.length == 0) {
            throw new NullPointerException("ОШИБКА: Одна или обе складываемые матрицы пустые!");
        }

        if (rows.length != matrix.rows.length || rows[0].getSize() != matrix.rows[0].getSize()) {
            throw new IllegalArgumentException("ОШИБКА: Прибавить переданную матрицу нельзя, т.к. её "
                    + "размерность отличается от размерности исходной матрицы!");
        }

        for (int i = 0; i < rows.length; ++i) {
            for (int j = 0; j < rows[i].getSize(); ++j) {
                rows[i].setComponent(j, rows[i].getComponent(j) + matrix.rows[i].getComponent(j));
            }
        }
    }

    public void subtractMatrix(Matrix matrix) {                 //  Вычитание матриц (не static).
        if (rows.length == 0 || matrix.rows.length == 0) {
            throw new NullPointerException("ОШИБКА: Исходная и/или вычитаемая матрицы пустые!");
        }

        if (rows.length != matrix.rows.length || rows[0].getSize() != matrix.rows[0].getSize()) {
            throw new IllegalArgumentException("ОШИБКА: Вычесть переданную матрицу нельзя, т.к. её "
                    + "размерность отличается от размерности исходной матрицы!");
        }

        for (int i = 0; i < rows.length; ++i) {
            for (int j = 0; j < rows[i].getSize(); ++j) {
                rows[i].setComponent(j, rows[i].getComponent(j) - matrix.rows[i].getComponent(j));
            }
        }
    }

    public static Matrix getMatricesSum(Matrix matrix1, Matrix matrix2) {    //  Сложение двух матриц (static).
        if (matrix1.rows.length == 0 || matrix2.rows.length == 0) {
            throw new NullPointerException("ОШИБКА: Одна или обе суммируемые (static) матрицы пустые!");
        }

        if (matrix1.rows.length != matrix2.rows.length || matrix1.rows[0].getSize() != matrix2.rows[0].getSize()) {
            throw new IllegalArgumentException("ОШИБКА: Сложить 2 переданные матрицы нельзя, т.к. они имеют разную размерность!");
        }

        Matrix resultSumMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.rows.length; ++i) {
            for (int j = 0; j < matrix1.rows[i].getSize(); ++j) {
                resultSumMatrix.rows[i].setComponent(j, matrix1.rows[i].getComponent(j)
                        + matrix2.rows[i].getComponent(j));
            }
        }

        return resultSumMatrix;
    }

    public static Matrix getMatricesSubtraction(Matrix matrix1, Matrix matrix2) {    //  Вычитание двух матриц (static).
        if (matrix1.rows.length == 0 || matrix2.rows.length == 0) {
            throw new NullPointerException("ОШИБКА: Одна или обе, переданные в метод вычитания (static), матрицы пустые!");
        }

        if (matrix1.rows.length != matrix2.rows.length || matrix1.rows[0].getSize() != matrix2.rows[0].getSize()) {
            throw new IllegalArgumentException("ОШИБКА: Произвести операцию вычитания (static) с 2 переданными"
                    + " матрицами нельзя, т.к. они имеют разную размерность!");
        }

        Matrix resultSubtractionMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.rows.length; ++i) {
            for (int j = 0; j < matrix1.rows[i].getSize(); ++j) {
                resultSubtractionMatrix.rows[i].setComponent(j, matrix1.rows[i].getComponent(j)
                        - matrix2.rows[i].getComponent(j));
            }
        }

        return resultSubtractionMatrix;
    }

    public static Matrix getMatricesProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows[0].getSize() != matrix2.rows.length) {
            throw new IllegalArgumentException("ОШИБКА: Произвести умножение (static) первой матрицы на вторую нельзя "
                    + "т.к. они не согласованы, т.е. число столбцов в первой матрице не равно числу строк во второй!");
        }

        Matrix resultMultiplicationMatrix = new Matrix(matrix1.rows.length, matrix2.rows[0].getSize());

        for (int i = 0; i < resultMultiplicationMatrix.getRowsQuantity(); ++i) {
            for (int j = 0; j < resultMultiplicationMatrix.getColumnsQuantity(); ++j) {
                resultMultiplicationMatrix.rows[i].setComponent(j,
                        Vector.getScalarProduct(matrix1.getRowByIndex(i), matrix2.getColumnByIndex(j)));
            }
        }

        return resultMultiplicationMatrix;
    }
}
