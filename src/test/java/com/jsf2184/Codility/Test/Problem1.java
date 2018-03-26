package com.jsf2184.Codility.Test;

import org.junit.Assert;
import org.junit.Test;

public class Problem1 {

    int[] sampleData9 = {1, 3, 2, 1, 2, 1, 5, 3, 3, 4, 2};
    int[] sampleData8 = {5, 8};
    int[] sampleData1 = {1, 1, 1, 1};

    public int solution(int[] A) {
        long total = 0;

        for (int r=1; true; r++) {
            int rowStrokes = processRow(r, A);
            if (rowStrokes == 0) {
                // r is past the highest height so we are done.
                break;
            }
            total += rowStrokes;
            if (total > 1000000000) {
                return -1;
            }
        }
        return (int) total;
    }

    public static int processRow(int r, int[] A) {
        int res = 0;
        boolean lastOne = false;
        for (int a : A) {
            if (r <= a) {
                if (!lastOne) {
                    res++;
                    lastOne = true;
                }
            } else {
                lastOne = false;
            }
        }
        return res;
    }

    @Test
    public void testSolution() {
        Assert.assertEquals(9, solution(sampleData9));
        Assert.assertEquals(8, solution(sampleData8));
        Assert.assertEquals(1, solution(sampleData1));
    }
    @Test
    public void testProcessRow() {
        Assert.assertEquals(1, processRow(1, sampleData9));
        Assert.assertEquals(3, processRow(2, sampleData9));
        Assert.assertEquals(2, processRow(3, sampleData9));
        Assert.assertEquals(2, processRow(4, sampleData9));
        Assert.assertEquals(1, processRow(5, sampleData9));
        Assert.assertEquals(0, processRow(6, sampleData9));
    }

//    @Test
//    public void testMax() {
//        Assert.assertEquals(7, findMax( new int[] {2, 3, 4, 7, 1}));
//        Assert.assertEquals(1, findMax( new int[] {1, 1}));
//    }

//    int findMax(int[] A) {
//        int res = 0;
//        for (int i=0; i<A.length; i++) {
//            if (A[i] > res) {
//                res = A[i];
//            }
//        }
//        return res;
//    }

}
