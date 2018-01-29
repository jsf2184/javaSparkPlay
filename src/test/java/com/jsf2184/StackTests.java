package com.jsf2184;

import com.jsf2184.collections.Stack;
import com.jsf2184.utility.LoggerUtility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("SimplifyStreamApiCallChains")
public class StackTests {

    private static final Logger _log = Logger.getLogger(StackTests.class);


    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    @Test
    public void testEmptyStack() {
        Stack<Integer> sut = new Stack<>();
        Assert.assertNull(sut.pop());
    }

    @Test
    public void testScenario() {
        Stack<Integer> sut = new Stack<>();
        for (int i=0; i<5; i++) {
            sut.push(i);
        }

        _log.info("After All were pushed");
        sut.forEach(i -> _log.info(i.toString()));

        Iterator<Integer> iterator = sut.iterator();
        while (iterator.hasNext()) {
            Integer val = iterator.next();
            if (val.equals(4) || val.equals(0)) {
                iterator.remove();
                _log.info(String.format("removing %d", val));
            }
        }

        _log.info("After 4 and 0 were removed");
        sut.forEach(i -> _log.info(i.toString()));

        for (Integer i=3; i>=1; i--) {
            Assert.assertEquals(i, sut.pop());
        }
        Assert.assertNull(sut.pop());
    }

    @SuppressWarnings({"Convert2MethodRef", "Java8CollectionRemoveIf"})
    @Test
    public void testIteratorRemoveInAList() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer val = iterator.next();
            if (val.equals(2)) {
                iterator.remove();
            }
        }
        integers.stream().forEach(i -> _log.info(i));
    }
}
