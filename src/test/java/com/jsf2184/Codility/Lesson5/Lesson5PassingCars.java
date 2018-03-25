package com.jsf2184.Codility.Lesson5;

import org.junit.Assert;
import org.junit.Test;

public class Lesson5PassingCars {
    public int solution(int[] A) {
        long res = 0;
        long totalOnes = 0;
        for (int i = A.length-1; i>= 0; i--) {
            if (A[i] == 0) {
                res += totalOnes;
            } else {
                totalOnes++;
            }
            if (res > 1000000000L) {
                return -1;
            }
        }
        return (int) res;
    }

    @Test
    public void testSampleData() {
        Assert.assertEquals(5, solution(new int[] {0, 1, 0, 1, 1}));
    }

    @Test
    public void testSize1() {
        Assert.assertEquals(0, solution(new int[] {0}));
        Assert.assertEquals(0, solution(new int[] {1}));

    }
}
