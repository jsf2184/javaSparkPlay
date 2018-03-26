package com.jsf2184.utility;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class QuickSort {

    private static final Logger _log = Logger.getLogger(QuickSort.class);

    public static int  sort(int[] array) {
        return sort(array, 0, array.length-1);
    }
    public static int sort(int[] array, int lo, int length)
    {

        int res = 0;
        if (length <= 1) {
            return res;
        }
        _log.info("Sorting");
        res++;
        int divIdx = QuickSort.partition(array, lo, length);
        res += sort(array, divIdx+1, lo + length - divIdx - 1);
        res += sort(array, lo,  divIdx-lo );
        return res;

    }

    // Here we are working with a particular segment of the array beginning at 'start' with length: 'length'.
    // We want to zero in on the last element of this segment and re-position it so that all elements to its left
    // are smaller than it, and all elements to its right are larger than it. We call this value the pivot.
    // The method returns the new position of the pivot. Note that after the method has returned, the values
    // in the two halves partitioned by the pivot are not sorted. However, the left ones are all less than the
    // pivot and the right ones are all greater.
    //
    public static int partition(int[] array, int start, int length) {
        int pivotIdx = start+length-1;
        int pivot = array[pivotIdx];
        int numLess = start-1;
        for (int j=start; j< pivotIdx; j++) {
            if (array[j] < pivot) {
                Utility.swap(array, j, ++numLess);

            }
        }
        Utility.swap(array, ++numLess, pivotIdx);
        _log.info(Arrays.toString(array));
        return numLess;
    }
}
