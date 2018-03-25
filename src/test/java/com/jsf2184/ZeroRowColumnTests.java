package com.jsf2184;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class ZeroRowColumnTests {


    public static boolean isZeroInRow(int r, int numColumns, int[][] matrix) {
        for(int c=0; c<numColumns; c++) {
            if(matrix[r][c] == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isZeroInColumn(int c, int numRows, int[][] matrix) {
        for(int r=0; r<numRows; r++) {
            if(matrix[r][c] == 0) {
                return true;
            }
        }
        return false;
    }


    public static void process(int[][] matrix) {

        int[] topRow = matrix[0];
        int numColumns = topRow.length;
        int numRows = matrix.length;

        boolean zeroInFirstRow = isZeroInRow(0, numColumns, matrix);
        boolean zeroInFirstColumn = isZeroInColumn(0, numRows, matrix);

        for (int r = 1; r< numRows; r++) {
            for (int c=1; c< matrix[r].length; c++) {
                int val = matrix[r][c];
                if (val == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        for (int r = 1; r< numRows; r++) {
            if (matrix[r][0] == 0) {
                setRowToZero(r, numColumns, matrix);
            }
        }

        for (int c = 1; c< numColumns; c++ ) {
            if (matrix[0][c] == 0) {
                setColumnToZero(c, numRows, matrix);
            }
        }

        if (zeroInFirstColumn) {
            setColumnToZero(0, numRows, matrix);
        }

        if (zeroInFirstRow) {
            setRowToZero(0, numColumns, matrix);
        }
    }

    public static void setRowToZero(int r, int numColumns, int[][] matrix) {
        for (int c=0; c< numColumns; c++) {
            matrix[r][c] = 0;
        }
    }


    public static void setColumnToZero(int c, int numRows, int[][] matrix) {
        for (int r=0; r< numRows; r++) {
            matrix[r][c] = 0;
        }
    }

    public static int[][] popArray(int numRows, int numCols) {
        Random random = new Random();
        int[][] res = new int[numRows][numCols];
        for (int r=0; r<numRows; r++) {
            for (int c=0; c<numCols; c++) {
                int v = random.nextInt(numRows);
                res[r][c] = v;
            }
        }
        return res;
    }

    public static int[][] copyMatrix(int[][] matrix) {
        int numRows = matrix.length;
        int[][] res = new int[numRows][];
        for (int r=0; r<numRows; r++) {
            int numColumns = matrix[r].length;
            res[r] = new int[numColumns];
            for (int c=0; c<numColumns; c++) {
                res[r][c] = matrix[r][c];
            }
        }
        return res;
    }


    public static void printArray(int[][] array) {
        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);
    }

    public static void validate(int[][] original, int[][] result) {
        int numRows = result.length;
        for (int r=0; r< numRows; r++) {
            int numColumns = result[r].length;
            for (int c = 0; c< numColumns; c++) {
                boolean actualZero = result[r][c] == 0;
                boolean expectZero = original[r][c] == 0;
                expectZero |= isZeroInColumn(c, numRows, original);
                expectZero |= isZeroInRow(r, numColumns, original);
                Assert.assertEquals(expectZero, actualZero);
            }
        }
    }

    public static void processAndTest() {
        int[][] original = popArray(8, 6);
        int[][] result = copyMatrix(original);
        System.out.println("\nOriginal\n");
        printArray(original);
        process(result);
        System.out.println("\nResults\n");
        printArray(result);
        validate(original, result);
    }


    @Test
    public void testIt() {
        processAndTest();
    }
}
