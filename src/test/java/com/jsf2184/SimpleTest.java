package com.jsf2184;

import com.jsf2184.utility.LoggerUtility;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

    private static final Logger _log = Logger.getLogger(SimpleTest.class);

    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    @Test
    public void testLog() {
        _log.info("Hello world");
    }
}
