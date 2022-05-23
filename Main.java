package matrixMultiplication;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        Matrix m1 = getRandomMatrix(300,300,random);
        Matrix m2 = getRandomMatrix(300,300,random);

        Matrix m1b = new Matrix(m1);
        Matrix m2b = new Matrix(m2);

        long start = System.currentTimeMillis();
        Matrix result1 = new MatrixMultiSerial().multiply(m1,m2);
        long end = System.currentTimeMillis();

        System.out.println("Serial : " + (end-start));

        start = System.currentTimeMillis();
        Matrix result2 = new MatrixMultiByThread().multiply(m1b,m2b);
        end = System.currentTimeMillis();

        System.out.println("Parallel : " + (end-start));
        System.out.println("Same results : " + result1.equals(result2));


    }

    public static Matrix getRandomMatrix(int rows , int cols , Random random){
        Matrix m = new Matrix(rows,cols);

        for (int x = 0 ; x < cols ; ++x){
            for (int y = 0 ; y < rows ; ++y){
                m.set(x,y,random.nextDouble());
            }
        }
        return m;
    }
}
