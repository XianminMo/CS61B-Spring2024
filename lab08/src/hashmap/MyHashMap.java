package hashmap;

import org.checkerframework.checker.units.qual.C;

import java.util.*;


/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Xianmin Mo
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /* Default value */
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int capacity;
    private int numOfElements;
    private double loadFactor;
    private Set<K> keyset;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    @SuppressWarnings("unchecked") // It is safe
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        this.capacity = initialCapacity;
        this.numOfElements = 0;
        this.loadFactor = loadFactor;
        this.keyset = new HashSet<>();
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }


    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    /* Insert or update a key-value pair */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        numOfElements += 1;
        keyset.add(key);

        // check load factor and resize if necessary
        if (numOfElements > loadFactor * capacity) {
            resize();
        }

    }

    /* Resize and rehash the table */
    private void resize() {
        int newCapacity = capacity * 2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        // Rehash all existing entries
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int newIndex = Math.floorMod(node.key.hashCode(), newCapacity);
                newBuckets[newIndex].add(node);
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return numOfElements;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i].clear();
        }

        numOfElements = 0;
        keyset.clear();
    }

    @Override
    public Set<K> keySet() {
        return keyset;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        Iterator<Node> iterator = bucket.iterator();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                V value = node.value;
                iterator.remove();
                numOfElements -= 1;
                keyset.remove(key);
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keyset.iterator();
    }
}
