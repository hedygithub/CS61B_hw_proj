import javax.lang.model.element.UnknownElementException;
import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    public int initialSize = 16;
    double loadFactor = 0.75;
    int resizeMultiply = 2;

    ArrayList buckets;
    public int size;
    public int totalKeyCount = 0;
    int[] bucketsKeyCount;

    public MyHashMap() {
        buckets = new ArrayList(initialSize);
        bucketsKeyCount = new int[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets.add(new KeyValueMap<K, V>());
        }
        size = initialSize;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = new ArrayList(initialSize);
        bucketsKeyCount = new int[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets.add(new KeyValueMap<K, V>());
        }
        size = initialSize;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        buckets = new ArrayList(initialSize);
        bucketsKeyCount = new int[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets.add(new KeyValueMap<K, V>());
        }
        size = initialSize;
    }

    private class KeyValueMap<K, V> {
        K key;
        V value;
        KeyValueMap<K, V> next;

        public KeyValueMap() {
        }

        public KeyValueMap(K key, V value) {
            this.key = key;
            this.value = value;
        }

        //replace if it had it
        public void add(K key, V value) {
            if (this.key == null) {
                this.key = key;
                this.value = value;
            } else if(this.key.equals(key)) {
                this.value = value;
            } else if(next == null) {
                next = new KeyValueMap(key, value);
            } else {
                this.next.add(key, value);
            }
        }

        public K getKey(int index) {
            if (index < 0 || key == null) {
                throw new RuntimeException("Incorrect index");
            } else if (index == 0) {
                return key;
            } else if(next != null){
                return next.getKey(index - 1);
            } else {
                throw new RuntimeException("Incorrect index");
            }
        }

        public boolean containKey(K key) {
            if (this.key != null && this.key.equals(key)) {
                return true;
            } else if (next == null) {
                return false;
            } else {
                return next.containKey(key);
            }
        }

        public V getKeyValue(K key) {
            if (this.key != null && this.key.equals(key)) {
                return value;
            } else if (next == null) {
                return null;
            } else {
                return next.getKeyValue(key);
            }
        }
    }

    @Override
    /**
     * Removes all of the mappings from this map.
     */
    public void clear(){
        buckets = new ArrayList(size);
        totalKeyCount = 0;
        bucketsKeyCount = new int[size];
        for (int i = 0; i < size; i++) {
            buckets.add(new KeyValueMap<K, V>());
        }
    }

    @Override
    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    public boolean containsKey(K key) {
        int bucketNumber = (key.hashCode() & 0x7FFFFFFF) % size;
        KeyValueMap bucketMap = (KeyValueMap) buckets.get(bucketNumber);
        return bucketMap.containKey(key);
    }

    @Override
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        int bucketNumber = (key.hashCode() & 0x7FFFFFFF) % size;
        KeyValueMap bucketMap = (KeyValueMap) buckets.get(bucketNumber);
        return (V) bucketMap.getKeyValue(key);
    }

    @Override
    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return totalKeyCount;
    }

    @Override
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        if(totalKeyCount * 1.0 / size > loadFactor) {
            MyHashMap newMyHashMap = new MyHashMap(size * resizeMultiply);
            for (K oldKey : this) {
                newMyHashMap.put(oldKey, get(oldKey));
            }
            size *= resizeMultiply;
            buckets = newMyHashMap.buckets;
            totalKeyCount = newMyHashMap.totalKeyCount;
            bucketsKeyCount = newMyHashMap.bucketsKeyCount;
            put(key, value);
        } else {
            int bucketNumber = (key.hashCode() & 0x7FFFFFFF) % size;
            KeyValueMap bucketMap = (KeyValueMap) buckets.get(bucketNumber);
            if(bucketMap.containKey(key)) {
                bucketMap.add(key, value);
            } else {
                totalKeyCount += 1;
                bucketsKeyCount[bucketNumber] += 1;
                bucketMap.add(key, value);
            }
        }
    }

    @Override
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set keySet = new HashSet<K>(totalKeyCount);
        for(K key : this) {
            keySet.add(key);
        }
        return keySet;
    }

    @Override
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }


    @Override
    /**
     * Iteration
     * important for resize
     */
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        int bucketNum = 0;
        int index = -1;
        int keyCount = 0;

        @Override
        public boolean hasNext() {
            if (keyCount == totalKeyCount) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public K next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            } else {
                if(index + 1 < bucketsKeyCount[bucketNum]) {
                    index += 1;
                } else if(bucketsKeyCount[bucketNum + 1] > 0) {
                    bucketNum += 1;
                    index = 0;
                } else {
                    bucketNum += 1;
                    index = -1;
                    return next();
                }
                KeyValueMap bucket = (KeyValueMap) buckets.get(bucketNum);
                keyCount += 1;
                return (K) bucket.getKey(index);
            }
        }
    }
}