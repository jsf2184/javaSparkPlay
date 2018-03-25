package com.jsf2184;

import org.junit.Test;

import java.util.Arrays;

public class TwoDimArrayPlay {


    public int[][] initArray() {
        int[][]  res = new int[5][]; // 5 rows;
        for (int row = 0; row < res.length; row++) {
            int columns = row * 2;
            res[row] = new int[columns];
            for (int col=0; col < columns; col++) {
                res[row][col] = 10 * row + col;
            }
        }
        return res;
    }

    public void printArray(int[][] array) {
        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);
    }

    @Test
    public void popAndPrintArray() {
        int[][] array = initArray();
        printArray(array);
    }

}
