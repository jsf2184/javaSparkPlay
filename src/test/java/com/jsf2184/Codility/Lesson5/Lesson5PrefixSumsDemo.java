package com.jsf2184.Codility.Lesson5;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Lesson5PrefixSumsDemo {

    public static int[] createPrefixSum(int[] A) {
        int[] res = new int[A.length+1];
        res[0] = 0;

        int sum = 0;
        for (int src=0, dest=1; src <A.length; src++, dest++) {
            sum += A[src];
            res[dest] = sum;
        }
        return res;
    }

    public static int countTotalInclusive(int left, int right, int[] pfxSum) {
        // important to know that x and y are the indices of the original A[] array,
        // not the prefixSum and the sum we get are treating x and y as inclusive indices.
        //
        int res = pfxSum[right+1] - pfxSum[left];
        return res;
    }

    @Test
    public void testPrefixSumUse() {
                                              // 0  1  2  3  4
        int[] pfxSum = createPrefixSum(new int[]{1, 2, 3, 4, 5});
        System.out.println(Arrays.toString(pfxSum));
        Assert.assertTrue(Arrays.equals(new int[] {0, 1, 3, 6, 10, 15}, pfxSum));

        // now lets take arbitrary ranges and test them out.
        Assert.assertEquals(1,  countTotalInclusive(0, 0, pfxSum ));
        Assert.assertEquals(3,  countTotalInclusive(0, 1, pfxSum ));
        Assert.assertEquals(6,  countTotalInclusive(0, 2, pfxSum ));
        Assert.assertEquals(10, countTotalInclusive(0, 3, pfxSum ));
        Assert.assertEquals(15, countTotalInclusive(0, 4, pfxSum ));

        Assert.assertEquals(2,  countTotalInclusive(1, 1, pfxSum ));
        Assert.assertEquals(5,  countTotalInclusive(1, 2, pfxSum ));
        Assert.assertEquals(9,  countTotalInclusive(1, 3, pfxSum ));
        Assert.assertEquals(14, countTotalInclusive(1, 4, pfxSum ));

        Assert.assertEquals(3,  countTotalInclusive(2, 2, pfxSum ));
        Assert.assertEquals(7,  countTotalInclusive(2, 3, pfxSum ));
        Assert.assertEquals(12, countTotalInclusive(2, 4, pfxSum ));

        Assert.assertEquals(4,  countTotalInclusive(3, 3, pfxSum ));
        Assert.assertEquals(9,  countTotalInclusive(3, 4, pfxSum ));

        Assert.assertEquals(5, countTotalInclusive(4, 4, pfxSum ));
    }

    @Test
    public void pickShrooms() {
        Assert.assertEquals(25, optimizePickings(new int[] {2, 3, 7, 5, 1, 3, 9}, 4, 6));
    }

    int optimizePickings(int[] A, int k, int m) {
        int[] prefixSum = createPrefixSum(A);

        int lowestIndex = k - m;
        if (lowestIndex < 0) {lowestIndex = 0;}
        int res = 0;

        System.out.println("Try moves to the left first");
        for (int winLeft = lowestIndex; winLeft <= k; winLeft++) {
            int deltaRight = Math.max(0, m - (2* (k-winLeft)));
            int winRight = Math.min(k + deltaRight, A.length -1);
            int count = countTotalInclusive(winLeft, winRight, prefixSum);
            System.out.printf("winLeft = %d, winRight = %d, count = %d\n", winLeft, winRight, count);
            if (count > res) {
                res = count;
            }
        }

        System.out.println("\nTry moves to the right first");
        int highestIndex = Math.min(k+m, A.length-1);
        for (int winRight = highestIndex; winRight >= k; winRight-- ) {
            int deltaLeft = Math.max(0, m - (2 * (winRight - k)));
            int winLeft = Math.max(k - deltaLeft, 0);
            int count = countTotalInclusive(winLeft, winRight, prefixSum);
            System.out.printf("winLeft = %d, winRight = %d, count = %d\n", winLeft, winRight, count);
            if (count > res) {
                res = count;
            }
        }



        return res;
    }


}
