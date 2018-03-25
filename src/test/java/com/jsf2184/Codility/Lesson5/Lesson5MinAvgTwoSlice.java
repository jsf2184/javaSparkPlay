package com.jsf2184.Codility.Lesson5;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class Lesson5MinAvgTwoSlice {
    //                                0  1  2  3  4  5  6
    public static int[] sampleData1 = {4, 2, 2, 4, 1, 5, 8};
    public static int[] sampleData2 = {2, -5, 9, -3, -20, 15, 3};

    public static int[] counter = {5, -6, 4, 5, -7, 8, 7, -2, 7, 6};
//    public static int[] sampleData3 = {4, 2, 2, 4, 1, 5, 8};
//    public static int[] sampleData4 = {4, 2, 2, 4, 1, 5, 8};

    public int solution(int[] A) {
        Worker worker = new Worker(A);
        Result doubleSlice = worker.getLowestDoubleSlice();
        Result tripleSlice = worker.getLowestTripleSlice();
        Result result = Result.best(doubleSlice, tripleSlice);
        return result._start;
    }

    public int[] genTestData(int len) {
        Random random = new Random();
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            int v = random.nextInt(20);
            if (v >= 10) {
                v = 9 - v;
            }
            res[i] = v;
        }
        return res;
    }

    @Test
    public void testBruteForceWithCounterExample() {
        int[] data = {1, 4, 2};
        lookForCounterExample(data);
    }


    @Test
    public void testBruteForceWithRandomData() {
        for (int i = 0; i < 10; i++) {
            int[] data = genTestData(100000);
            lookForCounterExample(data);
        }
    }


    public static void lookForCounterExample(int[] data) {
        Worker worker = new Worker(data);
        long[] pfxSum = worker.getPfxSum();

        DateTime bStart = DateTime.now();
        Result best = bruteForce(data.length, pfxSum);
        DateTime bEnd = DateTime.now();

        System.out.printf("ms = %d, best: %s\n", bEnd.getMillis() - bStart.getMillis(), best);

        DateTime shortStart = DateTime.now();
        Result doubleSlice = worker.getLowestDoubleSlice();
        Result tripleSlice = worker.getLowestTripleSlice();
        Result shortCut = Result.best(doubleSlice, tripleSlice);
        DateTime shortEnd = DateTime.now();


        System.out.printf("ms = %d, shortcut: %s\n",
                          shortEnd.getMillis() - shortStart.getMillis(),
                          shortCut);

        boolean bestsMatch = shortCut.equals(best);
        if (bestsMatch) {
            System.out.printf("BestsMatch");
        } else {
            System.out.printf("BestsMisMatch\n");
        }
        Assert.assertTrue(bestsMatch);
        System.out.println("");
    }



    @Test
    public void testBruteForce() {
        Result best = bruteForce(sampleData2);
        System.out.printf("best: %s", best);
    }

    public static Result bruteForce(int[] A) {
        long[] prefixSum = Worker.createPrefixSum(A);
        Result best = bruteForce(A.length, prefixSum);
        return best;
    }

    public static Result bruteForce(int inputLength, long[] prefixSum) {
        Result best = null;
        for (int len = 2; len <= inputLength; len++) {
            for (int start = 0; start < inputLength; start++) {
                if (start + len > inputLength) {
                    break;
                }
                int end = start + len - 1;
                Result result = Worker.create(start, end, prefixSum);
                best = Result.best(best, result);
                if (best == result) {
                    // We have a new best and it is a longer one.
                    System.out.printf("new best: %s\n", best);
                }
            }
        }
        return best;
    }


    public static class Result {
        int _start;
        int _end;
        double _avg;

        public Result(int start, int end, double avg) {
            _start = start;
            _end = end;
            _avg = avg;
        }

        public int getStart() {
            return _start;
        }

        public int getEnd() {
            return _end;
        }

        public double getAvg() {
            return _avg;
        }

        public int getLength() {
            int res = (_end - _start) + 1;
            return res;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            return _start == result._start &&
                    _end == result._end;
        }

        public static Result best(Result r1, Result r2) {
            if (r1 == null) {
                return r2;
            }
            if (r2 == null) {
                return r1;
            }
            if (r1._avg < r2._avg) {
                return r1;
            } else if (r2._avg < r1._avg) {
                return r2;
            }
            // the averages are equal.
            if (r1._start < r2._start) {
                return r1;
            }
            if (r2._start < r1._start) {
                return r2;
            }
            // the starts or equal, favor the shorter one.
            if (r1.getLength() < r2.getLength()) {
                return r1;
            }
            return r2;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "start=" + _start +
                    ", end=" + _end +
                    ", avg=" + _avg +
                    ", length=" + getLength() +
                    '}';
        }
    }

    public static class Worker {
        int[] _input;
        long[] _pfxSum;

        public Worker(int[] input) {
            _input = input;
            _pfxSum = createPrefixSum(input);
        }

        public long[] getPfxSum() {
            return _pfxSum;
        }

        public static long[] createPrefixSum(int[] input) {
            long[] res = new long[input.length + 1];
            res[0] = 0;

            int sum = 0;
            for (int src = 0, dest = 1; src < input.length; src++, dest++) {
                sum += input[src];
                res[dest] = sum;
            }
            return res;
        }

        public Result getLowestDoubleSlice() {
            Result best = null;
            for (int i=0; i < _input.length-1; i++) {

                Result result = create(i, i + 1);
                best = Result.best(best, result);
            }
            return best;
        }

        public Result getLowestTripleSlice() {
            Result best = null;
            for (int i=0; i < _input.length-2; i++) {
                Result result = create(i, i + 2);
                best = Result.best(best, result);
            }
            return best;
        }


        public static long countTotalInclusive(int left, int right, long[] pfxSum) {
            // important to know that x and y are the indices of the original A[] array,
            // not the prefixSum and the sum we get are treating x and y as inclusive indices.
            //
            long res = pfxSum[right + 1] - pfxSum[left];
            return res;
        }

        public Result create(int start, int end) {
            Result result = create(start, end, _pfxSum);
            return result;
        }

        public static Result create(int start, int end, long[] pfxSum) {
            double len = (end - start) + 1;
            long total = countTotalInclusive(start, end, pfxSum);
            double avg = total / len;
            Result res = new Result(start, end, avg);
            return res;
        }



    }
}
