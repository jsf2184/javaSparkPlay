package com.jsf2184.Codility.Lesson4;

import org.junit.Assert;
import org.junit.Test;

public class Lesson4PermCheck {

    public int solution(int[] A) {
        int length = A.length;
        boolean[] flags = new boolean[length];

        for (int a: A) {
            if (a < 1 || a > length) {
                return 0;
            }
            int idx = a-1;
            if (flags[idx]) {
                // got a repeat so we'll be missing another one.
                return 0;
            }
            flags[idx] = true;
        }
        return 1;
    }

    @Test
    public void testProvidedSampleData() {
        Assert.assertEquals(1, solution(new int[] {4, 1, 3, 2}));
        Assert.assertEquals(0, solution(new int[] {4, 1, 3}));
    }

    @Test
    public void testSuccess() {
        Assert.assertEquals(1, solution(new int[] {1}));
        Assert.assertEquals(1, solution(new int[] {2, 1}));
        Assert.assertEquals(1, solution(new int[] {6,5,4,3,2,1}));
    }

    @Test
    public void testFail() {
        Assert.assertEquals(0, solution(new int[] {2}));
        Assert.assertEquals(0, solution(new int[] {2, 2}));
        Assert.assertEquals(0, solution(new int[] {5,3,2,1}));
    }


}
