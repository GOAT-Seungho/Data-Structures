package cse2010.binary.tree;

/**
 * Each entry represents a "key-value" pair.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Entry<K extends Comparable<K>,V> {
    public K key;
    public V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
//        return "Entry(key=" + key + ")";
        return String.valueOf(key);
    }
}
