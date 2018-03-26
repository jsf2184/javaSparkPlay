package com.jsf2184.Codility.PracticeTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SmallestPossibleInteger {

    @Test
    public void test() {
        int res = mySolution(new int[]{1, 2, 3});
        Assert.assertEquals(4, res);
    }

    @Test
    public void testOther() {
        int[] arr = {1, 4, 6, -7, -2, 9, 5, 2, 3};
        int res;
//        res = solution(arr);
//        Assert.assertEquals(7, res);
        res = mySolution(arr);
        Assert.assertEquals(7, res);

    }

    public int mySolution(int[] A) {
        boolean[] flags = new boolean[A.length + 1];
        for (int a : A) {
            if (a >= 1 && a < flags.length) {
                flags[a] = true;
            }
        }
        for (int i=1; i<flags.length; i++) {
            if (!flags[i]) {
                return i;
            }
        }
        return flags.length;
    }


    public int solution(int[] A) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int a: A) {
            if (a > 0) {
                map.put(a, true);
            }
        }
        for (int i=1; i<=A.length; i++) {
            if (map.get(i) == null) {
                return i;
            }
        }
        return A.length + 1;
    }


}
