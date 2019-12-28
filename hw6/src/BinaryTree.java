import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface BinaryTree<E> extends Tree<E> {

    /** Returns the Position of p's left child (or null if no child exists). */
    Position<E> left(Position<E> p) throws IllegalArgumentException;

    /** Returns the Position of p's right child (or null if no child exists). */
    Position<E> right(Position<E> p) throws IllegalArgumentException;

    /** Returns the Position of p's sibling (or null if no sibling exists). */
    default Position<E> sibling(Position<E> p) {
        Position<E> parent = parent(p);
        if (parent == null) return null;
        if (p == left(parent))
            return right(parent);
        else
            return left(parent);
    }

    /** Returns the number of children of Position p. */
    @Override default int numChildren(Position<E> p) {
        int count=0;
        if (left(p) != null)
            count++;
        if (right(p) != null)
            count++;
        return count;
    }

    /** Returns a collection of the Positions representing p's children. */
    @Override default Collection<Position<E>> children(Position<E> p) {
        List<Position<E>> snapshot = new ArrayList<>(2);
        if (left(p) != null)
            snapshot.add(left(p));
        if (right(p) != null)
            snapshot.add(right(p));
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given snapshot.
     */
    private void inorderSubtree(Position<E> p, Collection<Position<E>> snapshot) {
        if (left(p) != null)
            inorderSubtree(left(p), snapshot);
        snapshot.add(p);
        if (right(p) != null)
            inorderSubtree(right(p), snapshot);
    }

    /**
     * Returns a collection of positions of a tree, reported in inOrder
     */
    @Override default Collection<Position<E>> inOrder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot); // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Check whether the position p is the left child.
     * @return true if left child, false otherwise
     */
    boolean isLeftChild(Position<E> p);

    /**
     * Check whether the position p is the right child.
     * @return true if the right child, false otherwise
     */
    boolean isRightChild(Position<E> p);

}
