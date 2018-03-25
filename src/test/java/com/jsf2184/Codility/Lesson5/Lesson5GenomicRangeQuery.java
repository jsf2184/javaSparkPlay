package com.jsf2184.Codility.Lesson5;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Lesson5GenomicRangeQuery {

    static final String sampleData = "CAGCCTA";
    int[] solution(String S, int[] P, int[] Q) {
        int[][] prefixSum = createPrefixSum(S);
        int[] res = new int[P.length];
        for (int i=0; i<P.length; i++) {
            int start = P[i];
            int end = Q[i];

            for (int r = 0; r<4; r++) {
                int occurrences = extractOccurrences(start, end, r, prefixSum);
                if (occurrences > 0) {
                    res[i] = r+1;
                    break;
                }
            }
        }
        return res;
    }

    public static int[][] createPrefixSum(String S) {

        int[] charMapToRow = new int[128];
        charMapToRow['A'] = 0;
        charMapToRow['C'] = 1;
        charMapToRow['G'] = 2;
        charMapToRow['T'] = 3;
        int[] sums = new int[4];

        int[][] res = new int[4][S.length() + 1];

        for (int src=0, dest=1; src <S.length(); src++, dest++) {
            char c = S.charAt(src);
            int rowIdx = charMapToRow[c];
            sums[rowIdx]++;
            for (int r =0; r<4; r++) {
                res[r][dest] = sums[r];
            }
        }
        return res;
    }

    int extractOccurrences(int start,
                          int end,
                          int row,
                          int[][] pfxSum)
    {
        int res = pfxSum[row][end+1] - pfxSum[row][start];
        return res;

    }

    @Test
    public void testWithSampleData() {
        int[] res = solution(sampleData, new int[]{2, 5, 0}, new int[]{4, 5, 6});
        Assert.assertTrue(Arrays.equals(new int[] {2, 4, 1}, res));
    }

    @Test
    public void testExtractOccurrencesForA() {
        int[][] prefixSum = createPrefixSum(sampleData);
        printArray(prefixSum);

        // Look for 'A's in length 1 segments
        Assert.assertEquals(0, extractOccurrences(0, 0, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 1, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 2, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 3, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 4, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(5, 5, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(6, 6, 0, prefixSum));

        // Look for 'A's in length 2 segments
        Assert.assertEquals(1, extractOccurrences(0, 1, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 2, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 3, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 4, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 5, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(5, 6, 0, prefixSum));

        // Look for 'A's in length 3 segments
        Assert.assertEquals(1, extractOccurrences(0, 2, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 3, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 4, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 5, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 6, 0, prefixSum));

        // Look for 'A's in length 4 segments
        Assert.assertEquals(1, extractOccurrences(0, 3, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 4, 0, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 5, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(3, 6, 0, prefixSum));

        // Look for 'A's in length 5 segments
        Assert.assertEquals(1, extractOccurrences(0, 4, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 5, 0, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 6, 0, prefixSum));

        // Look for 'A's in length 6 segments
        Assert.assertEquals(1, extractOccurrences(0, 5, 0, prefixSum));
        Assert.assertEquals(2, extractOccurrences(1, 6, 0, prefixSum));

        // Look for 'A's in length 7 segments
        Assert.assertEquals(2, extractOccurrences(0, 6, 0, prefixSum));

    }

    @Test
    public void testExtractOccurrencesForC() {
        int[][] prefixSum = createPrefixSum(sampleData);
        printArray(prefixSum);

        // Look for 'C's in length 1 segments
        Assert.assertEquals(1, extractOccurrences(0, 0, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 1, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 2, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(3, 3, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 4, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(5, 5, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(6, 6, 1, prefixSum));

        // Look for 'C's in length 2 segments
        Assert.assertEquals(1, extractOccurrences(0, 1, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 2, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 3, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(3, 4, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 5, 1, prefixSum));
        Assert.assertEquals(0, extractOccurrences(5, 6, 1, prefixSum));

        // Look for 'C's in length 3 segments
        Assert.assertEquals(1, extractOccurrences(0, 2, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 3, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(2, 4, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(3, 5, 1, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 6, 1, prefixSum));

        // Look for 'C's in length 4 segments
        Assert.assertEquals(2, extractOccurrences(0, 3, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(1, 4, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(2, 5, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(3, 6, 1, prefixSum));

        // Look for 'C's in length 5 segments
        Assert.assertEquals(3, extractOccurrences(0, 4, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(1, 5, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(2, 6, 1, prefixSum));

        // Look for 'C's in length 6 segments
        Assert.assertEquals(3, extractOccurrences(0, 5, 1, prefixSum));
        Assert.assertEquals(2, extractOccurrences(1, 6, 1, prefixSum));

        // Look for 'C's in length 7 segments
        Assert.assertEquals(3, extractOccurrences(0, 6, 1, prefixSum));

    }

    @Test
    public void testExtractOccurrencesForG() {
        int[][] prefixSum = createPrefixSum(sampleData);
        printArray(prefixSum);

        // Look for 'G's in length 1 segments
        Assert.assertEquals(0, extractOccurrences(0, 0, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 1, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 2, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 3, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 4, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(5, 5, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(6, 6, 2, prefixSum));

        // Look for 'G's in length 2 segments
        Assert.assertEquals(0, extractOccurrences(0, 1, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 2, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 3, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 4, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 5, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(5, 6, 2, prefixSum));

        // Look for 'G's in length 3 segments
        Assert.assertEquals(1, extractOccurrences(0, 2, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 3, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 4, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 5, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 6, 2, prefixSum));

        // Look for 'G's in length 4 segments
        Assert.assertEquals(1, extractOccurrences(0, 3, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 4, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 5, 2, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 6, 2, prefixSum));

        // Look for 'G's in length 5 segments
        Assert.assertEquals(1, extractOccurrences(0, 4, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 5, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 6, 2, prefixSum));

        // Look for 'G's in length 6 segments
        Assert.assertEquals(1, extractOccurrences(0, 5, 2, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 6, 2, prefixSum));

        // Look for 'G's in length 7 segments
        Assert.assertEquals(1, extractOccurrences(0, 6, 2, prefixSum));

    }


    @Test
    public void testExtractOccurrencesForT() {
        int[][] prefixSum = createPrefixSum(sampleData);
        printArray(prefixSum);

        // Look for 'T's in length 1 segments
        Assert.assertEquals(0, extractOccurrences(0, 0, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 1, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 2, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 3, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(4, 4, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(5, 5, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(6, 6, 3, prefixSum));

        // Look for 'T's in length 2 segments
        Assert.assertEquals(0, extractOccurrences(0, 1, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 2, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 3, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(3, 4, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 5, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(5, 6, 3, prefixSum));

        // Look for 'T's in length 3 segments
        Assert.assertEquals(0, extractOccurrences(0, 2, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 3, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(2, 4, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(3, 5, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(4, 6, 3, prefixSum));

        // Look for 'T's in length 4 segments
        Assert.assertEquals(0, extractOccurrences(0, 3, 3, prefixSum));
        Assert.assertEquals(0, extractOccurrences(1, 4, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 5, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(3, 6, 3, prefixSum));

        // Look for 'T's in length 5 segments
        Assert.assertEquals(0, extractOccurrences(0, 4, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 5, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(2, 6, 3, prefixSum));

        // Look for 'T's in length 6 segments
        Assert.assertEquals(1, extractOccurrences(0, 5, 3, prefixSum));
        Assert.assertEquals(1, extractOccurrences(1, 6, 3, prefixSum));

        // Look for 'T's in length 7 segments
        Assert.assertEquals(1, extractOccurrences(0, 6, 3, prefixSum));

    }

    @Test
    public void testCreatePrefixSum() {
        int[][] prefixSum = createPrefixSum(sampleData);
        printArray(prefixSum);
        int[][] expected = {
                {0, 0, 1, 1, 1, 1, 1, 2},
                {0, 1, 1, 1, 2, 3, 3, 3},
                {0, 0, 0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1}
        };
        compareMatrices(expected, prefixSum);
    }

    public static void printArray(int[][] array) {
        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);
    }

    public static void compareMatrices(int[][] array1, int[][] array2) {
        Assert.assertEquals(array1.length, array2.length);
        for (int r=0; r<array1.length; r++) {
            int[] r1 = array1[r];
            int[] r2 = array2[r];
            Assert.assertTrue(Arrays.equals(r1, r2));
        }
    }



}
