package com.jsf2184;

import com.jsf2184.collections.Stack;
import org.junit.Assert;
import org.junit.Test;

public class StackTests {

    @Test
    public void testEmptyStack() {
        Stack<Integer> sut = new Stack<Integer>();
        Assert.assertNull(sut.pop());
    }

    @Test
    public void testScenario() {
        Stack<Integer> sut = new Stack<Integer>();
        for (int i=0; i<5; i++) {
            sut.push(i);
        }
        for (Integer i=4; i>=0; i--) {
            Assert.assertEquals(i, sut.pop());
        }
        Assert.assertNull(sut.pop());
    }
}
