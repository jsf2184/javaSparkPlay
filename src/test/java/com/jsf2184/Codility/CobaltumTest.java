package com.jsf2184.Codility;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

public class CobaltumTest {

    @Test
    public void test0() {
        runIt(0, new int[] {1, 5, 6}, new int[] {-2, 0, 2});
    }

    @Test
    public void test1() {
        runIt(1, new int[] {5, 10, 7, 8}, new int[] {1, 6, 11, 12});
    }

    @Test
    public void test2() {
        runIt(1, new int[] {1, 2, 7, 8}, new int[] {5, 6, 3, 12});
    }

    @Test
    public void test3() {
        runIt(2,
              new int[] {5, 10, 7, 8,  101, 102, 107, 108},
              new int[] {1, 6, 11, 12, 105, 106, 103, 112});
    }

    @Test
    public void test4() {
        runIt(-1,
              new int[] {5, -3, 6, 4, 8},
              new int[] {2, 6, -5, 1, 0});
    }

    @Test
    public void test5() {
        runIt(2,
              new int[] {5, 3, 7, 7, 10},
              new int[] {1, 6, 6, 9, 9});
    }

    @Test
    public void testGenPathWithNoSwap_withNullPriorPath() {
        Assert.assertNull(genPathWithNoSwap(null, 10, 11));
    }

    @Test
    public void testGenPathWithSwap_withNullPriorPath() {
        Assert.assertNull(genPathWithSwap(null, 10, 11));
    }

    @Test
    public void testGenPathWithNoSwap_whichDeadEnds() {
        Assert.assertNull(genPathWithNoSwap(new Path(9, 5, 0), 9, 5));
        Assert.assertNull(genPathWithNoSwap(new Path(9, 5, 0), 9, 6));
        Assert.assertNull(genPathWithNoSwap(new Path(9, 5, 0), 10, 5));
        Assert.assertNull(genPathWithNoSwap(new Path(9, 5, 0), 6, 10));
    }

    @Test
    public void testGenPathWithSwap_whichDeadEnds() {
        Assert.assertNull(genPathWithSwap(new Path(9, 5, 0), 5, 9));
        Assert.assertNull(genPathWithSwap(new Path(9, 5, 0), 6, 9));
        Assert.assertNull(genPathWithSwap(new Path(9, 5, 0), 5, 10));
        Assert.assertNull(genPathWithSwap(new Path(9, 5, 0), 10, 6));
    }

    @Test
    public void testGenPathWithNoSwap_whichSucceeds() {
        Assert.assertEquals(new Path(10, 6, 3),
                            genPathWithNoSwap(new Path(9, 5, 3), 10, 6));
    }


    @Test
    public void testGenPathWithSwap_whichSucceeds() {
        Assert.assertEquals(new Path(10, 6, 4),
                            genPathWithSwap(new Path(9, 5, 3), 6, 10));
    }

    @Test
    public void testEvalPathWithNoSwap_whichReturnsNull() {
        Assert.assertNull(evalPathsWithNoSwap(
                null,
                5,
                6,
                null));

        Assert.assertNull(evalPathsWithNoSwap(
                new Path(9, 5, 0), 9, 5,
                null));
        Assert.assertNull(evalPathsWithNoSwap(
                new Path(9, 5, 0), 9, 6,
                null));
        Assert.assertNull(evalPathsWithNoSwap(
                new Path(9, 5, 0), 10, 5,
                null));
        Assert.assertNull(evalPathsWithNoSwap(
                new Path(9, 5, 0), 6, 10,
                null));

    }

