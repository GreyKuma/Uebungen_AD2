/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  1 20:09:33 CEST 2018
 */

package uebung03.as.aufgabe03;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import uebung02.as.aufgabe01.BinarySearchTree;

class AVLTreeImpl<K extends Comparable<? super K>, V> extends
        BinarySearchTree<K, V> {

    /**
     * After the BST-operation 'insert()':
     * actionNode shall point to the parent of the new inserted node.
     */
    protected AVLNode actionNode;


    protected class AVLNode extends BinarySearchTree<K, V>.Node {

        private int height;
        private Node parent;

        AVLNode(Entry<K, V> entry) {
            super(entry);
        }

        protected AVLNode setParent(AVLNode parent) {
            AVLNode old = avlNode(this.parent);
            this.parent = parent;
            return old;
        }

        protected AVLNode getParent() {
            return avlNode(parent);
        }

        protected int setHeight(int height) {
            int old = this.height;
            this.height = height;
            return old;
        }

        protected int getHeight() {
            return height;
        }

        @Override
        public AVLNode getLeftChild() {
            return avlNode(super.getLeftChild());
        }

        @Override
        public AVLNode getRightChild() {
            return avlNode(super.getRightChild());
        }

        @Override
        public String toString() {
            String result = String.format("%2d - %-6s : h=%d",
                    getEntry().getKey(), getEntry().getValue(), height);
            if (parent == null) {
                result += " ROOT";
            } else {
                boolean left = parent.getLeftChild() == this;
                result += (left ? " / " : " \\ ") + "parent(key)="
                        + parent.getEntry().getKey();
            }
            return result;
        }

    } // End of class AVLNode


    protected AVLNode getRoot() {
        return avlNode(root);
    }

    public V put(K key, V value) {
        // TODO Implement here...
        Entry<K, V> entry = find(key);
        if (entry != null) {
            return entry.setValue(value);
        } else {
            entry = insert(key, value);
        }
        return entry.getValue();
    }

    public V get(K key) {
        // TODO Implement here...
        Entry<K, V> entry = find(key);
        if(entry != null){
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
        // TODO Implement here...
        Entry<K,V> entry = new Entry<>(key, value);
        AVLNode child = avlNode(newNode(entry));
        if(isEmpty()){
            root = child;
            child.setHeight(0);
            return entry;
        }
        AVLNode parent = avlNode(findInsertParent(root, entry));
        if(key.compareTo(parent.getEntry().getKey()) < 0){
            parent.setLeftChild(child);
            parent.getLeftChild().setParent(parent);
        }else{
            parent.setRightChild(child);
            parent.getRightChild().setParent(parent);
        }
        assureHeights(child);
        return entry;
    }

    /**
     * The height of the tree.
     *
     * @return The actual height. -1 for an empty tree.
     */
    @Override
    public int getHeight() {
        return height(avlNode(root));
    }

    /**
     * Returns the height of this node.
     *
     * @param node
     * @return The height or -1 if null.
     */
    protected int height(AVLNode node) {
        return (node != null) ? node.getHeight() : -1;
    }

    /**
     * Assures the heights of the tree from 'node' up to the root.
     *
     * @param node The node from where to start.
     */
    protected void assureHeights(AVLNode node) {
        // TODO Implement here...
        AVLNode parent = node.getParent();
        int left = -1;
        int right = -1;

        if(node.getLeftChild() != null){
            left = node.getLeftChild().getHeight();
        }
        if(node.getRightChild() != null){
            right = node.getRightChild().getHeight();
        }
        node.setHeight(Math.max(right, left) + 1);

        if(parent != null) {
            assureHeights(parent);
        }
    }

    /**
     * Assures the correct height for node.
     *
     * @param node The node to assure its height.
     */
    protected void setHeight(AVLNode node) {
        // TODO Implement here...
        assureHeights(node);

    }

    /**
     * Factory-Method. Creates a new node.
     *
     * @param entry The entry to be inserted in the new node.
     * @return The new created node.
     */
    @Override
    protected Node newNode(Entry<K, V> entry) {
        // TODO Implement here...
        return new AVLNode(entry);
    }

    /**
     * Generates an inorder-node-list.
     *
     * @param nodeList The node-list to fill in inorder.
     * @param node     The node to start from.
     */
    protected void inorder(Collection<AVLNode> nodeList, AVLNode node) {
        if (node == null)
            return;
        inorder(nodeList, node.getLeftChild());
        nodeList.add(node);
        inorder(nodeList, node.getRightChild());
    }

    // Type-Casting: Node -> AVLNode (Cast-Encapsulation)
    @SuppressWarnings("unchecked")
    protected AVLNode avlNode(Node node) {
        return (AVLNode) node;
    }

    public void print() {
        List<AVLNode> nodeList = new LinkedList<>();
        inorder(nodeList, avlNode(root));
        for (AVLNode node : nodeList) {
            System.out.println(node + "  ");
        }
    }

}

 
