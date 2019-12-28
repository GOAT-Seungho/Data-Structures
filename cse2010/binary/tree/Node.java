package cse2010.binary.tree;

/**
 * Actual node representation in a tree implemented by linked-list. A tree consists of internal
 * nodes and external nodes. An internal node is implemented as always having two external nodes as
 * children. The external node will be used as a placeholder for further insertion.
 * User must not depend on the <tt>null</tt> check to determine the types of nodes. Instead,
 * User must use <tt>isInternal()</tt> or <tt>isExternal()</tt> to check whether a given node
 * is internal or external.
 *
 * @param <T> element type for the node
 */
abstract public class Node<T> implements Position<T> {
    protected T value;
    protected Position<T> parent;
    protected Position<T> left;
    protected Position<T> right;

    /**
     * This constructor creates an internal node with two external children (i.e. <tt>ENode</tt>).
     *
     * @param parent parent node
     */
    protected Node(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * Returns the element stored at this node.
     *
     * @return the element stored at this node
     */
    @Override
    public T element() {
        return value;
    }

    /**
     * Set the element at this node.
     *
     * @param element the element for the node
     */
    @Override
    public void setElement(T element) {
        this.value = element;
    }

    /**
     * Returns the parent of this node if exists (null otherwise).
     *
     * @return the parent of this node if exists (null otherwise)
     */
    @Override
    public Position<T> parent() {
        return parent;
    }

    /**
     * Returns the left child of this node.
     *
     * @return the left child of this node
     */
    @Override
    public Position<T> left() {
        return left;
    }

    /**
     * Returns the right child of this node.
     *
     * @return the right child of this node
     */
    @Override
    public Position<T> right() {
        return right;
    }

    /**
     * Associate p as the parent of this node
     *
     * @param p the parent node for this node
     */
    @Override
    public void setParent(Position<T> p) {
        this.parent = p;
    }

    /**
     * Associate p as the left child node.
     *
     * @param p the node to be the left child
     */
    @Override
    public void setLeft(Position<T> p) {
        this.left = p;
        p.setParent(this);
    }

    /**
     * Associate p as the right child node.
     *
     * @param p the node to be the right child
     */
    @Override
    public void setRight(Position<T> p) {
        this.right = p;
        p.setParent(this);
    }

    /**
     * Returns the depth of this node in the tree.
     *
     * @return the depth of this node in the tree.
     */
    @Override
    public int depth() {
        if (parent == null) return 0;

        return parent().depth() + 1;
    }

    /**
     * Returns the height of this node in the tree.
     *
     * @return the height of this node in the tree.
     */
    @Override
    public int height() {
        if (isExternal()) return 0;
        else {
            return 1 + max(left().height(), right().height());
        }
    }

    private int max(int x, int y) {
        return x > y ? x : y;
    }

    /**
     * Check whether the position is an external node.
     *
     * @return true if external node, false otherwise
     */
    abstract public boolean isExternal();

    /**
     * Check whether the position is an internal node.
     *
     * @return true if internal node, false otherwise
     */
    abstract public boolean isInternal();


    /**
     * Print the string representation for this node.
     *
     * @return string
     */
    public String toString() {
        return "Node(" + value + ')';
    }
}


