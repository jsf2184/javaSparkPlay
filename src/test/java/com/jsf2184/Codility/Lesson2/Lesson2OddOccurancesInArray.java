package com.jsf2184.Codility.Lesson2;

import org.junit.Assert;
import org.junit.Test;

public class Lesson2OddOccurancesInArray {

    public int solution(int[] A) {
        int res = 0;
        for (int i=0; i<A.length; i++) {
            int elem = A[i];
            res = elem ^ res;
        }
        return res;
    }

    @Test
    public void testPairsZeroOut() {
        Assert.assertEquals(0, solution(new int[] {3, 4, 5, 5, 4, 3}));
    }
    @Test
    public void testOneUnpaired() {
        Assert.assertEquals(25, solution(new int[] {9, 7, 25, 9, 7}));
    }

}
