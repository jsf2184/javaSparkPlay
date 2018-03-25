package com.jsf2184;

import com.jsf2184.utility.LoggerUtility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class LongestNonNegSlice {

    private static final Logger _log = Logger.getLogger(LongestNonNegSlice.class);

    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    private static int[] generateArray(int len) {
        int[] res = new int[len];
        Random r = new Random();
        for (int i=0; i< len; i++) {
            int val = r.nextInt(3) - 1;
            res[i] = val;
        }
        return res;
    }

    @Test
    public void testGenerate() {
        int[] array = generateArray(10);
        _log.info(Arrays.toString(array));
    }


    @Test
    public void testOnePassWhereNoSequenceExists() {
        int array[] = {-1, -1, -1};
        int res = Worker.onePass(0, array);
        Assert.assertEquals(0, res);
        res = Worker.onePass(1, array);
        Assert.assertEquals(0, res);
        res = Worker.onePass(2, array);
        Assert.assertEquals(0, res);
        res = Worker.onePass(3, array);
        Assert.assertEquals(0, res);
    }

    @Test
    public void testOnePassAtDifferentStartingPoints() {
        int[] array = {-1, 0, -1, 0, 1};

        int res = Worker.onePass(0, array);
        Assert.assertEquals(0, res);

        res = Worker.onePass(1, array);
        Assert.assertEquals(res, 4);  // 0, -1, 0, 1

        res = Worker.onePass(2, array);
        Assert.assertEquals(res, 3);  // -1, 0, 1

        res = Worker.onePass(3, array);
        Assert.assertEquals(res, 2);  // 0, 1

        res = Worker.onePass(4, array);
        Assert.assertEquals(res, 1);  // 1

        array =  new int[]{0, -1, 0, -1, 1};

        res = Worker.onePass(0, array);
        Assert.assertEquals(res,  1);   // 0

        res = Worker.onePass(1, array);
        Assert.assertEquals(0, res);                  // never recovers

        res = Worker.onePass(2, array);
        Assert.assertEquals(res,  3);   // 0, -1, 1

        res = Worker.onePass(3, array);
        Assert.assertEquals(res,  2);   // -1, 1

        res = Worker.onePass(4, array);
        Assert.assertEquals(res,  1);    // 1
    }

    @Test
    public void testRun() {
        int[] array = {-1, 0, -1, 0, 1};
        runBoth(array);
        int res = Worker.run(array);
        Assert.assertEquals(4, res);

        array =  new int[]{0, -1, 0, -1, 1};
        runBoth(array);
        res = Worker.run(array);
        Assert.assertEquals(3, res);
    }

    @Test
    public void test100Times() {
        for (int i=0; i<1; i++) {
            int[] A = generateArray(100000);
            Worker2.run(A);
        }
    }

    public static void runBoth(int A[]) {
        int res1 = Worker.run(A);
        int res2 = Worker2.run(A);
        Assert.assertEquals(res1, res2);
    }


    public static class Worker2 {

        public static int run(int[] A) {
            HashMap<Integer, Integer> sumIndex = new HashMap<>();
            int maxslice = 0;
            int sum = 0;

            for (int i=0; i<A.length; i++) {
                sum += A[i];
                if (sum >= 0) {
                    maxslice = i+1;
                } else if (sumIndex.get(sum) != null) {
                    int spanLen = i - sumIndex.get(sum);
                    maxslice = Math.max(maxslice, spanLen);
                } else {
                    sumIndex.put(sum, i);
                }
            }
            return maxslice;
        }
    }


    public static class Worker {
        int[] _array;

        public Worker(int[] array) {
            _array = array;
        }

        public static  int run(int[] A) {
            int longest = 0;
            for (int start=0; start< A.length; start++) {
                int res = onePass(start, A);
                if (res > longest) {
                    longest = res;
                }
                int maxPossibleInNextAttempt = A.length - start - 1;
                if (maxPossibleInNextAttempt <= longest) {
                    break;
                }
            }
            return longest;
        }

        public static int onePass(int start, int[] array) {
            int runningTotal = 0;
            Integer lastPosIdx = null;
            for (int i=start; i<array.length; i++) {
                runningTotal += array[i];
                if (runningTotal >= 0) {
                    lastPosIdx = i;
                }
            }
            return lastPosIdx == null? 0 :  lastPosIdx - start + 1;
        }
    }
}
