package cse2010.binary.tree;

/**
 * Internal node representation for the link-list based tree. An internal node always
 * have two external nodes as children. User must use <tt>isInternal()</tt> or
 * <tt>isExternal()</tt> to check whether a given node is internal or external.
 *
 * @param <T> element type for the node
 */
public class INode<T> extends Node<T> {

    /**
     * Creates an internal node with two children <tt>left</tt> and <tt>right</tt>.
     * Either or both children can be <tt>null</tt>. A <tt>null</tt> child is replaced by
     * an external node (i.e. <tt>ENode</tt>).
     *
     * @param value  element of this node
     * @param left   left child (can be <tt>null</tt>)
     * @param right  right child (can be <tt>null</tt>)
     * @param parent the parent of this node (can be <tt>null</tt>)
     */
    public INode(T value, Node<T> left, Node<T> right, Node<T> parent) {
        super(parent);
        this.value = value;
        this.left = (left != null) ? left : new ENode<>();
        this.right = (right != null) ? right : new ENode<>();
        this.left.setParent(this);
        this.right.setParent(this);
    }

    /**
     * Creates an internal node with two children <tt>left</tt> and <tt>right</tt>.
     * Either or both children can be <tt>null</tt>. A <tt>null</tt> child is replaced by
     * an external node (i.e. <tt>ENode</tt>). The parent node is set to <tt>null</tt>.
     *
     * @param value element of this node
     * @param left  left child (can be <tt>null</tt>)
     * @param right right child (can be <tt>null</tt>)
     */
    public INode(T value, Node<T> left, Node<T> right) {
        this(value, left, right, null);
    }

    /**
     * Create an internal node with two <tt>ENode</tt> external nodes.
     * The parent is set to <tt>null</tt>.
     *
     * @param value element of this node
     */
    public INode(T value) {
        this(value, null, null, null);
    }

    /**
     * Check whether the position is an internal node.
     *
     * @return true
     */
    @Override
    public boolean isInternal() {
        return true;
    }

    /**
     * Check whether the position is an external node.
     *
     * @return false
     */
    @Override
    public boolean isExternal() {
        return false;
    }

}
