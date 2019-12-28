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

    @Override public boolean equals(final Object obj) {
        if (obj == null)
            return false;

        Entry<K,V> other = (Entry<K,V>) obj;

        if (key.equals(other.key) && value.equals(other.value))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Entry(key=" + key + ", value = " + value + ")";
    }
}
