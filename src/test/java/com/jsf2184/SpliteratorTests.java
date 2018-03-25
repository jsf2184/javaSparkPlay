package com.jsf2184;

import com.jsf2184.utility.LoggerUtility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SpliteratorTests {

    private static final Logger _log = Logger.getLogger(SpliteratorTests.class);
    ConcurrentMap<Integer, Integer> _concurrentMap;
    ArrayList<Integer> _inputs;
    ArrayList<Spliterator<Integer>> _spliterators;


    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    @Before
    public void testSetup() {
        _concurrentMap = new ConcurrentHashMap<>();
        _inputs = new ArrayList<>();
        for (int i=0; i<131072; i++) {
            _inputs.add(i);
        }
        _spliterators = new ArrayList<>();
        Spliterator<Integer> spliterator = _inputs.spliterator();
        makeSpliterators(8, spliterator);

    }

    public void makeSpliterators(int depth, Spliterator<Integer> src) {
        if (depth == 0) {
            _spliterators.add(src);
            return;
        }
        Spliterator<Integer> copy = src.trySplit();
        makeSpliterators(depth-1, src);
        makeSpliterators(depth-1, copy);
    }

    @Test
    public void test512SpliteratorsSequentially() {
        _log.info(String.format("There are %d spliterators", _spliterators.size()));
        _spliterators.forEach(s -> _log.info(String.format("Spliterator has %d elements", s.getExactSizeIfKnown())));

        AtomicInteger spidx = new AtomicInteger();
        _spliterators.forEach(sp ->
        {
            spidx.incrementAndGet();
            sp.forEachRemaining(i->_concurrentMap.put(i, spidx.intValue() ));
        });

        validateMap();
    }

    @Test
    public void test512SpliteratorsParallel() throws InterruptedException {
        _log.info(String.format("There are %d spliterators", _spliterators.size()));
        AtomicInteger i= new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(_spliterators.size());
        _spliterators.stream().map(s -> new Worker(i.getAndIncrement(), s, latch)).forEach(Thread::start);
        latch.await();
        validateMap();
    }

    public void validateMap() {
        HashMap<Integer, Integer> spliteratorCounts = new HashMap<>();

        // verify that every input is in the spliterator map and count the
        // number of results attributed to each spliterator.
        //
        _inputs.forEach(i -> {
            Integer splitId = _concurrentMap.get(i);
            Assert.assertNotNull(splitId);
            spliteratorCounts.merge(splitId, 1, (o, n) -> o+n);
        });
        Integer expectedSize = _inputs.size() / _spliterators.size();
        spliteratorCounts.values().forEach(v -> Assert.assertEquals(expectedSize, v));
    }

    public class Worker extends Thread {
        int _id;
        Spliterator<Integer> _spliterator;
        CountDownLatch _latch;

        public Worker(int id, Spliterator<Integer> spliterator, CountDownLatch latch) {
            _id = id;
            _spliterator = spliterator;
            _latch = latch;
        }

        @Override
        public void run() {
            _spliterator.forEachRemaining(i -> _concurrentMap.put(i, _id));
            _latch.countDown();
        }
    }



}
