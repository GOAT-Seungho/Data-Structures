package cse2010.binary.tree;

import java.util.List;

/**
 * The binary tree interface.
 *
 * @param <T> element type for the node
 */
public interface BinaryTree<T> {

    /**
     * Tests whether the tree is empty.
     * @return true if the tree is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the position of the root.
     * @return the position of the root, or null if the tree is empty
     */
    Position<T> root();

    /**
     * Returns the number of nodes in the tree.
     * @return the size of the tree
     */
    int size();

    /**
     * Check if the position has a left child.
     * @param p position
     * @return true if the position has a left child, false otherwise
     */
    default boolean hasLeft(Position<T> p) {
        return p.isInternal() && isInternal(p.left());
    }

    /**
     * Check if the position has a right child.
     * @param p position
     * @return true if the position has a right child, false otherwise
     */
    default boolean hasRight(Position<T> p) {
        return p.isInternal() && isInternal(p.right());
    }

    /**
     * Returns the number of children of the position p.
     * @param p position
     * @return the number of children
     */
    default int numChildren(Position<T> p) {
        if (isEmpty()) return 0;

        return p.numChildren();
    }

    /**
     * Check whether the position is the root of the tree.
     * @param p the position
     * @return true if the position is the root, false otherwise
     */
    boolean isRoot(Position<T> p);

    /**
     * Check whether the position is the internal node of the tree.
     * @param p the position
     * @return true if the position is the internal node, false otherwise
     */
    default boolean isInternal(Position<T> p) {
        return p.isInternal();
    }

    /**
     * Check whether the position is the external node of the tree.
     * @param p the position
     * @return true if the position is the external node, false otherwise
     */
    default boolean isExternal(Position<T> p) {
        return p.isExternal();
    }

    /**
     * Get the left child of the position.
     * @param p the position
     * @return the left child the position if exists, null otherwise
     */
    default Position<T> left(Position<T> p) {
        return p.left();
    }

    /**
     * Get the right child of the position.
     * @param p the position
     * @return the right child the position if exists, null otherwise
     */
    default Position<T> right(Position<T> p) {
        return p.right();
    }

    /**
     * Get the parent of the position.
     * @param p the position
     * @return the parent if exists, null otherwise
     */
    default Position<T> parent(Position<T> p) {
        return p.parent();
    }

    /**
     * Get the sibling of the position.
     * @param p the position
     * @return the sibling of the position if exists, null otherwise
     */
    Position<T> sibling(Position<T> p);

    /**
     * Get the children of the position.
     * @param p the position
     * @return the list of the children of the position, empty list if none
     */
    default List<Position<T>> children(Position<T> p) {
        return p.children();
    }

    /**
     * Get the depth of the tree.
     * @return the depth of the tree
     */
    default int depth() {
        return height();
    }

    /**
     * Get the depth of the position.
     * @param p the position
     * @return the depth of the position
     */
    default int depth(Position<T> p) {
        return p.depth();
    }

    /**
     * Get the height of the tree.
     * @return the height of the tree
     */
    default int height() {
        return isEmpty() ? 0 : root().height();
    }

    /**
     * Get the height of the position.
     * @param p the position
     * @return the height of the position
     */
    default int height(Position<T> p) {
        return p.height();
    }

    /**
     * Get the position of the left sibling of the position <tt>p</tt>.
     * @param p the position
     * @return position of the left sibling
     */
    default Position<T> leftSibling(Position<T> p) {
        if (isRoot(p)) throw new IllegalStateException("Root cannot have a sibling");
        return p.parent().left();
    }

    /**
     * Get the position of the right sibling of the position <tt>p</tt>.
     * @param p the position
     * @return position of the right sibling
     */
    default Position<T> rightSibling(Position<T> p) {
        if (isRoot(p)) throw new IllegalStateException("Root cannot have a sibling");
        return p.parent().right();
    }

    /**
     * Check whether the position <tt>p</tt> is the left child.
     * @param p the position
     * @return true if <tt>p</tt> is the position of the left child, false otherwise
     */
    default boolean isLeftChild(Position<T> p) {
        return p.isLeftChild();
    }

    /**
     * Check whether the position <tt>p</tt> is the right child.
     * @param p the position
     * @return true if <tt>p</tt> is the position of the right child, false otherwise
     */
    default boolean isRightChild(Position<T> p) {
        return p.isRightChild();
    }

    /**
     * Replace the current value of the position.
     * @param p the position
     * @param value element to be replaced with
     * @return the old value of the position
     */
    default T replace(Position<T> p, T value) {
        return p.replace(value);
    }

    /**
     * Return the list of positions in the tree in pre-order.
     * @return the list of positions
     */
    List<Position<T>> preOrder();

    /**
     * Return the list of positions in the tree in in-order.
     * @return the list of positions
     */
    List<Position<T>> inOrder();

    /**
     * Return the list of positions in the tree in post-order.
     * @return the list of positions
     */
    List<Position<T>> postOrder();

    /**
     * Return the list of positions in the tree in level-order.
     * @return the list of positions
     */
    List<Position<T>> levelOrder();

    /**
     * Associate a new root with the value <tt>value</tt> as the root of this tree.
     * The tree must have been an empty tree before you call this method.
     * @param value element of the root
     * @return the position of the root
     */
    Position<T> addRoot(T value);


    /**
     * Attach a tree as the left child of the position <tt>p</tt> if the left child of the
     * position <tt>p</tt> is an external node. Throw exception otherwise.
     * @param p the position to insert left subtree
     * @param tree a subtree to be attached
     */
    void attachLeftSubtree(Position<T> p, BinaryTree<T> tree);

    /**
     * Attach a tree as the right child of the position <tt>p</tt> if the right child of the
     * position <tt>p</tt> is an external node. Throw exception otherwise.
     * @param p the position to insert left subtree
     * @param tree a subtree to be attached
     */
    void attachRightSubtree(Position<T> p, BinaryTree<T> tree);

    /**
     * Print the contents of the tree.
     */
    void printTree();
}
