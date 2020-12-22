package bearmaps.proj2d;

import bearmaps.proj2ab.DoubleMapPQ;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet {
    private Node root;
    private DoubleMapPQ<Character> pq;
    private class Node {
        private char c;
        private boolean isKey;
        private HashMap<Character, Node> map;

        public Node() {
            map = new HashMap<>();
        }
        public Node(char c, boolean b) {
            this.c = c;
            isKey = b;
            map = new HashMap<>();
        }
    }
    public MyTrieSet() {
        root = new Node();
    }
    public void add(String str) {
        if (str == null || str.length() < 1) {
            return;
        }
        Node current = root;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!current.map.containsKey(c)) {
                current.map.put(c, new Node(c, false));
            }
            current = current.map.get(c);
        }
        current.isKey = true;
    }
    public List<String> keysWithPrefix(String prefix) {
        List<String> x = new LinkedList<>();
        Node current = root;

        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

        }
        return x;
    }
}