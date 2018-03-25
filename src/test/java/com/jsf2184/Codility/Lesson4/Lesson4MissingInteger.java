package com.jsf2184.Codility.Lesson4;

import org.junit.Assert;
import org.junit.Test;

public class Lesson4MissingInteger {
    public int solution(int[] A) {

        int largestPossibleAnswer = A.length+1;
        boolean[] flags = new boolean[largestPossibleAnswer];

        for (int a : A) {
            if (a <0 || a>= largestPossibleAnswer) {
                continue;
            }
            flags[a] = true;
        }

        for (int i=1; i<largestPossibleAnswer; i++) {
            if (!flags[i]) {
                return i;
            }
        }
        return largestPossibleAnswer;
    }

    @Test
    public void testProvidedSampleData() {
        Assert.assertEquals(5, solution(new int[] {1, 3, 6, 4, 1, 2}));
        Assert.assertEquals(4, solution(new int[] {1, 2, 3}));
        Assert.assertEquals(1, solution(new int[] {-1, -3}));
    }

    @Test
    public void testHighValuesThatCouldCauseIndexExceptions() {
        Assert.assertEquals(2, solution(new int[] {1, 3, 4}));
        Assert.assertEquals(2, solution(new int[] {1, 5, 6, 1000000, -1000000}));
    }

}
