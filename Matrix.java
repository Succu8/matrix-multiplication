package matrixMultiplication;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
    private final int rows;
    private final int columns;
    private final double[] arr;

    public Matrix(int rows, int columns) {
        this.rows = checkRows(rows);
        this.columns = checkColumns(columns);
        this.arr = new double[rows * columns];
    }

    public Matrix(Matrix matrix) {
        Objects.requireNonNull(matrix , "The matrix is null.");
        this.rows = matrix.rows;
        this.columns = matrix.columns;
        this.arr = matrix.arr.clone();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double get(int x, int y) {
        return arr[index(x, y)];
    }

    public void set(int x, int y, double value) {
        arr[index(x, y)] = value;
    }

    public int index(int x, int y) {
        checkX(x);
        checkY(y);
        return y * columns + x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = "";

        for (int y = 0 ; y < rows ; ++y){
            sb.append(separator);
            separator = "\n";

            for (int x = 0 ; x < columns ; ++x){
                sb.append(get(x,y)).append(" ");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( o == this) return true;
        if(!getClass().equals(o.getClass())) return false;

        Matrix matrix = (Matrix) o;

        if(getRows() != matrix.getRows()) return false;

        if (getColumns() != matrix.getColumns()) return false;

        return Arrays.equals(arr , matrix.arr);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.hashCode(arr);
        return result;
    }

    private void checkX(int x) {
        if (x < 0) {
            throw new IndexOutOfBoundsException("x is negative: " + x);
        }
        if (x >= columns) {
            throw new IndexOutOfBoundsException("x is too large: " + x + ". " +
                    "The matrix has " + columns + " columns.");
        }
    }

    private void checkY(int y) {
        if (y < 0) {
            throw new IndexOutOfBoundsException("y is negative: " + y);
        }
        if (y >= columns) {
            throw new IndexOutOfBoundsException("y is too large: " + y + ". " +
                    "The matrix has " + rows + " rows.");
        }
    }

    private int checkRows(int rows) {
        return check(rows, "The number of rows is too small: " + rows);
    }

    private int checkColumns(int columns) {
        return check(columns, "The number of columns is too small: " + columns);
    }

    private int check(int number, String errorMessage) {
        if (number < 1) {
            throw new IllegalArgumentException(errorMessage);
        }
        return number;
    }
}
