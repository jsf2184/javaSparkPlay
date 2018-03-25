package com.jsf2184.Codility.Lesson3;

import org.junit.Assert;
import org.junit.Test;

public class Lesson3FrogJump {

    public int solution2(int X, int Y, int D) {
        int distance = Y - X;
        int res = distance/D;
        if (distance % D > 0) {
            res++;
        }
        return res;
    }

    public int solution(int X, int Y, int D) {
        int distance = Y - X;
        int res = distance/D;
        if ((res * D) + X < Y) {
            res++;
        }
        return res;
    }

    @Test
    public void testDoesntDivideEvenly() {
        Assert.assertEquals(5, solution(1, 10, 2));
        Assert.assertEquals(33, solution(1, 99, 3));
    }

    @Test
    public void testDoesDivideEvenly() {
        Assert.assertEquals(5, solution(1, 11, 2));
        Assert.assertEquals(33, solution(1, 100, 3));
    }

    @Test
    public void testZeroDistance() {
        Assert.assertEquals(0, solution(10, 10, 5));
    }

    @Test
    public void testMaxDistance() {
        Assert.assertEquals(999999999, solution(1, 1000000000, 1));
        Assert.assertEquals(500000000, solution(1, 1000000000, 2));
    }

}
