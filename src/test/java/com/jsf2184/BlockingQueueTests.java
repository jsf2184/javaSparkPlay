package com.jsf2184;

import com.jsf2184.utility.LoggerUtility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueTests {

    ConcurrentHashMap<Integer, String> _results;
    ConcurrentHashMap<String, Integer> _rcvCount;
    BlockingQueue<Integer> _blockingQueue;
    CountDownLatch _latch;

    private static final Logger _log = Logger.getLogger(BlockingQueueTests.class);
    private static final int NumConsumers = 3;
    private static final int NumMesages = 999999;


    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }


    @Before
    public void testSetup() {
        _results = new ConcurrentHashMap<>();
        _rcvCount = new ConcurrentHashMap<>();
        _blockingQueue = new LinkedBlockingDeque<>();
        _latch = new CountDownLatch(NumConsumers);
    }

    @Test
    public void testProducerConsumer() throws InterruptedException {
        for (int i = 0; i < NumConsumers; i++) {
            Consumer consumer = new Consumer("C" + i);
            consumer.start();
        }


        for (int i = 0; i < NumMesages; i++) {
//            Thread.sleep(100);
            _blockingQueue.put(i);
        }
        for (int i = 0; i < NumConsumers; i++) {
            _blockingQueue.put(-1);
        }
        _latch.await();
        _log.info("Finished");
        for (int i = 0; i < NumMesages; i++) {
            Assert.assertNotNull(_results.get(i));
        }

        AtomicInteger total = new AtomicInteger();
        _rcvCount.forEach((k,v) -> {_log.info(String.format("Consumer %s received %d messages", k, v)); total.addAndGet(v); } );
        Assert.assertEquals(NumMesages, total.get());
    }
    public class Consumer extends Thread {

        public Consumer(String name) {
            super(name);
        }

        @Override
        public void run() {
            boolean stop = false;
            String name = this.getName();
            while (true) {
                try {
                    Integer val = _blockingQueue.take();
                    if (val < 0) {
                        break;
                    }
//                    _log.info(String.format("Consumer w/name=%s, received msg w/val = %d", name, val));
                    _results.put(val, name);
                    _rcvCount.merge(name, 1, (defValue, current) -> defValue + current);
                } catch (Exception e) {
                    break;
                }
            }
            _latch.countDown();
        }
    }


}
