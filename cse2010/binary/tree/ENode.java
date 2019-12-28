package cse2010.binary.tree;

/**
 * An external node representation of a tree. Nodes of this type is used as a placeholder
 * for further insertion. In case of insertion, the external node must be replaced by another
 * internal nodes having two external nodes as children.
 *
 * @param <T> element type for the node
 */
public class ENode<T> extends Node<T> {

    /**
     * Creates an instance of an external node with its parent set to <tt>parent</tt>.
     * Both children are <tt>null</tt>.
     *
     * @param parent the parent node
     */
    public ENode(Node<T> parent) {
        super(parent);
        left  = null;
        right = null;
        value = null;
    }

    /**
     * Creates an instance of an external node with its parent set to <tt>null</tt>.
     * Both children are <tt>null</tt>.
     */
    public ENode() {
        super(null);
        left  = null;
        right = null;
        value = null;
    }

    /**
     * Check whether the position is an internal node.
     * @return false
     */
    @Override
    public boolean isInternal() {
        return false;
    }

    /**
     * Check whether the position is an external node.
     * @return true
     */
    @Override
    public boolean isExternal() {
        return true;
    }
}
