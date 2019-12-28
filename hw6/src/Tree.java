import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
  * An interface for a tree where nodes can have an arbitrary number of children
  */
public interface Tree<E> {

    // InOrder is supported only by Binary Trees
    enum TraverseOrder { PreOrder, PostOrder, LevelOrder, InOrder }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     * @return root Position of the tree (or null if tree is empty)
     */
    Position<E> root();

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p    A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    Position<E> parent(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns an iterable collection of the Positions representing p's children.
     *
     * @param p    A valid Position within the tree
     * @return collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    Collection<Position<E>> children(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns the number of children of Position p.
     *
     * @param p    A valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    int numChildren(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns true if Position p has one or more children.
     *
     * @param p    A valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    default boolean isInternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) > 0;
    }

    /**
     * Returns true if Position p does not have any children.
     *
     * @param p    A valid Position within the tree
     * @return true if p has zero children, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    default boolean isExternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) == 0;
    }

    /**
     * Returns true if Position p represents the root of the tree.
     *
     * @param p    A valid Position within the tree
     * @return true if p is the root of the tree, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    default boolean isRoot(Position<E> p) throws IllegalArgumentException {
        return root() == p;
    }

    /**
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    int size();

    /**
     * Tests whether the tree is empty.
     * @return true if the tree is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns an iterable collection of the positions of the tree.
     * @return collection of the tree's positions
     */
    default Collection<Position<E>> positions() {
        return preOrder();
    }

    /** Returns the number of levels separating Position p from the root.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    default int depth(Position<E> p) throws IllegalArgumentException {
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }

    /**
     * Returns the height of the subtree rooted at Position p.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    default int height(Position<E> p) throws IllegalArgumentException {
        int h = 0;
        for (Position<E> c : children(p))
            h = Math.max(h, 1 + height(c));
        return h;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal
     * @param p       Position serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p); // for preOrder, we add position p before exploring subtrees
        for (Position<E> c : children(p))
            preorderSubtree(c, snapshot);
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in preorder.
     * @return collection of the tree's positions in preorder
     */
    default Collection<Position<E>> preOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot); // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a postorder traversal
     * @param p       Position serving as the root of a subtree
     * @param snapshot  a collection to which results are appended
     */
    private void postorderSubtree(Position<E> p, Collection<Position<E>> snapshot) {
        for (Position<E> c : children(p))
            postorderSubtree(c, snapshot);
        snapshot.add(p); // for postOrder, we add position p after exploring subtrees
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in postorder.
     * @return collection of the tree's positions in postorder
     */
    default Collection<Position<E>> postOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot); // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Returns a collection of positions of a tree, reported in inOrder
     */
    default Collection<Position<E>> inOrder() {
       throw new IllegalArgumentException("inOrder traversal not supported");
    }

    /**
     * Returns a collection of positions of the tree in breadth-first order.
     * @return collection of the tree's positions in breadth-first order
     */
    default Collection<Position<E>> levelOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<E>> fringe = new LinkedBlockingDeque<>();
            fringe.add(root());
            while (!fringe.isEmpty()) {
                Position<E> p = fringe.remove();
                snapshot.add(p);
                for (Position<E> c : children(p))
                    fringe.add(c);
            }
        }
        return snapshot;
    }
}
