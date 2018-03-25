package com.jsf2184.Codility.Lesson5;

import org.junit.Assert;
import org.junit.Test;

public class Lesson5CountDiv {
    public int solution(int A, int B, int K) {
        int rem = A%K;
        if (rem != 0) {
            A+= (K - rem);
        }
        int gap = B - A;
        if (gap < 0) {return  0;}
        int res = 1 + (gap / K);
        return res;
    }

    @Test
    public void runTests() {
        Assert.assertEquals(0, solution(7, 8, 3));
        Assert.assertEquals(1, solution(7, 9, 3));
        Assert.assertEquals(1, solution(7, 10, 3));
        Assert.assertEquals(1, solution(7, 11, 3));
        Assert.assertEquals(2, solution(7, 12, 3));

        Assert.assertEquals(1, solution(8, 9, 3));
        Assert.assertEquals(1, solution(9, 9, 3));

        Assert.assertEquals(1, solution(0, 0, 3));
        Assert.assertEquals(1, solution(0, 1, 3));
        Assert.assertEquals(1, solution(0, 2, 3));
        Assert.assertEquals(2, solution(0, 3, 3));

        Assert.assertEquals(0, solution(2000000000, 2000000000, 3));
        Assert.assertEquals(0, solution(2000000000-1, 2000000000, 3));
        Assert.assertEquals(1, solution(2000000000-2, 2000000000, 3));

    }
}
