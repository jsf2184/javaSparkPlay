package com.jsf2184.utility;

import com.jsf2184.SpliteratorTests;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTests {
    private static final Logger _log = Logger.getLogger(PriorityQueueTests.class);

    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Integer> sut = new PriorityQueue<>();
        int[] shuffle = Utility.shuffle(10);
        _log.info(String.format("input: %s", Arrays.toString(shuffle)));
        Arrays.stream(shuffle).boxed().forEach(sut::add);
        while (!sut.isEmpty()) {
            Integer v = sut.remove();
            _log.info("" + v);
        }
    }
    @Test
    public void testReversePriorityQueue() {
        PriorityQueue<Integer> sut = new PriorityQueue<>((x,y) -> x-y);
        int[] shuffle = Utility.shuffle(10);
        _log.info(String.format("input: %s", Arrays.toString(shuffle)));
        Arrays.stream(shuffle).boxed().forEach(sut::add);
        while (!sut.isEmpty()) {
            Integer v = sut.remove();
            _log.info("" + v);
        }
    }

    @Test
    public void testMedianArray() {
        MedianArray sut = new MedianArray();

        sut.add(6);
        Assert.assertNull(sut.biggestSmall());
        Assert.assertEquals((Integer) 6, sut.smallestBig());

        sut.add(8);
        Assert.assertEquals((Integer) 6, sut.biggestSmall());
        Assert.assertEquals((Integer) 8, sut.smallestBig());

        sut.add(9);
        Assert.assertEquals((Integer) 6, sut.biggestSmall());
        Assert.assertEquals((Integer) 8, sut.smallestBig());

        sut.add(10);
        Assert.assertEquals((Integer) 8, sut.biggestSmall());
        Assert.assertEquals((Integer) 9, sut.smallestBig());

    }
    public static class MedianArray {
        ArrayList<Integer> _list;
        PriorityQueue<Integer> _increasing;
        PriorityQueue<Integer> _decreasing;

        public MedianArray() {
            _list = new ArrayList<>();
            _increasing = new PriorityQueue<>();
            _decreasing = new PriorityQueue<>((x,y) -> y-x);
        }

        public Integer biggestSmall() {

            return  _decreasing.peek();
        }
        public Integer smallestBig() {
            return  _increasing.peek();
        }

        public void add(int v) {
            _list.add(v);
            if (_increasing.size() == 0 && _decreasing.size() == 0) {
                _increasing.add(v);
                return;
            }
            if (v < _increasing.peek()) {
                _decreasing.add(v);
            } else {
                _increasing.add(v);
            }

            PriorityQueue<Integer> longer;
            PriorityQueue<Integer> shorter;

            int sizeDiff = _increasing.size() - _decreasing.size();
            if (Math.abs(sizeDiff) <= 1) {
                return;
            }
            if (sizeDiff > 0) {
                longer = _increasing;
                shorter = _decreasing;
            } else {
                longer = _decreasing;
                shorter = _increasing;
            }
            Integer moveVal = longer.remove();
            shorter.add(moveVal);
        }
    }


}

