package com.jsf2184.Codility.Lesson4;

import org.junit.Assert;
import org.junit.Test;

public class Lesson4FrogRiverOne {
    public int solution(int X, int[] A) {
        if (X == 0) {
            return 0;
        }

        boolean[] flags = new boolean[X];
        int numFalses = X;
        for (int i=0; i< A.length; i++) {

            int pos = A[i];
            if (pos > X) {
                // the leaf fell on the other shore but we don't have a slot in the array for that.
                continue;
            }
            int flagsIdx = pos - 1;

            if (!flags[flagsIdx]) {
                numFalses--;
                flags[flagsIdx] = true;
                if (numFalses == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Test
    public void testProvidedSampleData() {
        int[] A = {1, 3, 1, 4, 2, 3, 5, 4};
        int res = solution(5, A);
        Assert.assertEquals(6, res);
    }

    @Test
    public void testZeroLengthData() {
        int[] A = {};
        int res = solution(1, A);
        Assert.assertEquals(-1, res);
    }


    @Test
    public void testZeroLengthDataWithNowhereToGo() {
        int[] A = {};
        int res = solution(0, A);
        Assert.assertEquals(0, res);
    }

    @Test
    public void testSimpleSuccessWithBadIndexOnTheWay() {
        // this tests our ability to handle a bad index.
        int res = solution(1, new int[]{2, 1});
        Assert.assertEquals(1, res);
    }


    @Test
    public void testSimpleFailureWithBadIndexOnTheWay() {
        // this tests our ability to handle a bad index.
        int res = solution(1, new int[]{2, 2});
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testSimpleSuccesses() {
        int res = solution(1, new int[]{1, 2});
        Assert.assertEquals(0, res);

        res = solution(2, new int[]{1, 1, 1, 2});
        Assert.assertEquals(3, res);

    }


}
