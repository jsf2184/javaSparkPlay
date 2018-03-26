package com.jsf2184.Codility.Lesson14;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BinarySearchPractice {

    public Integer binarySearch(int val, int[] A) {
        Arrays.sort(A);
        Integer res = binarySearchSortedArray(val, A);
        return res;
    }

    private Integer binarySearchSortedArray(int val, int[] A) {
        int len = A.length;
        int start = 0;
        while (len > 0) {
            int idx = start + len/2;
            int guess = A[idx];
            if (guess == val) {
                return idx;
            }
            if (val < guess) {
                len = idx - start;
            } else {
                len = start + len - idx - 1;
                start = idx + 1;
            }
        }
        return null;
    }

    private Integer altSearchSortedArray(int val, int[] A) {
        System.out.printf("Searching for %d\n", val);
        int start = 0;
        int end = A.length;
        while (start <= end) {
            int idx = (start + end)/2;
            int guess = A[idx];
            if (guess == val) {
                return idx;
            }
            if (val < guess) {
                end = idx - 1;
            } else {
                start = idx + 1;
            }
        }
        return null;
    }


    @Test
    public void testGoodSearch() {
        int[] A = popArray(20, 2);
        printArray(A);
        Assert.assertEquals(2, altSearchSortedArray(4, A).intValue());

        for (int i=0; i<20; i++) {
            Assert.assertEquals(i, binarySearchSortedArray(2 * i, A).intValue());
            Assert.assertEquals(i, altSearchSortedArray(2 * i, A).intValue());
        }
        Assert.assertEquals(0, binarySearch(0, A).intValue());
    }

    @Test
    public void testBadSearch() {
        int[] A = popArray(20, 2);
        printArray(A);
        for (int i=0; i<20; i++) {
            Assert.assertNull(binarySearch((2 * i) + 1, A));
        }


    }

    public void printArray(int[] A) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< A.length; i++) {
            sb.append(String.format("[%02d]=%d ", i, A[i]));
        }
        System.out.println(sb.toString());
    }

    int[] popArray(int n, int gap) {
        int[] res = new int[n];
        for (int i=0; i < n; i++) {
            res[i] = i*gap;
        }
        return res;
    }
}

