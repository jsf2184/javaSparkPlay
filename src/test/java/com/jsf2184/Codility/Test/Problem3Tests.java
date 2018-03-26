package com.jsf2184.Codility.Test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Problem3Tests {

    // true samples
    String[] sample1 = {"A2Le", "2pL1"};
    String[] sample2 = {"a10", "10a"};
    String[] sample3 = {"a10", "abbbbbbbbba"};

    // false samples
    String[] sample4 = {"ba1", "1Ad"};
    String[] sample5 = {"3x2x", "8"};

    public boolean solution(String S, String T) {
        String eS = expand(S);
        String eT = expand(T);
        boolean res = compareExpandedStrings(eS, eT);
        return res;
    }

    public static String expand(String s) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        Token token;
        while ((token = getNextToken(s, idx)) != null) {
            idx += token.writeToStringBuilder(sb);
        }
        return sb.toString();
    }

    public static boolean compareExpandedStrings(String e1, String e2) {
        if (e1.length() != e2.length()) {
            return false;
        }
        for (int i = 0; i<e1.length(); i++) {
            char c1 = e1.charAt(i);
            char c2 = e2.charAt(i);

            if (c1 == c2 || c1 == '*' || c2 == '*') {
                continue;
            }
            return false;
        }
        return true;
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


    public interface Token {
        // writes to a StringBuilder and returns the number of input
        // characters consumed in the building of this token.
        //
        int writeToStringBuilder(StringBuilder sb);
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
    public void sampleTest() {
        Assert.assertTrue(solution(sample1[0], sample1[1]));
        Assert.assertTrue(solution(sample2[0], sample2[1]));
        Assert.assertTrue(solution(sample3[0], sample3[1]));

        Assert.assertFalse(solution(sample4[0], sample4[1]));
        Assert.assertFalse(solution(sample5[0], sample5[1]));
    }

    @Test
    public void testCompareExpandedStrings() {
        validateExpandedStringCompare(true, "a", "a");
        validateExpandedStringCompare(true, "abc", "abc");
        validateExpandedStringCompare(true, "abc", "abc");
        validateExpandedStringCompare(true, "abc", "abc");
        
        validateExpandedStringCompare(true, "abc", "*bc");
        validateExpandedStringCompare(true, "abc", "a*c");
        validateExpandedStringCompare(true, "abc", "ab*");
        validateExpandedStringCompare(true, "abc", "**c");
        validateExpandedStringCompare(true, "abc", "a**");
        validateExpandedStringCompare(true, "abc", "***");

        validateExpandedStringCompare(false, "a", "x");
        validateExpandedStringCompare(false, "abc", "ab");
        validateExpandedStringCompare(false, "ab", "abc");
        validateExpandedStringCompare(false, "abc", "abx");
        validateExpandedStringCompare(false, "abx", "abc");

        validateExpandedStringCompare(false, "abc", "axc");
        validateExpandedStringCompare(false, "axc", "abc");

        validateExpandedStringCompare(false, "abc", "xbc");
        validateExpandedStringCompare(false, "xbc", "abc");

        validateExpandedStringCompare(false, "abc", "*bc*");
        validateExpandedStringCompare(false, "abc", "a*c*");
        validateExpandedStringCompare(false, "abc", "ab**");
        validateExpandedStringCompare(false, "abc", "**c*");
        validateExpandedStringCompare(false, "abc", "a***");
        validateExpandedStringCompare(false, "abc", "****");

        validateExpandedStringCompare(false, "abc*", "*bc");
        validateExpandedStringCompare(false, "abc*", "a*c");
        validateExpandedStringCompare(false, "abc*", "ab*");
        validateExpandedStringCompare(false, "abc*", "**c");
        validateExpandedStringCompare(false, "abc*", "a**");
        validateExpandedStringCompare(false, "abc*", "***");
    }

    @Test
    public void testExpands() {
        validateExpand("a", "a");
        validateExpand("abc", "abc");
        validateExpand("**abc*", "2abc1");
        validateExpand("***********a*c***", "11a1c3");
        validateExpand("a*c", "a1c");
    }


    @Test
    public void testTokenCreation() {
        validateCreateTokens(Arrays.asList(new CharToken('c')), "c");

        validateCreateTokens(Arrays.asList(new CharToken('a'),
                                           new CharToken('b'),
                                           new CharToken('c')),
                             "abc");

        validateCreateTokens(Arrays.asList(new NumToken(29)), "29");

        validateCreateTokens(Arrays.asList(new CharToken('a'),
                                           new NumToken(1),
                                           new CharToken('b'),
                                           new NumToken(11),
                                           new CharToken('c'),
                                           new NumToken(3)
                                           ),
                             "a1b11c3");

    }

    @Test
    public void testEqualTokens() {
        Assert.assertTrue(equalTokens(null, null));
        Assert.assertTrue(equalTokens(new NumToken(3), new NumToken(3)));
        Assert.assertTrue(equalTokens(new CharToken('a'), new CharToken('a')));

        Assert.assertFalse(equalTokens(new NumToken(3), null));
        Assert.assertFalse(equalTokens(new CharToken('a'), null));
        Assert.assertFalse(equalTokens(null, new NumToken(3)));
        Assert.assertFalse(equalTokens(null, new CharToken('a')));
        Assert.assertFalse(equalTokens(new NumToken(3), null));
        Assert.assertFalse(equalTokens(new CharToken('a'), new NumToken(4)));
        Assert.assertFalse(equalTokens(new NumToken(3), new NumToken(4)));
        Assert.assertFalse(equalTokens(new CharToken('a'), new CharToken('b')));
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

    public void validateCreateTokens(List<Token> expectedTokens, String s) {
        List<Token> actualTokens = new ArrayList<>();
        int idx = 0;
        Token token;
        StringBuilder dummy = new StringBuilder();
        while ((token = getNextToken(s, idx)) != null) {
            actualTokens.add(token);
            idx += token.writeToStringBuilder(dummy);
        }
        validateTokenLists(expectedTokens, actualTokens);
    }

    public void validateToken(int expectedConsumed, Token token, String expectedOutput) {
        StringBuilder sb = new StringBuilder();
        int consumed = token.writeToStringBuilder(sb);
        Assert.assertEquals(expectedConsumed, consumed);
        Assert.assertEquals(expectedOutput, sb.toString());
    }

    public static void validateTokenLists(List<Token> expectedTokens, List<Token> actualTokens) {
        Assert.assertEquals(expectedTokens.size(), actualTokens.size());
        for (int i=0; i< expectedTokens.size(); i++) {
            Assert.assertTrue(equalTokens(expectedTokens.get(i), actualTokens.get(i)));
        }
    }

    public static void validateExpand(String expected, String input) {
        String actual = expand(input);
        Assert.assertEquals(expected, actual);
    }

    public static void validateExpandedStringCompare(boolean expectEquals, String e1, String e2) {
        Assert.assertEquals(expectEquals, compareExpandedStrings(e1, e2));
    }



}
