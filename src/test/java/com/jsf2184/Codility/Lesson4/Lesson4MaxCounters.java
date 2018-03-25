package com.jsf2184.Codility.Lesson4;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class Lesson4MaxCounters {


    public int[] solution(int N, int[] A) {
        int currMax = 0;
        int lastResetVal = 0;
        int lastResetIdx = -1;

        int[] counters = new int[N];
        int[] lastDirectChange = new int[N];

        for (int i=0; i<A.length; i++) {
            int a = A[i];

            if (a <= N) {
                int idx = a - 1;
                int idxChanged = lastDirectChange[idx];
                int priorValue = counters[idx];
                if (lastResetIdx > idxChanged) {
                    priorValue = lastResetVal;
                }
                int newVal = priorValue+1;
                if (newVal > currMax) {
                    currMax = newVal;
                }
                counters[idx] = newVal;
                lastDirectChange[idx] = i;

            } else {
                lastResetVal = currMax;
                lastResetIdx = i;
            }
        }

        for (int i=0; i<counters.length; i++) {
            int idxChanged = lastDirectChange[i];
            if (idxChanged < lastResetIdx) {
                counters[i] = lastResetVal;
            }
        }
        return counters;
    }

    public int[] bruteForceSolution(int N, int[] A) {
        int currMax = 0;
        int[] counters = new int[N];

        for (int a : A) {
            if (a > N) {
                for (int i = 0; i < N; i++) {
                    counters[i] = currMax;
                }
                continue;
            }
            int idx = a - 1;
            int newVal = counters[idx] + 1;
            counters[idx] = newVal;
            if (newVal > currMax) {
                currMax = newVal;
            }
        }
        return counters;
    }

    @Test
    public void testProvidedSampleData() {
        int[] res = solution(5, new int[]{3, 4, 4, 6, 1, 4, 4});
        Assert.assertTrue(Arrays.equals(new int[] {3, 2, 2, 4, 2}, res));
    }

    @Test
    public void testAllMaximums() {
        int[] res = solution(3, new int[]{4, 4, 4, 4});
        Assert.assertTrue(Arrays.equals(new int[] {0, 0, 0}, res));
    }

    @Test
    public void testLastIsAMaximum() {
        int[] res = solution(3, new int[]{3, 3, 3, 4});
        Assert.assertTrue(Arrays.equals(new int[] {3, 3, 3}, res));
    }

    @Test
    public void doRandomTests() {
        testRandom(4, 5000);
        testRandom(100, 25000);
    }

    public void testRandom(int numCounters, int resultSize) {
        int[] A = popArray(numCounters, resultSize);
        int[] oldRes = bruteForceSolution(numCounters, A);
        int[] newRes = solution(numCounters, A);
        Assert.assertTrue(Arrays.equals(oldRes, newRes));
    }

    @Test
    public void popArrayTest() {
        int[] res = popArray(4, 20);


        System.out.println(Arrays.toString(res));
    }


    public int[] popArray(int numCounters, int resultSize) {
        int[] res = new int[resultSize];
        Random random = new Random();
        for (int i=0; i<resultSize; i++) {
            res[i] = random.nextInt(numCounters+1) + 1;
        }
        return res;
    }

}