package com.jsf2184.Codility.Test;

import org.junit.Assert;
import org.junit.Test;

public class Problem3 {

    // true samples
    String[] sample1 = {"A2Le", "2pl1"};
    String[] sample2 = {"a10", "10a"};
    String[] sample3 = {"a10", "abbbbbbbbba"};

    // false samples
    String[] sample4 = {"ba1", "1Ad"};
    String[] sample5 = {"3x2x", "8"};

    public boolean solution(String S, String T) {
        // write your code in Java SE 8
        return true;
    }

    public static String expand(String s) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        Token token = null;
        while ((token = getNextToken(s, idx)) != null) {
            idx += token.writeToStringBuilder(sb);
        }
        return sb.toString();
    }

    public static Token getNextToken(String s, int startIdx) {
        int len = s.length();
        if (startIdx >= len) {
            return null;
        }
        StringBuilder numberBuilder = new StringBuilder();

        for (int i=startIdx; i<len; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                numberBuilder.append(c);
            } else {
                if (numberBuilder.length() == 0) {
                    return new CharToken(c);
                }
                return new NumToken(numberBuilder);
            }
        }
        if (numberBuilder.length() > 0) {
            return new NumToken(numberBuilder);
        }
        return null;
    }

    public static boolean equalTokens(Token t1, Token t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 != null && t2 != null) {
            if (t1.getClass() != t2.getClass()) {
                return false;
            }
            if (t1 instanceof  NumToken) {
                NumToken numToken1 = (NumToken) t1;
                NumToken numToken2 = (NumToken) t2;
                boolean res = numToken1.val == numToken2.val;
                return res;
            }
            CharToken charToken1 = (CharToken) t1;
            CharToken charToken2 = (CharToken) t2;
            boolean res = charToken1.val == charToken2.val;
            return res;
        }
        return false;

    }

    public static interface Token {
        // writes to a StringBuilder and returns the number of input
        // characters consumed in the building of this token.
        //
        public int writeToStringBuilder(StringBuilder sb);
    }

    public static class CharToken implements  Token{
        char val;

        public CharToken(char val) {
            this.val = val;
        }
        @Override
        public int writeToStringBuilder(StringBuilder sb) {
            sb.append(val);
            return 1;
        }
    }

    public static class NumToken implements  Token{
        int val;

        public NumToken(StringBuilder sb) {
            this(Integer.parseInt(sb.toString()));
        }

        public NumToken(int val) {
            this.val = val;
        }

        @Override
        public int writeToStringBuilder(StringBuilder sb) {
            for (int i=0; i<val; i++) {
                sb.append('*');
            }
            // return the number of digits of the integer.
            int res = Integer.toString(val).length();
            return res;
        }
    }

    @Test
    public void testEqualTokens() {
        Assert.assertTrue(equalTokens(null, null));

        Assert.assertTrue(equalTokens(null, null));


    }




    @Test
    public void testNumToken() {
        validateToken(1, new NumToken(3), "***");
        validateToken(2, new NumToken(12), "************");
        validateToken(1, new NumToken(1), "*");
    }

    @Test
    public void testCharToken() {
        validateToken(1, new CharToken('a'), "a");
        validateToken(1, new CharToken('X'), "X");
    }


    public void validateToken(int expectedConsumed, Token token, String expectedOutput) {
        StringBuilder sb = new StringBuilder();
        int consumed = token.writeToStringBuilder(sb);
        Assert.assertEquals(expectedConsumed, consumed);
        Assert.assertEquals(expectedOutput, sb.toString());
    }


    @Test
    public void sampleTest() {
        Assert.assertTrue(solution(sample1[0], sample1[1]));
        Assert.assertTrue(solution(sample2[0], sample2[1]));
        Assert.assertTrue(solution(sample3[0], sample3[1]));

        Assert.assertFalse(solution(sample4[0], sample4[1]));
        Assert.assertFalse(solution(sample5[0], sample5[1]));


    }

}
