package com.jsf2184;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapTests {
    @Test
    public void testListProcess() {
        HashMap<String, List<Integer>> sut = new HashMap<>();

    }
    public void addIt(HashMap<String, List<Integer>> sut, String key, int i) {
        List<Integer> list = sut.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(i);
    }
}
