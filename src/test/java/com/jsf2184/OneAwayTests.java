package com.jsf2184;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Test;

public class OneAwayTests {
    
    static boolean oneAway(String s1, String s2) {
        if (!validateLengths(s1, s2)) {
            return false;
        }
        int edits = 0;
        int i1 = 0;
        int i2 = 0;
        
        while (true) {

            Character c1 = i1 < s1.length()? s1.charAt(i1) : null;
            Character c2 = i2 < s2.length()? s2.charAt(i2) : null;

            if (c1 == null && c2 == null) {
                break;
            }

            if (charEquals(c1, c2)) {
                i1++;
                i2++;
                continue;
            }

            edits++;
            if (edits > 1) {
                return false;
            }
            if (s1.length() > s2.length()) {
                i1++;
                continue;
            }
            if (s2.length() > s1.length()) {
                i2++;
                continue;
            }
            i1++;
            i2++;
        }
        return true;
        
    }

    private static boolean charEquals(Character c1, Character c2) {
        if (c1 != null && c2 != null) {
            return c1.equals(c2);
        }
        return false;
    }


    private static boolean validateLengths(String s1, String s2) {
        return Math.abs(s1.length() - s2.length()) <= 1;
    }
    
    @Test
    public void runTests() {
        Assert.assertTrue(oneAway("", ""));
        Assert.assertTrue(oneAway("a", ""));
        Assert.assertTrue(oneAway("", "a"));
        Assert.assertTrue(oneAway("a", "b"));
        Assert.assertTrue(oneAway("pale", "ple"));
        Assert.assertTrue(oneAway("pales", "pale"));
        Assert.assertTrue(oneAway("pale", "bale"));
        Assert.assertFalse(oneAway("pale", "bake"));
    }
}
