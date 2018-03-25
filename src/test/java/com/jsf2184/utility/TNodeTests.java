package com.jsf2184.utility;

import com.jsf2184.collections.TNode;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TNodeTests {
    private static final Logger _log = Logger.getLogger(TNodeTests.class);


    @BeforeClass
    public static void setup()
    {
        LoggerUtility.initRootLogger();
    }

    @Test
    public void testBreadthTreeBuild() {
        TNode tree = TNode.buildBreadthTree(4);
        Assert.assertNotNull(tree);
        TNode.breadthTravers(tree, (v) -> _log.info("" + v));

    }

    @Test
    public void testDepthTreeBuild() {
        TNode tree = TNode.buildDepthTree(4);
        Assert.assertNotNull(tree);
        TNode.traverseDepthTree(tree, (v) -> _log.info("" + v));

    }
    @Test
    public void testDepthTreeBuild2() {
        TNode tree = TNode.buildDepthTree2(4);
        Assert.assertNotNull(tree);
        TNode.traverseDepthTree(tree, (v) -> _log.info("" + v));

    }

}
