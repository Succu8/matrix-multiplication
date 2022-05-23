package matrixMultiplication;

import java.util.Objects;

public class MatrixMultiByThread implements MatrixMultiplier {

    private static final int MINIMUM_WORKLOAD = 10000;

    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left, "The left matrix is null.");
        Objects.requireNonNull(right, "The right matrix is null.");
        checkDimensions(left, right);

        int workLoad = left.getRows() * right.getColumns() * right.getRows();
        int numberOfTreads = Runtime.getRuntime().availableProcessors();
        numberOfTreads = Math.min(numberOfTreads, workLoad / MINIMUM_WORKLOAD);
        numberOfTreads = Math.min(numberOfTreads, left.getRows());
        numberOfTreads = Math.max(numberOfTreads, 1);

        if (numberOfTreads == 1) {
            return new MatrixMultiSerial().multiply(left, right);
        }

        Matrix result = new Matrix(left.getRows(), right.getColumns());
        MultiplierThread[] threads = new MultiplierThread[numberOfTreads - 1];
        int basicRowsPerThreads = left.getRows() / numberOfTreads;
        int startRow = 0;

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new MultiplierThread(left, right, result, startRow, basicRowsPerThreads);
            threads[i].start();
            startRow += basicRowsPerThreads;
        }

        new MultiplierThread(left, right, result,
                startRow, basicRowsPerThreads + left.getRows() % basicRowsPerThreads)
                .run();

        for (MultiplierThread thread : threads){
            try {
                thread.join();
            }catch (InterruptedException ex){
                throw new RuntimeException("A thread interrupted." , ex);
            }
        }

        return result;
    }

    public static final class MultiplierThread extends Thread {

        private final Matrix left;
        private final Matrix right;
        private final Matrix result;
        private final int startRow;
        private final int rows;

        public MultiplierThread(Matrix left, Matrix right, Matrix result, int startRow, int rows) {
            this.left = left;
            this.right = right;
            this.result = result;
            this.startRow = startRow;
            this.rows = rows;
        }

        @Override
        public void run() {
            for (int y = startRow; y < startRow + rows; ++y) {
                for (int x = 0; x < right.getColumns(); ++x) {
                    double sum = 0.0;

                    for (int i = 0; i < left.getColumns(); ++i) {
                        sum += left.get(i, y) * right.get(x, i);
                    }
                    result.set(x, y, sum);
                }
            }
        }
    }

    private void checkDimensions(Matrix left, Matrix right) {
        if (left.getColumns() != right.getRows()) {
            throw new IllegalArgumentException(
                    "Trying to multiply non-compatible matrix. Columns of " +
                            "left matrix: " + left.getColumns() + ". Rows of " +
                            "right matrix: " + right.getRows()
            );
        }
    }
}
