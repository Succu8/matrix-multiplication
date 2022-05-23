package matrixMultiplication;

import java.util.Objects;

public final class MatrixMultiSerial implements MatrixMultiplier{
    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left , "The left matrix is null.");
        Objects.requireNonNull(right , "The right matrix is null.");
        checkDimensions(left ,right);

        Matrix result = new Matrix(left.getRows() , right.getColumns());

        for (int y = 0 ; y < left.getRows() ; ++y){
            for (int x = 0 ; x < right.getColumns() ; ++x){
                double sum = 0.0;

                for (int i = 0 ; i < left.getColumns() ; ++i){
                    sum += left.get(i,y) * right.get(x , i);
                }
                result.set(x , y , sum);
            }
        }

        return result;
    }

    private void checkDimensions(Matrix left, Matrix right) {
        if(left.getColumns() != right.getRows()){
            throw new IllegalArgumentException(
                    "Trying to multiply non-compatible matrix. Columns of " +
                            "left matrix: " + left.getColumns() + ". Rows of " +
                            "right matrix: " + right.getRows()
            );
        }
    }
}
