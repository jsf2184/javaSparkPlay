package com.jsf2184.utility;

import com.jsf2184.StackTests;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuickSortTests {
    private static final Logger _log = Logger.getLogger(QuickSortTests.class);


    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }


    @Test
    public void testPartition() {
        int[] array = Utility.shuffle(10);
        int divIdx = QuickSort.partition(array, 0, array.length);
        _log.info(Arrays.toString(array));
        _log.info(String.format("divIdx = %d, divValue=%d", divIdx, array[divIdx]));
    }



    @Test
    public void testSort() {
        int[] array =  Utility.shuffle(128);
        int res = QuickSort.sort(array, 0, array.length);
        _log.info(String.format("res = %d: array = %s", res, Arrays.toString(array)));
    }

}
