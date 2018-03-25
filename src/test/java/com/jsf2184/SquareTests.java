package com.jsf2184;

import com.jsf2184.utility.LoggerUtility;
import com.jsf2184.utility.Utility;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareTests {

    private static final Logger _log = Logger.getLogger(SquareTests.class);

    @BeforeClass
    public static void setup() {
        LoggerUtility.initRootLogger();
    }

    public static class Pair {
        int _x;
        int _y;

        public Pair(int x, int y) {
            _x = x;
            _y = y;
        }

        public int getX() {
            return _x;
        }

        public int getY() {
            return _y;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", _x, _y);
        }
    }

    @Test
    public void testRotate() {
        int[][] array = createArray();
        Utility.logArray(array);
        _log.info("");
        rotate(array);
        Utility.logArray(array);

    }


    public void rotate(int arr[][]) {
        int n = arr.length;

        for (int i=0; i<n-1; i++) {
//        for (int i=1; i<2; i++) {
            int tmp = arr[0][i];               // top     -> temp
            arr[0][i] = arr[n-1-i][0];         // left    -> top
            arr[n-1-i][0]    = arr[n-1][n-1-i];   // bottom  -> left
            arr[n-1][n-1-i]  = arr[i][n-1];       // right   -> bottom;
            arr[i][n-1]      = tmp;               // temp    -> right
        }

    }

    public int[][] createArray() {
        int arr[][] = new int[4][];
        arr[0] = new int[]{0, 1, 2, 3};
        arr[1] = new int[]{4, 5, 6, 7};
        arr[2] = new int[]{8, 9, 10, 11};
        arr[3] = new int[]{12, 13, 14, 15};
        return arr;
    }
}