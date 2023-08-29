package ru.academits.valyukhov.matrix;

import ru.academits.valyukhov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    //  ����������� Matrix(n, m) � ������� ����� ������� n x m.
    public Matrix(int rowsQuantity, int columnsQuantity) {
        if (rowsQuantity < 1) {
            throw new IllegalArgumentException(String.format("���������� ����� � ������� ������ ���� �� ����� 1. "
                    + "�������� ��������: %d", rowsQuantity));
        }

        if (columnsQuantity < 1) {
            throw new IllegalArgumentException(String.format("���������� �������� � ������� ������ ���� �� ����� 1. "
                    + "�������� ��������: %d", columnsQuantity));
        }

        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; ++i) {
            rows[i] = new Vector(columnsQuantity);
        }
    }

    //  ����������� Matrix(Matrix) � ����������� �����������.
    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];     //  ������ ������ ������ ��������-�����.

        for (int i = 0; i < rows.length; ++i) {    //  �������� ������ ���������� ������� � ������ ����� ����������� �������.
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    //  ����������� Matrix(double[][]) � �� ���������� �������.
    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("������, ���������� � �����������, ����!");
        }

        rows = new Vector[array.length];

        int maxRowSize = 0;

        for (double[] row : array) {
            if (maxRowSize < row.length) {
                maxRowSize = row.length;
            }
        }

        if (maxRowSize == 0) {
            throw new IllegalArgumentException("���������� � ����������� ��������� ������ ������� �� ������ �����.");
        }

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize, array[i]);
        }
    }

    //  ����������� Matrix(Vector[]) � �� ������� ��������-�����.
    public Matrix(Vector[] rowsArray) {
        if (rowsArray.length == 0) {
            throw new IllegalArgumentException("������ ��������, ���������� � �����������, ����!");
        }

        int maxRowSize = 0;                //  ���������� ������������ ����� ������ ������������ �������.

        for (Vector row : rowsArray) {
            if (maxRowSize < row.getSize()) {
                maxRowSize = row.getSize();
            }
        }

        rows = new Vector[rowsArray.length];      //  ������ ������ ������ ��������-�����.

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(maxRowSize);    //  ����� ������� �������-������ ����� (��������� ����������� ������).
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

    //  ��������� ���������� ����� �������.
    public int getRowsQuantity() {
        return rows.length;
    }

    //  ��������� ���������� �������� �������.
    public int getColumnsQuantity() {
        return rows[0].getSize();
    }

    //  ��������� � ������� �������-������ �� �������.
    public Vector getRowByIndex(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("� ����� ��������� �������-������ ������� ������ "
                    + "�������������� ������ (%d). ������ ������ ���� � ��������� �� 0 �� %d.", rowIndex, rows.length - 1));
        }

        return new Vector(rows[rowIndex]);
    }

    // ������� �������-������ �� �������.
    public void setRowByIndex(int rowIndex, Vector row) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("� ����� ������� �������-������ �� ������� ������� ������"
                    + "�������������� ������ (%d). ������ ������ ���� � ��������� �� 0 �� %d.", rowIndex, rows.length - 1));
        }

        if (row.getSize() != getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("���������� ������-������ ����� ������ %d, � ��������� %d!",
                    row.getSize(), getColumnsQuantity()));
        }

        for (int i = 0; i < row.getSize(); ++i) {
            rows[rowIndex].setElement(i, row.getElement(i));
        }
    }

    //  ��������� �������-������� �� �������.
    public Vector getColumnByIndex(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("� ����� ��������� �������-������� ������� ������ "
                    + "��������������� ������� (%d). ������ ������ ���� � ��������� �� 0 �� %d.", columnIndex, getColumnsQuantity() - 1));
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            column.setElement(i, rows[i].getElement(columnIndex));
        }

        return column;
    }

    //  ���������������� �������.
    public void transpose() {
        Vector[] transposedRows = new Vector[getColumnsQuantity()];

        for (int i = 0; i < transposedRows.length; ++i) {
            transposedRows[i] = getColumnByIndex(i);
        }

        rows = transposedRows;
    }

    //  ��������� ������� �� ������.
    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    //  ��������� ����������� ������� ��� ������� ������������ �������� �������.
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

    //  ���������� ������������ �������.
    public double getDeterminant() {
        if (rows.length != getColumnsQuantity()) {
            throw new UnsupportedOperationException(String.format("������������ �� ����� ���� ��������, �.�. "
                    + "������� �� ����������, � ����� ����������� %d x %d.", rows.length, getColumnsQuantity()));
        }

        int baseRowIndex = 0;                   //  �������� ������ "0" � ���-�� ������� (�������� ������� ����� �������� ������������ �������).
        int rowLength = getColumnsQuantity();   // ���������� ����� ����� �������.
        double determinant = 0;

        if (rows.length == 1) {                 //  ���� ������� 1�1, �� ������������ ����� ����� ��������.
            return rows[0].getElement(0);
        }

        for (int i = 0; i < rowLength; ++i) {
            determinant += ((i % 2 == 0) ? 1 : -1) * rows[baseRowIndex].getElement(i)
                    * getReducedMatrix(baseRowIndex, i).getDeterminant();    //  ����������� ����� ������� �� ����� ����.
        }

        return determinant;
    }

    //  ��������� ������� �� ������.
    public Vector multiplyByVector(Vector columnVector) {
        if (getColumnsQuantity() != columnVector.getSize()) {
            throw new IllegalArgumentException(String.format("����� �������� � ������� (%d) �� ��������� "
                    + "� ������ ��������� � �������-������� (%d).", getColumnsQuantity(), columnVector.getSize()));
        }

        Vector resultColumn = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            resultColumn.setElement(i, Vector.getScalarProduct(rows[i], columnVector));
        }

        return resultColumn;
    }

    //  �������� ������ �� ��������� ������������.
    public void hasSameDimension(Matrix matrix) {
        if (rows.length != matrix.rows.length || getColumnsQuantity() != matrix.getColumnsQuantity()) {
            throw new IllegalArgumentException(String.format("������� ����� ������ �����������: (%d x %d) � (%d x %d)."
                            + "������� �������� �������� � ��������� ����� ���� ����������� ����������!",
                    matrix.rows.length, matrix.getColumnsQuantity(), rows.length, getColumnsQuantity()));
        }
    }

    //  �������� ������ (�� static).
    public void add(Matrix matrix) {
        hasSameDimension(matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    //  ��������� ������ (�� static).
    public void subtract(Matrix matrix) {
        hasSameDimension(matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    //  �������� ���� ������ (static).
    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.hasSameDimension(matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    //  ��������� ���� ������ (static).
    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        matrix1.hasSameDimension(matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    //  ��������� ������ (static).
    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsQuantity() != matrix2.rows.length) {
            throw new IllegalArgumentException(String.format("���������� ��������� (static) ������ ������� �� ������ ������ "
                    + "�.�. ��� �� �����������, �.�. ����� �������� � ������ ������� (%d) �� ����� ����� ����� "
                    + "�� ������ (%d)!", matrix1.getColumnsQuantity(), matrix2.rows.length));
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
