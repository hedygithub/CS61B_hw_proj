import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.lang.Comparable;

public class BSTMap<K extends Comparable<K> ,V> implements Map61B<K, V> {

    public K key;
    public V value;
    public BSTMap left;
    public BSTMap right;


    public BSTMap(){
        left = null;
        right = null;
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear(){
        key = null;
        value = null;
        left = null;
        right = null;
    }

    @Override
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        if(this.key == null) {
            return false;
        } else if(this.key.equals(key)){
            return true;
        } else if (left == null && right == null){
            return false;
        } else if(left != null && right == null) {
            return left.containsKey(key);
        } else if(left == null && right != null) {
            return right.containsKey(key);
        } else {
            return (left.containsKey(key) || right.containsKey(key));
        }
    }

    @Override
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (this.key == null) {
            return null;
        } else if (this.key.equals(key)) {
            return value;
        } else if (this.key.compareTo(key) > 0 && left == null) {
            return null;
        } else if (this.key.compareTo(key) < 0 && right == null) {
            return null;
        } else if (this.key.compareTo(key) > 0) {
            return (V) left.get(key);
        } else {
            return (V) right.get(key);
        }
    }

    @Override
    /* Returns the number of key-value mappings in this map. */
    public int size(){
        int size = 0;
        if (key != null){
            size += 1;
            if (left != null) {
                size += left.size();
            }
            if (right != null){
                size += right.size();
            }
        }
        return size;
    }

    @Override
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value){
        if (this.key == null) {
            this.key = key;
            this.value = value;
        } else if (this.key.compareTo(key) > 0 && left == null) {
            left = new BSTMap();
            left.key = key;
            left.value = value;
        } else if (this.key.compareTo(key) < 0 && right == null) {
            right = new BSTMap();
            right.key = key;
            right.value = value;
        } else if (this.key.compareTo(key) > 0) {
            left.put(key, value);
        } else if (this.key.compareTo(key) < 0) {
            right.put(key,value);
        } else if (this.key.equals(key)) {
            this.value = value;
        }
    }

    @Override
    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){
        if (key == null) {
            return null;
        } else {
            Set<K> set = new HashSet<K>(size());
            eachKey(set);
            return(set);
        }
    }

    private void eachKey(Set<K> set){
        if (left == null && right == null){
            set.add(key);
        } else if(left != null && right == null) {
            set.add(key);
            left.eachKey(set);
        } else if(left == null && right != null) {
            set.add(key);
            right.eachKey(set);
        } else {
            set.add(key);
            left.eachKey(set);
            right.eachKey(set);
        }
    }


    /* Prints out your BSTMap in order of increasing Key. */
    public void printInOrder(){
        if (left == null) {
            System.out.println(value);
            if(right != null) {
                right.printInOrder();   // 2) handel all leafs below the smallest one, /up, /up till all left in the root
            }
        }
        if(left != null) {
            left.printInOrder();        // 1) find the smallest one
            System.out.println(value);
            if(right != null) {
                right.printInOrder();   // 3) handle all leafs bigger than root.
            }

        }
    }

    @Override
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    @Override
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
