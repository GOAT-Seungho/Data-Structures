package cse2010.binary.search.tree;

import cse2010.binary.tree.Position;
import cse2010.binary.tree.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer, Integer> tree;

    @Before
    public void setup() throws Exception {
        makeTree(10, 5, 3, 4, 7, 6, 20, 30, 15, 25);
    }

    @Test
    public void test_search() throws Exception {
        assertEquals(10, (long) tree.get(10).key);
        assertEquals(5, (long) tree.get(5).key);
        assertEquals(20, (long) tree.get(20).key);
        assertEquals(7, (long) tree.get(7).key);
        assertEquals(15, (long) tree.get(15).key);
        assertEquals(30, (long) tree.get(30).key);
        assertEquals(null, tree.get(9));
    }

    @Test
    public void tree_with_root_only() throws Exception {
        makeTree(7);

        assertEquals(7, (long) tree.firstEntry().key);
        assertEquals(7, (long) tree.lastEntry().key);
        assertEquals(7, (long) tree.floorEntry(7).key);
        assertEquals(null, tree.higherEntry(7));
        assertEquals(7, (long) tree.ceilingEntry(7).key);
        assertEquals(null, tree.lowerEntry(7));
    }

    @Test
    public void test_lowerEntry_when_left_subtree_exits() throws Exception {
        assertEquals(7, (long) tree.lowerEntry(10).key);
        assertEquals(4, (long) tree.lowerEntry(5).key);
        assertEquals(6, (long) tree.lowerEntry(7).key);
        assertEquals(15, (long) tree.lowerEntry(20).key);
    }

    @Test
    public void test_lowerEntry_when_no_left_subtree_exits() throws Exception {
        assertEquals(null, tree.lowerEntry(3));
        assertEquals(10, (long) tree.lowerEntry(15).key);
        assertEquals(20, (long) tree.lowerEntry(25).key);
    }

    @Test
    public void test_higherEntry_when_right_subtree_exits() throws Exception {
        assertEquals(4, (long) tree.higherEntry(3).key);
        assertEquals(6, (long) tree.higherEntry(5).key);
        assertEquals(15, (long) tree.higherEntry(10).key);
        assertEquals(25, (long) tree.higherEntry(20).key);
    }

    @Test
    public void test_higherEntry_when_no_right_subtree_exits() throws Exception {
        assertEquals(5, (long) tree.higherEntry(4).key);
        assertEquals(10, (long) tree.higherEntry(7).key);
        assertEquals(20, (long) tree.higherEntry(15).key);
        assertEquals(30, (long) tree.higherEntry(25).key);
        assertEquals(null, tree.higherEntry(30));
    }

    private void makeTree(Integer... elements) {
        tree = new LinkedBinarySearchTree3<>();
        Arrays.asList(elements)
            .stream()
            .forEach(i -> tree.put(i, i));
    }

    private void makeTree2(List<Integer> elements) {
        tree = new LinkedBinarySearchTree3<>();
        elements.stream().forEach(i -> tree.put(i, i));
    }

    @Test
    public void basic_tests() throws Exception {

        List<Integer> as = Arrays.asList(6, 2, 1, 4, 3, 7, 5);
        makeTree2(as);

        List<Position<Entry<Integer, Integer>>> bs = tree.inOrder();

        List<Integer> xs = bs.stream()
            .map(p -> p.element().key)
            .collect(Collectors.toList());

        assertEquals(true,
            xs.equals(as.stream().sorted().collect(Collectors.toList())));
    }

    private boolean isEqual(List<Integer> as, List<Position<Entry<Integer, Integer>>> bs) {

        List<Integer> xs = bs.stream()
            .map(p -> p.element().key)
            .collect(Collectors.toList());

        return xs.equals(as.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void test_insertion() throws Exception {
        tree = new LinkedBinarySearchTree3<>();

        List<Integer> as = genList(10, 100);

        for (int i = 0; i < as.size(); i++) {
            tree.put(as.get(i), as.get(i));
            assertEquals(true, isEqual(as.subList(0,i+1), tree.inOrder()));
        }
    }

    @Test
    public void test_deletion() throws Exception {
        tree = new LinkedBinarySearchTree3<>();

        List<Integer> as = genList(10, 100);
        as.stream().forEach(i -> tree.put(i,i));

        for (int i = 0; i < as.size(); i++) {
            tree.remove(as.get(i));
            as.remove(i);
            assertEquals(true, isEqual(as.subList(0,as.size()), tree.inOrder()));
        }
    }

    private List<Integer> genList(int count, int limit) {
        Random rnd = new Random(System.currentTimeMillis());
        List<Integer> as = new ArrayList<>();
        int i = 0;

        while (i++ < count) {
            int p = rnd.nextInt(limit);
            while (as.contains(p)) p = rnd.nextInt(limit);
            as.add(p);
        }

        return as;
    }
}