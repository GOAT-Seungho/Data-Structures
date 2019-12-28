
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchTable<K extends Comparable<K>,V> implements OrderedMap<K, V> {

    private Entry<K,V> entries[];
    private int size;

    public SearchTable(int capacity) {
        entries = new Entry[capacity];
        size = 0;
    }

    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return size == entries.length; }

    @Override public Entry<K, V> firstEntry() {
        if (size == 0) { return null; }
        else {

        }
    }

    @Override public Entry<K, V> lastEntry() {

    }

    @Override public Entry<K, V> floorEntry(final K key) {

    }

    @Override public Entry<K, V> ceilingEntry(final K key) {

    }

    @Override public Entry<K, V> lowerEntry(final K key) {

    }

    @Override public Entry<K, V> higherEntry(final K key) {

    }

    @Override public Entry<K, V> get(final K key) {

    }

    /**
     * Associates the given value with the given key, returning any overridden value.
     * @param key search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null otherwise
     * @throws IllegalStateException if full
     */
    @Override public V put(final K key, final V value) {

        Entry<K, V> newEntry = new Entry<> (key, value);

        if (isFull()) { // 꽉 참
            throw new IllegalStateException();
        }

        else if (isEmpty()) { // 빔
            entries[0] = newEntry;
            size++;
            return null;
        }

        else if (entries[0].key.compareTo(key) > 0) { // 제일 작은 key 값보다 작은 경우

            for (int i = size-1 ; i >= 0 ; i--) { // index 밀어주기
                entries[i+1] = entries[i];
            }

            entries[0] = newEntry;
            size++;
            return null;
        }

        else if (entries[size].key.compareTo(key) < 0) { // 제일 큰 key 값보다 큰 경우
            entries[size+1] = newEntry;
            size++;
            return null;
        }

        else {

            Entry<K, V> oldEntry = new Entry<> (null, null);

            for (int i = 0 ; i < size ; i++) {

                if (entries[i].key.compareTo(key) == 0) { // key 값이 같은 경우
                    oldEntry = entries[i];
                    entries[i].key = key;
                }

                else if (entries[i].key.compareTo(key) < 0) {

                    if (entries[i+1].key.compareTo(key) > 0) {

                        for (int j = size - 1; j >= i; j--) {
                            entries[j + 1] = entries[j];
                        }

                        entries[i] = newEntry;
                        size++;
                    }

                }

            }

            return oldEntry.value;
        }

    }

    @Override public V remove(final K key) {

        for (int i = 0 ; i < size ; i++) {

            if (entries[i].key == key) {
                V oldValue = entries[i].value;

                for (int j = i ; j < size ; j++) {
                    entries[j] = entries[j+1];
                }

                size--;
                return oldValue;
            }
        }

        return null;
    }

    @Override public int size() {
        return size;
    }

    @Override public Set<K> keys() {
        return Arrays.stream(entries).limit(size).map(e -> e.key).collect(Collectors.toSet());
    }

    @Override public Collection<V> values() {
        return Arrays.stream(entries).limit(size).map(e -> e.value).collect(Collectors.toList());
    }

    @Override public Collection<Entry<K, V>> entries() {

    }

    @Override public String toString() {
        return Arrays.toString(entries);
    }

    // You can define private fields and/or methods below, if necessary ...

}
