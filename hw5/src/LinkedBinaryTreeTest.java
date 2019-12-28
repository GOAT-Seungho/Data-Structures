
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedBinaryTreeTest {

    private LinkedBinaryTree tree1;
    private LinkedBinaryTree tree2;

    @BeforeEach
    void setUp() {
        tree1 = new LinkedBinaryTree();
        Position<Integer> root1 = tree1.addRoot(4);

        Position<Integer> l1_1 = tree1.addLeft(root1, 2);
        Position<Integer> l2_1 = tree1.addLeft(l1_1, 1);
        Position<Integer> l2_2 = tree1.addRight(l1_1, 3);

        Position<Integer> l1_2 = tree1.addRight(root1, 6);
        Position<Integer> l2_3 = tree1.addLeft(l1_2, 5);
        Position<Integer> l2_4 = tree1.addRight(l1_2, 7);

        tree2 = new LinkedBinaryTree();
        Position<Integer> root2 = tree2.addRoot(7);

        Position<Integer> t1_1 = tree2.addLeft(root2, 3);
        Position<Integer> t2_1 = tree2.addLeft(t1_1, 1);

        Position<Integer> t1_2 = tree2.addRight(root2, 5);
        Position<Integer> t2_2 = tree2.addLeft(t1_2, 2);
    }

    @Test
    void should_create_empty_tree_with_default_preorder() {
        BinaryTree tree = new LinkedBinaryTree();

        assertEquals(0, tree.size());
        assertEquals(LinkedBinaryTree.TraverseOrder.PreOrder,
                ((LinkedBinaryTree) tree).getTraverseOrder());
    }

    @Test
    void should_add_new_node_to_empty_tree_as_root() {
        LinkedBinaryTree tree = new LinkedBinaryTree();
        tree.addRoot(7);

        assertEquals(1, tree.size());
        assertEquals(7, tree.root().getElement());
    }

    @Test
    void should_add_left_child_to_a_node_with_no_left_child() {
        LinkedBinaryTree tree = new LinkedBinaryTree();
        Position<Integer> p = tree.addRoot(5);
        tree.addLeft(p, 1);

        assertEquals(2, tree.size());
        assertEquals(1, tree.root.getLeft().getElement());
    }

    @Test
    void should_add_right_child_to_a_node_with_no_right_child() {
        LinkedBinaryTree tree = new LinkedBinaryTree();
        Position<Integer> p = tree.addRoot(5);
        tree.addRight(p, 9);

        assertEquals(2, tree.size());
        assertEquals(9, tree.root.getRight().getElement());
    }

    @Test
    void should_traverse_inOrder() {

        assertEquals(7, tree1.size());

        checkIt(tree1.inOrder(), Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    }

    private void checkIt(Iterable<Position<Integer>> iterable, List<Integer> expected) {
        List<Integer> nodes = new ArrayList<>();
        iterable.forEach(i -> nodes.add(i.getElement()));

        assertEquals(expected, nodes);
    }

    @Test
    void should_traverse_preOrder() {

        checkIt(tree1.preOrder(), Arrays.asList(4, 2, 1, 3, 6, 5, 7));
    }

    @Test
    void should_traverse_postOrder() {

        checkIt(tree1.postOrder(), Arrays.asList(1, 3, 2, 5, 7, 6, 4));
    }

    @Test
    void should_traverse_levelOrder() {

        checkIt(tree1.levelOrder(), Arrays.asList(4, 2, 6, 1, 3, 5, 7));
    }

    @Test
    void should_height_be_the_same_as_the_maximum_depth_of_nodes_in_a_tree() {

        int height = tree1.height(tree1.root());
        int depth = getDepth(tree1);

        assertEquals(2, height);
        assertEquals(height, depth);

        int height2 = tree2.height(tree2.root());
        int depth2 = getDepth(tree2);

        assertEquals(2, height2);
        assertEquals(height2, depth2);
    }

    private int getDepth(final LinkedBinaryTree tree) {
        Iterator<Position> iter = tree.positions().iterator();
        int depth = 0;
        while (iter.hasNext()) {
            depth = Math.max(depth, tree.depth(iter.next()));
        }
        return depth;
    }

    @Test
    void should_attach_two_subtrees_to_external_node() {
        LinkedBinaryTree tree = new LinkedBinaryTree();
        Position<Integer> root = tree.addRoot(333);

        tree.attach(root, tree1, tree2);

        assertEquals(3, tree.height(tree.root()));
        assertEquals(13, tree.size()); // old tree1.size() + old tree2.size() + 1

        // attached trees can no longer be a standalone tree
        assertEquals(0, tree1.size());
        assertEquals(0, tree2.size());
    }

}
