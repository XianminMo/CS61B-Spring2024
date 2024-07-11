import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K>{

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;
    private Set<K> keySet;

    public BSTMap() {
        this.root = null;
        this.size = 0;
        this.keySet = new TreeSet<>();
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    // Helper method to insert a key-value pair
    private Node put(Node node, K key, V value) {
        if (node == null) {
            size += 1;
            keySet.add(key);
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    // Helper method to get a node with a given key
    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNode(node.left, key);
        } else if (cmp > 0 ) {
            return getNode(node.right, key);
        }else {
            return node;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
        keySet.clear();
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node == null) {
            return null;
        }
        root = removeNode(root, key);
        size -= 1;
        keySet.remove(key);
        return node.value;
    }

    // Helper method to remove a node with a given key
    private Node removeNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeNode(node.left, key);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node minNode = findMin(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = removeNode(node.right, key);
            }
        }
        return node;
    }

    // Helper method to find the minimum node in the right subtree
    private Node findMin(Node node) {
        while (node.left != null) {
            node= node.left;
        }
        return node;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}