    @Test
    public void testEvalPathWithNoSwap_whichReturnsPossiblePathCuzItsOnlyChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(9, 5, 1);
        Assert.assertSame(possiblePath, evalPathsWithNoSwap(priorPath, 6, 11, possiblePath));
    }

    @Test
    public void testEvalPathWithNoSwap_whichReturnsPossiblePathCuzItsBetterChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(5, 10, 4);
        Assert.assertSame(possiblePath, evalPathsWithNoSwap(priorPath, 6, 11, possiblePath));
    }

    @Test
    public void testEvalPathWithNoSwap_whichReturnsGeneratedPathCuzItsBetterChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(5, 10, 2);
        Assert.assertEquals(new Path(6, 11, 2), evalPathsWithNoSwap(priorPath, 6, 11, possiblePath));
    }

    @Test
    public void testEvalPathWithSwap_whichReturnsNull() {
        Assert.assertNull(evalPathsWithSwap(
                null,
                5,
                6,
                null));

        Assert.assertNull(evalPathsWithSwap(
                new Path(9, 5, 0), 5, 9,
                null));
        Assert.assertNull(evalPathsWithSwap(
                new Path(9, 5, 0), 6, 9,
                null));
        Assert.assertNull(evalPathsWithSwap(
                new Path(9, 5, 0), 5, 10,
                null));
        Assert.assertNull(evalPathsWithSwap(
                new Path(9, 5, 0), 10, 6,
                null));

    }

    @Test
    public void testEvalPathWithSwap_whichReturnsPossiblePathCuzItsOnlyChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(5, 9, 1);
        Assert.assertSame(possiblePath, evalPathsWithSwap(priorPath, 6, 11, possiblePath));
    }

    @Test
    public void testEvalPathWithSwap_whichReturnsPossiblePathCuzItsBetterChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(5, 9, 3);
        Assert.assertSame(possiblePath, evalPathsWithSwap(priorPath, 11, 6, possiblePath));
    }

    @Test
    public void testEvalPathWithSwap_whichReturnsGeneratedPathCuzItsBetterChoice() {
        Path possiblePath = new Path(6, 11, 3);
        Path priorPath = new Path(5, 9, 1);
        Assert.assertEquals(new Path(6, 11, 2), evalPathsWithSwap(priorPath, 11, 6, possiblePath));
    }


    public void runIt(int expectedMoves, int[] A, int[] B)
    {
        System.out.printf("A: %s\n", Arrays.toString(A));
        System.out.printf("B: %s\n", Arrays.toString(B));
        int actualMoves = doIt(A, B);
        System.out.printf("Expected moves = %d: Actual moves = %d\n",
                          expectedMoves,
                          actualMoves);


        Assert.assertEquals(expectedMoves, actualMoves);
    }

    public static class Path {
        int _a;
        int _b;
        int _moves;

        public Path(int a, int b, int moves) {
            _a = a;
            _b = b;
            _moves = moves;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "_a=" + _a +
                    ", _b=" + _b +
                    ", _moves=" + _moves +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return _a == path._a &&
                    _b == path._b &&
                    _moves == path._moves;
        }

    }

    public static int doIt(int[] A, int[] B) {

        int len = A.length;

        if (len == 0) {
            return 0;
        }

        int a = A[0];
        int b = B[0];

        // Right off the bat, we have 2 possibilities: with a swap and without.
        Path priorPath1 = new Path(a, b, 0);
        Path priorPath2 = new Path(b, a, 1);

        for (int i=1; i<len; i++) {
            a = A[i];
            b = B[i];

            // At the end of each iteration, we can have at most 2 current paths:
            //   - one where the A path ends with an 'a' element and the B path ends with a  'b' element.
            //   - one where the A path ends with a  'b' element and the B path ends with an 'a' element.
            // Our goal in each iteration is to come up with these 2 paths from our prior 2 paths each in the
            // cheapest way possible.

            // Using priorPath1, try generating a new path without a swap
            Path possiblePath1 = genPathWithNoSwap(priorPath1, a, b);
            // Using priorPath1, try generating a new path with a swap.
            Path possiblePath2 = genPathWithSwap(priorPath1, a, b);

            // Now, we're going to see if we can do a better job deriving these 2 new alternatives
            // if we had used the alternate way (i.e. deriving them from priorPath 2) of getting to
            // these 2 new paths. Note 'better' means less moves.
            //
            priorPath1 = evalPathsWithNoSwap(priorPath2, a, b, possiblePath1);
            priorPath2 = evalPathsWithSwap(priorPath2, a, b, possiblePath2);

            // keep going unless we hit a dead end.
            if (priorPath1 == null && priorPath2 == null) {
                return -1;
            }
        }

        int path1Moves = priorPath1 == null ? len+1 : priorPath1._moves;
        int path2Moves = priorPath2 == null ? len+1 : priorPath2._moves;

        int res = path1Moves <= path2Moves ? path1Moves : path2Moves;
        return res;
    }

    private static Path genPathWithNoSwap(Path priorPath, int a, int b) {
        if (priorPath == null) {
            return  null;
        }
        if (a > priorPath._a && b > priorPath._b) {
            return new Path(a, b, priorPath._moves);
        }
        return null;
    }

    private static Path genPathWithSwap(Path priorPath, int a, int b) {
        if (priorPath == null) {
            return null;
        }
        if (b > priorPath._a && a > priorPath._b) {
            return new Path(b, a, priorPath._moves + 1);
        }
        return null;
    }

    private static Path evalPathsWithNoSwap(Path priorPath,
                                            int a,
                                            int b,
                                            Path possiblePath)
    {
        if (priorPath == null) {
            // we don't have any thing prior to base it on so the best we can do is return
            // the possiblePath that was passed in to us.
            //
            return possiblePath;
        }

        if (a > priorPath._a && b > priorPath._b) {
            if (possiblePath == null || priorPath._moves < possiblePath._moves) {
                return  new Path(a, b, priorPath._moves);
            }
        }
        return possiblePath;
    }

    private static Path evalPathsWithSwap(Path priorPath, int a, int b, Path possiblePath) {
        if (priorPath == null) {
            // we don't have any thing prior to base it on so the best we can do is return
            // the possiblePath that was passed in to us.
            //
            return possiblePath;
        }
        if (b > priorPath._a && a > priorPath._b) {
            if (possiblePath == null || priorPath._moves + 1 < possiblePath._moves) {
                return new Path(b, a, priorPath._moves + 1);
            }
        }
        return possiblePath;
    }



}
