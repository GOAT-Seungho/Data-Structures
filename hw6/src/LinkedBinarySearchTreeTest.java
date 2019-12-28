import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LinkedBinarySearchTreeTest {

    private Integer inputs[] = { 1, 6, 3, 10, 7, 2, 4 };

    private Integer floorOutputs[] = { null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10, 10 };
    private Integer ceilingOutputs[] = { 1, 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null };
    private Integer lowerOutputs[] = { null, null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10 };
    private Integer higherOutputs[] = { 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null, null };

    private OrderedMap<Integer,Integer> map;
    private OrderedMap<Integer,Integer> tree;

    @BeforeEach
    void setUp() {
        map = new LinkedBinarySearchTree<>();

        Arrays.stream(inputs).forEach(k -> map.put(k, k));

        makeTree(10, 5, 3, 4, 7, 6, 20, 30, 15, 25);
    }

    @Test
    void should_return_null_if_empty() {
        map = new LinkedBinarySearchTree<>();

        assertEquals(0, map.size());
        assertEquals(null, map.firstEntry());
        assertEquals(null, map.lastEntry());
        assertEquals(null, map.floorEntry(42));
        assertEquals(null, map.ceilingEntry(42));
        assertEquals(null, map.lowerEntry(42));
        assertEquals(null, map.higherEntry(42));
    }

    @Test
    void should_insert_new_entries() {

        assertEquals(7, map.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 6, 7, 10)), map.keys());

        map.put(5,5);

        assertEquals(8, map.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 10)), map.keys());
    }

    @Test
    void should_return_first_entry() {
        assertEquals(new Entry<>(1, 1), map.firstEntry());
    }

    @Test
    void should_return_last_entry() {
        assertEquals(new Entry<>(10, 10), map.lastEntry());
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_floor_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(null, map.floorEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry(10, 10), map.floorEntry(key));
        else
            assertEquals(new Entry(floorOutputs[key], floorOutputs[key]), map.floorEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_ceiling_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry(1, 1), map.ceilingEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(null, map.ceilingEntry(key));
        else
            assertEquals(new Entry(ceilingOutputs[key], ceilingOutputs[key]), map.ceilingEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_lower_entry(int key) {
        if (key == Integer.MIN_VALUE || key == 1)
            assertEquals(null, map.lowerEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry(10, 10), map.lowerEntry(key));
        else
            assertEquals(new Entry(lowerOutputs[key], lowerOutputs[key]), map.lowerEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_higher_entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry(1, 1), map.higherEntry(key));
        else if (key == Integer.MAX_VALUE || key == 10)
            assertEquals(null, map.higherEntry(key));
        else
            assertEquals(new Entry(higherOutputs[key], higherOutputs[key]), map.higherEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_able_to_search(int key) {
        if (map.keys().contains(key)) {
            assertEquals(key, map.get(key).key);
        }
        else
            assertEquals(null, map.get(key));
    }

    @Test
    void should_remove_entry_if_matched_key_found() {

        map.remove(1);
        assertEquals(6, map.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7, 10)), map.keys());

        map.remove(10);
        assertEquals(5, map.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7)), map.keys());

    }

    @Test
    void should_not_remove_if_no_matched_key_found() {
        Integer oldValue = map.remove(4);
        assertEquals(4, oldValue);
        assertEquals(6, map.size());

        oldValue = map.remove(4);
        assertEquals(null, oldValue);
        assertEquals(6, map.size());
    }


    @Test
    void should_reflect_entry_removal_when_search() {
        map.remove(1);
        assertEquals(map.lowerEntry(1), map.floorEntry(1));
        assertEquals(new Entry(2,2), map.ceilingEntry(1));
        assertEquals(new Entry(2,2), map.higherEntry(1));

        map.remove(6);
        assertEquals(new Entry(4,4), map.floorEntry(6));
        assertEquals(map.ceilingEntry(6), map.higherEntry(6));
    }

    @Test
    void tree_with_root_only() {
        makeTree(7);

        assertEquals(7, tree.firstEntry().key);
        assertEquals(7, tree.lastEntry().key);
        assertEquals(7, tree.floorEntry(7).key);
        assertEquals(null, tree.higherEntry(7));
        assertEquals(7, tree.ceilingEntry(7).key);
        assertEquals(null, tree.lowerEntry(7));
    }

    @Test
    void test_lowerEntry_when_left_subtree_exits() {
        assertEquals(7, tree.lowerEntry(10).key);
        assertEquals(4, tree.lowerEntry(5).key);
        assertEquals(6, tree.lowerEntry(7).key);
        assertEquals(15, tree.lowerEntry(20).key);
    }

    @Test
    void test_lowerEntry_when_no_left_subtree_exits() {
        assertEquals(null, tree.lowerEntry(3));
        assertEquals(10, tree.lowerEntry(15).key);
        assertEquals(20, tree.lowerEntry(25).key);
    }

    @Test
    void test_higherEntry_when_right_subtree_exits() {
        assertEquals(4,  tree.higherEntry(3).key);
        assertEquals(6,  tree.higherEntry(5).key);
        assertEquals(15,  tree.higherEntry(10).key);
        assertEquals(25,  tree.higherEntry(20).key);
    }

    @Test
    void test_higherEntry_when_no_right_subtree_exits() {
        assertEquals(5,  tree.higherEntry(4).key);
        assertEquals(10,  tree.higherEntry(7).key);
        assertEquals(20,  tree.higherEntry(15).key);
        assertEquals(30,  tree.higherEntry(25).key);
        assertEquals(null, tree.higherEntry(30));
    }

    private void makeTree(Integer... elements) {
        tree = new LinkedBinarySearchTree<>();
        Arrays.asList(elements)
              .stream()
              .forEach(i -> tree.put(i, i));
    }

    private void makeTree2(List<Integer> elements) {
        tree = new LinkedBinarySearchTree<>();
        elements.stream().forEach(i -> tree.put(i, i));
    }

    @Test
    void basic_tests() {

        List<Integer> as = Arrays.asList(6, 2, 1, 4, 3, 7, 5);
        makeTree2(as);

        assertEquals(tree.keys(), new HashSet<>(as));
    }

    @Test
    void test_insertion() {
        tree = new LinkedBinarySearchTree<>();

        Set<Integer> as = genList(10, 100);
        Set<Integer> bs = new HashSet<>();

        for (int i : as) {
            tree.put(i, i);
            bs.add(i);
            assertEquals(bs, tree.keys());
        }
    }

    @Test
    void test_deletion() {
        tree = new LinkedBinarySearchTree<>();

        Set<Integer> as = genList(10, 100);
        Set<Integer> bs = new HashSet<>();
        as.stream().forEach(i -> { tree.put(i,i); bs.add(i); });

        for (int i : as) {
            tree.remove(i);
            bs.remove(i);
            assertEquals(bs, tree.keys());
        }
    }

    private Set<Integer> genList(int count, int limit) {
        Random rnd = new Random(System.currentTimeMillis());
        Set<Integer> as = new HashSet<>();
        int i = 0;

        while (i++ < count) {
            int p = rnd.nextInt(limit);
            while (as.contains(p)) p = rnd.nextInt(limit);
            as.add(p);
        }

        return as;
    }
}