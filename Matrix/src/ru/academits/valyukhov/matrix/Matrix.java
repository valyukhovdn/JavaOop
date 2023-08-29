package ru.academits.valyukhov.matrix;

import ru.academits.valyukhov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    //  Конструктор Matrix(n, m) – матрица нулей размера n x m.
    public Matrix(int rowsQuantity, int columnsQuantity) {
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

    //  Конструктор Matrix(Matrix) – конструктор копирования.
    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];     //  Создаём массив пустых векторов-строк.

        for (int i = 0; i < rows.length; ++i) {    //  Копируем строки переданной матрицы в вектор строк создаваемой матрицы.
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    //  Конструктор Matrix(double[][]) – из двумерного массива.
    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив, переданный в конструктор, пуст!");
        }

        rows = new Vector[array.length];

        int maxRowSize = 0;

        for (double[] row : array) {
            if (maxRowSize < row.length) {
                maxRowSize = row.length;
            }
        }

        if (maxRowSize == 0) {
            throw new IllegalArgumentException("Переданный в конструктор двумерный массив состоит из пустых строк.");
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize, array[i]);
        }
    }

    //  Конструктор Matrix(Vector[]) – из массива векторов-строк.
    public Matrix(Vector[] rowsArray) {
        if (rowsArray.length == 0) {
            throw new IllegalArgumentException("Массив векторов, переданный в конструктор, пуст!");
        }

        int maxRowSize = 0;                //  Определяем максимальную длину строки передаваемой матрицы.

        for (Vector row : rowsArray) {
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
            sb.append(vector).append(' ');
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');

        return sb.toString();
    }

    //  Получение количества строк матрицы.
    public int getRowsQuantity() {
        return rows.length;
    }

    //  Получение количества столбцов матрицы.
    public int getColumnsQuantity() {
        return rows[0].getSize();
    }

    //  Получение и задание вектора-строки по индексу.
    public Vector getRowByIndex(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("В метод получения вектора-строки передан индекс "
                    + "несуществующей строки (%d). Индекс должен быть в диапазоне от 0 до %d.", rowIndex, rows.length - 1));
        }

        return new Vector(rows[rowIndex]);
    }

    // Задание вектора-строки по индексу.
    public void setRowByIndex(int rowIndex, Vector row) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("В метод задания вектора-строки по индексу передан индекс"
                    + "несуществующей строки (%d). Индекс должен быть в диапазоне от 0 до %d.", rowIndex, rows.length - 1));
        }

        if (row.getSize() != getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("Переданный вектор-строка имеет размер %d, а требуется %d!",
                    row.getSize(), getColumnsQuantity()));
        }

        for (int i = 0; i < row.getSize(); ++i) {
            rows[rowIndex].setElement(i, row.getElement(i));
        }
    }

    //  Получение вектора-столбца по индексу.
    public Vector getColumnByIndex(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("В метод получения вектора-столбца передан индекс "
                    + "несуществующего столбца (%d). Индекс должен быть в диапазоне от 0 до %d.", columnIndex, getColumnsQuantity() - 1));
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            column.setElement(i, rows[i].getElement(columnIndex));
        }

        return column;
    }

    //  Транспонирование матрицы.
    public void transpose() {
        Vector[] transposedRows = new Vector[getColumnsQuantity()];

        for (int i = 0; i < transposedRows.length; ++i) {
            transposedRows[i] = getColumnByIndex(i);
        }

        rows = transposedRows;
    }

    //  Умножение матрицы на скаляр.
    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    //  Получение уменьшенной матрицы для расчёта определителя основной матрицы.
    public Matrix getReducedMatrix(int baseRowIndex, int baseColumnIndex) {
        Matrix reducedMatrix = new Matrix(rows.length - 1, getColumnsQuantity() - 1);

        for (int i = 0, rowIndex = 0; i < rows.length; ++i) {
            if (i == baseRowIndex) {
                continue;
            }

            for (int j = 0, columnIndex = 0; j < getColumnsQuantity(); ++j) {
                if (j == baseColumnIndex) {
                    continue;
                }

                reducedMatrix.rows[rowIndex].setElement(columnIndex, rows[i].getElement(j));

                ++columnIndex;
            }

            ++rowIndex;
        }

        return reducedMatrix;
    }

    //  Вычисление определителя матрицы.
    public double getDeterminant() {
        if (rows.length != getColumnsQuantity()) {
            throw new UnsupportedOperationException(String.format("Определитель не может быть вычислен, т.к. "
                    + "матрица не квадратная, а имеет размерность %d x %d.", rows.length, getColumnsQuantity()));
        }

        int baseRowIndex = 0;                   //  Выбираем строку "0" в кач-ве базовой (элементы которой будем умножать определители миноров).
        int rowLength = getColumnsQuantity();   // Определяем длину строк матрицы.
        double determinant = 0;

        if (rows.length == 1) {                 //  Если матрица 1х1, то определитель равен этому элементу.
            return rows[0].getElement(0);
        }

        for (int i = 0; i < rowLength; ++i) {
            determinant += ((i % 2 == 0) ? 1 : -1) * rows[baseRowIndex].getElement(i)
                    * getReducedMatrix(baseRowIndex, i).getDeterminant();    //  Рекурсивный вызов функции из самой себя.
        }

        return determinant;
    }

    //  Умножение матрицы на вектор.
    public Vector multiplyByVector(Vector columnVector) {
        if (getColumnsQuantity() != columnVector.getSize()) {
            throw new IllegalArgumentException(String.format("Число столбцов в матрице (%d) не совпадает "
                    + "с числом элементов в векторе-столбце (%d).", getColumnsQuantity(), columnVector.getSize()));
        }

        Vector resultColumn = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            resultColumn.setElement(i, Vector.getScalarProduct(rows[i], columnVector));
        }

        return resultColumn;
    }

    //  Проверка матриц на равенство размерностей.
    public void hasSameDimension(Matrix matrix) {
        if (rows.length != matrix.rows.length || getColumnsQuantity() != matrix.getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("Матрицы имеют разные размерности: (%d x %d) и (%d x %d)."
                            + "Поэтому операции сложения и вычитания между ними производить невозможно!",
                    matrix.rows.length, matrix.getColumnsQuantity(), rows.length, getColumnsQuantity()));
        }
    }

    //  Сложение матриц (не static).
    public void add(Matrix matrix) {
        hasSameDimension(matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    //  Вычитание матриц (не static).
    public void subtract(Matrix matrix) {
        hasSameDimension(matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    //  Сложение двух матриц (static).
    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.hasSameDimension(matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    //  Вычитание двух матриц (static).
    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        matrix1.hasSameDimension(matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    //  Умножение матриц (static).
    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsQuantity() != matrix2.rows.length) {
            throw new IllegalArgumentException(String.format("Произвести умножение (static) первой матрицы на вторую нельзя "
                    + "т.к. они не согласованы, т.е. число столбцов в первой матрице (%d) не равно числу строк "
                    + "во второй (%d)!", matrix1.getColumnsQuantity(), matrix2.rows.length));
        }

        Matrix resultMatrix = new Matrix(matrix1.rows.length, matrix2.getColumnsQuantity());

        for (int i = 0; i < resultMatrix.getRowsQuantity(); ++i) {
            for (int j = 0; j < resultMatrix.getColumnsQuantity(); ++j) {
                resultMatrix.rows[i].setElement(j, Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumnByIndex(j)));
            }
        }

        return resultMatrix;
    }
}
