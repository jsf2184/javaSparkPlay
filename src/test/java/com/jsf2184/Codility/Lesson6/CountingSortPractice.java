package com.jsf2184.Codility.Lesson6;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CountingSortPractice {

    public static final int[] testData1 = {3, 1, 2, 4, 3, 3, 0};
    public static final int[] testData2 = {3, 1, 4, 3, 3};


    @Test
    public void testCreateCounts() {
        int[] res = Worker.createCounts(testData1, 4);
        Assert.assertTrue(Arrays.equals(new int[] {1, 1, 1, 3, 1}, res));
    }

    @Test
    public void testWorkerWithTestData1() {
        Worker worker = new Worker(testData1, 4);
        int[] res = worker.solve();
        Assert.assertTrue(Arrays.equals(new int[] {0, 1, 2, 3, 3, 3, 4}, res));

    }

    @Test
    public void testWorkerWithTestData2() {
        Worker worker = new Worker(testData2, 4);
        int[] res = worker.solve();
        Assert.assertTrue(Arrays.equals(new int[] {1, 3, 3, 3, 4}, res));

    }

    public static class Worker {
        int[] _counts;
        int[] _input;

        public Worker(int[] input, int maxValue) {
            _input = input;
            _counts = createCounts(input, maxValue);
        }

        public static int[] createCounts(int[] input, int maxValue) {
            int[] res = new int[maxValue+1];
            IntStream.of(input).forEach(a -> res[a]++);
            return res;
        }

        public int[] solve() {
            int destIdx = 0;
            for (int v = 0; v< _counts.length; v++) {
                int cnt = _counts[v];
                for (int c=0; c<cnt; c++, destIdx++) {
                    _input[destIdx] = v;
                }
            }
            return _input;
        }
    }


}
