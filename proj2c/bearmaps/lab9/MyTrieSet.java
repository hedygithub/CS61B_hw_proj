package bearmaps.lab9;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {
    private class Node {
        public boolean endOfWord;
        public Map<Character, Node> children;

        public Node(){
            endOfWord = false;
            children = new HashMap<Character, Node>();
        }
    }

    public Node root;
    public MyTrieSet() {
        root = new Node();
    }

    @Override
    /** Clears all items out of Trie */
    public void clear() {
        root = new Node();
    }

    @Override
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if(key == null || key.length() < 1) {
            return false;
        }
        Node current = root;
        for(int i = 0; i < key.length(); i++) {
            if(!current.children.containsKey(key.charAt(i))){
                return false;
            } else {
                current = current.children.get(key.charAt(i));
            }
        }
        if (current.endOfWord) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    /** Inserts string KEY into Trie */
    public void add(String key) {
        if(key == null || key.length() < 1) {
            return;
        }
        Node current = root;
        for(int i = 0; i < key.length(); i++) {
            if(!current.children.containsKey(key.charAt(i))){
                current.children.put(key.charAt(i), new Node());
                current = current.children.get(key.charAt(i));
            } else {
                current = current.children.get(key.charAt(i));
            }
        }
        current.endOfWord = true;
    }

    @Override
    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        List<String> list = new LinkedList<String>();
        if(prefix == null || prefix.length() < 1) {
            return null;
        }
        Node current = root;
        for(int i = 0; i < prefix.length(); i++) {
            if(!current.children.containsKey(prefix.charAt(i))){
                return null;
            } else {
                current = current.children.get(prefix.charAt(i));
            }
        }
        if(current.endOfWord) {
            list.add(prefix);
        }
        keysWithPrefix(current, list, prefix);
        return list;
    }

    private void keysWithPrefix(Node current, List<String> list, String s){
        for(Character child : current.children.keySet()) {
            String newS = s + child;
            Node newCurrent = current.children.get(child);
            if(newCurrent.endOfWord) {
                list.add(newS);
            }
            if(!newCurrent.children.isEmpty()) {
                keysWithPrefix(newCurrent, list, newS);
            }
        }

    }

    @Override
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    }
}
