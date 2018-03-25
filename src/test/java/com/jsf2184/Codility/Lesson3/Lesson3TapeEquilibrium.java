package com.jsf2184.Codility.Lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Lesson3TapeEquilibrium {

    @Test
    public void writeupExampleTest() {
        runit(new int[] {3, 1, 2, 4, 3}, 1);
    }

    @Test
    public void smallArrayTest() {
        runit(new int[] {-1000, 1000}, 2000);
    }


    public void runit(int[] A, int expected) {
        int actual = solution(A);
        Assert.assertEquals(expected, actual);
    }

    public int solution(int[] A) {

        int total = 0;
        for (int i=0; i< A.length; i++) {
            total += (A[i]);
        }
        int runningTotal = 0;
        Integer minDiff = null;
        for (int p=1; p < A.length; p++) {
            runningTotal += A[p-1];
            int otherHalf = total - runningTotal;
            int diff = Math.abs(runningTotal - otherHalf );
            if (minDiff == null || diff < minDiff) {
                minDiff = diff;
            }
        }
        return minDiff;
    }
}
