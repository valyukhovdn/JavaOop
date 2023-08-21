package ru.academits.valyukhov.matrix;

import ru.academits.valyukhov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsQuantity, int columnsQuantity) {    //  Конструктор Matrix(n, m) – матрица нулей размера n x m.
        if (rowsQuantity < 1) {
            throw new IllegalArgumentException(String.format("Количество строк в матрице должно быть не менее 1. "
                    + "Передано значение: %d", rowsQuantity));
        }

        if (columnsQuantity < 1) {
            throw new IllegalArgumentException(String.format("Количество столбцов в матрице должно быть не менее 1. "
                    + "Передано значение: %d", columnsQuantity));
        }

        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; ++i) {
            rows[i] = new Vector(columnsQuantity);
        }
    }

    public Matrix(Matrix matrix) {              //  Конструктор Matrix(Matrix) – конструктор копирования.
        rows = new Vector[matrix.rows.length];  //  Создаём массив пустых векторов-строк.

        for (int i = 0; i < rows.length; ++i) {    //  Копируем строки переданной матрицы в вектор строк создаваемой матрицы.
            rows[i] = new Vector(matrix.rows[1]);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив, переданный в конструктор, пуст!");
        }

        rows = new Vector[array.length];

        int maxRowSize = 0;

        for (double[] row : array) {
            if (row.length == 0) {
                throw new IllegalArgumentException("Переданный в конструктор массив имеет пустую строку!");
            }

            if (maxRowSize < row.length) {
                maxRowSize = row.length;
            }
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize, array[i]);
        }
    }

    public Matrix(Vector[] rowsArray) {    //  Конструктор Matrix(Vector[]) – из массива векторов-строк.
        if (rowsArray.length == 0) {
            throw new IllegalArgumentException("Массив векторов, переданный в конструктор, пуст!");
        }

        int maxRowSize = 0;                //  Определяем максимальную длину строки передаваемой матрицы.

        for (Vector row : rowsArray) {
            if (row.getSize() == 0) {
                throw new IllegalArgumentException("В массиве векторов, переданном в конструктор, есть пустой вектор-строка!");
            }

            if (maxRowSize < row.getSize()) {
                maxRowSize = row.getSize();
            }
        }

        rows = new Vector[rowsArray.length];      //  Создаём массив пустых векторов-строк.

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize);    //  Задаём каждому вектору-строке длину (автоматом заполняется нулями).
            rows[i].add(rowsArray[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (Vector vector : rows) {
            sb.append(vector);
            sb.append(' ');
        }

        sb.append('\b');
        sb.append('}');

        return sb.toString();
    }

    public int getRowsQuantity() {       // Получение количества строк матрицы.
        return rows.length;
    }

    public int getColumnsQuantity() {    // Получение количества столбцов матрицы.
        return rows[0].getSize();
    }

    public Vector getRowByIndex(int rowIndex) {     //  Получение вектора-строки по индексу.
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("Задан индекс несуществующего номера строки (%d) для "
                    + "получения вектора-строки. Индекс должен быть в диапазоне от 0 до %d.", rowIndex, rows.length - 1));
        }

        return new Vector(rows[rowIndex]);
    }

    public void setRowByIndex(int rowIndex, Vector row) {    // Задание вектора-строки по индексу.
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("Задан индекс несуществующего номера строки (%d) для "
                    + "задания вектора-строки. Индекс должен быть в диапазоне от 0 до %d.", rowIndex, rows.length - 1));
        }

        if (row.getSize() != getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("Переданный вектор-строка имеет размер %d, а требуется %d!",
                    row.getSize(), getColumnsQuantity()));
        }

        for (int i = 0; i < row.getSize(); ++i) {
            rows[rowIndex].setComponent(i, row.getComponent(i));
        }
    }

    public Vector getColumnByIndex(int columnIndex) {   //  Получение вектора-столбца по индексу.
        if (columnIndex < 0 || columnIndex >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("Задан не существующий индекс столбца (%d) для получения "
                    + "вектора-столбца. Индекс должен быть в диапазоне от 0 до %d.", columnIndex, getColumnsQuantity() - 1));
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            column.setComponent(i, rows[i].getComponent(columnIndex));
        }

        return column;
    }

    public void transpose() {      //  Транспонирование матрицы.
        double[][] transposedArray = new double[getColumnsQuantity()][rows.length];

        for (int i = 0; i < getColumnsQuantity(); ++i) {
            for (int j = 0; j < rows.length; ++j) {
                transposedArray[i][j] = getColumnByIndex(i).getComponent(j);
            }
        }

        rows = (new Matrix(transposedArray)).rows;
    }

    public void multiplyByScalar(double scalar) {   //  Умножение на скаляр.
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public Matrix getReducedMatrix(int baseRowIndex, int baseColumnIndex) {    //  Уменьшенная матрица для расчёта определителя основной матрицы.
        Matrix reducedMatrix = new Matrix(rows.length - 1, getColumnsQuantity() - 1);

        for (int i = 0, rowIndex = 0; i < rows.length; ++i) {
            if (i == baseRowIndex) {
                continue;
            }

            for (int j = 0, columnIndex = 0; j < getColumnsQuantity(); ++j) {
                if (j == baseColumnIndex) {
                    continue;
                }

                reducedMatrix.rows[rowIndex].setComponent(columnIndex, rows[i].getComponent(j));

                ++columnIndex;
            }

            ++rowIndex;
        }

        return reducedMatrix;
    }

    public double getDeterminant() {      //  Вычисление определителя матрицы
        if (rows.length != getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("Определитель не может быть вычислен, т.к. "
                    + "матрица не квадратная, а имеет размерность %d x %d.", rows.length, getColumnsQuantity()));
        }

        int baseRowIndex = 0;                   //  Выбираем строку "0" в кач-ве базовой (элементы которой будем умножать определители миноров).
        int rowLength = getColumnsQuantity();   // Определяем длину строк матрицы.
        double determinant = 0;

        if (rows.length == 1) {                 //  Если матрица 1х1, то определитель равен этому элементу.
            return rows[0].getComponent(0);
        }

        for (int i = 0; i < rowLength; ++i) {
            determinant += ((i % 2 == 0) ? 1 : -1) * rows[baseRowIndex].getComponent(i)
                    * getReducedMatrix(baseRowIndex, i).getDeterminant();    //  Рекурсивный вызов функции из самой себя.
        }

        return determinant;
    }

    public Vector multiplyByVector(Vector columnVector) {
        if (getColumnsQuantity() != columnVector.getSize()) {
            throw new IllegalArgumentException(String.format("Число столбцов в матрице (%d) не совпадает "
                    + "с числом элементов в векторе-столбце (%d).", getColumnsQuantity(), columnVector.getSize()));
        }

        double[] resultArray = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            for (int j = 0; j < getColumnsQuantity(); ++j) {
                resultArray[i] += rows[i].getComponent(j) * columnVector.getComponent(j);
            }
        }

        return new Vector(resultArray);
    }

    public boolean hasNoSameDimension(Matrix matrix) {
        return (rows.length != matrix.rows.length || getColumnsQuantity() != matrix.getColumnsQuantity());
    }

    public void add(Matrix matrix) {                 //  Сложение матриц (не static).
        if (hasNoSameDimension(matrix)) {
            throw new IllegalArgumentException(String.format("Прибавить переданную матрицу нельзя, т.к. её "
                            + "размерность (%d x %d) отличается от размерности исходной матрицы (%d x %d)!",
                    matrix.rows.length, matrix.getColumnsQuantity(), rows.length, getColumnsQuantity()));
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {                 //  Вычитание матриц (не static).
        if (hasNoSameDimension(matrix)) {
            throw new IllegalArgumentException(String.format("Вычесть переданную матрицу нельзя, т.к. её "
                            + "размерность (%d x %d) отличается от размерности исходной матрицы (%d x %d)!",
                    matrix.rows.length, matrix.getColumnsQuantity(), rows.length, getColumnsQuantity()));
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {    //  Сложение двух матриц (static).
        if (matrix1.hasNoSameDimension(matrix2)) {
            throw new IllegalArgumentException(String.format("Сложить 2 переданные матрицы нельзя, т.к. они имеют разную " +
                            "размерность: (%d x %d) и (%d x %d).", matrix1.rows.length, matrix1.getColumnsQuantity(),
                    matrix2.rows.length, matrix2.getColumnsQuantity()));
        }

        Matrix resultMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.rows.length; ++i) {
            resultMatrix.rows[i] = Vector.getSum(matrix1.rows[i], matrix2.rows[i]);
        }

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {    //  Вычитание двух матриц (static).
        if (matrix1.hasNoSameDimension(matrix2)) {
            throw new IllegalArgumentException(String.format("Произвести операцию вычитания (static) с 2 переданными"
                            + " матрицами нельзя, т.к. они имеют разную размерность: (%d x %d) и (%d x %d).",
                    matrix1.rows.length, matrix1.getColumnsQuantity(), matrix2.rows.length, matrix2.getColumnsQuantity()));
        }

        Matrix resultMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.rows.length; ++i) {
            resultMatrix.rows[i] = Vector.getDifference(matrix1.rows[i], matrix2.rows[i]);
        }

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsQuantity() != matrix2.rows.length) {
            throw new IllegalArgumentException(String.format("Произвести умножение (static) первой матрицы на вторую нельзя "
                    + "т.к. они не согласованы, т.е. число столбцов в первой матрице (%d) не равно числу строк "
                    + "во второй (%d)!", matrix1.getColumnsQuantity(), matrix2.rows.length));
        }

        Matrix resultMatrix = new Matrix(matrix1.rows.length, matrix2.getColumnsQuantity());

        for (int i = 0; i < resultMatrix.getRowsQuantity(); ++i) {
            for (int j = 0; j < resultMatrix.getColumnsQuantity(); ++j) {
                resultMatrix.rows[i].setComponent(j,
                        Vector.getScalarProduct(matrix1.getRowByIndex(i), matrix2.getColumnByIndex(j)));
            }
        }

        return resultMatrix;
    }
}
