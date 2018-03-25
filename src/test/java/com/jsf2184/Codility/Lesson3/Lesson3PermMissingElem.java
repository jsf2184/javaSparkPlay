package com.jsf2184.Codility.Lesson3;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.OptionalLong;
import java.util.stream.IntStream;

public class Lesson3PermMissingElem {

    public int solution(int[] A) {
        long expected = expectedSum(A.length+1);

        long sum = createSum1(A);
        long res = expected - sum;
        return (int) res;
    }

    long expectedSum(long x) {
        long res = (x * (x + 1)) / 2;
        return res;
    }

    long createSum1(int[] A) {
        long sum = 0;
        for (int a : A) {
            sum += a;
        }
        return sum;
    }

    // IMPORTANT NOTE:  Was very surprised to find that when I used this implementation, of
    // createSum(), codility said that my solution took too long.
    //
    long createSum2(int[] A) {
        OptionalLong sum = Arrays.stream(A).mapToLong(i -> i).reduce(Long::sum);
        if (sum.isPresent()) {
            return sum.getAsLong();
        }
        return 0;
    }

    long createSum3(int[] A) {
        long sum = IntStream.of(A).asLongStream().sum();
        return sum;
    }


    @Test
    public void testCreateSums() {
        int[] array = popWithGap(100000, 100001);
        long sum1 = createSum1(array);
        long sum2 = createSum2(array);
        Assert.assertEquals(sum1, sum2);
    }



    long printExpectedSumResults(int x) {
        long res = expectedSum(x);
        System.out.printf("x = %d, res = %d\n", x, res);
        return res;
    }

    @Test
    public void testZeroSize() {
        Assert.assertEquals(1, solution(new int[] {}));
    }

    @Test
    public void testSize1() {
        Assert.assertEquals(2, solution(new int[] {1}));
        Assert.assertEquals(1, solution(new int[] {2}));
    }

    @Test
    public void testMaxSize() {
        int[] array = popWithGap(100000, 100001);
        int res = solution(array);
        Assert.assertEquals(100001, res);

        array = popWithGap(100000, 10001);
        Assert.assertEquals(10001, solution(array));

        array = popWithGap(100000, 1);
        Assert.assertEquals(1, solution(array));

    }

    @Test
    public void compareTimeTest() {
        int[] array = popWithGap(100000, 100001);
        compareTimes(array, 100000);
    }

    public void compareTimes(int[] array, int repeats) {
        System.out.printf("On array with %d elements and %d tries\n", array.length, repeats);
        DateTime start1 = DateTime.now();
        for(int i=0; i< repeats; i++) {
            createSum1(array);
        }
        DateTime finish1 = DateTime.now();
        System.out.printf("createSum1 took %d ms\n", finish1.getMillis() - start1.getMillis());

        DateTime start2 = DateTime.now();
        for(int i=0; i< repeats; i++) {
            createSum2(array);
        }
        DateTime finish2 = DateTime.now();
        System.out.printf("createSum2 took %d ms\n", finish2.getMillis() - start2.getMillis());

        DateTime start3 = DateTime.now();
        for(int i=0; i< repeats; i++) {
            createSum3(array);
        }
        DateTime finish3 = DateTime.now();
        System.out.printf("createSum3 took %d ms\n", finish3.getMillis() - start3.getMillis());
    }

    @Test
    public void testPopWithGap() {
        int[] res = popWithGap(4, 3);
        System.out.printf("array: %s\n", Arrays.toString(res));
    }

    public int[] popWithGap(int N, int missingVal) {
        int[] res = new int[N];

        for (int v=1, idx=0; v<=N+1; v++) {
            if (v == missingVal) {
                continue;
            }
            res[idx++] = v;
        }
        return res;
    }


    @Test
    public void invokeExpectedSum() {
        Assert.assertEquals(1, printExpectedSumResults(1));
        Assert.assertEquals(3, printExpectedSumResults(2));
        Assert.assertEquals(6, printExpectedSumResults(3));
        Assert.assertEquals(10, printExpectedSumResults(4));
        Assert.assertEquals(15, printExpectedSumResults(5));
    }

}
