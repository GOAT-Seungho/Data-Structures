// package cse2010.binary.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as an abstraction for the location of the nodes in the tree.
 *
 * @param <T> element type for the node
 */
public interface Position<T> {

    /**
     * Returns the element stored at this position.
     *
     * @return the element stored at this position
     */
    T element();

    /**
     * Set the element at this position.
     *
     * @param element the element for the node
     */
    void setElement(T element);

    /**
     * Returns the left child of this position.
     *
     * @return the left child of this position
     */
    Position<T> left();

    /**
     * Associate p as the left child position.
     *
     * @param p the position to be the left child
     */
    void setLeft(Position<T> p);

    /**
     * Returns the right child of this position.
     *
     * @return the right child of this position
     */
    Position<T> right();

    /**
     * Associate p as the right child position.
     *
     * @param p the position of the right child
     */
    void setRight(Position<T> p);

    /**
     * Check whether the position is internal node.
     *
     * @return true if internal position, false otherwise
     */
    boolean isInternal();

    /**
     * Check whether the position is external node.
     *
     * @return true if external position, false otherwise
     */
    boolean isExternal();

    /**
     * Returns the parent of this position if exists (null otherwise).
     *
     * @return the parent of this position if exists (null otherwise)
     */
    Position<T> parent();

    /**
     * Associate p as the parent of this position
     *
     * @param p the parent position for this position
     */
    void setParent(Position<T> p);

    /**
     * Returns the depth of this position in the tree.
     *
     * @return the depth of this position in the tree.
     */
    int depth();

    /**
     * Returns the height of this position in the tree.
     *
     * @return the height of this position in the tree.
     */
    int height();

    /**
     * Returns the number of children of this position.
     *
     * @return the number of children
     */
    default int numChildren() {
        int count = 0;
        if (hasLeft()) count++;
        if (hasRight()) count++;
        return count;
    }

    /**
     * Get the children of this position.
     *
     * @return the list of the children of this position, empty list if none
     */
    default List<Position<T>> children() {
        if (isExternal())
            throw new IllegalStateException("Cannot be called on external node");

        List<Position<T>> children = new ArrayList<>(2);
        if (left().isInternal())
            children.add(left());
        if (right().isInternal())
            children.add(right());
        return children;
    }

    /**
     * Check if this position has a left child.
     *
     * @return true if this position has a left child, false otherwise
     */
    default boolean hasLeft() {
        return isInternal() && left().isInternal();
    }

    /**
     * Check if this position has a right child.
     *
     * @return true if this position has a right child, false otherwise
     */
    default boolean hasRight() {
        return isInternal() && right().isInternal();
    }

    /**
     * Check whether this position is the left child.
     *
     * @return true if left child, false otherwise
     */
    default boolean isLeftChild() {
        return parent() != null ? parent().left() == this : false;
    }

    /**
     * Check whether this position is the right child.
     *
     * @return true if the right child, false otherwise
     */
    default boolean isRightChild() {
        return parent() != null ? parent().right() == this : false;
    }

    /**
     * Replace the current value of this position.
     *
     * @param value element to be replaced with
     * @return the old value of this position
     */
    default T replace(T value) {
        if (isExternal()) {
            throw new IllegalStateException("External node cannot be replaced");
        }

        T old = element();
        setElement(value);
        return old;
    }
}
