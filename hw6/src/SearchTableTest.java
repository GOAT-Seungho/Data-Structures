import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SearchTableTest {

    private OrderedMap<Integer, Integer> table;

    private Integer inputs[] = { 1, 6, 3, 10, 7, 2, 4 };

    private Integer floorOutputs[] = { null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10, 10 };
    private Integer ceilingOutputs[] = { 1, 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null };
    private Integer lowerOuputs[] = { null, null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10 };
    private Integer higherOutputs[] = { 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null, null };

    @BeforeEach
    void setUp() {
        table = new SearchTable<>(10);

        Arrays.stream(inputs).forEach(k -> table.put(k, k));

    }

    @Test
    void should_insert_new_entries() {

        assertEquals(7, table.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 6, 7, 10)), table.keys());

        table.put(5,5);

        assertEquals(8, table.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 10)), table.keys());
    }

    @Test
    void should_throw_exception_if_table_is_full() {
        table = new SearchTable<>(10);

        assertThrows(IllegalStateException.class,
                () -> IntStream.rangeClosed(1, 11).forEach(k -> table.put(k, k)));
    }

    @Test
    void should_return_null_if_empty() {
        table = new SearchTable<>(10);

        assertEquals(0, table.size());
        assertEquals(null, table.firstEntry());
        assertEquals(null, table.lastEntry());
        assertEquals(null, table.floorEntry(42));
        assertEquals(null, table.ceilingEntry(42));
        assertEquals(null, table.lowerEntry(42));
        assertEquals(null, table.higherEntry(42));
    }

    @Test
    void should_return_first_entry() {
        assertEquals(new Entry<>(1, 1), table.firstEntry());
    }

    @Test
    void should_return_last_entry() {
        assertEquals(new Entry<>(10, 10), table.lastEntry());
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_floor_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(null, table.floorEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry(10, 10), table.floorEntry(key));
        else
            assertEquals(new Entry(floorOutputs[key], floorOutputs[key]), table.floorEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_ceiling_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry(1, 1), table.ceilingEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(null, table.ceilingEntry(key));
        else
            assertEquals(new Entry(ceilingOutputs[key], ceilingOutputs[key]), table.ceilingEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_lower_entry(int key) {
        if (key == Integer.MIN_VALUE || key == 1)
            assertEquals(null, table.lowerEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry(10, 10), table.lowerEntry(key));
        else
            assertEquals(new Entry(lowerOuputs[key], lowerOuputs[key]), table.lowerEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_higher_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry(1, 1), table.higherEntry(key));
        else if (key == Integer.MAX_VALUE || key == 10)
            assertEquals(null, table.higherEntry(key));
        else
            assertEquals(new Entry(higherOutputs[key], higherOutputs[key]), table.higherEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_able_to_search(int key) {
        if (table.keys().contains(key)) {
            assertEquals(key, table.get(key).key);
        }
        else
            assertEquals(null, table.get(key));
    }

    @Test
    void should_remove_entry_if_matched_key_found() {

        table.remove(1);
        assertEquals(6, table.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7, 10)), table.keys());

        table.remove(10);
        assertEquals(5, table.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7)), table.keys());

    }

    @Test
    void should_not_remove_if_no_matched_key_found() {
        Integer oldValue = table.remove(4);
        assertEquals(4, oldValue);
        assertEquals(6, table.size());

        oldValue = table.remove(4);
        assertEquals(null, oldValue);
        assertEquals(6, table.size());
    }

    @Test
    void should_reflect_entry_removal_when_search() {
        Integer oldValue = table.remove(1);
        assertEquals(1, oldValue);
        assertEquals(table.lowerEntry(1), table.floorEntry(1));
        assertEquals(new Entry(2,2), table.ceilingEntry(1));
        assertEquals(new Entry(2,2), table.higherEntry(1));

        oldValue = table.remove(6);
        assertEquals(6, oldValue);
        assertEquals(new Entry(4,4), table.floorEntry(6));
        assertEquals(table.ceilingEntry(6), table.higherEntry(6));
    }
}
