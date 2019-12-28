package cse2010.binary.tree;

/**
 * Link-based implementation of binary trees. A tree consists of internal
 * nodes and external nodes. An internal node is implemented as always having two external nodes as
 * children. The external node will be used as a placeholder for further insertion.
 * User must not depend on the <tt>null</tt> check to determine the types of nodes. Instead,
 * User must use <tt>isInternal()</tt> or <tt>isExternal()</tt> to check whether a given node
 * is internal or external.
 *
 * @param <T> element type for the node
 */
public class LinkedBinaryTree<T> extends AbstractBinaryTree<T> {

    protected Position<T> root = null;

    public LinkedBinaryTree() {
    }

    public LinkedBinaryTree(Node<T> root) {
        this.root = root;
        size = preOrder().size();
    }

    /**
     * Check whether this tree is empty or not.
     * @return true if empty tree, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Check whether the node is the root of the tree.
     * @param node the node of the tree
     * @return true if the node is the root, false otherwise
     */
    @Override
    public boolean isRoot(Position<T> node) {
        return node.parent() == null;
    }

    /**
     * Return the root position of this three.
     * @return position of the root
     */
    @Override
    public Position<T> root() {
        return root;
    }

    /**
     * Associate a new root with the value <tt>value</tt> as the root of this tree.
     * The tree must have been an empty tree before you call this method.
     * @param value element of the root
     * @return the position of the root
     */
    public Position<T> addRoot(T value) {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = new INode<>(value);
        size = 1;
        return root;
    }

    /**
     * Replace the external position with a newly created internal position.
     * @param p     the external position
     * @param value element for the newly created internal position
     * @return the position of newly created internal position
     */
    protected Position<T> expandExternal(Position<T> p, T value) {
        if (isInternal(p)) throw new IllegalStateException("Internal node cannot be expanded");

        Node<T> newNode = new INode<>(value);

        if (isLeftChild(p))
            p.parent().setLeft(newNode);
        else
            p.parent().setRight(newNode);

        return newNode;
    }
}
